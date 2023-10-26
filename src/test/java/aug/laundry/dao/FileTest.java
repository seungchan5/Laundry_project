package aug.laundry.dao;

import aug.laundry.dto.FileUploadDto;
import aug.laundry.enums.fileUpload.FileUploadType;
import aug.laundry.service.FileUploadService_ksh;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class FileTest {

    @Autowired
    private FileUploadMapper fileUploadMapper;

    @Autowired
    private FileUploadService_ksh fileUploadService;

    @Test
    public void fileSaveTest() {
        FileUploadDto file = new FileUploadDto();
        file.setId(4L);
        file.setImageStoreName("imageTest5");
        file.setImageUploadName("imageTest5");
        file.setTableType(FileUploadType.INSPECTION.getTableType());

        int res = fileUploadMapper.saveImage(file);

        Assertions.assertThat(res).isEqualTo(1);
    }

    @Test
    public void fileServiceTest() {
        byte[] testFile = new byte[]{ (byte) 0xFF, (byte) 0xD8 };

        MultipartFile dummyFile = new MockMultipartFile(
                "dummy77.jpg", // 파일 이름
                "dummy77.jpg", // 오리지널 파일 이름
                "image/jpeg", // 컨텐츠 타입
                testFile
        );

        List<MultipartFile> multipartFiles = new ArrayList<>();
        multipartFiles.add(dummyFile);

        int res = fileUploadService.saveFile(multipartFiles, 4L, FileUploadType.INSPECTION);

        Assertions.assertThat(res).isEqualTo(1);
    }
}
