import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of a Map using a binary search tree.
 *
 * @param <K>
 * @param <V>
 */
public class MyTreeMap<K, V> implements Map<K, V> {

    private int size = 0;
    private Node root = null;

    /**
     * Represents a node in the tree.
     */
    protected class Node {
        public K key;
        public V value;
        public Node left = null;
        public Node right = null;

        /**
         * @param key
         * @param value
         * @param left
         * @param right
         */
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * Removes all key-value mappings from this binary search tree.
     * The size of the tree is set to zero, and the root node is set to null.
     */
    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Checks if the binary search tree contains a mapping for the specified key.
     *
     * @param target the key whose presence in this tree is to be tested.
     * @return {@code true} if this tree contains a mapping for the specified key,
     * {@code false} otherwise.
     */
    @Override
    public boolean containsKey(Object target) {
        return findNode(target) != null;
    }

    /**
     * Returns the entry that contains the target key, or null if there is none.
     *
     * @param target the key value the method will search for in the tree
     */
    private Node findNode(Object target) {
        Node returVal = null;
        Node pointer = root;

        // some implementations can handle null as a key, but not this one
        if (target == null) {
            throw new IllegalArgumentException();
        }

        // something to make the compiler happy
        @SuppressWarnings("unchecked")
        Comparable<? super K> k = (Comparable<? super K>) target;

        //uses the tree's stepping logic to descend through the levels until it finds the passed value, then kills the loop when the value is found or tree is exhausted.
        while (returVal == null && pointer != null) {
            if (k == pointer.key)
                returVal = pointer;
            else
                pointer = k.compareTo(pointer.key) < 0 ? pointer.left : pointer.right;
        }

        return returVal;
    }

    /**
     * Compares two keys or two values, handling null correctly.
     *
     * @param target
     * @param obj
     * @return
     */
    private boolean equals(Object target, Object obj) {
        if (target == null) {
            return obj == null;
        }
        return target.equals(obj);
    }


    @Override
    public boolean containsValue(Object target) {
        return containsValueHelper(root, target);
    }

    private boolean containsValueHelper(Node node, Object target) {
        if (target.equals(node.value))
            return true;

        if (node.left != null && containsValueHelper(node.left, target))
            return true;

        //this is not legible but the IDE insists on it
        return node.right != null && containsValueHelper(node.right, target);
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V get(Object key) {
        Node node = findNode(key);
        if (node == null) {
            return null;
        }
        return node.value;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new LinkedHashSet<K>();
        // TODO: FILL THIS IN!
        return set;
    }

    @Override
    public V put(K key, V value) {
        if (key == null) {
            throw new NullPointerException();
        }
        if (root == null) {
            root = new Node(key, value);
            size++;
            return null;
        }
        return putHelper(root, key, value);
    }

    private V putHelper(Node node, K key, V value) {
        V returnVal = null;
        Comparable<? super K> k = (Comparable<? super K>) key;

// If the key is found, update the value and return the old value
        if (k.compareTo(node.key) == 0) {
            returnVal = node.value;
            node.value = value;
            return returnVal;
        }

        // Decide whether to go left or right in the tree
        Node next = k.compareTo(node.key) < 0 ? node.left : node.right;

        // If the child is null, create a new node and set it as the left or right child
        if (next == null) {
            if (k.compareTo(node.key) < 0)
                node.left = new Node(key, value);
            else
                node.right = new Node(key, value);

            size++;
        } else
            // If the child is not null, recursively call putHelper on the child
            putHelper(next, key, value);

        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public V remove(Object key) {
        // OPTIONAL TODO: FILL THIS IN!
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Collection<V> values() {
        Set<V> set = new HashSet<V>();
        Deque<Node> stack = new LinkedList<Node>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            if (node == null) continue;
            set.add(node.value);
            stack.push(node.left);
            stack.push(node.right);
        }
        return set;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Map<String, Integer> map = new MyTreeMap<String, Integer>();
        map.put("Word1", 1);
        map.put("Word2", 2);
        Integer value = map.get("Word1");
        System.out.println(value);

        for (String key : map.keySet()) {
            System.out.println(key + ", " + map.get(key));
        }
    }

    /**
     * Makes a node.
     * <p>
     * This is only here for testing purposes.  Should not be used otherwise.
     *
     * @param key
     * @param value
     * @return
     */
    public MyTreeMap<K, V>.Node makeNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Sets the instance variables.
     * <p>
     * This is only here for testing purposes.  Should not be used otherwise.
     *
     * @param node
     * @param size
     */
    public void setTree(Node node, int size) {
        this.root = node;
        this.size = size;
    }

    /**
     * Returns the height of the tree.
     * <p>
     * This is only here for testing purposes.  Should not be used otherwise.
     *
     * @return
     */
    public int height() {
        return heightHelper(root);
    }

    private int heightHelper(Node node) {
        if (node == null) {
            return 0;
        }
        int left = heightHelper(node.left);
        int right = heightHelper(node.right);
        return Math.max(left, right) + 1;
    }
}