package com.zj.test.jedis;

import java.util.ResourceBundle;

import javax.swing.JEditorPane;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import sun.security.krb5.Config;

public class TestJedis {
	public static void main(String[] args) {
//		Jedis jedis=new Jedis("127.0.0.1",6379);
//		jedis.del("name");
//		jedis.set("name", "zhoujie");
//		String value=jedis.get("name");
//		System.out.println(value);
		Jedis jedis=JedisFactory.getJedisFromPool();
		jedis.set("east", "china");
		System.out.println(jedis.get("east"));
		JedisFactory.releaseJedis(jedis);
	}
	public void newJedis(){
		Jedis jedis=new Jedis("127.0.0.1",6379);
		jedis.del("name");
		jedis.set("name", "zhoujie");
		String value=jedis.get("name");
		System.out.println(value);
	}
	public void newJedisByconfig(){
		ResourceBundle bundle=ResourceBundle.getBundle("redis");
		JedisPoolConfig config=new JedisPoolConfig();
		config.setMaxActive(Integer.parseInt(bundle.getString("redis.pool.maxActive")));
		config.setMaxIdle(Integer.parseInt(bundle.getString("redis.pool.maxIdle")));
		config.setMaxWait(Long.parseLong(bundle.getString("redis.pool.maxWait")));
		config.setTestOnBorrow(Boolean.parseBoolean(bundle.getString("redis.pool.testOnBorrow")));
		config.setTestOnReturn(Boolean.parseBoolean(bundle.getString("redis.pool.testOnReturn")));
		JedisPool pool=new JedisPool(config,bundle.getString("redis.ip"),Integer.parseInt(bundle.getString("redis.port")));
		pool.getResource();
	}
}