package com.hn658.framework.scheduling.dao;

import java.sql.Connection;
import java.util.List;

import com.hn658.framework.scheduling.domain.JobPlanning;
/**
 * 
 * 任务执行计划查询
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:平台开发小组,date:2013-1-22 上午8:08:07,content: </p>
 * @author 平台开发小组
 * @date 2013-1-22 上午8:08:07
 * @since
 * @version
 */
public interface JobPlanningDao {    
    List<JobPlanning> queryByInstance(String instanceId);

    List<JobPlanning> queryByInstance(Connection conn, String instanceId);
}
