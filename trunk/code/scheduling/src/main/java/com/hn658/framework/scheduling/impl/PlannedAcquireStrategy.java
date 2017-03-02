package com.hn658.framework.scheduling.impl;

import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.quartz.Trigger;

import com.hn658.framework.scheduling.AcquireStrategy;
import com.hn658.framework.scheduling.dao.JobPlanningDao;
import com.hn658.framework.scheduling.dao.jdbc.JobPlanningDaoImpl;
import com.hn658.framework.scheduling.domain.JobPlanning;

public class PlannedAcquireStrategy implements AcquireStrategy {
	//允许的规则
    private static final int RULES_ALLOW = 1;
    //允许的分组范围
    private static final int SCOPE_GROUP = 1;
    //允许的作业分组
    private Set<String> allowedJobGroup;
    //允许的作业
    private Set<String> allowedJob;
    //拒绝的作业分组
    private Set<String> deniedJobGroup;
    //拒绝的作业
    private Set<String> deniedJob;

    /**
     * 
     * <p>判断一个触发器是否是允许的范围内</p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午10:05:46
     * @param instanceId 作业ID
     * @param conn 数据库连接
     * @param trigger 触发器
     * @return 
     * @see com.deppon.foss.framework.server.components.jobgrid.AcquireStrategy#isAcquire(java.lang.String, java.sql.Connection, org.quartz.Trigger)
     */
    @Override
    public boolean isAcquire(String instanceId, Connection conn, Trigger trigger) {
        initAccessRules(conn, instanceId);
        if (deniedJob.contains(trigger.getJobName()) || deniedJobGroup.contains(trigger.getJobGroup())) {
            return false;
        }
        if (allowedJob.contains(trigger.getJobName()) || allowedJobGroup.contains(trigger.getJobGroup())) {
            return true;
        }
        return false;
    }
    /**
     * 
     * <p>实例化作业分组，想作业分组中添加计划</p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午10:06:48
     * @param conn 数据库连接
     * @param instanceId  作业ID
     * @see
     */
    protected void initAccessRules(Connection conn, String instanceId) {
        if (null == allowedJobGroup) {
            JobPlanningDao planDao = new JobPlanningDaoImpl("");
            List<JobPlanning> rules = planDao.queryByInstance(conn, instanceId);

            allowedJobGroup = new HashSet<String>();
            allowedJob = new HashSet<String>();
            deniedJobGroup = new HashSet<String>();
            deniedJob = new HashSet<String>();
            for (JobPlanning rule : rules) {
                if (SCOPE_GROUP == rule.getScopeType()) {
                    if (RULES_ALLOW == rule.getAccessRule()) {
                        allowedJobGroup.add(rule.getScopeName());
                    } else {
                        deniedJobGroup.add(rule.getScopeName());
                    }
                } else {
                    if (RULES_ALLOW == rule.getAccessRule()) {
                        allowedJob.add(rule.getScopeName());
                    } else {
                        deniedJob.add(rule.getScopeName());
                    }
                }
            }
        }
    }
}
