package com.hn658.framework.scheduling;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloJob extends GridJob {
@Override
    protected void doExecute(JobExecutionContext context) throws JobExecutionException {
        // 取得静态参数配置
        String username = (String) getProperty(context, "username");
        // 从ApplicationContext中取得Spring配置的HelloService

        // 后续业务逻辑
        System.out.println("helloleipan" + username);
    }
}

