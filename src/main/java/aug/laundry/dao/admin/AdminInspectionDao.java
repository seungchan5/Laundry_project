package aug.laundry.dao.admin;

import aug.laundry.domain.*;
import aug.laundry.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class AdminInspectionDao {

    private final AdminInspectionMapper adminInspectionMapper;

    public List<AdminInspectionDto> getInspectionList(Criteria cri, Long orderStatus) {
        return adminInspectionMapper.getInspectionList(cri, orderStatus);
    }
    public int getTotalCount(Long ordersStatus){
        return adminInspectionMapper.getTotalCount(ordersStatus);
    }

    public AdminInspectionDto getOrderInfo(Long ordersId) {
        return adminInspectionMapper.getOrderInfo(ordersId);
    }

    public AdminCommonLoundryDto getCommonLaundryInfo(Long orderDetailId) {
        return adminInspectionMapper.getCommonLaundryInfo(orderDetailId);
    }

    public List<AdminDrycleaningDto> getDrycleaningInfo(Long orderDetailId) {
        return adminInspectionMapper.getDrycleaningInfo(orderDetailId);
    }

    public List<AdminRepairDto> getRepairInfo(Long orderDetailId) {
        return adminInspectionMapper.getRepairInfo(orderDetailId);
    }
    public List<String> getRepairImage(Long repairId){
        return adminInspectionMapper.getRepairImage(repairId);
    }
    public int updateCommon(AdminCommonLoundryDto commonLaundryDto){
        return adminInspectionMapper.updateCommon(commonLaundryDto);
    }
    public int updateRepair(AdminRepairDto repair){
        return adminInspectionMapper.updateRepair(repair);
    }
    public int updateDrycleaning(AdminDrycleaningDto drycleaning){
        return adminInspectionMapper.updateDrycleaning(drycleaning);
    }
    public int updateInspectionStatus(Long ordersId, Long adminId){
        return adminInspectionMapper.updateInspectionStatus(ordersId, adminId);
    }
    public int updateOrderStatus(Long ordersId){
        return adminInspectionMapper.updateOrderStatus(ordersId);
    }
    public AdminInspectionDto getOrderSearchInfo(Long ordersId, Long ordersStatus) {
        return adminInspectionMapper.getOrderSearchInfo(ordersId, ordersStatus);
    }
    public List<InspectionImage> getInspectionImageList(Long ordersId){
        return adminInspectionMapper.getInspectionImageList(ordersId);
    }

    public int deleteImage(String inspectionImageStoreName) {
        return adminInspectionMapper.deleteImage(inspectionImageStoreName);
    }

    public Long getMemberId(Long ordersId) {
        return adminInspectionMapper.getMemberId(ordersId);
    }

}
