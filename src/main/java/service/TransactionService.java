package service;

import model.Account;
import model.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static model.Transaction.Type.*;

public class TransactionService {

    private static final Map<Long, Account> accounts = new HashMap<>();

    /**
     * To make a deposit. If the id of the account does not yet exist it will be automatically created.
     *
     * @param idAccount - the id of the account
     * @param amount - the amount to deposit
     */
    public static void makeDeposit(Long idAccount, Double amount) {
        makeTransaction(idAccount, amount, DEPOSIT);
    }

    /**
     * To make a withdrawal. If the id of the account does not yet exist it will be automatically created.
     *
     * @param idAccount - the id of the account
     * @param amount - the amount to withdraw
     */
    public static void makeWithdrawal(Long idAccount, Double amount) {
        makeTransaction(idAccount, amount, WITHDRAWAL);
    }

    /**
     * To get an account current balance. If the id of the account does not yet exist then it returns null.
     *
     * @param idAccount - the id of the account
     * @return - the current amount if the accounts exists.
     */
    public static Double getBalance(Long idAccount) {
        boolean isAccountCreated = accounts.containsKey(idAccount);

        if(isAccountCreated) {
            Account account = accounts.get(idAccount);
            return account.getBalance();
        }
        else {
            return null;
        }
    }

    /**
     * To get an account transaction history. If the id of the account does not yet exist then it returns null.
     *
     * @param idAccount - the id of the account
     * @return - the current history if the accounts exists.
     */
    public static List<Transaction> getTransactionHistoryByAccount(Long idAccount) {
        boolean isAccountCreated = accounts.containsKey(idAccount);

        if(isAccountCreated) {
            Account account = accounts.get(idAccount);
            return account.getTransactions();
        }
        else {
            return null;
        }
    }

    public static List<Transaction> getTransactionHistoryByAccount(Long idAccount, LocalDateTime in, LocalDateTime out) {
        boolean isAccountCreated = accounts.containsKey(idAccount);

        Predicate<Transaction> transactionBefore = e -> e.getDateTime().isBefore(out);
        Predicate<Transaction> transactionAfter = e -> e.getDateTime().isAfter(in);

        if(isAccountCreated) {
            Account account = accounts.get(idAccount);

            return account.getTransactions().stream().filter(transactionBefore.and(transactionAfter)).collect(Collectors.toList());
        }
        else {
            return null;
        }
    }

    public static void makeTransfer(Long idAccountOrigin, Long idAccountDestination, Double amount) {
        boolean isAccountOriginCreated = accounts.containsKey(idAccountOrigin);
        boolean isAccountDestinationCreated = accounts.containsKey(idAccountDestination);

        if(isAccountOriginCreated && isAccountDestinationCreated) {
            Account accountOrigin = accounts.get(idAccountOrigin);
            Account accountDestination = accounts.get(idAccountDestination);

            accountOrigin.addTransaction(new Transaction(amount, LocalDateTime.now(), TRANSFER_OUT));
            accountDestination.addTransaction(new Transaction(amount, LocalDateTime.now(), TRANSFER_IN));
        }

    }

    private static void makeTransaction(Long idAccount, Double amount, Transaction.Type transactionType) {

        // If the id of the account does not exist, we create a new Account
        boolean isAccountCreated = accounts.containsKey(idAccount);

        if(isAccountCreated) {
            Account account = accounts.get(idAccount);
            account.addTransaction(new Transaction(amount, LocalDateTime.now(), transactionType));
        }
        else {
            Account newAccount = new Account(idAccount);
            newAccount.addTransaction(new Transaction(amount, LocalDateTime.now(), transactionType));
            accounts.put(idAccount, newAccount);
        }

    }


}
