package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeDecorator implements Component{
    /* 여기 부터*/
    private Component component;

    public TimeDecorator(Component component) {
        this.component = component;
    }
    /* 여기 까지 - 중복으로 처리되는 코드로 추상클래스로 올릴 수 있다*/
    @Override
    public String operation() {
        log.info("TimeDecorator 실행");

        long startTime = System.currentTimeMillis();
        String result = component.operation();
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;

        log.info("TimeDecorator 종료 resultTime ={}ms", resultTime);
        return result;
    }
}
