package ipower.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存操作类。
 * 对缓存进行管理，采用处理队列，定时循环清除的方式。
 * (过期控制不太精准,资源消耗较小)
 * @author yangyong.
 * @since 2014-02-27.
 * */
public class CacheListHandler {
	private static final long SECOND_TIME = 1000;
	private static final ConcurrentHashMap<String, CacheEntity<?>> map;
	private static final List<CacheEntity<?>> tempList;
	
	static{
		map = new ConcurrentHashMap<>(new HashMap<String, CacheEntity<?>>(1<<18));
		tempList = new ArrayList<>();
		new Thread(new TimeoutTimerThread()).start();
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
		//添加到过期处理队列。
		tempList.add(entity);
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
		tempList.clear();
		map.clear();
	}
	/**
	 * 缓存过期处理线程类。
	 * */
	static class TimeoutTimerThread implements Runnable{

		@Override
		public void run() {
			while(true){
				try {
					//过期检查
					this.checkTime();
					//线程休眠
					Thread.sleep(SECOND_TIME);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		/**
		 * 过期缓存具体处理方法。
		 * @throws Exception 
		 * */
		private void checkTime() throws Exception{
			if(tempList.size() == 0){
				Thread.sleep(SECOND_TIME * 10);
				return;
			}
			long timeout = 1000L;
			for(int i = 0; i < tempList.size(); i++){
				CacheEntity<?> entity = tempList.get(i);
				if(entity == null)continue;
				if((timeout = entity.getTimeoutStamp() - System.currentTimeMillis()) > 0){
					Thread.sleep(timeout);
					continue;
				}
				//清除过期缓存和删除对应的缓存队列
				removeCache(entity.getKey());
				tempList.remove(entity);
			}
		}
	}
}