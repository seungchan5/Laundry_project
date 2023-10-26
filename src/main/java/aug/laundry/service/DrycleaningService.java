package aug.laundry.service;

import aug.laundry.dto.DrycleaningDto;
import org.springframework.stereotype.Service;

@Service
public interface DrycleaningService {

    int insert(DrycleaningDto dto);
}
