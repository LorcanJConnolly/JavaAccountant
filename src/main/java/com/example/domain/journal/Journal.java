package com.example.domain.journal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class Journal {
    private final UUID journalID;
    private final String description;
    private final ArrayList<Adjustment> adjustments;
    private final LocalDateTime date;

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
}
