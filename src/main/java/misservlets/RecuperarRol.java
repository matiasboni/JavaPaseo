package misservlets;

import java.io.IOException;

import entidades.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class RecuperarRol
 */

public class RecuperarRol extends HttpServlet {

    public RecuperarRol() {
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion=request.getSession(false);
		if(sesion!=null) {
			System.out.println("Entramos bien");
			Usuario usuario=(Usuario)sesion.getAttribute("usuario");
			String rol=usuario.getRol().toString();
			response.setContentType("application/json");
            response.getWriter().write("{\"rol\": \"" + rol + "\"}");	
		}else {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}


}
