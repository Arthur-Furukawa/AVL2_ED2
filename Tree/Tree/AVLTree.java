package Tree;
import java.util.LinkedList;
import java.util.Queue;

class AVLTreeNode<T extends Comparable<T>> {
    private T data;
    private AVLTreeNode<T> left;
    private AVLTreeNode<T> right;
    private int height;

    public AVLTreeNode(T data) {
        this.data = data;
        this.height = 1;
    }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
    public AVLTreeNode<T> getLeft() { return left; }
    public void setLeft(AVLTreeNode<T> left) { this.left = left; }
    public AVLTreeNode<T> getRight() { return right; }
    public void setRight(AVLTreeNode<T> right) { this.right = right; }
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }
}

public class AVLTree<T extends Comparable<T>> {
    private AVLTreeNode<T> root;

    public void addNode(T data) {
        root = insert(root, data);
    }

    private AVLTreeNode<T> insert(AVLTreeNode<T> node, T data) {
        if (node == null) {
            return new AVLTreeNode<>(data);
        }
        int cmp = data.compareTo(node.getData());
        if (cmp < 0) {
            node.setLeft(insert(node.getLeft(), data));
        } else if (cmp > 0) {
            node.setRight(insert(node.getRight(), data));
        } else {
            return node;
        }
        updateHeight(node);
        return balance(node);
    }

    public void removeNode(T data) {
        root = remove(root, data);
    }

    private AVLTreeNode<T> remove(AVLTreeNode<T> node, T data) {
        if (node == null) {
            return null;
        }
        int cmp = data.compareTo(node.getData());
        if (cmp < 0) {
            node.setLeft(remove(node.getLeft(), data));
        } else if (cmp > 0) {
            node.setRight(remove(node.getRight(), data));
        } else {
            if (node.getLeft() == null || node.getRight() == null) {
                node = (node.getLeft() != null) ? node.getLeft() : node.getRight();
            } else {
                AVLTreeNode<T> successor = findMin(node.getRight());
                node.setData(successor.getData());
                node.setRight(remove(node.getRight(), successor.getData()));
            }
        }
        if (node == null) {
            return null;
        }
        updateHeight(node);
        return balance(node);
    }

    private AVLTreeNode<T> findMin(AVLTreeNode<T> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    private AVLTreeNode<T> balance(AVLTreeNode<T> node) {
        int bf = getBalanceFactor(node);
        if (bf > 1) {
            if (getBalanceFactor(node.getLeft()) < 0) {
                node.setLeft(leftRotate(node.getLeft()));
            }
            return rightRotate(node);
        }
        if (bf < -1) {
            if (getBalanceFactor(node.getRight()) > 0) {
                node.setRight(rightRotate(node.getRight()));
            }
            return leftRotate(node);
        }
        return node;
    }

    private AVLTreeNode<T> rightRotate(AVLTreeNode<T> y) {
        AVLTreeNode<T> x = y.getLeft();
        AVLTreeNode<T> T2 = x.getRight();
        x.setRight(y);
        y.setLeft(T2);
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private AVLTreeNode<T> leftRotate(AVLTreeNode<T> x) {
        AVLTreeNode<T> y = x.getRight();
        AVLTreeNode<T> T2 = y.getLeft();
        y.setLeft(x);
        x.setRight(T2);
        updateHeight(x);
        updateHeight(y);
        return y;
    }

    private int getHeight(AVLTreeNode<T> node) {
        return (node == null) ? 0 : node.getHeight();
    }

    private void updateHeight(AVLTreeNode<T> node) {
        node.setHeight(1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight())));
    }

    private int getBalanceFactor(AVLTreeNode<T> node) {
        return (node == null) ? 0 : getHeight(node.getLeft()) - getHeight(node.getRight());
    }

    public AVLTreeNode<T> getRoot() {
        return root;
    }

    public boolean searchInAVL(AVLTreeNode<T> node, T data) {
        if(node == null) {
            return false;
        }

        int cmp = data.compareTo(node.getData());
        if(cmp < 0) {
            return searchInAVL(node.getLeft(), data);
        } else if(cmp > 0) {
            return searchInAVL(node.getRight(), data);
        } else {
            return true;
        }
    }

    public void breadthFirstSearch() {
        if(root == null) {
            System.out.println("Árvore vazia");
            return;
        }
        Queue<AVLTreeNode<T>> queue = new LinkedList<>();
        queue.add(root);

        System.out.print("Busca em largura: ");
        while(!queue.isEmpty()) {
            AVLTreeNode<T> node = queue.poll();
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

    public void preordem(AVLTreeNode<T> node) {
        if(node != null) {
            System.out.print(node.getData() + " ");
            preordem(node.getLeft());
            preordem(node.getRight());
        }
    }

    public void emordem(AVLTreeNode<T> node) {
        if(node != null) {
            emordem(node.getLeft());
            System.out.print(node.getData() + " ");
            emordem(node.getRight());
        }
    }

    public void posordem(AVLTreeNode<T> node) {
        if(node != null) {
            posordem(node.getLeft());
            posordem(node.getRight());
            System.out.print(node.getData() + " ");
        }
    }
}