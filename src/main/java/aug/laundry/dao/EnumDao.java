package aug.laundry.dao;

import aug.laundry.enums.category.Category;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class EnumDao {

    private final ObjectMapperFactory objectMapperFactory;

    // 하위 카테고리를 JSON 형식으로 반환
    public String getJson(Category category) throws JsonProcessingException {
        return objectMapperFactory.getObjectMapper().writeValueAsString(category.getChildCategories());
    }

    // 오버로딩된 getJson
    public String getJson(Map<String, Long> category) throws JsonProcessingException {
        return objectMapperFactory.getObjectMapper().writeValueAsString(category);
    }

}
