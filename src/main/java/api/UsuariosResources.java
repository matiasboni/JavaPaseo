package api;


import java.util.List;

import org.hibernate.exception.ConstraintViolationException;

import auxiliares.MD5;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Email;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.core.Response.Status;
import entidades.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperties;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.Parameters.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import dao.implementaciones.*;
import dao.interfaces.*;

@Path("/usuarios")
@Tags(value= {@Tag(name="Usuarios",description="Gestión de usuarios: ABM de Administrador y consulta de usuarios")})
public class UsuariosResources {

	private String mensaje;
	
	//@Inject
	private UsuarioDAOImpl usuarioDAO = new UsuarioDAOImpl();
	private PedidoDAOImpl pedidoDAO = new PedidoDAOImpl();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener lista de usuarios", description = "Devuelve una lista de usuarios registrados(admin y visitantes)")
	@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
		    @ApiResponse(responseCode = "400", description = "Error de consulta", content = @Content(mediaType = "text/plain")),
		    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
		})
	public Response getUsuarios(){
		try {
	        List<Usuario> usuarios = usuarioDAO.lista();
	        return Response.ok().entity(usuarios).build();
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
	@Operation(summary = "Crear usuario", description = "Crea un nuevo usuario")
	@RequestBody(content = @Content(mediaType = "application/json",schema = @Schema(implementation = Usuario.class),
            examples = @ExampleObject(value = "{\"email\": \"adminapi@gmail.com\", \"password\":\"123456\", \"rol\":\"admin\"}")))
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente", content = @Content(mediaType = "application/json")),
	    @ApiResponse(responseCode = "400", description = "Debe enviar un email,una password y un rol", content = @Content(mediaType = "text/plain")),
	    @ApiResponse(responseCode = "400", description = "El email se encuentra repetido", content = @Content(mediaType = "text/plain"))
	})
	public Response crear(@Parameter(description="Los atributos email,password y rol son requeridos")Usuario usuario) {
			if((usuario.getEmail()!=null)&&(usuario.getPassword()!=null)&&(usuario.getRol()!=null)) {
				try {
					usuario.setPassword(MD5.encodeMD5(usuario.getPassword()));
					usuarioDAO.guardar(usuario);
					if(usuario.getRol().equals(Rol.visitante)) {
						Pedido p=new Pedido(Estado.Preparacion,usuario);
						pedidoDAO.guardar(p);
					}
					return Response.created(null).entity(usuario).build();
				//}catch(ConstraintViolationException e) { No tomaba la excepción ya que lanza una excpeción SQLIntegrityConstraintViolationException y no podia ser capturada
					
				}catch(Exception e) {
					mensaje="El email se encuentra repetido";
					return Response.status(Status.BAD_REQUEST).entity(mensaje).build();
				}	
			}
			else {
				mensaje="Debe completar todos los campos";
				return Response.status(Status.BAD_REQUEST).entity(mensaje).build();
			}
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener usuario", description = "Obtiene un usuario(admin o visitante) por su ID")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(mediaType = "text/plain"))
	public Response encontrar(@PathParam("id") @DefaultValue("6")Integer id) {
		Usuario usuario= (Usuario)usuarioDAO.getById(id);
		if(usuario != null) {
			return Response.ok().entity(usuario).build();
		}
		else {
			mensaje="No se encontro el usuario";
			return Response.status(Status.NOT_FOUND).entity(mensaje).build();
		}
	}
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.TEXT_PLAIN)
	@Operation(summary = "Eliminar usuario admin", description = "Elimina un usuario por su ID")
    @ApiResponse(responseCode = "204", description = "Usuario eliminado")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(mediaType = "text/plain"))
	public Response borrar(@PathParam("id") @DefaultValue("6") Integer id) {
		Usuario usuario =(Usuario) usuarioDAO.getById(id);
		if(usuario!= null && usuario.getRol().equals(Rol.admin)) {
			usuarioDAO.eliminar(usuario);
			return Response.noContent().build();
		}
		else {
			mensaje= "Usuario no encontrado";
			return Response.status(Status.NOT_FOUND).entity(mensaje).build();
		}
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Modificar usuario", description = "Modificar un usuario existente")
	@RequestBody(content = @Content(mediaType = "application/json",schema = @Schema(implementation = Usuario.class),
    examples = @ExampleObject(value = "{\"id\":1, \"email\":\"adminmodificado@gmail.com\", \"password\":\"nuevapassword\", \"rol\":\"admin\"}")))
    @ApiResponse(responseCode = "200", description = "Usuario modificado", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(mediaType = "text/plain"))
	public Response editar(@Parameter(in=ParameterIn.DEFAULT,description="Los atributos id,email y password son requeridos")Usuario usuario){
		Usuario aux = (Usuario)usuarioDAO.getById(usuario.getId());
		if (aux != null && usuario.getEmail()!=null && usuario.getRol()!=null){
			try {
				usuarioDAO.modificar(usuario);
				return Response.ok().entity(usuario).build();
			//}catch(ConstraintViolationException e) { No tomaba la excepción ya que lanza una excpeción SQLIntegrityConstraintViolationException y no podia ser capturada		
			}catch(Exception e) {
				mensaje="El email se encuentra repetido";
				return Response.status(Status.BAD_REQUEST).entity(mensaje).build();
			}	
		} else {
			mensaje="Los datos no son válidos";
			return Response.status(Status.NOT_FOUND).entity(mensaje).build();
		}
	}

}
