package aug.laundry.category;

import aug.laundry.enums.category.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class categoryGetNameTest {

    @Test
    void getName() {
        Optional<Category> category = Category.findByTitle("실크이불");
//        Assertions.assertThat(category).isEmpty();
        Category category1 = category.get();
        String name = category1.name();
        System.out.println("name = " + name);
        System.out.println("category1 = " + category1);
    }
}
