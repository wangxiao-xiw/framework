package com.hn658.framework.scheduling.impl;

import java.sql.Connection;

import org.quartz.Trigger;

import com.hn658.framework.scheduling.AcquireStrategy;
/**
 * 
 * 定时执行策略
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:ningyu,date:2013-3-29 下午2:25:51,content:TODO </p>
 * @author ningyu
 * @date 2013-3-29 下午2:25:51
 * @since
 * @version
 */
public class NOPAcquireStrategy implements AcquireStrategy {

    @Override
    public boolean isAcquire(String instanceId, Connection conn, Trigger trigger) {
        return true;
    }

}
