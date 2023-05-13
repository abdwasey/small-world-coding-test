package com.smallworld;

import com.smallworld.reader.TransactionsFileReader;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class TransactionTest {

    static TransactionDataFetcher transactionDataFetcher;

    @BeforeClass
    public static void addAllTransactionToList(){
        transactionDataFetcher = new TransactionDataFetcher();
    }

    @Test
    public void getTotalTransactionAmount(){
        assertEquals(4371.37, transactionDataFetcher.getTotalTransactionAmount(), 0.01);
    }

    @Test
    public void getTotalTransactionAmountSentBy(){
        assertEquals(828.26, transactionDataFetcher.getTotalTransactionAmountSentBy("Tom Shelby"), 0.0001);
    }

    @Test
    public void getMaxTransactionAmount(){
        assertEquals(985.0, transactionDataFetcher.getMaxTransactionAmount(), 0.01);
    }

}
