package com.hn658.${projectName}.dao.redis.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import com.hn658.${projectName}.dao.redis.IFunctionRedisDAO;
import com.hn658.${projectName}.common.constants.*;
import com.hn658.${projectName}.common.entity.FunctionEO;
import com.hn658.${projectName}.common.entity.TreeNode;
import com.hn658.${projectName}.common.utils.RedisKeyHelper;
import com.hn658.framework.dataaccess.redis.AbstractRedisDAO;
import com.hn658.framework.shared.utils.JsonMapper;

/**
 * 权限Redis DAO 实现类
 * 
 * @author ztjie
 * 
 */
@Repository
public class FunctionRedisDAOImpl extends AbstractRedisDAO<FunctionEO>
		implements IFunctionRedisDAO {
	
	@Autowired
	@Qualifier("redisTemplate")
	@Override
	public void setTemplate(StringRedisTemplate template) {
		super.template = template;
	}
	
	JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();
	
	@Override
	public FunctionEO findFunctionById(String id) {
		if(StringUtils.isEmpty(id)){
			return null;
		}
		String functionInfoJson = valueOps.get(RedisKeyHelper.functionId(id));
		FunctionEO function = jsonMapper.fromJson(functionInfoJson, FunctionEO.class);
        return function;
	}
	
	@Override
	public FunctionEO findFunctionByUri(String functionUri) {
		// 先获取clientId对应的Uid
		String id = valueOps.get(RedisKeyHelper.functionUri(functionUri));
		if(StringUtils.isEmpty(id)){
			return null;
		}

		// 从Redis中获取对应uid的FunctionEO
		return this.findFunctionById(id);
	}

	@Override
	public void save(FunctionEO function) {
		if(function == null){
			return;
		}
		
        if (StringUtils.isNotBlank(function.getId().toString())) {
            // 设置url对应的id
            valueOps.set(RedisKeyHelper.functionUri(function.getUri()),
            		function.getId().toString());
            String appInfoJson = jsonMapper.toJson(function);
            valueOps.set(RedisKeyHelper.functionId(function.getId().toString()), appInfoJson);
        }
        
	}

	@Override
	public void delete(String id) {
		this.template.delete(RedisKeyHelper.functionId(id));
		this.template.delete(RedisKeyHelper.allTreeNode());
	}

	@Override
	public FunctionEO findRootFunction() {
		return this.findFunctionById("ROOT");
	}

	@Override
	public void saveRoot(FunctionEO function) {
		if(function == null){
			return;
		}
		
        if (StringUtils.isNotBlank(function.getId().toString())) {
            // 设置url对应的id
            valueOps.set(RedisKeyHelper.functionUri(function.getUri()),
            		${ConstantClassName}.ROOT);
            String appInfoJson = jsonMapper.toJson(function);
            valueOps.set(RedisKeyHelper.functionId(${ConstantClassName}.ROOT), appInfoJson);
        }
	}

	@Override
	public TreeNode<FunctionEO, TreeNode> findAllTreeNode() {
		String allTreeNodeStr = valueOps.get(RedisKeyHelper.allTreeNode());
		TreeNode allTreeNode = jsonMapper.fromJson(allTreeNodeStr, TreeNode.class);
        return allTreeNode;
	}

	@Override
	public void saveAllTreeNode(TreeNode<FunctionEO, TreeNode> allTreeNode) {
		String allTreeNodeStr = jsonMapper.toJson(allTreeNode);
        valueOps.set(RedisKeyHelper.allTreeNode(), allTreeNodeStr);
	}
}
