package api;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.core.Response.Status;
import entidades.*;
import dao.implementaciones.*;
import dao.interfaces.*;
import dto.CarritoDTO;
import dto.ProductoPedidoDTO;
import helpers.PedidoHelper;
import helpers.ProductoPedidoHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

@Path("/pedidos")
@Tags(value= {@Tag(name="Pedidos",description="Metodos de pedidos")})
public class PedidosResources {

	String mensaje;
	
	//@Inject
	private PedidoDAOImpl pedidoDAO = new PedidoDAOImpl();
	//@Inject
	private ProductoDAOImpl productoDAO = new ProductoDAOImpl();
	private UsuarioDAOImpl usuarioDAO=new UsuarioDAOImpl();
	
	@PUT
	@Path("/confirmar")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Confirmar pedido", description = "Confirma un pedido y pasa a estado pendiente")
	@RequestBody(content = @Content(mediaType = "application/json",schema = @Schema(implementation = Pedido.class),
    examples = @ExampleObject(value = "{\"id\":3,\"puntoDeRetiro_id\":1,\"productosPedidos\":[{\"producto_id\":1,\"pedido_id\":3,\"cantidad\":2}]}")))
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Pedido confirmado exitosamente", content = @Content(mediaType = "application/json")),
	    @ApiResponse(responseCode = "400", description = "Solicitud no válida", content = @Content(mediaType = "text/plain"))
	})
	public Response confirmarPedido(CarritoDTO carrito){
		Pedido pedido=(Pedido)pedidoDAO.getById(carrito.getId());
		Response r;
		if(pedido!=null && pedido.getEstado().equals(Estado.Preparacion) && (carrito.getPuntoDeRetiro_id() !=0 || carrito.getDireccionEntrega_id() !=0)){
			if(PedidoHelper.setearPedido(pedido, carrito) && PedidoHelper.verificarStock(pedido)) {
				PedidoHelper.confirmarPedido(pedido);
				pedidoDAO.modificar(pedido);
				List<ProductoPedido> lpp = pedido.getProductosPedidos();
			    for (ProductoPedido productopedido : lpp ) {
			    	productopedido.getProducto().descontarStock(productopedido.getCantidad());
			    	productoDAO.modificar(productopedido.getProducto());
			    }
			    Usuario u=pedido.getUsuario();
			    Pedido p=new Pedido(Estado.Preparacion,u);
				pedidoDAO.guardar(p);
			    r= Response.ok().entity(pedido).build();
			}
			else {
				mensaje="Solicitud no válida";
				r=Response.status(Status.BAD_REQUEST).entity(mensaje).build();
			}
		}else {
			mensaje="Solicitud no válida";
			r=Response.status(Status.BAD_REQUEST).entity(mensaje).build();
		}
		return r;
	}
	
	@PUT
	@Path("/cancelar/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Cancelar pedido", description = "Cancela un pedido")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Pedido cancelado exitosamente", content = @Content(mediaType = "application/json")),
	    @ApiResponse(responseCode = "400", description = "Solicitud no válida", content = @Content(mediaType = "text/plain"))
	})
	public Response cancelarPedido(@PathParam("id") @DefaultValue("3")Integer id){
		Pedido pedido=(Pedido)pedidoDAO.getById(id);
		Response r;
		if(pedido!=null && pedido.cancelar()){
				pedidoDAO.modificar(pedido);
				List<ProductoPedido> lpp = pedido.getProductosPedidos();
		    	for (ProductoPedido productopedido : lpp ) {
		    		productopedido.getProducto().sumarStock(productopedido.getCantidad());
		    		productoDAO.modificar(productopedido.getProducto());
		    	}
				r= Response.ok().entity(pedido).build();
		}else {
			mensaje="El pedido no puede ser cancelado o no existe";
			r=Response.status(Status.BAD_REQUEST).entity("mensaje").build();
		}
		return r;
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener pedido", description = "Obtiene un pedido por su ID")
    @ApiResponse(responseCode = "200", description = "Pedido encontrado", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "Pedido no encontrado", content = @Content(mediaType = "text/plain"))
	public Response encontrar(@PathParam("id") @DefaultValue("3")Integer id) {
		Pedido p= (Pedido)pedidoDAO.getById(id);
		if(p != null) {
			return Response.ok().entity(p).build();
		}
		else {
			mensaje="No se encontro el pedido";
			return Response.status(Status.NOT_FOUND).entity(mensaje).build();
		}
	}
	
	@GET
	@Path("/usuario/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener pedidos de usuario", description = "Obtiene los pedidos de un usuario por su id")
    @ApiResponse(responseCode = "200", description = "Pedidos encontrados", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "Pedidos no encontrados", content = @Content(mediaType = "text/plain"))
	public Response pedidosDeUsuario(@PathParam("id") Integer id) {
		Usuario u=usuarioDAO.getById(id);
		if(u!=null) {
			List<Pedido>pedidos=pedidoDAO.pedidosDelUsuario(u);
			return Response.ok().entity(pedidos).build();
		}
		else {
			mensaje="No se encontraron los pedidos";
			return Response.status(Status.NOT_FOUND).entity(mensaje).build();
		}
	}
	
	@GET
	@Path("/pendiente/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener pedido pendiente de usuario", description = "Obtiene el carrito del usuario")
    @ApiResponse(responseCode = "200", description = "Carrito encontrado", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "Carrito no encontrado", content = @Content(mediaType = "text/plain"))
	public Response pedidosPendiente(@PathParam("id") Integer id) {
		Usuario u=usuarioDAO.getById(id);
		if(u!=null) {
			Pedido p=pedidoDAO.pedidoPreparacion(u);
			return Response.ok().entity(p).build();
		}
		else {
			mensaje="Carrito no encontrado";
			return Response.status(Status.NOT_FOUND).entity(mensaje).build();
		}
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Modificar pedido", description = "Modifica un pedido")
	@RequestBody(content = @Content(mediaType = "application/json",schema = @Schema(implementation = Pedido.class),
    examples = @ExampleObject(value = "{\"id\":3,\"puntoDeRetiro_id\":1,\"productosPedidos\":[{\"producto_id\":1,\"pedido_id\":3,\"cantidad\":3},{\"producto_id\":2,\"pedido_id\":3,\"cantidad\":2}]}")))
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Pedido modificado exitosamente", content = @Content(mediaType = "application/json")),
	    @ApiResponse(responseCode = "400", description = "Solicitud no válida", content = @Content(mediaType = "text/plain"))
	})
	public Response modificarPedido(CarritoDTO carrito){
		Pedido pedido=(Pedido)pedidoDAO.getById(carrito.getId());
		Response r;
		//Se verifica que existe el pedido,que se encuentre pendientes y que haya un punto de retiro o una direccion de entrega
		if(pedido!=null && pedido.getEstado().equals(Estado.Pendiente) && (carrito.getPuntoDeRetiro_id() !=0 || carrito.getDireccionEntrega_id() !=0) ){
			List<ProductoPedido> productosViejos=new ArrayList<ProductoPedido>(pedido.getProductosPedidos());
			pedido.setProductosPedidos(new ArrayList<ProductoPedido>());
			pedido.setTotal(BigDecimal.ZERO);
			List<Producto> productosActualizar =new ArrayList<Producto>();
			//Se setea como tendria que quedar el pedido si se puede actualizar
			if(PedidoHelper.setearPedido(pedido, carrito) && PedidoHelper.setearProductosPedidos(carrito, pedido)) {
				//Se setean en null los contrarios de la modalidad por si se envian en ambos atributos algo !=0
				//Si se envian en ambos 1 por defecto toma la modalidaEntrega de reparto
				if(pedido.getModalidadEntrega().equals(ModalidadEntrega.Reparto)) {
					pedido.setPuntoDeRetiro(null);
				}else {
					pedido.setDireccionEntrega(null);
				}
				//Se valida el stock y se arma la lista de productosActualizar que es la lista que mantiene como deben quedar los productos en la base de datos
				if(PedidoHelper.validacionDeStock(productosViejos, pedido, productosActualizar)) {
					ProductoPedidoHelper.eliminarAnteriores(pedido);//Se eliminan los anteriores productospedidos
					pedidoDAO.modificar(pedido);
					for(Producto producto : productosActualizar) {
						productoDAO.modificar(producto);
					}
					r= Response.ok().entity(pedido).build();
				}
				else {
					mensaje="No hay suficiente stock";
					r=Response.status(Status.BAD_REQUEST).entity(mensaje).build();
				}
			}
			else {
				mensaje="Solicitud no válida";
				r=Response.status(Status.BAD_REQUEST).entity(mensaje).build();
			}
		}else {
			mensaje="Solicitud no válida";
			r=Response.status(Status.BAD_REQUEST).entity(mensaje).build();
		}
		return r;
	}
	
	
	
	@PUT
	@Path("/agregar")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Agregar al pedido", description = "Agregar Producto al pedido")
	@RequestBody(content = @Content(mediaType = "application/json",
    examples = @ExampleObject(value = "")))
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Agregado exitosamente", content = @Content(mediaType = "application/json")),
	    @ApiResponse(responseCode = "400", description = "Solicitud no válida", content = @Content(mediaType = "text/plain"))
	})
	public Response agregarAlPedido(ProductoPedidoDTO linea){
		Response r;
		Usuario u=usuarioDAO.getById(linea.getPedido_id());
		Producto p=productoDAO.getById(linea.getProducto_id());
		if(u!=null && linea.getCantidad()>0 && p!=null && p.hayStockDisponible(linea.getCantidad())) {
			Pedido pedido=(Pedido)pedidoDAO.pedidoPreparacion(u);
			boolean productoExistente = pedido.getProductosPedidos().stream().anyMatch(productoPedido -> productoPedido.getProducto().getId() == p.getId());
			if(productoExistente) {
				mensaje="El producto ya se encuentra en el carrito";
				r=Response.status(Status.BAD_REQUEST).entity(mensaje).build();
			}else {
				ProductoPedido pp=new ProductoPedido(p,pedido,linea.getCantidad());
				pedido.agregarProductoPedido(pp);
				pedidoDAO.modificar(pedido);
				r= Response.ok().entity(pedido).build();
			}
		}
		else {
			mensaje="Solicitud no válida";
			r=Response.status(Status.BAD_REQUEST).entity(mensaje).build();
			}
		return r;
	}
}
