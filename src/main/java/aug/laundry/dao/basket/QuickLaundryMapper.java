package aug.laundry.dao.basket;

import aug.laundry.dto.QuickLaundryDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuickLaundryMapper {
    public int insert(QuickLaundryDto dto);
}
