package com.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import junit.framework.TestCase;

public class ClientTest extends TestCase {
    // JUnit 3 does not use @Test annotation
    public void testInitialisationNameOnly(){
        Client test_client = new Client("test_client");
        assertEquals("test_client", test_client.name);
        assertTrue(test_client.assignments.isEmpty());
    }

    public void testInitialisationNameAndAssignment(){
        Job test_job1 = new Job(LocalDate.now());
        Job test_job2 = new Job(LocalDate.now().plusMonths(13));
        ArrayList<Job> jobs = new ArrayList<>(Arrays.asList(test_job1, test_job2));

        Client test_client = new Client("test_client", jobs);
        assertEquals("test_client", test_client.name);
        assertEquals(jobs, test_client.assignments);
    }

    public void testCreateAssignment(){
        Client test_client = new Client("test_client");
        assertEquals(0,  test_client.assignments.size());
        test_client.createAssignment(LocalDate.now());
        assertEquals(1,  test_client.assignments.size());
    }

}
