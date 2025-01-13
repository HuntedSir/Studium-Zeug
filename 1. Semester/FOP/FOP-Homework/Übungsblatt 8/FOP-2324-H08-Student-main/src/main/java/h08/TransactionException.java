package h08;

import java.util.Arrays;

public class TransactionException extends Exception{
    public TransactionException(String message, long transactionNumber){
        super(message + " " + String.valueOf(transactionNumber));
    }

    public TransactionException(Transaction[] transactions){
        super("Transaction numbers: [" + Arrays.toString(transactions) + "]");
    }
}
