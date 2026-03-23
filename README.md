# Spring WebFlux + MongoDB Atlas CRUD

Este proyecto implementa un CRUD reactivo utilizando Spring WebFlux y MongoDB Atlas para la gestión de productos.

## Requisitos

- Java 17
- Maven 3.6+
- MongoDB Atlas cluster
- Git

## Configuración

1. **Clonar el repositorio:**
   ```bash
   git clone <repository-url>
   cd spring-weblux-nosql
   git checkout develop
   ```

2. **Configurar MongoDB Atlas:**
   - Crear una base de datos llamada `22_maylin_jauregui`
   - Crear una collection llamada `product`
   - Crear una collection llamada `database_sequences` para IDs secuenciales
   - Obtener la cadena de conexión desde MongoDB Atlas
   - Actualizar el archivo `src/main/resources/application.yaml` con tus credenciales:
   ```yaml
   spring:
     data:
       mongodb:
         uri: mongodb+srv://maylinjauregui:oTDesoVuG2nx4AdJ@cluster0.5djyl.mongodb.net/22_maylin_jauregui?retryWrites=true&w=majority
   ```

3. **Compilar y ejecutar:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## Endpoints de la API

La aplicación estará disponible en `http://localhost:8080`

### Documentación Swagger/OpenAPI
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api-docs`

### Endpoints CRUD

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/products` | Crear un nuevo producto (ID secuencial automático) |
| GET | `/api/products/{id}` | Obtener producto por ID |
| GET | `/api/products` | Obtener todos los productos |
| PUT | `/api/products/{id}` | Actualizar producto existente |
| DELETE | `/api/products/{id}` | Eliminar producto |

### Endpoints de Activación/Desactivación

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| PATCH | `/api/products/{id}/activate` | Activar producto (active: true) |
| PATCH | `/api/products/{id}/deactivate` | Desactivar producto (active: false) |

### Endpoints de Búsqueda

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/products/search/name?name=` | Buscar productos por nombre |
| GET | `/api/products/search/category?category=` | Filtrar por categoría |
| GET | `/api/products/search/price-range?min=&max=` | Filtrar por rango de precios |
| GET | `/api/products/active` | Obtener productos activos |
| GET | `/api/products/in-stock` | Obtener productos con stock |

## Modelo de Datos

El modelo `Product` contiene los siguientes campos:
- `id`: Identificador único secuencial (String) - Ej: "1", "2", "3"
- `name`: Nombre del producto (String)
- `description`: Descripción detallada (String)
- `price`: Precio (BigDecimal)
- `category`: Categoría (String)
- `stock`: Cantidad disponible (Integer)
- `createdAt`: Fecha de creación (LocalDateTime)
- `updatedAt`: Fecha de actualización (LocalDateTime)
- `active`: Estado activo (Boolean)

## Tecnologías Utilizadas

- **Spring Boot 3.5.11**
- **Spring WebFlux** - Programación reactiva
- **Spring Data MongoDB Reactive** - Acceso reactivo a MongoDB
- **MongoDB Atlas** - Base de datos NoSQL en la nube
- **Lombok** - Reducción de código boilerplate
- **SpringDoc OpenAPI** - Documentación automática de API
- **Maven** - Gestión de dependencias

## Estructura del Proyecto

```
src/main/java/ap1/maylin/jauregui/
├── model/          # Entidades de datos
│   └── Product.java
├── repository/     # Interfaces de acceso a datos
├── service/        # Interfaces de lógica de negocio
├── impl/           # Implementaciones de servicios
├── rest/           # Controladores REST
├── config/         # Configuraciones
│   ├── DatabaseSequence.java
│   └── SequenceGeneratorService.java
└── Application.java # Clase principal
```

## Testing

Para probar los endpoints puedes usar:

**Swagger UI**: Navega a `http://localhost:8080/swagger-ui.html`


## Pruebas en Postman

### Configuración inicial
1. **Content-Type**: `application/json`
2. **Base URL**: `http://localhost:8080/api/products`

### Operaciones CRUD

#### 1. Crear Producto
```
POST http://localhost:8080/api/products
Content-Type: application/json

{
    "name": "Laptop Dell XPS 15",
    "description": "High-performance laptop with 16GB RAM",
    "price": 1299.99,
    "category": "Electronics",
    "stock": 50
}
```
**Respuesta esperada:** Producto creado con ID "2"

![Postman Result - Create Product](./images/postman-create-product.png)

#### 2. Obtener Todos los Productos
```
GET http://localhost:8080/api/products
```
**Respuesta esperada:** Lista de todos los productos

![Postman Result - Get All Products](./images/postman-get-all-products.png)


#### 3. Obtener Producto por ID
```
GET http://localhost:8080/api/products/2
```
**Respuesta esperada:** Producto con ID "2"

![Postman Result - Get Product by ID](./images/postman-get-product-by-id.png)

#### 4. Actualizar Producto
```
PUT http://localhost:8080/api/products/2
Content-Type: application/json

{
    "name": "Laptop Dell XPS 15 Updated",
    "description": "Updated high-performance laptop",
    "price": 1199.99,
    "category": "Electronics",
    "stock": 45,
    "active": true
}
```
**Respuesta esperada:** Producto actualizado

![Postman Result - Update Product](./images/postman-update-product.png)

### Operaciones de Activación

#### 5. Desactivar Producto
```
PATCH http://localhost:8080/api/products/2/deactivate
```
**Respuesta esperada:** Producto con `active: false`

![Postman Result - Deactivate Product](./images/postman-deactivate-product.png)

#### 6. Activar Producto
```
PATCH http://localhost:8080/api/products/2/activate
```
**Respuesta esperada:** Producto con `active: true`

![Postman Result - Activate Product](./images/postman-activate-product.png)

#### 7. Eliminar Producto
```
DELETE http://localhost:8080/api/products/2
```
**Respuesta esperada:** 204 No Content

![Postman Result - Delete Product 2](./images/postman-delete-product.png)


**Verificación:** Como se observa en la imagen, el producto con ID 2 ha sido eliminado correctamente y solo existe el producto con ID 1.

![Postman Result - Delete Product 2 List After Delete](./images/postman-delete2-product-list.png)


## Verificación en MongoDB Atlas

### Listar Productos en MongoDB

Para verificar que los productos se guardan correctamente en MongoDB Atlas:

1. **Conectarse a MongoDB Atlas**
2. **Navegar a la base de datos:** `22_maylin_jauregui`
3. **Seleccionar la collection:** `product`

**Resultado esperado:** Lista de todos los productos almacenados en la base de datos

![MongoDB Atlas - List Products](./images/mongodb-list-products.png)

## Notas Importantes

- Asegúrate de configurar correctamente las credenciales de MongoDB Atlas
- La base de datos debe llamarse `22_maylin_jauregui`
- La collection debe llamarse `product`
- El proyecto usa programación reactiva con WebFlux y MongoDB Reactive 
