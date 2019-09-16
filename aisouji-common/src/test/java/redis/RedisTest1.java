package redis;

import java.util.UUID;

import com.aisouji.redis.utils.RedisUtils;

import redis.clients.jedis.Jedis;

public class RedisTest1 {
		
	private String randomUUID;
	private Jedis jedis;
	
	
	public RedisTest1() {
		randomUUID=UUID.randomUUID().toString();
		jedis=RedisUtils.getJedis();
		this.jiasuoreids("1","123" ,30);//先加锁--加锁与解锁的值   requestId  必须一样  ,randomUUID每次是变化的
		this.jiesuoredis("1","123" );//在解锁
	}

	public void jiasuoreids(String key , String value , int i) {

		System.out.println("加锁键值："+randomUUID);
		boolean flage=RedisUtils.tryGetDistributedLock( key , value , 1000*i);
		//最后一个设置超时时间是以毫秒为单位
		if (flage) {
			System.out.println("设置加锁成功!");
		}else {
			System.out.println("设置加锁失败!");
		}
	}

	public void jiesuoredis( String key , String value) {
		System.out.println("解锁键值："+randomUUID);
		boolean flage= RedisUtils.releaseDistributedLock(key, value);
		if (flage) {
			System.out.println("释放锁成功！");
		}else {
			System.out.println("释放锁失败！");
		}
	}
	
	public static void main(String[] args) {
		//new RedisTest1();
		setKey();
	}
	
	
	public static void setKey() {
		String ok = RedisUtils.setObject("2", "2", 120);
		if (ok.equals("OK")) {
			System.out.println("设置成功");
		}else {
			System.out.println("设置失败");
		}

	}
}
