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

## Docker
Estando dentro de la carpeta donde se encuentra el "Dockerfile":
- Crear imagen: docker image build -t app-calculator ./
- Iniciar aplicación: docker run -p 8080:8080 app-calculator

## Auditoria

Para consultar todas las acciones realizadas apartir de una fecha y hora especifica se debe realizar
en el formato "yyyy-MM-dd HH:mm" por ejemplo:
- http://localhost:8080/audit/2020-12-20 00:00