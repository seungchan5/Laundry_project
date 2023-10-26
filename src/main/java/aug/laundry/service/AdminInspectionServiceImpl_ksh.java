package aug.laundry.service;

import aug.laundry.dao.admin.AdminInspectionDao;
import aug.laundry.domain.CommonLaundry;
import aug.laundry.domain.Drycleaning;
import aug.laundry.domain.InspectionImage;
import aug.laundry.domain.Repair;
import aug.laundry.dto.*;
import aug.laundry.enums.category.Category;
import aug.laundry.enums.fileUpload.FileUploadType;
import aug.laundry.enums.repair.RepairCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminInspectionServiceImpl_ksh implements AdminInspectionService_ksh{

    @Value("${file.dir}")
    private String fileDir;

    private final AdminInspectionDao adminInspectionDao;
    private final FileUploadService_ksh fileUpload;

    @Override
    public List<AdminInspectionDto> getInspectionList(Criteria cri, Long orderStatus) {
        return adminInspectionDao.getInspectionList(cri, orderStatus);
    }

    @Override
    public int getTotalCount(Long ordersStatus) {
        return adminInspectionDao.getTotalCount(ordersStatus);
    }

    @Override
    public Map<String, Object> getInspectionDetail(Long ordersId){

        Map<String, Object> detailInfo = new HashMap<>();

        Long orderDetailId = adminInspectionDao.getOrderInfo(ordersId).getOrdersDetailId();

        detailInfo.put("orderDetailId", orderDetailId);

        detailInfo.put("orderInfo", adminInspectionDao.getOrderInfo(ordersId));

        AdminCommonLoundryDto commonLaundry = adminInspectionDao.getCommonLaundryInfo(orderDetailId);
        if(commonLaundry == null){
            detailInfo.put("commonLaundryInfo", null);
        } else {
            detailInfo.put("commonLaundryInfo", commonLaundry);
        }

        List<AdminDrycleaningDto> drycleaningInfo = adminInspectionDao.getDrycleaningInfo(orderDetailId);
        if(drycleaningInfo.size() == 0){
            detailInfo.put("drycleaningInfo", null);
        } else {
            for (AdminDrycleaningDto drycleaning : drycleaningInfo) {
                drycleaning.setDrycleaningCategory(Category.valueOf(drycleaning.getDrycleaningCategory()).getTitle());
            }
            detailInfo.put("drycleaningInfo", drycleaningInfo);
        }

        List<AdminRepairDto> repairInfo = adminInspectionDao.getRepairInfo(orderDetailId);

        // 수선 주문에 대한 이미지
        List<RepairInfoDto> repairInfoDtos = repairInfo.stream().map(repair -> {
            RepairInfoDto repairInfoDto = new RepairInfoDto();
            repairInfoDto.setRepairId(repair.getRepairId());
            repairInfoDto.setOrdersDetailId(repair.getOrdersDetailId());
            repairInfoDto.setRepairRequest(repair.getRepairRequest());
            repairInfoDto.setRepairCategory(repair.getRepairCategory());
            repairInfoDto.setRepairPossibility(repair.getRepairPossibility());
            repairInfoDto.setRepairNotReason(repair.getRepairNotReason());
            repairInfoDto.setRepairImageStoreName(adminInspectionDao.getRepairImage(repair.getRepairId()));
            return repairInfoDto;
        }).collect(Collectors.toList());

        if(repairInfoDtos.size() == 0){
            detailInfo.put("repairInfo", null);
        } else {
            for (RepairInfoDto repairInfoDto : repairInfoDtos) {
                repairInfoDto.setRepairCategory(RepairCategory.valueOf(repairInfoDto.getRepairCategory()).getTitle());
            }
            detailInfo.put("repairInfo", repairInfoDtos);
        }

        List<InspectionImage> imageList = adminInspectionDao.getInspectionImageList(ordersId);
        if(imageList.size() == 0){
            detailInfo.put("imageList", null);
        } else {
            detailInfo.put("imageList", imageList);
        }

        return detailInfo;
    }

    @Override
    public AdminInspectionDto getOrderSearchInfo(Long ordersId, Long ordersStatus) {
        return adminInspectionDao.getOrderSearchInfo(ordersId, ordersStatus);
    }

    @Transactional
    @Override
    public void updateInspectionResult(Long adminId, Long ordersId, InspectionDataDto inspectionDataDto,
                                      List<MultipartFile> files){

        if(inspectionDataDto.getCommonLaundryDto() != null) {
            adminInspectionDao.updateCommon(inspectionDataDto.getCommonLaundryDto());
        }
        if(inspectionDataDto.getRepairList() != null) {
           inspectionDataDto.getRepairList().forEach(repair -> adminInspectionDao.updateRepair(repair));
        }

        if(inspectionDataDto.getDrycleaningList() != null) {
            inspectionDataDto.getDrycleaningList().forEach(drycleaning -> adminInspectionDao.updateDrycleaning(drycleaning));
        }

        if (inspectionDataDto.getDeleteFileList() != null) {
            inspectionDataDto.getDeleteFileList().forEach(fileName -> adminInspectionDao.deleteImage(fileName));
        }

        adminInspectionDao.updateInspectionStatus(ordersId, adminId);
        adminInspectionDao.updateOrderStatus(ordersId);

        if (files != null) {
            fileUpload.saveFile(files, inspectionDataDto.getInspectionId(), FileUploadType.INSPECTION);
        }
    }

    @Override
    public Map<String, String> deleteImageFile(List<String> fileNames) {

        Map<String, String> map = new HashMap<>();

        fileNames.forEach(fileName -> {
            int index=0;
            // 경로 지정
            Path imagePath = Paths.get(fileDir+fileName);
            try {
                Files.deleteIfExists(imagePath);
            } catch (IOException e) {
                map.put("result", "fail");
                map.put("error"+index, fileName+"="+e.getMessage());
            }
        });

        return map;
    }

    @Override
    public Long getMemberId(Long ordersId) {
        return adminInspectionDao.getMemberId(ordersId);
    }

}
