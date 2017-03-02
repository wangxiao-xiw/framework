/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		OffsetLimitInterceptor
 *	包	名：		com.wzitech.chaos.framework.server.dataaccess.mybatis.plugin
 *	项目名称：	chaos-dataaccess 
 *	作	者：		Shawn
 *	创建时间：	2012-4-20
 *	描	述：		Mybatis物理分页拦截器
 *	更新纪录：	1. Shawn 创建于 2012-4-20 上午2:11:20
 * 				
 ************************************************************************************/
package com.hn658.framework.dataaccess.mybatis.plugin;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hn658.framework.dataaccess.dialect.AbstractBaseDialect;
import com.hn658.framework.shared.utils.PropertiesHelper;

/**
 * Mybatis物理分页拦截器
 * 将拦截Executor.query()方法实现分页方言的插入.
 * 配置文件内容:
 * <pre>
 * 	&lt;plugins>
 *		&lt;plugin interceptor="com.wzitech.chaos.framework.server.dataaccess.mybatis.plugin.OffsetLimitInterceptor">
 *			&lt;property name="dialectClass" value="com.wzitech.chaos.framework.server.dataaccess.dialect.PostgreSQLDialect"/>
 *		&lt;/plugin>
 *	&lt;/plugins>
 * </pre>
 * @author Shawn
 *
 */
@Intercepts({@Signature(
		type= Executor.class,
		method = "query",
		args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class OffsetLimitInterceptor implements Interceptor {
	private static final Logger logger = LoggerFactory.getLogger(OffsetLimitInterceptor.class);
	
	static int MAPPED_STATEMENT_INDEX = 0;
	static int PARAMETER_INDEX = 1;
	static int ROWBOUNDS_INDEX = 2;
	static int RESULT_HANDLER_INDEX = 3;
	
	AbstractBaseDialect dialect;
	
	/* (non-Javadoc)
	 * @see org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin.Invocation)
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		processIntercept(invocation.getArgs());
		return invocation.proceed();
	}

	private void processIntercept(final Object[] queryArgs) {
		//queryArgs = query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler)
		MappedStatement ms = (MappedStatement)queryArgs[MAPPED_STATEMENT_INDEX];
		Object parameter = queryArgs[PARAMETER_INDEX];
		final RowBounds rowBounds = (RowBounds)queryArgs[ROWBOUNDS_INDEX];
		int offset = rowBounds.getOffset();
		int limit = rowBounds.getLimit();
		
		if(dialect.supportsLimit() && (offset != RowBounds.NO_ROW_OFFSET || limit != RowBounds.NO_ROW_LIMIT)) {
			BoundSql boundSql = ms.getBoundSql(parameter);
			String sql = boundSql.getSql().trim();
			if (dialect.supportsLimitOffset()) {
				sql = dialect.getLimitString(sql, offset, limit);
				offset = RowBounds.NO_ROW_OFFSET;
			} else {
				sql = dialect.getLimitString(sql, 0, limit);
			}
			limit = RowBounds.NO_ROW_LIMIT;
			
			queryArgs[ROWBOUNDS_INDEX] = new RowBounds(offset,limit);
			BoundSql newBoundSql = new BoundSql(ms.getConfiguration(),sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
			try {
				Field field = BoundSql.class.getDeclaredField("additionalParameters");//这个对应的是属性
				field.setAccessible(true);
				@SuppressWarnings("unchecked")
				Map<String, Object> fieldValue = (Map<String, Object>) field.get(boundSql);//属性的值
				for (Entry<String, Object> entry : fieldValue.entrySet()) {
					newBoundSql.setAdditionalParameter(entry.getKey(), entry.getValue());
				}
			} catch (Exception e) {
				logger.debug("BoundSql属性获取异常", e);
			}
			MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
			queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
		}
	}
	
	// see: MapperBuilderAssistant
	private MappedStatement copyFromMappedStatement(MappedStatement ms,
			SqlSource newSqlSource) {
		Builder builder = new MappedStatement.Builder(ms.getConfiguration(),
				ms.getId(), newSqlSource, ms.getSqlCommandType());

		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		
		/*
		 * mybatis 3.1中将getKeyProperty重构为了getKeyProperties
		 * 从源码中看到使用了","做了字符串的分割处理
		 * 所以这里使用String[] join "," 再将字符串还原
		 * By Shawn 2012-4-20
		 */
		// builder.keyProperty(ms.getKeyProperty());
		builder.keyProperty(StringUtils.join(ms.getKeyProperties(), ","));

		// setStatementTimeout()
		builder.timeout(ms.getTimeout());

		// setStatementResultMap()
		builder.parameterMap(ms.getParameterMap());

		// setStatementResultMap()
		builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());

		// setStatementCache()
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());

		return builder.build();
	}
		
	/* (non-Javadoc)
	 * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
	 */
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties)
	 */
	@Override
	public void setProperties(Properties properties) {
		String dialectClass = new PropertiesHelper(properties).getRequiredString("dialectClass");
		try {
			dialect = (AbstractBaseDialect)Class.forName(dialectClass).newInstance();
		} catch (Exception e) {
			logger.error("不能创建方言实列：{}", dialectClass);
			throw new RuntimeException(String.format("不能创建方言实列：%s", dialectClass), e);
		}
		
		logger.info("MyBatis OffsetLimitInterceptor Dialect is {}", dialectClass);
	}

	public static class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;
		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}
		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}
}
