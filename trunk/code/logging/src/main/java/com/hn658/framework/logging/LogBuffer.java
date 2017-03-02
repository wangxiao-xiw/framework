package com.hn658.framework.logging;

import java.util.ArrayList;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.LifeCycle;

import com.hn658.framework.logging.exception.BufferedInitException;
import com.hn658.framework.logging.logSender.ILogSender;

/**
 * 该类用来作为日志的buffer,使用队列的结构
 * @author Administrator
 *
 */
public class LogBuffer extends ContextAwareBase implements LifeCycle {
    
    public static final Log LOGGER = LogFactory.getLog(LogBuffer.class);
    
    protected boolean started = false;
    
	/**
	 *阻塞队列元素的长度
	 */
	private int queueSize = 20;
	
	/**
	 * 队列中数组的长度
	 */
	private int listSize = 5000;
	
	/**
	 * 日志发送者
	 */
	private ILogSender logSender;
	
	/**
	 * 定时刷新时间间隔,单位秒,默认30分钟
	 */
	private long interval = 30L * 60 * 1000;
	
	private FlushThread thread;
	
	public int getQueueSize() {
		return queueSize;
	}

	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}

	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	public ILogSender getLogSender() {
		return logSender;
	}

	public void setLogSender(ILogSender logSender) {
		this.logSender = logSender;
	}

    /**
     * 单位秒
     * @param interval void
     * @throws 
     * @author ztjie
     */
    public void setInterval(long seconds) {
        this.interval = seconds * 1000;
    }

    /**
	 * 日志缓冲开关标志
	 */
	private final AtomicBoolean shutdown = new AtomicBoolean(false);

	/**
	 * 日志集合队列
	 */
	private BlockingDeque<ArrayList<Object>> queuePool;

	
	
	
	/**
	 *  如果队列的元素个数和队列的元素的容量设置正确，进行下面操作:
	 * 1、 初始化日志缓冲
	 * 2、初始化队列中的缓冲区
	 *  init
	 * @return void
	 * @since JDK1.6
	 */
	private void init() {
		check();
		initQueues();
	}

	/**
	 * 初始化队列中的缓冲区
	 * initQueues
	 * @return void
	 * @since JDK1.6
	 */
	private void initQueues() {
		queuePool = new LinkedBlockingDeque<ArrayList<Object>>(queueSize);
		for (int i = 0; i < queueSize; i++) {
			ArrayList<Object> list = new ArrayList<Object>(listSize);
			try {
				queuePool.put(list);
			} catch (InterruptedException e) {
				throw new BufferedInitException("LogBuffer initQueues error!");
			}
		}
	}

	/**
	 * 检查队列的初始化元素个数、队列元素的初始化容量是否正确，
	 * 若不正确则抛出初始化异常
	 * check
	 * @return void
	 * @since JDK1.6
	 */
	private void check() {
		if (listSize <= 0) {
			throw new BufferedInitException("listSize can not <= 0");
		}
		if (queueSize < 1) {
			throw new BufferedInitException("queueSize can not < 1");
		}
	}
	
	/**
	 * 记录日志
	 * 将需要写入日志的对象放入到日志缓冲中，每次取出日志缓冲阻塞队列中的首个日志缓冲，
	 * 	并向其添加需要记录的日志信息，若日志缓冲已经达到容量限制，则通过异步jms发送该日志缓冲
	 * 并在队列的末尾添加一个新的日志缓冲
	 * 以下3种情况将不记录日志：1、禁止启用缓存；2、日志缓冲已经关闭；3、需要写入的对象为null
	 * write
	 * @param obj
	 * @return void
	 * @since JDK1.6
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void write(Object obj) {
		try {
		    //判断日志缓冲的状态
            if (shutdown.get()) {
                LOGGER.debug("Logbuffer has closed!");
                return;
            }
		    if (obj == null) {
		        return;
		    }
			//从队头取出缓冲块
		    ArrayList list = queuePool.takeFirst();
			//将日志放入缓冲块
			list.add(obj);
			//如果缓冲块已满,则新建一个发送任务交给线程池发送
			//并且新建一个空的缓冲块放入队尾
			if (list.size() == listSize) {
				queuePool.putLast(new ArrayList(listSize));
				if(logSender == null) {
				    LOGGER.debug("LogSender didn't find the corresponding configuration! shutdown LogBuffer!");
				    shutdown.set(true);
				    return;
				}
				logSender.send(list);
			} else {//否则将缓冲块放回队头
				queuePool.putFirst(list);
			}
		} catch (Exception e) {
		    LOGGER.error("Write Log error! shutdown LogBuffer!",e);
		    shutdown.set(true);
            return;
		}
	}


	/**
	 * 属性注入完毕后，调用初始化方法
	 * 初始化阻塞队列、线程池以及阻塞队列中的缓冲区
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 * afterPropertiesSet
	 * @throws Exception
	 * @since JDK1.6
	 */
	@Override
	public void start() {
		init();
		thread = new FlushThread(this.getClass().getName());
		thread.setDaemon(true);
		thread.start();
		started = true;
	}

	/**
	 * 强制关闭LogBuffer,可能会抛出线程异常,关闭时内存中的日志信息将会丢失
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 * destroy
	 * @throws Exception
	 * @since JDK1.6
	 */
	@Override
	public void stop() {
	    thread.interrupt();
		shutdown.set(true);
		started = false;
	}
	
	private class FlushThread extends Thread {
	    
        public FlushThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            while(true) {
                try {
                    //判断日志缓冲的状态
                    if (shutdown.get()) {
                        LOGGER.debug("Logbuffer has closed!");
                        return;
                    }
                    Thread.sleep(interval);
                    //从队头取出缓冲块
                    ArrayList<Object> list = queuePool.pollFirst();
                    //避免pool中元素不够返回null对象
                    if(list == null) continue;
                    if(list != null && list.size() > 0) {
                        //如果缓冲块已满,则新建一个发送任务交给线程池发送
                        //并且新建一个空的缓冲块放入队尾
                        queuePool.offerLast(new ArrayList<Object>(listSize));
                        if(logSender == null) {
                            LOGGER.error("LogSender didn't find the corresponding configuration! shutdown LogBuffer!");
                            shutdown.set(true);
                            return;
                        }
                        logSender.send(list);
                    } else {
                    	queuePool.putFirst(list);
                    }
                } catch (Exception e) {
                    LOGGER.error("Flush log error! shutdown LogBuffer!",e);
                    shutdown.set(true);
                }
            }
        }
	    
	}

	@Override
	public boolean isStarted() {
		return started;
	}
	
}