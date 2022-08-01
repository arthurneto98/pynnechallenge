package com.pyyne.challenge.bank.adaptor;

import com.bank1.integration.Bank1AccountSource;
import com.bank2.integration.Bank2AccountBalance;
import com.bank2.integration.Bank2AccountSource;
import com.pyyne.challenge.bank.Domain.CurrencyEnum;
import com.pyyne.challenge.bank.Domain.BankAccountInformation;
import com.pyyne.challenge.bank.repository.BankAccountInformationRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BankBalanceAdaptorImpl implements BankBalanceAdaptor {
    private final BankAccountInformationRepository bankAccountInformationRepository;
    private final Bank1AccountSource bank1AccountSource;
    private final Bank2AccountSource bank2AccountSource;

    public BankBalanceAdaptorImpl(BankAccountInformationRepository bankAccountInformationRepository, Bank1AccountSource bank1AccountSource, Bank2AccountSource bank2AccountSource) {
        this.bankAccountInformationRepository = bankAccountInformationRepository;
        this.bank1AccountSource = bank1AccountSource;
        this.bank2AccountSource = bank2AccountSource;
    }

    @Override
    public List<AccountBalanceDto> getAllBalances() {
        AccountBalanceDto bank1Balance = bank1Balance();
        AccountBalanceDto bank2Balance = bank2Balance();

        return Stream.of(bank1Balance, bank2Balance).collect(Collectors.toList());
    }

    private AccountBalanceDto bank1Balance() {
        BankAccountInformation bank1AccountInformation = bankAccountInformationRepository.getBank1AccountInformation();
        long bank1AccountId = bank1AccountInformation.getAccountId();
        AccountBalanceDto bank1Balance = new AccountBalanceDto();
        bank1Balance.amount = bank1AccountSource.getAccountBalance(bank1AccountId);
        bank1Balance.currency = CurrencyEnum.valueOf(bank1AccountSource.getAccountCurrency(bank1AccountId));
        return bank1Balance;
    }

    private AccountBalanceDto bank2Balance() {
        BankAccountInformation bank2AccountInformation = bankAccountInformationRepository.getBank2AccountInformation();
        long bank2AccountId = bank2AccountInformation.getAccountId();
        AccountBalanceDto bank2Balance = new AccountBalanceDto();
        Bank2AccountBalance balance = bank2AccountSource.getBalance(bank2AccountId);
        bank2Balance.amount = balance.getBalance();
        bank2Balance.currency = CurrencyEnum.valueOf(balance.getCurrency());
        return bank2Balance;
    }
}
