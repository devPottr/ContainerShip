import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.stream.Stream;

public class MyStackArray<E> {
    private static final int MAX_CAPACITY = 35;
    private Object[] elements;
    private int size;

    public MyStackArray() {
        this.elements = new Object[MAX_CAPACITY];
        this.size = 0;
    }

    public void push(E element) {
        if (size >= MAX_CAPACITY) {
            throw new IllegalStateException("Stack is full");
        }
        elements[size++] = element;
    }

    @SuppressWarnings("unchecked")
    public E pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        E element = (E) elements[--size];
        elements[size] = null; // Remove reference for GC
        return element;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    public E peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return (E) elements[size - 1];
    }

    public int size() {
        return size;
    }

    public boolean isFull() {
        return size == MAX_CAPACITY;
    }

    public Stream<E> stream() {
        return Arrays.stream(elements, 0, size).map(element -> (E) element);
    }
}
