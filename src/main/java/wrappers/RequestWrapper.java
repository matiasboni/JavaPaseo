package wrappers;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class RequestWrapper extends HttpServletRequestWrapper {
	
	private final String requestBodyModified;
	
	public RequestWrapper(HttpServletRequest req,String requestBodyModified) {
		super(req);
		this.requestBodyModified = requestBodyModified;
	}
	 @Override
     public ServletInputStream getInputStream() throws IOException {
         ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestBodyModified.getBytes());
         return new ServletInputStream() {
             @Override
             public int read() throws IOException {
                 return byteArrayInputStream.read();
             }

			@Override
			public boolean isFinished() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isReady() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void setReadListener(ReadListener readListener) {
				// TODO Auto-generated method stub
				
			}
         };
     }

     @Override
     public BufferedReader getReader() throws IOException {
         return new BufferedReader(new InputStreamReader(getInputStream()));
     }

}
