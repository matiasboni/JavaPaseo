# **Java y Aplicaciones Avanzadas sobre Internet 2023 Grupo7**

## **Pasos para ejecutar el ambiente de prueba Java**

Una vez descargado el proyecto se debe crear:
- Una base de datos con nombre jyaa2023_bd7
- Un usuario con nombre jyaa2023_usr7 y password jyaa2023_pwd7

Luego se debe ejecutar el servlet ServletPrueba que se encuentra src/main/java/misservlets.Hecho esto se lo redirigira al link http://localhost:8080/paseounlp/servletprueba y los resultados de las pruebas se mostrarán en consola.
Para poder probar la API se debe ingresar a http://localhost:8080/paseounlp/swagger-ui/#/ ,los metodos ya vienen cargados con ejemplos.El orden de ejecución que se habia pensado es el siguiente:

### **Usuarios**
- POST de administrador.(Crear usuario)
- GET enviando el id del usuario creado en el POST anterior.(Obtener usuario)
- DELETE del usuario admin creado anteriormente.(Eliminar usuario admin)
- PUT del usuario.(Modificar usuario admin)
- GET del listado de usuarios.(Obtener lista de usuarios)
- POST de visitante se debe cambiar el rol que se envía.(Crear usuario)

### **Pedidos**
- GET enviando el id de un pedido que se encuentra en estado Preparacion.(Obtener pedido)
- PUT confirmando el pedido .(Confirmar pedido)
- PUT modificando el pedido confirmado anteriormente. (Modificar pedido)
- PUT enviando un id para cancelar el pedido anterior.(Cancelar pedido)

## **Pasos para ejecutar el ambiente de prueba Angular**

- Dentro de la carpeta angular se debe ejecutar npm install para instalar las dependencias necesarias.
- Luego ng serve para ejecutar la aplicación angular.
- Para acceder a la aplicación se debe entrar a  http://localhost:4200/ .

### **Usuarios cargados**

- admin@gmail.com
- visitante1@gmail.com
- visitante2@gmail.com tiene una dirección cargada.

Las contraseñas de todos los usuarios cargados es 12345678
