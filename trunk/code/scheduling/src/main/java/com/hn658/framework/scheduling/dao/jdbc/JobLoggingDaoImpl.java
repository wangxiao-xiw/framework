package com.hn658.framework.scheduling.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hn658.framework.scheduling.dao.JobLoggingDao;
import com.hn658.framework.scheduling.domain.JobLogging;
import com.hn658.framework.shared.utils.UUIDUtils;

public class JobLoggingDaoImpl extends JobDaoBase implements JobLoggingDao {
    private Log logs = LogFactory.getLog(JobLoggingDaoImpl.class);
    
    public JobLoggingDaoImpl(String dataSourceName) {
        super(dataSourceName);
    }

    /**
     * 
     * <p>插入作业日志</p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午9:14:16
     * @param log 
     * @see com.deppon.foss.framework.server.components.jobgrid.dao.JobLoggingDao#insert(com.deppon.foss.framework.server.components.jobgrid.domain.JobLogging)
     */
    @Override
    synchronized public void insert(JobLogging log) {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(INSERT_JOB_LOGGING);
            ps.setString(1, UUIDUtils.getUUID().toString());
            ps.setString(2, log.getInstanceId());
            ps.setString(3, log.getTriggerGroup());
            ps.setString(4, log.getTriggerName());
            ps.setString(5, log.getJobGroup());
            ps.setString(6, log.getJobName());

            ps.setTimestamp(7, new Timestamp(log.getFiredTime().getTime()));
            ps.setString(8, log.getJobAction());
            ps.setString(9, log.getFlowUuid());
            ps.setString(10, log.getErrorMessage());

            ps.executeUpdate();
        } catch (SQLException e) {
            logs.error("insert log error", e);
        } finally {
            closeStatement(ps);
            closeConnection(conn);
        }
    }

    /**
     * 
     * <p>查询作业日志</p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午9:14:56
     * @param cluster 集群
     * @param jobName 作业名称
     * @return 
     * @see com.deppon.foss.framework.server.components.jobgrid.dao.JobLoggingDao#queryByJob(java.lang.String, java.lang.String)
     */
    @Override
    public List<JobLogging> queryByJob(String cluster, String jobName) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 
     * <p>查询触发器</p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午9:15:29
     * @param cluster 集群
     * @param jobName 作业名称
     * @return 
     * @see com.deppon.foss.framework.server.components.jobgrid.dao.JobLoggingDao#queryByTrigger(java.lang.String, java.lang.String)
     */
    @Override
    public List<JobLogging> queryByTrigger(String cluster, String jobName) {
        // TODO Auto-generated method stub
        return null;
    }

}
