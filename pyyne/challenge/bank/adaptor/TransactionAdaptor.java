package com.pyyne.challenge.bank.adaptor;

import java.util.Date;
import java.util.List;

public interface TransactionAdaptor {
    List<TransactionDto> getAllTransactions(Date initialDate, Date finalDate);
}
