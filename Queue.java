package queue;

public interface Queue {
    void enqueue(Object element);
    /*public*/ int size();
    /*public*/ boolean isEmpty();
    Object dequeue();
    Object[] toArray();
    Object remove();

}
