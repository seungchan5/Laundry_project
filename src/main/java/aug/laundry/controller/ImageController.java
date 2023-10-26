package aug.laundry.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;

@Controller
@Slf4j
public class ImageController {

    @GetMapping("/images/{imageName}")
    @ResponseBody
    public byte[] getImage(@PathVariable String imageName) throws IOException {
        Resource resource = new ClassPathResource("static/images/" + imageName);
        log.info("getImage Controller ");
        return Files.readAllBytes(resource.getFile().toPath());
    }
}
