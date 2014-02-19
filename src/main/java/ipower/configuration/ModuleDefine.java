package ipower.configuration;

import java.io.Serializable;
/**
 * 模块系统信息。
 * @author young。
 * @since 2013-09-18。
 * */
public class ModuleDefine implements Serializable {
	private static final long serialVersionUID = 1L;	
	private String moduleID, moduleName, moduleUri;
	private Integer orderNo = 0;
	private ModuleDefineCollection modules;
	/**
	 * 构造函数。
	 * */
	public ModuleDefine(){
		this.setModules(new ModuleDefineCollection());
	}
	/**
	 * 获取模块ID。
	 * @return 模块ID。
	 * */
	public String getModuleID() {
		return moduleID;
	}
	/**
	 * 设置模块ID。
	 * @param moduleID
	 * 	模块ID。
	 * */
	public void setModuleID(String moduleID) {
		this.moduleID = moduleID;
	}
	/**
	 * 获取模块名称。
	 * @return 模块名称。
	 * */
	public String getModuleName() {
		return moduleName;
	}
	/**
	 * 设置模块名称。
	 * @param moduleName
	 * 	模块名称。
	 * */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	/**
	 * 获取模块URI。
	 * @return 模块URI。
	 * */
	public String getModuleUri() {
		return moduleUri;
	}
	/**
	 * 设置模块URI。
	 * @param moduleUri
	 * 	模块URI。
	 * */
	public void setModuleUri(String moduleUri) {
		this.moduleUri = moduleUri;
	}
	/**
	 * 获取排序号。
	 * @return 排序号。
	 * */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置排序号。
	 * @param orderNo
	 * 	排序号。
	 * */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 获取子模块集合。
	 * @return 子模块集合。
	 * */
	public ModuleDefineCollection getModules() {
		return modules;
	}
	/**
	 * 设置子模块集合。
	 * @param modules
	 * 	子模块集合。
	 * */
	public void setModules(ModuleDefineCollection modules) {
		this.modules = modules;
	}
}