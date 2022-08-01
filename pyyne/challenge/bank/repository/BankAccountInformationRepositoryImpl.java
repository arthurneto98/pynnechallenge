package com.pyyne.challenge.bank.repository;

import com.pyyne.challenge.bank.Domain.BankAccountInformation;

public class BankAccountInformationRepositoryImpl implements BankAccountInformationRepository {

    @Override
    public BankAccountInformation getBank1AccountInformation() {
        //this would normally be a database query
        BankAccountInformation result = new BankAccountInformation(1);
        return result;
    }

    @Override
    public BankAccountInformation getBank2AccountInformation() {
        //this would normally be a database query
        BankAccountInformation result = new BankAccountInformation(2);
        return result;
    }
}
