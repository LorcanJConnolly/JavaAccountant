package com.example;

import java.time.Period;
import java.util.UUID;

public class JobManager {
    private final UUID id;
    private Period period_length;

    public JobManager(Period period_length){
        this.id = UUID.randomUUID(); // generate a UUID for the Object
        this.period_length = period_length;
    }

    public JobManager(){
        this.id = UUID.randomUUID(); // generate a UUID for the Object
        this.period_length = Period.ofMonths(12);
    }

    public Period getPeriodLength() {
        return this.period_length;
    }

    public void changePeriodLength(Period period_length){
        this.period_length = period_length;
    }

    public UUID getId(){
        return this.id;
    }
}
