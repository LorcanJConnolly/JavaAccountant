package com.example.domain.model.accountmanager;

import java.time.Period;
import java.time.LocalDate;
import java.util.*;

public class AccountsManager {
    private final UUID accountsManagerId;
    private String description;
    private final LocalDate initialisation_date;
    private final AccountingPeriod accountingPeriod;
    private final HashMap<HashSet<String>, Account> accounts;
    private ChartOfAccountsTree chartOfAccounts;

    private static class AccountingPeriod {
        private final LocalDate start;
        private final Period length;

        public AccountingPeriod(LocalDate period_start, Period period_length){
            this.start = period_start;
            this.length = period_length;
        }

        public Period getLength() {
            return length;
        }

        public LocalDate getStart() {
            return start;
        }

        @Override
        public boolean equals(Object o){
            if (this == o) return true;
            if (!(o instanceof AccountingPeriod accountingPeriod)) return false;
            return this.getLength() == accountingPeriod.getLength() &&  this.getStart() == accountingPeriod.getStart();
        }
    }

    public AccountsManager(String description, LocalDate period_start, Period period_length, ChartOfAccountsTree chartOfAccounts){
        this.accountsManagerId = UUID.randomUUID(); // generate a UUID for the Object
        this.description = description;
        this.initialisation_date = LocalDate.now();
        this.accountingPeriod = new AccountingPeriod(period_start, period_length);
        this.accounts = new HashMap<>();
        this.chartOfAccounts = chartOfAccounts;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountsManager accountsManager)) return false;
        return this.getId() == accountsManager.getId() &&
                this.accountingPeriod.getLength() == accountsManager.accountingPeriod.getLength() &&
                this.accountingPeriod.getStart() == accountsManager.accountingPeriod.getStart() &&
                Objects.equals(this.getDescription(), accountsManager.getDescription());
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
        return this.accountingPeriod.getStart();
    }

    public Period getPeriodLength(){
        return this.accountingPeriod.getLength();
    }

    public HashMap<HashSet<String>, Account> getAccounts(){
        return new HashMap<>(this.accounts);
    }

    public ChartOfAccountsTree getChartOfAccounts(){
        return this.chartOfAccounts;
    }

    public void changeDescription(String newDescription){
        this.description = newDescription;
    }

    public void changeChartOfAccounts(ChartOfAccountsTree newChartOfAccounts){
        this.chartOfAccounts = newChartOfAccounts;
    }

    public void addAccount(HashSet<String> identifiers, ChartOfAccountsTree.Node chartOfAccountsNode){
        AccountCategory category = AccountCategory.fromString(this.chartOfAccounts.getRoot().getCategory());
        Account account = new Account(identifiers, category, chartOfAccountsNode);

        if (findAccountByIdentifiers(account.getIdentifiers()) != null){
            throw new IllegalArgumentException("Account already exists in AccountsManager.");
        }

        if (this.chartOfAccounts.findNode(account.getChartOfAccountsNode().getCategory(), account.getChartOfAccountsNode().getCode()) == null){
            throw new IllegalArgumentException("Account has no index in chart of accounts.");
        }

        this.accounts.put(account.getIdentifiers(), account);
    }

    public void deleteAccount(HashSet<String> accountIdentifiers){
        Account foundAccount = findAccountByIdentifiers(accountIdentifiers);

        if (foundAccount == null) {
            throw new NoSuchElementException("Account does not exist in AccountManager.");
        }

        this.accounts.remove(accountIdentifiers, foundAccount);
    }
    public Account findAccountByIdentifiers(HashSet<String> accountIdentifiers){
        return this.accounts.get(accountIdentifiers);
    }
}
