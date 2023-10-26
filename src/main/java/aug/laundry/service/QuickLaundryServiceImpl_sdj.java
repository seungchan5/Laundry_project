package aug.laundry.service;

import aug.laundry.dao.basket.QuickLaundryMapper;
import aug.laundry.dto.QuickLaundryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuickLaundryServiceImpl_sdj implements QuickLaundryService_sdj{

    @Autowired
    QuickLaundryMapper mapper;

    @Override
    public int insert(QuickLaundryDto dto) {
        return mapper.insert(dto);
    }
}
