package it.dibek.charreader;

public class BinaryTree<T extends Comparable> {

    Node root;


    public void addObject(T value) {
        addNode(createRootNode(value),value.hashCode(),value);
    }


    public void addNode(Node node, int key, T value) {

         if (this.root == null)
        {
            this.root = new Node(key,"Root",value);
            node = this.root;
        }

        if (value.compareTo(node.value) > 0) {
      if (node.leftChild != null) {
        addNode(node.leftChild, key,value);
      } else {
        System.out.println("  Inserted " + key + " to left of "
            + node.key);
        node.leftChild = new Node(key, "leftOf" + node.key,value);
      }
    } else if (value.compareTo(node.value) < 0) {
      if (node.rightChild != null) {
        addNode(node.rightChild, key,value);
      } else {
        System.out.println("  Inserted "  + key + " to right of "
            + node.key);
        node.rightChild = new Node(key, "rightOf" + node.key,value);
      }
    }
  }

    private Node createRootNode(T value) {
        Node node = this.root;
        if (this.root == null)
        {
            this.root = new Node(-1,"Root",value);
            node = this.root;
        }
        return node;
    }


    // All nodes are visited in ascending order
    // Recursion is used to go to one node and
    // then go to its child nodes and so forth

    public String inOrderTraverseTree(Node focusNode, String nodeVisited) {



        if (focusNode != null) {

            // Traverse the left node

            nodeVisited = inOrderTraverseTree(focusNode.leftChild,nodeVisited);

            // Visit the currently focused on node

            System.out.println(focusNode);

            nodeVisited+= focusNode;

            // Traverse the right node

            nodeVisited = inOrderTraverseTree(focusNode.rightChild,nodeVisited);

        }

        return nodeVisited;
    }
    // All nodes are visited in ascending order
    // Recursion is used to go to one node and
    // then go to its child nodes and so forth

    public void getIndexSorted(Node<T> focusNode, T[] sortArray, int countItem, String nodeVisited) {





        if (focusNode != null) {

            // Traverse the right node
            getIndexSorted(focusNode.rightChild ,sortArray,countItem,nodeVisited);



            // Visit the currently focused on node

            System.out.println(focusNode);

            sortArray[countItem--] = focusNode.value;

            // Traverse the left node

            getIndexSorted(focusNode.leftChild,sortArray,countItem,nodeVisited);


        }



    }
    public String preorderTraverseTree(Node focusNode) {

        String nodeVisited = "";

        if (focusNode != null) {

            System.out.println(focusNode);
            nodeVisited+= focusNode;

            nodeVisited+= preorderTraverseTree(focusNode.leftChild);
            nodeVisited+= preorderTraverseTree(focusNode.rightChild);

        }
        return nodeVisited;
    }

    public String postOrderTraverseTree(Node focusNode) {

        String nodeVisited = "";

        if (focusNode != null) {

            nodeVisited+= focusNode;

            postOrderTraverseTree(focusNode.leftChild);
            postOrderTraverseTree(focusNode.rightChild);

            System.out.println(focusNode);


        }
        return nodeVisited;
    }

    public Node findNode(int key) {

        // Start at the top of the tree

        Node focusNode = root;

        // While we haven't found the Node
        // keep looking

        while (focusNode.key != key) {

            // If we should search to the left

            if (key < focusNode.key) {

                // Shift the focus Node to the left child

                focusNode = focusNode.leftChild;

            } else {

                // Shift the focus Node to the right child

                focusNode = focusNode.rightChild;

            }

            // The node wasn't found

            if (focusNode == null)
                return null;

        }

        return focusNode;

    }

    public static void main(String[] args) {



    }
}

class Node<T extends Comparable> {

    int key;
    String name;
    T value;

    Node<T> leftChild;
    Node<T> rightChild;


    Node(int key, String name,T value) {

        this.key = key;
        this.name = name;
        this.value = value;

    }

    public String toString() {

        return " -> " + value ;

    }

}