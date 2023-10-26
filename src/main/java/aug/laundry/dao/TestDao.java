package aug.laundry.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TestDao {

    private final TestMapper testMapper;

    public int find(){
        return testMapper.find();
    }



    



}

