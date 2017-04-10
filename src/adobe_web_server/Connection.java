package adobe_web_server;

/**
 * Created by gargd006 on 4/9/17.
 */

import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.IOException;
import adobe_web_server.HttpResponse.StatusCode;



public class Connection implements Runnable
{

	protected Server server = null;
	protected Socket client = null;
	private InputStream in;
	private OutputStream out;
	protected boolean keepAlive = false;

	//Constructor
	public Connection(Socket client, Server server)
	{
			this.client = client;
			this.server = server;

	}

	@Override
	public void run()
	{
		try
		{
			do
			{
				in = client.getInputStream();
				out = client.getOutputStream();
				String connection = "";

				HttpRequest request = HttpRequest.parseRequest(in);
				

				if (request != null) {

					connection = request.headers.get("Connection");
					if(connection == null && request.getProtocol().equals("HTTP/1.1"))
					{
						keepAlive = true;
						connection = "keep-alive";
					}
					else
					{
						keepAlive = false;
						connection="close";
					}
					request.headers.put("Connection",connection);

					System.out.println("Request for " + request.getUrl() + " is being processed ");

					HttpResponse response;

					String method = request.getMethod();
					
					if (method.equals("GET") || method.equals("HEAD"))
					{
						File file = new File(server.getRoot_directory() + request.getUrl());
						response = new HttpResponse(StatusCode.OK).getFile(file);
						
						if (method.equals("HEAD"))
						{
							response.body =null;
						}
					} 
					else 
					{
						response = new HttpResponse(StatusCode.NOT_IMPLEMENTED);
					}

					respond(response);

				}
				else
				{
					System.err.println("Server accepts only HTTP protocol.");
					break;
				}

			}while(keepAlive);

			in.close();
			out.close();
		}
		catch (IOException e)
		{
			System.err.println("Error in client's IO.");
		}
		finally
		{
			try
			{
				client.close();
			}
			catch (IOException e)
			{
				System.out.println("client port closing failed");
			}
		}
	}

	//function to respond to request
	public synchronized void respond(HttpResponse response)
	{
		String data = response.toString();
		PrintWriter writer = new PrintWriter(out);
		writer.write(data);
		writer.flush();
	}

}
