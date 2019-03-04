package com.park.service;
/**
 * redis客户端接口
 * @author shuang
 *
 */
public interface JedisClient {
	/**
	 * 获取值
	 * @param key
	 * @return
	 */
	String get(String key);
	/**
	 * 设置键值 
	 * @param key
	 * @param value
	 * @return
	 */
	String set(String key,String value);
	
	String set(String key,String value,long seconds);
	/**
	 * 用于情况对应缓存
	 * @param key
	 * @return
	 */
	Long del(String key);
	/**
	 * hash法获取值
	 * @param hkey
	 * @param key
	 * @return
	 */
	String hget(String hkey,String key);
	/**
	 * hash法设置键值
	 * @param hkey
	 * @param key
	 * @param value
	 * @return
	 */
	Long hset(String hkey,String key,String value);
	/**
	 * 用于情况对应缓存
	 * @param key
	 * @return
	 */
	Long hdel(String hkey,String key);
	/**
	 * 这个key存储的值递增
	 * @param key
	 * @return
	 */
	Long incr(String key);
	/**
	 * 这个key存储的值递减
	 * @param key
	 * @return
	 */
	Long decr(String key);
	
	/**
	 * 设置key的有效时间
	 * @param key
	 * @param second
	 * @return
	 */
	Long expire(String key,int second);
	/**
	 * 获取key所剩的有效时长
	 * @param key
	 * @return
	 */
	Long ttl(String key);
	
	

}
