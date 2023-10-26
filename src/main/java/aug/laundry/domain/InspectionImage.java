package aug.laundry.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InspectionImage {

    private Long inspectionImageId;
    private Long inspectionId;
    private String inspectionImageUploadName;
    private String inspectionImageStoreName;

    public InspectionImage() {
    }
}
