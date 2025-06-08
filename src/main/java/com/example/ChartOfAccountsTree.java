package com.example;

import java.io.File;
import java.util.*;

public class ChartOfAccountsTree implements Iterable<ChartOfAccountsTree.Node> {
    private final UUID ChartOfAccountsId;
    private String description;
    private final Node root;
    private final HashSet<Node> nodes;
    private static final Set<String> ALLOWED_ROOT_VALUES = Set.of(
            "assets", "liabilities", "equity", "revenue", "expenses"
    );

    public class Node implements Iterable<Node> {
        private String category;
        private String COA_code;
        private Node parent;
        private ArrayList<Node> children;

        public Node(String value, String COA_code) {
            if (!helperStringIsPositiveInteger(COA_code)){
                throw new IllegalArgumentException("COA Code '" + COA_code + "' is not a positive integer.");

            }
            this.category = value;
            this.COA_code = COA_code;
            this.parent = null;
            this.children = new ArrayList<>();
        }

        public ChartOfAccountsTree getChartOfAccounts(){
            return ChartOfAccountsTree.this;
        }

        public String getCategory(){
            return this.category;
        }

        public String getCOACode(){
            return this.COA_code;
        }

        public Node getParent(){
            return this.parent;
        }

        public Node getChild(Node child){
            for (Node node : this.children){
                if (node == child){
                    return node;
                }
            }
            throw new IllegalArgumentException("Child node not found.");
        }

        public ArrayList<Node> getChildren(){
            return new ArrayList<>(this.children);
        }

        public void editNode(String category, String COA_code){
            if (this == ChartOfAccountsTree.this.getRoot() ){

                throw new UnsupportedOperationException("Can't edit root node.");
            }
            Node node_shallow_copy = new Node(category, COA_code);
            if (helperCheckExists(node_shallow_copy)) {
                throw new IllegalArgumentException("The node '" + node_shallow_copy + "' already exists in the chart of accounts.");
            }
            node_shallow_copy.children = new ArrayList<>(this.children);
            node_shallow_copy.parent = this.parent;
            this.category = category;
            this.COA_code = COA_code;
        }

        public void addChild(Node child){
            if (helperCheckExists(child)) {
                throw new IllegalArgumentException("The value '" + child + "' already exists in the chart of accounts.");
            }
            if (child == null){
                // For warnings.
                throw new IllegalArgumentException("Node cannot be null.");
            }
            this.children.add(child);
            child.parent = this;
            ChartOfAccountsTree.this.nodes.add(child);
        }

        public void addParent(Node parent_node){
            if (this == ChartOfAccountsTree.this.getRoot() ){
                throw new UnsupportedOperationException("Can't add parent to root node.");
            }
        }

        /**
         *  The Chart of Accounts is represented by a Tree.
         *  Each branch represents an account category, and each node of this branch represents further detail of this category.
         *  The branches and nodes represent a hierarchical relationship.
         *  When we delete a node, we will be deleting the entire branch from the Chart of Accounts.
         */
        public void deleteNodeAndBranch(){
            if (!this.children.isEmpty()){
                List<Node> childrenCopy = new ArrayList<>(this.children);
                for (Node child : childrenCopy){
                    child.deleteNodeAndBranch();
                }
            }
            if (this.parent != null){
                Node parent = this.parent;
                parent.children.remove(this);
            }
            this.parent = null;
            ChartOfAccountsTree.this.nodes.remove(this);
        }

        @Override
        public boolean equals(Object o){
            if (this == o) return true;
            if (!(o instanceof Node)) return false;
            Node node = (Node) o;
            return this.category.equals(node.category) && this.COA_code.equals(node.COA_code);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.category, this.COA_code);
        }

        @Override
        public String toString(){
            return "(" + this.category + ", " + this.COA_code + ")";
        }

        @Override
        public Iterator<Node> iterator(){
            return new BreadthFirstTraversal(this);
        }

        public class BreadthFirstTraversal implements Iterator<Node> {
            private final Queue<Node> queue = new LinkedList<>();

            public BreadthFirstTraversal(Node node) {
                if (node != null) {
                    this.queue.add(node);
                }
            }
            @Override
            public boolean hasNext() {
                return !queue.isEmpty();
            }
            @Override
            public Node next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("ChartOfAccountsTree.Node has no next Node.");
                }
                Node current_node = queue.poll();
                this.queue.addAll(current_node.children);
                return current_node;
            }
        }

        private boolean helperCheckExists(Node node){
            return ChartOfAccountsTree.this.showNodes().contains(node);
        }

        private boolean helperStringIsPositiveInteger(String str){
            return str.matches("\\d+");
        }
    }

    public ChartOfAccountsTree(String root_value, String root_COA_code){
        if (ALLOWED_ROOT_VALUES.contains(root_value)){
            throw new IllegalArgumentException("Invalid root value: " + root_value + ". Root value must be one of the following: " + ALLOWED_ROOT_VALUES);
        }
        this.ChartOfAccountsId = UUID.randomUUID();
        this.root = new Node(root_value, root_COA_code);
        this.nodes = new HashSet<>();
        this.nodes.add(this.root);
    }

    public ChartOfAccountsTree(File json){
        // init COA from json file
        this.ChartOfAccountsId = UUID.randomUUID();
        this.root = null;
        this.nodes = new HashSet<>();
    }

    public ChartOfAccountsTree(ChartOfAccountsTree json){
        // init COA from an existing COA (copy) - possibly for when the root is wrong and we need to construct a new one
        this.ChartOfAccountsId = UUID.randomUUID();
        this.root = null;
        this.nodes = new HashSet<>();
    }

    public Node getRoot(){
        return this.root;
    }

    public Set<String> getAllowedRootValues(){
        return ALLOWED_ROOT_VALUES;
    }

    public HashSet<Node> showNodes(){
        return new HashSet<>(this.nodes);
    }

    @Override
    public Iterator<Node> iterator() {
        return root.iterator();
    }
}
