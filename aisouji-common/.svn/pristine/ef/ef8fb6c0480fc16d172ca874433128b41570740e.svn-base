package com.aisouji.redis.utils;

import java.util.Random;

public class PasswordUtil {
	private static final String DEFALUT_NUM= "!@#$%^&*_-+=/";
	//生成6位随机数字
	public static String getRandom_6() {
		char[] nums = new char[6];
		Random random = new Random();
		for(int i=0 ; i<6 ;i++) {
			nums[i] = (char)('0'+random.nextInt(10));
		}
		return new String(nums);
	}
	//生成8位复杂的密码，包含特殊字符
	public static String getRandom_8() {
		final char [] nums = new char[8];
		final Random random = new Random();
		//定义了一个方法内不类
		class Inner {//要在之后调用
			//返回一个char
			public  char netChar(Random random) {
				switch (random.nextInt(4)) {
				case 0://0-9的数字
					return (char)('0'+random.nextInt(10));
				case 1://a - z
					return (char)('a'+random.nextInt(26));
				case 2://A-Z
					return (char)('A'+random.nextInt(26));
				default://一个特殊字符
					return DEFALUT_NUM.charAt(random.nextInt(DEFALUT_NUM.length()));
				}
			}

			public String getRandom_8_0() {
				for (int i = 0; i < 8; i++) {
					nums[i] = netChar(random);//返回一个char
				}
				return new String(nums);
			}

		}		
		Inner ran = new Inner();//使用内部类
		return ran.getRandom_8_0();//返回一个字符串
	}
	public static String getRandom_8_0() {
		// TODO Auto-generated method stub
		char[] nums = new char[8];
		Random random = new Random();
		//在一个随机的位置，添加一个随机特殊字符
		nums[nextIndex(nums,random)] = nextSpecialChar(random);
		//返回一整个A-Z的字符
		nums[nextIndex(nums,random)] = nextUpperLetter(random);
		//返回一整个a-z的字符
		nums[nextIndex(nums,random)] = nextLowerLetter(random);
		//返回一个0-10的数字char
		nums[nextIndex(nums,random)] = nextNumLetter(random);
		for (int i = 0; i < 8; i++) {
			if (nums[i] == 0) {//表示之前没有填的
				nums[i] = netChar(random);
			}
		}
		return new String(nums);
	}

	public static char netChar(Random random) {
		switch (random.nextInt(4)) {
		case 0://0-9的数字
			return (char)('0'+random.nextInt(10));
		case 1://a - z
			return (char)('a'+random.nextInt(26));
		case 2://A-Z
			return (char)('A'+random.nextInt(26));
		default://一个特殊字符
			return DEFALUT_NUM.charAt(random.nextInt(DEFALUT_NUM.length()));
		}
	}
		//返回一个0-10的数字char
		private static char nextNumLetter(Random random) {
			// TODO Auto-generated method stub
			return (char)('0'+random.nextInt(10));
		}
		//返回一整个a-z的字符
		private static char nextLowerLetter(Random random) {
			// TODO Auto-generated method stub
			return (char)('a'+random.nextInt(26));
		}
		//返回一整个A-Z的字符
		private static char nextUpperLetter(Random random) {
			// TODO Auto-generated method stub
			return (char)('A'+random.nextInt(26));
		}
		//返回一个特殊字符
		private static char nextSpecialChar(Random random) {
			// TODO Auto-generated method stub
			return DEFALUT_NUM.charAt(random.nextInt(DEFALUT_NUM.length()));
		}
		//在一个随机的位置，添加一个随机特殊字符,并返回
		private static int nextIndex(char[] nums, Random random) {
			// TODO Auto-generated method stub
			int index = random.nextInt(nums.length);
			while (nums[index] != 0) {
				index = random.nextInt(nums.length);
			}
			return index;
		}
	}
