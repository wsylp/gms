package wsylp.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * 基于@Aspect的方式(java 5 的首先选择)
 * Created by wsylp on 2017/7/22.
 */
@Aspect//切面
public class MyLog {

    private static Logger logger = LogManager.getLogger(MyLog.class.getName());

    @Before("execution(* wsylp.service..*(..))")//定义切面
    public void logBegin(JoinPoint point) {

        logger.info("@Before：" + point.getSignature().getDeclaringTypeName() + "." + point
            .getSignature().getName() +" begin.");
    }

    @AfterReturning("execution(* wsylp.service..*(..))")//定义切面
    public void logAfter(JoinPoint point) {

        logger.info(
            "@AfterReturning：" + point.getSignature().getDeclaringTypeName() + "." + point
                .getSignature().getName() +" end.");
    }

//    @Around("execution(* wsylp.service.UserService.*(..))")//定义切面
//    public void logAround(ProceedingJoinPoint point) throws Throwable {
//        logger.info("@Around：环绕增强方法开始：" + point.getSignature().getDeclaringTypeName() + "." + point
//            .getSignature().getName());
//        point.proceed();
//        logger.info("@Around：环绕增强方法结束：" + point.getSignature().getDeclaringTypeName() + "." + point
//            .getSignature().getName());
//
//    }
}
