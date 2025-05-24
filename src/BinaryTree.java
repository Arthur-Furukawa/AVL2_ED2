import java.util.ArrayList;
import java.util.List;

// Classe genérica que representa uma árvore binária.
// Armazena nós do tipo T (definido na hora de instanciar a árvore).
public class BinaryTree<T extends Comparable<T>> {

    protected BinaryNode<T> root;

    public BinaryTree() {
        this(null);
    }

    public BinaryTree(BinaryNode<T> root) {
        this.root = root;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int getHeight() {
        if (isEmpty()) {
            return -1;
        }
        return root.getHeight();
    }

    // Método para calcular o tamanho (número de nós) da árvore
    public int size() {
        return size(root);
    }

    private int size(BinaryNode<T> node) {
        if (node == null) {
            return 0;
        } else {
            return (size(node.getLeft()) + 1 + size(node.getRight()));
        }
    }


    // Método para realizar percurso em ordem e retornar lista de dados
    // Mantendo apenas esta versão que retorna List<T>
    public List<T> inOrderTraversal() {
        List<T> list = new ArrayList<>();
        inOrderTraversal(root, list);
        return list;
    }

    private void inOrderTraversal(BinaryNode<T> node, List<T> list) {
        if (node != null) {
            inOrderTraversal(node.getLeft(), list);
            list.add(node.getData());
            inOrderTraversal(node.getRight(), list);
        }
    }
}

