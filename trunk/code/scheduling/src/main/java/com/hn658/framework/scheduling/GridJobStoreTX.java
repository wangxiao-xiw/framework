package com.hn658.framework.scheduling;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import org.quartz.JobDetail;
import org.quartz.JobPersistenceException;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.Trigger;
import org.quartz.core.SchedulingContext;
import org.quartz.impl.jdbcjobstore.JobStoreTX;
import org.quartz.utils.Key;

import com.hn658.framework.scheduling.impl.NOPAcquireStrategy;
/**
 * 
 * 将任务持久化到数据库中
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:平台开发小组,date:2013-1-21 下午1:50:54,content:</p>
 * @author 平台开发小组
 * @date 2013-1-21 下午1:50:54
 * @since
 * @version
 */
public class GridJobStoreTX extends JobStoreTX {
    private AcquireStrategy acquireStrategy = null;
    private String acquireStrategyClass = null;

    public String getAcquireStrategyClass() {
        return acquireStrategyClass;
    }

    public void setAcquireStrategyClass(String acquireStrategyClass) {
        this.acquireStrategyClass = acquireStrategyClass;
    }

    /**
     * 
     * <p>通过传入参数配置一个触发器</p> 
     * @author 平台开发小组
     * @date 2013-1-21 下午2:00:18
     * @param conn 数据库连接
     * @param ctxt  SchedulingContext上下文
     * @param noLaterThan  执行相隔的时间
     * @return
     * @throws JobPersistenceException 
     * @see org.quartz.impl.jdbcjobstore.JobStoreSupport#acquireNextTrigger(java.sql.Connection, org.quartz.core.SchedulingContext, long)
     */
    @SuppressWarnings("rawtypes")
    @Override
    protected Trigger acquireNextTrigger(Connection conn, SchedulingContext ctxt, long noLaterThan)
        throws JobPersistenceException {
        do {
            try {
                Trigger nextTrigger = null;
                List keys = getDelegate().selectTriggerToAcquire(conn, noLaterThan, getMisfireTime());
                if (keys == null || keys.size() == 0) {
                    return null;
                }
                Iterator itr = keys.iterator();
                while (itr.hasNext()) {
                    Key triggerKey = (Key) itr.next();

                    nextTrigger = retrieveTrigger(conn, ctxt, triggerKey.getName(), triggerKey.getGroup());
                    //
                    if (!getAcquireStrategy().isAcquire(getInstanceId(), conn, nextTrigger)) {
                        continue;
                    }
                    int rowsUpdated =
                        getDelegate().updateTriggerStateFromOtherState(conn, triggerKey.getName(),
                            triggerKey.getGroup(), STATE_ACQUIRED, STATE_WAITING);
                    if (rowsUpdated <= 0) {
                        continue;
                    }
                    if (nextTrigger == null) {
                        continue;
                    }
                    break;
                }
                if (nextTrigger == null) {
                    continue;
                }
                nextTrigger.setFireInstanceId(getFiredTriggerRecordId());
                getDelegate().insertFiredTrigger(conn, nextTrigger, STATE_ACQUIRED, null);
                return nextTrigger;
            } catch (Exception e) {
                throw new JobPersistenceException("Couldn't acquire next trigger: " + e.getMessage(), e);
            }
        } while (true);
    }

    /**
     * 
     * <p>将一个作业设置到触发器中</p> 
     * @author 平台开发小组
     * @date 2013-1-21 下午2:06:15
     * @param conn 数据库连接
     * @param ctxt SchedulingContext上下文
     * @param newTrigger 触发器
     * @param job 一个JobDetail作业
     * @param replaceExisting 是否替换已有触发器
     * @param state
     * @param forceState
     * @param recovering
     * @throws ObjectAlreadyExistsException
     * @throws JobPersistenceException 
     * @see org.quartz.impl.jdbcjobstore.JobStoreSupport#storeTrigger(java.sql.Connection, org.quartz.core.SchedulingContext, org.quartz.Trigger, org.quartz.JobDetail, boolean, java.lang.String, boolean, boolean)
     */
    @Override
    protected void storeTrigger(Connection conn, SchedulingContext ctxt, Trigger newTrigger, JobDetail job,
        boolean replaceExisting, String state, boolean forceState, boolean recovering)
        throws ObjectAlreadyExistsException, JobPersistenceException {
        if (null != job || getAcquireStrategy().isAcquire(getInstanceId(), conn, newTrigger)) {
            super.storeTrigger(conn, ctxt, newTrigger, job, replaceExisting, state, forceState, recovering);
        }
    }
    /**
     * 
     * <p>构建一个JobDetail作业</p> 
     * @author 平台开发小组
     * @date 2013-1-21 下午2:28:04
     * @param conn数据库连接
     * @param ctxt SchedulingContext上下文
     * @param jobName 作业名称
     * @param groupName  作业组
     * @return
     * @throws JobPersistenceException 
     * @see org.quartz.impl.jdbcjobstore.JobStoreSupport#retrieveJob(java.sql.Connection, org.quartz.core.SchedulingContext, java.lang.String, java.lang.String)
     */
    @Override
    protected JobDetail retrieveJob(Connection conn, SchedulingContext ctxt, String jobName, String groupName)
        throws JobPersistenceException {
        try {
            return super.retrieveJob(conn, ctxt, jobName, groupName);
        } catch (JobPersistenceException jpe) {
            if (jpe.getCause() instanceof ClassNotFoundException) {
                return null;
            }
            throw jpe;
        }
    }

    /**
     * 
     * <p>获取触发器的策略</p> 
     * @author 平台开发小组
     * @date 2013-1-21 下午2:00:46
     * @return
     * @see
     */
    protected AcquireStrategy getAcquireStrategy() {
        if (null == acquireStrategy) {
            if (null == acquireStrategyClass) {
                acquireStrategy = new NOPAcquireStrategy();
            }
            try {
                Class<?> clazz = getClassLoadHelper().loadClass(acquireStrategyClass);
                acquireStrategy = (AcquireStrategy) clazz.newInstance();
            } catch (Exception e) {
                throw new GridJobException("Can't create acquireStrategy using " + acquireStrategyClass);
            }
        }
        return acquireStrategy;
    }

}
