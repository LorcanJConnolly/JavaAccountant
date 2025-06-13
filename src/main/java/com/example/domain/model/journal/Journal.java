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

    public class Adjustment {
        private final String description;
        private final int value;

        public Adjustment(String description, int value){
            this.description = description;
            this.value = value;
        }

        public String getDescription(){
            return this.description;
        }

        public int getValue(){
            return this.value;
        }

        @Override
        public String toString(){
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

    public void addAdjustment(Adjustment adjustment){
        total += adjustment.getValue();
        adjustments.add(adjustment);
    }

    // TODO make domain service for editing Journals that exist in accounthistory ("EditJournalInAccount", "RemoveJournalInAccount")
    public void removeAdjustment(Adjustment adjustment){
        if (!this.adjustments.contains(adjustment)){
            throw new NoSuchElementException("Adjustment not found in journal: '" + adjustment + "'.");
        }
        total -= adjustment.getValue();
        adjustments.remove(adjustment);
    }
}
