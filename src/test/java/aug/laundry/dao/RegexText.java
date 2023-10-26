package aug.laundry.dao;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexText {

  @Test
  void regexText() {

    String num = "010-3567-4431";
    String regex = "(01)([016789]{1})(-)([0-9]{3,4})(-)([0-9]{4})";
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(num);
    if (m.find()) {
      System.out.println(m.group());
    }

  }
}
