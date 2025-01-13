package h05.model;

/**
 * Memory, one of the components of the computer.
 */
public class MemoryImpl extends PurchasedComponent implements Memory{
    private final char capacity;

    /**
     * Instantiates a new Memory with the given attributes.
     *
     * @param capacity the capacity
     * @param price    the price
     */
    public MemoryImpl(char capacity, double price){
        super(price);
        this.capacity = capacity;
    }
    @Override
    public char getCapacity() {
        return this.capacity;
    }
}
