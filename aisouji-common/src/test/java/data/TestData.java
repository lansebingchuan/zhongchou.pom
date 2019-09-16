package data;

import org.junit.Test;

public class TestData {

	
	@Test
	public void name() {
		String total_amount = "2.00";
		total_amount = total_amount.substring(0, total_amount.indexOf("."));
		System.out.println(total_amount);
	}
}
