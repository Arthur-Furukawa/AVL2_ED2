import java.util.List;

public class Benchmark {

    public static class Result {
        public double timeMillis;
        public long comparisons;

        public Result(double timeMillis, long comparisons) {
            this.timeMillis = timeMillis;
            this.comparisons = comparisons;
        }

        @Override
        public String toString() {
            return String.format("Tempo: %.4f ms, Comparações: %d", timeMillis, comparisons);
        }
    }

    /**
     * Mede o tempo e as comparações para inserir dados em uma árvore.
     * @param tree A árvore (BST ou AVL) a ser testada.
     * @param data Lista de dados a serem inseridos.
     * @return Um objeto Result contendo o tempo em milissegundos e o número de comparações.
     */
    public static <T extends Comparable<T>> Result measureInsert(BST<T> tree, List<T> data) {
        tree.resetComparisonCount(); // Reseta antes de começar
        long startTime = System.nanoTime();
        
        for (T item : data) {
            try {
                tree.insert(item);
            } catch (RuntimeException e) {
                // Ignora exceções de duplicata se a lógica for de agregação
                if (!e.getMessage().contains("sem lógica de agregação")) {
                     System.err.println("Erro durante inserção no benchmark: " + e.getMessage());
                }
            }
        }
        
        long endTime = System.nanoTime();
        double durationMillis = (endTime - startTime) / 1_000_000.0;
        long comparisons = tree.getComparisonCount();
        
        return new Result(durationMillis, comparisons);
    }

    /**
     * Mede o tempo e as comparações para buscar chaves em uma árvore.
     * @param tree A árvore (BST ou AVL) a ser testada.
     * @param keys Lista de chaves a serem buscadas.
     * @return Um objeto Result contendo o tempo em milissegundos e o número de comparações.
     */
    public static <T extends Comparable<T>> Result measureSearch(BST<T> tree, List<T> keys) {
        tree.resetComparisonCount(); // Reseta antes de começar
        long startTime = System.nanoTime();
        
        for (T key : keys) {
            tree.search(key);
        }
        
        long endTime = System.nanoTime();
        double durationMillis = (endTime - startTime) / 1_000_000.0;
        long comparisons = tree.getComparisonCount();
        
        return new Result(durationMillis, comparisons);
    }

    /**
     * Mede o tempo e as comparações para remover chaves de uma árvore.
     * @param tree A árvore (BST ou AVL) a ser testada.
     * @param keys Lista de chaves a serem removidas.
     * @return Um objeto Result contendo o tempo em milissegundos e o número de comparações.
     */
    public static <T extends Comparable<T>> Result measureRemove(BST<T> tree, List<T> keys) {
        tree.resetComparisonCount(); // Reseta antes de começar
        long startTime = System.nanoTime();
        
        for (T key : keys) {
            try {
                 tree.remove(key);
            } catch (Exception e) {
                 System.err.println("Erro durante remoção no benchmark: " + e.getMessage());
            }
        }
        
        long endTime = System.nanoTime();
        double durationMillis = (endTime - startTime) / 1_000_000.0;
        long comparisons = tree.getComparisonCount();
        
        return new Result(durationMillis, comparisons);
    }
}

