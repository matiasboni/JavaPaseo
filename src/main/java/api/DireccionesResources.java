package api;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.core.Response.Status;
import entidades.Direccion;
import entidades.Pedido;
import entidades.Rubro;
import entidades.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import dao.implementaciones.*;
import dao.interfaces.*;
import dto.DireccionDTO;

import java.util.List;


@Path("/direcciones")
@Tags(value= {@Tag(name="Direcciones",description="Metodos de direcciones")})
public class DireccionesResources {
	
	String mensaje;
	
	//@Inject
	private DireccionDAOImpl direccionDAO = new DireccionDAOImpl();
	private UsuarioDAOImpl usuarioDAO=new UsuarioDAOImpl();
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener direccion", description = "Obtiene una direccion por su ID")
    @ApiResponse(responseCode = "200", description = "Direccion encontrada", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "Direccion no encontrada", content = @Content(mediaType = "text/plain"))
	public Response encontrar(@PathParam("id") @DefaultValue("1")Integer id) {
		Direccion direccion= (Direccion)direccionDAO.getById(id);
		if(direccion != null) {
			return Response.ok().entity(direccion).build();
		}
		else {
			mensaje="No se encontro la direccion";
			return Response.status(Status.NOT_FOUND).entity(mensaje).build();
		}
	}
	
	
	@GET
	@Path("/usuario/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener direcciones de usuario", description = "Obtiene las direcciones de un usuario por su id")
    @ApiResponse(responseCode = "200", description = "Direcciones encontradas", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "Direcciones no encontradas", content = @Content(mediaType = "text/plain"))
	public Response direccionesDeUsuario(@PathParam("id") Integer id) {
		Usuario u=usuarioDAO.getById(id);
		if(u!=null) {
			List<Direccion>direcciones= direccionDAO.direccionesDelUsuario(u) ;
			return Response.ok().entity(direcciones).build();
		}
		else {
			mensaje="No se encontraron las direcciones";
			return Response.status(Status.NOT_FOUND).entity(mensaje).build();
		}
	}
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Crear Direccion", description = "Crea una nueva direccion")
	public Response crear(DireccionDTO direccionDTO) {
		try {
	        Direccion direccion = new Direccion();
	        direccion.setCalle(direccionDTO.getCalle());
	        direccion.setNro(direccionDTO.getNro());
	        direccion.setPiso(direccionDTO.getPiso());
	        direccion.setDepartamento(direccionDTO.getDepartamento());
	        
	       
	        Usuario usuario = usuarioDAO.getById(direccionDTO.getUsuario_id());
	        direccion.setUsuario(usuario);
	        
	        direccionDAO.guardar(direccion);

	        return Response.status(Response.Status.CREATED).entity(direccion).build();
		} catch (Exception e) {
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al crear la dirección").build();
	    }
	}
}
