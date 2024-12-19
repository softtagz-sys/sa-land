# Land Logistics Application

## Project Description
This is a school project developed for the **Software Architecture course (2024-2025)**. The **Land Logistics Application** manages truck-based deliveries for the Krystal distributie Groep (KdG). It facilitates efficient scheduling, tracking, and management of mineral deliveries to warehouses.

## Technologies Used
- **Backend**: Spring Boot (Java/Kotlin)
- **Database**: PostgreSQL (Land schema)
- **Queue System**: RabbitMQ
- **Identity Provider**: Keycloak
- **Build Tool**: Maven/Gradle
- **Logging**: Integrated with Spring Boot logging

## Features
- Truck arrival scheduling with appointment windows (8:00 - 20:00).
- FIFO queue handling for unscheduled trucks.
- Automated weighbridge operations.
- Payload delivery ticket (PDT) and weighbridge ticket (WBT) generation.
- Storage capacity checks integrated with warehouse systems.
- RESTful API endpoints secured with OAuth2.

## Related Projects
This project is part of a larger system for managing the logistics of Krystal distributie Groep (KdG). The following related projects provide additional functionality:
- **[Water Logistics Application](https://github.com/softtagz-sys/sa-water)**: Handles ship docking, inspections, bunkering, and loading operations.
- **[Warehouse Management Application](https://github.com/softtagz-sys/sa-warehouse)**: Manages inventory levels, storage operations, and daily cost calculations.

## Contributors
- [Nicolas Verachtert](https://github.com/NicolasVerachtert)
- [Kobe Ponet](https://github.com/softtagz-sys)
