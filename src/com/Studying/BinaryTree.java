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

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> current = null;

        private BinaryTreeIterator() {
            current = null;
        }

        private Node<T> findNext() {
            if (current == null) {
                return root;
            }
            else if (current.left != null) {
                return current.left;
            }
            else if (current.right != null) {
                return current.right;
            }
            else {
                Node<T> tempCurrent = current;
                Node tempPrevious = null;
                do {
                    if (tempCurrent == root) {
                        return null;
                    }
                    tempPrevious = tempCurrent;
                    tempCurrent = tempCurrent.parent;
                } while ((tempCurrent.right == null || tempCurrent.right == tempPrevious));
                return tempCurrent.right;
            }
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
            if (temp == null) {
                return;
            }
            else if (temp.left == null && temp.right == null) {
                if (temp.parent.left == temp) {
                    temp.parent.left = null;
                }
                else {
                    temp.parent.right = null;
                }
            }
            else if (temp.right == null) {next();
                if (temp.parent.left == temp) {
                    temp.parent.left = temp.left;
                    temp.left.parent = temp.parent;
                }
                else {
                    temp.parent.right = temp.left;
                    temp.left.parent = temp.parent;
                }
            }
            else if (temp.left == null) {next();
                if (temp.parent.left == temp) {
                    temp.parent.left = temp.right;
                    temp.right.parent = temp.parent;
                }
                else {
                    temp.parent.right = temp.right;
                    temp.right.parent = temp.parent;
                }
            }
            else {
                if (temp.right.left == null) {
                    if (temp.parent.left == temp) {
                        temp.parent.left = temp.right;
                        temp.right.parent = temp.parent;
                    }
                    else {
                        temp.parent.right = temp.right;
                        temp.right.parent = temp.parent;
                    }
                    temp.left.parent = temp.right;
                    temp.right.left = temp.left;
                }
                else if (temp.left.right == null) {
                    if (temp.parent.left == temp) {
                        temp.parent.left = temp.left;
                        temp.left.parent = temp.parent;
                    }
                    else {
                        temp.parent.right = temp.left;
                        temp.left.parent = temp.parent;
                    }
                    temp.right.parent = temp.left;
                    temp.left.right = temp.right;
                }
                else {
                    Node<T> additionalTemp = temp.right;
                    while (additionalTemp.left != null) {
                        additionalTemp = additionalTemp.left;
                    }

                    current = additionalTemp;
                    remove();

                    if (temp.parent.left == temp) {
                        temp.parent.left = additionalTemp;
                    }
                    else {
                        temp.parent.right = additionalTemp;
                    }
                    additionalTemp.parent = temp.parent;
                    additionalTemp.right = temp.right;
                    temp.right.parent = additionalTemp;
                    additionalTemp.left = temp.left;
                    temp.left.parent = additionalTemp;
                }
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
