package com.mega.tank.iterator;

public class LinkedList_<E> implements Collection_<E> {

    private Node<E> head;
    private Node<E> tail;

    private int size = 0;

    @Override
    public void add(E ele) {
        Node<E> node = new Node<>(ele);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator_<E> iterator() {
        return new Itr();
    }

    static class Node<E> {
        E value;
        Node<E> next;

        Node(E value) {
            this.value = value;
        }
    }

    private class Itr implements Iterator_<E> {

        private Node<E> currNode = head;

        @Override
        public boolean hasNext() {
            return currNode != null && currNode.next != null;
        }

        @Override
        public E next() {
            E ele = currNode.next.value;
            currNode = currNode.next;
            return ele;
        }
    }
}
