package com.example;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class ChartOfAccounts {
    // Tree
    private final Node root;

    public ChartOfAccounts(Node root){
        if (root.parent != null){
            throw new IllegalArgumentException("Root node must have no parent.");
        }
        String[] allowed_root_values = {"assets", "liabilities", "equity", "revenue", "expenses"};
        if (!Arrays.asList(allowed_root_values).contains(root.getValue())){
            throw new IllegalArgumentException("Invalid root value: " + root.getValue() + ". Root node value must be one of the following: " + Arrays.toString(allowed_root_values));
        }
        this.root = root;
    }

    public ChartOfAccounts(File json){

    }

    public class Node{
        private final String value;
        private Node parent;
        private ArrayList<Node> children;

        public Node(String value){
            this.value = value;
            this.parent = null;
            this.children = new ArrayList<>();
        }

        public String getValue(){
            return this.value;
        }

        public Node getParent(){
            return this.parent;
        }

        public void getChild(){}

        public void getChildren(){}

        public void editValue(){}

        public void addChild(){}

        public void newParent(){}

        public void removeParent(){}

        public void removeChild(){}

        public void removeChildren(){}

        public void deleteNode(){}
    }

    public Node getRoot(){
        return this.root;
    }
}
