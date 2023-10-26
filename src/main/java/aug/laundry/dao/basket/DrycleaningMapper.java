package aug.laundry.dao.basket;

import aug.laundry.dto.DrycleaningDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DrycleaningMapper {
   public int insert(DrycleaningDto dto);
}
