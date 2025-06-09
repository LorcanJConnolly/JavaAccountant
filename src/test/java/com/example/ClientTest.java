package com.example;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.example.domain.accountmanager.AccountsManager;
import com.example.domain.client.Client;

public class ClientTest {

    @Test
    public void testInitialisation1(){
        Client test_client = new Client("test_client");
        assertEquals("test_client", test_client.name);
        assertTrue(test_client.getAccountManagerIds().isEmpty());
    }

    @Test
    public void testInitialisation2(){
        HashSet<String> accountsManagerIds = new HashSet<>();
        accountsManagerIds.add("id1");
        accountsManagerIds.add("id2");
        Client client = new Client("test_client", accountsManagerIds);
        assertEquals("test_client", client.name);
        assertFalse(client.getAccountManagerIds().isEmpty());
    }

    @Test
    public void testAddAccountManagerId(){

        // Success
        Client client = new Client("test_client");
        client.addAccountManagerId("id");
        assertFalse(client.getAccountManagerIds().isEmpty());

        // Failure
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            client.addAccountManagerId("id");
        });
    }

    @Test
    public void testGetAccountManagerIds(){
        HashSet<String> accountsManagerIds = new HashSet<>();
        accountsManagerIds.add("id1");
        accountsManagerIds.add("id2");
        Client client = new Client("test_client", accountsManagerIds);
        assertEquals("test_client", client.name);
        // See testInitialisation2
        assertEquals(accountsManagerIds, client.getAccountManagerIds());
    }


    @Test
    public void testRemoveAccountManagerId(){

        HashSet<AccountsManager> jobs = new HashSet<>();
        HashSet<String> accountsManagerIds = new HashSet<>();
        accountsManagerIds.add("id1");
        accountsManagerIds.add("id2");
        Client client = new Client("test_client", accountsManagerIds);
        assertEquals("test_client", client.name);
        assertEquals(accountsManagerIds, client.getAccountManagerIds());
        client.removeAccountManagerIds("id1");
        assertEquals(1, client.getAccountManagerIds().size());
        assertTrue(client.getAccountManagerIds().contains("id2"));
    }

}
