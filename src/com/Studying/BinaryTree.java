package com.Studying;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node<T> parent = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        }
        else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        }
        else {
            assert closest.right == null;
            closest.right = newNode;
        }
        newNode.parent = closest;
        size++;
        return true;
    }

    boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    private boolean replaceInParent(Node<T> replaceable, Node<T> replacer) {
        if (replaceable != root) {
            if (replaceable.parent.left == replaceable) {
                replaceable.parent.left = replacer;
            }
            else  {
                replaceable.parent.right = replacer;
            }
            if (replacer != null) replacer.parent = replaceable.parent;
            return true;
        }
        if (replacer != null) {
            replacer.parent = null;
        }
        root = replacer;
        return false;
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> current = null;

        private BinaryTreeIterator() {
            Node<T> temp = root;
            while (temp.left != null) {
                temp = temp.left;
            }
            current = new Node<T>(temp.value);
            current.right = temp;
        }

        private Node<T> findNext() {
            if (current.right != null) {
                return down();
            }
            else {
                return up();
            }
        }

        private Node<T> down() {
            Node<T> temp = current.right;
            while (temp.left != null) {
                temp = temp.left;
            }
            return temp;
        }

        private Node<T> up() {
            Node<T> temp = current;
            while (temp.parent.right == temp) {
                temp = temp.parent;
                if (temp == root) {
                    return null;
                }
            }
                temp = temp.parent;
                return temp;
        }

        @Override
        public boolean hasNext() {
            return findNext() != null;
        }

        @Override
        public T next() {
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }

        @Override
        public void remove() {
            Node<T> temp = current;
            if (temp.parent == null && temp != root) {
                throw new IllegalStateException();
            }
            else if (temp.left == null && temp.right == null) {
                replaceInParent(temp, null);
                size--;
            }
            else if (temp.right == null || (temp.left != null && temp.left.right == null)) {
                replaceInParent(temp, temp.left);
                if (temp.right != null) {
                    temp.left.right = temp.right;
                    temp.right.parent = temp.left;
                }
                size--;
            }
            else if (temp.left == null || (temp.right != null && temp.right.left == null)) {
                replaceInParent(temp, temp.right);
                if (temp.left != null) {
                    temp.right.left = temp.left;
                    temp.left.parent = temp.right;
                }
                size--;
            }
            else {
                Node<T> additionalTemp = temp.right;
                while (additionalTemp.left != null) {
                    additionalTemp = additionalTemp.left;
                }

                current = additionalTemp;
                remove();

                replaceInParent(temp, additionalTemp);
                additionalTemp.right = temp.right;
                temp.right.parent = additionalTemp;
                additionalTemp.left = temp.left;
                temp.left.parent = additionalTemp;
            }
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
