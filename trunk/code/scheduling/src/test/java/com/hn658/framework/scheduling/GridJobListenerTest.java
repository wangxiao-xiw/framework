package com.hn658.framework.scheduling;

import java.util.Properties;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
@ContextConfiguration(locations = { "classpath*:/quartz-spring.xml" })
public class GridJobListenerTest {
	
	@Resource(name="gridNode1")
	private JobGridNode gridNode1;
	
	//任务添加
	@Test
	public void scheduleCronJob() throws SchedulerException {
		 try {
//			 gridNode1.scheduleCronJob(Hello02Job.class, properties, "0 */2 * * * ?");
			 gridNode1.scheduleCronJob(Hello02Job.class, "leipan", "HelloJob001", "", null, "CronTrigger003", "DEFAULT", "0 */3 * * * ?");
//			 gridNode1.scheduleCronJob(Hello02Job.class, "leipan", properties, triggerName, cronExpr)
//			 gridNode1.deleteJob("xubulong", "HelloJob001");
			Thread.sleep(500000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
	}
	
	//任务删除
	@Test
	public void unscheduleJob() throws SchedulerException {
		 try {
			 gridNode1.unscheduleJob("CronTrigger002", "DEFAULT");
			 Thread.sleep(500000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
	}
	
	//停止
	@Test
	public void pauseJob() throws SchedulerException {
		 try {
			 gridNode1.pauseJob("xubulong", "HelloJob001");
//			 gridNode1.pauseJobGroup("HelloJob001");
			 Thread.sleep(500000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
	}
	
	//恢复
	@Test
	public void resumeJob() throws SchedulerException {
		 try {
			 gridNode1.resumeJob("xubulong", "HelloJob001");
//			 gridNode1.resumeJobGroup("HelloJob001");
			 Thread.sleep(500000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
	}
	
	//立即执行
	@Test
	public void triggerJob() throws SchedulerException {
		 try {
			 gridNode1.triggerJob("xubulong", "HelloJob001");
			Thread.sleep(500000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
	}
	//任务更新
	@Test
	public void update() throws SchedulerException {
		 try {
			gridNode1.updateJob("xubulong", "HelloJob001","0 */2 * * * ?");
			Thread.sleep(500000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
	}
}
