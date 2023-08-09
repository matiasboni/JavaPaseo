package misservlets;

import java.io.IOException;

import entidades.Rol;
import entidades.Usuario;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class FiltroRolAPI implements Filter {

    public FiltroRolAPI() {
    }

	public void destroy() {
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse res=(HttpServletResponse) response;
		HttpSession session = req.getSession(false);
		Usuario usuario=(Usuario)session.getAttribute("usuario");
		if(usuario.getRol().equals(Rol.admin)) {
			chain.doFilter(request, response);
		}else if(req.getMethod().equals("GET")) {
			chain.doFilter(request, response);
		}else {
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {	
	}

}
