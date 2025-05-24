// Classe genérica que representa uma árvore binária de busca (BST).
// Armazena nós do tipo T (definido na hora de instanciar a árvore).
public class BST<T extends Comparable<T>> extends BinaryTree<T> {

    // Contador de comparações para análise de desempenho
    private long comparisonCount = 0;

    public BST() {
        super();
    }

    public BST(BinaryNode<T> root) {
        super(root);
    }

    // Método para obter a contagem de comparações
    public long getComparisonCount() {
        return comparisonCount;
    }

    // Método para resetar a contagem de comparações
    public void resetComparisonCount() {
        this.comparisonCount = 0;
    }

    @Override
    public BinaryNode<T> search(T data) {
        // Reseta a contagem antes de iniciar a busca, se desejado medir por operação
        // resetComparisonCount(); 
        return search(root, data);
    }

    /**
     * Realiza uma busca na BST.
     */
    private BinaryNode<T> search(BinaryNode<T> node, T data) {
        if (node == null) {
            return null;
        }
        
        comparisonCount++; // Incrementa a cada comparação
        int cmp = data.compareTo(node.getData());

        if (cmp == 0) {
            return node;
        } else if (cmp < 0) {
            return search(node.getLeft(), data);
        } else {
            return search(node.getRight(), data);
        }
    }

    @Override
    public void insert(T data) {
        // Reseta a contagem antes de iniciar a inserção, se desejado medir por operação
        // resetComparisonCount(); 
        root = insert(root, null, data);
    }

    /**
     * Insere um nó na BST.
     */
    @Override
    protected BinaryNode<T> insert(BinaryNode<T> node, BinaryNode<T> parent, T data) {
        if (node == null) {
            node = new BinaryNode<>(data);
            // Não há comparação aqui, apenas criação
        } else {
            comparisonCount++; // Incrementa a cada comparação
            int cmp = data.compareTo(node.getData());

            if (cmp == 0) {
                // Nacionalidade já existe, agrega o total de alunos.
                if (node.getData() instanceof NacionalidadeInfo && data instanceof NacionalidadeInfo) {
                    ((NacionalidadeInfo) node.getData()).adicionarAlunos(((NacionalidadeInfo) data).getTotalAlunos());
                } else {
                    throw new RuntimeException("BST.insert(): Tentativa de inserir duplicata ou tipo inesperado sem lógica de agregação: " + data);
                }
                // Não continua a inserção, apenas atualiza o nó existente.
            } else if (cmp < 0) {
                node.setLeft(insert(node.getLeft(), node, data));
            } else {
                node.setRight(insert(node.getRight(), node, data));
            }
        }
        return node;
    }

    @Override
    public void remove(T data) {
        // Reseta a contagem antes de iniciar a remoção, se desejado medir por operação
        // resetComparisonCount(); 
        root = remove(root, data);
    }

    /**
     * Procura o nó a ser removido da BST.
     */
    @Override
    protected BinaryNode<T> remove(BinaryNode<T> node, T data) {
        if (node == null) {
            return null;
        }
        
        comparisonCount++; // Incrementa a cada comparação
        int cmp = data.compareTo(node.getData());

        if (cmp == 0) {
            node = removeNode(node);
        } else if (cmp < 0) {
            node.setLeft(remove(node.getLeft(), data));
        } else {
            node.setRight(remove(node.getRight(), data));
        }

        return node;
    }

    /**
     * Remove um nó da BST.
     */
    private BinaryNode<T> removeNode(BinaryNode<T> node) {
        if (node.isLeaf()) {
            return null;
        } else if (!node.hasLeft()) {
            return node.getRight();
        } else if (!node.hasRight()) {
            return node.getLeft();
        }

        var predecessor = predecessor(node);
        node.setData(predecessor.getData());
        // A chamada recursiva de remove incrementará as comparações necessárias
        node.setLeft(remove(node.getLeft(), predecessor.getData())); 
        return node;
    }

    /**
     * Encontra o predecessor de um nó.
     */
    private BinaryNode<T> predecessor(BinaryNode<T> node) {
        if (node == null) {
            return null;
        }

        if (node.hasLeft()) {
            return findMax(node.getLeft());
        } else {
            // Lógica para encontrar predecessor subindo na árvore (não implementada/necessária aqui)
            // Para a remoção, apenas o findMax na subárvore esquerda é usado.
             throw new UnsupportedOperationException("Predecessor para nó sem subárvore esquerda não implementado/necessário para remoção padrão.");
        }
    }

    /**
     * Encontra o nó com valor máximo na subárvore.
     */
    private BinaryNode<T> findMax(BinaryNode<T> node) {
        if (node == null) {
            return null;
        }
        // Não conta comparações aqui, pois é parte da lógica interna da remoção
        while (node.hasRight()) {
            node = node.getRight();
        }
        return node;
    }
}

