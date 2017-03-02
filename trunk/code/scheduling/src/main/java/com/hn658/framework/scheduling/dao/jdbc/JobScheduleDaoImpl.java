package com.hn658.framework.scheduling.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hn658.framework.scheduling.dao.JobScheduleDao;
import com.hn658.framework.scheduling.domain.JobSchedule;
import com.hn658.framework.shared.utils.UUIDUtils;

/**
 * 作业实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0</p>
 * @author bulong.xu
 * @date 2012-10-12 下午5:17:37
 * @since
 * @version
 */
public class JobScheduleDaoImpl extends JobDaoBase implements JobScheduleDao {
    private Log log = LogFactory.getLog(JobScheduleDaoImpl.class);
    
    /**
     * <p>创建一个新的实例 JobScheduleDaoImpl.TODO</p>
     * @param dataSourceName
     */
    public JobScheduleDaoImpl(String dataSourceName) {
        super(dataSourceName);
    }

    /** 
     * <p>持久化作业调度</p> 
     * @param js 作业对象
     * @see com.deppon.foss.framework.server.components.jobgrid.dao.JobScheduleDao#insert(com.deppon.foss.framework.server.components.jobgrid.domain.JobSchedule)
     */
    @Override
    public void insert(JobSchedule js) {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(INSRET_JOB_SCHEDULE);
            ps.setString(1, UUIDUtils.getUUID().toString());
            ps.setString(2, js.getTriggerGroup());
            ps.setString(3, js.getTriggerName());
            ps.setString(4, js.getJobGroup());
            ps.setString(5, js.getJobName());
            ps.setString(6, js.getDescription());
            ps.setInt(7, js.getTriggerType());
            ps.setString(8, js.getTriggerExpression());
            ps.setString(9, js.getJobClass());
            ps.setString(10, js.getJobData());
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("insert schedule error", e);
        } finally {
            closeStatement(ps);
            closeConnection(conn);
        }
    }

    /** 
     * <p>修改作业调度</p> 
     * @param js 
     * @see com.deppon.foss.framework.server.components.jobgrid.dao.JobScheduleDao#update(com.deppon.foss.framework.server.components.jobgrid.domain.JobSchedule)
     */
    @Override
    public void update(JobSchedule js) {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(UPDATE_SCHEDULE_BY_PK);
            ps.setString(1, js.getTriggerGroup());
            ps.setString(2, js.getTriggerName());
            ps.setString(3, js.getJobGroup());
            ps.setString(3, js.getJobName());
            ps.setString(4, js.getDescription());
            ps.setInt(6, js.getTriggerType());
            ps.setString(7, js.getTriggerExpression());
            ps.setString(8, js.getJobClass());
            ps.setString(9, js.getJobData());
            ps.setString(10, js.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("update schedule error", e);
        } finally {
            closeStatement(ps);
            closeConnection(conn);
        }
    }

    /** 
     * <p>查询作业调度</p> 
     * @param cluster 
     * @return 
     * @see com.deppon.foss.framework.server.components.jobgrid.dao.JobScheduleDao#queryByCluster(java.lang.String)
     */
    @Override
    public List<JobSchedule> queryAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        List<JobSchedule> list = new ArrayList<JobSchedule>();
        try {
            conn = getConnection();
            ps = conn.prepareStatement(QUERY_SCHEDULE_BY_CLUSTER);
            rs = ps.executeQuery();
            while (rs.next()) {
                JobSchedule js = new JobSchedule();
                js.setId(rs.getString(1));
                js.setTriggerGroup(rs.getString(2));
                js.setTriggerName(rs.getString(3));
                js.setJobGroup(rs.getString(4));
                js.setJobName(rs.getString(5));
                js.setDescription(rs.getString(6));
                js.setTriggerType(rs.getInt(7));
                js.setTriggerExpression(rs.getString(8));
                js.setJobClass(rs.getString(9));
                js.setJobData(rs.getString(10));
                list.add(js);
            }
        } catch (SQLException e) {
            log.error("queryAll schedule error", e);
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(conn);
        }
        return list;
    }
    
    /** 
     * <p>查询作业调度</p> 
     * @param cluster 
     * @return 
     * @see com.deppon.foss.framework.server.components.jobgrid.dao.JobScheduleDao#queryByCluster(java.lang.String)
     */
    @Override
    public JobSchedule querySchedule(String jobName, String jobGroup) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        JobSchedule JobSchedule = new JobSchedule();
        try {
            conn = getConnection();
            ps = conn.prepareStatement(QUERY_SCHEDULE_BY_TRIGGER);
            ps.setString(1, jobGroup);
            ps.setString(2, jobName);
            rs = ps.executeQuery();
            while (rs.next()) {
                JobSchedule.setId(rs.getString(1));
                JobSchedule.setTriggerGroup(rs.getString(2));
                JobSchedule.setTriggerName(rs.getString(3));
                JobSchedule.setJobGroup(rs.getString(4));
                JobSchedule.setJobName(rs.getString(5));
                JobSchedule.setDescription(rs.getString(6));
                JobSchedule.setTriggerType(rs.getInt(7));
                JobSchedule.setTriggerExpression(rs.getString(8));
                JobSchedule.setJobClass(rs.getString(9));
                JobSchedule.setJobData(rs.getString(10));
            }
        } catch (SQLException e) {
            log.error("queryAll schedule error", e);
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(conn);
        }
        return JobSchedule;
    }
    
    /**
     * 
     * <p>查询作业</p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午9:20:43
     * @param jobGroup 作业分组名称
     * @param jobName  作业名称
     * @see com.deppon.foss.framework.server.components.jobgrid.dao.JobScheduleDao#deleteByJob(java.lang.String, java.lang.String)
     */
    @Override
    public void deleteByJob(String jobGroup, String jobName) {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(DELETE_SCHEDLE_BY_JOB);
            ps.setString(1, jobGroup);
            ps.setString(2, jobName);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("deleteByJob schedule error", e);
        } finally {
            closeStatement(ps);
            closeConnection(conn);
        }
    }
    
    /**
     * 
     * <p>删除触发器</p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午9:21:27
     * @param triggerGroup 触发器分组名称
     * @param triggerName  触发器名称
     * @see com.deppon.foss.framework.server.components.jobgrid.dao.JobScheduleDao#deleteByTrigger(java.lang.String, java.lang.String)
     */
    @Override
    public void deleteByTrigger(String triggerGroup, String triggerName) {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(DELETE_SCHEDLE_BY_TRIGGER);
            ps.setString(1, triggerGroup);
            ps.setString(2, triggerName);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("deleteByTrigger schedule error", e);
        } finally {
            closeStatement(ps);
            closeConnection(conn);
        }
    }

}
