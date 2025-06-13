package com.example.domain.model.journal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.UUID;

public class Journal {
    private final UUID journalID;
    private final String description;
    private final ArrayList<Adjustment> adjustments;
    private double total;
    private final LocalDateTime date;

    public record Adjustment(String description, int value) {
        @Override
            public String toString() {
                return "Adjustment(" + this.description + ", " + this.value + ")";
            }
        }

    public Journal(String description, ArrayList<Adjustment> adjustments, LocalDateTime date){
        this.journalID = UUID.randomUUID();
        this.description = description;
        this.adjustments = adjustments;
        this.date = date;
    }

    public Journal(String description, Adjustment adjustment, LocalDateTime date){
        this.journalID = UUID.randomUUID();
        this.description = description;
        this.adjustments = new ArrayList<>(Collections.singletonList(adjustment));
        this.date = date;
    }

    public LocalDateTime getDate(){
        return this.date;
    }

    public double getTotal(){
        return this.total;
    }

    public ArrayList<Adjustment> getAdjustments(){
        // TODO: return copy and overwrite?
        return this.adjustments;
    }

    public void addAdjustment(String description, int value){
        total += value;
        adjustments.add(new Adjustment(description, value));
    }

    // TODO make domain service for editing Journals that exist in accounthistory ("EditJournalInAccount", "RemoveJournalInAccount")
    public void removeAdjustment(String description, int value){
        Adjustment proxy_adjustment = new Adjustment(description, value);
        if (!this.adjustments.contains(proxy_adjustment)){
            throw new NoSuchElementException("Adjustment not found in journal: '" + proxy_adjustment + "'.");
        }
        total -= value;
        adjustments.remove(proxy_adjustment);
    }
}
