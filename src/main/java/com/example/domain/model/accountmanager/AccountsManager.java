package com.example.domain.model.accountmanager;

import java.time.Period;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

public class AccountsManager {
    private final UUID accountsManagerId;
    private String description;
    private final LocalDate initialisation_date;
    private LocalDate period_start;
    private Period period_length;
    private final HashMap<UUID, Account> accounts;
    private ChartOfAccountsTree chartOfAccounts;

    public AccountsManager(String description, LocalDate period_start, Period period_length, ChartOfAccountsTree chartOfAccounts){
        this.accountsManagerId = UUID.randomUUID(); // generate a UUID for the Object
        this.description = description;
        this.initialisation_date = LocalDate.now();
        this.period_start = period_start;
        this.period_length = period_length;
        this.accounts = new HashMap<>();
        this.chartOfAccounts = chartOfAccounts;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountsManager accounts_manager)) return false;
        return this.getId() == accounts_manager.getId() &&
                this.getPeriodLength() == accounts_manager.getPeriodLength() &&
                this.getPeriodStart() == accounts_manager.getPeriodStart() &&
                Objects.equals(this.getDescription(), accounts_manager.getDescription());
    }

    // TODO: hashcode override

    public UUID getId(){
        return this.accountsManagerId;
    }

    public String getDescription(){
        return this.description;
    }

    public LocalDate getInitialisationDate(){
        return this.initialisation_date;
    }

    public LocalDate getPeriodStart(){
        return this.period_start;
    }

    public Period getPeriodLength() {
        return this.period_length;
    }

    public HashMap<UUID, Account> getAccounts(){
        return new HashMap<>(this.accounts);
    }

    public ChartOfAccountsTree getChartOfAccounts(){
        return this.chartOfAccounts;
    }

    public void changePeriodStart(LocalDate new_date){
        this.period_start = new_date;
    }

    public void changePeriodLength(Period new_period_length){
        this.period_length = new_period_length;
    }

    public void changeDescription(String newDescription){
        this.description = newDescription;
    }

    public void changeChartOfAccounts(ChartOfAccountsTree newChartOfAccounts){
        this.chartOfAccounts = newChartOfAccounts;
    }

    public void addAccount(Account account){
        if (findAccountById(account.getAccountId()) != null){
            throw new IllegalArgumentException("Account already exists in AccountsManager.");
        }

        if (this.chartOfAccounts.findNodeByCategory(account.getChartOfAccountsNodeCategory()) == null){
            throw new IllegalArgumentException("Account has no index in chart of accounts.");
        }

        this.accounts.put(account.getAccountId(), account);
    }

    public void deleteAccount(Account account){
        Account foundAccount = findAccountById(account.getAccountId());

        if (foundAccount == null) {
            throw new NoSuchElementException("Account does not exist in AccountManager.");
        }

        this.accounts.remove(account.getAccountId(), account);
    }
    public Account findAccountById(UUID accountId){
        return this.accounts.get(accountId);
    }
}
