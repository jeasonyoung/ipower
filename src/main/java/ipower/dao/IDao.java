package ipower.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
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
	/**
	 * 查询数据对象。
	 * @param hql
	 * 	HQL语句。
	 * @param parameters
	 * 	参数集合。
	 * @param page
	 * 	页码索引。
	 * @param rows
	 * 	页记录。
	 * @return 数据对象集合。
	 * */
	List<T> find(String hql,Map<String, Object> parameters, Integer page, Integer rows);
	/**
	 * 统计数据对象。
	 * @param hql
	 * 	HQL语句。
	 * @param parameters
	 *  参数对象集合。
	 * */
	Long count(String hql,Map<String, Object> parameters);
}