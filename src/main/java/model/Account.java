package model;

import java.util.ArrayList;
import java.util.List;

import static model.Transaction.Type.DEPOSIT;
import static model.Transaction.Type.WITHDRAWAL;

public class Account {

    private Long id;

    private Double balance = 0D;

    private List<Transaction> transactions;

    public Account(Long id) {
        this.id = id;
    }

    // GETTERS AND SETTERS

    public Double getBalance() {
        return this.balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    // OPERATIONS

    public void addTransaction(Transaction transaction) {

        // If no transaction has yet been created for the account
        if(transactions == null) {
            transactions = new ArrayList<>();
        }

        if(transaction.getType() == DEPOSIT) {
            this.balance = this.balance + transaction.getAmount();
            transactions.add(transaction);
        }
        else if(transaction.getType() == WITHDRAWAL) {
            this.balance = this.balance - transaction.getAmount();
            transactions.add(transaction);
        }
    }

}
