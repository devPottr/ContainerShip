import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.stream.Stream;

/**
 * A stack implementation using an array.
 * @param <E> The type of elements stored in this stack.
 */
public class MyStackArray<E> {
    /**
     * Maximum capacity of the stack
     */
    private int maxCapacity;

    /**
     * Array to internally store the elements
     */
    private Object[] elements;

    /**
     * Current size of the stack
     */
    private int size;

    /**
     * Initializes an empty stack with a maximum capacity.
     */
    public MyStackArray(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.elements = new Object[maxCapacity];
        this.size = 0;
    }

    /**
     * Pushes an element onto the stack.
     * @param element The element to be pushed onto the stack.
     * @throws IllegalStateException if the stack is full.
     */
    public void push(E element) {
        if (size >= maxCapacity) {
            throw new IllegalStateException("Stack is full");
        }
        elements[size++] = element;
    }

    /**
     * Pops an element from the stack.
     * @return The element at the top of the stack.
     * @throws EmptyStackException if the stack is empty.
     */
    @SuppressWarnings("unchecked")
    public E pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        E element = (E) elements[--size];
        elements[size] = null; // Remove reference for GC
        return element;
    }

    /**
     * Checks if the stack is empty.
     * @return true if the stack is empty, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Peeks at the element at the top of the stack without removing it.
     * @return The element at the top of the stack.
     * @throws EmptyStackException if the stack is empty.
     */
    @SuppressWarnings("unchecked")
    public E peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return (E) elements[size - 1];
    }

    /**
     * Returns the current size of the stack.
     * @return The current size of the stack.
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the stack is full.
     * @return true if the stack is full, false otherwise.
     */
    public boolean isFull() {
        return size == maxCapacity;
    }

    /**
     * Returns a stream of the elements in the stack.
     * @return A stream of the elements in the stack.
     */
    public Stream<E> stream() {
        return Arrays.stream(elements, 0, size).map(element -> (E) element);
    }
}
