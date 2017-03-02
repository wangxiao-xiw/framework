package com.hn658.framework.logging.logSender.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ch.qos.logback.core.db.ConnectionSource;
import ch.qos.logback.core.db.DBHelper;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.LifeCycle;

import com.hn658.framework.logging.exception.BufferedStateException;
import com.hn658.framework.logging.logSender.ILogSender;

/**
 * 
 * 将日志信息存入mongoDB中
 * 
 * @author ztjie
 * @date 2014-7-21 下午4:09:47
 * @since
 * @version
 */
public abstract class DBLogSender extends ContextAwareBase implements
		ILogSender, LifeCycle {

	public static final Log LOGGER = LogFactory.getLog(DBLogSender.class);

	protected boolean started = false;

	protected ConnectionSource connectionSource;

	private ExecutorService threadPool = null;

	private int threadSize = 5;

	//static final int ID = 1;
	static final int REQUEST_ID = 1;
	static final int TYPE = 2;
	static final int CATEGORY = 3;
	static final int LEVEL = 4;
	static final int MESSAGE = 5;
	static final int CRATED_DATETIME = 6;

	protected abstract String getInsertSQL();

	@Override
	public void start() {
		if (connectionSource == null) {
			throw new IllegalStateException(
					"DBLogSender cannot function without a connection source");
		}
		threadPool = new ThreadPoolExecutor(threadSize, threadSize, 3,
				TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(
						5 * threadSize), new ThreadPoolExecutor.AbortPolicy());
		started = true;
	}

	/**
	 * 
	 * <p>
	 * 发送db日志
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
				Connection connection = null;
				try {
					connection = connectionSource.getConnection();
					connection.setAutoCommit(false);
					PreparedStatement insertStatement = connection
							.prepareStatement(getInsertSQL());
					synchronized (this) {
						subInsertBatch(msg, connection, insertStatement);
					}
					connection.commit();
					DBHelper.closeStatement(insertStatement);
				} catch (Throwable sqle) {
					addError("problem appending event", sqle);
				} finally {
					DBHelper.closeConnection(connection);
				}
			} catch (Exception e) {
				LOGGER.error("DBLogSender: Write Log error!", e);
			}
		}
	}

	protected abstract void subInsertBatch(List<Object> msg, Connection connection,
			PreparedStatement statement) throws Throwable;

	public ConnectionSource getConnectionSource() {
		return connectionSource;
	}

	public void setConnectionSource(ConnectionSource connectionSource) {
		this.connectionSource = connectionSource;
	}

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

	@Override
	public boolean isStarted() {
		return started;
	}
}