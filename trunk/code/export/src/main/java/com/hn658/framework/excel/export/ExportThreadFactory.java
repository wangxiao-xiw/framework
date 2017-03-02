package com.hn658.framework.excel.export;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 *<p>Description</p>
 *<p>导出线程池线程创建工厂了，为了能在在JVM监控的时候更方便的找到导出线程
 *</p>
 *@ClassName: ExportThreadFactory 
 *@author davidcun
 *@date 2014年8月4日 下午2:06:50
 */
public class ExportThreadFactory implements ThreadFactory {

    final ThreadGroup group;
    final AtomicInteger threadNumber = new AtomicInteger(1);
    final String namePrefix;

    public ExportThreadFactory() {
        SecurityManager s = System.getSecurityManager();
        group = (s != null)? s.getThreadGroup() :
                             Thread.currentThread().getThreadGroup();
        namePrefix = "pool-" +
                      "export" +
                     "-thread-";
    }

    public Thread newThread(Runnable r) {
    	Thread t = new Thread(group, r,
    			namePrefix + threadNumber.getAndIncrement(),
    			0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
    
    
    final static AtomicInteger resultProcessNumber = new AtomicInteger(1);
    
    public static Thread newThread(boolean daemon, Runnable r){
    	Thread t = new Thread(Thread.currentThread().getThreadGroup(), r,
    			"export-thread-" + resultProcessNumber.getAndIncrement(),
    			0);
    	t.setDaemon(daemon);
    	return t;
    }

}
