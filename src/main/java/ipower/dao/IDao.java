package ipower.dao;

import java.io.Serializable;
/**
 * 数据操作接口。
 * @author 杨勇.
 * @since 2013-11-27.
 * */
public interface IDao<T> {
	/**
	 * 加载数据对象。
	 * @param c
	 * 	数据对象类型。
	 * @param id
	 * 	数据对象主键。
	 * @return 对象。
	 * */
	T load(Class<T> c,Serializable id);
	/**
	 * 保存数据对象。
	 * @param data
	 * 	数据对象。
	 * @return 主键值。
	 * */
	Serializable save(T data);
	/**
	 * 更新数据对象。
	 * @param data
	 * 	数据对象。
	 * */
	void update(T data);
	/**
	 * 保存或更新数据对象。
	 * @param data
	 * 	数据对象。
	 * */
	void saveOrUpdate(T data);
	/**
	 * 删除数据对象。
	 * @param data
	 * 	数据对象。
	 * */
	void delete(T data);
}