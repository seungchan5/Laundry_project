package aug.laundry.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FileUploadDto {

    private Long imageId;
    private Long id;
    private String imageUploadName;
    private String imageStoreName;
    private String tableType;
    public FileUploadDto() {
    }
}
