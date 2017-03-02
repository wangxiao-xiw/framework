package com.hn658.framework.scheduling.impl;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

public final class GridJobUtils {

	/**
	 * 
	 * <p>通过调度返回调度ID</p> 
	 * @author 平台开发小组
	 * @date 2013-1-22 上午9:42:53
	 * @param sch Scheduler
	 * @return
	 * @see
	 */
    public static String getInstanceId(Scheduler sch) {
        try {
            return sch.getSchedulerInstanceId();
        } catch (SchedulerException e) {
            return "@UNKNOWN-CLUSTER";
        }
    }

    /**
     * 
     * <p>将JobDataMap放入properties中</p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午9:43:58
     * @param jdm
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    public static Properties toProperties(JobDataMap jdm) {
        Properties p = new Properties();
        p.putAll(jdm);
        return p;
    }

    /**
     * 
     * <p>通过text查询JobDataMap</p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午9:45:00
     * @param text
     * @return
     * @see
     */
    public static JobDataMap fromString(String text) {
        if (null == text || 0 == text.length()) {
            return null;
        }
        try {
            Properties p = new Properties();
            StringReader sr = new StringReader(text);
            p.load(sr);
            return toJobDataMap(p);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 
     * <p>通过Properties查询JobDataMap</p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午9:49:56
     * @param p
     * @return
     * @see
     */
    public static JobDataMap toJobDataMap(Properties p) {
        return new JobDataMap(p);
    }

    /**
     * 
     * <p></p> 
     * @author 平台开发小组
     * @date 2013-1-22 上午9:51:33
     * @param jdm
     * @return
     * @see
     */
    public static String toPropertiesString(JobDataMap jdm) {
        Properties p = toProperties(jdm);
        return toPropertiesString(p);
    }

    public static String toPropertiesString(Properties p) {
        if (null == p || 0 == p.size()) {
            return "";
        }
        try {
            StringWriter sw = new StringWriter();
            p.store(sw, null);
            sw.close();
            return sw.toString();
        } catch (IOException e) {
        }
        return "";
    }
}
