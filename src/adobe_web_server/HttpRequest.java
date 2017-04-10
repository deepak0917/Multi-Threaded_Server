package adobe_web_server;

/**
 * Created by gargd006 on 4/9/17.
 */

import java.util.*;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


public class HttpRequest
{

	protected HashMap<String, String> headers = new HashMap<String, String>();
	protected String method;
	protected String url;
	private List<String> body = new ArrayList<String>();
	protected String protocol;


	//Parsing the URL Request
	public static HttpRequest parseRequest(InputStream in) throws IOException {
		try
		{
			HttpRequest request = new HttpRequest();
			BufferedReader buffer = new BufferedReader(new InputStreamReader(in));

			String line = buffer.readLine();
			if (line == null)
			{
				throw new IOException("EXCEPTION: Only HTTP requests Accepted");
			}

			String[] param_request = line.split(" ", 3);
			if (param_request.length != 3)
			{
				throw new IOException("EXCEPTION: parsing request line FAILED");
			}
			request.method = param_request[0];
			request.url = param_request[1];

			if (!param_request[2].startsWith("HTTP/"))
			{
				throw new IOException("EXCEPTION: Only HTTP requests Accepted");
			}

			request.protocol = param_request[2];

			line = buffer.readLine();

			while(!line.equals("") && line != null )
			{
				String[] header = line.split(": ", 2);

				if (header.length != 2)
					throw new IOException("Cannot parse header" + line);
				else 
					request.headers.put(header[0], header[1]);

				line = buffer.readLine();
			}
			
			while(buffer.ready())
			{
				line = buffer.readLine();
				request.body.add(line);
			}
			
			return request;
		}
		catch (IOException e)
		{
			System.out.println("Server accepts only HTTP.");
			return null;
		}

	}

	// function to get url
	public String getUrl()
	{
		return url;
	}

	//function to get what method it is
	public String getMethod()
	{
		return method;
	}

	public String getProtocol()
	{
		return protocol;
	}


}
