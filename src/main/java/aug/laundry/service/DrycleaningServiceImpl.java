package aug.laundry.service;


import aug.laundry.dao.basket.DrycleaningMapper;
import aug.laundry.dto.DrycleaningDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrycleaningServiceImpl implements DrycleaningService {


    @Autowired
    DrycleaningMapper mapper;

    @Override
    public int insert(DrycleaningDto dto) {
        return mapper.insert(dto);

    }
}
