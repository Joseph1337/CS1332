import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;


/**
 * Your implementation of a BST.
 *
 * @author Joseph Guo
 * @version 1.0
 * @userid jguo345
 * @GTID 903437631
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot initialize null data.");
        }
        for (T i: data) {
            if (i == null) {
                throw new IllegalArgumentException("Cannot initialize null data.");
            }
            this.add(i);
        }

    }

    /**
     * Adds the data to the tree.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot initialize null data.");
        }
        if (root == null) {
            root = new BSTNode<T>(data);
            size++;
        } else {
            add(data, root);
        }
    }

    /***
     * Helper method for add()
     * @param data the data to add
     * @param current the current node that is being traversed on in the BST
     */
    private void add(T data, BSTNode<T> current) {
        int compare = data.compareTo(current.getData());
        BSTNode<T> newNode = new BSTNode<>(data);
        if (compare == 0) {
            return;
        }
        if (compare >= 1) {
            if (current.getRight() == null) {
                current.setRight(newNode);
                size++;
            } else {
                add(data, current.getRight());
            }
        } else if (compare <= -1) {
            if (current.getLeft() == null) {
                current.setLeft(newNode);
                size++;
            } else {
                add(data, current.getLeft());
            }
        }
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove 'null' data.");
        }
        if (!contains(data)) {
            throw new NoSuchElementException("Element does not exist in BST.");
        }
        T removed = get(data);
        root = removeNode(data, root);
        size--;
        return removed;

    }

    /***
     * Helper method for remove.
     * Traverses the BST in search for node matching the given data and removes it from the tree.
     * @param data the data to remove
     * @param current the current node being traversed in the BST
     * @return the modified BST with removed data (if found)
     */
    private BSTNode<T> removeNode(T data, BSTNode<T> current) {
        if (current == null) {
            return null;
        }
        int compare = data.compareTo(current.getData());
        if (compare <= -1) {
            current.setLeft(removeNode(data, current.getLeft()));
        } else if (compare >= 1) {
            current.setRight(removeNode(data, current.getRight()));
        } else {
            if (current.getLeft() == null) {
                return current.getRight();
            } else if (current.getRight() == null) {
                return current.getLeft();
            } else {
                current.setData(getSuccessor(current.getRight()));
                current.setRight(removeNode(current.getData(), current.getRight()));
            }
        }
        return current;
    }

    /***
     * Finds the successor for the node that is being called on.
     * @param node the child node of the parent node that we are finding the successor for
     * @return successor for parent node
     */
    private T getSuccessor(BSTNode<T> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node.getData();
    }


    /**
     * Returns the data from the tree matching the given parameter.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (root == null) {
            throw new NoSuchElementException("No element available for search.");
        }
        T i = search(data, root);
        if (i != null) {
            return i;
        } else {
            throw new NoSuchElementException("Element does not exist in BST.");
        }
    }

    /***
     * Traverses the BST in search for node that matches the given data
     * @param data the data to search for
     * @param current current node being accessed in tree traversal
     * @return if data is in the tree, returns the node that contains it. Else, returns 'null'.
     */
    private T search(T data, BSTNode<T> current) {
        int compare = data.compareTo(current.getData());
        if (compare >= 1) {
            if (current.getRight() == null) {
                return null;
            } else {
                return search(data, current.getRight());
            }
        } else if (compare <= -1) {
            if (current.getLeft() == null) {
                return null;
            } else {
                return search(data, current.getLeft());
            }
        } else {
            return current.getData();
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        if (root == null) {
            return false;
        }
        return (search(data, root) != null);

    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        ArrayList<T> list = new ArrayList<T>();
        return preorder(root, list);
    }

    /***
     * Helper method for preorder()
     * @param node current node being accessed in tree traversal
     * @param list list to add each node in once found
     * @return list of nodes obtained through pre-order traversal
     */
    private List<T> preorder(BSTNode<T> node, List<T> list) {
        if (node != null) {
            list.add(node.getData());
            preorder(node.getLeft(), list);
            preorder(node.getRight(), list);
        }
        return list;
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        ArrayList<T> list = new ArrayList<>();
        return inorder(root, list);
    }

    /***
     * Helper method for inorder()
     * @param node current node being accessed in tree traversal
     * @param list list to add each node in once found
     * @return list of nodes obtained through inorder traversal
     */
    private List<T> inorder(BSTNode<T> node, List<T> list) {
        if (node != null) {
            inorder(node.getLeft(), list);
            list.add(node.getData());
            inorder(node.getRight(), list);
        }
        return list;
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        ArrayList<T> list = new ArrayList<>();
        return postorder(root, list);
    }

    /***
     * Helper method for postorder()
     * @param node current node being accessed in tree traversal
     * @param list list to add each node in once found
     * @return list of nodes obtained through postorder traversal
     */
    private List<T> postorder(BSTNode<T> node, List<T> list) {
        if (node != null) {
            postorder(node.getLeft(), list);
            postorder(node.getRight(), list);
            list.add(node.getData());
        }
        return list;
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> q = new LinkedList<>();
        ArrayList<T> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        q.add(root);
        while (!q.isEmpty()) {
            BSTNode<T> current = q.poll();
            if (current != null) {
                list.add(current.getData());
                if (current.getLeft() != null) {
                    q.add(current.getLeft());
                }
                if (current.getRight() != null) {
                    q.add(current.getRight());
                }
            }
        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return height(root);
    }

    /***
     * Helper method for height()
     * @param node node at which the height is to be calculated from
     * @return height of BST from given node
     */
    private int height(BSTNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Generates a list of the max data per level from the top to the bottom
     * of the tree. (Another way to think about this is to get the right most
     * data per level from top to bottom.)
     *
     * This list should contain the last node of each level.
     *
     * If the tree is empty, an empty list should be returned.
     *
     * Ex:
     * Given the following BST composed of Integers
     *      2
     *    /   \
     *   1     4
     *  /     / \
     * 0     3   5
     * getMaxDataPerLevel() should return the list [2, 4, 5] - 2 is the max
     * data of level 0, 4 is the max data of level 1, and 5 is the max data of
     * level 2
     *
     * Ex:
     * Given the following BST composed of Integers
     *               50
     *           /        \
     *         25         75
     *       /    \
     *      12    37
     *     /  \    \
     *   10   15   40
     *  /
     * 13
     * getMaxDataPerLevel() should return the list [50, 75, 37, 40, 13] - 50 is
     * the max data of level 0, 75 is the max data of level 1, 37 is the
     * max data of level 2, etc.
     *
     * Must be O(n).
     *
     * @return the list containing the max data of each level
     */
    public List<T> getMaxDataPerLevel() {
        ArrayList<T> list = new ArrayList<T>();
        if (root == null) {
            return list;
        }
        BSTNode<T> current = root;
        int depth = 1;
        list.add(root.getData());
        getMaxDataPerLevel(current, list, depth);
        return list;
    }

    /***
     * Helper method for getMaxDataPerLevel()
     * @param current node current node being accessed in tree traversal
     * @param list the list to add each max data found into
     * @param depth the current depth at the given node
     */
    private void getMaxDataPerLevel(BSTNode<T> current, ArrayList<T> list, int depth) {
        if (list.size() < depth) {
            list.add(current.getData());
        }
        if (current.getRight() != null) {
            depth++;
            getMaxDataPerLevel(current.getRight(), list, depth--);
        }
        if (current.getLeft() != null) {
            depth++;
            getMaxDataPerLevel(current.getLeft(), list, depth--);
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
