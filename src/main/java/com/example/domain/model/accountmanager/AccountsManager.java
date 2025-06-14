package com.example.domain.model.accountmanager;

import java.time.Period;
import java.time.LocalDate;
import java.util.*;

public class AccountsManager {
    private final UUID accountsManagerId;
    private String description;
    private final LocalDate initialisation_date;
    private final AccountingPeriod accountingPeriod;
    private final HashMap<UUID, Account> accounts;
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

    public AccountingPeriod getAccountingPeriod(){
        return this.accountingPeriod;
    }

    // TODO: getAccounts by descriptors
    public HashMap<UUID, Account> getAccounts(){
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

    public void addNewAccount(ArrayList<String> descriptors, AccountCategory category, String chartOfAccountsNodeCategory){
        Account account = new Account(descriptors, category, chartOfAccountsNodeCategory);

        if (findAccountById(account.getAccountId()) != null){
            throw new IllegalArgumentException("Account already exists in AccountsManager.");
        }

        if (this.chartOfAccounts.findNodeByCategory(account.getChartOfAccountsNodeCategory()) == null){
            throw new IllegalArgumentException("Account has no index in chart of accounts.");
        }
        // TODO: check if account's category is an AccountCategory
        // TODO: string account category and then we make the object here which checks if it is an AccountCategory
        // TODO: both wrong, when we add a new account we should create its category from the COA root - done after so node exists in COA


        this.accounts.put(account.getAccountId(), account);
    }

    // TODO would we access this account through its account manager (existing account by definition must have an account manager) in the function signature?
    public void addExistingAccount(Account account){
        // TODO, check the account's category fits into the COA

        if (findAccountById(account.getAccountId()) != null){
            throw new IllegalArgumentException("Account already exists in AccountsManager.");
        }

        if (this.chartOfAccounts.findNodeByCategory(account.getChartOfAccountsNodeCategory()) == null){
            throw new IllegalArgumentException("Account has no index in chart of accounts.");
        }

        this.accounts.put(account.getAccountId(), account);
    }

    public void deleteAccount(UUID accountId){
        Account foundAccount = findAccountById(accountId);

        if (foundAccount == null) {
            throw new NoSuchElementException("Account does not exist in AccountManager.");
        }

        this.accounts.remove(accountId, foundAccount);
    }
    public Account findAccountById(UUID accountId){
        return this.accounts.get(accountId);
    }
}
