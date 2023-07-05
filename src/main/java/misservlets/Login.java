package misservlets;

import java.io.IOException;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import dao.implementaciones.UsuarioDAOImpl;

import dao.implementaciones.UsuarioDAOImpl;
import dao.interfaces.UsuarioDAO;
import entidades.Usuario;

/**
 * Servlet implementation class Login
 */

public class Login extends HttpServlet {
	private UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
	private Usuario usuario = new Usuario();
	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String email = request.getParameter("email");
	        String password = request.getParameter("password");
	        usuario = usuarioDAO.getByEmail(email);
	        if (usuario != null && usuario.getPassword().equals(password) ) {
	        	HttpSession sesion=request.getSession();
	        	if(sesion.isNew()) {
	        		request.getSession().setAttribute("usuario",usuario);
	        		long id=usuario.getId();
	    			response.setContentType("application/json");
	                response.getWriter().write("{\"id\": \"" + id + "\"}");	
	        	}else {
	        		Usuario user=(Usuario)sesion.getAttribute("usuario");
	        		System.out.println("El usuario "+user.getEmail()+ " ya se encuentra con la sesion iniciada");
	        	}
	        } else {
	        	String error="";
	        	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        	if(usuario==null) {
	        		error="El email no se encuentra registrado";
	        	}else
	        		error="Contraseña incorrecta";
	            response.getWriter().write(error);
	        }
	    }

}
