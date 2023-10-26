package aug.laundry.dao;

import aug.laundry.dto.FileUploadDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileUploadMapper {

    int saveImage(FileUploadDto file);
}
