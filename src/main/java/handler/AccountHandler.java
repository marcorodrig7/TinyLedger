package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Transaction;
import service.TransactionService;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        String[] parts = path.split("/");

        Long accountId = Long.parseLong(parts[2]);
        String action = parts[3];

        Long accountIdDestination = null;
        if(parts.length == 5) {
            accountIdDestination = Long.parseLong(parts[4]);
        }

        if("POST".equals(method) && "deposit".equals(action)) {
            handleAccountDeposit(exchange.getRequestBody(), accountId);

            String response = "Deposit done";
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        }
        else if("POST".equals(method) && "withdrawal".equals(action)) {
            handleAccountWithdrawal(exchange.getRequestBody(), accountId);

            String response = "Withdrawal done";
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        }
        else if("GET".equals(method) && "balance".equals(action)) {
            Double balance = TransactionService.getBalance(accountId);

            String response = String.valueOf(balance);
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        }
        else if("GET".equals(method) && "history".equals(action)) {

            String body = new String(exchange.getRequestBody().readAllBytes());

            Map<String, String> bodyParsed = parseBody(body);

            List<Transaction> transactions = null;
            if(bodyParsed.get("date_in") != null && bodyParsed.get("date_out") != null) {
                LocalDateTime dateIn = LocalDateTime.parse(bodyParsed.get("date_in"));
                LocalDateTime dateOut = LocalDateTime.parse(bodyParsed.get("date_out"));

                transactions = TransactionService.getTransactionHistoryByAccount(accountId, dateIn, dateOut);
            }
            else {
                transactions = TransactionService.getTransactionHistoryByAccount(accountId);
            }



            String response = toJson(transactions);
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        }
        else if("POST".equals(method) && "transfer".equals(action)) {
            handleAccountTransfer(exchange.getRequestBody(), accountId, accountIdDestination);

            String response = "Transfer done";
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        }

    }

    private void handleAccountDeposit(InputStream requestBody, Long id) throws IOException {
        String body = new String(requestBody.readAllBytes());

        // example: amount = 1
        String[] parts = body.split("=");

        double amount = Double.parseDouble(parts[1]);

        TransactionService.makeDeposit(id, amount);
    }

    private void handleAccountWithdrawal(InputStream requestBody, Long id) throws IOException {
        String body = new String(requestBody.readAllBytes());

        // example: amount = 1
        String[] parts = body.split("=");

        double amount = Double.parseDouble(parts[1]);

        TransactionService.makeWithdrawal(id, amount);
    }

    private void handleAccountTransfer(InputStream requestBody, Long id_origin, Long id_destination) throws IOException {
        String body = new String(requestBody.readAllBytes());

        // example: amount = 1
        String[] parts = body.split("=");

        double amount = Double.parseDouble(parts[1]);

        TransactionService.makeTransfer(id_origin, id_destination, amount);
    }

    private String toJson(List<Transaction> transactions) {
        StringBuilder sb = new StringBuilder();
        if(transactions!= null && !transactions.isEmpty()) {
            sb.append("[");

            for(int i = 0; i < transactions.size(); i++) {
                Transaction e = transactions.get(i);

                sb.append("{")
                        .append("\"amount\":").append(e.getAmount()).append(",")
                        .append("\"type\":\"").append(e.getType()).append("\",")
                        .append("\"datetime\":\"").append(e.getDateTime()).append("\"")
                        .append("}");

                if (i < transactions.size() - 1) {
                    sb.append(",");
                }

            }
            sb.append("]");
        }
        else {
            sb.append("{}");
        }

        return sb.toString();
    }

    private Map<String, String> parseBody(String body) {
        Map<String, String> map = new HashMap<>();

        String[] pairs = body.split(";");

        for (String pair : pairs) {
            String[] keyValue = pair.split("=");

            if (keyValue.length == 2) {
                map.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }

        return map;
    }

}
