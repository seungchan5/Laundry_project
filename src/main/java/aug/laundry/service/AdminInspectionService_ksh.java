package aug.laundry.service;

import aug.laundry.domain.CommonLaundry;
import aug.laundry.domain.Drycleaning;
import aug.laundry.domain.Repair;
import aug.laundry.dto.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public interface AdminInspectionService_ksh {
    List<AdminInspectionDto> getInspectionList(Criteria cri, Long orderStatus);

    int getTotalCount(Long ordersStatus);

    Map<String, Object> getInspectionDetail(Long ordersId);

    AdminInspectionDto getOrderSearchInfo(Long ordersId, Long ordersStatus);

    void updateInspectionResult(Long adminId, Long ordersId, InspectionDataDto inspectionDataDto,
                               List<MultipartFile> files);

    Map<String, String> deleteImageFile(List<String> fileNames);
    Long getMemberId(Long ordersId);
}
