package aug.laundry.dao.basket;

import aug.laundry.dto.CommonLaundryDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonLaundryMapper {
    public int insert(CommonLaundryDto dto);
}
