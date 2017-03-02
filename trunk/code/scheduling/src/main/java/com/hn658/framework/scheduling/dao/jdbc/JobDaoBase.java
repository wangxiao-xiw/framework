package com.hn658.framework.scheduling.dao.jdbc;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.quartz.utils.DBConnectionManager;

public abstract class JobDaoBase implements JDBCContants {
	//数据库别名
    protected String dataSourceName;

    public JobDaoBase(String dataSourceName) {
        super();
        this.dataSourceName = dataSourceName;
    }

    /**
     * 
     * <p>通过数据库别名获取连接</p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午8:49:39
     * @return
     * @throws SQLException
     * @see
     */
    protected Connection getConnection() throws SQLException {
        return DBConnectionManager.getInstance().getConnection(dataSourceName);
    }

    /**
     * 
     * <p>关闭数据库连接</p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午8:50:10
     * @param conn
     * @see
     */
    protected void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            } catch (Throwable e) {
            }
        }
    }

    /**
     * 
     * <p>关闭数据库结果集</p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午8:50:34
     * @param rs
     * @see
     */
    protected void closeResultSet(ResultSet rs) {
        if (null != rs) {
            try {
                rs.close();
            } catch (SQLException ignore) {
            }
        }
    }

    /**
     * 
     * <p>关闭数据库预编译</p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午8:50:56
     * @param statement 预编译对象
     * @see
     */
    protected void closeStatement(Statement statement) {
        if (null != statement) {
            try {
                statement.close();
            } catch (SQLException ignore) {
            }
        }
    }

    /**
     * 
     * <p>为预编译设置boolean的值</p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午8:53:03
     * @param ps 预编译对象
     * @param index 索引值
     * @param val 设置值
     * @throws SQLException
     * @see
     */
    protected void setBoolean(PreparedStatement ps, int index, boolean val) throws SQLException {
        ps.setBoolean(index, val);
    }

   
    /**
     * 
     * <p>通过列名获取结果boolean值</p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午8:53:24
     * @param rs 结果集
     * @param columnName 表列名
     * @return
     * @throws SQLException
     * @see
     */
    protected boolean getBoolean(ResultSet rs, String columnName) throws SQLException {
        return rs.getBoolean(columnName);
    }

    /**
     * 
     * <p>通过列索引获取boolean值</p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午8:55:50
     * @param rs 结果集
     * @param columnIndex 列索引
     * @return
     * @throws SQLException
     * @see
     */
    protected boolean getBoolean(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getBoolean(columnIndex);
    }

    /**
     * 
     * <p></p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午8:57:53
     * @param ps 预编译对象
     * @param index 索引值
     * @param baos 字节流数组对象
     * @throws SQLException
     * @see
     */
    protected void setBytes(PreparedStatement ps, int index, ByteArrayOutputStream baos) throws SQLException {
        ps.setBytes(index, (baos == null) ? new byte[0] : baos.toByteArray());
    }

}
