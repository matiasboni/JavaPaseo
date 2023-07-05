package api;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.core.Response.Status;

import java.util.List;

import dao.implementaciones.*;
import dao.interfaces.*;
import entidades.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

@Path("/productos")
@Tags(value= {@Tag(name="Productos",description="Metodos de productos")})
public class ProductosResources {

	String mensaje;
	
//@Inject
	private ProductoDAO productoDAO = new ProductoDAOImpl();
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener producto", description = "Obtiene un producto por su ID")
    @ApiResponse(responseCode = "200", description = "Producto encontrado", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content(mediaType = "text/plain"))
	public Response encontrar(@PathParam("id")@DefaultValue("1") Integer id) {
		Producto producto= (Producto)productoDAO.getById(id);
		if(producto != null) {
			return Response.ok().entity(producto).build();
		}
		else {
			mensaje="No se encontro el producto";
			return Response.status(Status.NOT_FOUND).entity(mensaje).build();
		}
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener lista de productos", description = "Devuelve la lista de pruductos")
	@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
		    @ApiResponse(responseCode = "400", description = "Error de consulta", content = @Content(mediaType = "text/plain")),
		    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
		})
	public Response getProductos(){
		try {
	        List<Producto> productos = productoDAO.lista();
	        return Response.ok().entity(productos).build();
	    } catch (IllegalArgumentException e) {
	    	mensaje="Error :";
	        return Response.status(Response.Status.BAD_REQUEST).entity(mensaje + e.getMessage()).build();
	    } catch (RuntimeException e) {
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error interno del servidor").build();
	    }
	}
	
	
	
}
