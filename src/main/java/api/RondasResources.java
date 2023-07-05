package api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.core.Response.Status;
import entidades.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import dao.implementaciones.*;
import dao.interfaces.*;

@Path("/rondas")
@Tags(value= {@Tag(name="Rondas",description="Metodos de rondas")})
public class RondasResources {
	
	String mensaje;
	//@Inject
	private RondaDAOImpl rondaDAO = new RondaDAOImpl();
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener ronda", description = "Obtiene una ronda por su ID")
    @ApiResponse(responseCode = "200", description = "Ronda encontrada", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "Ronda no encontrada", content = @Content(mediaType = "text/plain"))
	public Response encontrar(@PathParam("id") @DefaultValue("1")Integer id) {
		Ronda ronda= (Ronda)rondaDAO.getById(id);
		if(ronda != null) {
			return Response.ok().entity(ronda).build();
		}
		else {
			mensaje="No se encontro la ronda";
			return Response.status(Status.NOT_FOUND).entity(mensaje).build();
		}
	}

}
