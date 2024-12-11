# Bank-Management-Project

This project is designed to provide admin-level functionalities for managing customers, cards, and transactions in a banking application. 
The application offers CRUD operations for customers and cards, as well as transaction tracking.

## Project Objectives

The main goal of this project is to simplify banking management tasks through services such as:

- Managing customer information and statuses.
- Managing card information and statuses.
- Tracking and viewing transactions.

## Features

### **CardService**
Handles all operations related to managing cards:
- **getAllCards**: Retrieves a list of all cards in the system.
- **getCardById**: Fetches details of a specific card using its ID.
- **createCard**: Adds a new card to the system.
- **updateCard**: Updates the details of an existing card.
- **updateIsActiveCard**: Changes the active status of a card (activate/deactivate).
- **deleteCard**: Removes a card from the system.

### **CustomerService**
Handles all operations related to managing customers:
- **getAllCustomers**: Retrieves a list of all customers in the system.
- **getCustomerById**: Fetches details of a specific customer using their ID.
- **createCustomer**: Adds a new customer to the system.
- **updateCustomer**: Updates the details of an existing customer.
- **updateIsActiveCustomer**: Changes the active status of a customer (activate/deactivate).
- **deleteCustomer**: Removes a customer from the system.

### **TransactionService**
Handles all operations related to managing transactions:
- **getAllTransactions**: Retrieves a list of all transactions in the system.
- **getTransactionById**: Fetches details of a specific transaction using its ID.

## Technologies Used

- **Java 17**: Programming language for building the application.
- **Spring Boot**: Framework for backend development.
- **PostgreSQL**: Database for storing customer, card, and transaction data.
- **Spring Data JPA**: Simplifies data access and integrates seamlessly with the database layer.
