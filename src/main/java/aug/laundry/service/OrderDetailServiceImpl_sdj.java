package aug.laundry.service;

import aug.laundry.dao.basket.OrderDetailMapper;
import aug.laundry.dto.OrdersDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl_sdj implements OrderDetailService_sdj {

    @Autowired
    OrderDetailMapper mapper;

    @Override
    public int insert(OrdersDetailDto dto) {
        return mapper.insert(dto);
    }
}
