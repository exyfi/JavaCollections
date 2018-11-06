package queue;

public class ArrayQueueADT {
    private int begin = 0;
    private int size = 0;
    //post: element[being'] - first element in queue, begin' = 0 && elements[tail'] - place for next element,
    // tail' = 0 => begin' == tail' => queue is empty
    private Object[] elements = new Object[10];
    //post: len - elements.length; len' = 10

    //pre: len >= 5 && capcity > 0
    private static void ensureCapacity(ArrayQueueADT queue, int capacity) {
        if (capacity <= queue.elements.length) {
            return;
            //post: len' = len && elements[being'] - first element in queue && elements[tail'] - place for next element
        }
        //post: capacity >= len => size == len && elements[being'] - first element in queue && elements[tail'] - place for next element
        Object[] newElements = new Object[2 * capacity];
        for (int i = 0; i < queue.size; i++) {
            newElements[i] = queue.elements[(queue.begin + i) % queue.elements.length];
        }
        queue.begin = 0;
        queue.elements = newElements;
        //post: len' = 2 * capacity => size < len' && begin' = 0 && tail' = size => elements[being'] - first element in queue && elements[tail'] - place for next element, because indexes start from 0
    }

    //pre: elem != null
    public static void enqueue(ArrayQueueADT queue, Object element) {
        assert element != null;
        //pre: size >= 0 && elem != null && elements[being'] - first element in queue && elements[tail'] - place for next element
        ensureCapacity(queue, queue.size + 1);
        //post: len' >= size + 1 && elements[being'] - first element in queue && elements[tail'] - place for next element

        //pre: elements[being'] - first element in queue && tail' = (begin + size) % len' && elements[tail'] - place for next element
        queue.elements[(queue.begin + queue.size++) % queue.elements.length] = element;
        //post: element[tail'] = element && size' = size + 1 && elements[being'] - first element in queue && tail' = (begin + size) % len => elements[tail'] - place for next element
    }

    ///pre: size != 0
    public static Object element(ArrayQueueADT queue) {
        assert queue.size > 0;
        //pre: begin' = first element in queue
        return queue.elements[queue.begin];
    }

    //pre: size > 0
    public static Object dequeue(ArrayQueueADT queue) {
        assert queue.size > 0;
        //pre: elements[being'] - first element in queue
        Object elem = queue.elements[queue.begin];
        //post: elem = first element in queue

        //pre: elements[being'] - first element in queue
        queue.begin = (queue.begin + 1) % queue.elements.length;
        //post: elements[being'] - second element in queue

        queue.size--;
        //post: size' = size' - 1 => elements[being'] - first element in queue && tail' = (begin' + size) % len' && elements[tail'] - place for next element
        return elem;
    }

    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }
    //post: return size

    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.size == 0;
    }
    //post: return answer: is queue empty?

    public static void clear(ArrayQueueADT queue) {
        queue.elements = new Object[5];
        queue.size = queue.begin = 0;
    }
    //post: begin' = size' = 0 => tail' = (begin + size) % len = 0 => queue is empty

    public static Object[] toArray(ArrayQueueADT queue) {
        Object[] arr = new Object[queue.size];

        if(queue.begin + queue.size > queue.elements.length) {
            System.arraycopy(queue.elements, queue.begin, arr, 0, queue.elements.length - queue.begin);
            System.arraycopy(queue.elements, 0, arr, queue.elements.length - queue.begin, (queue.begin + queue.size) % queue.elements.length );
        } else {
            System.arraycopy(queue.elements, queue.begin, arr, 0, queue.size);
        }

        return arr;
    }

    public static void push(ArrayQueueADT queue, Object element) {
        assert element != null;

        ensureCapacity(queue, ++queue.size);
        if (--queue.begin < 0) {
            queue.begin += queue.elements.length;
        }
        queue.elements[queue.begin] = element;
    }

    public static Object peek(ArrayQueueADT queue) {
        assert queue.size > 0;

        return queue.elements[(queue.begin + queue.size - 1) % queue.elements.length];
    }

    public static Object remove(ArrayQueueADT queue) {
        assert queue.size > 0;

        Object elem = queue.elements[(queue.begin + --queue.size) % queue.elements.length];

        if (queue.size < 0) {
            queue.size += queue.elements.length;
        }

        return elem;
    }
}
