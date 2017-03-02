package com.hn658.framework.scheduling;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;

import com.hn658.framework.scheduling.dao.JobLoggingDao;
import com.hn658.framework.scheduling.dao.JobScheduleDao;
import com.hn658.framework.scheduling.dao.jdbc.JDBCContants;
import com.hn658.framework.scheduling.dao.jdbc.JobLoggingDaoImpl;
import com.hn658.framework.scheduling.dao.jdbc.JobScheduleDaoImpl;
import com.hn658.framework.scheduling.domain.JobSchedule;
import com.hn658.framework.scheduling.impl.GridJobListener;
import com.hn658.framework.scheduling.impl.GridJobUtils;
import com.hn658.framework.scheduling.impl.GridTriggerListener;
import com.hn658.framework.shared.utils.UUIDUtils;

/**
 * 任务集群节点
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0</p>
 * @author bulong.xu
 * @date 2012-11-13 上午8:51:19
 * @since
 * @version
 */
public class JobGridNode implements ApplicationContextAware, InitializingBean {
	//网格触发器监听
    private GridTriggerListener gobalTriggerListener;
    //网格作业监听
    private GridJobListener gobalJobListener;
    //Spring上下文
    private ApplicationContext context;
    //数据库别名
    private String dataSourceName = "myDS";
    //作业管理DAO
    private JobScheduleDao scheduleDao;
    //作业调度
    private Scheduler scheduler;

    protected JobScheduleDao getScheduleDao() {
        return scheduleDao;
    }

    protected void setScheduleDao(JobScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    /**
     * 
     * <p>spring容器加载之后执行此方法</p> 
     * @author 平台开发小组
     * @date 2013-1-22 下午1:48:13
     * @throws Exception 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        gobalJobListener = new GridJobListener();
        gobalJobListener.setApplicationContext(context);
        JobLoggingDao logDao = new JobLoggingDaoImpl(getDataSourceName());
        gobalJobListener.setLoggingDao(logDao);
        scheduler.addGlobalJobListener(gobalJobListener);

        gobalTriggerListener = new GridTriggerListener();
        scheduler.addGlobalTriggerListener(gobalTriggerListener);

        if (null == scheduleDao) {
            scheduleDao = new JobScheduleDaoImpl(getDataSourceName());
        }
    }

    /**
     * 
     * <p>创建一个作业节点</p> 
     * @author 平台开发小组
     * @date 2013-1-22 下午1:53:58
     * @param context
     * @param scheduler
     * @return
     * @throws Exception
     * @see
     */
    public static JobGridNode createNode(ApplicationContext context, Scheduler scheduler) throws Exception {
        JobGridNode node = new JobGridNode();
        node.setApplicationContext(context);
        node.setScheduler(scheduler);
        node.afterPropertiesSet();
        return node;
    }

    /**
     * 
     * <p>获取作业实例ID</p> 
     * @author 平台开发小组
     * @date 2013-1-22 下午1:57:52
     * @return
     * @throws SchedulerException
     * @see
     */
    public String getInstanceId() throws SchedulerException {
        return scheduler.getSchedulerInstanceId();
    }

    /**
     * 
     * <p>获取作业集群名称</p> 
     * @author 平台开发小组
     * @date 2013-1-22 下午1:58:20
     * @return
     * @throws SchedulerException
     * @see
     */
    public String getClusterName() throws SchedulerException {
        return scheduler.getSchedulerName();
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * 
     * <p>添加一个作业</p> 
     * @author 平台开发小组
     * @date 2013-1-22 下午1:58:51
     * @param jobDetail 作业详细信息
     * @param replace  是否替换
     * @throws SchedulerException
     * @see
     */
    public void addJob(JobDetail jobDetail, boolean replace) throws SchedulerException {
        scheduler.addJob(jobDetail, replace);
    }

    /**
     * 
     * <p>删除一个作业</p> 
     * @author 平台开发小组
     * @date 2013-1-22 下午2:07:35
     * @param jobName 作业名称
     * @param groupName 作业分组名称
     * @return
     * @throws SchedulerException
     * @see
     */
    public boolean deleteJob(String jobName, String groupName) throws SchedulerException {
        return scheduler.deleteJob(jobName, groupName);
    }

    /**
     * 
     * <p>为作业设置触发器</p> 
     * @author 平台开发小组
     * @date 2013-1-22 下午2:09:03
     * @param jobName
     * @param groupName
     * @throws SchedulerException
     * @see
     */
    public void triggerJob(String jobName, String groupName) throws SchedulerException {
        scheduler.triggerJob(jobName, groupName);
    }

    /**
     * 
     * <p>获取当前执行的</p> 
     * @author 平台开发小组
     * @date 2013-1-22 下午2:19:03
     * @return
     * @throws SchedulerException
     * @see
     */
    public List<?> getCurrentlyExecutingJobs() throws SchedulerException {
        return scheduler.getCurrentlyExecutingJobs();
    }

    public String[] getJobGroupNames() throws SchedulerException {
        return scheduler.getJobGroupNames();
    }

    public String[] getJobNames(String groupName) throws SchedulerException {
        return scheduler.getJobNames(groupName);
    }

    public Set<?> getPausedTriggerGroups() throws SchedulerException {
        return scheduler.getPausedTriggerGroups();
    }

    public String getSchedulerName() throws SchedulerException {
        return scheduler.getSchedulerName();
    }

    public Trigger[] getTriggersOfJob(String jobName, String groupName) throws SchedulerException {
        return scheduler.getTriggersOfJob(jobName, groupName);
    }

    public Trigger getTrigger(String triggerName, String triggerGroup) throws SchedulerException {
        return scheduler.getTrigger(triggerName, triggerGroup);
    }

    public String[] getTriggerNames(String groupName) throws SchedulerException {
        return scheduler.getTriggerNames(groupName);
    }

    public int getTriggerState(String triggerName, String triggerGroup) throws SchedulerException {
        return scheduler.getTriggerState(triggerName, triggerGroup);
    }

    public void pauseAll() throws SchedulerException {
        scheduler.pauseAll();
    }
    
    /**
     * 停止任务
     * @param jobName
     * @param groupName
     * @throws SchedulerException
     */
    public void pauseJob(String jobName, String groupName) throws SchedulerException {
        scheduler.pauseJob(jobName, groupName);
    }

    public void pauseJobGroup(String groupName) throws SchedulerException {
        scheduler.pauseJobGroup(groupName);
    }

    public void pauseTrigger(String triggerName, String groupName) throws SchedulerException {
        scheduler.pauseTrigger(triggerName, groupName);
    }

    public void pauseTriggerGroup(String groupName) throws SchedulerException {
        scheduler.pauseTriggerGroup(groupName);
    }

    public void resumeAll() throws SchedulerException {
        scheduler.resumeAll();
    }
    
    /**
     * 恢复任务
     * @param jobName
     * @param groupName
     * @throws SchedulerException
     */
    public void resumeJob(String jobName, String groupName) throws SchedulerException {
        scheduler.resumeJob(jobName, groupName);
    }

    public void resumeJobGroup(String groupName) throws SchedulerException {
        scheduler.resumeJobGroup(groupName);
    }

    public void resumeTrigger(String triggerName, String groupName) throws SchedulerException {
        scheduler.resumeTrigger(triggerName, groupName);
    }

    public void resumeTriggerGroup(String groupName) throws SchedulerException {
        scheduler.resumeTriggerGroup(groupName);
    }

    public Date scheduleJob(JobDetail jobDetail, Trigger trigger) throws SchedulerException {
        Date result = scheduler.scheduleJob(jobDetail, trigger);
        JobSchedule js = createSchedule(jobDetail, trigger);
        scheduleDao.insert(js);
        return result;
    }

    private JobSchedule createSchedule(JobDetail jobDetail, Trigger trigger) {
        JobSchedule schedule = new JobSchedule();
//        schedule.setClusterName(GridJobUtils.getClusterName(scheduler));
        schedule.setDescription(jobDetail.getDescription());
        schedule.setJobClass(jobDetail.getJobClass().getName());
        schedule.setJobData(GridJobUtils.toPropertiesString(jobDetail.getJobDataMap()));
        schedule.setJobGroup(jobDetail.getGroup());
        schedule.setJobName(jobDetail.getName());
        if (trigger instanceof CronTrigger) {
            CronTrigger ct = (CronTrigger) trigger;
            schedule.setTriggerExpression(ct.getCronExpression());
            schedule.setTriggerGroup(ct.getGroup());
            schedule.setTriggerName(ct.getName());
            schedule.setTriggerType(JDBCContants.TRIGGER_TYPE_CRON);
        }
        return schedule;
    }
    
    /**
     * 删除任务
     * @param triggerName
     * @param groupName
     * @return
     * @throws SchedulerException
     */
    public boolean unscheduleJob(String triggerName, String groupName) throws SchedulerException {
        boolean hadUnscheduled = scheduler.unscheduleJob(triggerName, groupName);
        if (hadUnscheduled) {            
            scheduleDao.deleteByTrigger(groupName, triggerName);
        }
        return hadUnscheduled;
    }

    public void shutdown() throws SchedulerException {
        scheduler.shutdown();
    }

    public void start() throws SchedulerException {
        scheduler.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public void setDataSourceName(String dsName) {
        this.dataSourceName = dsName;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }
    
    /**
     * 添加任务
     * @param jobClass
     * @param jobName
     * @param jobData
     * @param triggerName
     * @param cronExpr
     * @return
     * @throws Exception
     */
    public <T extends GridJob> Date scheduleCronJob(Class<T> jobClass, String jobName, Properties jobData,
        String triggerName, String cronExpr) throws Exception {
        return scheduleCronJob(jobClass, jobName, "DEFAULT", "", jobData, triggerName, "DEFAULT", cronExpr);
    }

    public <T extends GridJob> Date scheduleCronJob(Class<T> jobClass, String cronExpr) throws Exception {
        String jobName = jobClass.getSimpleName() + "_" + UUIDUtils.getUUID().toString();
        String triggerName = "cron_" + UUIDUtils.getUUID().toString();
        return scheduleCronJob(jobClass, jobName, "DEFAULT", "", null, triggerName, "DEFAULT", cronExpr);
    }

    public <T extends GridJob> Date scheduleCronJob(Class<T> jobClass, Properties jobData, String cronExpr)
        throws Exception {
        String jobName = jobClass.getSimpleName() + "_" + UUIDUtils.getUUID().toString();
        String triggerName = "cron_" + UUIDUtils.getUUID().toString();
        return scheduleCronJob(jobClass, jobName, "DEFAULT", "", jobData, triggerName, "DEFAULT", cronExpr);
    }

    public <T extends GridJob> Date scheduleCronJob(Class<T> jobClass, String jobName, String jobGroup,
        String description, Properties jobData, String triggerName, String triggerGroup, String cronExpr)
        throws Exception {
        JobDetail jobDetail = new JobDetail(jobName, jobGroup, jobClass);
        jobDetail.setDescription(description);
        if (null != jobData) {
        	System.out.println(GridJobUtils.toJobDataMap(jobData));
            jobDetail.setJobDataMap(GridJobUtils.toJobDataMap(jobData));
        }
        CronTrigger trigger = new CronTrigger(triggerName, triggerGroup, cronExpr);
        return scheduleJob(jobDetail, trigger);
    }
    
    //查询任务
    public JobSchedule querySchedule(String jobName, String jobGroup){
    	return scheduleDao.querySchedule(jobName, jobGroup);
    }
    
    //更新任务
	@Transactional
    public void updateJob(String jobName, String jobGroup, String cronExpr) throws Exception{
    	JobSchedule jobSchedule = scheduleDao.querySchedule(jobName, jobGroup);
    	unscheduleJob(jobSchedule.getTriggerName(), jobSchedule.getTriggerGroup());
    	 Class onwClass = Class.forName(jobSchedule.getJobClass());
    	scheduleCronJob(onwClass, jobSchedule.getJobName(), jobSchedule.getJobGroup(), 
    			jobSchedule.getDescription(), null, jobSchedule.getTriggerName(),
    			jobSchedule.getTriggerGroup(), cronExpr);
    }
}
