package com.hn658.${projectName}.common.exceptions;

import com.hn658.framework.shared.exception.BusinessException;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:与功能信息有关的异常</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 1 2012-08-30 钟庭杰    新增
* </div>  
********************************************
 */
public class FunctionException extends BusinessException {
	
	/** 功能Uri不能为空 */
	public static final String FUNCTION_URI_NULL = "base.user.FunctionUriNullException";
	
	/** 功能名称不能为空 */
	public static final String FUNCTION_NAME_NULL = "base.user.FunctionNameNullException";
	
	/** 功能类型不能为空 */
	public static final String FUNCTION_TYPE_NULL = "base.user.FunctionTypeNullException";
	
	/** 父功能不能为空 */
	public static final String FUNCTION_PARENT_NULL = "base.user.FunctionParentNullException";
	
	/** 功能不能为空 */
	public static final String FUNCTION_NULL = "base.user.FunctionNull";

	/** 此功能不存在 */
	public static final String FUNCTION_NOT_EXITED = "base.user.FunctionExitedException";
	
	/** 功能是否验证不能为空 */
	public static final String FUNCTION_VALIDFLAG_NULL = "base.user.FunctionValidflagNullException";
	
	/** 功能URI已经存在 */
	public static final String FUNCTION_URI_EXITED = "base.user.FunctionUriExitedException";
	
	/** 父功能已禁用 */
	public static final String FUNCTION_PARENT_DISABLE = "base.user.FunctionParentDisableException";
	
	/** 父功能ID不能为空 */
	public static final String FUNCTION_PARENT_ID_NULL = "base.user.FunctionParentIdNullException";
	
	/** 不能删除，还有子节点 */
	public static final String FUNCTION_HASCHILDREN = "base.user.FunctionDelHaschildrenException";
	
	/** 不能删除，还有角色关联 */
	public static final String FUNCTION_HASROLES = "base.user.FunctionDelHasrolesException";
	
	/** 没有子功能菜单 */
	public static final String CHILD_MEUN_NULL = "base.user.ChildMenuNullException";

	/** 非法的查询条件 */
	public static final String INVALID_QUERY_PARAM = "base.user.InvalidQueryParam";

	/** 模块只能是子系统或模块的子功能 */
	public static final String MODULE = "base.user.FunctionTypeModuleException";

	/** 菜单只能是模块的子功能 */
	public static final String MENU = "base.user.FunctionTypeMenuException";

	/** 页面只能是菜单的子功能 */
	public static final String BUTTON = "base.user.FunctionTypeButtonException";

	/** 功能ID不能为空 */
	public static final String FUNCTION_ID_NULL = "base.user.FunctionIdNullException";
	
	private static final long serialVersionUID = -3422399009620737953L;
	
	/**
	  * 功能异常类定义
	  * @param errCode
	  * @since
	 */
	public FunctionException(String errorCode, String... args) {
		super(errorCode, args);
	}

	public FunctionException(String errorCode, Throwable t) {
		super(errorCode, t);
	}
}
