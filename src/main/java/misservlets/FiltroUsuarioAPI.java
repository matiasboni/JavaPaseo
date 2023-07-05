package misservlets;


import java.io.IOException;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

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
import wrappers.RequestWrapper;


/**
 * Servlet Filter implementation class AutenticadoFiltro
 */

public class FiltroUsuarioAPI implements Filter {

    public FiltroUsuarioAPI() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse res=(HttpServletResponse) response;
		HttpSession session = req.getSession(false);
		String url=req.getRequestURI();
		if(session!=null && session.getAttribute("usuario")!=null) {
			Usuario usuario=(Usuario)session.getAttribute("usuario");
			if(usuario.getRol().equals(Rol.visitante)){
				try {
					String[] partes=req.getPathInfo().split("/");
					long id=Long.parseLong(partes[partes.length - 1]);
					if(req.getMethod().equals("GET")&&(id==usuario.getId())) {
						chain.doFilter(request, response);
					}
					else {
						res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
					}
				}
				catch(NumberFormatException e) {
					res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				}
			}else {
				if(req.getMethod().equals("DELETE")) {
					try {
						String[] partes=req.getPathInfo().split("/");
						long id=Long.parseLong(partes[partes.length - 1]);
						if(id!=usuario.getId()) {
							chain.doFilter(request, response);
						}
						else {
							res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
							String error="No puede eliminarse su propio usuario";
							response.getWriter().write(error);
						}
					}catch(NumberFormatException e) {
						return;
					}
				}else {
					chain.doFilter(request, response);
				}
			}
		}else if(req.getMethod().equals("POST")&& url.equals("/paseounlp/api/usuarios")) {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
