package com.example.domain.model.accountmanager;

import com.example.domain.model.journal.Journal;

import java.time.LocalDateTime;
import java.util.*;

public class Account {
    private final HashSet<String> identifiers;
    private final LocalDateTime initialisationDate;  // TODO: do we need an initialisation date if that date *should* be the lowest date in the accounts history*
    private AccountCategory category;
    private final ChartOfAccountsTree.Node chartOfAccountsNode;
    private final AccountHistory history; // HashMap<LocalDateTime, HashMap<Integer, ArrayList<Journal>>>

    public Account(HashSet<String> identifiers, AccountCategory category, ChartOfAccountsTree.Node chartOfAccountsNode) {
        this.identifiers = identifiers;
        this.initialisationDate = LocalDateTime.now();
        this.category = category;
        this.chartOfAccountsNode = chartOfAccountsNode;
        this.history = new AccountHistory(this.initialisationDate, 0);
    }

    public Account(HashSet<String> descriptors, AccountCategory category, ChartOfAccountsTree.Node chartOfAccountsNode, LocalDateTime initialisationDate, double initialValue) {
        this.identifiers = descriptors;
        this.initialisationDate = initialisationDate;
        this.category = category;
        this.chartOfAccountsNode = chartOfAccountsNode;
        this.history = new AccountHistory(initialisationDate, initialValue);
    }

    public HashSet<String> getIdentifiers(){
        return new HashSet<>(identifiers);
    }

    public AccountCategory getCategory(){
        return this.category;
    }

    public LocalDateTime getInitialisationDate(){
        return this.initialisationDate;
    }

    public ChartOfAccountsTree.Node getChartOfAccountsNode(){
        return this.chartOfAccountsNode;
    }

    public AccountHistory getHistory(){
        return this.history;
    }

    public void newCategory(AccountCategory newCategory){
        this.category = newCategory;
    }

    public double getValueAtDate(LocalDateTime date){
        AccountHistory.AccountHistoryEntry event = this.history.getEvent(date);
        if (event != null){
            return event.getValueAtEpoch();
        } else {
            AccountHistory.AccountHistoryEntry previous_event  = this.history.getPreviousEvent(date);
            return previous_event.getValueAtEpoch();
        }
    }

    public void applyAdjustment(LocalDateTime date, Journal.Adjustment adjustment){
        if (this.category == AccountCategory.LIABILITY || this.category == AccountCategory.EQUITY || this.category == AccountCategory.REVENUE){
            int value = adjustment.value();
            int valueToAccountBalance = -1*(value);
            this.history.storeAdjustment(date, adjustment, valueToAccountBalance);
        } else {
            int valueToAccountBalance = adjustment.value();
            this.history.storeAdjustment(date, adjustment, valueToAccountBalance);
        }
    }

    public void removeAdjustment(LocalDateTime date, Journal.Adjustment adjustment){
        if (this.category == AccountCategory.LIABILITY || this.category == AccountCategory.EQUITY || this.category == AccountCategory.REVENUE){
            int value = adjustment.value();
            int valueToAccountBalance = -1*(value);
            this.history.removeAdjustment(date, adjustment, valueToAccountBalance);
        } else {
            int valueToAccountBalance = adjustment.value();
            this.history.removeAdjustment(date, adjustment, valueToAccountBalance);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return this.identifiers == account.identifiers;
    }
}
