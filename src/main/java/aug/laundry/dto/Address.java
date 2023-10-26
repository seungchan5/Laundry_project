package aug.laundry.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Address {

    private String zipcode;
    private String memberAddress;
    private String memberAddressDetails;


    public Address(String zipcode, String memberAddress, String memberAddressDetails) {
        this.zipcode = zipcode;
        this.memberAddress = memberAddress;
        this.memberAddressDetails = memberAddressDetails;
    }
}
