package helpers;

import java.util.ArrayList;
import java.util.List;
import entidades.*;
import dao.implementaciones.*;
import dao.interfaces.*;
import dto.CarritoDTO;
import dto.ProductoPedidoDTO;

public class PedidoHelper {

	public static void confirmarPedido(Pedido p) {
			RondaDAOImpl rondaDAO=new RondaDAOImpl();
	    	if(p.getDireccionEntrega()!=null) {
	    		p.confirmar(rondaDAO.rondaActual(),p.getDireccionEntrega(),p.getRangoHorarioD());
	    	}else{
	    		p.confirmar(rondaDAO.rondaActual(),p.getPuntoDeRetiro());
	    	}
	}
	
	public static void repetirPedido(Pedido nuevo,Pedido viejo) {
		RondaDAOImpl rondaDAO=new RondaDAOImpl();
    	nuevo.repetir(viejo,rondaDAO.rondaActual());
	}
	
	public static boolean setearProductosPedidos(CarritoDTO carrito,Pedido p) {
		boolean ok=true;
		ProductoDAOImpl productoDAO=new ProductoDAOImpl();
		for(ProductoPedidoDTO pp : carrito.getProductosPedidos()) {
			Producto producto=(Producto)productoDAO.getById(pp.getProducto_id());
			int cantidad=pp.getCantidad();
			if(producto!=null && cantidad>=1) {
				p.agregarProductoPedido(new ProductoPedido(producto,p,cantidad));
			}else {
				ok=false;
				break;
			}
		}
		return ok;
	}
	
	public static boolean verificarStock(Pedido p) {
		List<ProductoPedido> lpp = p.getProductosPedidos();
		boolean ok=true;
    	for (ProductoPedido productopedido : lpp ) {
    		if(! productopedido.getProducto().hayStockDisponible(productopedido.getCantidad())) {
    			ok=false;
    			break;
    		}
    	}
    	return ok;
	}
	
	public static boolean setearPedido(Pedido p,CarritoDTO carrito) {
		boolean ok=false;
		DireccionDAOImpl direccionDAO=new DireccionDAOImpl();
		PuntoDeRetiroDAOImpl puntoDeRetiroDAO=new PuntoDeRetiroDAOImpl();
		if(carrito.getDireccionEntrega_id()!=0 && carrito.getRangoHorarioD()!=null) {
			ok=true;
			p.setDireccionEntrega((Direccion)direccionDAO.getById(carrito.getDireccionEntrega_id()));
			p.setRangoHorarioD(carrito.getRangoHorarioD());
			p.setModalidadEntrega(ModalidadEntrega.Reparto);
			p.setPuntoDeRetiro(null);
		}else {
			ok=true;
			p.setPuntoDeRetiro((PuntoDeRetiro)puntoDeRetiroDAO.getById(carrito.getPuntoDeRetiro_id()));
			p.setModalidadEntrega(ModalidadEntrega.Retiro);
			p.setDireccionEntrega(null);
			p.setRangoHorarioD(null);
		}
		return ok;
	}
	
	public static boolean validacionDeStock(List<ProductoPedido> productosViejos,Pedido pedido,List<Producto> productosActualizar) {
		boolean valido=true;
		//Se recorre la lista que se tenia antes
		for(int i=0;i<productosViejos.size() && valido;i++) {
			boolean encontre=false;
			ProductoPedido ppV=productosViejos.get(i);
			//Se recorre la lista nueva
			for(int j=0;j<pedido.getProductosPedidos().size() && valido && ! encontre;j++) {
				ProductoPedido ppN=pedido.getProductosPedidos().get(j);
				//Si se encuentra el producto en la lista nueva
				if(ppV.getProducto().getId()==ppN.getProducto().getId()) {
					Producto p=ppN.getProducto();
					//Si la cantidad vieja es menor que la nueva entonces descuenta el stock
					if(ppV.getCantidad()<ppN.getCantidad()) { 
						p.descontarStock(ppN.getCantidad()-ppV.getCantidad());
						if(p.getStock()<0) {
							valido=false;
						}
					}
					//Si la cantidad vieja es mayor a la nueva entonces suma el stock
					else if(ppV.getCantidad()>ppN.getCantidad()) {
						p.sumarStock(ppV.getCantidad()-ppN.getCantidad());
					}
					if(valido) {
						productosActualizar.add(p);
						encontre=true;
					}
				}
			}
			if(! encontre && valido) { //Si no lo encontro signfica que lo borro por completo y tiene que sumarse lo que se tenia antes
				Producto p=ppV.getProducto();
				p.sumarStock(ppV.getCantidad());
				productosActualizar.add(p);
			}
		}
		if(valido) {
			//Falta el caso que se haya agregado como nuevo
			List<Producto>productosNuevos=new ArrayList<Producto>();
			for(int i=0;i<pedido.getProductosPedidos().size() && valido;i++) {
				Producto producto = pedido.getProductosPedidos().get(i).getProducto();    
				boolean productoExiste = false;
				for (Producto p : productosActualizar) {
			        if (p.getId() == producto.getId()) {
			            productoExiste = true;
			            break;
			        }
			    }
				if (!productoExiste) {//Si el producto no existe en la lista de productosActualizar significa que es nuevo y debe agregarse
				        producto.descontarStock(pedido.getProductosPedidos().get(i).getCantidad());
				        if(producto.getStock()>=0) {
				        	productosNuevos.add(producto);
				        }else {
				        	valido=false;
				        }
				}
			}
			if(valido) {
				productosActualizar.addAll(productosNuevos);
			}
		}
		return valido;
	}
	

}
