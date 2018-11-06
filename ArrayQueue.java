package queue;

import java.util.Arrays;

public class ArrayQueue implements Queue {
    private int size = 0;
    //post: size' = 0 && tail = 0
    private Object[] elements = new Object[10];
    //post: len - elements.length; len' = 10
    private int begin = 0;
    private int tail = 0;
    //post: element[being'] - first element in queue, begin' = 0 && elements[tail'] - place for next element, tail' = 0 => begin' == tail' => queue is empty

    //pre: len >= 5 && capcity > 0
    private void ensureCapacity(int capacity) {
        if (capacity <= elements.length) {
            return;
            //post: len' = len && elements[being'] - first element in queue && elements[tail'] - place for next element
        }

        //post: capacity >= len => size == len && elements[being'] - first element in queue && elements[tail'] - place for next element

        Object[] newElements = new Object[2 * capacity];

        /*for (int i = 0; i < size; i++) {
            newElements[i] = elements[(begin + i) % elements.length];
        }*/
        System.arraycopy(toArray(), 0, newElements, 0, size());
        elements = newElements;


        begin = 0;
        size = size();

        //post: len' = 2 * capacity => size < len' && begin' = 0 && tail' = size => elements[being'] - first element in queue && elements[tail'] - place for next element, because indexes start from 0
    }

    //pre: elem != null
    public void enqueue(Object element) {
        assert element != null;

        //pre: size >= 0 && elem != null && elements[being'] - first element in queue && elements[tail'] - place for next element
        ensureCapacity(size + 1);
        //post: len' >= size + 1 && elements[being'] - first element in queue && elements[tail'] - place for next element

        //pre: elements[being'] - first element in queue && tail' = (begin + size) % len' && elements[tail'] - place for next element
        elements[(begin + size++) % elements.length] = element;
        //post: element[tail'] = element && size' = size + 1 && elements[being'] - first element in queue && tail' = (begin + size) % len => elements[tail'] - place for next element

    }

    ///pre: size != 0
    public Object element() {
        assert size > 0;
        //pre: begin' = first element in queue
        return elements[begin];
    }

    //pre: size > 0
    public Object dequeue() {
        assert size > 0;
        //pre: elements[being'] - first element in queue
        Object elem = elements[begin];
        //post: elem = first element in queue
        //pre: elements[being'] - first element in queue
        begin = (begin + 1) % elements.length;
        //post: elements[being'] - second element in queue
        size--;
        //post: size' = size' - 1 => elements[being'] - first element in queue && tail' = (begin' + size) % len' && elements[tail'] - place for next element
        return elem;
    }

    public int size() {
        return size;
    }
    //post: return size

    public boolean isEmpty() {
        return size == 0;
    }
    //post: return answer: is queue empty?

    public void clear() {
        elements = new Object[size / 2 + 5];
        size = begin = 0;
    }
    //post: begin' = size' = 0 => tail' = (begin + size) % len = 0 => queue is empty

    public Object[] toArray() {
        Object[] arr = new Object[size];

        if (begin + size > elements.length) {
            System.arraycopy(elements, begin, arr, 0, elements.length - begin);
            System.arraycopy(elements, 0, arr, elements.length - begin, (begin + size) % elements.length);
        } else {
            System.arraycopy(elements, begin, arr, 0, size);
        }

        return arr;
    }
/*
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[(begin + i) % elements.length];
        }*/

    public void push(Object element) {
        assert element != null;

        ensureCapacity(++size);
        if (--begin < 0) {
            begin += elements.length;
        }
        elements[begin] = element;
    }

    public Object peek() {
        assert size > 0;

        return elements[(begin + size - 1) % elements.length];
    }

    public Object remove() {
        assert size > 0;

        Object elem = elements[(begin + --size) % elements.length];

        if (size < 0) {
            size += elements.length;
        }

        return elem;
    }
}