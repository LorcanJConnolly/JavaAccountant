package com.example.domain.model.accountmanager;

public enum AccountCategory {
    ASSET, LIABILITY , EQUITY, REVENUE, EXPENSE;

    public static AccountCategory fromString(String input) {
        try {
            return AccountCategory.valueOf(input);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Category '" + input + "' is not a member of the AccountCategory enum.");
        }
    }
}
