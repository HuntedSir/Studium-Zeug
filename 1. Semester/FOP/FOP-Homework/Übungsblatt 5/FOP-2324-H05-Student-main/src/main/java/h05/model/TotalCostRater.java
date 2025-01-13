package h05.model;

/**
 * The type Total cost rater, which rates the total cost of a motherboard.
 */
public class TotalCostRater implements ComponentRater{
    private double cost;

    /**
     * Instantiates a new Total cost rater.
     */
    public TotalCostRater(){

    }

    /**
     * Get total price double that was calculates using this classes consume methods.
     *
     * @return the double
     */
    public double getTotalPrice(){
        return this.cost;
    }
    @Override
    public void consumeMainboard(Mainboard mainboard) {
        this.cost = this.cost + mainboard.getPrice();
    }

    @Override
    public void consumeCPU(CPU cpu) {
        this.cost = this.cost + cpu.getPrice();
    }

    @Override
    public void consumeMemory(Memory memory) {
        this.cost = this.cost + memory.getPrice();
    }

    @Override
    public void consumePeripheral(Peripheral peripheral) {
        this.cost = this.cost + peripheral.getPrice();
    }
}
