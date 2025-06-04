package com.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Account {
    private final UUID accountId;
    private final ArrayList<String> descriptors;
    private final double initialValue;
    private AccountCategory category;
    private HashMap<LocalDateTime, ArrayList<Journal>> history;
    private ChartOfAccounts.Node COA_Index;

    public Account(ArrayList<String> descriptors, double initialValue, ChartOfAccounts.Node COA_Index){
        this.accountId = UUID.randomUUID(); // generate a UUID for the Object
        this.descriptors = descriptors;
        this.initialValue = initialValue;
        this.history = new HashMap<LocalDateTime, ArrayList<Journal>>();
        this.COA_Index = COA_Index;
        this.category = COA_Index.getRoot();
    }

    public Account(String descriptor, double initialValue, ChartOfAccounts.Node COA_Index){
        this.accountId = UUID.randomUUID(); // generate a UUID for the Object
        this.descriptors = new ArrayList<>(Collections.singleton(descriptor));
        this.initialValue = initialValue;
        this.history = new HashMap<LocalDateTime, ArrayList<Journal>>();
        this.COA_Index = COA_Index;
        this.category = COA_Index.getRoot();
    }

    public UUID getAccountId(){
        return this.accountId;
    }

    public void applyJournal(){}

    public void getValueAt(){}
}
