package adobe_web_server;

/**
 * Created by gargd006 on 4/9/17.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;


public class HttpResponse {


	private HashMap<String, String> headers = new HashMap<String, String>();
	protected byte[] body = null;
	private static final String protocol = "HTTP/1.1";
	private String status;

	//Constructor
	public HttpResponse(String status)
	{
		this.status = status;
	}

	public HttpResponse getFile(File f)
	{
		if (f.isFile())
		{
			try
			{
				FileInputStream reader = new FileInputStream(f);
				int length = reader.available();
				body = new byte[length];
				reader.read(body);
				reader.close();
				
				setContentLength(length);
				if (f.getName().endsWith(".htm") || f.getName().endsWith(".html"))
				{
					setContentType("text/html");
				}
				else
				{
					setContentType("text/plain");
				}
			}
			catch (IOException e)
			{
				System.out.println("Error while reading file " + f);
			}
			return this;
		}
		else
		{
			return new HttpResponse(StatusCode.NOT_FOUND).HtmlBody("<html><body>File " + f + " not found.</body></html>");
		}
	}

	public HttpResponse HtmlBody(String msg)
	{
		setContentLength(msg.getBytes().length);
		setContentType("text/html");
		body = msg.getBytes();
		return this;
	}


	public void setContentLength(long value)
	{
		headers.put("Content-Length", String.valueOf(value));
	}

	public void setContentType(String value)
	{
		headers.put("Content-Type", value);
	}


	@Override
	public String toString()
	{
		String result = protocol + " " + status +"\n";
		for (String key : headers.keySet())
		{
			result += key + ": " + headers.get(key) + "\n";
		}
		result += "\r\n"; // handles for both mac and windows
		if (body != null)
		{
			result += new String(body);
		}
		return result;
	}

	public static class StatusCode
	{
		public static final String OK = "200 OK";
		public static final String NOT_FOUND = "404 Not Found";
		public static final String NOT_IMPLEMENTED = "501 Not Implemented";
	}

}
