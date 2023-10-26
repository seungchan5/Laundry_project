package aug.laundry.dao.basket;

import aug.laundry.dto.OrdersDetailDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDetailMapper {

    public int insert (OrdersDetailDto dto);
}
