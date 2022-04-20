package hello.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
public class BeanPostProcessorTest {

    @Test
    void basicConfig(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanPostProcessorConfig.class); // 스프링 컨테이너를 만듦

        // beanA라는 이름으로 B객체가 반환되야 한다
        B b = applicationContext.getBean("beanA", B.class);
        b.helloB();

        // A객체는 빈으로 되어있지 않음을 예외를 검증해보자
        Assertions.assertThrows(NoSuchBeanDefinitionException.class, ()-> applicationContext.getBean(A.class));
    }

    @Configuration
    static class BeanPostProcessorConfig {
        @Bean(name= "beanA")
        public A a(){
            return new A();
        }

        @Bean
        public AToBPostProcessor helloProcessor(){
            return new AToBPostProcessor();
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

    static class AToBPostProcessor implements BeanPostProcessor { // BeanPostProcessor 메서드는 default 메서드로 구현안해도 기본 동작이 포함되어있음
        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            log.info("beanName = {}, bean = {}", beanName,bean);
            // 들어오는 빈마다 검사해서 A객체면 B객체로 반환
            if(bean instanceof A){
                return new B();
            }
            return bean;
        }
    }
}
