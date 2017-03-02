package com.hn658.framework.logging.logSender.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.LifeCycle;

import com.hn658.framework.logging.exception.BufferedStateException;
import com.hn658.framework.logging.logSender.ILogSender;
import com.hn658.framework.logging.model.LogInfoEO;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * 
 * 将日志信息存入mongoDB中
 * 
 * @author ztjie
 * @date 2014-7-21 下午4:09:47
 * @since
 * @version
 */
public class MongoLogSender extends ContextAwareBase implements ILogSender,
		LifeCycle {

	public static final Log LOGGER = LogFactory.getLog(MongoLogSender.class);

	protected boolean started = false;

	private MongoConnectionSource connectionSource;

	private ExecutorService threadPool = null;

	private int threadSize = 5;

	/**
	 * 
	 * <p>
	 * 关闭资源
	 * </p>
	 * 
	 * @author ztjie
	 * @date 2013-4-1 上午10:48:40
	 */
	@Override
	public void stop() {
		threadPool.shutdown();
		while (!threadPool.isTerminated()) {
			try {
				synchronized (this) {
					this.wait(1000);
				}
			} catch (InterruptedException e) {
				throw new BufferedStateException(e.getMessage());
			}
		}
		started = false;
	}

	/**
	 * 
	 * <p>
	 * 初始化配置
	 * </p>
	 * 
	 * @author ztjie
	 * @date 2013-4-1 上午10:49:00
	 */
	@Override
	public void start() {
		threadPool = new ThreadPoolExecutor(threadSize, threadSize, 3,
				TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(
						5 * threadSize), new ThreadPoolExecutor.AbortPolicy());
		started = true;
	}

	/**
	 * 
	 * <p>
	 * 发送mongodb日志
	 * </p>
	 * 
	 * @author ztjie
	 * @date 2013-4-1 上午10:50:47
	 * @param msg
	 */
	@Override
	public void send(List<Object> msg) {
		threadPool.submit(new SendTask(msg));
	}

	/**
	 * 
	 * 添加发送任务
	 * 
	 * @author ztjie
	 * @date 2014-7-21 下午5:40:30
	 * @since
	 * @version
	 */
	private class SendTask implements Runnable {

		private List<Object> msg;

		public SendTask(List<Object> list) {
			this.msg = list;
		}

		@Override
		public void run() {
			try {
				DBCollection coll = connectionSource.getCollection();
				List<DBObject> dbObjectList = new ArrayList<DBObject>();
				for (int x = 0; x < msg.size(); x++) {
					LogInfoEO logInfo = (LogInfoEO) msg.get(x);
					BasicDBObject doc = new BasicDBObject("id",
							connectionSource.getAutoIncreaseID("id"))
							.append("requestId", logInfo.getRequestId())
							.append("level", logInfo.getLevel())
							.append("category", logInfo.getCategory())
							.append("logType", logInfo.getLogType())
							.append("message", logInfo.getMessage())
							.append("createdDateTime", logInfo.getCreatedDateTime());
					dbObjectList.add(doc);
				}
				coll.insert(dbObjectList);
			} catch (Exception e) {
				LOGGER.error("MongoLogSender: Write Log error!", e);
			}
		}
	}

	public MongoConnectionSource getConnectionSource() {
		return connectionSource;
	}

	public void setConnectionSource(MongoConnectionSource connectionSource) {
		this.connectionSource = connectionSource;
	}

	@Override
	public boolean isStarted() {
		return false;
	}

}