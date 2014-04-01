package ipower.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存操作类。
 * 对缓存进行管理，采用处理队列，定时循环清除的方式。
 * (过期控制不太精准,资源消耗较小)
 * @author yangyong.
 * @since 2014-02-27.
 * */
public class CacheListHandler {
	private static final long SECOND_TIME = 1000 * 20;
	private static final ConcurrentHashMap<String, CacheEntity<?>> map;
	private static final Timer timer;
	static{
		map = new ConcurrentHashMap<>(new HashMap<String, CacheEntity<?>>(1<<18));
		timer = new Timer();
		timer.schedule(new TimeoutTimerTask(), SECOND_TIME);
	}
	/**
	 * 增加缓存对象。
	 * @param key
	 * 	缓存键.
	 * @param entity
	 * 	缓存对象.
	 * @param validityTime
	 * 	有效时间(秒).
	 * */
	public static synchronized void addCache(String key,CacheEntity<?> entity,int validityTime){
		entity.setTimeoutStamp(System.currentTimeMillis() + validityTime * SECOND_TIME);
		map.put(key, entity);
	}
	/**
	 * 增加缓存。
	 * @param key
	 * 	缓存键.
	 * @param entity
	 * 	缓存对象.
	 * */
	public static void addCache(String key,CacheEntity<?> entity){
		addCache(key, entity, entity.getValidityTime());
	}
	/**
	 * 获取缓存对象。
	 * @param key
	 * 	缓存键。
	 * @return 
	 * 	缓存对象。
	 * */
	public static synchronized CacheEntity<?> getCache(String key){
		return map.get(key);
	}
	/**
	 * 检查是否含有key的缓存。
	 * @param key
	 * 	缓存键。
	 * @return
	 * 	存在返回true,否则返回false.
	 * */
	public static synchronized boolean isConcurrent(String key){
		return map.containsKey(key);
	}
	/**
	 * 删除缓存。
	 * @param key
	 * 	缓存键。
	 * */
	public static synchronized void removeCache(String key){
		map.remove(key);
	}
	/**
	 * 获取缓存大小。
	 * @return 缓存大小。
	 * */
	public static int getCacheSize(){
		return map.size();
	}
	/**
	 * 清除全部缓存。
	 * */
	public static synchronized void ClearCache(){
		map.clear();
	}
	/**
	 * 缓存过期处理线程类。
	 * */
	static class TimeoutTimerTask extends TimerTask{

		@Override
		public void run() {
			try {
				if(map != null && map.size() > 0){
					 List<String> keys = new ArrayList<>();
					 for(CacheEntity<?> entity : map.values()){
						 if(entity == null)continue;
						 if(System.currentTimeMillis() - entity.getTimeoutStamp() > 0){
							 keys.add(entity.getKey());
						 }
					 }
					for(int i = 0; i < keys.size(); i++){
						removeCache(keys.get(i));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				timer.schedule(new TimeoutTimerTask(), SECOND_TIME);
			}
		}	
	}
}