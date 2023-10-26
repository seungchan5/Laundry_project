package aug.laundry.validator;

import aug.laundry.dto.OrderPost;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;

@Component
public class OrderPostValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return OrderPost.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OrderPost order = (OrderPost) target;

        System.out.println("validate Order = " + order);
        // DateTime 검증
        if (order.getTakeDate() != null && order.getDeliveryDate() != null) {
            // 빠른세탁은 시간 데이터가 null임
            // DB에서 sysdate 가 사용됨

            if (order.getTakeDate().isBefore(LocalDateTime.now()) || order.getDeliveryDate().isBefore(LocalDateTime.now())) {
                // 수거시간이나 배송시간이 현재보다 이전일때
                errors.reject("date", "IllegalDateValue");
            }
            if (order.getTakeDate().isAfter(LocalDateTime.now().plusDays(5)) || order.getDeliveryDate().isAfter(LocalDateTime.now().plusDays(8))) {
                // 수거시간이 현재+5일보다 이후 일떄, 배송시간이 현재+8일보다 이후 일때
                errors.reject("date", "exceedDateValue");
            }
            if (order.getTakeDate().isAfter(order.getDeliveryDate()) || order.getDeliveryDate().isBefore(order.getTakeDate())) {
                // 수거시간이 배송시간보다 이후이거나 배송시간이 수거시간보다 이전일때
                errors.reject("date", "reject");
            }
        }

        if (order.getAddress().equals("") || order.getAddress() == null) {
            errors.reject("address", "invalidAddress");
        }
        if (order.getAddressDetails().equals("") || order.getAddressDetails() == null) {
            errors.reject("addressDetails", "invalidAddressDetails");
        }

        if (order.getIsPw() == null || (!order.getIsPw().equals("X") && !order.getIsPw().equals("O"))) {
            errors.reject("pw", "not exist isPw");
        }

        if (order.getIsPw().equals("O") && (order.getPassword() == null || order.getPassword().equals(""))) {
            errors.reject("pw", "not exist Password");
        }
        if (order.getIsPw().equals("X") && (order.getPassword() != null && !order.getPassword().equals(""))) {
            errors.reject("pw", "invalid Password");
        }
//


    }
}
