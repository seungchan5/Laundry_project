package aug.laundry.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Getter
@Setter
@ToString
public class OrderPost {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH")
    private LocalDateTime takeDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH")
    private LocalDateTime deliveryDate;

    @NotNull
    private String zipcode;

    @NotNull
    private String address;

    @NotNull
    private String addressDetails;
    private String location;
    private Long coupon;
    private String isPw;
    private String password;
    private String request;

    public OrderPost(LocalDateTime takeDate, LocalDateTime deliveryDate, String zipcode, String address, String addressDetails, String location, Long coupon, String isPw, String password, String request) {
        this.takeDate = takeDate;
        this.deliveryDate = deliveryDate;
        this.zipcode = zipcode;
        this.address = address;
        this.addressDetails = addressDetails;
        this.location = location;
        this.coupon = coupon;
        this.isPw = isPw;
        this.password = password;
        this.request = request;
    }

}
