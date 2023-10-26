package aug.laundry.service;

import aug.laundry.dao.FileUploadDao;
import aug.laundry.dto.FileUploadDto;
import aug.laundry.enums.fileUpload.FileUploadType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileUploadService_ksh {

    @Value("${file.dir}")
    private String fileDir;

    private final FileUploadDao fileDao;

    public int saveFile(List<MultipartFile> files, Long id, FileUploadType fileUpload) {

        int res = 0;

        for (MultipartFile file : files) {
            try {
                String contentType = file.getContentType();
                byte[] fileBytes = file.getBytes();

                // MIME 타입 이미지 파일인 경우에만
                if (contentType != null && contentType.startsWith("image/") && imageFile(fileBytes)) {
                    // 첨부파일 저장
                    if (!file.isEmpty()) {
                        String originalFilename = file.getOriginalFilename();
                        String storeFileName = createFileName(originalFilename);

                        file.transferTo(new File(getFullPath(storeFileName, fileUpload.getfolderName())));

                        // 데이터베이스 등록
                        FileUploadDto fileDto = new FileUploadDto();
                        fileDto.setImageUploadName(originalFilename);
                        fileDto.setImageStoreName(storeFileName);
                        fileDto.setId(id);
                        fileDto.setTableType(fileUpload.getTableType());
                        res += fileDao.saveImage(fileDto);
                    }
                } else {
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return res;
    }

    public String getFullPath(String fileName, String folderName) {

        return fileDir +folderName+"/"+ fileName;
    }

    private boolean imageFile(byte[] fileBytes) {
        if(fileBytes.length>=2 && fileBytes[0] == (byte) 0xFF && fileBytes[1] == (byte) 0xD8 ) {
            // JPEG
            return true;
        } else if(fileBytes.length >= 3 && fileBytes[0] == (byte) 'G' && fileBytes[1] == (byte) 'I' && fileBytes[2] == (byte) 'F') {
            // GIF
            return true;
        } else if(fileBytes.length >= 8 && fileBytes[0] == (byte) 0x89 && fileBytes[1] == (byte) 0x50 &&
                fileBytes[2] == (byte) 0x4E && fileBytes[3] == (byte) 0x47 && fileBytes[4] == (byte) 0x0D &&
                fileBytes[5] == (byte) 0x0A && fileBytes[6] == (byte) 0x1A && fileBytes[7] == (byte) 0x0A) {
            // PNG
            return true;
        }else return false;
    }

    private String createFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();

        int pos = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(pos+1);

        return uuid + "." +ext;
    }

}
