package aug.laundry.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ObjectMapperFactory {

    // 스레드 환경에서 동시성문제를 해결하기위해
    // 스레드 로컬 변수를 사용해서 스레므마다 별도의 인스턴스를 유지하기 위해 사용
    private final ThreadLocal<ObjectMapper> objectMapperThreadLocal = ThreadLocal.withInitial(ObjectMapper::new);

    public ObjectMapper getObjectMapper() {
        return objectMapperThreadLocal.get();
    }

}
