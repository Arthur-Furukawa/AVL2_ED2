package Tree;

// Classe genérica que representa um nó de uma árvore binária.
// Armazena dados do tipo T (definido na hora de instanciar o nó).
public class BinaryNode<T extends Comparable<T>> {
	
	// O nó armazena um tipo genérico T como dado.
	private T data;
	
	// Referência para o nó pai.
	private BinaryNode<T> parent;
	
	// Referência para o nó filho da esquerda.
	private BinaryNode<T> left;

	// Referência para o nó filho da direita.
	private BinaryNode<T> right;
	
	// Fator de balanceamento para a árvore AVL.
	private int balanceFactor;
	
	// Método construtor padrão (sem parâmetros).
	public BinaryNode() { this(null); }
	
	// Método construtor recebendo parâmetro dos dados do nó.
	public BinaryNode(T data) {
		this.data = data;
		parent = null;
		left = null;
		right = null;
	}
	
	public BinaryNode<T> getParent() { return parent; }
	public void setParent(BinaryNode<T> parent) { this.parent = parent; }
	
	public BinaryNode<T> getLeft() { return left; }
	public void setLeft(BinaryNode<T> left) {
		this.left = left;
		if (this.left != null) {
			this.left.setParent(this);
		}
		updateBalanceFactor();
	}
	
	public BinaryNode<T> getRight() { return right; }
	public void setRight(BinaryNode<T> right) {
		this.right = right;
		if (this.right != null) {
			this.right.setParent(this);
		}
		updateBalanceFactor();
	}
	
	public T getData() { return data; }
	public void setData(T data) { this.data = data; }
	
	public int getBalanceFactor() { return balanceFactor; }
	
	private void updateBalanceFactor() {
		// bf(n) = hr – hl (hr = -1 se não tem filho direito e hl = -1 se não tem filho esquerdo).
		int hl = hasLeft() ? left.getHeight() : -1;
		int hr = hasRight() ? right.getHeight() : -1;
		balanceFactor = hr - hl;
	}
	
	public boolean hasLeft() {
		return left != null;
	}
	
	public boolean hasRight() {
		return right != null;
	}
	
	// Verifica se o nó é raiz (true se nó é raiz, false caso contrário).
	public boolean isRoot() {
		return parent == null;
	}

	// Verifica se o nó é folha (true se nó é folha, false caso contrário).
	public boolean isLeaf() {
		return left == null && right == null;
	}

	// Retorna o grau do nó (int).
	public int getDegree() {
		int degree = 0;
		
		if (left != null) {
			++degree;
		}

		if (right != null) {
			++degree;
		}
		
		return degree;		
	}

	// Retorna o nível (profundidade) do nó (int).
	public int getLevel() {
		if (isRoot()) {
			return 0;
		}
		
		// depth(N) = nível do nó N = nível do pai de N + 1
		return parent.getLevel() + 1;
	}

	// Retorna a altura do nó (int).
	public int getHeight() {
		if (isLeaf()) {
			return 0;
		}
		
		// h(N) = altura do nó N = 1 + maior altura dos nós filhos de N
		int hl = left != null ? left.getHeight() : 0;
		int hr = right != null ? right.getHeight() : 0;
		return Math.max(hl, hr) + 1;
	}
	
	@Override
	public String toString() {
		return "data: " + data
				+ ", balance factor: " + balanceFactor
				+ ", parent: " + (parent != null ? parent.getData() : "null")
				+ ", left: " + (left != null ? left.getData() : "null")
				+ ", right: " + (right != null ? right.getData() : "null");
	}

}
