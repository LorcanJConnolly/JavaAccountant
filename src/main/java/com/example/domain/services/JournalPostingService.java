package com.example.domain.services;

import com.example.domain.model.accountmanager.Account;
import com.example.domain.model.accountmanager.AccountsManager;
import com.example.domain.model.journal.Journal;

import java.util.HashSet;

// TODO Jounral does not go to accounts, its adjustments do > we just give the journal to the account manager and unpack it
public class JournalPostingService {
    public void PostJournalToAccounts(AccountsManager accountsManager, HashSet<String> accountIdentifiers, Journal journal) {
        if (journal.getTotal() != 0) {
            throw new IllegalArgumentException("Cannot post unbalanced Journal. Please balance adjustments.");
        }

        Account account = accountsManager.findAccountByIdentifiers(accountIdentifiers);
        for (Journal.Adjustment adjustment : journal.getAdjustments()) {
            account.applyAdjustment(journal.getDate(), adjustment);
        }
    }

    public void deleteJournalFromAccounts(AccountsManager accountsManager, HashSet<String> accountIdentifiers, Journal journal) {
        Account account = accountsManager.findAccountByIdentifiers(accountIdentifiers);
        for (Journal.Adjustment adjustment : journal.getAdjustments()) {
            account.removeAdjustment(journal.getDate(), adjustment);
        }
    }
}
