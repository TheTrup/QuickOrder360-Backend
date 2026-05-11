# QuickOrder 360 - Backend Microservices
QuickOrder 360 es una plataforma distribuida de gestión de pedidos diseñada bajo una arquitectura de microservicios. El sistema garantiza la integridad de los datos y la consistencia transaccional a través de la orquestación de servicios independientes.

# Arquitectura del Sistema
El ecosistema se compone de 10 microservicios que se comunican de forma síncrona mediante OpenFeign y se descubren dinámicamente a través de Netflix Eureka.

Tecnologías Clave:
Java 21 & Spring Boot 3.2.5

Spring Cloud Netflix Eureka: Service Discovery.

Spring Cloud OpenFeign: Declarative REST Client.

MySQL: Persistencia de datos independiente por servicio.

Liquibase: Gestión y versionamiento de esquemas de base de datos.

Lombok & SLF4J: Optimización de código y registro de logs.

Servicio,Puerto,Descripción
eureka-server,8761,Servidor de descubrimiento de servicios.
ms-usuarios,8080,Gestión de perfiles y seguridad.
ms-clientes,8081,Administración de datos maestros de clientes.
ms-pedidos,8082,Orquestador principal del flujo de venta.
ms-productos,8083,Catálogo de productos y precios.
ms-detalles,8084,Gestión del desglose de ítems por pedido.
ms-inventario,8085,Control de stock físico y movimientos de bodega.
ms-pagos,8086,Procesamiento de transacciones financieras.
ms-despachos,8087,Logística y seguimiento de entregas.
ms-reclamos,8088,Gestión de post-venta y soporte.
ms-notificaciones,8089,Motor de alertas y avisos al usuario.

# Flujo de Integración Crítico (Pedidos -> Inventario)
El sistema implementa una lógica de orquestación segura. Un pedido no puede ser confirmado si no cumple con las siguientes validaciones cruzadas:

Validación de Identidad: El MS Pedidos consulta al MS Clientes para confirmar la existencia del comprador.

Validación de Producto: Se verifica la existencia del ítem en el MS Productos.

Reserva de Stock: El MS Pedidos solicita al MS Inventario el descuento de unidades. Si el stock es insuficiente, la transacción se aborta.

Persistencia: Solo si los pasos anteriores son exitosos, el pedido se guarda con estado CONFIRMADO.

Evidencia de Funcionamiento (Logs)

1. Registro Exitoso en Eureka
Muestra que todos los servicios están conectados al panel de control central.

INFO 19184 --- [eureka] : Registered instance CLIENTES with status UP
INFO 19184 --- [eureka] : Registered instance PEDIDOS with status UP
INFO 19184 --- [eureka] : Registered instance INVENTARIO with status UP

2. Flujo de Negocio Completo (Happy Path)
Evidencia de cómo el microservicio de Pedidos dirige la operación:

INFO 19484 --- [Pedidos] : Iniciando validación para el cliente con ID: 1
INFO 19484 --- [Pedidos] : Cliente válido.
INFO 19484 --- [Pedidos] : Verificando existencia del producto ID: 1
INFO 19484 --- [Pedidos] : Intentando descontar unidades del inventario...
INFO 19484 --- [Pedidos] : Stock descontado exitosamente.
INFO 19484 --- [Pedidos] : Procesando guardado de pedido...
Hibernate: insert into pedidos (cantidad, cliente_id, estado, producto_id) values (?, ?, ?, ?)

3. Resiliencia y Manejo de Errores
El sistema detecta y comunica fallos de lógica de negocio sin interrumpir la disponibilidad:

ERROR 19484 --- [Pedidos] : Error al descontar stock: [400] during [PUT] to [http://INVENTARIO/...] : [Stock insuficiente o producto no encontrado]

# Instalación y Ejecución

Orden de encendido (Recomendado):

Levantar eureka-server.

Levantar servicios de soporte (clientes, productos, inventario).

Levantar pedidos y el resto de los servicios.

Compilación:

Durante el desarrollo se experimentaron problemas al lanzar los MS por lo que se propone como metodo seguro ejecutar los siguientes comandos en su terminal dentro de la carpeta del MS que quiera iniciar.

.\mvnw clean compile

.\mvnw spring-boot:run -DskipTests

