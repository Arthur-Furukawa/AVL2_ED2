package Tree;
import java.util.Arrays;
import java.util.Random;

class BenchmarkTree {

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

    public static void printBenchmark(String operation, String dataType, int numElements, double bstTime, double avlTime) {
        System.out.println("\n--- Benchmark de " + operation + " (" + dataType + ") ---");
        System.out.printf("Número de elementos: %d%n", numElements);
        System.out.printf("Temp BST: %.3f ms%n", bstTime);
        System.out.printf("Tempo AVL: %.3f ms%n", avlTime);
        System.out.println("------------------------------------");
    }
}

public class Benchmark { 
    public static void main(String[] args) {

	// Geração de 10000 elementos aleatórios para teste de Benchmark
        int numElements = 10000;
        Integer[] randomData = generateRandomIntegers(numElements);
        Integer[] searchKeys = Arrays.copyOf(randomData, randomData.length);
        Integer[] removeKeys = Arrays.copyOf(randomData, randomData.length / 2);
        System.out.println("Iniciando Benchmark...");


	// Cria dois vetores independentes, que serão utilizados nos testes de inserção e remoção das duas árvores
	// Ambos armazenam os valores gerados aleatoriamente que depois serão inseridos nas árvores
        Integer[] insertDataBST = Arrays.copyOf(randomData, randomData.length);
        Integer[] insertDataAVL = Arrays.copyOf(randomData, randomData.length);

        AVLTree<Integer> avlInsert = new AVLTree<>();
        BSTTree<Integer> bstInsert = new BSTTree<>();


	// Teste de inserção de valores gerados aleatoriamente nas árvores BST e AVL
        double bstInsertTime = BenchmarkTree.measureInsert(bstInsert, insertDataBST);
        double avlInsertTime = BenchmarkTree.measureInsert(avlInsert, insertDataAVL);
        BenchmarkTree.printBenchmark("Inserção", "Inteiros aleatórios", numElements, bstInsertTime, avlInsertTime);


	// Teste de busca de um elemento nas árvores BST e AVL
        double bstSearchTime = BenchmarkTree.measureSearch(bstInsert, searchKeys);
        double avlSearchTime = BenchmarkTree.measureInsert(avlInsert, searchKeys);
        BenchmarkTree.printBenchmark("Busca", "Inteiros aleatórios", numElements, bstSearchTime, avlSearchTime);
    }


    // Método para gerar os valores aleatórios utilizando a biblioteca Random
    private static Integer[] generateRandomIntegers(int size) {
        Integer[] data = new Integer[size];
        Random rand = new Random();
        for(int i = 0; i < size; i++) {
            data[i] = rand.nextInt(size * 2);
        }
        return data;
    }
}
