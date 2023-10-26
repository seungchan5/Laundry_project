package aug.laundry.ValidatorTest;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validator {

    @Test
    void validate() {
        String date = "2023-09-04 11";
        String regex = "[0-9]{4}(-)[0-9]{2}(-)[0-9]{2}( )[0-9]{2}";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(date);
        if (m.find()) {
            System.out.println(m.group());
        }

    }

    @Test
    void ConverTest() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String string = localDateTime.toString();
        System.out.println("string = " + string);
    }
}
