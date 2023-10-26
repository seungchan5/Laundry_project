package aug.laundry.service;

import aug.laundry.dao.subscribe.SubscribeDao;
import aug.laundry.dto.ScheduleDto;
import aug.laundry.dto.SubscriptionPayDto;
import aug.laundry.enums.subscribe.Subscribe;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscribeServiceImpl_ksh implements SubscribeService_ksh{

    private final SubscribeDao subscribeDao;

    @Value("${import.rest-api-s}")
    private String restApi;

    @Value("${import.rest-api-secret-s}")
    private String restApiSecret;

    @Transactional
    @Override
    public int saveSubscribe(SubscriptionPayDto subData) {
        // 결제정보 DB저장
        int res = subscribeDao.insertJoinSubscribe(subData);
        res += subscribeDao.updateMemberSubscribe(subData);
        return res;
    }

    @Override
    public String getAccessToken() {

        String accessToken = "";

        try {
            String requestUrl = "https://api.iamport.kr/users/getToken";
            String jsonBody = String.format("{\"imp_key\": \"%s\", \"imp_secret\": \"%s\"}", restApi, restApiSecret);
            StringEntity json = new StringEntity(jsonBody, "UTF-8");
            json.setContentType("application/json");

            CloseableHttpClient httpClient = HttpClients.createDefault(); // http 요청하기위한 객체 생성
            HttpPost postRequest = new HttpPost(requestUrl);
            postRequest.setEntity(json);

            // 요청 후 데이터 받기
            HttpResponse response = httpClient.execute(postRequest);
            HttpEntity getEntity = response.getEntity();

            // 응답데이터를 String으로 변환
            String responseEntity = EntityUtils.toString(getEntity);

            // responseEntity데이터를 JsonObject객체로 파싱
            JsonObject jsonObject = new Gson().fromJson(responseEntity, JsonObject.class);

            // accessToken추출
            accessToken = jsonObject.get("response").getAsJsonObject().get("access_token").getAsString();

            httpClient.close();

            return accessToken;
        } catch (IOException e) {
            throw new RuntimeException("Unable to get access token",e);
        }
    }

    @Override
    public JsonObject postData(String requestUrl, String jsonBody) throws IOException {
        StringEntity json = new StringEntity(jsonBody, "UTF-8");
        json.setContentType("application/json");

        CloseableHttpClient httpClient = HttpClients.createDefault(); // http 요청하기위한 객체 생성
        HttpPost postRequest = new HttpPost(requestUrl);
        postRequest.setHeader("Authorization", getAccessToken());
        postRequest.setEntity(json);

        // 요청 후 응답 받기
        HttpResponse response = httpClient.execute(postRequest);
        // 응답 본문 가져오기
        HttpEntity getEntity = response.getEntity();

        // 응답데이터를 String으로 변환
        String responseEntity = EntityUtils.toString(getEntity);

        // responseEntity데이터를 JsonObject객체로 파싱
        JsonObject data = new Gson().fromJson(responseEntity, JsonObject.class);
        log.info("postData={}", data);

        httpClient.close();

        return data;
    }

    @Override
    public JsonObject getData(String requestUrl) throws IOException {
        String token = getAccessToken();
        CloseableHttpClient httpClient = HttpClients.createDefault(); // http 요청하기위한 객체 생성
        HttpGet getRequest = new HttpGet(requestUrl);
        getRequest.setHeader("Authorization", token);

        // 요청 후 데이터 받기
        HttpResponse response = httpClient.execute(getRequest);
        HttpEntity getEntity = response.getEntity();

        //responseEntity 널처리 하기
        String responseEntity = EntityUtils.toString(getEntity);
        JsonObject data = new Gson().fromJson(responseEntity, JsonObject.class);

        log.info("getData={}", data);

        httpClient.close();

        return data;
    }

    @Override
    public int updateNextMerchantId(String merchantUid, String merchantUidRe) {
        return subscribeDao.updateNextMerchantId(merchantUid, merchantUidRe);
    }

    @Override
    public SubscriptionPayDto getScheduleInfo(Long customerUid) {
        return subscribeDao.getScheduleInfo(customerUid);
    }

    @Override
    public int updateCancel(String merchantUid) {
        return subscribeDao.updateCancel(merchantUid);
    }

    @Transactional
    @Override
    public String schedulePay(SubscriptionPayDto subDto, long timeStamp) throws IOException {
        // 재결제 예약
        String scheduleUrl =  "https://api.iamport.kr/subscribe/payments/schedule";
        CloseableHttpClient httpClient = HttpClients.createDefault(); // http 요청하기위한 객체 생성

        String merchantUidRe = subDto.getCustomerUid()+"_subscribe_"+getDate();

        ScheduleDto schedule = new ScheduleDto();
        schedule.setMerchant_uid(merchantUidRe);
        schedule.setSchedule_at(timeStamp);
        schedule.setAmount(subDto.getAmount());
        schedule.setName(subDto.getSelectMonth()+"개월 정기결제예약");

        Gson gson = new Gson();
        String scheduleJson = gson.toJson(schedule);

        String jsonBody = String.format("{\"customer_uid\": \"%s\", \"schedules\": [%s]}", subDto.getCustomerUid(), scheduleJson);
        JsonObject jsonObjS = postData(scheduleUrl, jsonBody);

        // 리스폰스 데이터 배열이므로 JsonArray로 받음
        JsonArray responseArr = jsonObjS.get("response").getAsJsonArray();
        JsonObject response = responseArr.get(0).getAsJsonObject();

        String result = "";
        if("scheduled".equals(response.get("schedule_status").getAsString())){
            // 예약 성공하면 테이블에 merchant_uid_r 업데이트
            updateNextMerchantId(subDto.getMerchantUid(), merchantUidRe);
            result = "success";
        } else {
            // 예약 실패일 경우 구독 해지 처리
            if(!response.get("fail_reason").isJsonNull()) {
                updateFailReason(subDto.getMerchantUid(), response.get("fail_reason").getAsString());
            }
            updateCancel(subDto.getMerchantUid());
            result = "fail";
        }

        return result;
    }

    @Override
    public String refund(String impUid, int payPrice) throws IOException {
        String result="";

        String cancelUrl =  "https://api.iamport.kr/payments/cancel";
        String jsonBody = String.format("{\"reason\": \"%s\", \"imp_uid\": \"%s\", \"amount\": %d, \"checksum\": %d}", "잘못된 결제", impUid, payPrice, payPrice);

        JsonObject cancelDate  = postData(cancelUrl, jsonBody);
        JsonObject statusJson = cancelDate.get("response").getAsJsonObject();

        if("cancelled".equals(statusJson.get("status").getAsString())) {
            result = "success";
        } else {
            if(!statusJson.get("fail_reason").isJsonNull()) {
                result = "fail"+statusJson.get("fail_reason").getAsString();
            }
        }

        return result;
    }

    @Override
    public int getRepayCount(String merchantUid) {
        return subscribeDao.getRepayCount(merchantUid);
    }

    @Override
    public int updateRepayCount(String merchantUid) {
        return subscribeDao.updateRepayCount(merchantUid);
    }

    @Override
    public SubscriptionPayDto getSubscribeInfo(Long memberId) {
        return subscribeDao.getSubscribeInfo(memberId);
    }

    @Override
    public SubscriptionPayDto validationPay(String imp_uid) throws IOException {
        String result = "";
        SubscriptionPayDto subDto = new SubscriptionPayDto();

        // imp_uid 또는 merchant_uid로 아임포트 서버에서 결제 정보 조회
        String requestUrl = "https://api.iamport.kr/payments/" + imp_uid;
        JsonObject jsonObj = getData(requestUrl);
        JsonObject data = jsonObj.get("response").getAsJsonObject();

        int selectMonth = Integer.parseInt(data.get("name").getAsString().replaceAll("[^0-9]", ""));
        SubscriptionPayDto infoData = getSubscribeInfo(data.get("customer_uid").getAsLong());
        int amountToBePaid = 0;

        if(infoData == null){
            // 신규 구독일때
            amountToBePaid  = 0;
        } else {
            amountToBePaid = getPrice(selectMonth);
        }

        subDto.setCustomerUid(data.get("customer_uid").getAsLong());
        subDto.setMerchantUid(data.get("merchant_uid").getAsString());
        subDto.setImpUid(data.get("imp_uid").getAsString());
        subDto.setAmount(Integer.parseInt(data.get("amount").toString()));
        subDto.setSelectMonth(selectMonth);
        subDto.setAmountToBepay(amountToBePaid);
        subDto.setPayStatus(data.get("status").getAsString());
        if(!data.get("fail_reason").isJsonNull()) {
            subDto.setFailReason(data.get("fail_reason").getAsString());
        }
        return subDto;
    }
    @Override
    public int updateFailReason(String merchantUid, String failReason) {
        return subscribeDao.updateFailReason(merchantUid, failReason);
    }
    @Override
    public int getPrice(int selectMonth) {

        int getPrice = 0;
        if (selectMonth == Subscribe.ONE.getSelectMonth()) {
            getPrice = Subscribe.ONE.getPrice();
        } else if (selectMonth == Subscribe.THREE.getSelectMonth()) {
            getPrice = Subscribe.THREE.getPrice();
        } else if (selectMonth == Subscribe.SIX.getSelectMonth()) {
            getPrice = Subscribe.SIX.getPrice();
        } else if (selectMonth == Subscribe.TWELVE.getSelectMonth()) {
            getPrice = Subscribe.TWELVE.getPrice();
        } else {
            getPrice = -1;
        }
        return getPrice;
    }
    private String getDate() {
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HHmmssSSS");
        String date = today.format(formatter);

        return date;
    }

}
