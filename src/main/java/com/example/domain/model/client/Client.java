package com.example.domain.model.client;

import java.util.HashSet;
import java.util.NoSuchElementException;

public class Client {
    private final HashSet<String> accountManagerIds;
    public final String name;

    public Client(String name){
        this.name = name;
        this.accountManagerIds = new HashSet<>();
    }

    public Client(String name, HashSet<String> jobs){
        this.name = name;
        this.accountManagerIds = jobs;
    }

    public void addAccountManagerId(String accountManagerId){
        boolean added = this.accountManagerIds.add(accountManagerId);
        if (!added){
            throw new IllegalArgumentException("Job already exists in Client. Change attribute(s) of Job.");
        }
    }

    public HashSet<String> getAccountManagerIds(){
        return this.accountManagerIds;
    }

    public void removeAccountManagerIds(String accountManagerId){
        boolean removed = this.accountManagerIds.remove(accountManagerId);
        if (!removed){
            throw new NoSuchElementException("Did not find job to delete.");
        }
    }
}
