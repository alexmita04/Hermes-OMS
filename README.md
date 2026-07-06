# Hermes-OMS

Hermes-OMS is a lightweight Order Management System designed for fast and secure e-commerce operations. The application uses JWT authentication to protect endpoints and leverages Redis caching to ensure high-performance inventory lookups. When an order is placed, the system processes it asynchronously using an event-driven approach, where a dedicated logging consumer automatically writes the details into a CSV file. The architecture is fully scalable, allowing you to easily plug in additional consumers for tasks like sending emails or updating analytics.
> This project is a demo application built for learning and demonstration purposes. It is not intended for production use and may not include production-level security, scalability, or error handling.

## Tech Stack

| Technology | Purpose |
| :--- | :--- |
| **Java & Spring Boot** | Core application framework & REST APIs |
| **PostgreSQL** | Primary relational database for persistence |
| **Redis** | Fast in-memory caching for store inventory |
| **Apache Kafka** | Message broker for decoupling order placement and consumers |
| **JWT** | Stateless token-based user authentication |

## Installation and Local Configuration

Follow these steps to get the project up and running on your local machine:

### 1. Environment Variables
Before starting the application, you need to configure the environment variables.
1. Locate the `.env.example` file in the root directory of the repository.
2. Create a copy of it and rename it to `.env`.
3. Fill in the required local configuration values (database credentials, Kafka bootstrap servers, Redis host, etc.) inside the `.env` file.

### 2. Running with Docker Compose
The project includes a `docker-compose.yml` file that orchestrates all the necessary infrastructure (PostgreSQL, Redis, Apache Kafka).

To start the services, run the following command in your terminal:
```bash
docker compose up --build


## API Endpoints

Replace `<port>` with the port configured for your application.

```text
Host: http://localhost:<port>
```

### Auth Endpoints

#### Sign Up

**POST** `{{host}}/auth/signup`

Request Body:

```json
{
  "username": "testuser",
  "password": "strongpassword123"
}
```

#### Login

**POST** `{{host}}/auth/login`

Request Body:

```json
{
  "username": "testuser",
  "password": "strongpassword123"
}
```

### Inventory Endpoints

> Requires a Bearer authentication token.

#### Get All Products

**GET** `{{host}}/inventory/products`

#### Get Product by ID

**GET** `{{host}}/inventory/products/:productId`

### Orders Endpoints

> Requires a Bearer authentication token.

#### Create Order

**POST** `{{host}}/orders`

Request Body:

```json
{
  "items": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 3,
      "quantity": 1
    }
  ]
}
```
## Redis Caching

This project uses **Redis** to cache product details and reduce the number of database queries. The caching mechanism is implemented using the **cache-aside** pattern.

When a client requests a product by its ID, the application first checks whether the product is already stored in Redis using a key in the format `product:{productId}`.

* If the product is found in Redis (**cache hit**), it is returned immediately without querying the database, resulting in a much faster response.
* If the product is not found (**cache miss**), the application retrieves it from the database, stores it in Redis with a **10-minute expiration time (TTL)**, and then returns it to the client.

This approach improves performance for frequently requested products by reducing database load and taking advantage of Redis's in-memory storage. The expiration time ensures that cached data is periodically refreshed, helping keep the cache reasonably up to date while still providing fast read operations.

## Kafka Event Processing

This project uses **Apache Kafka** to process order events asynchronously, following an event-driven architecture.

When a new order is successfully created and the database transaction is committed, the application publishes an `OrderCreatedEvent` to the `order-created` Kafka topic. Publishing the event only after the transaction has been committed ensures that events are never sent for orders that failed to be saved.

A Kafka consumer listens to the `order-created` topic and processes each incoming event independently from the order creation request. In this project, the consumer logs every ordered product into a CSV file, creating the file and its header automatically if it does not already exist.

Using Kafka decouples the order creation process from additional background tasks such as logging, reporting, or notifications. As a result, the API remains responsive while these operations are performed asynchronously, making the system more scalable and easier to extend with new consumers in the future.

## Other Functionalities

### Database Seeding

The application includes an automatic **database seeder** that runs on startup. Its role is to populate the database with an initial set of products if the database is empty.

This is implemented using a `CommandLineRunner`, which checks whether any products already exist. If the database is empty, it inserts a predefined list of sample products. This approach is useful for development and testing because it removes the need to manually create data every time the application starts.

---

### Global Error Handling

The application uses a **global exception handler** to centralize error management across all controllers.

Instead of handling errors individually in each endpoint, exceptions are intercepted in one place using `@RestControllerAdvice`. The handler formats all errors into a consistent JSON structure and returns appropriate HTTP status codes.

It also supports validation errors, meaning that invalid request bodies are automatically parsed into clear and readable error messages. This ensures a consistent and predictable error response format for the client.

---

### JWT Authentication Filter

Security is handled using a **JWT-based authentication system** with a custom filter.

For each incoming request, the filter checks the `Authorization` header for a Bearer token. If a token is present, it is validated and the username is extracted. Then the system loads the user details and verifies the token integrity.

If the token is valid, the user is authenticated and stored in the Spring Security context, allowing access to protected endpoints.

This mechanism ensures that only authenticated users can access secured resources while keeping the system stateless and scalable.

## Contact

- **Email:** alexmita04@gmail.com
- [**LinkedIn**](https://www.linkedin.com/in/alexandru-mita-ba74b2299/)

## License

See [LICENSE](LICENSE.md)