package ipower.model;

import java.io.Serializable;
/**
 * 用户身份信息。
 * @author yangyong.
 * @since 2014-04-04.
 * */
public class UserIdentity implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id,name;
	/**
	 * 获取用户ID。
	 * @return 用户ID。
	 * */
	public String getId() {
		return id;
	}
	/**
	 * 设置获取用户ID。
	 * @param id
	 * 用户ID。
	 * */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 设置用户姓名。
	 * @return 用户姓名。
	 * */
	public String getName() {
		return name;
	}
	/**
	 * 设置用户姓名。
	 * @param name
	 * 用户姓名。
	 * */
	public void setName(String name) {
		this.name = name;
	}
}