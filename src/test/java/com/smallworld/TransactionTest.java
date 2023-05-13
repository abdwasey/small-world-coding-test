package com.smallworld;

import com.smallworld.dao.TransactionDetailsDAO;
import com.smallworld.reader.TransactionsFileReader;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

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

    @Test
    public void countUniqueClients(){
        assertEquals(5, transactionDataFetcher.countUniqueClients());
    }

    @Test
    public void hasOpenComplianceIssues(){
        assertEquals(true, transactionDataFetcher.hasOpenComplianceIssues("Tom Shelby"));
    }

    @Test
    public void getTransactionsByBeneficiaryName(){
        Map<String, Object> result = transactionDataFetcher.getTransactionsByBeneficiaryName();
        assertEquals(10,result.size());
    }

    @Test
    public void getUnsolvedIssueIds(){
        Set<Integer> result = transactionDataFetcher.getUnsolvedIssueIds();
        assertEquals(5,result.size());
    }

    @Test
    public void getAllSolvedIssueMessages(){
        List<String>  stringList = transactionDataFetcher.getAllSolvedIssueMessages();
        assertEquals(8,stringList.size());
    }

    @Test
    public void getTop3TransactionsByAmount(){
        List<TransactionDetailsDAO>  stringList = transactionDataFetcher.getTop3TransactionsByAmount();
        double sumOfAmount = stringList.stream().mapToDouble(value -> value.getAmount()).sum();
        assertEquals(2317.0,sumOfAmount, 0.01);
    }

    @Test
    public void getTopSender(){
        Optional<Object> objectOptional = transactionDataFetcher.getTopSender();
        if(objectOptional.isPresent()){
            Object o = "Optional[Grace Burgess=1998.0]";
            assertEquals(objectOptional.get().toString(), o);
        }
    }

}
