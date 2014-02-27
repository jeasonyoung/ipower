package ipower.cache;

import java.io.Serializable;

/**
 * 缓存对象。
 * @author yangyong.
 * @since 2014-02-27.
 * */
public class CacheEntity<T extends Serializable> implements Serializable {
	private static final long serialVersionUID = 1L;
	private final int DEFUALT_VALIDITY_TIME = 20;//默认过期时间20秒.
	
	private String key;
	private T entity;
	private int validityTime;//有效时长(单位秒).
	private long timeoutStamp;//过期时间戳.
	/**
	 * 构造函数。
	 * */
	private CacheEntity(){
		this.timeoutStamp = System.currentTimeMillis() + DEFUALT_VALIDITY_TIME * 1000;
		this.validityTime = DEFUALT_VALIDITY_TIME;
	}
	/**
	 * 构造函数。
	 * @param key
	 * 	缓存键。
	 * @param entity
	 * 	缓存对象。
	 * */
	public CacheEntity(String key,T entity){
		this();
		this.key = key;
		this.entity = entity;
	}
	/**
	 * 构造函数。
	 * @param key
	 * 	缓存键。
	 * @param entity
	 * 	缓存对象。
	 * @param timeoutStamp
	 *  过期时间戳。
	 * */
	public CacheEntity(String key,T entity,long timeoutStamp){
		this(key,entity);
		this.timeoutStamp = timeoutStamp;
	}
	/**
	 * 构造函数。
	 * @param key
	 * 	缓存键。
	 * @param entity
	 * 	缓存对象。
	 * @param validityTime
	 *  有效时长(单位秒).
	 * */
	public CacheEntity(String key,T entity,int validityTime){
		this(key,entity);
		this.validityTime = validityTime;
		this.timeoutStamp = System.currentTimeMillis() + validityTime * 1000;
	}
	/**
	 * 获取缓存键。
	 * @return 缓存键。
	 * */
	public String getKey() {
		return key;
	}
	/**
	 * 设置缓存键。
	 * @param key
	 * 	缓存键。
	 * */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * 获取缓存对象。
	 * @return 缓存对象。
	 * */
	public T getEntity() {
		return entity;
	}
	/**
	 * 设置缓存对象。
	 * @param entity
	 * 	缓存对象。
	 * */
	public void setEntity(T entity) {
		this.entity = entity;
	}
	/**
	 * 获取有效时长(单位秒)。
	 * @return 有效时长(单位秒)。
	 * */
	public int getValidityTime() {
		return validityTime;
	}
	/**
	 * 设置有效时长(单位秒)。
	 * @param validityTime
	 * 	有效时长(单位秒)。
	 * */
	public void setValidityTime(int validityTime) {
		this.validityTime = validityTime;
	}
	/**
	 * 获取过期时间戳。
	 * @return 过期时间戳。
	 * */
	public long getTimeoutStamp() {
		return timeoutStamp;
	}
	/**
	 * 设置过期时间戳。
	 * @param timeoutStamp
	 * 	过期时间戳。
	 * */
	public void setTimeoutStamp(long timeoutStamp) {
		this.timeoutStamp = timeoutStamp;
	}
}