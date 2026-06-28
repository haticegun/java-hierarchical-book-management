package datastructureproject;

public class Book {

    static class Node {
        String title;
        Node parent;
        Node child;
        Node next;

        Node(String title) {
            this.title = title;
            this.parent = null;
            this.child = null;
            this.next = null;
        }
    }

    private Node root;
    private boolean silentLoad;

    public Book() {
        root = null;
        silentLoad = false;
    }

    public void loadSampleData() {
        silentLoad = true;

        addTitle("1", "Data Structures and Algorithms");
        addTitle("1.1", "What is a Data Structure");
        addTitle("1.2", "What is an Algorithm");
        addTitle("1.2.1", "Pseudocode");
        addTitle("1.2.2", "Flowchart");
        addTitle("1.2.3", "Algorithm Analysis");
        addTitle("1.2.3.1", "Best Case Analysis");
        addTitle("1.2.3.2", "Average Case Analysis");
        addTitle("1.2.3.3", "Worst Case Analysis");

        addTitle("2", "Sorting");
        addTitle("2.1", "What is Sorting");
        addTitle("2.2", "Sorting Algorithms");

        addTitle("2.2.1", "Insertion Sort");
        addTitle("2.2.1.1", "Pseudocode");
        addTitle("2.2.1.1.1", "C Code");
        addTitle("2.2.1.1.2", "Java Code");
        addTitle("2.2.1.1.2.1", "Insert");
        addTitle("2.2.1.1.2.2", "Delete");
        addTitle("2.2.1.1.2.3", "Traverse");

        addTitle("2.2.1.2", "Examples");
        addTitle("2.2.1.2.1", "Example 1");
        addTitle("2.2.1.2.2", "Example 2");

        addTitle("2.2.1.3", "Algorithm Analysis");

        addTitle("2.2.2", "Selection Sort");
        addTitle("2.2.3", "Bubble Sort");

        addTitle("3", "Searching");

        addTitle("4", "Trees");
        addTitle("4.1", "What is a Tree");

        silentLoad = false;
    }

    public void addTitle(String position, String title) {

        position = Utilities.trimText(position);
        title = Utilities.trimText(title);

        int[] levelPath = Utilities.parseLevels(position);
        int depth = Utilities.filledLength(levelPath);

        if (depth <= 0) {
            if (!silentLoad) {
                Utilities.print("No hierarchy level information was found for the title to be added.");
            }
            return;
        }

        Node parentNode = null;

        for (int level = 0; level < depth - 1; level++) {

            int targetOrder = levelPath[level];
            Node searchList = (parentNode == null) ? root : parentNode.child;

            Node foundNode = findNodeByOrder(searchList, targetOrder);

            if (foundNode == null) {
                if (!silentLoad) {
                    String missingPrefix = Utilities.buildPrefix(levelPath, level);
                    Utilities.print("Cannot add the title because hierarchy level " + missingPrefix + " does not exist.");
                }
                return;
            }

            parentNode = foundNode;
        }

        int orderToInsert = levelPath[depth - 1];
        Node targetList = (parentNode == null) ? root : parentNode.child;

        if (titleExists(targetList, title)) {
            if (!silentLoad) {
                Utilities.print("A title named \"" + title + "\" already exists at this level.");
            }
            return;
        }

        Node newNode = new Node(title);
        newNode.parent = parentNode;

        if (targetList == null) {
            if (parentNode == null) root = newNode;
            else parentNode.child = newNode;

            if (!silentLoad) {
                Utilities.print("No title existed at this level, so the new title was added as the first item.");
            }
            return;
        }

        if (orderToInsert <= 1) {
            newNode.next = targetList;

            if (parentNode == null) root = newNode;
            else parentNode.child = newNode;

            if (!silentLoad) Utilities.print("Added.");
            return;
        }

        int currentCount = getListLength(targetList);

        if (orderToInsert > currentCount) {
            Node currentNode = targetList;
            while (currentNode.next != null) currentNode = currentNode.next;
            currentNode.next = newNode;

            if (!silentLoad) {
                Utilities.print("The requested position does not exist at this level, so the title was added at the end.");
            }
            return;
        }

        Node previousNode = findNodeByOrder(targetList, orderToInsert - 1);
        newNode.next = previousNode.next;
        previousNode.next = newNode;

        if (!silentLoad) Utilities.print("Added.");
    }

    public void printHierarchy() {
        printTopLevel(root);
    }

    private void printTopLevel(Node start) {
        Node currentNode = start;
        int order = 1;
        boolean firstItem = true;

        while (currentNode != null) {

            if (!firstItem) {
                Utilities.print("");
            }
            firstItem = false;

            String number = order + ".";
            Utilities.print(number + " " + currentNode.title);

            if (currentNode.child != null) {
                printChildren(currentNode.child, number, 2);
            }

            currentNode = currentNode.next;
            order++;
        }
    }

    private void printChildren(Node start, String previousNumber, int level) {
        Node currentNode = start;
        int order = 1;

        while (currentNode != null) {
            String number = previousNumber + order + ".";
            String indent = Utilities.createIndent((level - 1) * 4);
            Utilities.print(indent + number + " " + currentNode.title);

            if (currentNode.child != null) {
                printChildren(currentNode.child, number, level + 1);
            }

            currentNode = currentNode.next;
            order++;
        }
    }

    private Node findNodeByOrder(Node start, int order) {
        if (start == null || order <= 0) return null;

        Node currentNode = start;
        int counter = 1;

        while (currentNode != null && counter < order) {
            currentNode = currentNode.next;
            counter++;
        }
        return currentNode;
    }

    private boolean titleExists(Node start, String title) {
        Node currentNode = start;

        while (currentNode != null) {
            if (Utilities.equalsText(currentNode.title, title)) return true;
            currentNode = currentNode.next;
        }
        return false;
    }

    private int getListLength(Node start) {
        int counter = 0;
        Node currentNode = start;

        while (currentNode != null) {
            counter++;
            currentNode = currentNode.next;
        }
        return counter;
    }
}
