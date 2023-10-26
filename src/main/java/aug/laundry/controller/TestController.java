package aug.laundry.controller;

import aug.laundry.dao.TestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final TestMapper testMapper;
//    @GetMapping("/test")
//    public String test(Model model) {
//
//        int result = testMapper.find();
//        log.info("result");
//
//        model.addAttribute("result", result);
//        return "test";
//    }

}
