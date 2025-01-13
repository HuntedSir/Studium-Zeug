package h05.model;

/**
 * All components have a price, so we all of them share the attribute price and the method to get it.
 */
public abstract class PurchasedComponent implements Component{
    private final double price;

    /**
     * Instantiates a new Purchased component with a price.
     *
     * @param price the price
     */
    public PurchasedComponent(double price){
        this.price = price;
    }
    @Override
    public double getPrice() {
        return this.price;
    }
}
