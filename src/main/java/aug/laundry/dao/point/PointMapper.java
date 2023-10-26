package aug.laundry.dao.point;

import aug.laundry.domain.Drycleaning;
import aug.laundry.domain.Repair;
import aug.laundry.dto.OrdersResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PointMapper {

    Integer findByMemberId(Long memberId);
    int registerPoint(Long memberId);
    int addRecommandPoint(@Param("memberId") Long memberId, @Param("point") int point, @Param("reason") String reason);

    int addBonusPoint(@Param("memberId") Long memberId, @Param("point") Long point);

}
