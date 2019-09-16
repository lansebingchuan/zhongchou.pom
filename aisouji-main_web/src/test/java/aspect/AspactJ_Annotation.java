package aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspactJ_Annotation {
	
	@Pointcut("execution(* aspect.*.*(..))")//被代理的对象，不要在去创建bean因为代理对象本身也是bean
	private void myPointcut() { }
	
    @Before(value="myPointcut()")
	public void before(JoinPoint joinpoint) {
		System.out.println("前置通知");
		System.out.println("通知类："+joinpoint.getTarget()+" 方法名称："+joinpoint.getSignature().getName());
	}
    @AfterReturning(value="myPointcut()" ,returning="returnVal")//returning
	public void afterReturn(JoinPoint joinpoint ,Object returnVal) {
		System.out.println("后置通知");
		System.out.println("通知类："+joinpoint.getTarget());
		System.out.println(" 返回结果："+returnVal);
	}
	@Around("myPointcut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("环绕通知开始，执行目标方法");
		Object proceed = joinPoint.proceed();
		System.out.println("环绕结束");
		return proceed;
	}
	@AfterThrowing(value="myPointcut()" ,throwing="e")
	public void throwing(JoinPoint joinPoint , Throwable e) {
		System.out.print("异常通知 ，类："+joinPoint.getTarget()+"  方法："+joinPoint.getSignature().getName());
		System.err.println("  出错了："+e.getMessage());
	}
	@After(value="myPointcut()")
	public void after() {
		System.out.println("最后通知");
	}
}
