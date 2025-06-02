package com.example;

import java.util.UUID;

public class Account {
    private final UUID id;
    private int current_value;

    public Account(){
        this.id = UUID.randomUUID(); // generate a UUID for the Object
    }

    public UUID getId(){
        return this.id;
    }

    public void applyAdjustment(){}

    public void getValueAt(){}
}
