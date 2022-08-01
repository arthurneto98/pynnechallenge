package com.pyyne.challenge.bank.adaptor;

import com.bank1.integration.Bank1AccountSource;
import com.bank1.integration.Bank1Transaction;
import com.bank2.integration.Bank2AccountSource;
import com.bank2.integration.Bank2AccountTransaction;
import com.pyyne.challenge.bank.Domain.TransactionTypeEnum;
import com.pyyne.challenge.bank.Domain.BankAccountInformation;
import com.pyyne.challenge.bank.repository.BankAccountInformationRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TransactionAdaptorImpl implements TransactionAdaptor {
    private final BankAccountInformationRepository bankAccountInformationRepository;
    private final Bank1AccountSource bank1AccountSource;
    private final Bank2AccountSource bank2AccountSource;

    public TransactionAdaptorImpl(BankAccountInformationRepository bankAccountInformationRepository, Bank1AccountSource bank1AccountSource, Bank2AccountSource bank2AccountSource) {
        this.bankAccountInformationRepository = bankAccountInformationRepository;
        this.bank1AccountSource = bank1AccountSource;
        this.bank2AccountSource = bank2AccountSource;
    }

    @Override
    public List<TransactionDto> getAllTransactions(Date initialDate, Date finalDate) {
        List<TransactionDto> result = new ArrayList<>(Collections.emptyList());

        List<Bank1Transaction> bank1Transactions = getBank1Transactions(initialDate, finalDate);
        bank1Transactions.forEach(transaction -> {
            TransactionDto dto = new TransactionDto();
            dto.amount = transaction.getAmount();
            dto.type = getBank1Type(transaction.getType());
            dto.description = transaction.getText();
            result.add(dto);
        });

        List<Bank2AccountTransaction> bank2Transactions = getBank2Transactions(initialDate, finalDate);
        bank2Transactions.forEach(transaction -> {
            TransactionDto dto = new TransactionDto();
            dto.amount = transaction.getAmount();
            dto.type = getBank2Type(transaction.getType());
            dto.description = transaction.getText();
            result.add(dto);
        });

        return result;
    }

    private List<Bank1Transaction> getBank1Transactions(Date initialDate, Date finalDate) {
        BankAccountInformation bank1AccountInformation = bankAccountInformationRepository.getBank1AccountInformation();
        long bank1AccountId = bank1AccountInformation.getAccountId();
        return bank1AccountSource.getTransactions(bank1AccountId, initialDate, finalDate);
    }

    private List<Bank2AccountTransaction> getBank2Transactions(Date initialDate, Date finalDate) {
        BankAccountInformation bank2AccountInformation = bankAccountInformationRepository.getBank2AccountInformation();
        long bank2AccountId = bank2AccountInformation.getAccountId();
        return bank2AccountSource.getTransactions(bank2AccountId, initialDate, finalDate);
    }

    private TransactionTypeEnum getBank1Type(Integer type) {
        return type == 1 ? TransactionTypeEnum.CREDIT : TransactionTypeEnum.DEBIT;
    }

    private TransactionTypeEnum getBank2Type(Bank2AccountTransaction.TRANSACTION_TYPES type) {
        return type == Bank2AccountTransaction.TRANSACTION_TYPES.CREDIT ? TransactionTypeEnum.CREDIT : TransactionTypeEnum.DEBIT;
    }
}
