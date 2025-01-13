package h05.model;

/**
 * The Peripheral, a component which can fit into a peripheral slot.
 */
public class PeripheralImpl extends PurchasedComponent implements Peripheral{
    private final PeripheralType peripheralType;

    /**
     * Instantiates a new Peripheral with the given attributes.
     *
     * @param peripheralType the peripheral type
     * @param price          the price
     */
    public PeripheralImpl(PeripheralType peripheralType, int price){
        super(price);
        this.peripheralType = peripheralType;
    }

    @Override
    public PeripheralType getPeripheralType() {
        return this.peripheralType;
    }
}
