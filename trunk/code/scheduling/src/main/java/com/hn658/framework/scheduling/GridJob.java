package com.hn658.framework.scheduling;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.hn658.framework.scheduling.impl.GridJobListener;

public abstract class GridJob implements Job {
    private ApplicationContext appContext;

    /**
     * 
     * <p>初始化ApplicationContext</p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午10:15:15
     * @param context
     * @throws JobExecutionException 
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        this.init(context);
        this.doExecute(context);
    }

    /**
     * 
     * <p>实例化ApplicationContext</p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午10:15:37
     * @param context
     * @see
     */
    protected void init(JobExecutionContext context) {
        Object cxt = context.get(GridJobListener.APPLICATION_CONTEXT);
        if (null != cxt && ApplicationContext.class.isInstance(cxt)) {
            appContext = (ApplicationContext) cxt;
        }
    }

    /**
     * 
     * <p></p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午10:19:27
     * @param context JobExecutionContext
     * @param key 
     * @return
     * @see
     */
    protected Object getProperty(JobExecutionContext context, String key) {
        JobDataMap dataMap = context.getMergedJobDataMap();
        return dataMap.get(key);
    }

    protected <T> T getBean(String name, Class<T> clazz) {
        return getAppContext().getBean(name, clazz);
    }

    protected ApplicationContext getAppContext() {
        return appContext;
    }

    protected abstract void doExecute(JobExecutionContext context) throws JobExecutionException;
}
