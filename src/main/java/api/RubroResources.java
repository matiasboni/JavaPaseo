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

@Path("/rubros")
@Tags(value= {@Tag(name="Rubros",description="Metodos de rubros")})
public class RubroResources {

	String mensaje;
	//@Inject
	private RubroDAOImpl rubroDAO = new RubroDAOImpl();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener lista de rubros", description = "Devuelve la lista de rubros ")
	public Response getRubros(){
		try {
	        List<Rubro> rubros = rubroDAO.lista();
	        return Response.ok().entity(rubros).build();
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
	@Operation(summary = "Obtener usuario", description = "Obtiene un rubro por su ID")
	public Response encontrar(@PathParam("id") @DefaultValue("6")Integer id) {
		Rubro rubro= (Rubro)rubroDAO.getById(id);
		if(rubro != null) {
			return Response.ok().entity(rubro).build();
		}
		else {
			mensaje="No se encontro el rubro";
			return Response.status(Status.NOT_FOUND).entity(mensaje).build();
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Crear Rubro", description = "Crea un nuevo rubro")
	public Response crear(Rubro rubro) {
		if(rubro.getNombre()!=null) {
			try {
				rubroDAO.guardar(rubro);
				return Response.created(null).entity(rubro).build();
			}catch(Exception e) {
				mensaje="El nombre del rubro se encuentra registrado";
				return Response.status(Status.BAD_REQUEST).entity(mensaje).build();
			}	
		}
		else {
			mensaje="Debe completar todos los campos";
			return Response.status(Status.BAD_REQUEST).entity(mensaje).build();
		}
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Modificar rubro", description = "Modificar un rubro existente")
	public Response editar(Rubro rubro){
		Rubro aux = (Rubro)rubroDAO.getById(rubro.getId());
		if (aux!=null && rubro.getNombre()!=null){
			try {
				rubroDAO.modificar(rubro);
				return Response.ok().entity(rubro).build();
			}catch(Exception e) {
				mensaje="El nombre del rubro se encuentra registrado";
				return Response.status(Status.BAD_REQUEST).entity(mensaje).build();
			}	
		} else {
			mensaje="Los datos no son válidos";
			return Response.status(Status.NOT_FOUND).entity(mensaje).build();
		}
	}
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.TEXT_PLAIN)
	@Operation(summary = "Eliminar rubro", description = "Elimina un rubro por su ID")
	public Response borrar(@PathParam("id") Integer id) {
		Rubro rubro =(Rubro) rubroDAO.getById(id);
		if(rubro!= null ) {
			rubroDAO.eliminar(rubro);
			return Response.noContent().build();
		}
		else {
			mensaje= "Rubro no encontrado";
			return Response.status(Status.NOT_FOUND).entity(mensaje).build();
		}
	}
	
	
	
}
