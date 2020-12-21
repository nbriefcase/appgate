# Calculator

Proyecto de Spring-Boot que exponen endpoints para realizar operaciones aritméticas como: división, multiplication, resta, suma, potenciacion.

## Endpoint
````
- POST addOperand PARAM sessionId, num
- GET createSession
- GET calculate PARAM sessionId, action
- GET sessions PARAM sessionId(no mandatory)
- DELETE sessions PARAM sessionId(mandatory)
```` 
## Swagger UI

- http://localhost:8080/swagger-ui.html: Interfaz para realizar peticiones reales.
- http://localhost:8080/v2/api-docs: detalles sobre el/los endpoints.