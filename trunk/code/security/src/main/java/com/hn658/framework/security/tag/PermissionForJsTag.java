package com.hn658.framework.security.tag;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;

import com.hn658.framework.security.SecurityAccessor;

/**
 * 产生指定模块内javascript会用到的变量、国际化函数、权限函数
 * @author ztjie
 *
 */
public class PermissionForJsTag extends SimpleTagSupport {
	
	/**
	 * 
	 * <p>tag配置</p> 
	 * @author ztjie
	 * @date 2013-3-29 下午2:58:37
	 * @throws JspException
	 * @throws IOException 
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 */
	@Override
	public void doTag() throws JspException, IOException {
		Map<String, String> tags = TagsCache.getInstance().getTagesInfo();
		getJspContext().getOut().write(createPermissionScript(tags.get("urls")));
	}
	
	/**
	 * 
	 * <p>生成权限控制的javascript代码格式</p> 
	 * @author 平台开发小组
	 * @date 2013-4-1 上午10:52:52
	 * @param moduleName
	 * @param urls
	 * @return
	 * @see
	 */
	private String createPermissionScript(String urls){
		if(StringUtils.isBlank(urls)) {
			return urls;
		}
		String[] urlArray = parseStringToArray(urls);
		StringBuilder  msgObject=new StringBuilder("");
		msgObject.append("<script type='text/javascript'> \n");
		msgObject.append("isPermission = function (url){ \n");
		msgObject.append("permissions = {");
		for(String url :urlArray)
		{
			boolean message = SecurityAccessor.hasAccessSecurity(url);
			msgObject.append("'"+url+"'" +":"+message+",");
		}
		msgObject.deleteCharAt(msgObject.lastIndexOf(","));
		msgObject.append("}; \n");
		msgObject.append("return permissions[url]; \n");
		msgObject.append("}; \n");
		msgObject.append("</script>");
		return msgObject.toString();
	}
	/**
	 * 
	 * <p>将字符串转成字符数组</p> 
	 * @author 平台开发小组
	 * @date 2013-4-1 上午10:54:27
	 * @param str
	 * @return
	 * @see
	 */
	private String[] parseStringToArray(String str){
		if(str==null){
			return new String[0];
		}
		str = str.replaceAll("\n", "");
		str = str.replaceAll("\r", "");
		String[] keyArray = str.split(",");
		for (int i = 0; i < keyArray.length; i++) {
			keyArray[i] = keyArray[i].trim();
		}
		return keyArray;		
	}
}
