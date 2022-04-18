package hello.proxy.cglib;

import hello.proxy.cglib.code.TimeMethodInterceptor;
import hello.proxy.common.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CglibTest {
    @Test
    void cglib(){
        ConcreteService target = new ConcreteService();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ConcreteService.class); // 동적프록시를 만들어야 되는데, 인터페이스가 아닌 구체클래스로 넣어준다
        enhancer.setCallback(new TimeMethodInterceptor(target));
        ConcreteService proxy = (ConcreteService) enhancer.create(); // ConcreteService의 프록시를 만들 수 있다

        log.info("target classs = {}", target.getClass());
        log.info("proxy classs = {}", proxy.getClass());

        proxy.call();
    }
}
