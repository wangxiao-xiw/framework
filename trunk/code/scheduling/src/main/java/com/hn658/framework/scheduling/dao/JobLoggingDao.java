package com.hn658.framework.scheduling.dao;

import java.util.List;

import com.hn658.framework.scheduling.domain.JobLogging;
/**
 * 
 * 定时任务的日志接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:平台开发小组,date:2013-1-22 上午8:07:36,content: </p>
 * @author 平台开发小组
 * @date 2013-1-22 上午8:07:36
 * @since
 * @version
 */
public interface JobLoggingDao {

    void insert(JobLogging log);

    List<JobLogging> queryByJob(String cluster, String jobName);
    
    List<JobLogging> queryByTrigger(String cluster, String jobName);
}
