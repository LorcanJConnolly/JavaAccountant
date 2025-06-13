package com.example.domain.services;

import com.example.domain.model.accountmanager.Account;
import com.example.domain.model.accountmanager.AccountsManager;
import com.example.domain.model.journal.Journal;

import java.util.UUID;

public class JournalPostingService {
    public void PostJournalToAccounts(AccountsManager accountsManager, String stringAccountId, Journal journal) {
        if (journal.getTotal() != 0) {
            throw new IllegalArgumentException("Cannot post unbalanced Journal. Please balance adjustments.");
        }
        UUID accountId = UUID.fromString(stringAccountId);
        Account account = accountsManager.findAccountById(accountId);
        for (Journal.Adjustment adjustment : journal.getAdjustments()) {
            account.applyAdjustment(journal.getDate(), adjustment);
        }
    }

    public void deleteJournalFromAccounts(AccountsManager accountsManager, String stringAccountId, Journal journal) {
        UUID accountId = UUID.fromString(stringAccountId);
        Account account = accountsManager.findAccountById(accountId);
        for (Journal.Adjustment adjustment : journal.getAdjustments()) {
            account.removeAdjustment(journal.getDate(), adjustment);
        }
    }
}
