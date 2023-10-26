package aug.laundry.controller;

import aug.laundry.commom.SessionConstant;
import aug.laundry.domain.RepairImage;
import aug.laundry.dto.*;
import aug.laundry.enums.category.Category;
import aug.laundry.enums.category.CategoryPriceCalculator;
import aug.laundry.enums.category.MemberShip;
import aug.laundry.enums.category.Pass;
import aug.laundry.enums.repair.RepairCategory;
import aug.laundry.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/laundry")
public class LaundryOrderController {

    private final MainService mainService;
    private final LaundryService laundryService;

    @PostMapping
    public String postLaundry(@SessionAttribute(name = SessionConstant.LOGIN_MEMBER, required = false) Long memberId,
                              @SessionAttribute(name = SessionConstant.ORDERS_CONFIRM, required = false) Long ordersDetailId,
                              String option,
                              FormatDate formatDate,
                              @RequestParam("service") List<String> service,
                              RedirectAttributes redirectAttributes){

        // 빠른세탁 장바구니 추가
        if (option.equals("fast")){
            laundryService.insertQuickLaundry(ordersDetailId);
        }

        // 일반세탁 있으면
        for (String serviceName : service) {
            if(serviceName.equals("common")){
                laundryService.insertCommon(ordersDetailId);
            }
        }

        redirectAttributes.addFlashAttribute("quick", option); // 빠른세탁이면 true 아니면 false
        redirectAttributes.addFlashAttribute("service", service); // 드라이클리닝, 생활빨, 수선 선택여부
        redirectAttributes.addFlashAttribute("dateTime", formatDate);
        redirectAttributes.addFlashAttribute("ordersDetailId", ordersDetailId);

        System.out.println("option = " + option);
        System.out.println("formatDate = " + formatDate);
        System.out.println("service = " + service);

        return "redirect:/laundry/order";
    }

    @GetMapping
    public String first(@SessionAttribute(name = SessionConstant.LOGIN_MEMBER, required = false) Long memberId, HttpSession session, Model model) {
        boolean isAddress = laundryService.checkAddress(memberId);
        if (!isAddress) {
            return "redirect:/members/" + memberId + "/address/update"; // 주소값이 없으면 주소 변경으로 리다이렉트
        }
        laundryService.check(memberId, session); // 이전에 존재하던 장바구니 테이블이 있으면 삭제하고 새로 생성 , 없으면 그냥 생성, 생성후에 session 발급

        DateForm dateForm = new DateForm(); // 수거, 배송에 대한 정보
        model.addAttribute("dateTime", dateForm);
        return "project_order_1";
    }

    @GetMapping("/dry")
    public String drycleaning(@SessionAttribute(name = SessionConstant.LOGIN_MEMBER, required = false) Long memberId,
                              @SessionAttribute(name = SessionConstant.ORDERS_CONFIRM, required = false) Long orderDetailId,
                              Model model) {

        // 카테고리
        Map<String, Map<String, Long>> category = mainService.getCategory();
        MemberShip memberShip = laundryService.isPass(memberId);
        model.addAttribute("category", category);
        if (memberShip.getCheck() == Pass.PASS) {
            model.addAttribute("percent", CategoryPriceCalculator.PASS.percent());
        }

        // 현재 장바구니에서 기록이 있는지 확인후 장바구니 가져오기
        List<OrderDrycleaning> reload = laundryService.reloadDrycleaning(orderDetailId); // null 이면 이전에 저장된값 없음
        System.out.println("reload = " + reload);
        model.addAttribute("reload", reload);
        return "project_order_2_1";
    }

    @GetMapping("/repair")
    public String repair(@SessionAttribute(name = SessionConstant.LOGIN_MEMBER, required = false) Long memberId,
                         @SessionAttribute(name = SessionConstant.ORDERS_CONFIRM, required = false) Long orderDetailId,
                         Model model) {
        MemberShip memberShip = laundryService.isPass(memberId);
        if (memberShip.getCheck() == Pass.PASS){
            model.addAttribute("percent", CategoryPriceCalculator.PASS.percent());
        }
        RepairCategory[] repairCategory = RepairCategory.values();

        List<OrderRepair> reload = laundryService.reloadRepair(orderDetailId);
        if (reload != null && !reload.isEmpty()) {
            Map<Long, List<String>> uploadImage = laundryService.getRepairImage(reload);
            model.addAttribute("uploadImage", uploadImage);
        }
        model.addAttribute("reload", reload);
        model.addAttribute("repairCategory", repairCategory);

        return "project_order_2_2";
    }

    @Transactional
    @PostMapping("/dry/order")
    public @ResponseBody Map<String, Boolean> dryOrder(@SessionAttribute(name = SessionConstant.LOGIN_MEMBER, required = false) Long memberId,
                                                       @SessionAttribute(name = SessionConstant.ORDERS_CONFIRM, required = false) Long ordersDetailId,
                                                       @RequestBody Map<String, Integer> result) {
        HashMap<String, Boolean> resultMap = new HashMap<>();
        boolean status = laundryService.insertDrycleaning(memberId, ordersDetailId, result, resultMap);

        System.out.println("status = " + status);

        resultMap.put("result", status);
        return resultMap;
    }

    @GetMapping("/repair/check")
    public @ResponseBody String repairCheck(@SessionAttribute(name = SessionConstant.ORDERS_CONFIRM, required = false) Long ordersDetailId) {
        log.info("repair check [ ordersDetailId = {} ]", ordersDetailId);
        List<Long> repairIdList = laundryService.findByRepairId(ordersDetailId);
        if (repairIdList != null) {
            for (Long repairId : repairIdList) {
                laundryService.removeRepairImagesFile(repairId);
                laundryService.removeRepairImages(repairId);
            }
                laundryService.removeRepair(ordersDetailId);
        }

        return "";

    }

    @Transactional
    @PostMapping(value = "/repair/order")
    public @ResponseBody Map<String, Boolean> repairOrder(@SessionAttribute(name = SessionConstant.LOGIN_MEMBER, required = false) Long memberId,
                                                          @SessionAttribute(name = SessionConstant.ORDERS_CONFIRM, required = false) Long ordersDetailId,
                                                          @RequestPart("repairData") Map<String, RepairFormData> repairData,
                                                          @RequestParam(name = "files", required = false) List<MultipartFile> files) {
        HashMap<String, Boolean> resultMap = new HashMap<>();
        System.out.println("files = " + files);
        boolean status = laundryService.insertRepair(memberId, ordersDetailId, resultMap, repairData, files);


        log.info("repairData = {}", repairData);
        log.info("files = {}", files);

        return resultMap;
    }
}
