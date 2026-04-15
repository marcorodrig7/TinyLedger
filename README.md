# TinyLedger

Simple Java HTTP server (no framework used) that simulates a basic banking system with account deposits, withdrawals, and transaction history.

Current Features:

- Creation account transactions - deposit and withdrawal;
- Retrieve account balance;
- Retrieve account history;
- Custom HTTP server;
- No external frameworks;
- In-memory data structures used.

---

Package organization:

- config - route configuration
- handler - HTTP endpoint handler
- model - domain entities
- service - business logic
- Main.java - application entry point

---

How to run:

- Option 1 - IDE
	- Import maven project and directly run the java application

- Option 2 - Bash
  	- Inside the projet's directory open bash window and execute the following commands:

		- rm -rf out
		- mkdir out
		- javac -d out $(find src/main/java -name "*.java")
		- java -cp out Main

---

API endpoints

- POST /accounts/{accountId}/deposit
- POST /accounts/{accountId}/withdrawal
- GET /accounts/{accountId}/balance
- GET /accounts/{accountId}/history

---

Examples 

- Make Deposit using CURL
	- Request: curl -X POST "http://localhost:8080/accounts/1/deposit" -H "Content-Type: text/plain" -d "amount=100"
	- Response: Deposit done


- Make Withdrawal using CURL
  	- Request: curl -X POST "http://localhost:8080/accounts/1/withdrawal" -H "Content-Type: text/plain" -d "amount=100"
	- Response: Withdrawal done

- Get account balance
  	- Request: curl -X GET "http://localhost:8080/accounts/1/balance"
	- Response example: -100.0


- Get account history
  	- Request: curl -X GET "http://localhost:8080/accounts/1/history"
	- Response example:
   
[
	{	
		"amount":100.0,
		"type":"DEPOSIT",
		"datetime":"2026-04-15T16:20:28.338854400"
	},
	{
		"amount":100.0,
		"type":"WITHDRAWAL",
		"datetime":"2026-04-15T16:21:43.513413800"
	},
	{
		"amount":100.0,
		"type":"WITHDRAWAL",
		"datetime":"2026-04-15T16:22:26.352605200"
	}
]

