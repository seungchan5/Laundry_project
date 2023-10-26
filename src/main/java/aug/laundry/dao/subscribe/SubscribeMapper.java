package aug.laundry.dao.subscribe;

import aug.laundry.dto.SubscriptionPayDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SubscribeMapper {

    int insertJoinSubscribe(SubscriptionPayDto subData);
    int updateMemberSubscribe(SubscriptionPayDto subData);
    int updateNextMerchantId(@Param("merchantUid") String merchantUid, @Param("merchantUidRe") String merchantUidRe);
    SubscriptionPayDto getScheduleInfo(Long customerUid);
    int updateCancel(String merchantUid);// 구독취소 업데이트
    int getRepayCount(String merchantUid);
    int updateRepayCount(String merchantUid);
    int updateFailReason(@Param("merchantUid") String merchantUid, @Param("failReason") String failReason);
    SubscriptionPayDto getSubscribeInfo(Long memberId);

}
