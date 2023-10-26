package aug.laundry.dao.subscribe;

import aug.laundry.dto.SubscriptionPayDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class SubscribeDao {

    private final SubscribeMapper subscribeMapper;

    public int insertJoinSubscribe(SubscriptionPayDto subData){
        return subscribeMapper.insertJoinSubscribe(subData);
    }

    public int updateMemberSubscribe(SubscriptionPayDto subData){
        return subscribeMapper.updateMemberSubscribe(subData);
    }

    public int updateNextMerchantId(String merchantUid, String merchantUidRe){
        return subscribeMapper.updateNextMerchantId(merchantUid, merchantUidRe);
    }

    public SubscriptionPayDto getScheduleInfo(Long customerUid) {
        return subscribeMapper.getScheduleInfo(customerUid);
    }

    public int updateCancel(String merchantUid){
        return subscribeMapper.updateCancel(merchantUid);
    }

    public int getRepayCount(String merchantUid){
        return subscribeMapper.getRepayCount(merchantUid);
    }

    public int updateRepayCount(String merchantUid) {
        return subscribeMapper.updateRepayCount(merchantUid);
    }

    public SubscriptionPayDto getSubscribeInfo(Long memberId) {
        return subscribeMapper.getSubscribeInfo(memberId);
    }

    public int updateFailReason(String merchantUid, String failReason) {
        return subscribeMapper.updateFailReason(merchantUid, failReason);
    }
}
