package com.example.domain.model.accountmanager;

import com.example.domain.model.journal.Journal;

import java.time.LocalDateTime;
import java.util.*;

public class AccountHistory {
    // TODO: fix type
    private final TreeMap<LocalDateTime, AccountHistoryEntry> tree;

    public class AccountHistoryEntry{
        private double valueAtEpoch;
        private final List<Journal.Adjustment> adjustments;


        public AccountHistoryEntry(double valueAtEpoch){
            this.valueAtEpoch = valueAtEpoch;
            this.adjustments = new ArrayList<>();
        }

        public void addAdjustment(Journal.Adjustment adjustment, int valueToAccountBalance){
            this.valueAtEpoch += valueToAccountBalance;
            this.adjustments.add(adjustment);
        }

        public void removeAdjustment(Journal.Adjustment adjustment, int valueToAccountBalance){
            if (this.adjustments.isEmpty()){
                throw new NoSuchElementException("Attempting to remove an adjustment from an AccountHistoryEntry with no stored adjustments.");
            }
            if (!this.adjustments.contains(adjustment)){
                throw new NoSuchElementException("Cannot find the adjustment: '" + adjustment + "' in AccountHistoryEntry.");
            }
            this.valueAtEpoch -= valueToAccountBalance;
            this.adjustments.remove(adjustment);
        }

        public void updateValue(double value){
            this.valueAtEpoch += value;
        }

        public List<Journal.Adjustment> getAdjustments(){
            return new ArrayList<>(this.adjustments);
        }

        public double getValueAtEpoch(){
            return this.valueAtEpoch;
        }
    }

    public AccountHistory(LocalDateTime date, double valueAtEpoch){
        this.tree = new TreeMap<>();
        this.createNewNode(date, valueAtEpoch);
    }

    public AccountHistoryEntry getEvent(LocalDateTime date){
        return this.tree.get(date);
    }

    public AccountHistoryEntry getPreviousEvent(LocalDateTime date){
        LocalDateTime previousEventDate = this.tree.lowerKey(date);
        return this.tree.get(previousEventDate);
    }

    public void createNewNode (LocalDateTime date, double valueAtEpoch){
        AccountHistoryEntry historyEntry = new AccountHistoryEntry(valueAtEpoch);
        this.tree.put(date, historyEntry);
    }

    public void storeAdjustment(LocalDateTime adjustmentDate, Journal.Adjustment adjustment, int valueToAccountBalance){
        if (adjustment == null){
            throw new IllegalArgumentException("Adjustment cannot be null.");
        }

        if (this.tree.containsKey(adjustmentDate)){
            // Add adjustment to the existing node
            AccountHistoryEntry accountHistoryEntry = this.tree.get(adjustmentDate);
            accountHistoryEntry.addAdjustment(adjustment, valueToAccountBalance);
            this.updateFutureEvents(adjustmentDate, valueToAccountBalance);
        } else {
            // Create new node
            LocalDateTime previousEventDate = this.tree.lowerKey(adjustmentDate);
            AccountHistoryEntry previousEvent = this.tree.get(previousEventDate);
            // Get the value of the account at the given date
            if (previousEvent == null){
                this.createNewNode(adjustmentDate, 0);
            } else {
                this.createNewNode(adjustmentDate, previousEvent.getValueAtEpoch());
            }
            // Add adjustment to the new node
            AccountHistoryEntry accountHistoryEntry = this.tree.get(adjustmentDate);
            accountHistoryEntry.addAdjustment(adjustment, valueToAccountBalance);
            this.updateFutureEvents(adjustmentDate, valueToAccountBalance);
        }
    }

    public void removeAdjustment(LocalDateTime dateOfAdjustment, Journal.Adjustment adjustment, int valueToAccountBalance){
        if (adjustment == null){
            throw new IllegalArgumentException("Adjustment cannot be null.");
        }

        if (!this.tree.containsKey(dateOfAdjustment)){
            throw new IllegalArgumentException("No Events recorded on the given date: '" + dateOfAdjustment +"'.");
        }

        AccountHistoryEntry accountHistoryEntry = this.tree.get(dateOfAdjustment);
        accountHistoryEntry.removeAdjustment(adjustment, valueToAccountBalance);
        this.updatePastEvents(dateOfAdjustment, valueToAccountBalance);
    }

    public void updateFutureEvents(LocalDateTime date, int value){
        // From after iven date to latest-date
        NavigableMap<LocalDateTime, AccountHistoryEntry> treeSubsection = this.tree.subMap(date, false, this.tree.lastKey(), true);
        for (Map.Entry<LocalDateTime, AccountHistoryEntry> eventNode: treeSubsection.entrySet()){
            AccountHistoryEntry accountHistoryEntry = eventNode.getValue();
            accountHistoryEntry.updateValue(value);
        }
    }

    public void updatePastEvents(LocalDateTime date, int value){
        // From earliest-date to before given date
        NavigableMap<LocalDateTime, AccountHistoryEntry> treeSubsection = this.tree.subMap(this.tree.firstKey(), true, date, false);
        for (Map.Entry<LocalDateTime, AccountHistoryEntry> eventNode: treeSubsection.entrySet()){
            AccountHistoryEntry accountHistoryEntry = eventNode.getValue();
            accountHistoryEntry.updateValue(value);
        }
    }
}
