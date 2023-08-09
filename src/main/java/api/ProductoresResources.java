package api;

import java.util.List;

import dao.implementaciones.ProductorDAOImpl;
import entidades.Productor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/productores")
@Tags(value= {@Tag(name="Productores",description="Metodos de productores")})
public class ProductoresResources {
	
	String mensaje;
	//@Inject
	private ProductorDAOImpl productorDAO = new ProductorDAOImpl();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener lista de productores", description = "Devuelve la lista de productores ")
	public Response getProductores(){
		try {
	        List<Productor> productores = productorDAO.lista();
	        return Response.ok().entity(productores).build();
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
	@Operation(summary = "Obtener productor", description = "Obtiene un productor por su ID")
	public Response encontrar(@PathParam("id") Integer id) {
		Productor productor= (Productor)productorDAO.getById(id);
		if(productor != null) {
			return Response.ok().entity(productor).build();
		}
		else {
			mensaje="No se encontro el productor";
			return Response.status(Status.NOT_FOUND).entity(mensaje).build();
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Crear Productor", description = "Crea un nuevo productor")
	public Response crear(Productor productor) {
		if(productor.getNombre()!=null && productor.getDescripcion()!=null) {
			try {
				productorDAO.guardar(productor);
				return Response.created(null).entity(productor).build();
			}catch(Exception e) {
				mensaje="El nombre del productor se encuentra registrado";
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
	@Operation(summary = "Modificar productor", description = "Modificar un productor existente")
	public Response editar(Productor productor){
		Productor aux = (Productor)productorDAO.getById(productor.getId());
		if (aux!=null && productor.getNombre()!=null && productor.getDescripcion()!=null){
			try {
				productorDAO.modificar(productor);
				return Response.ok().entity(productor).build();
			}catch(Exception e) {
				mensaje="El nombre del productor se encuentra registrado";
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
	@Operation(summary = "Eliminar productor", description = "Elimina un productor por su ID")
	public Response borrar(@PathParam("id") Integer id) {
		Productor productor =(Productor) productorDAO.getById(id);
		if(productor!= null ) {
			productorDAO.eliminar(productor);
			return Response.noContent().build();
		}
		else {
			mensaje= "Productor no encontrado";
			return Response.status(Status.NOT_FOUND).entity(mensaje).build();
		}
	}
	

}
