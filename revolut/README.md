# Name
Anu Gorla

# Bank API Implementation 

## Prerequisites

Maven, Java 8 installed on your machine.  

This application uses [Restlet Framework 2.3.9](http://restlet.com/download/current) and light weight OKHTTPClient


### Run this application

The main class is : `AppProcessor.java`
By default the application is launched at `http://localhost:8080`, to access the API append version URL and path eg. 'http://localhost:8080/v1/ping' 
A logger is configured. Its configuration is made in `logging.properties` and declared in `AppProcessor.java`.

You can interact with this application easily using any REST client like [POSTMAN](http://www.getpostman.com/).

## Description

This Web API contains 3 main resources:
* v1/account/create : creates account with given amount, accepts json { "amount": 11.55} POST request
* v1/account/{id} : Get an account with the id GET request
* v1/account/balance/transfer : Transfer balance from one account to another
 {
 	"fromId":1,
 	"toId":2,
 	"amount":110
 } POST request
