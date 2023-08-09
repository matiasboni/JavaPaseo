package api;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.core.Response.Status;

import java.math.BigDecimal;
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
	private ImagenDAO imagenDAO =new ImagenDAOImpl();
	
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
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Crear Producto", description = "Crea un nuevo producto")
	public Response crear(Producto producto) {
		if(producto.getNombre()!=null && producto.getDescripcion()!=null && producto.getPrecio()!=null && producto.getPrecio().compareTo(BigDecimal.ZERO) > 0 && producto.getProductor()!=null && producto.getRubro()!=null) {
			try {
				productoDAO.guardar(producto);
				return Response.created(null).entity(producto).build();
			}catch(Exception e) {
				mensaje="El nombre del producto se encuentra registrado para ese productor";
				return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
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
	@Operation(summary = "Modificar producto", description = "Modificar un producto existente")
	public Response editar(Producto producto){
		Producto aux = (Producto)productoDAO.getById(producto.getId());
		if (aux!=null && producto.getNombre()!=null && producto.getDescripcion()!=null && producto.getPrecio()!=null && producto.getPrecio().compareTo(BigDecimal.ZERO) > 0 && producto.getProductor()!=null && producto.getRubro()!=null){
			try {
				productoDAO.modificar(producto);
				return Response.ok().entity(producto).build();
			}catch(Exception e) {
				mensaje="El nombre del producto se encuentra registrado para ese productor";
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
	@Operation(summary = "Eliminar producto", description = "Elimina un producto por su ID")
	public Response borrar(@PathParam("id") Integer id) {
		Producto producto =(Producto) productoDAO.getById(id);
		if(producto!= null ) {
			productoDAO.eliminar(producto);
			return Response.noContent().build();
		}
		else {
			mensaje= "Producto no encontrado";
			return Response.status(Status.NOT_FOUND).entity(mensaje).build();
		}
	}
	
	
	
}
