package com.hn658.framework.scheduling.impl;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.spi.SchedulerPlugin;

import com.hn658.framework.scheduling.dao.JobScheduleDao;
import com.hn658.framework.scheduling.dao.jdbc.JDBCContants;
import com.hn658.framework.scheduling.dao.jdbc.JobScheduleDaoImpl;
import com.hn658.framework.scheduling.domain.JobSchedule;
/**
 * 
 * 网格作业加载插件
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:ningyu,date:2013-4-3 上午10:15:05,content:TODO </p>
 * @author 平台开发小组
 * @date 2013-4-3 上午10:15:05
 * @since
 * @version
 */
public class GridJobLoaderPlugin implements SchedulerPlugin {
	//调度对象
    private Scheduler scheduler;
    //作业调度DAO层对象
    private JobScheduleDao jobScheduleDao;
    //数据库别名，获取连接
    private String dataSource;
    private Log log = LogFactory.getLog(GridJobLoaderPlugin.class);
    @Override
    public void initialize(String name, Scheduler scheduler) throws SchedulerException {
        this.scheduler = scheduler;
    }

    /**
     * 
     * <p>作业初始化</p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午9:37:19 
     * @see org.quartz.spi.SchedulerPlugin#start()
     */
    @Override
    public void start() {
        JobScheduleDao jsd = getJobScheduleDao();
        List<JobSchedule> jss = jsd.queryAll();
        for (JobSchedule js : jss) {
            // 更新scheduler中的任务数据
            JobDetail jd = fromJobSchedule(js);
            if (null == jd) {
                continue;
            }
            try {
                Trigger oldTrigger = scheduler.getTrigger(js.getTriggerName(), js.getTriggerGroup());                
                int triggerStatus = scheduler.getTriggerState(js.getTriggerName(), js.getTriggerGroup());  
                if (null != oldTrigger) {
                    scheduler.addJob(jd, true);
                    scheduler.unscheduleJob(oldTrigger.getName(), oldTrigger.getGroup());
                }
                // 如果任务没有运行加入到计划中去
                Trigger newTrigger = createTrigger(js);
                if (null != newTrigger) {
                    scheduler.scheduleJob(jd, newTrigger);
                }
                // 如果先前状态已经暂停，那么设置新任务暂停
                if(triggerStatus==Trigger.STATE_PAUSED){
                	scheduler.pauseJob(js.getJobName(), js.getJobGroup());
                }
            } catch (SchedulerException e) {
                log.error("schedule job error", e);
            }
        }

    }

    /**
     * 
     * <p>创建触发器</p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午9:38:27
     * @param js
     * @return
     * @see
     */
    private Trigger createTrigger(JobSchedule js) {
        try {
            Trigger trigger = null;
            if (JDBCContants.TRIGGER_TYPE_SIMPLE == js.getTriggerType()) {
                String expr = js.getTriggerExpression();
                int splitIndex = expr.indexOf(":");
                int repeatCount = Integer.valueOf(expr.substring(0, splitIndex));
                long repeatInterval = Long.valueOf(expr.substring(splitIndex + 1));
                trigger = new SimpleTrigger(js.getTriggerName(), js.getTriggerGroup(), repeatCount, repeatInterval);
            } else if (JDBCContants.TRIGGER_TYPE_CRON == js.getTriggerType()) {
                trigger = new CronTrigger(js.getTriggerName(), js.getTriggerGroup(), js.getTriggerExpression());
                //对没有处理的job不做任何处理
                trigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
            }
            return trigger;
        } catch (NumberFormatException e) {
        } catch (ParseException e) {
            log.error("invaild cron expression", e);
        }
        return null;
    }

    /**
     * 
     * <p>构建作业详细信息</p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午9:39:31
     * @param js
     * @return
     * @see
     */
    private JobDetail fromJobSchedule(JobSchedule js) {
        try {
            JobDetail jd = new JobDetail();
            jd.setJobClass(Class.forName(js.getJobClass()));
            jd.setDescription(js.getDescription());
            jd.setGroup(js.getJobGroup());
            jd.setName(js.getJobName());
            jd.setJobDataMap(GridJobUtils.fromString(js.getJobData()));
            return jd;
        } catch (ClassNotFoundException e) {
            log.info("can't find job class:" + js.getJobClass());
            return null;
        }
    }

    @Override
    public void shutdown() {

    }

    public void setJobScheduleDao(JobScheduleDao jobScheduleDao) {
        this.jobScheduleDao = jobScheduleDao;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSourceName) {
        this.dataSource = dataSourceName;
    }

    public JobScheduleDao getJobScheduleDao() {
        if (null == jobScheduleDao && null != dataSource) {
            jobScheduleDao = new JobScheduleDaoImpl(dataSource);
        }
        return jobScheduleDao;
    }

}
