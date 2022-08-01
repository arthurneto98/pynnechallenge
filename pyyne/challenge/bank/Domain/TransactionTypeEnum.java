package com.pyyne.challenge.bank.Domain;

public enum TransactionTypeEnum {
    CREDIT("Credit"),
    DEBIT("Debit");

    private String description;

    TransactionTypeEnum(String description) {
        this.description = description;
    }
}
