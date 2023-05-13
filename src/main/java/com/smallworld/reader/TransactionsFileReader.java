package com.smallworld.reader;

import com.smallworld.dao.TransactionDetailsDAO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionsFileReader {

    private final String FILE_NAME = System.getProperty("user.dir") + File.separator + "transactions.json" + File.separator;
    public List<TransactionDetailsDAO> transactionDetailsDAOList = new ArrayList<>();

    public void fileReader() {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(FILE_NAME)));
            parseToList(jsonContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void parseToList(String jsonContent) {
        JSONArray jsonArray = new JSONArray(jsonContent);
        for (int index = 0; index < jsonArray.length(); index++) {
            JSONObject jsonObject = jsonArray.getJSONObject(index);
            TransactionDetailsDAO transactionDetailsDAO = new TransactionDetailsDAO();
            transactionDetailsDAO.setMtn(Optional.ofNullable(jsonObject.getInt("mtn")).orElse(null));
            transactionDetailsDAO.setAmount(Optional.ofNullable(jsonObject.getDouble("amount")).orElse(null));
            transactionDetailsDAO.setSenderFullName(Optional.ofNullable(jsonObject.getString("senderFullName")).orElse(null));
            transactionDetailsDAO.setSenderAge(Optional.ofNullable(jsonObject.getInt("senderAge")).orElse(null));
            transactionDetailsDAO.setBeneficiaryFullName(Optional.ofNullable(jsonObject.getString("beneficiaryFullName")).orElse(null));
            transactionDetailsDAO.setBeneficiaryAge(Optional.ofNullable(jsonObject.getInt("beneficiaryAge")).orElse(null));
            transactionDetailsDAO.setIssueId(jsonObject.isNull("issueId") ? null : jsonObject.getInt("issueId"));
            transactionDetailsDAO.setIssueMessage(jsonObject.isNull("issueMessage") ? null :
                    Optional.ofNullable(jsonObject.getString("issueMessage")).orElse(null));
            transactionDetailsDAO.setIssueSolved(Optional.ofNullable(jsonObject.getBoolean("issueSolved")).orElse(null));
            transactionDetailsDAOList.add(transactionDetailsDAO);
        }
    }
}
