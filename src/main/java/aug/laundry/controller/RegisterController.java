package aug.laundry.controller;

import aug.laundry.dto.MemberDto;
import aug.laundry.service.MemberService_kgw;
import aug.laundry.service.SendSmsService_kgw;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final MemberService_kgw memberService;

    @ResponseBody
    @GetMapping("/id/check/{memberAccount}")
    public Map<String, Object> idCheck(@PathVariable String memberAccount){
        Map<String, Object> msg = new HashMap<>();
        int res = memberService.checkId(memberAccount);

        if(res <= 0){
            msg.put("res", res);
            msg.put("msg", "사용가능한 아이디입니다.");
        }else{
            msg.put("res", res);
            msg.put("msg", "아이디가 중복됩니다.");
        }


        return msg;
    }

    @RequestMapping(value = "/registerAction", method = {RequestMethod.POST})
    public String registerUser(@Valid MemberDto memberDto, BindingResult bindingResult, Model model) {
        System.out.println(memberDto.toString());
        Map<String, String> validation = new HashMap<>();
        String phonenumber = memberDto.getMemberPhone().replace("-","");
        if(memberService.getPhoneCnt(phonenumber) > 0){
            model.addAttribute("msg", "이미 등록된 휴대폰 번호입니다.");
            return "project_register";
        }
        if (bindingResult.hasErrors()) {
            //throw new IllegalArgumentException("validation 실패");

            System.out.println("spring validation 실패!");
            validation.put("validation", "실패");
            bindingResult.getAllErrors()
                    .forEach(objectError -> {
                        System.err.println("code : " + objectError.getCode());
                        System.err.println("defaultMessage : " + objectError.getDefaultMessage());
                        System.err.println("objectName : " + objectError.getObjectName());
                    });
            return "/register";

        } else {
            validation.put("validation", "성공");
            memberService.registerUser(memberDto);
        }

        return "redirect:/login";

    }

    @ResponseBody
    @GetMapping("/inviteCode/{inviteCode}")
    public Map<String, Object> getInviteCode(@PathVariable String inviteCode){
        int res = memberService.inviteCodeCheck(inviteCode);
        Map<String, Object> map = new HashMap<>();
        if(res > 0){
            map.put("result", res);
            map.put("resultMsg", "사용가능한 코드입니다.");
        }else{
            map.put("result", res);
            map.put("resultMsg", "코드를 다시 확인해주세요.");
        }

        return map;
    }

    @GetMapping("/register")
    public String goRegister(){
        return "project_register";
    }


}
