package com.example.domain.services;

import com.example.domain.model.AdjustmentTest;
import com.example.domain.model.accountmanager.AccountCategory;
import com.example.domain.model.accountmanager.AccountsManager;
import com.example.domain.model.accountmanager.ChartOfAccountsTree;
import com.example.domain.model.journal.Journal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashSet;

public class JournalPostingServiceTest {

    @BeforeEach
    void initAccountManagers(){
        // dates
        LocalDate periodStart1 = LocalDate.of(2024, 1,1);
        LocalDate periodStart2 = LocalDate.of(2025, 1,1);
        LocalDate periodStart3 = LocalDate.of(2026, 1,1);

        // periods
        Period periodLength1 = Period.ofMonths(3);
        Period periodLength2 = Period.ofMonths(6);
        Period periodLength3 = Period.ofMonths(12);

        // COAs
        ChartOfAccountsTree coa1 = new ChartOfAccountsTree("assets", "0001", "chart of accounts 1");
        ChartOfAccountsTree coa2 = new ChartOfAccountsTree("liabilities", "0001", "chart of accounts 2");
        ChartOfAccountsTree coa3 = new ChartOfAccountsTree("equity", "0001", "chart of accounts 3");
        // TODO: COA add and remove node#
        coa1.addNode(coa1.getRoot(), "even more assets", "0002");
        coa1.addNode(coa1.getRoot(), "even more liabilities", "0002");
        coa1.addNode(coa1.getRoot(), "even more equity", "0002");


        // AccountManagers
        AccountsManager accountsManager1 = new AccountsManager("account manager 1", periodStart1, periodLength1, coa1);
        AccountsManager accountsManager2 = new AccountsManager("account manager 2", periodStart2, periodLength2, coa2);
        AccountsManager accountsManager3 = new AccountsManager("account manager 3", periodStart3, periodLength3, coa3);

        // Add Accounts
        //descriptors
        HashSet<String> identifiers1 = new HashSet<>();
        identifiers1.add("account description 1");
        identifiers1.add("account code 1");

        HashSet<String> identifiers2 = new HashSet<>();
        identifiers2.add("account description 2");
        identifiers2.add("account code 2");

        HashSet<String> identifiers3 = new HashSet<>();
        identifiers3.add("account description 3");
        identifiers3.add("account code 3");

        accountsManager1.addAccount(identifiers1, accountsManager1.getChartOfAccounts().findNode("even more assets", "0002"));
        accountsManager1.addAccount(identifiers2, accountsManager2.getChartOfAccounts().findNode("even more liabilities", "0002"));
        accountsManager1.addAccount(identifiers3, accountsManager3.getChartOfAccounts().findNode("even more equity", "0002"));
    }

    @BeforeEach
    void initJournals(){
        // date times
        LocalDateTime sameDateTime1 = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime sameDateTime2 = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime sameDateTime3 = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime uniqueDateTime1 = LocalDateTime.of(1, 1, 1, 1, 9);
        LocalDateTime uniqueDateTime2 = LocalDateTime.of(1, 1, 1, 9, 1);
        LocalDateTime uniqueDateTime3 = LocalDateTime.of(1, 1, 9, 1, 1);
        LocalDateTime uniqueDateTime4 = LocalDateTime.of(1, 9, 1, 1, 1);
        LocalDateTime uniqueDateTime5 = LocalDateTime.of(9, 1, 1, 2, 1);

        Journal sameJournal1 = new Journal("non-unique journal 1", sameDateTime1);
        Journal sameJournal2 = new Journal("non-unique journal 2", sameDateTime2);
        Journal sameJournal3 = new Journal("non-unique journal 3", sameDateTime3);
        Journal uniqueJournal1 = new Journal("unique journal 1", uniqueDateTime1);
        Journal uniqueJournal2 = new Journal("unique journal 1", uniqueDateTime2);
        Journal uniqueJournal3 = new Journal("unique journal 1", uniqueDateTime3);
        Journal uniqueJournal4 = new Journal("unique journal 1", uniqueDateTime4);
        Journal uniqueJournal5 = new Journal("unique journal 1", uniqueDateTime5);

        // Adjustments
        sameJournal1.addAdjustment("adjustment 1", 100);
        sameJournal1.addAdjustment("adjustment 2", -100);
        sameJournal2.addAdjustment("adjustment 1", 100);
        sameJournal2.addAdjustment("adjustment 2", -100);
        sameJournal3.addAdjustment("adjustment 1", 100);
        sameJournal3.addAdjustment("adjustment 2", -100);

        uniqueJournal1.addAdjustment("adjustment 1.1", 100);
        uniqueJournal1.addAdjustment("adjustment 1.2", -100);
        uniqueJournal2.addAdjustment("adjustment 2.1", 100);
        uniqueJournal2.addAdjustment("adjustment 2.2", -100);
        uniqueJournal3.addAdjustment("adjustment 3.1", 100);
        uniqueJournal3.addAdjustment("adjustment 3.2", -100);
        uniqueJournal4.addAdjustment("adjustment 4.1", 100);
        uniqueJournal4.addAdjustment("adjustment 4.2", -100);
        uniqueJournal5.addAdjustment("adjustment 5.1", 100);
        uniqueJournal5.addAdjustment("adjustment 5.2", -100);

    }

    // Account Managers
    AccountsManager accountsManager1;
    AccountsManager accountsManager2;
    AccountsManager accountsManager3;

    // Accounts
    HashSet<String> identifiers1;
    HashSet<String> identifiers2;
    HashSet<String> identifiers3;

    // Journals
    Journal sameJournal1;
    Journal sameJournal2;
    Journal sameJournal3;
    Journal uniqueJournal1;
    Journal uniqueJournal2;
    Journal uniqueJournal3;
    Journal uniqueJournal4;
    Journal uniqueJournal5;

    @Test
    void testPostJournalToAccounts(){
        JournalPostingService journalPostingService = new JournalPostingService();
        // TODO Jounral does not go to accounts, its adjustments do > we just give the journal to the account manager and unpack it
        journalPostingService.PostJournalToAccounts(accountsManager1, identifiers1, sameJournal1);
        journalPostingService.PostJournalToAccounts(accountsManager1, identifiers1, sameJournal2);
        journalPostingService.PostJournalToAccounts(accountsManager1, identifiers1, sameJournal3);
    }

    @Test
    void testPostUnbalancedJournal(){
        sameJournal1.removeAdjustment("adjustment 1", 100);
        JournalPostingService journalPostingService = new JournalPostingService();
        journalPostingService.PostJournalToAccounts(accountsManager1, identifiers1, sameJournal1);

    }
}
