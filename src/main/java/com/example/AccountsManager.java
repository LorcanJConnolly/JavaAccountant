package com.example;

import java.time.Period;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.UUID;

public class AccountsManager {
    private final UUID id;
    private Period period_length;
    private LocalDate period_start;
    private final LocalDate initialisation_date;
    private String description;
    private HashMap<UUID, Account> accounts;

    public AccountsManager(LocalDate period_start, Period period_length, String description){
        this.id = UUID.randomUUID(); // generate a UUID for the Object
        this.period_length = period_length;
        this.period_start = period_start;
        this.description = description;
        this.initialisation_date = LocalDate.now();
        this.accounts = new HashMap<>();
    }

    public AccountsManager(LocalDate period_start, String description){
        this.id = UUID.randomUUID(); // generate a UUID for the Object
        this.period_length = Period.ofMonths(12);
        this.period_start = period_start;
        this.description = description;
        this.initialisation_date = LocalDate.now();
        this.accounts = new HashMap<>();
    }

    public UUID getId(){
        return this.id;
    }

    public Period getPeriodLength() {
        return this.period_length;
    }

    public LocalDate getPeriodStart(){
        return this.period_start;
    }

    public String getDescription(){
        return this.description;
    }

    public HashMap<UUID, Account> getAccounts(){
        return new HashMap<>(this.accounts);
    }

    public void changePeriodStart(LocalDate new_date){
        this.period_start = new_date;
    }

    public void changePeriodLength(Period new_period_length){
        this.period_length = new_period_length;
    }

    public void changeDescription(String new_description){
        this.description = new_description;
    }

    public void addAccount(Account account){
        if (this.accounts.containsKey(account.getId())) {
            throw new IllegalArgumentException("Account already exists in AccountsManager.");
        }
        this.accounts.put(account.getId(), account);
    }

    public void deleteAccount(Account account){
        if (!this.accounts.containsKey(account.getId())) {
            throw new IllegalArgumentException("Account does not exist in AccountsManager.");
        }
        this.accounts.remove(account.getId(), account);
    }

    public void adjustAccount(){}


}
