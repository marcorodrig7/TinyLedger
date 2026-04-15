package model;

import java.time.LocalDateTime;

public class Transaction {

    public enum Type {DEPOSIT, WITHDRAWAL}

    private Double amount;

    private LocalDateTime dateTime;

    private final Type type;

    public Transaction(Double amount, LocalDateTime dateTime, Type type) {
        this.amount = amount;
        this.dateTime = dateTime;
        this.type = type;
    }

    // GETTERS AND SETTERS

    public Double getAmount() {
        return this.amount;
    }

    public Type getType() {
        return this.type;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

}
