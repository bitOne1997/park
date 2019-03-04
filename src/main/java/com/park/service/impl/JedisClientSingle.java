package com.park.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import com.park.service.JedisClient;
/**
 * 单机版redis实现类
 * @author shuang
 *
 */
@Service("jedisClient")
public class JedisClientSingle implements JedisClient {

	@Resource(name="jedisPool")
	private JedisPool jedisPool;//这个在sping中配置好的

	@Override
	public String get(String key) {
		try {
			Jedis jedis = jedisPool.getResource();
			String string=jedis.get(key);
			jedis.close();
			return string;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String set(String key, String value) {
		try {
			Jedis jedis = jedisPool.getResource();
			String string = jedis.set(key, value);
			
			jedis.close();
			return string;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String hget(String hkey, String key) {
		try {
			Jedis jedis = jedisPool.getResource();
			String string = jedis.hget(hkey, key);
			jedis.close();
			return string;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Long hset(String hkey, String key, String value) {
		try {
			Jedis jedis = jedisPool.getResource();
			Long l = jedis.hset(hkey, key, value);
			jedis.close();
			return l;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Long incr(String key) {
		try {
			Jedis jedis = jedisPool.getResource();
			Long result = jedis.incr(key);
			jedis.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Long decr(String key) {
		try {
			Jedis jedis = jedisPool.getResource();
			Long result =jedis.decr(key);
			jedis.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Long expire(String key, int seconds) {
		try {
			Jedis jedis = jedisPool.getResource();
			Long expire = jedis.expire(key,(int) seconds);
			jedis.close();
			return expire;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Long ttl(String key) {
		try {
			Jedis jedis = jedisPool.getResource();
			Long result = jedis.ttl(key);
			jedis.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Long del(String key) {
		try {
			Jedis jedis = jedisPool.getResource();
			Long result = jedis.del(key);
			jedis.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Long hdel(String hkey, String key) {
		try {
			Jedis jedis = jedisPool.getResource();
			Long result = jedis.hdel(hkey, key);
			jedis.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String set(String key, String value, long seconds) {
		try {
			Jedis jedis = jedisPool.getResource();
			String string = jedis.set(key, value,"NX", "EX",seconds);			
			jedis.close();
			return string;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
