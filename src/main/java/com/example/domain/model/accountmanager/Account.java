package com.example.domain.model.accountmanager;

import com.example.domain.model.journal.Adjustment;
import com.example.domain.model.journal.Journal;

import java.time.LocalDateTime;
import java.util.*;

//public class Account {
//    private final UUID accountId;
//    private final ArrayList<String> descriptors;
//    private final double initialValue;
//    private String category;
//    private HashMap<LocalDateTime, HashMap<Integer, ArrayList<Journal>>> history;
//    private ChartOfAccountsTree.Node COA_Index;
//
//    public Account(ArrayList<String> descriptors, double initialValue, ChartOfAccountsTree.Node COA_Index){
//        this.accountId = UUID.randomUUID(); // generate a UUID for the Object
//        this.descriptors = descriptors;
//        this.initialValue = initialValue;
//        this.history = new HashMap<LocalDateTime, ArrayList<Journal>>();
//        this.COA_Index = COA_Index;
//        this.category = COA_Index.getChartOfAccounts().getRoot().getCategory();
//    }
//
//    public Account(String descriptor, double initialValue, ChartOfAccountsTree.Node COA_Index){
//        this.accountId = UUID.randomUUID(); // generate a UUID for the Object
//        this.descriptors = new ArrayList<>(Collections.singleton(descriptor));
//        this.initialValue = initialValue;
//        this.history = new HashMap<LocalDateTime, ArrayList<Journal>>();
//        this.COA_Index = COA_Index;
//        this.category = COA_Index.getChartOfAccounts().getRoot().getCategory();
//    }
//
//    public UUID getAccountId(){
//        return this.accountId;
//    }
//
//    public void applyAdjustment(LocalDateTime date, Adjustment adjustment){
//        int current_value = this.getValueAt(LocalDateTime.now());
//        HashMap<int, Adjustment>
//        history.put(date)
//    }
//
//    public int getValueAt(LocalDateTime date){}
//
//    public ArrayList<Journal> getJournalsAt(LocalDateTime date){}
//}
