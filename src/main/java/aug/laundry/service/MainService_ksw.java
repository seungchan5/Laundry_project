package aug.laundry.service;

import aug.laundry.dao.MainMapper;
import aug.laundry.dto.OrdersEnum2;
import aug.laundry.enums.category.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service

public class MainService_ksw implements MainService{

    private final MainMapper mainMapper;

    private final Map<String, Map<String, Long>> priceTag;

    @Autowired
    public MainService_ksw(MainMapper mainMapper, Map<String, Map<String, Long>> priceTag) {
        this.mainMapper = mainMapper;
        Set<Category> parentCategoryAll = Category.getParentCategoryAll();
        for (Category category : parentCategoryAll) priceTag.put(category.getTitle(), category.getChildCategories());
        this.priceTag = Collections.unmodifiableMap(priceTag);
    }

    @Override
    public List<OrdersEnum2> getOrders(Long memberId) {
        return mainMapper.getOrders(memberId);
    }

    @Override
    public Map<String, Map<String, Long>> getCategory() {
        return priceTag;
    }
}
