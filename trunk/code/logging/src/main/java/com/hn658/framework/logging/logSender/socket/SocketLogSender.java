package com.hn658.framework.logging.logSender.socket;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ch.qos.logback.classic.net.SocketNode;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.LifeCycle;

import com.hn658.framework.logging.logSender.ILogSender;
import com.hn658.framework.logging.model.LogInfoEO;

public class SocketLogSender extends ContextAwareBase implements ILogSender,
		LifeCycle {

	public static final Log LOGGER = LogFactory.getLog(ZeroConfSupport.class);

	/**
	 * The default port number of remote logging server (4560).
	 * 
	 * @since 1.2.15
	 */
	static public final int DEFAULT_PORT = 4560;

	/**
	 * The default reconnection delay (30000 milliseconds or 30 seconds).
	 */
	static final int DEFAULT_RECONNECTION_DELAY = 30000;

	/**
	 * We remember host name as String in addition to the resolved InetAddress
	 * so that it can be returned via getOption().
	 */
	String remoteHost;

	String name;

	InetAddress address;

	int port = DEFAULT_PORT;

	ObjectOutputStream oos;

	/**
	 * Is this appender closed?
	 */
	protected boolean closed = false;

	int reconnectionDelay = DEFAULT_RECONNECTION_DELAY;

	private Connector connector;

	int counter = 0;

	// reset the ObjectOutputStream every 70 calls
	// private static final int RESET_FREQUENCY = 70;
	private static final int RESET_FREQUENCY = 1;

	public SocketLogSender() {
	}

	/**
	 * Connects to remote server at <code>address</code> and <code>port</code>.
	 */
	public SocketLogSender(InetAddress address, int port) {
		this.address = address;
		this.remoteHost = address.getHostName();
		this.port = port;
		connect(address, port);
	}

	/**
	 * Connects to remote server at <code>host</code> and <code>port</code>.
	 */
	public SocketLogSender(String host, int port) {
		this.port = port;
		this.address = getAddressByName(host);
		this.remoteHost = host;
		connect(address, port);
	}

	/**
	 * Close this appender.
	 * 
	 * <p>
	 * This will mark the appender as closed and call then {@link #cleanUp}
	 * method.
	 * */
	synchronized public void close() {
		if (closed)
			return;
		this.closed = true;
		cleanUp();
	}

	/**
	 * Drop the connection to the remote host and release the underlying
	 * connector thread if it has been created
	 * */
	public void cleanUp() {
		if (oos != null) {
			try {
				oos.close();
			} catch (IOException e) {
				if (e instanceof InterruptedIOException) {
					Thread.currentThread().interrupt();
				}
				LOGGER.error("Could not close oos.", e);
			}
			oos = null;
		}
		if (connector != null) {
			// LOGGER.debug("Interrupting the connector.");
			connector.interrupted = true;
			connector = null; // allow gc
		}
	}

	void connect(InetAddress address, int port) {
		if (this.address == null)
			return;
		try {
			// First, close the previous connection if any.
			cleanUp();
			oos = new ObjectOutputStream(
					new Socket(address, port).getOutputStream());
		} catch (IOException e) {
			if (e instanceof InterruptedIOException) {
				Thread.currentThread().interrupt();
			}
			String msg = "Could not connect to remote log4j server at ["
					+ address.getHostName() + "].";
			if (reconnectionDelay > 0) {
				msg += " We will try again later.";
				fireConnector(); // fire the connector thread
			} else {
				msg += " We are not retrying.";
			}
			LOGGER.error(msg);
		}
	}

	@Override
	public void send(List<Object> msg) {
		if (msg == null)
			return;

		if (address == null) {
			LOGGER.error("No remote host is set for SocketLogSender named \""
					+ this.name + "\".");
			return;
		}

		if (oos != null) {
			try {
				for (int x = 0; x < msg.size(); x++) {
					LogInfoEO logInfo = (LogInfoEO) msg.get(x);
					oos.writeObject(logInfo);
					// LOGGER.debug("=========Flushing.");
					oos.flush();
					if (++counter >= RESET_FREQUENCY) {
						counter = 0;
						// Failing to reset the object output stream every now
						// and
						// then creates a serious memory leak.
						// System.err.println("Doing oos.reset()");
						oos.reset();
					}
				}
			} catch (IOException e) {
				if (e instanceof InterruptedIOException) {
					Thread.currentThread().interrupt();
				}
				oos = null;
				LOGGER.warn("Detected problem with connection: " + e);
				if (reconnectionDelay > 0) {
					fireConnector();
				} else {
					LOGGER.error(
							"Detected problem with connection, not reconnecting.",
							e);
				}
			}
		}
	}

	void fireConnector() {
		if (connector == null) {
			LOGGER.debug("Starting a new connector thread.");
			connector = new Connector();
			connector.setDaemon(true);
			connector.setPriority(Thread.MIN_PRIORITY);
			connector.start();
		}
	}

	static InetAddress getAddressByName(String host) {
		try {
			return InetAddress.getByName(host);
		} catch (Exception e) {
			if (e instanceof InterruptedIOException
					|| e instanceof InterruptedException) {
				Thread.currentThread().interrupt();
			}
			LOGGER.error("Could not find address of [" + host + "].", e);
			return null;
		}
	}

	/**
	 * The SocketLogSender does not use a layout. Hence, this method returns
	 * <code>false</code>.
	 * */
	public boolean requiresLayout() {
		return false;
	}

	/**
	 * The <b>RemoteHost</b> option takes a string value which should be the
	 * host name of the server where a {@link SocketNode} is running.
	 * */
	public void setRemoteHost(String host) {
		address = getAddressByName(host);
		remoteHost = host;
	}

	/**
	 * Returns value of the <b>RemoteHost</b> option.
	 */
	public String getRemoteHost() {
		return remoteHost;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * The <b>Port</b> option takes a positive integer representing the port
	 * where the server is waiting for connections.
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Returns value of the <b>Port</b> option.
	 */
	public int getPort() {
		return port;
	}

	/**
	 * The <b>ReconnectionDelay</b> option takes a positive integer representing
	 * the number of milliseconds to wait between each failed connection attempt
	 * to the server. The default value of this option is 30000 which
	 * corresponds to 30 seconds.
	 * 
	 * <p>
	 * Setting this option to zero turns off reconnection capability.
	 */
	public void setReconnectionDelay(int delay) {
		this.reconnectionDelay = delay;
	}

	/**
	 * Returns value of the <b>ReconnectionDelay</b> option.
	 */
	public int getReconnectionDelay() {
		return reconnectionDelay;
	}

	/**
	 * The Connector will reconnect when the server becomes available again. It
	 * does this by attempting to open a new connection every
	 * <code>reconnectionDelay</code> milliseconds.
	 * 
	 * <p>
	 * It stops trying whenever a connection is established. It will restart to
	 * try reconnect to the server when previously open connection is droppped.
	 * 
	 * @author Ceki G&uuml;lc&uuml;
	 * @since 0.8.4
	 */
	class Connector extends Thread {

		boolean interrupted = false;

		public void run() {
			Socket socket;
			while (!interrupted) {
				try {
					sleep(reconnectionDelay);
					LOGGER.debug("Attempting connection to "
							+ address.getHostName());
					socket = new Socket(address, port);
					synchronized (this) {
						oos = new ObjectOutputStream(socket.getOutputStream());
						connector = null;
						LOGGER.debug("Connection established. Exiting connector thread.");
						break;
					}
				} catch (InterruptedException e) {
					LOGGER.debug("Connector interrupted. Leaving loop.");
					return;
				} catch (java.net.ConnectException e) {
					LOGGER.debug("Remote host " + address.getHostName()
							+ " refused connection.");
				} catch (IOException e) {
					if (e instanceof InterruptedIOException) {
						Thread.currentThread().interrupt();
					}
					LOGGER.debug("Could not connect to "
							+ address.getHostName() + ". Exception is " + e);
				}
			}
			// LOGGER.debug("Exiting Connector.run() method.");
		}

		/**
		 * public void finalize() {
		 * LOGGER.debug("Connector finalize() has been called."); }
		 */
	}

	@Override
	public void start() {
		connect(address, port);
	}

	@Override
	public void stop() {
		close();
	}

	@Override
	public boolean isStarted() {
		return false;
	}

}
