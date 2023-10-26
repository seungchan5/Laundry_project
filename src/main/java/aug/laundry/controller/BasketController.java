package aug.laundry.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
//@RequestMapping("/basket/*")
public class BasketController {

    @GetMapping("/basket/order")
    public String order(){
        return "project_order_1";
    }

    @GetMapping("/basket/dry")
    public String drycleaning(){
        return "project_order_drycleaning";
    }

    @GetMapping("/basket/repair")
    public String repair(){
        return "project_order_repair";
    }


}
