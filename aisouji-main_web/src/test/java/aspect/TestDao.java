package aspect;

import org.springframework.stereotype.Component;

@Component
public class TestDao{

	public String save() {
		System.out.println("保存书目");
		return "保存完成";
	}
}
