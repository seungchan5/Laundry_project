package aug.laundry.dao.admin;

import aug.laundry.domain.*;
import aug.laundry.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminInspectionMapper {

    List<AdminInspectionDto> getInspectionList(@Param("cri") Criteria cri, @Param("orderStatus") Long orderStatus);
    int getTotalCount(Long ordersStatus);
    // 주문정보조회
    AdminInspectionDto getOrderInfo(Long ordersId);
    // 생활빨래
    AdminCommonLoundryDto getCommonLaundryInfo(Long orderDetailId);
    // 드라이클리닝
    List<AdminDrycleaningDto> getDrycleaningInfo(Long orderDetailId);
    //수선
    List<AdminRepairDto> getRepairInfo(Long orderDetailId);
    List<String> getRepairImage(Long repairId);
    AdminInspectionDto getOrderSearchInfo(@Param("ordersId") Long ordersId,@Param("ordersStatus") Long ordersStatus);
    List<InspectionImage> getInspectionImageList(Long ordersId);
    int updateCommon(AdminCommonLoundryDto commonLaundryDto);
    int updateRepair(AdminRepairDto repair);
    int updateDrycleaning(AdminDrycleaningDto drycleaning);
    int updateInspectionStatus(@Param("ordersId") Long ordersId, @Param("adminId")Long adminId);
    int updateOrderStatus(Long ordersId);
    int deleteImage(String inspectionImageStoreName);
    Long getMemberId(Long ordersId);
}
