package ipower.model;

import java.io.Serializable;
/**
 * 分页接口。
 * @author young。
 * @since 2013-11-02.
 * */
public interface IPaging extends Serializable {
	/**
	 * 获取每页数据量。
	 * @return 每页数据量。
	 * */
	public Integer getRows();
	/**
	 * 设置每页数据量。
	 * @param rows
	 * 	每页数据量。
	 * */
	public void setRows(Integer rows);
	/**
	 * 获取页码。
	 * @return 页码。
	 * */
	public Integer getPage();
	/**
	 * 设置页码。
	 * @param page
	 * 	页码。
	 * */
	public void setPage(Integer page);
	/**
	 * 获取排序字段名称。
	 * @return 排序字段名称。
	 * */
	public String getSort();
	/**
	 * 设置排序字段名称。
	 * @param sort
	 * 	排序字段名称。
	 * */
	public void setSort(String sort);
	/**
	 * 获取排序方式。
	 * @return 排序方式。
	 * */
	public String getOrder();
	/**
	 * 设置排序方式。
	 * @param order
	 *  排序方式。
	 * */
	public void setOrder(String order);
}