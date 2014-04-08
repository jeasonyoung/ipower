package ipower.aware;

import ipower.model.UserIdentity;
/**
 * 用户信息接口。
 * @author yangyong.
 * @since 2014-03-15.
 * */
public interface IUserAware {
	/**
	 * 设置用户信息。
	 * @param identity
	 * 	用户信息。
	 * */
	void setUserIdentity(UserIdentity identity);
}