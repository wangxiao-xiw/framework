package com.hn658.${projectName}.common.enums;

import com.hn658.${projectName}.common.entity.FunctionEO;
import com.hn658.${projectName}.common.exceptions.FunctionException;

/**
 * 权限功能类型
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:ztjie,date:2013-4-12 下午2:50:41,content:权限功能类型 </p>
 * @author ztjie
 * @date 2013-4-12 下午2:50:41
 * @since
 * @version
 */
public enum FunctionType {

	/** 模块 */
	MODULE(1,"模块") {
		public void checkParentType(FunctionEO parent) throws FunctionException {
			if (parent.getParentId() != null) {
				throw new FunctionException(FunctionException.MODULE);
			}
		}
	},
	/** 菜单 */
	MENU(2,"菜单") {
		public void checkParentType(FunctionEO parent) throws FunctionException {
			if (parent.getFunctionType().intValue()!=FunctionType.MODULE.getType().intValue()) {
				throw new FunctionException(FunctionException.MENU);
			}
		}
	},
	/** 按钮 */
	BUTTON(3,"按钮") {
		public void checkParentType(FunctionEO parent) throws FunctionException {
			if (parent.getFunctionType().intValue()!=FunctionType.MENU.getType().intValue()) {
				throw new FunctionException(FunctionException.BUTTON);
			}
		}
	};
	
	/**
	 * 功能类型
	 */
	private Integer type;
	
	/**
	 * 功能名称
	 */
	private String name;
	
	FunctionType(Integer type, String name){
		this.type = type;
		this.name = name;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 检查此类型的父节点类型是否合格
	 * @author ztjie
	 * @date 2013-4-19 下午4:42:41
	 * @param parent
	 * @throws FunctionException 不合格将抛出异常
	 * @see
	 */
	public void checkParentType(FunctionEO parent) throws FunctionException {
		
	}
	
	/**
	 * 根据code值获取对应的枚举
	 * @param code
	 * @return
	 */
	public static FunctionType getTypeByCode(Integer type){
		for(FunctionType functionType : FunctionType.values()){
			if(functionType.getType() == type){
				return functionType;
			}
		}
		return null;
	}
}
