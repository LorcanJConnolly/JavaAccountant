package com.example.domain.model.accountmanager;

import com.example.domain.model.journal.Journal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class AccountHistory {
    // TODO: fix type
    private final HashMap<LocalDateTime, HashMap<Integer, ArrayList<Journal>>> tree;


    public AccountHistory(){
        this.tree = new HashMap<>();
    }

    public void storeEvent(LocalDateTime date, double valueAtDate, Journal journal){
        if (journal == null && !this.tree.isEmpty()){
            throw new IllegalArgumentException("Journal cannot be null.");
        }
    }
}
