package com.example;

import java.time.Period;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class AccountsManager {
    private final UUID accountsManagerId;
    private String description;
    private Period period_length;
    private LocalDate period_start;
    private final HashMap<UUID, Account> accounts;
    private final LocalDate initialisation_date;

    public AccountsManager(LocalDate period_start, Period period_length, String description){
        this.accountsManagerId = UUID.randomUUID(); // generate a UUID for the Object
        this.period_length = period_length;
        this.period_start = period_start;
        // TODO currency and reorder
        this.description = description;
        this.initialisation_date = LocalDate.now();
        this.accounts = new HashMap<>();
    }

    public AccountsManager(LocalDate period_start, String description){
        this.accountsManagerId = UUID.randomUUID(); // generate a UUID for the Object
        this.period_length = Period.ofMonths(12);
        this.period_start = period_start;
        this.description = description;
        this.initialisation_date = LocalDate.now();
        this.accounts = new HashMap<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountsManager)) return false;
        AccountsManager accounts_manager = (AccountsManager) o;
        return this.getId() == accounts_manager.getId() &&
                this.getPeriodLength() == accounts_manager.getPeriodLength() &&
                this.getPeriodStart() == accounts_manager.getPeriodStart() &&
                Objects.equals(this.getDescription(), accounts_manager.getDescription());
    }

    // TODO: hashcode override

    public UUID getId(){
        return this.accountsManagerId;
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
        if (this.accounts.containsKey(account.getAccountId())) {
            throw new IllegalArgumentException("Account already exists in AccountsManager.");
        }
        this.accounts.put(account.getAccountId(), account);
    }

    public void deleteAccount(Account account){
        if (!this.accounts.containsKey(account.getAccountId())) {
            throw new IllegalArgumentException("Account does not exist in AccountsManager.");
        }
        this.accounts.remove(account.getAccountId(), account);
    }

    public void adjustAccount(){}


}
