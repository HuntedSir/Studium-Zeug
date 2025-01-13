package h11;

import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * A {@link Warehouse} manages a list of {@link Product}s and provides methods to query information about them.
 * It also contains a maximum capacity, which it can hold and a current capacity, which specifies how many {@link Product}s
 * are in the {@link Warehouse} currently
 */
public class Warehouse {

    @DoNotTouch
    public List<Product> products = new ArrayList<>();
    @DoNotTouch
    private int maxCapacity = 100;
    @DoNotTouch
    private int currentCapacity = 0;


    @DoNotTouch
    public Warehouse(List<Product> products) {
        this.products = products;
    }


    /**
     * Gets price of a given product.
     *
     * @param prod the prod
     * @return the price
     */
    @StudentImplementationRequired
    public double getPrice(@Nullable Product prod) {
        //H2.1
        var optional = Optional.ofNullable(prod).map(product -> product.price()).orElse(0.0);
        return optional;
    }

    @DoNotTouch
    public List<Product> getProducts() {
        return this.products;
    }

    /**
     * Gets products that match the predicates.
     *
     * @param predicate the predicate
     * @return the products
     */
    @StudentImplementationRequired
    public List<Product> getProducts(Predicate<? super Product> predicate) {
        //H2.2
        return this.products.stream().filter(product -> predicate.test(product)).toList();
    }

    /**
     * Sets max capacity.
     *
     * @param maxCapacity the max capacity
     */
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }


    /**
     * Gets current capacity.
     *
     * @return the current capacity
     */
    @DoNotTouch
    public int getCurrentCapacity() {
        return currentCapacity;
    }

    /**
     * Gets max capacity.
     *
     * @return the max capacity
     */
    @DoNotTouch
    public int getMaxCapacity() {
        return maxCapacity;
    }

    /**
     * Sets current capacity.
     *
     * @param currentCapacity the current capacity
     */
    @DoNotTouch
    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    /**
     * Gets total quantity of product.
     *
     * @param product the product
     * @return the total quantity of product
     */
    @StudentImplementationRequired
    public long getTotalQuantityOfProduct(Product product) {
        //H2.3
        return this.products.stream().filter(product1 -> product1==product).count();
    }

    /**
     * Gets total price.
     *
     * @return the total price
     */
    @StudentImplementationRequired
    public double getTotalPrice() {
        //H2.4
        return this.products.stream().map(product -> product.price()).reduce(0.0, (x, y) -> x+y);
    }

    /**
     * Add products.
     *
     * @param product          the product
     * @param numberOfProducts the number of products
     */
    @StudentImplementationRequired
    public void addProducts(Product product, int numberOfProducts) {
        //H2.6
        var newProducts = generateProducts(product.type(), product.price(), product.name()).limit(numberOfProducts).toList();
        this.products = Stream.concat(this.products.stream(), newProducts.stream()).toList();
    }

    /**
     * Generate products stream.
     *
     * @param typ   the typ
     * @param price the price
     * @param name  the name
     * @return the stream
     */
    @StudentImplementationRequired
    public Stream<Product> generateProducts(ProductType typ, double price, String name) {
        //H2.5
        return Stream.generate(() -> new Product(typ, price, 1, name));
    }
}
