package com.example.domain.services;

import com.example.domain.model.accountmanager.AccountCategory;
import com.example.domain.model.accountmanager.AccountsManager;
import com.example.domain.model.accountmanager.ChartOfAccountsTree;
import com.example.domain.model.journal.Journal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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
        ChartOfAccountsTree coa1 = new ChartOfAccountsTree("assets", "0001");
        ChartOfAccountsTree coa2 = new ChartOfAccountsTree("liabilities", "0001");
        ChartOfAccountsTree coa3 = new ChartOfAccountsTree("equity", "0001");
        // TODO: COA add and remove node


        // AccountManagers
        AccountsManager accountsManager1 = new AccountsManager("account manager 1", periodStart1, periodLength1, coa1);
        AccountsManager accountsManager2 = new AccountsManager("account manager 2", periodStart2, periodLength2, coa2);
        AccountsManager accountsManager3 = new AccountsManager("account manager 3", periodStart3, periodLength3, coa3);

        // Add Accounts
        //descriptors
        ArrayList<String> descriptors1 = new ArrayList<>();
        descriptors1.add("account description 1");
        descriptors1.add("account code 1");

        ArrayList<String> descriptors2 = new ArrayList<>();
        descriptors2.add("account description 2");
        descriptors2.add("account code 2");

        ArrayList<String> descriptors3 = new ArrayList<>();
        descriptors2.add("account description 3");
        descriptors2.add("account code 3");

        // (temp) Category TODO: REMOVE
        AccountCategory accountCategory1 = AccountCategory.ASSET;
        AccountCategory accountCategory2 = AccountCategory.LIABILITY;
        AccountCategory accountCategory3 = AccountCategory.EQUITY;


        accountsManager1.addNewAccount(descriptors1, accountCategory1, "assets");
        accountsManager1.addNewAccount(descriptors2, accountCategory2, "liabilities");
        accountsManager1.addNewAccount(descriptors3, accountCategory3, "equity");
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

    }

    AccountsManager accountsManager1;
    AccountsManager accountsManager2;
    AccountsManager accountsManager3;

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
        journalPostingService.PostJournalToAccounts(accountsManager1, sameJournal1);
    }
}
