package aug.laundry.dto;

import aug.laundry.domain.Drycleaning;
import aug.laundry.domain.Repair;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
public class InspectionDataDto {

    @NotNull
    Long inspectionId;
    @Valid
    AdminCommonLoundryDto commonLaundryDto ;
    @Valid
    private List<AdminDrycleaningDto> drycleaningList;
    @Valid
    private List<AdminRepairDto> repairList;
    private List<String> deleteFileList;

    public InspectionDataDto() {

    }

}
