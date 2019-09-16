package aspect;

import org.aspectj.lang.*;
import org.springframework.stereotype.Component;

@Component
public class AspectJ_xml {

	public void before(JoinPoint joinpoint) {
		System.out.println("前置通知");
		System.out.println("通知类："+joinpoint.getTarget()+" 方法名称："+joinpoint.getSignature().getName());
	}
	
	public void afterReturn(JoinPoint joinpoint ,Object returnVal) {
		System.out.println("后置通知");
		System.out.println("通知类："+joinpoint.getTarget()+" 方法名称："+joinpoint.getSignature().getName());
		System.out.println(" 返回结果："+returnVal);
	}
	
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("环绕通知开始，执行目标方法");
		Object proceed = joinPoint.proceed();
		System.out.println("环绕结束");
		return proceed;
	}
	public void throwing(JoinPoint joinPoint , Throwable e) {
		System.out.print("异常通知 ，类："+joinPoint.getTarget()+"  方法："+joinPoint.getSignature().getName());
		System.err.println("  出错了："+e.getMessage());
	}
	
	public void after() {
		System.out.println("最后通知");
	}
}
