package h11;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * A {@link Company} manages a list of {@link Department}s and a list of {@link Warehouse}s and provides
 * methods to query information about them.
 *
 * @param departments The departments of this company.
 * @param warehouses  The warehouses of this company.
 */
public record Company(@DoNotTouch List<Department> departments, @DoNotTouch List<Warehouse> warehouses) {

    /**
     * Creates a new {@link Company} with the given departments and warehouses.
     *
     * @param departments the departments
     * @param warehouses  the warehouses
     */
    @DoNotTouch
    public Company {
    }

    /**
     * Gets list of all employees.
     *
     * @return the list of all employee
     */
    @StudentImplementationRequired
    public List<Employee> getListOfAllEmployee() {
        //H3.1
        return this.departments.stream().flatMap(department -> department.employees().stream()).toList();
    }

    /**
     * Gets quantity of product in all warehouses.
     *
     * @param product the product
     * @return the quantity of product
     */
    @StudentImplementationRequired
    public long getQuantityOfProduct(Product product) {
        //H3.2
        return this.warehouses.stream()
            .flatMap(warehouse -> warehouse.products.stream())
            .filter(product1 -> product1.equals(product)).count();
    }

    /**
     * Gets filtered product names.
     *
     * @param predicates the predicates
     * @return the filtered product names
     */
    @StudentImplementationRequired
    public List<String> getFilteredProductNames(List<Predicate<Product>> predicates) {
        //H3.3
        return this.warehouses.stream()
            .flatMap(warehouse -> warehouse.products.stream())
            .filter(product -> predicates.stream()
            .allMatch(productPredicate -> productPredicate.test(product)))
            .map(product -> product.name()).toList();
    }

    /**
     * Gets all products, where the price is between the 2 parameters sorted by price..
     *
     * @param low  the low
     * @param high the high
     * @return the list
     */
    @StudentImplementationRequired
    public List<Product> priceRange(double low, double high) {
        //H3.4
        Comparator<Product> comparator = new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                if(o1.price()==o2.price()) {
                    return 0;
                } else if (o1.price()<o2.price()) {
                    return -1;
                }
                else if (o1.price()>o2.price()) {
                    return 1;
                }
                return 0;
            }
        };
        return this.warehouses.stream()
            .flatMap(warehouse -> warehouse.products.stream())
            .filter(product -> product.price()<high && product.price()>low)
            .sorted(Comparator.comparingDouble(Product -> Product.price())).toList();
    }

    /**
     * Gets employees sorted by name.
     *
     * @return the employees sorted by name
     */
    @StudentImplementationRequired
    public List<String> getEmployeesSortedByName() {
        //H3.5
        return this.departments.stream()
            .flatMap(department -> department.employees().stream())
            .map(employee -> employee.getName()
            .replaceAll("(\\b\\w+\\b)\\s+(\\b\\w+\\b)", "$2, $1"))
            .sorted()
            .toList();
    }

    /**
     * Gets all products by type.
     *
     * @param type             the type
     * @param numberOfProducts the number of products
     * @return the all products by type
     */
    @StudentImplementationRequired
    public List<String> getAllProductsByType(ProductType type, int numberOfProducts) {
        //H3.6
        return null;
    }

    @Override
    public List<Department> departments() {
        return departments;
    }

    @Override
    public List<Warehouse> warehouses() {
        return warehouses;
    }
}
