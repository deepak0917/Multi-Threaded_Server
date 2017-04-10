package adobe_web_server;

/**
 * Created by gargd006 on 4/9/17.
 */

import java.util.concurrent.ExecutorService;
import java.net.ServerSocket;
import java.util.concurrent.Executors;
import java.io.IOException;
import java.net.Socket;


public class Server implements Runnable
{
	private ServerSocket server;
	private final String root_directory;
	private ExecutorService MaxPoolThreads;
	private final int port;
	private boolean isStopped = false;
	private final int maxThreads;

	//Constructor
	public Server(int port, String root_directory, int maxThreads)
	{
		this.port = port;
		this.maxThreads = maxThreads;
		this.root_directory = root_directory;
		this.MaxPoolThreads = Executors.newFixedThreadPool(this.maxThreads);
	}


	@Override
	public void run()
	{
		System.out.println("Running server: Port " + port + " and threads limit: " + maxThreads);

		openServerSocket();

		while (!Thread.interrupted())
		{
			Socket clientSocket = null;
			try
			{
				clientSocket = server.accept();
			}
			catch (IOException e)
			{
				if(isStopped())
				{
					System.out.println("Server Stopped.") ;
					break;
				}
				throw new RuntimeException("Error accepting client connection", e);
			}

			MaxPoolThreads.execute(new Thread(new Connection(clientSocket, this)));

		}
		close();
	}

	//Function close
	public void close()
	{
		this.isStopped = true;

		try
		{
			server.close();
		}
		catch (IOException e)
		{
			throw new RuntimeException("Error while closing server socket.", e);
		}

		MaxPoolThreads.shutdown();
		System.out.println("Server Stopped.") ;

	}

	//Function getWebDirectory function ( where all my files are stored)
	public String getRoot_directory()
	{
		return root_directory;
	}

	// Opening the server on the given port in this function
	private synchronized void openServerSocket()
	{
		try
		{
			this.server = new ServerSocket(this.port);
		}
		catch (IOException e)
		{
			throw new RuntimeException("Cannot open port 8090", e);
		}
	}

	private synchronized boolean isStopped() {
		return this.isStopped;
	}


	public static void main(String[] args)
	{
		int port = 8090;
		String root = "www_root";
		int maxThreads = 8;
		new Thread(new Server(port, root, maxThreads)).start();
	}


}
