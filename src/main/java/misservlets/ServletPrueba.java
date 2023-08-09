package misservlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

import auxiliares.MD5;
import dao.implementaciones.ProductoDAOImpl;
import dao.implementaciones.UsuarioDAOImpl;
import dao.implementaciones.*;
import dao.interfaces.*;
import entidades.Direccion;
import entidades.Estado;
import entidades.Imagen;
import entidades.ModalidadEntrega;
import entidades.Pedido;
import entidades.Producto;
import entidades.ProductoPedido;
import entidades.Productor;
import entidades.PuntoDeRetiro;
import entidades.Rol;
import entidades.Ronda;
import entidades.Rubro;
import entidades.Usuario;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

/**
 * Servlet implementation class ServletPrueba
 */

public class ServletPrueba extends HttpServlet {
	
	private Usuario u;
	private Usuario u1;
	private Usuario u2;
	private Usuario u3;
	private Usuario u4;
	private Rubro r;
	private Rubro r1;
	private Rubro r2;
	Productor p;
	Productor p1;
	Productor p2;
	Producto produc;
	Producto produc1;
	Producto produc2;
	Pedido pedido;
	Pedido pedido1; 
	Pedido pedido2;
	Ronda ronda;
	PuntoDeRetiro punto;
	Direccion direccion;
	ProductoPedido productopedido;
	
	//@Inject
	private UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
	//@Inject
	private DireccionDAO direccionDAO = new DireccionDAOImpl();
	//@Inject
	private PedidoDAO pedidoDAO =new PedidoDAOImpl();
	//@Inject
	private ProductoDAO productoDAO = new ProductoDAOImpl();
	//@Inject
	private ProductoPedidoDAO productoPedidoDAO= new ProductoPedidoDAOImpl();
	//@Inject
	private ProductorDAO productorDAO = new ProductorDAOImpl();
	//@Inject
	private PuntoDeRetiroDAO puntoDeRetiroDAO = new PuntoDeRetiroDAOImpl();
	//@Inject
	private RondaDAO rondaDAO= new RondaDAOImpl();
	//@Inject
	private RubroDAO rubroDAO = new RubroDAOImpl();
	
    public ServletPrueba() {
       
    }
    
    public void pruebasUsuario() {
    	u=new Usuario("admin@gmail.com",MD5.encodeMD5("12345678"),Rol.admin, "Juan", "Ro", "4123123");
		u1=new Usuario("visitante1@gmail.com",MD5.encodeMD5("12345678"),Rol.visitante, "Pedro", "Rodriguez", "41231232");
		u2=new Usuario("visitante2@gmail.com",MD5.encodeMD5("12345678"),Rol.visitante, "Pepito", "Perez", "41231234");
		u3=new Usuario("visitante3@gmail.com",MD5.encodeMD5("12345678"),Rol.visitante, "Juan", "cra", "91218000");
		u4=new Usuario("visitante4@gmail.com",MD5.encodeMD5("12345678"),Rol.visitante, "Enzo", "Pe", "91218001");
		//Se crea un usuario admin
		usuarioDAO.guardar(u);
		//Se crean usuarios visitante
		usuarioDAO.guardar(u1);
		usuarioDAO.guardar(u2);
		usuarioDAO.guardar(u3);
		usuarioDAO.guardar(u4);
		//Se recupera la lista de usuarios
		System.out.println("Lista de usuarios despues de crear");
		List<Usuario> lu=usuarioDAO.lista();
		for(Usuario usuario :lu ) {
			System.out.println(usuario.toString());
		}
		//Se elimina un usuario
		usuarioDAO.eliminar(u3);
		//Lista sin el usuario eliminado
		System.out.println("Lista luego de eliminar");
		lu=usuarioDAO.lista();
		for(Usuario usuario :lu ) {
			System.out.println(usuario.toString());
		}
		System.out.println("Se le modifica el email a "+u4.toString());
		u4.setEmail("emaildistinto@gmail.com");
		usuarioDAO.modificar(u4);
		System.out.println("Email modificado:"+usuarioDAO.getById(u4.getId()));
		u1.agregarDireccion(new Direccion("Calle 1","0000"));
		usuarioDAO.modificar(u1);
    }
    
    public void pruebasRubro() {
    	r=new Rubro("Frutas");
    	r1=new Rubro("Semillas");
    	r2=new Rubro("Quesos");
    	//Se guardan los rubros
    	rubroDAO.guardar(r);
    	rubroDAO.guardar(r1);
    	rubroDAO.guardar(r2);
    	//Se recupera la lista de usuarios
    	System.out.println("Lista de rubros despues de crear");
    	List<Rubro> lr=rubroDAO.lista();
    	for(Rubro rubro :lr ) {
    		System.out.println(rubro.toString());
    	}
    	//Se elimina un rubro
    	rubroDAO.eliminar(r2);
    	//Lista sin el rubro eliminado
    	System.out.println("Lista luego de eliminar");
    	lr=rubroDAO.lista();
    	for(Rubro rubro :lr ) {
    		System.out.println(rubro.toString());
    	}
    	System.out.println("Se le modifica el nombre a "+r1.toString());
    	r1.setNombre("Verduras");
    	rubroDAO.modificar(r1);
    	System.out.println("Nombre de rubro modificado:"+rubroDAO.getById(r1.getId()));
    }
    
    public void pruebasProductor() {
    	p=new Productor("Productor1","Soy el productor1");
    	p1=new Productor("Productor2","Soy el productor2");
    	p2=new Productor("Productor3","Soy el productor3");
    	//Se guardan los productores
    	productorDAO.guardar(p);
    	productorDAO.guardar(p1);
    	productorDAO.guardar(p2);
    	//Se recupera la lista de productores
    	System.out.println("Lista de productores despues de crear");
    	List<Productor> lp=productorDAO.lista();
    	for(Productor productor :lp ) {
    		System.out.println(productor.toString());
    	}
    	//Se elimina un productor 
    	productorDAO.eliminar(p2);
    	//Lista sin el productor eliminado
    	System.out.println("Lista luego de eliminar");
    	lp=productorDAO.lista();
    	for(Productor productor :lp ) {
    		System.out.println(productor.toString());
    	}
    	//Se modifica un productor
    	System.out.println("Se le modifica el nombre a "+p1.toString());
    	p1.setNombre("Carlos Gonzalez");
    	productorDAO.modificar(p1);
    	System.out.println("Productor modificado:"+productorDAO.getById(p1.getId()));
    	//Se agregan imagenes a productores
    	p.agregarImagen(new Imagen("imagen1.com"));
    	p.agregarImagen(new Imagen("imagen25.com"));
    	p1.agregarImagen(new Imagen("imagen10.com"));
    	productorDAO.modificar(p);
    	productorDAO.modificar(p1);
    	List<Imagen> li=productorDAO.imagenesDeProductor(p);
    	System.out.println("Imagenes del productor Productor1:");
    	for(Imagen imagen :li) {
    		System.out.println(imagen.toString());
    	}
    	
    }
    
    public void pruebasProductos() {
    	produc=new Producto("Naranja","Descripcion naranja",BigDecimal.valueOf(50),r,p);
    	Imagen imagen=new Imagen("https://res.cloudinary.com/estebandiaz/image/upload/w_256,f_auto/cocinario-beta-foods/5f22b58d6eb30acead5eb7cc.png");
    	imagen.setProducto(produc);
    	produc.agregarImagen(imagen);
    	produc1=new Producto("Banana","Descripcion banana",BigDecimal.valueOf(150),r,p1);
    	imagen=new Imagen("https://images.openfoodfacts.org/images/products/4011/front_en.19.400.jpg");
    	imagen.setProducto(produc1);
    	produc1.agregarImagen(imagen);
    	produc2=new Producto("Lechuga","Descripcion lechuga",BigDecimal.valueOf(150),r1,p1);
    	imagen=new Imagen("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSQ-3WI5_dRPAbgXtxtKxsRSspzeqcGrM_mLYGQwXb-DVGAcCu-9RiGBgVecBQ7Wu06yxQ&usqp=CAU");
    	imagen.setProducto(produc2);
    	produc2.agregarImagen(imagen);
    	//Se guardan los productos
    	productoDAO.guardar(produc);
    	productoDAO.guardar(produc1);
    	productoDAO.guardar(produc2);
    	produc=new Producto("Pera","Descripcion Pera",BigDecimal.valueOf(50),r,p1);
    	imagen=new Imagen("https://tienda.biota.app/web/image/product.template/599/image_256");
    	imagen.setProducto(produc);
    	produc.agregarImagen(imagen);
    	productoDAO.guardar(produc);
    	produc=new Producto("Uva","Descripcion Uva",BigDecimal.valueOf(50),r,p1);
    	imagen=new Imagen("https://images.rappi.com.ar/products/1612640-1604433606562.png?d=136x136&e=webp");
    	imagen.setProducto(produc);
    	produc.agregarImagen(imagen);
    	productoDAO.guardar(produc);
    	produc=new Producto("Uva","Descripcion Uva",BigDecimal.valueOf(50),r,p);
    	imagen=new Imagen("https://images.rappi.com.ar/products/1612648-1604433606593.png?d=136x136&e=webp");
    	imagen.setProducto(produc);
    	produc.agregarImagen(imagen);
    	productoDAO.guardar(produc);
    	produc=new Producto("Manzana","Descripcion manzana",BigDecimal.valueOf(50),r,p);
    	imagen=new Imagen("https://consol.coop.ar/web/image/product.template/11651/image_256/%5B634%5D%20Manzana%20roja%20x%20kg?unique=9f687c8");
    	imagen.setProducto(produc);
    	produc.agregarImagen(imagen);
    	productoDAO.guardar(produc);
    	//Se recupera la lista de todos los productos
    	System.out.println("Lista de productos despues de crear");
    	List<Producto> lp=productoDAO.lista();
    	for(Producto producto :lp ) {
    		System.out.println(producto.toString());
    	}
    	//Se recuperan los productos de un rubro
    	System.out.println("Productos del rubro Frutas:");
    	lp=rubroDAO.productosDeRubro(r);
    	for(Producto producto :lp ) {
    		System.out.println(producto.toString());
    	}
    	//Se recuperan los productos pertenecientes a un productor
    	System.out.println("Productos del productor Productor2:");
    	lp=productorDAO.productosDeProductor(p1);
    	for(Producto producto :lp ) {
    		System.out.println(producto.toString());
    	}
    	
    }
    
    public void pruebasPedidoEntrega() {
    	crearRonda_PuntoRetiro_Direccion();
    	pedido=new Pedido(Estado.Preparacion,u2);
    	//Guardar pedido
    	pedidoDAO.guardar(pedido);
    	System.out.println("Pedido despues de guardar");
    	System.out.println(pedido);
    	//Agregar Producto al pedido
    	ProductoPedido pp = new ProductoPedido(produc);
    	pp.setCantidad(2);
    	if (produc.hayStockDisponible(2)) {
    		pp.setPedido(pedido);
    		pedido.agregarProductoPedido(pp);
    	}
    	ProductoPedido pp1 = new ProductoPedido(produc1);
    	pp1.setCantidad(1);
    	if (produc1.hayStockDisponible(1)) {
    		pp1.setPedido(pedido);
    		pedido.agregarProductoPedido(pp1);	
    	}
    	pedido.confirmar(ronda, direccion,"De 10 a 12"); //Esta en pendiente
    	pedidoDAO.modificar(pedido);
    	System.out.println("Pedido despues de confirmar");
    	System.out.println(pedido);
    	List<ProductoPedido> lpp = pedido.getProductosPedidos();
    	for (ProductoPedido productopedido : lpp ) {
    		productopedido.getProducto().descontarStock(productopedido.getCantidad());
    		productoDAO.modificar(productopedido.getProducto());
    		System.out.println("Producto despues de modificar el stock:");
    		System.out.println(productopedido.getProducto());
    	}
    	
    	//Se recuperan los pedidos pertenecientes a un usuario
    	System.out.println("Pedidos del usuario usuario2:");
    	List<Pedido>lpd=pedidoDAO.pedidosDelUsuario(u2);
    	for(Pedido pedido :lpd ) {
    		System.out.println(pedido);
    	}
    }
    
    public void pruebasPedidoRetiro() {
    	pedido1=new Pedido(Estado.Preparacion,u1);
    	//Guardar pedido
    	pedidoDAO.guardar(pedido1);
    	System.out.println("Pedido despues de guardar");
    	System.out.println(pedido);
    	//Agregar Producto al pedido
    	ProductoPedido pp = new ProductoPedido(produc);
    	pp.setCantidad(2);
    	if (produc.hayStockDisponible(2)) {
    		pp.setPedido(pedido1);
    		pedido1.agregarProductoPedido(pp);
    	}
    	pedido1.confirmar(ronda, punto); //Esta en pendiente
    	pedidoDAO.modificar(pedido1);
    	System.out.println("Pedido despues de confirmar");
    	System.out.println(pedido);
    	List<ProductoPedido> lpp = pedido1.getProductosPedidos();
    	for (ProductoPedido productopedido : lpp ) {
    		productopedido.getProducto().descontarStock(productopedido.getCantidad());
    		productoDAO.modificar(productopedido.getProducto());
    		System.out.println("Producto despues de modificar el stock:");
    		System.out.println(productopedido.getProducto());
    	}
    	
    	//Se recuperan los pedidos pertenecientes a un usuario
    	System.out.println("Pedidos del usuario usuario1:");
    	List<Pedido>lpd=pedidoDAO.pedidosDelUsuario(u1);
    	for(Pedido pedido :lpd ) {
    		System.out.println(pedido);
    	}
    	//Se cancela el pedido
    	pedido1.cancelar();
    	pedidoDAO.modificar(pedido1);
    	lpp = pedido1.getProductosPedidos();
    	for (ProductoPedido productopedido : lpp ) {
    		productopedido.getProducto().sumarStock(productopedido.getCantidad());
    		productoDAO.modificar(productopedido.getProducto());
    		System.out.println("Producto despues de modificar el stock:");
    		System.out.println(productopedido.getProducto());
    	}
    	System.out.println("Pedido despues de cancelar:");
    	System.out.println(pedido1);
    	Pedido pedido5=new Pedido(Estado.Preparacion,u1);
    	//Guardar pedido
    	pedidoDAO.guardar(pedido5);
    	Pedido pedido6=new Pedido(Estado.Preparacion,u2);
    	pedidoDAO.guardar(pedido6);
    }
    
    private void pruebasPedido() {
    	//Lista de pedidos por modalidad entrega
    	List<Pedido> lp=pedidoDAO.pedidosPorModalidad(ModalidadEntrega.Reparto);
    	System.out.println("Lista de pedidos para reparto");
    	for(Pedido p :lp) {
    		System.out.println(p);
    	}
    	lp=pedidoDAO.pedidosPorModalidad(ModalidadEntrega.Retiro);
    	System.out.println("Lista de pedidos para retiro");
    	for(Pedido p :lp) {
    		System.out.println(p);
    	}
    }
     
    
    
    private void crearRonda_PuntoRetiro_Direccion() {
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.YEAR, 2023);
    	calendar.set(Calendar.MONTH, Calendar.AUGUST);
    	calendar.set(Calendar.DAY_OF_MONTH, 7);
    	java.util.Date fechaInicio=calendar.getTime();
    	calendar.set(Calendar.DAY_OF_MONTH, 11);
    	LocalDate fechaRetiro = LocalDate.of(2023, 8, 12);
    	LocalTime retiroInicio = LocalTime.of(8, 0);
    	LocalTime retiroFin = LocalTime.of(17, 0);
    	ronda = new Ronda(fechaInicio, calendar.getTime(), fechaRetiro, retiroInicio, retiroFin);
    	punto=new PuntoDeRetiro("Facultad", "Calle 50", "8 a 12");
    	puntoDeRetiroDAO.guardar(punto);
    	punto=new PuntoDeRetiro("Facultad", "Calle 50", "17 a 20");
    	puntoDeRetiroDAO.guardar(punto);
    	punto=new PuntoDeRetiro("Paseo", "Calle 000", "8 a 14");
    	puntoDeRetiroDAO.guardar(punto);
    	direccion=new Direccion("Calle 0","0000");
    	direccion.setUsuario(u2);
    	//Se guardan la ronda y el punto de retiro
    	rondaDAO.guardar(ronda);
    	direccionDAO.guardar(direccion);
    }
    
    
    private void crearPedidosRepartoRetiro() {
    	pedido=new Pedido(Estado.Preparacion,u2);
    	//Guardar pedido
    	pedidoDAO.guardar(pedido);
    	//Agregar Producto al pedido
    	ProductoPedido pp = new ProductoPedido(produc);
    	pp.setCantidad(2);
    	if (produc.hayStockDisponible(2)) {
    		pp.setPedido(pedido);
    		pedido.agregarProductoPedido(pp);
    	}
    	ProductoPedido pp1 = new ProductoPedido(produc2);
    	pp1.setCantidad(1);
    	if (produc1.hayStockDisponible(1)) {
    		pp1.setPedido(pedido);
    		pedido.agregarProductoPedido(pp1);	
    	}
    	pedido.confirmar(ronda, direccion,"De 10 a 12"); //Esta en pendiente
    	pedidoDAO.modificar(pedido);
    	
    	
    	
    	
    	pedido=new Pedido(Estado.Preparacion,u2);
    	//Guardar pedido
    	pedidoDAO.guardar(pedido);
    	//Agregar Producto al pedido
        pp1 = new ProductoPedido(produc2);
    	pp1.setCantidad(1);
    	if (produc1.hayStockDisponible(1)) {
    		pp1.setPedido(pedido);
    		pedido.agregarProductoPedido(pp1);	
    	}
    	pedido.confirmar(ronda, direccion,"De 10 a 12"); //Esta en pendiente
    	pedidoDAO.modificar(pedido);
    	
    	
    	
    	pedido=new Pedido(Estado.Preparacion,u1);
    	//Guardar pedido
    	pedidoDAO.guardar(pedido);
    	//Agregar Producto al pedido
        pp1 = new ProductoPedido(produc);
    	pp1.setCantidad(1);
    	if (produc1.hayStockDisponible(1)) {
    		pp1.setPedido(pedido);
    		pedido.agregarProductoPedido(pp1);	
    	}
    	pedido.confirmar(ronda, punto);
    	pedidoDAO.modificar(pedido);
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.pruebasUsuario();
		System.out.println("-".repeat(50));
		this.pruebasRubro();
		System.out.println("-".repeat(50));
		this.pruebasProductor();
		System.out.println("-".repeat(50));
		this.pruebasProductos();
		System.out.println("-".repeat(50));
		this.pruebasPedidoEntrega();
		System.out.println("-".repeat(50));
		this.pruebasPedidoRetiro();
		System.out.println("-".repeat(50));
		this.pruebasPedido();
		this.crearPedidosRepartoRetiro();
	}

}
