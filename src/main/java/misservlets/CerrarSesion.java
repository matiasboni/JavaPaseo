package misservlets;

import java.io.IOException;

import entidades.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class CerrarSesion
 */

public class CerrarSesion extends HttpServlet {

    public CerrarSesion() {
        // TODO Auto-generated constructor stub
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion=request.getSession(false);
		if(sesion!=null) {
			sesion.invalidate();
		}else {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}


}
