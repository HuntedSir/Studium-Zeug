package h05.model;

/**
 * The type Virtual memory, memory that doesn't need to be placed into a memory slot on the motherboard.
 */
public class VirtualMemory implements Memory{
    private double costPerGigaByte;
    private char capacity;

    /**
     * Instantiates a new Virtual memory with a given cost per gigabyte.
     *
     * @param costPerGigaByte the cost per gigabyte
     */
    public VirtualMemory(double costPerGigaByte){
        this.costPerGigaByte = costPerGigaByte;
    }
    @Override
    public double getPrice() {
        double price = 0;

        int capacityAsInt = this.capacity - '0';
        price = capacityAsInt * costPerGigaByte;

        return price;
    }

    /**
     * Set capacity.
     *
     * @param capacity the capacity
     */
    public void setCapacity(char capacity){
        this.capacity = capacity;
    }

    @Override
    public char getCapacity() {
        return this.capacity;
    }
}
