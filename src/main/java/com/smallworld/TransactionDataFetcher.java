package com.smallworld;

import com.smallworld.dao.TransactionDetailsDAO;
import com.smallworld.reader.TransactionsFileReader;

import java.util.*;
import java.util.stream.Collectors;

public class TransactionDataFetcher {

    TransactionsFileReader transactionsFileReader;

    public TransactionDataFetcher() {
        transactionsFileReader = new TransactionsFileReader();
        transactionsFileReader.fileReader();
    }

    /**
     * Returns the sum of the amounts of all transactions
     */
    public double getTotalTransactionAmount() {
        return transactionsFileReader.transactionDetailsDAOList.stream().mapToDouble(TransactionDetailsDAO::getAmount).sum();
    }

    /**
     * Returns the sum of the amounts of all transactions sent by the specified client
     */
    public double getTotalTransactionAmountSentBy(String senderFullName) {
        return transactionsFileReader.transactionDetailsDAOList.stream().filter(
                transaction -> transaction.getSenderFullName().equalsIgnoreCase(senderFullName)).mapToDouble(TransactionDetailsDAO::getAmount).sum();
    }

    /**
     * Returns the highest transaction amount
     */
    public double getMaxTransactionAmount() {
        return transactionsFileReader.transactionDetailsDAOList.stream().map(TransactionDetailsDAO::getAmount)
                .max(Comparator.naturalOrder()).orElse(0.0);
    }

    /**
     * Counts the number of unique clients that sent or received a transaction
     */
    public long countUniqueClients() {
        return transactionsFileReader.transactionDetailsDAOList.stream().map(TransactionDetailsDAO::getSenderFullName).distinct().count();
    }

    /**
     * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
     * issue that has not been solved
     */
    public boolean hasOpenComplianceIssues(String clientFullName) {
        return transactionsFileReader.transactionDetailsDAOList.stream()
                .filter(transactionDetailsDAO ->
                        transactionDetailsDAO.getSenderFullName().equalsIgnoreCase(clientFullName) ||
                                transactionDetailsDAO.getBeneficiaryFullName().equalsIgnoreCase(clientFullName)).collect(Collectors.toList()).size() > 0 ? true : false;
    }

    /**
     * Returns all transactions indexed by beneficiary name
     */
    public Map<String, Object> getTransactionsByBeneficiaryName() {
        Map<String, Object> stringObjectHashMap = new HashMap<>();
        Map<String, List<TransactionDetailsDAO>> stringIntegerMap =
                transactionsFileReader.transactionDetailsDAOList.stream()
                        .collect(Collectors.groupingBy(TransactionDetailsDAO::getBeneficiaryFullName));
        for (Map.Entry<String, List<TransactionDetailsDAO>> entry : stringIntegerMap.entrySet()) {
            List<TransactionDetailsDAO> beneficiaryTransactions = entry.getValue();
            stringObjectHashMap.put(entry.getKey(), beneficiaryTransactions);
        }
        return stringObjectHashMap;
    }

    /**
     * Returns the identifiers of all open compliance issues
     */
    public Set<Integer> getUnsolvedIssueIds() {
        return transactionsFileReader.transactionDetailsDAOList.stream()
                .filter((transactionDetailsDAO -> !(transactionDetailsDAO.getIssueSolved())))
                .map(transactionDetailsDAO -> transactionDetailsDAO.getIssueId()).collect(Collectors.toSet());
    }

    /**
     * Returns a list of all solved issue messages
     */
    public List<String> getAllSolvedIssueMessages() {
        return transactionsFileReader.transactionDetailsDAOList.stream()
                .filter(transactionDetailsDAO -> transactionDetailsDAO.getIssueSolved()).map(transactionDetailsDAO -> transactionDetailsDAO.getIssueMessage()).collect(Collectors.toList());
    }

    /**
     * Returns the 3 transactions with highest amount sorted by amount descending
     */
    public List<TransactionDetailsDAO> getTop3TransactionsByAmount() {
        return transactionsFileReader.transactionDetailsDAOList.stream()
                .sorted((o1, o2) -> Double.compare(o2.getAmount(), o1.getAmount())).limit(3)
                .collect(Collectors.toList());
    }

    /**
     * Returns the sender with the most total sent amount
     */
    public Optional<Object> getTopSender() {
        Map<String, Double> totalSentAmountsBySender = transactionsFileReader.transactionDetailsDAOList.stream()
                .collect(Collectors.groupingBy(TransactionDetailsDAO::getSenderFullName,
                        Collectors.summingDouble(TransactionDetailsDAO::getAmount)));
        return Optional.of(totalSentAmountsBySender.entrySet().stream()
                .max(Map.Entry.comparingByValue()));
    }

}
