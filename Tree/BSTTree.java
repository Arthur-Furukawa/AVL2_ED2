package Tree;
import java.util.LinkedList;
import java.util.Queue;

class Node<T extends Comparable<T>> {
	private T data;
	private Node<T> left;
	private Node<T> right;
	
	public Node(T data) {
		this.data = data;
	}
	
	public T getData() { return data; }
	public void setData(T data) { this.data = data; }
	public Node<T> getLeft() { return left; }
	public void setLeft(Node<T> left) { this.left = left; }
	public Node<T> getRight() { return right; }
	public void setRight(Node<T> right) { this.right = right; }
}

public class BSTTree<T extends Comparable<T>> {
    private Node<T> root;

    public BSTTree() {
        this.root = null;
    }

    public void addNode(T data) {
        Node<T> newNode = new Node<>(data);
        if (root == null) {
            root = newNode;
        } else {
            addNodeRecursive(root, newNode);
        }
    }

    private void addNodeRecursive(Node<T> currentNode, Node<T> newNode) {
        if (newNode.getData().compareTo(currentNode.getData()) < 0) {
            if (currentNode.getLeft() == null) {
                currentNode.setLeft(newNode);
            } else {
                addNodeRecursive(currentNode.getLeft(), newNode);
            }
        } else if (newNode.getData().compareTo(currentNode.getData()) > 0) {
            if (currentNode.getRight() == null) {
                currentNode.setRight(newNode);
            } else {
                addNodeRecursive(currentNode.getRight(), newNode);
            }
        }
    }

    public Node<T> getRoot() {
        return this.root;
    }

    public void removeNode(T data) {
        root = removeRecursive(root, data);
    }

    private Node<T> removeRecursive(Node<T> node, T data) {
        if (node == null) {
            return null;
        }

        int cmp = data.compareTo(node.getData());
        if (cmp < 0) {
            node.setLeft(removeRecursive(node.getLeft(), data));
        } else if (cmp > 0) {
            node.setRight(removeRecursive(node.getRight(), data));
        } else {
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            } else {
                Node<T> successor = findMin(node.getRight());
                node.setData(successor.getData());
                node.setRight(removeRecursive(node.getRight(), successor.getData()));
            }
        }
        return node;
    }

    public boolean searchInBST(Node<T> node, T data) {
        if(node == null) {
            return false;
        }

        int cmp = data.compareTo(node.getData());
        if(cmp < 0) {
            return searchInBST(node.getLeft(), data);
        } else if(cmp > 0) {
            return searchInBST(node.getRight(), data);
        } else {
            return true;
        }
    }

    private Node<T> findMin(Node<T> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    public void breadthFirstSearch() {
        if(root == null) {
            System.out.println("Árvore vazia");
            return;
        }
        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);

        System.out.print("Busca em largura: ");
        while(!queue.isEmpty()) {
            Node<T> node = queue.poll();
            System.out.print(node.getData() + " ");

            if(node.getLeft() != null) {
                queue.add(node.getLeft());
            }
            if(node.getRight() != null) {
                queue.add(node.getRight());
            }
        }
        System.out.println();
    }

    public void preordem(Node<T> node) {
        if(node != null) {
            System.out.print(node.getData() + " ");
            preordem(node.getLeft());
            preordem(node.getRight());
        }
    }

    public void emordem(Node<T> node) {
        if(node != null) {
            emordem(node.getLeft());
            System.out.print(node.getData() + " ");
            emordem(node.getRight());
        }
    }

    public void posordem(Node<T> node) {
        if(node != null) {
            posordem(node.getLeft());
            posordem(node.getRight());
            System.out.print(node.getData() + " ");
        }
    }
}
