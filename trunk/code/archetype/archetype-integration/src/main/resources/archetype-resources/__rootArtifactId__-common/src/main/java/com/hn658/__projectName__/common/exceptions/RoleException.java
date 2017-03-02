package com.hn658.${projectName}.common.exceptions;

import com.hn658.framework.shared.exception.BusinessException;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:与角色信息有关的异常</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 1 2012-08-30 钟庭杰    新增
* </div>  
********************************************
 */
public class RoleException extends BusinessException {

	/** 角色ID不能为空 */
	public static final String ROLE_ID_NULL = "base.user.rolecodenotnull"; 
	/** 当前操作角色不能为空 */
	public static final String MODIFY_USER_NULL = "base.user.modifyusernull";
	/** 存在角色权限关联记录 */
	public static final String EXIST_ROLEFUNCTION_RECORD = "base.user.existrolefunctionrecord";
	/** 存在角色用户关联记录 */
	public static final String EXIST_ROLEUSER_RECORD = "base.user.existroleuserrecord";
	/** 角色名称重复 */
	public static final String EXIST_ROLEFUNCTION_NAME_REPEAT = "base.user.existrolefunctionnamerepeat";
	/** 角色编码重复 */
	public static final String EXIST_ROLEFUNCTION_CODE_REPEAT = "base.user.existrolefunctioncoderepeat";
	/** 角色名称为空 */
	public static final String ROLE_NAME_NULL = "base.user.rolenamenull";
	
	private static final long serialVersionUID = 590525254182760551L;
	
	/**
	  * 异常的构造方法
	  * @param errCode
	  * @since
	 */
	public RoleException(String errorCode, String... args) {
		super(errorCode, args);
	}

	public RoleException(String errorCode, Throwable t) {
		super(errorCode, t);
	}
}
