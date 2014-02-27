package ipower.cache;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
  * 缓存操作类。
 * 对缓存进行管理，清除方式采用Timer定时的方式。
 * (过期控制较为精准,资源消耗随缓存容量递增)
 * @author yangyong.
 * @since 2014-02-27. 
 * */
public class CacheTimeHandler {
	private static final long SECOND_TIME = 1000;
	private static final Timer timer;
	private static final ConcurrentHashMap<String, CacheEntity<?>> map;
	
	static{
		timer = new Timer();
		map = new ConcurrentHashMap<>(new HashMap<String, CacheEntity<?>>(1<<18));
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
		//添加过期定时。
		timer.schedule(new TimeoutTimerTask(key), validityTime * SECOND_TIME);
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
		if(timer != null) 
			timer.cancel();
		map.clear();
	}
	/**
	 * 清除超时缓存定时服务类。
	 * */
	static class TimeoutTimerTask extends TimerTask{
		private String key;
		/**
		 * 构造函数。
		 * @param key
		 * 	缓存键。
		 * */
		public TimeoutTimerTask(String key){
			this.key = key;
		}
		
		@Override
		public void run() {
			try{
				 CacheEntity<?> entity = map.get(key);
				 if(entity == null)return;
				 long timeout = entity.getTimeoutStamp() - System.currentTimeMillis();
				 if(timeout > 0){
					 timer.schedule(this, timeout);
					 return;
				 }
				 removeCache(key);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
}