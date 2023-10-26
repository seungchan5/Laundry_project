package aug.laundry.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Grade {

    private Long gradeId;
    private String gradeName;

    public Grade() {
    }
}
