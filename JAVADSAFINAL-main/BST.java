import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BST<T> {
    private class Node {
        T data;
        Node left, right;
        Node(T data) { this.data = data; }
    }

    private Node root;
    private Comparator<T> comparator;

    public BST(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void insert(T data) {
        root = insertRec(root, data);
    }

    private Node insertRec(Node node, T data) {
        if (node == null) return new Node(data);
        int cmp = comparator.compare(data, node.data);
        if (cmp < 0) node.left = insertRec(node.left, data);
        else if (cmp > 0) node.right = insertRec(node.right, data);
        return node;
    }

    public T search(T data) {
        Node node = root;
        while (node != null) {
            int cmp = comparator.compare(data, node.data);
            if (cmp == 0) return node.data;
            else if (cmp < 0) node = node.left;
            else node = node.right;
        }
        return null;
    }

    // Return all elements in sorted order as a list
    public List<T> inOrder() {
        List<T> result = new ArrayList<T>();
        inOrderRec(root, result);
        return result;
    }

    private void inOrderRec(Node node, List<T> result) {
        if (node != null) {
            inOrderRec(node.left, result);
            result.add(node.data);
            inOrderRec(node.right, result);
        }
    }
}
