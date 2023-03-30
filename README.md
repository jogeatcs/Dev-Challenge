# Dev-Challenge
This application is a simple bank account implementation. An account able to transfer money to another account.

implementation
It should be possible to deposite and withdraw money from an account. 
A negative balance is not possible

InsufficientBalanceException created to manage negative or possible negative balances
Account should contain at least some accountId and current balance
All models are placed in com.dws.challenge.domain package

One rest endpoint to see account info
AccountsController.getAccount and AccountsController.createAccount

One rest endpoint to transfer money
TransferController.transfer

prerequisites
JDK 11
gradle
Spring Boot
Lombok
endpoints


tests
The unit and integration test package is same. The name of the unit test classes ends with Test; 

Run the command to run all tests:

gradle test


build
gradle clean build
