package com.pyyne.challenge.bank.Domain;

public enum CurrencyEnum {
    USD("US Dollar"),
    BRL("Brazilian Real"),
    EUR("Euro");

    private String description;

    CurrencyEnum(String description) {
        this.description = description;
    }
}
