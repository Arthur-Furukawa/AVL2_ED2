// Classe genérica que representa uma árvore (balanceada) binária de busca (AVL).
// Armazena nós do tipo T (definido na hora de instanciar a árvore).
public class AVL<T extends Comparable<T>> extends BST<T> {
	
	public AVL() {
		super();
	}
	
	public AVL(BinaryNode<T> root) {
		super(root);
	}

	/**
	 * Aplica a rotação para esquerda (rotação LL) na subárvore cuja raiz é o nó indicado no parâmetro root.
	 * Retorna a nova raiz da subárvore após a rotação ser aplicada.
	 */
	private BinaryNode<T> rotateLeft(BinaryNode<T> root) {
		// NOVO: Não é possível rotacionar uma raiz nula.
		if (root == null) {
			return null;
		}
		
		// Comparando com o slide da aula:
		// nó 1 = root
		// nó 2 = root.right
		// nó Y = root.right.left
		BinaryNode<T> node1 = root;
		BinaryNode<T> node2 = root.getRight();
		
		// NOVO: o nó atual deve ter um filho direito, que será a nova raiz desta subárvore.
		if (node2 == null) {
			return null;
		}
		
		BinaryNode<T> nodeY = node2.getLeft();

		// Rotação à esquerda em torno do pai:
		// - Nó 2 se torna raiz da subárvore.
		// - Pai do nó 1 vira o pai do nó 2.
		// - Nó 2 se torna pai do nó 1.
		// - Filho direito do nó 1 recebe filho esquerdo do nó 2.
		// - Nó 1 se torna pai do filho esquerdo do nó 2.
		// - Nó 1 se torna filho esquerdo do nó 2.
		BinaryNode<T> newRoot = node2; // Nó 2 se torna raiz da subárvore.
		node2.setParent(node1.getParent()); // Pai do nó 1 vira o pai do nó 2.
		node1.setRight(nodeY); // Filho direito do nó 1 recebe filho esquerdo do nó 2. (E) Nó 1 se torna pai do filho esquerdo do nó 2.
		node2.setLeft(node1); // Nó 2 se torna pai do nó 1. (E) Nó 1 se torna filho esquerdo do nó 2.
		
		return newRoot;
	}
	
	/**
	 * Aplica a rotação para direita (rotação RR) na subárvore cuja raiz é o nó indicado no parâmetro root.
	 * Retorna a nova raiz da subárvore após a rotação ser aplicada.
	 */
	private BinaryNode<T> rotateRight(BinaryNode<T> root) {
		// NOVO: Não é possível rotacionar uma raiz nula.
		if (root == null) {
			return null;
		}
		
		// Comparando com o slide da aula:
		// nó 3 = root
		// nó 2 = root.left
		// nó X = root.left.right
		BinaryNode<T> node3 = root;
		BinaryNode<T> node2 = root.getLeft();

		// NOVO: o nó atual deve ter um filho esquerdo, que será a nova raiz desta subárvore.
		if (node2 == null) {
			return null;
		}
		
		BinaryNode<T> nodeX = node2.getRight();
		
		// Rotação à direita em torno do pai:
		// - Nó 2 se torna raiz da subárvore.
		// - Pai do nó 3 vira o pai do nó 2.
		// - Nó 2 se torna pai do nó 3.
		// - Filho esquerdo do nó 3 recebe filho direito do nó 2.
		// - Nó 3 se torna pai do filho direito do nó 2.
		// - Nó 3 se torna filho direito do nó 2.
		BinaryNode<T> newRoot = node2; // Nó 2 se torna raiz da subárvore.
		node2.setParent(node3.getParent()); // Pai do nó 3 vira o pai do nó 2.
		node3.setLeft(nodeX); // Filho esquerdo do nó 3 recebe filho direito do nó 2. (E) Nó 3 se torna pai do filho direito do nó 2.
		node2.setRight(node3); // Nó 2 se torna pai do nó 3. (E) Nó 3 se torna filho direito do nó 2.
		
		return newRoot;
	}

	/**
	 * Aplica a rotação esquerda-direita (rotação LR) na subárvore cuja raiz é o nó indicado no parâmetro root.
	 * Retorna a nova raiz da subárvore após a rotação ser aplicada.
	 */
	private BinaryNode<T> rotateLeftRight(BinaryNode<T> root) {
		// Comparando com o slide da aula:
		// nó 3 = root
		// nó 1 = root.left
		BinaryNode<T> node3 = root;
		BinaryNode<T> node1 = root.getLeft();
		
		// Aplica a rotação para esquerda (rotação intermediária pelo nó 1 como raiz da subárvore).
		BinaryNode<T> newRootRotateLeft = rotateLeft(node1);
		node3.setLeft(newRootRotateLeft);
		
		// Aplica a rotação para direita (rotação final pelo nó 3 como raiz da subárvore).
		BinaryNode<T> newRoot = rotateRight(node3);
		
		return newRoot;
	}

	/**
	 * Aplica a rotação direita-esquerda (rotação RL) na subárvore cuja raiz é o nó indicado no parâmetro root.
	 * Retorna a nova raiz da subárvore após a rotação ser aplicada.
	 */
	private BinaryNode<T> rotateRightLeft(BinaryNode<T> root) {
		// Comparando com o slide da aula:
		// nó 1 = root
		// nó 3 = root.right
		BinaryNode<T> node1 = root;
		BinaryNode<T> node3 = root.getRight();
		
		// Aplica a rotação para direita (rotação intermediária pelo nó 3 como raiz da subárvore).
		BinaryNode<T> newRootRotateRight = rotateRight(node3);
		node1.setRight(newRootRotateRight);
		
		// Aplica a rotação para esquerda (rotação final pelo nó 1 como raiz da subárvore).
		BinaryNode<T> newRoot = rotateLeft(node1);
		
		return newRoot;
	}

	/**
	 * Verifica se a árvore está desbalanceada e corrige o balanceamento com a rotação certa.
	 */
	private BinaryNode<T> balance(BinaryNode<T> root) {
		// NOVO: Não é possível balancar uma subárvore cuja raiz seja nula.
		if (root == null) {
			return null;
		}
		
		int balanceFactor = root.getBalanceFactor();
		if (balanceFactor > 1) {
			BinaryNode<T> right = root.getRight();
			if (right != null) {
				if (right.getBalanceFactor() >= 0) {
					root = rotateLeft(root);
				} else {
					root = rotateRightLeft(root);
				}
			}
		} else if (balanceFactor < -1) {
			BinaryNode<T> left = root.getLeft();
			if (left != null) {
				if (left.getBalanceFactor() <= 0) {
					root = rotateRight(root);
				} else {
					root = rotateLeftRight(root);
				}
			}
		}
		
		return root;
	}

	/**
	 * Sobrescreve o método insert(node, parent, data) da classe BST.
	 * O nó raiz resultante da inserção é passado como nó raiz para verificar se a subárvore
	 * foi desbalanceada. Em caso positivo, a subárvore é rotacionada (e árvore volta a ficar
	 * balanceada).
	 */
	@Override
	protected BinaryNode<T> insert(BinaryNode<T> node, BinaryNode<T> parent, T data) {
		return balance(super.insert(node, parent, data));
	}

	@Override
	protected BinaryNode<T> remove(BinaryNode<T> node, T data) {
		return balance(super.remove(node, data));
	}
	
}
