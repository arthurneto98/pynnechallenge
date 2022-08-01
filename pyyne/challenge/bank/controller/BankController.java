package com.pyyne.challenge.bank.controller;

import com.pyyne.challenge.bank.adaptor.AccountBalanceDto;
import com.pyyne.challenge.bank.adaptor.BankBalanceAdaptor;
import com.pyyne.challenge.bank.adaptor.TransactionAdaptor;
import com.pyyne.challenge.bank.adaptor.TransactionDto;

import java.util.Date;
import java.util.List;

public class BankController {
    private final BankBalanceAdaptor bankBalanceAdaptor;
    private final TransactionAdaptor transactionAdaptor;

    public BankController(BankBalanceAdaptor bankBalanceAdaptor, TransactionAdaptor transactionAdaptor) {
        this.bankBalanceAdaptor = bankBalanceAdaptor;
        this.transactionAdaptor = transactionAdaptor;
    }

    public void printBalances() {
        List<AccountBalanceDto> balances = bankBalanceAdaptor.getAllBalances();
        balances.forEach(accountBalance -> {
            System.out.println(accountBalance.amount + " " + accountBalance.currency);
        });
    }

    public void printTransactions(Date initialDate, Date finalDate) {
        List<TransactionDto> transactions = transactionAdaptor.getAllTransactions(initialDate, finalDate);
        transactions.forEach(transaction -> {
            System.out.println(transaction.description + ", " + transaction.amount + ", " + transaction.type.name());
        });
    }
}
