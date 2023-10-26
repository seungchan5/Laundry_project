package aug.laundry.controller;

import aug.laundry.dto.SubscriptionPayDto;
import aug.laundry.service.SubscribeService_ksh;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SubscribeController {

    private final SubscribeService_ksh subscribeService_ksh;
    @GetMapping(value={"/subscription/{memberId}", "/subscription"})
    public String subscribeInfo(@PathVariable(required = false) Long memberId, Model model) {

        log.info("memberId={}", memberId);

        SubscriptionPayDto data = null;
        if(memberId != null) {
            data = subscribeService_ksh.getSubscribeInfo(memberId);
        }
        log.info("data={}",data);
        model.addAttribute("data", data);
        return "project_subscribe";
    }

    @GetMapping("/subscription/order/{memberId}")
    public String subscribeOrder(@RequestParam(name = "result", required = false) String result,
                                 @PathVariable("memberId") Long memberId, Model model) {
        if(memberId != null) {
            SubscriptionPayDto data = subscribeService_ksh.getSubscribeInfo(memberId);
            model.addAttribute("data", data);
        }
        if(result!=null) {
            model.addAttribute("result", result);
        }

        return "project_subscribe_choice";
    }

    @GetMapping("/subscription/confirm/{impUid}")
    public String confirmView(@RequestParam(name = "result", required = false) String result,
                              @PathVariable("impUid") String impUid, Model model) {
        try {
            String requestUrl = "https://api.iamport.kr/payments/" + impUid;
            JsonObject jsonObj = subscribeService_ksh.getData(requestUrl);
            JsonObject dataObj = jsonObj.get("response").getAsJsonObject();

            int selectMonth = Integer.parseInt(dataObj.get("name").getAsString().replaceAll("[^0-9]", ""));

            String expireDate = LocalDate.now().plusDays(31*selectMonth).toString();
            SubscriptionPayDto data = new SubscriptionPayDto();
            boolean first = false;
            if(dataObj.get("amount").getAsInt() == 0) {
                // 1개월 무료 체험일경우
                expireDate = LocalDate.now().plusDays(31).toString();
                first = true;
            }
            data.setAmount(subscribeService_ksh.getPrice(selectMonth));
            data.setSelectMonth(selectMonth);
            data.setSubscriptionExpireDate(expireDate);

            model.addAttribute("first", first);
            model.addAttribute("data", data);
        } catch (IOException e) {
            model.addAttribute("error", e.getMessage());
        }

        model.addAttribute("result", result);
        return "project_subscribe_success";
    }

    @GetMapping("/payments/mobile")
    public String subscribeOrderConfirm(String imp_uid, String merchant_uid, String imp_success) {
        // 모바일 결제
        String result = "";
        int payPrice = 0;
        try {
            SubscriptionPayDto subDto = subscribeService_ksh.validationPay(imp_uid);
            payPrice = subDto.getAmount(); // amount
            // 결제금액 일치. 결제 된 금액 === 결제 되어야 하는 금액
            if (payPrice == subDto.getAmountToBepay()) {
                if ("paid".equals(subDto.getPayStatus())) {
                    result = "success";
                } else {
                    // 잔액 부족등과 같은 결제 오류가 났을경우
                    result = "fail";
                    return "redirect:/subscription/order?result=" + result;
                }
            } else {
                // 결제금액 불일치. 위/변조 된 결제
                String refundRes = subscribeService_ksh.refund(imp_uid, payPrice);
                result = "Fraudulent payment attempt refund "+refundRes;
                return "redirect:/subscription/order?result=" + result;
            }
        }
        catch (IOException e) {
            // 오류 -> 관리자 문의
            return "redirect:/subscription/order?result=" + e.getMessage();
        }
        String url = "/subscription/confirm/"+imp_uid+"?result="+result;
        return "redirect:"+url;
    }

    @PostMapping("/payments/prepare")
    public @ResponseBody Map<String, String> paymentPrepare(@RequestBody SubscriptionPayDto subData) {
        Map<String, String> map = new HashMap<>();
        // 결제 전 검증
        String result = "fail";

        SubscriptionPayDto getInfo = subscribeService_ksh.getSubscribeInfo(subData.getCustomerUid());
        if(getInfo==null) {
            // 신규
            result = "success";
        } else if(subData.getAmount() == subscribeService_ksh.getPrice(subData.getSelectMonth()) ) {
            result = "success";
        }

        map.put("result", result);

        return map;
    }

    @PostMapping("/payments/pc")
    public @ResponseBody Map<String, String> paymentsComplete(@RequestBody SubscriptionPayDto subData) {
        // pc결제
        String result = "";
        Map<String, String> map = new HashMap<>();
        int payPrice = 0;
        try {
            SubscriptionPayDto subDto = subscribeService_ksh.validationPay(subData.getImpUid());
            payPrice = subDto.getAmount(); // amount
            // 결제금액 일치. 결제 된 금액 === 결제 되어야 하는 금액
            if (payPrice == subDto.getAmountToBepay()) {
                if ("paid".equals(subDto.getPayStatus())) {
                    result = "success";
                } else {
                    // 잔액 부족등과 같은 결제 오류가 났을경우
                    result = "fail";
                }
            } else {
                // 결제금액 불일치. 위/변조 된 결제
                String refundRes = subscribeService_ksh.refund(subData.getImpUid(), payPrice);
                result = "Fraudulent payment attempt refund "+refundRes;
            }
        }
        catch (IOException e) {
            // 오류 -> 관리자 문의
            result = e.getMessage();
        }
        map.put("result", result);
        return map;
    }

    @PostMapping("/repayments/webhook")
    public @ResponseBody String webhookUse(@RequestBody String webhook){
        // 포트원에서 받은 결제 정보처리 & 다음 결제 예약
        JsonObject data = new Gson().fromJson(webhook, JsonObject.class);
        String imp_uid = data.get("imp_uid").getAsString();
        String result = "";
        log.info("웹훅 호출");
        try {
            SubscriptionPayDto subDto = subscribeService_ksh.validationPay(imp_uid);
            int payPrice = subDto.getAmount(); // amount
            // 결제금액 일치. 결제 된 금액 === 결제 되어야 하는 금액
            if (payPrice == subDto.getAmountToBepay()) {
                if ("paid".equals(subDto.getPayStatus())) {
                    log.info("paid 호출");
                    // DB저장
                    log.info("subDto={}", subDto);
                    subscribeService_ksh.saveSubscribe(subDto);
                    ZonedDateTime date = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).with(LocalTime.MIDNIGHT);
                    if(payPrice==0) {
                        date= date.plusDays(31);
                        subDto.setAmount(subscribeService_ksh.getPrice(subDto.getSelectMonth()));
                        log.info("**************IF**********subDto={}", subDto);
                    } else {
                        date = date.plusDays(31*subDto.getSelectMonth());
                    }
                    Instant instant = date.toInstant();
                    long timeStamp = instant.getEpochSecond();
                    // 다음결제 예약
                    result = subscribeService_ksh.schedulePay(subDto, timeStamp);
                } else {
                    SubscriptionPayDto getInfo = subscribeService_ksh.getSubscribeInfo(subDto.getCustomerUid());
                    // 처음 구독시 결제실패 DB저장 X
                    if(getInfo!=null && 'Y'==getInfo.getSubscriptionStatus()) {
                        // 재결제 때 잔액 부족등과 같은 결제 오류가 났을경우 최근 결제 내역이 있으면
                        // 다음날로 재결제 예약이랑 fail_resaon저장, 결제 시도 3번 후 더이상 예약 X
                        ZonedDateTime date = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).plusDays(1);
                        Instant instant = date.toInstant();
                        long timeStamp = instant.getEpochSecond();

                        subscribeService_ksh.updateFailReason(subDto.getMerchantUid(), subDto.getFailReason());
                        int count = subscribeService_ksh.getRepayCount(subDto.getMerchantUid());
                        if (count < 3) {
                            result = subscribeService_ksh.schedulePay(subDto, timeStamp);
                            subscribeService_ksh.updateRepayCount(subDto.getMerchantUid());
                        } else {
                            subscribeService_ksh.updateCancel(subDto.getMerchantUid());
                        }
                    }
                }
            } else {
                // 결제금액 불일치. 위/변조 된 결제
                String refundRes = subscribeService_ksh.refund(imp_uid, payPrice);
                result = "Fraudulent payment attempt refund "+refundRes;
            }
        }
        catch (IOException e) {
            // 오류 -> 관리자 문의
            result = e.getMessage();
        }
        return result;
    }

    @GetMapping("/subscribe/unschedule/{memberId}")
    public @ResponseBody Map<String, String> cancleTest(@PathVariable("memberId") Long memberId) {
        // 구독 취소
        Map<String, String> map = new HashMap<>();

        try {
            SubscriptionPayDto info = subscribeService_ksh.getScheduleInfo(memberId);
            String cancelUrl =  "https://api.iamport.kr/subscribe/payments/unschedule";
            String jsonBody = String.format("{\"customer_uid\": \"%s\", \"merchant_uid\": \"%s\"}", info.getCustomerUid(), info.getMerchantUidRe());

            JsonObject scheduleStatus = subscribeService_ksh.postData(cancelUrl, jsonBody);

            if(scheduleStatus.get("response").isJsonNull()) {
                // 이미 구독이 해지된 경우
                map.put("result", "none");
                map.put("reason", scheduleStatus.get("message").getAsString());
                return map;
            } else {
                scheduleStatus = scheduleStatus.getAsJsonArray("response").get(0).getAsJsonObject();


                if("revoked".equals(scheduleStatus.get("schedule_status").getAsString())) {
                    subscribeService_ksh.updateCancel(info.getMerchantUid());
                    map.put("result", "success");
                } else {
                    if(!scheduleStatus.get("fail_reason").isJsonNull()) {
                        subscribeService_ksh.updateFailReason(info.getMerchantUid(),scheduleStatus.get("fail_reason").getAsString());
                    }
                    map.put("result", "fail");
                    map.put("reason", scheduleStatus.get("fail_reason").getAsString());
                }
                map.put("result", "test");
            }

        } catch (IOException e) {
            map.put("error", e.getMessage());
        }
        return map;
    }
}



