package com.example;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.HashSet;

public class ChartOfAccountsTest extends TestCase {

    public void testCOAInitialisationFailure(){
        try {
            ChartOfAccountsTree not_root_value = new ChartOfAccountsTree("non-accountancy category", "1111");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue("Error message should state that the node value is invalid", e.getMessage().contains("Invalid root value: "));
        }
    }

    public void testShowNodesDefensiveCopy(){
        ChartOfAccountsTree COA = new ChartOfAccountsTree("equity", "2222");
        HashSet<ChartOfAccountsTree.Node> content = COA.showNodes();

        content.remove(COA.getRoot());
        assertTrue(content.isEmpty());

        HashSet<ChartOfAccountsTree.Node> same_content = COA.showNodes();
        HashSet<ChartOfAccountsTree.Node> expected_content = new HashSet<>();
        expected_content.add(COA.getRoot());
        assertEquals(expected_content, same_content);
    }

    public void testNodeInitialisationFailure(){
        ChartOfAccountsTree COA = new ChartOfAccountsTree("expenses", "1010");
        // TODO: Are codes always less than 5 characters long?
        HashSet<String> wrong_codes = new HashSet<>(Arrays.asList("-1", "abc"));
        for (String wrong_COA_code : wrong_codes){
            try {
                ChartOfAccountsTree.Node node = COA.new Node("value", wrong_COA_code);
                fail("Expected IllegalArgumentException");
            } catch (IllegalArgumentException e) {
                assertEquals("COA Code '" + wrong_COA_code + "' is not a positive integer.", e.getMessage());
            }
        }
    }

    public void testNodeEquals(){
        ChartOfAccountsTree COA = new ChartOfAccountsTree("equity", "9999");
        ChartOfAccountsTree.Node node1 = COA.new Node("value", "1");
        ChartOfAccountsTree.Node node2 = COA.new Node("value", "1");
        assertEquals(node1, node2);
    }

    public void testTraversal(){
        ChartOfAccountsTree COA = new ChartOfAccountsTree("liabilities", "9999");
        ChartOfAccountsTree.Node node1_1 = COA.new Node("one-one", "11");
        ChartOfAccountsTree.Node node1_2 = COA.new Node("one-two", "12");
        ChartOfAccountsTree.Node node2_1 = COA.new Node("two-one", "21");
        ChartOfAccountsTree.Node node2_2 = COA.new Node("two-two", "22");
        ChartOfAccountsTree.Node node2_3 = COA.new Node("two-three", "23");
        ChartOfAccountsTree.Node node2_4 = COA.new Node("two-four", "24");
        ChartOfAccountsTree.Node node3_1 = COA.new Node("three-one", "31");
        ChartOfAccountsTree.Node node4_1 = COA.new Node("four-one", "41");

        ChartOfAccountsTree.Node root = COA.getRoot();
        // h = 1
        root.addChild(node1_1);
        root.addChild(node1_2);
        // h = 2
        node1_1.addChild(node2_1);
        node1_1.addChild(node2_2);
        node1_2.addChild(node2_3);
        node1_2.addChild(node2_4);
        // h = 3
        node2_1.addChild(node3_1);
        // h = 4
        node3_1.addChild(node4_1);

        ChartOfAccountsTree.Node[] expected_order = {root, node1_1, node1_2, node2_1, node2_2, node2_3, node2_4, node3_1, node4_1};
        int i = 0;
        // Iterate COA Tree
        for (ChartOfAccountsTree.Node node : COA.getRoot()){
            assertEquals(expected_order[i], node);
            i += 1;
        }

        ChartOfAccountsTree.Node[] expected_branch_order = {node1_2, node2_3, node2_4};
        int j = 0;
        // Iterate a branch
        for (ChartOfAccountsTree.Node node : node1_2){
            assertEquals(expected_branch_order[j], node);
            j += 1;
        }
    }

    public void testAddChildNode(){
        ChartOfAccountsTree COA = new ChartOfAccountsTree("equity", "36");
        ChartOfAccountsTree.Node root_node = COA.getRoot();

        ChartOfAccountsTree.Node node1 = COA.new Node("value1", "1000");
        root_node.addChild(node1);

        assertEquals(1, root_node.getChildren().size());

        ChartOfAccountsTree.Node child_node = root_node.getChild(node1);
        assertSame(child_node, node1);

        try {
            root_node.addChild(node1);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The value '" + node1.getCategory() + "' already exists in the chart of accounts.", e.getMessage());
        }
    }

    public void testEditNodeValue() {
        ChartOfAccountsTree COA = new ChartOfAccountsTree("equity", "100");
        ChartOfAccountsTree.Node root_node = COA.getRoot();

        ChartOfAccountsTree.Node node1 = COA.new Node("value1", "1000");
        ChartOfAccountsTree.Node node2 = COA.new Node("value2", "2000");
        root_node.addChild(node1);
        root_node.addChild(node2);

        node1.editNode("new category", "a new node value");
        String new_category = "value2";
        String new_COA_code = "0001";
        try {
            node1.editNode(new_category, new_COA_code);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue("Error message should state that the node is invalid as it already exists", e.getMessage().contains("already exists in the chart of accounts."));
        }
    }
}

