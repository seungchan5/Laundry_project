package aug.laundry.dao;

import aug.laundry.dto.FileUploadDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class FileUploadDao {

    private final FileUploadMapper fileUploadMapper;

    public int saveImage(FileUploadDto file) {
        return fileUploadMapper.saveImage(file);
    }

}
