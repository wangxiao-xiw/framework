package com.hn658.framework.scheduling.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.hn658.framework.scheduling.dao.JobLoggingDao;
import com.hn658.framework.scheduling.domain.JobLogging;
import com.hn658.framework.shared.utils.UUIDUtils;

/**
 * 
 * 作业调度的持久化实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:平台开发小组,date:2013-1-22 上午9:28:25,content:TODO </p>
 * @author 平台开发小组
 * @date 2013-1-22 上午9:28:25
 * @since
 * @version
 */
public class GridJobListener implements JobListener, ApplicationContextAware {
    public static final String APPLICATION_CONTEXT = "SPRING_CONTEXT";
    private static final String LISTENER_NAME = "GRID_JOB_LISTENER";
    private Log log = LogFactory.getLog(GridJobListener.class);
    private ApplicationContext appContext;
    private JobLoggingDao loggingDao;

    public void setLoggingDao(JobLoggingDao loggingDao) {
        this.loggingDao = loggingDao;
    }

    @Override
    public String getName() {
        return LISTENER_NAME;
    }

    /**
     * 
     * <p>作业日志记录的实现</p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午9:29:57
     * @param context JobExecutionContext
     * @see org.quartz.JobListener#jobToBeExecuted(org.quartz.JobExecutionContext)
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        context.put(APPLICATION_CONTEXT, appContext);
        try {
            String flowId = UUIDUtils.getUUID().toString();
            context.put("FLOW_UUID", flowId);
            JobLogging log = createLogging(context, "EXECUTING", flowId, null);
            loggingDao.insert(log);
        } catch (Exception e) {
            log.error("jobToBeExecuted", e);
        }
    }

    /**
     * 
     * <p>作业日志记录的实现</p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午9:30:35
     * @param context 
     * @see org.quartz.JobListener#jobExecutionVetoed(org.quartz.JobExecutionContext)
     */
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        String flowId = (String) context.get("FLOW_UUID");
        JobLogging log = createLogging(context, "VETOED", flowId, null);
        loggingDao.insert(log);
    }

    /**
     * 
     * <p>作业日志记录的实现</p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午9:32:04
     * @param context
     * @param jobException 
     * @see org.quartz.JobListener#jobWasExecuted(org.quartz.JobExecutionContext, org.quartz.JobExecutionException)
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        String flowId = (String) context.get("FLOW_UUID");
        String errMessage = null;
        if (null != jobException) {
            errMessage = jobException.getMessage();
        }
        JobLogging log = createLogging(context, "EXECUTED", flowId, errMessage);
        loggingDao.insert(log);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;
    }

    /**
     * 
     * <p>创建作业日志对象</p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午9:32:45
     * @param ctx
     * @param action 执行动作
     * @param flowId 流水号
     * @param errMessage 错误信息
     * @return
     * @see
     */
    private JobLogging createLogging(JobExecutionContext ctx, String action, String flowId, String errMessage) {
        JobLogging log = new JobLogging();
        Scheduler sch = ctx.getScheduler();
        log.setInstanceId(GridJobUtils.getInstanceId(sch));
        log.setFiredTime(new Date());
        log.setJobAction(action);
        log.setJobGroup(ctx.getJobDetail().getGroup());
        log.setJobName(ctx.getJobDetail().getName());
        log.setTriggerGroup(ctx.getTrigger().getGroup());
        log.setTriggerName(ctx.getTrigger().getName());
        log.setErrorMessage(errMessage);
        log.setFlowUuid(flowId);
        return log;
    }
}
