package com.hn658.${projectName}.dictionary.exception;

import com.hn658.framework.shared.exception.BusinessException;

public class DictionaryException extends BusinessException {

	private static final long serialVersionUID = 4858857865102000835L;
	
    public static final String DictionaryHasNoParentException = "父级数据为空，参数传递出现异常";
    public static final String DictionaryCoreDataIsBlankException = "数据字典核心数据为空，参数传递出现异常！";
    public static final String DictionaryHasSameTypeOrAliasException = "添加失败！已经存在相同的分类别名。";
    public static final String DictionaryEntityIsNullException = "数据字典实体为空，参数传递出现异常！";
    public static final String DictionaryTypeNOHasException = "此数据类型不存在";
	
	public DictionaryException(String errorCode, String... args) {
		super(errorCode, args);
	}

	public DictionaryException(String errorCode, Throwable t) {
		super(errorCode, t);
	}

}
