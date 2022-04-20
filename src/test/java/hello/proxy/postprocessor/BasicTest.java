package hello.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
public class BasicTest {

    @Test
    void basicConfig(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BasicConfig.class); // 스프링 컨테이너를 만듦

        // A는 빈으로 등록되었는지 조회
        A a = applicationContext.getBean("beanA", A.class);
        a.helloA();

        // B는 빈으로 되어있지 않음을 예외를 검증해보자
        Assertions.assertThrows(NoSuchBeanDefinitionException.class, ()-> applicationContext.getBean(B.class));
    }

    @Configuration
    static class BasicConfig{
        @Bean(name= "beanA")
        public A a(){
            return new A();
        }
    }

    static class A{
        public void helloA(){
            log.info("hello A");
        }
    }

    static class B{
        public void helloB(){
            log.info("hello B");
        }
    }
}
