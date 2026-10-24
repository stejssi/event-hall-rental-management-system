# Event Hall Rental Management System

A Java-based client-server application developed for managing event hall rentals, customers, rental appointments, and contracts. The system follows the Model-View-Controller (MVC) architectural pattern and uses a MySQL relational database for data storage.

## Features

- **Employee Authentication** — Employee login and access to the application.
- **Customer Management** — Create, view, update, and delete customer records.
- **Rental Appointment Management** — Create, view, and update rental appointments.
- **Contract Management** — Create, search, view, and update rental contracts.
- **Client-Server Communication** — Exchange requests and responses between the client and server.
- **Database Transactions** — Commit and rollback mechanisms to maintain data consistency.

## Architecture

The application is organized into three modules:

- **Seminarski_Klijent** — The client-side application, including graphical user interface forms, controllers, and table models.
- **Seminarski_Server** — The server-side application, responsible for processing client requests, executing business operations, and interacting with the database.
- **Seminarski_Zajednicki** — Shared domain classes and communication objects used by both the client and server.

The system uses a multi-threaded client-server architecture. A dedicated thread handles communication with each connected client. Requests and responses are exchanged as serialized Java objects.

The server-side business logic uses a generic operation pattern with precondition checks and transaction management.

## Technologies Used

- **Programming Language:** Java
- **Database:** MySQL
- **Database Management Tool:** SQLyog
- **IDE:** Apache NetBeans
- **Architecture:** Client-Server, MVC
- **Networking:** Java Sockets and Multithreading
- **Design Concepts:** Object-Oriented Programming, Layered Architecture, Transaction Management

## Database

The relational database stores information related to customers, employees, event halls, rental appointments, contracts, payment methods, and rental categories.

## Getting Started

1. Clone or download this repository.
2. Open the project modules in Apache NetBeans.
3. Configure the MySQL database connection and create the required database schema.
4. Start the server application.
5. Run the client application and connect it to the server.

*Note: The database schema and connection settings must be configured before running the application.*

## Project Context

Developed as an academic software engineering project, applying object-oriented design, client-server communication, MVC architecture, relational database design, and software testing.

