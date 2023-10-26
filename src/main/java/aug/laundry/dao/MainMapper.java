package aug.laundry.dao;

import aug.laundry.dto.OrdersEnum;
import aug.laundry.dto.OrdersEnum2;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MainMapper {

    List<OrdersEnum2> getOrders(Long memberId);
}
