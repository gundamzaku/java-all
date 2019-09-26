package com.javferna.packtpub.mastering.simpleserver.concurrent.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.javferna.packtpub.mastering.simpleserver.common.Constants;
import com.javferna.packtpub.mastering.simpleserver.parallel.cache.ParallelCache;
import com.javferna.packtpub.mastering.simpleserver.parallel.log.Logger;
import com.javferna.packtpub.mastering.simpleserver.wdi.data.WDIDAO;

/**
 * Class that implements the concurrent server
 * 
 * @author author
 *
 */
public class ConcurrentServer {

	/**
	 * Executor to execute the commands of the server
	 */
	private static ThreadPoolExecutor executor;

	/**
	 * Cache to get a better performance
	 */
	private static ParallelCache cache;

	/**
	 * Socket to read the requests of the clients
	 */
	private static ServerSocket serverSocket;

	/**
	 * Attribute to control the status of the server
	 */
	private static volatile boolean stopped = false;

	/**
	 * Main method that implements the core functionality of the server
	 * 
	 * @param args
	 *            Arguments
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {

		WDIDAO dao = WDIDAO.getDAO();
		/**
		 * 固定大小的线程池
		 *
		 * 同时可以处理【参数】个任务，多余的任务会排队，当处理完一个马上就会去接着处理排队中的任务。
		 * Callable的任务在后面的blog有更详细的文章说明
		 */
		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		cache = new ParallelCache();
		Logger.initializeLog();

		System.out.println("Initialization completed.");

		serverSocket = new ServerSocket(Constants.CONCURRENT_PORT);

		do {
			try {
				Socket clientSocket = serverSocket.accept();
				RequestTask task = new RequestTask(clientSocket);
				executor.execute(task);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (!stopped);

		/*
		该方法调用会被阻塞，并且在以下几种情况任意一种发生时都会导致该方法的执行:
		即shutdown方法被调用之后，或者参数中定义的timeout时间到达或者当前线程被打断，这几种情况任意一个发生了都会导致该方法在所有任务完成之后才执行。
		第一个参数是long类型的超时时间，第二个参数可以为该时间指定单位。
		 */
		executor.awaitTermination(1, TimeUnit.DAYS);
		System.out.println("Shutting down cache");
		cache.shutdown();
		System.out.println("Cache ok");
		System.out.println("Main server thread ended");
	}

	/**
	 * Method that returns the executor of the server
	 * 
	 * @return The executor of the server
	 */
	public static ThreadPoolExecutor getExecutor() {
		return executor;
	}

	/**
	 * Method that returns the cache
	 * 
	 * @return The cache
	 */
	public static ParallelCache getCache() {
		return cache;
	}

	/**
	 * Methods that finish the execution of the server
	 */
	public static void shutdown() {
		stopped = true;
		System.out.println("Shutting down the server...");
		System.out.println("Shutting down executor");
		executor.shutdown();
		System.out.println("Executor ok");
		System.out.println("Closing socket");
		try {
			serverSocket.close();
			System.out.println("Socket ok");
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Shutting down logger");
		Logger.sendMessage("Shuttingdown the logger");
		Logger.shutdown();
		System.out.println("Logger ok");
	}

}
