package aug.laundry.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Slf4j
@Aspect
@Component
public class FooterAspect {

    @Before("execution(* aug.laundry.controller.MypageController_osc.*(..))")
    public void footerMyPageAspect(JoinPoint joinPoint) {

        log.info("MyPageController Aspect Before 실행 : {}", joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof Model){
                ((Model) arg).addAttribute("footer", "mypage");
            }
            if (arg instanceof Long) {

                Long memberId = (Long) arg;
                if (memberId == null) {
                    // 자동 로그인을 Application 형태로 바꾸면 여기에다가 추가작성해야됨
                }

            }
        }
    }

    @Before("execution(* aug.laundry.controller.MainController.*(..))")
    public void mainpageAspect(JoinPoint joinPoint) {
        log.info("MainPageController Aspect Before 실행 : {}", joinPoint.getSignature().getName());

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof Model) {
                ((Model) arg).addAttribute("footer", "main");
            }

        }
    }

    @Before("execution(* aug.laundry.controller.OrdersController.*(..))")
    public void orderListAspect(JoinPoint joinPoint) {
        log.info("OrdersController Aspect Befor 실행 : {}", joinPoint.getSignature().getName());

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof Model) {
                ((Model) arg).addAttribute("footer", "orderList");
            }
        }
    }

    @Before("execution(* aug.laundry.controller.RiderController.*routine*(..))")
    public void rider(JoinPoint joinPoint) {
        System.out.println("RiderController실행----------------------------------");

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof Model) {
                ((Model) arg).addAttribute("head", "routine");
            }
        }
    }

    @Before("execution(* aug.laundry.controller.AdminInspectionController.*view*(..))")
    public void admin(JoinPoint joinPoint) {
        log.info("AdminInspectionController실행");

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof Model) {
                ((Model) arg).addAttribute("head", "view");
            }
        }
    }
}
