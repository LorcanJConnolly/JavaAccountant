package com.example.domain.model.accountmanager;

import com.example.AccountCategory;
import com.example.domain.model.journal.Journal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class AccountHistory {
    // TODO: fix type
    private final TreeMap<LocalDateTime, AccountHistoryEntry> tree;

    private class AccountHistoryEntry{
        private double value;
        private List<Journal> journals;


        public AccountHistoryEntry(double value){
            this.value = value;
            this.journals = new ArrayList<>();
        }

        public void addJournal(Journal journal){
            this.value += journal.getVa
        }
    }

    public AccountHistory(){
        this.tree = new TreeMap<>();
    }

    public void storeJournal(LocalDateTime date, double valueAtDate, Journal journal){
        if (journal == null && !this.tree.isEmpty()){
            throw new IllegalArgumentException("Journal cannot be null.");
        }

        LocalDateTime journalDate = journal.getDate();

        if (this.tree.containsKey(journalDate)){
            HashMap<Double, ArrayList<Journal>> entryAtDate = this.tree.get(journalDate);
            entryAtDate.put(valueAtDate)
        }
    }

    public void removeJournal(Journal journal){

    }
}
