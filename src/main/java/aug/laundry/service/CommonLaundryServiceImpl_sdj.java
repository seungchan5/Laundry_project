package aug.laundry.service;

import aug.laundry.dao.basket.CommonLaundryMapper;
import aug.laundry.dto.CommonLaundryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonLaundryServiceImpl_sdj implements CommonLaundryService_sdj {

    @Autowired
    CommonLaundryMapper mapper;

    @Override
    public int insert(CommonLaundryDto dto) {
        return mapper.insert(dto);
    }
}
