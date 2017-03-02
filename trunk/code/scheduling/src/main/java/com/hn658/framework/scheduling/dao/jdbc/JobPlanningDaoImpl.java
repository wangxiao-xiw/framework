package com.hn658.framework.scheduling.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hn658.framework.scheduling.dao.JobPlanningDao;
import com.hn658.framework.scheduling.domain.JobPlanning;
/**
 * 
 *  作业调度实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:ningyu,date:2013-4-3 上午10:15:37,content:TODO </p>
 * @author 平台开发小组
 * @date 2013-4-3 上午10:15:37
 * @since
 * @version
 */
public class JobPlanningDaoImpl extends JobDaoBase implements JobPlanningDao {
    private Log log = LogFactory.getLog(JobPlanningDaoImpl.class);
    
    public JobPlanningDaoImpl(String dataSourceName) {
        super(dataSourceName);
    }

    /**
     * 
     * <p>查询集群作业计划</p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午9:16:15
     * @param conn  数据库连接
     * @param instanceId  作业UD
     * @return 
     * @see com.deppon.foss.framework.server.components.jobgrid.dao.JobPlanningDao#queryByInstance(java.sql.Connection, java.lang.String)
     */
    @Override
    public List<JobPlanning> queryByInstance(Connection conn, String instanceId) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<JobPlanning> list = new ArrayList<JobPlanning>();
        try {            
            ps = conn.prepareStatement(QUERY_PLANNING_BY_CLUSTER);
            ps.setString(1, instanceId);
            rs = ps.executeQuery();
            while (rs.next()) {
                JobPlanning plan = new JobPlanning();
                plan.setInstanceId(rs.getString(1));
                plan.setScopeType(rs.getInt(2));
                plan.setScopeName(rs.getString(3));
                plan.setAccessRule(rs.getInt(4));
                list.add(plan);
            }
        } catch (SQLException e) {
            log.error("queryByInstance error", e);
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
        }
        return list;
    }

    /**
     * 
     * <p>查询作业集群计划</p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午9:17:12
     * @param instanceId 作业ID
     * @return 
     * @see com.deppon.foss.framework.server.components.jobgrid.dao.JobPlanningDao#queryByInstance(java.lang.String)
     */
    @Override
    public List<JobPlanning> queryByInstance(String instanceId) {
        Connection conn = null;
        try {
            conn = getConnection();
            return queryByInstance(conn, instanceId);
        } catch (SQLException e) {
            log.error("queryByInstance error", e);
        } finally {
            closeConnection(conn);
        }
        return new ArrayList<JobPlanning>();
    }

}
