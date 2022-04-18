package hello.proxy.proxyfactory;

import hello.proxy.common.ConcreteService;
import hello.proxy.common.ServiceImpl;
import hello.proxy.common.ServiceInterface;
import hello.proxy.common.advice.TimeAdvice;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

@Slf4j
public class ProxyFactoryTest {
    @Test
    @DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용함")
    void interfaceProxy(){
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target); // target이 자동 등록
        proxyFactory.addAdvice(new TimeAdvice()); // 호출할 Advice

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        log.info("target Class = {}", target.getClass());
        log.info("proxy Class = {}", proxy.getClass());

        proxy.save();

        // 프록시가 적용이 되었는지 확인하기 위해
        Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        // jdk 동적 프록시인지
        Assertions.assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        // cglib가 적용되었는지
        Assertions.assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
    }

    @Test
    @DisplayName("구체클래스 있으면 JDK 동적 프록시 사용함")
    void concreteProxy(){
        ConcreteService target = new ConcreteService();
        ProxyFactory proxyFactory = new ProxyFactory(target); // target이 자동 등록
        proxyFactory.addAdvice(new TimeAdvice()); // 호출할 Advice

        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();
        log.info("target Class = {}", target.getClass());
        log.info("proxy Class = {}", proxy.getClass());

        proxy.call();

        // 프록시가 적용이 되었는지 확인하기 위해
        Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        // jdk 동적 프록시인지
        Assertions.assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        // cglib가 적용되었는지
        Assertions.assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }

    @Test
    @DisplayName("ProxyTargetClass 옵션을 사용하면 인터페이스가 있어도 CGLIB를 사용하고, 클래스 기반으로 돈다")
    void proxyTargetProxy(){
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target); // target이 자동 등록
        proxyFactory.setProxyTargetClass(true); // 무조건 CGLIB로 클래스 기반 프록시를 만들거야! 하는 경우
        proxyFactory.addAdvice(new TimeAdvice()); // 호출할 Advice

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        log.info("target Class = {}", target.getClass());
        log.info("proxy Class = {}", proxy.getClass());

        proxy.save();

        // 프록시가 적용이 되었는지 확인하기 위해
        Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        // jdk 동적 프록시인지
        Assertions.assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        // cglib가 적용되었는지
        Assertions.assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }
}
