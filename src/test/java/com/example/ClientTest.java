package com.example;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.UUID;

import junit.framework.TestCase;

public class ClientTest extends TestCase {
    public void testInitialisation1(){
        Client test_client = new Client("test_client");
        assertEquals("test_client", test_client.name);
        assertTrue(test_client.getJobs().isEmpty());
    }

    public void testInitialisation2(){
        HashSet<AccountsManager> jobs = new HashSet<>();
        AccountsManager job1 = new AccountsManager(LocalDate.now(), Period.ofMonths(6), "Test Job 1");
        AccountsManager job2 = new AccountsManager(LocalDate.now(), Period.ofMonths(6), "Test Job 2");
        jobs.add(job1);
        jobs.add(job2);
        Client client = new Client("test_client", jobs);
        assertEquals("test_client", client.name);
        assertEquals(jobs, client.getJobs());
    }

    public void testCreateJob(){
        Client client = new Client("test_client");
        AccountsManager job1 = new AccountsManager(LocalDate.now(), "Test Job 1");
        client.newJob(job1);
        assertFalse(client.getJobs().isEmpty());
    }

    public void testCreateTheSameJob(){
        // FIXME
        Client client = new Client("test_client");
        AccountsManager job1 = new AccountsManager(LocalDate.now(), "Test Job 1");
        client.newJob(job1);
        // Junit 3.8.1 version of assertThrows
        try {
            client.newJob(job1);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Job already exists in Client. Change attribute(s) of Job.", e.getMessage());
        }

    }

    public void testGetJobs(){
        HashSet<AccountsManager> jobs = new HashSet<>();
        AccountsManager job1 = new AccountsManager(LocalDate.now(), Period.ofMonths(6), "Test Job 1");
        AccountsManager job2 = new AccountsManager(LocalDate.now(), Period.ofMonths(6), "Test Job 2");
        jobs.add(job1);
        jobs.add(job2);
        Client client = new Client("test_client", jobs);
        assertEquals("test_client", client.name);
        assertEquals(jobs, client.getJobs());
    }

    public void testDeleteJob(){
        // TODO: possibly change "job" to AccountManager in some places
        HashSet<AccountsManager> jobs = new HashSet<>();
        AccountsManager job1 = new AccountsManager(LocalDate.now(), Period.ofMonths(6), "Test Job 1");
        AccountsManager job2 = new AccountsManager(LocalDate.now(), Period.ofMonths(6), "Test Job 2");
        jobs.add(job1);
        jobs.add(job2);
        Client client = new Client("test_client", jobs);
        assertEquals("test_client", client.name);
        assertEquals(jobs, client.getJobs());
        client.deleteJob(job1);
        jobs.remove(job1);
        assertEquals(jobs, client.getJobs());
    }

}
