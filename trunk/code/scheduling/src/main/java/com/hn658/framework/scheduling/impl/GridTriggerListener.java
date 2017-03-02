package com.hn658.framework.scheduling.impl;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
/**
 * 
 * 监听定时任务的执行情况和执行数序
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:ningyu,date:2013-3-29 下午3:00:19,content:TODO </p>
 * @author ningyu
 * @date 2013-3-29 下午3:00:19
 * @since
 * @version
 */
public class GridTriggerListener implements TriggerListener {
	//触发器监听名称
    private static final String LISTENER_NAME = "GRID_TRIGGER_LISTENER";

    @Override
    public String getName() {
        return LISTENER_NAME;
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {

    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {

    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, int triggerInstructionCode) {

    }

}
