package com.mega.tank.iterator;

public class ArrayList_<E> implements Collection_<E> {

    private static final int DEFAULT_CAPACITY = 10;

    private Object[] element = new Object[DEFAULT_CAPACITY];

    private int index = 0;

    @Override
    public void add(E ele) {
        if (index == element.length) {
            Object[] objects = new Object[element.length * 2];
            System.arraycopy(element, 0, objects, 0, element.length);
            element = objects;
        }
        element[index++] = ele;
    }

    public int size() {
        return index;
    }

    @Override
    public Iterator_<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator_<E> {

        private int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < index;
        }

        @Override
        public E next() {
            return (E) element[cursor++];
        }
    }
}
