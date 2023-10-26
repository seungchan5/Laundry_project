package aug.laundry.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class CancelData {

    private String imp_uid;
    private String merchant_uid;
    private BigDecimal amount;
}
