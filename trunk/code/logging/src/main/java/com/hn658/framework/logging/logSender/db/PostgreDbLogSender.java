package com.hn658.framework.logging.logSender.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import com.hn658.framework.logging.enums.ColumnName;
import com.hn658.framework.logging.enums.TableName;
import com.hn658.framework.logging.model.LogInfoEO;

public class PostgreDbLogSender extends DBLogSender {
	
	protected String insertSQL;
	
	@Override
	public void start() {
		insertSQL = this.buildInsertSQL();
		super.start();
	}
	
	protected String buildInsertSQL(){
		StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ");
	    sqlBuilder.append("\"").append(TableName.T_FRAMEWORK_LOGGING).append("\"").append(" (");
	    sqlBuilder.append("\"").append(ColumnName.LEVEL).append("\"").append(", ");
	    sqlBuilder.append("\"").append(ColumnName.MESSAGE).append("\"").append(", ");
	    sqlBuilder.append("\"").append(ColumnName.REQUEST_ID).append("\"").append(", ");
	    sqlBuilder.append("\"").append(ColumnName.TYPE).append("\"").append(", ");
	    sqlBuilder.append("\"").append(ColumnName.CATEGORY).append("\"").append(", ");
	    sqlBuilder.append("\"").append(ColumnName.CRATED_DATETIME).append("\"").append(") ");
	    sqlBuilder.append("VALUES (?, ?, ?, ?, ?, ?)");
	    return sqlBuilder.toString();
	}

	@Override
	protected String getInsertSQL() {
		return insertSQL;
	}

	@Override
	protected void subInsertBatch(List<Object> msg, Connection connection,
			PreparedStatement prest) throws Throwable {
		for (int x = 0; x < msg.size(); x++) {
			LogInfoEO logInfo = (LogInfoEO) msg.get(x);
			prest.setString(LEVEL, logInfo.getLevel());
			prest.setString(MESSAGE, logInfo.getMessage());
			prest.setString(REQUEST_ID, logInfo.getRequestId());
			prest.setString(TYPE, logInfo.getLogType());
			prest.setString(CATEGORY, logInfo.getCategory());
			prest.setTimestamp(CRATED_DATETIME, logInfo.getCreatedDateTime());				
			prest.addBatch();
		}
		prest.executeBatch();    //执行批处理 
	}

}
