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

	public void inOrderTraversal() {
		inOrderTraversal(root);
	}
	
	private void inOrderTraversal(BinaryNode<T> node) {
		// LNR (ir para esquerda, visitar nó atual, ir para direita).
		if (node == null) {
			return;
		}
		
		inOrderTraversal(node.getLeft());
		//System.out.print(node.getData() + ", ");
		System.out.print("{ " + node + " }\n");
		inOrderTraversal(node.getRight());
	}
	
	public void preOrderTraversal() {
		preOrderTraversal(root);
	}
	
	private void preOrderTraversal(BinaryNode<T> node) {
		// NLR (visitar nó atual, ir para esquerda, ir para direita).
		if (node != null) {
			System.out.print(node.getData() + ", ");
			
			if (node.getLeft() != null) {
				preOrderTraversal(node.getLeft());
			}
			
			if (node.getRight() != null) {
				preOrderTraversal(node.getRight());
			}
		}
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

}



    // Método para realizar percurso em ordem e retornar lista de dados
    public java.util.List<T> inOrderTraversal() {
        java.util.List<T> list = new java.util.ArrayList<>();
        inOrderTraversal(root, list);
        return list;
    }

    private void inOrderTraversal(BinaryNode<T> node, java.util.List<T> list) {
        if (node != null) {
            inOrderTraversal(node.getLeft(), list);
            list.add(node.getData());
            inOrderTraversal(node.getRight(), list);
        }
    }

    // Adicionar outros métodos de percurso se necessário (preOrder, postOrder)

