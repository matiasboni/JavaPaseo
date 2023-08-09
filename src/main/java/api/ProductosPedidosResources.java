package api;

import dto.ProductoPedidoDTO;
import entidades.Pedido;
import entidades.Producto;
import entidades.ProductoPedido;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import dao.implementaciones.*;

@Path("/productosPedidos")
@Tags(value= {@Tag(name="Productos Pedidos",description="Metodos de productos pedidos")})
public class ProductosPedidosResources {
	
	//@Inject
	private ProductoPedidoDAOImpl ppDAO = new ProductoPedidoDAOImpl();
	private ProductoDAOImpl productoDAO = new ProductoDAOImpl();
	private PedidoDAOImpl pedidoDAO = new PedidoDAOImpl();
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Sumar o restar al producto pedido", description = "Sumar o restar al producto pedido")
	@RequestBody(content = @Content(mediaType = "application/json",
    examples = @ExampleObject(value = "")))
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Agregado exitosamente", content = @Content(mediaType = "application/json")),
	    @ApiResponse(responseCode = "400", description = "Solicitud no válida", content = @Content(mediaType = "text/plain"))
	})
	public Response modificar(ProductoPedidoDTO linea){
		Response r;
		Producto p = productoDAO.getById(linea.getProducto_id());
		if(linea.getCantidad()>0 && p.hayStockDisponible(linea.getCantidad())) {
			ProductoPedido pp = ppDAO.getById(linea.getId());
			pp.setCantidad(linea.getCantidad());
			ppDAO.modificar(pp);
			Pedido pe = pedidoDAO.getById(pp.getPedido().getId());
			pe.calcularTotal();
			pedidoDAO.modificar(pe);
			r= Response.ok().entity(pe).build();
		}
		else {
			r=Response.status(Status.BAD_REQUEST).build();
		}
		return r;
	}
	
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Eliminar línea de producto pedido", description = "Eliminar una línea de producto pedido por su ID")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Línea de producto pedido eliminada exitosamente", content = @Content(mediaType = "application/json")),
	    @ApiResponse(responseCode = "400", description = "Solicitud no válida", content = @Content(mediaType = "text/plain"))
	})
	public Response borrar(@PathParam("id") int id) {
		Response r;
		ProductoPedido pp = ppDAO.getById(id);
		if(pp != null) {
			Pedido pe = pedidoDAO.getById(pp.getPedido().getId());
			ppDAO.eliminar(pp);
			pe.eliminarProductoPedido(pp);
		    pedidoDAO.modificar(pe);
		    r = Response.ok().entity(pe).build();
		}
		else {
			r = Response.status(Status.NOT_FOUND).build();
		}
		   return r;
	}
}
