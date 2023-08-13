![wide-logo.jpg](img%2Fwide-logo.jpg)
# BlockTrackerWeb Application

> This repository contains my "BlockTracker" project. The application is designed to track cryptocurrency investments.

## Table of Contents

- [Functionalities](#functionalities)
- [Technologies Used](#technologies-used)
- [DB Diagram](#db-diagram)
- [UML Diagram](#uml-diagram)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Usage](#usage)

## Functionalities

The application is using both SSR(Server-side rendering), CSR(Client-side rendering) and supports the following 
functionalities:

### Backend

- Registering and authenticating users using credentials or OAuth2 Google auth.
- Validating request information.
- Mailing service for registering confirmation.
- Handling exceptions and serving error messages.
- Editing and serving web-pages with static information.
- CRUD operations for portfolios, transactions, coins and watchlist items following REST style.
- Working with KuCoin API to get actual information on cryptocurrencies.

### Frontend

- Display a watchlist(actual information on added cryptocurrencies) and ability to add/remove items.
- Display a list of all portfolios and the ability to add/remove/edit them.
- Display a list of all coins in portfolio and the ability to add them through transactions or remove them.
- Display a list of all transactions on chosen cryptocurrency and add/remove/edit them.
- Display errors responses from server.

### How SSR and CSR are combined?

- Pages with static data are generated from templates and fragments using Thymeleaf.
- Controllers serving those pages to client together with JS code.
- JS code is running on client, perform API requests to server.
- API controllers will handle those requests. 
- JS code fill pages with up-to-date dynamic information.

## Technologies Used

The application utilizes the following technologies:

### Backend:

- Java 11
- Maven
- Lombok
- Spring Boot
- Spring Security
- Spring JavaMail
- Thymeleaf
- JPA
- PostgreSQL
- OAuth2 (Google auth)
- Google Gson
- KuCoin API

### Frontend:

- HTML
- CSS
- JavaScript
- Bootstrap
- JQuery

## DB Diagram

![DB.png](img%2FDB.png)
### Relations: 
- portfolio - usr: many to one.
- confirmation_token - usr: one to one.
- watchlist_item - usr: many to one.
- coin - portfolio: many to one.
- transaction - coin: many to one.

## UML Diagram

![UML.png](img%2FUML.png)
> Note: Portfolio class contains list of coin only for cascading deletion using JPA. For the same reason, 
> coin contains list of transactions.

## Project Structure

The project implements following basic architecture:

- **Backend**:
    - Controllers: Handle incoming HTTP requests.
    - Services: Implement business logic.
    - Repositories: Manage database interactions.

- **Frontend**: Communicate with the backend, displaying information.

## Getting Started

1. Clone the repository.
2. Prepare database.
3. Set up the database configuration in `application.properties`.
4. Obtain your OAuth2 credentials [here](https://console.cloud.google.com/apis/dashboard).
5. Get your e-mail credentials. (host, port, username, password)
6. Get your KuCoin API credentials [here](https://www.kucoin.com/api). 
7. Fill in all fields in `application.properties`.
8. Build and run the Spring Boot application.

## Usage

- Access the application by navigating to http://localhost:8080 in your web browser.
- Explore all the functionalities as described.
