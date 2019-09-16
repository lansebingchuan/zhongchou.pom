package aspect;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class MyTest {
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("aspect/spring.xml");
		TestDao testDao =(TestDao) applicationContext.getBean("testDao");
		testDao.save();
	}
}
