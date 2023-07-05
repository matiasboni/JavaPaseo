package api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
	    info = @Info(
	        title = "API",
	        version = "3.0.1",
	        description = "API de Ejemplo para Swagger"
	    ),
	    	servers = {
	    			@Server(url = "http://localhost:8080/paseounlp/api"),
	      }
	)
public class ApiConfig {

}
