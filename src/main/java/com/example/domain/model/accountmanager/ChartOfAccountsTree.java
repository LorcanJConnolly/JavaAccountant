package com.example.domain.model.accountmanager;

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
        private String code;
        private Node parent;
        private final ArrayList<Node> children;

        public Node(String category, String code) {
            if (category == null  || code == null){
                throw new IllegalArgumentException("Node initialised with null attributes.");
            }
            if (!helperStringIsPositiveInteger(code)){
                throw new IllegalArgumentException("COA Code '" + code + "' is not a positive integer.");

            }
            this.category = category;
            this.code = code;
            this.parent = null;
            this.children = new ArrayList<>();
        }

        public String getCategory(){
            return this.category;
        }

        public String getCode(){
            return this.code;
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
            this.category = category;
            this.code = COA_code;
        }

        public void addChild(Node child){
            this.children.add(child);
            child.parent = this;
            ChartOfAccountsTree.this.nodes.add(child);
        }

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
            if (!(o instanceof Node node)) return false;
            return this.category.equals(node.category) && this.code.equals(node.code);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.category, this.code);
        }

        @Override
        public String toString(){
            return "(" + this.category + ", " + this.code + ")";
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

        private boolean helperStringIsPositiveInteger(String str){
            return str.matches("\\d+");
        }
    }

    public ChartOfAccountsTree(String root_value, String root_COA_code, String description){
        if (ALLOWED_ROOT_VALUES.contains(root_value)){
            throw new IllegalArgumentException("Invalid root value: " + root_value + ". Root value must be one of the following: " + ALLOWED_ROOT_VALUES);
        }
        this.ChartOfAccountsId = UUID.randomUUID();
        this.description = description;
        this.root = new Node(root_value, root_COA_code);
        this.nodes = new HashSet<>();
        this.nodes.add(this.root);
    }

    public UUID getChartOfAccountsId(){
        return this.ChartOfAccountsId;
    }

    public String getDescription(){
        return this.description;
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

    public void newDescription(String description){
        this.description = description;
    }

    public Node findNode(String category, String code){
        for (Node node: this.showNodes()){
            if (node.category.equals(category) && node.code.equals(code)){
                return node;
            }
        }
        return null;
    }

    public void addNode(Node parent, String category, String COA_code){
        if (parent == null){
            throw new IllegalArgumentException("Parent node cannot be null. Root node is a property of the chart of accounts tree and cannot be altered.");
        }
        Node node = new Node(category, COA_code);
        if (this.nodes.contains(node)){
            throw new IllegalArgumentException("The node '" + node + "' already exists in the chart of accounts tree.");
        }
        parent.addChild(node);
    }

    public void removeNode(Node node){
        if (this.root == node){
            throw new IllegalArgumentException("Node cannot be root. Root node is a property of the chart of accounts tree and cannot be altered.");
        }
        if (!this.nodes.contains(node)){
            throw new IllegalArgumentException("The node '" + node + "' does not exist in the chart of accounts tree.");
        }
        node.deleteNodeAndBranch();
    }

    public void editNode(Node node){
        if (this.root == node){
            throw new IllegalArgumentException("Node cannot be root. Root node is a property of the chart of accounts tree and cannot be altered.");
        }
        if (!this.nodes.contains(node)){
            throw new IllegalArgumentException("The node '" + node + "' does not exist in the chart of accounts tree.");
        }
        this.editNode(node);

    }

    @Override
    public Iterator<Node> iterator() {
        return root.iterator();
    }
}
