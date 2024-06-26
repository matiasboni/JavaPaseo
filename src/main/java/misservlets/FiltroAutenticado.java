package misservlets;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class FiltroAutenticado implements Filter {


    public FiltroAutenticado() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse res=(HttpServletResponse) response;
		HttpSession session = req.getSession(false);
		String url=req.getRequestURI();
		if(session!=null && session.getAttribute("usuario")!=null) {
			chain.doFilter(request, response);
		}else if(req.getMethod().equals("POST")&& url.equals("/paseounlp/api/usuarios")) {
			chain.doFilter(request, response);
		}
		else {
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
