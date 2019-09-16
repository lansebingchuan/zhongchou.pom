package aspect;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;
@Component
public class MyAspect implements MethodInterceptor{

	public Object invoke(MethodInvocation i) throws Throwable {
		System.out.println("方法： "+i.getMethod()+" is called on "+
				i.getThis()+" 参数： "+i.getArguments());
		Object ret=i.proceed();
		System.out.println("method "+i.getMethod()+" returns "+ret);
		return ret;
	}

}
