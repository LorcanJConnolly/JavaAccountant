package com.example.domain.model.accountmanager;

import com.example.domain.model.journal.Journal;

import java.time.LocalDateTime;
import java.util.*;

public class Account {
    private final UUID accountId;
    private final ArrayList<String> descriptors;
    private final double initialValue;
    private AccountCategory category;
    private final AccountHistory history; // HashMap<LocalDateTime, HashMap<Integer, ArrayList<Journal>>>
    private final String chartOfAccountsNodeCategory;

    public Account(ArrayList<String> descriptors, double initialValue, AccountCategory category, String chartOfAccountsNodeCategory) {
        this.accountId = UUID.randomUUID(); // generate a UUID for the Object
        this.descriptors = descriptors;
        this.initialValue = initialValue;
        this.category = category;
        this.chartOfAccountsNodeCategory = chartOfAccountsNodeCategory;
        this.history = new AccountHistory();
    }

    public UUID getAccountId(){
        return this.accountId;
    }

    public ArrayList<String> getDescriptors(){
        return new ArrayList<>(descriptors);
    }

    public double getInitialValue(){
        return this.initialValue;
    }

    public AccountCategory getCategory(){
        return this.category;
    }

    public String getChartOfAccountsNodeCategory(){
        return this.chartOfAccountsNodeCategory;
    }

    public AccountHistory getHistory(){
        return this.history;
    }

    public void newCategory(AccountCategory newCategory){
        this.category = newCategory;
    }
}
