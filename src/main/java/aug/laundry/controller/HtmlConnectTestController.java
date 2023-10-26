package aug.laundry.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HtmlConnectTestController {

    @GetMapping("/test/1")
    public String http1() {
        return "project_login";
    }

    @GetMapping("/test/2")
    public String http2() {
        return "project_change_password";
    }

    @GetMapping("/test/3")
    public String http3() {
        return "project_coupon";
    }

    @GetMapping("/test/4")
    public String http4() {
        return "project_invite";
    }

    @GetMapping("/test/5")
    public String http5() {
        return "project_register";
    }

    @GetMapping("/test/6")
    public String http6() {
        return "project_search_id";
    }

    @GetMapping("/test/7")
    public String http7() {
        return "project_search_id_result";
    }

    @GetMapping("/test/8")
    public String http8() {
        return "project_search_password";
    }

    @GetMapping("/test/9")
    public String http9() {
        return "project_order_confirm";
    }

    @GetMapping("/test/10")
    public String http10() {
        return "project_update_address";
    }

    @GetMapping("/test/11")
    public String http11() {
        return "project_update_phone";
    }

    @GetMapping("/test/12")
    public String http12() {
        return "project_order_1";
    }

    @GetMapping("/test/13")
    public String http13() {
        return "project_order_2_1";
    }

    // 수선 위에 수선에 대한 이미지 하나만 채워주세요.
    @GetMapping("/test/14")
    public String http14() {
        return "project_order_2_2";
    }
    @GetMapping("/test/15")
    public String http15() {
        return "project_point";
    }

    // 포인트에 이미지 윤곽드러나는거 확인해주세요.
    @GetMapping("/test/16")
    public String http16() {
        return "project_use_point";
    }
    @GetMapping("/test/17")
    public String http17() {
        return "project_use_coupon";
    }

    // css 확인아직 못함
    @GetMapping("/test/18")
    public String http18() {
        return "project_order_view";
    }

    @GetMapping("/test/19")
    public String http19() {
        return "project_pickup_location";
    }

    @GetMapping("/test/20")
    public String http20() {
        return "project_mypage";
    }

    @GetMapping("/test/21")
    public String http21() {
        return "project_mypage_list";
    }

    // css 확인 못함
    @GetMapping("/test/22")
    public String http22() { return "project_rider_list_on_call"; }

    @GetMapping("/test/23")
    public String http23() {
        return "project_rider_using_list";
    }
    @GetMapping("/test/24")
    public String http24() {
        return "project_rider_complete";
    }

    @GetMapping("/test/25")
    public String http25() {
        return "project_rider_read_more";
    }

    @GetMapping("/test/26")
    public String http26() {
        return "project_manager_order_list";
    }
    @GetMapping("/test/27")
    public String http27() {
        return "project_manager_order_list_complete";
    }

    @GetMapping("/test/28")
    public String http28() {
        return "project_manager_order_detail";
    }

    @GetMapping("/test/29")
    public String http29() {
        return "project_main";
    }

    @GetMapping("/test/30")
    public String http30() {
        return "project_price_tag";
    }
}