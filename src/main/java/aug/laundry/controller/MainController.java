package aug.laundry.controller;

import aug.laundry.commom.SessionConstant;
import aug.laundry.dao.EnumDao;
import aug.laundry.domain.Admin;
import aug.laundry.dto.MemberDto;
import aug.laundry.dto.OrdersEnum2;
import aug.laundry.enums.category.CategoryPriceCalculator;

import aug.laundry.enums.repair.RepairCategory;
import aug.laundry.service.MainService;
import aug.laundry.service.MemberService_kgw;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberService_kgw memberServiceKgw;
    private final MainService mainService;
    private final EnumDao enumDao;

    @GetMapping
    public String mainPage(@SessionAttribute(name = SessionConstant.LOGIN_MEMBER, required = false) Long memberId,
                           Model model) {
        model.addAttribute("firstLogin", model.getAttribute("firstLogin"));


        if (memberId != null){
            MemberDto memberDto = memberServiceKgw.selectOne(memberId); // 일반회원
            if (memberDto != null) {
                List<OrdersEnum2> orders = mainService.getOrders(memberId);
                model.addAttribute("name", memberDto.getMemberName());
                model.addAttribute("orders", orders);
            }
        }


        return "project_main";
    }

    @GetMapping("price")
    public String priceTag(Model model) {
        Map<String, Map<String, Long>> priceTag = mainService.getCategory();
        Float percent = CategoryPriceCalculator.PASS.percent();
        RepairCategory[] repair = RepairCategory.values();


        model.addAttribute("percent", percent);
        model.addAttribute("drycleaning", priceTag);
        model.addAttribute("repair", repair);
        return "project_price_tag";
    }

    @GetMapping("info")
    public String info() {

        return "project_info";
    }
}
