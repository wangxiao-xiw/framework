package com.hn658.framework.scheduling.dao;

import java.util.List;

import com.hn658.framework.scheduling.domain.JobSchedule;
/**
 * 定义定时任务的公共服务接口
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:平台开发小组,date:2013-1-22 上午8:06:28,content: </p>
 * @author 平台开发小组
 * @date 2013-1-22 上午8:06:28
 * @since
 * @version
 */
public interface JobScheduleDao {
    
    void insert(JobSchedule js);

    void update(JobSchedule js);
    
    void deleteByJob(String jobName, String triggerName);
    
    List<JobSchedule> queryAll();
    
    JobSchedule querySchedule(String jobName, String jobGroup);

    void deleteByTrigger(String triggerGroup, String triggerName);
}
