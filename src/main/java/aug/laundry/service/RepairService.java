package aug.laundry.service;

import aug.laundry.dto.RepairDto;
import org.springframework.stereotype.Service;

@Service
public interface RepairService {

    public int insert(RepairDto dto);
}
