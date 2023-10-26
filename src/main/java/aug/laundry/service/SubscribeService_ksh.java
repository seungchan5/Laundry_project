package aug.laundry.service;

import aug.laundry.dto.SubscriptionPayDto;
import com.google.gson.JsonObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface SubscribeService_ksh {

    int saveSubscribe(SubscriptionPayDto subData);
    String getAccessToken();
    JsonObject postData(String requestUrl, String jsonBody) throws IOException;
    JsonObject getData(String requestUrl) throws IOException;
    int updateNextMerchantId(String merchantUid, String merchantUidRe);
    SubscriptionPayDto getScheduleInfo(Long customerUid);
    int updateCancel(String merchantUid);
    String schedulePay(SubscriptionPayDto subDto, long timeStamp) throws IOException;
    String refund(String impUid, int payPrice) throws IOException;
    int getRepayCount(String merchantUid);
    int updateRepayCount(String merchantUid);
    SubscriptionPayDto getSubscribeInfo(Long memberId);
    SubscriptionPayDto validationPay(String imp_uid) throws IOException;
    int updateFailReason(String merchantUid,String failReason);
    int getPrice(int selectMonth);
}
