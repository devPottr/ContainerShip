import java.util.Objects;

/**
 * A generic class to represent a pair of two elements.
 * @param <K> The type of the first element (key).
 * @param <V> The type of the second element (value).
 */
public class Pair<K, V> {
    // The first element (key) of the pair
    private final K key;

    // The second element (value) of the pair
    private final V value;

    /**
     * Constructs a new Pair with the given key and value.
     * @param key   The key for this pair.
     * @param value The value to use for this pair.
     */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the key for this pair.
     * @return The key for this pair.
     */
    public K getKey() {
        return key;
    }

    /**
     * Returns the value for this pair.
     * @return The value for this pair.
     */
    public V getValue() {
        return value;
    }

    /**
     * Checks the equality of this pair with another object.
     * @param o The object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (!Objects.equals(key, pair.key)) return false;
        return Objects.equals(value, pair.value);
    }

    /**
     * Generates a hash code for this pair.
     * @return The hash code for this pair.
     */
    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    /**
     * Returns a string representation of this pair.
     * @return A string representation of this pair.
     */
    @Override
    public String toString() {
        return "Pair{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
