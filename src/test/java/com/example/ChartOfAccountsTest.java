package com.example;

public class ChartOfAccountsTest {
    public void test(){}


    public void testInitialisationFailure(){
        try {
            ChartOfAccounts not_root_value = new ChartOfAccounts("non-accountancy category");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Root node must have no parent.", e.getMessage());
        }
    }
}
