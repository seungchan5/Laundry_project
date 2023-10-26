package aug.laundry.dao.point;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PointDao {

    private final PointMapper pointMapper;

    public Integer findByMemberId(Long memberId) {
        return pointMapper.findByMemberId(memberId);
    }
    public int registerPoint(Long memberId) { return pointMapper.registerPoint(memberId); }

    public int addRecommandPoint(Long memberId, int point, String reason){return pointMapper.addRecommandPoint(memberId, point,reason); }

    public int addBonusPoint(Long memberId, Long pointStack){
        return pointMapper.addBonusPoint(memberId, pointStack);
    }
}
