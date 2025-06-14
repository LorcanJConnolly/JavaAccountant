package com.example.domain.model.accountmanager;

import com.example.domain.model.journal.Journal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Account {
    private final UUID accountId;
    private final ArrayList<String> descriptors;    // TODO: possibly set?
    private final LocalDateTime initialisationDate;  // TODO: do we need an initialisation date if that date *should* be the lowest date in the accounts history*
    private AccountCategory category;
    private final String chartOfAccountsNodeCategory;   // TODO: could this be gotten from the COA node category
    private final AccountHistory history; // HashMap<LocalDateTime, HashMap<Integer, ArrayList<Journal>>>

    public Account(ArrayList<String> descriptors, AccountCategory category, String chartOfAccountsNodeCategory) {
        this.accountId = UUID.randomUUID();
        this.descriptors = descriptors;
        this.initialisationDate = LocalDateTime.now();
        this.category = category;
        this.chartOfAccountsNodeCategory = chartOfAccountsNodeCategory;
        this.history = new AccountHistory(this.initialisationDate, 0);
    }

    public Account(ArrayList<String> descriptors, AccountCategory category, String chartOfAccountsNodeCategory, LocalDateTime initialisationDate, double initialValue) {
        this.accountId = UUID.randomUUID();
        this.descriptors = descriptors;
        this.initialisationDate = initialisationDate;
        this.category = category;
        this.chartOfAccountsNodeCategory = chartOfAccountsNodeCategory;
        this.history = new AccountHistory(initialisationDate, initialValue);
    }

    public UUID getAccountId(){
        return this.accountId;
    }

    public ArrayList<String> getDescriptors(){
        return new ArrayList<>(descriptors);
    }

    public AccountCategory getCategory(){
        return this.category;
    }

    public LocalDateTime getInitialisationDate(){
        return this.initialisationDate;
    }

    public String getChartOfAccountsNodeCategory(){
        return this.chartOfAccountsNodeCategory;
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

    // TODO: equals for descriptors and UUID, convert to set
    // TODO: refactor - descriptors become identifiers, we can drop accountId
}
