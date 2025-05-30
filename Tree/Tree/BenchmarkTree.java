package Tree;

public class BenchmarkTree {

    public static <T extends Comparable<T>> double measureInsert(BSTTree<T> bst, T[] data) {
        long startTime = System.nanoTime();
        for (T item : data) {
            bst.addNode(item);
        }
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000.0;
    }

    public static <T extends Comparable<T>> double measureInsert(AVLTree<T> avl, T[] data) {
        long startTime = System.nanoTime();
        for (T item : data) {
            avl.addNode(item);
        }
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000.0;
    }

    public static <T extends Comparable<T>> double measureSearch(BSTTree<T> tree, T[] keys) {
        long startTime = System.nanoTime();
        for (T key : keys) {
            searchInBST(tree, key);
        }
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000.0;
    }

    public static <T extends Comparable<T>> double measureSearch(AVLTree<T> tree, T[] keys) {
        long startTime = System.nanoTime();
        for (T key : keys) {
            searchInAVL(tree, key);
        }
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000.0;
    }

    public static <T extends Comparable<T>> double measureRemove(BSTTree<T> tree, T[] data) {
        long startTime = System.nanoTime();
        for (T item : data) {
            tree.removeNode(item);
        }
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000.0;
    }

    public static <T extends Comparable<T>> double measureRemove(AVLTree<T> tree, T[] data) {
        long startTime = System.nanoTime();
        for (T item : data) {
            tree.removeNode(item);
        }
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000.0;
    }

    private static <T extends Comparable<T>> boolean searchInBST(BSTTree<T> tree, T key) {
        Node<T> current = tree.getRoot();
        while (current != null) {
            int cmp = key.compareTo(current.getData());
            if (cmp == 0) return true;
            current = cmp < 0 ? current.getLeft() : current.getRight();
        }
        return false;
    }

    private static <T extends Comparable<T>> boolean searchInAVL(AVLTree<T> tree, T key) {
        var current = tree.getRoot();
        while (current != null) {
            int cmp = key.compareTo(current.getData());
            if (cmp == 0) return true;
            current = (cmp < 0) ? current.getLeft() : current.getRight();
        }
        return false;
    }

    public static String formatNanos(long nanos) {
        if (nanos > 1_000_000) { // Mais de 1 ms
            return String.format("%.3f ms", nanos / 1_000_000.0);
        } else if (nanos > 1_000) { // Mais de 1 microsegundo
             return String.format("%.3f µs", nanos / 1_000.0);
        } else {
            return nanos + " ns";
        }
    }
}
