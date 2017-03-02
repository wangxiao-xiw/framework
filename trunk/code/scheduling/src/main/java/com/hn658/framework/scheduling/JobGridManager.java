package com.hn658.framework.scheduling;

import java.util.HashMap;
import java.util.Map;

import org.quartz.Scheduler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 
 * 网格作业管理类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:平台开发小组,date:2013-1-22 下午12:59:51,content:TODO </p>
 * @author 平台开发小组
 * @date 2013-1-22 下午12:59:51
 * @since
 * @version
 */
public final class JobGridManager implements ApplicationContextAware, InitializingBean {
    private Map<String, JobGridNode> gridNodeMap = new HashMap<String, JobGridNode>();
    private ApplicationContext appContext;

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;
    }

    public JobGridNode getGridNode(String clusterName) {
        return gridNodeMap.get(clusterName);
    }
   /**
    * 
    * <p>注册节点</p> 
    * @author 平台开发小组
    * @date 2013-4-3 上午9:58:25
    * @param configLoacation
    * @return
    * @throws Exception
    * @see
    */
    public JobGridNode register(String configLoacation) throws Exception {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setConfigLocation(new ClassPathResource(configLoacation));
        factory.afterPropertiesSet();
        Scheduler scheduler = factory.getObject();
        JobGridNode node = JobGridNode.createNode(appContext, scheduler);
        gridNodeMap.put(node.getClusterName(), node);
        return node;
    }

    public void unRegister(JobGridNode node) {
    }

}
