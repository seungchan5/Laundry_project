package aug.laundry.service;

import aug.laundry.dao.basket.RepairMapper;
import aug.laundry.dto.RepairDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepairServiceImpl implements RepairService {

    @Autowired
    RepairMapper mapper;

    @Override
    public int insert(RepairDto dto) {
        return mapper.insert(dto);
    }
}
