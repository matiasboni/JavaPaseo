package api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.core.Response.Status;
import entidades.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.util.List;

import dao.implementaciones.*;
import dao.interfaces.*;

@Path("/puntos")
@Tags(value= {@Tag(name="Puntos de retiro",description="Metodos de puntos de retiro")})
public class PuntosRetiroResources {

	String mensaje;
	//@Inject
	private PuntoDeRetiroDAOImpl puntoDeRetiroDAO = new PuntoDeRetiroDAOImpl();
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener punto de retiro", description = "Obtiene un punto de retiro por su ID")
    @ApiResponse(responseCode = "200", description = "Punto de retiro encontrado", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "Punto de retiro no encontrado", content = @Content(mediaType = "text/plain"))
	public Response encontrar(@PathParam("id") @DefaultValue("1")Integer id) {
		PuntoDeRetiro punto= (PuntoDeRetiro)puntoDeRetiroDAO.getById(id);
		if(punto != null) {
			return Response.ok().entity(punto).build();
		}
		else {
			mensaje="No se encontro el punto de retiro";
			return Response.status(Status.NOT_FOUND).entity(mensaje).build();
		}
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener lista de puntos de retiro", description = "Devuelve una lista de puntos de retiro")
	@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
		    @ApiResponse(responseCode = "400", description = "Error de consulta", content = @Content(mediaType = "text/plain")),
		    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
		})
	public Response getPuntosDeRetiro(){
		try {
	        List<PuntoDeRetiro> puntos = puntoDeRetiroDAO.lista();
	        return Response.ok().entity(puntos).build();
	    } catch (IllegalArgumentException e) {
	    	mensaje="Error :";
	        return Response.status(Response.Status.BAD_REQUEST).entity(mensaje + e.getMessage()).build();
	    } catch (RuntimeException e) {
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
	    }
	}
}
