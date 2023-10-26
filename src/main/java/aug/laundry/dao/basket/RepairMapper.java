package aug.laundry.dao.basket;

import aug.laundry.dto.RepairDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RepairMapper {
    public int insert (RepairDto dto);
}
