package api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.core.Response.Status;
import entidades.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.util.List;

import dao.implementaciones.*;
import dao.interfaces.*;

@Path("/rondas")
@Tags(value= {@Tag(name="Rondas",description="Metodos de rondas")})
public class RondasResources {
	
	String mensaje;
	//@Inject
	private RondaDAOImpl rondaDAO = new RondaDAOImpl();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener lista de rondas", description = "Devuelve la lista de rondas ")
	public Response getRondas(){
		try {
	        List<Ronda> rondas = rondaDAO.lista();
	        return Response.ok().entity(rondas).build();
	    } catch (IllegalArgumentException e) {
	    	mensaje="Error :";
	        return Response.status(Response.Status.BAD_REQUEST).entity(mensaje + e.getMessage()).build();
	    } catch (RuntimeException e) {
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error interno del servidor").build();
	    }
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener ronda", description = "Obtiene una ronda por su ID")
	public Response encontrar(@PathParam("id") @DefaultValue("6")Integer id) {
		Ronda ronda= (Ronda)rondaDAO.getById(id);
		if(ronda != null) {
			return Response.ok().entity(ronda).build();
		}
		else {
			mensaje="No se encontro la ronda";
			return Response.status(Status.NOT_FOUND).entity(mensaje).build();
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Crear Ronda", description = "Crea una nueva ronda")
	public Response crear(Ronda ronda) {
		if(ronda.getFechaFin()!=null && ronda.getFechaInicio()!=null && ronda.getFechaFin()!=null) {
			rondaDAO.guardar(ronda);
			return Response.created(null).entity(ronda).build();
		}
		else {
			mensaje="Debe completar todos los campos";
			return Response.status(Status.BAD_REQUEST).entity(mensaje).build();
		}
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Modificar ronda", description = "Modificar una ronda existente")
	public Response editar(Ronda ronda){
		Ronda aux = (Ronda)rondaDAO.getById(ronda.getId());
		if(aux!=null && ronda.getFechaFin()!=null && ronda.getFechaInicio()!=null && ronda.getFechaFin()!=null){
			rondaDAO.modificar(ronda);
			return Response.ok().entity(ronda).build();
		} else {
			mensaje="Los datos no son válidos";
			return Response.status(Status.NOT_FOUND).entity(mensaje).build();
		}
	}
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.TEXT_PLAIN)
	@Operation(summary = "Eliminar ronda", description = "Elimina una ronda por su ID")
	public Response borrar(@PathParam("id") Integer id) {
		Ronda ronda =(Ronda) rondaDAO.getById(id);
		if(ronda!= null ) {
			rondaDAO.eliminar(ronda);
			return Response.noContent().build();
		}
		else {
			mensaje= "Ronda no encontrada";
			return Response.status(Status.NOT_FOUND).entity(mensaje).build();
		}
	}
	
}
