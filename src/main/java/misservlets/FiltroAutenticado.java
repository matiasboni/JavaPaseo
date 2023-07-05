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


public class FiltroAutenticado implements Filter {


    public FiltroAutenticado() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse res=(HttpServletResponse) response;
		HttpSession session = req.getSession(false);
		System.out.println("Filtro autenticado");
		String url=req.getRequestURI();
		if(session!=null && session.getAttribute("usuario")!=null) {
			chain.doFilter(request, response);
		}else if(req.getMethod().equals("POST")&& url.equals("/paseounlp/api/usuarios")) {
			System.out.println("Crear usuario visitante");
			String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
	        ObjectMapper objectMapper = new ObjectMapper();
	        Usuario usuario = objectMapper.readValue(requestBody, Usuario.class);
	        usuario.setRol(Rol.visitante);
	        String requestBodyModified = objectMapper.writeValueAsString(usuario);
	        RequestWrapper requestModified = new RequestWrapper(req, requestBodyModified);
	        chain.doFilter(requestModified, response);
		}
		else {
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
