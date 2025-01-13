package h09.function;

import java.util.function.Function;
import java.util.function.Predicate;

import h09.stack.StackOfObjects;

/**
 * A collection of operations usable for rooms.
 */
public class StackOfObjectsOperations {

    /**
     * Returns a stack containing all objects of the given stack in reversed order for which the given filter returns true.
     *
     * @param in     the input stack
     * @param filter the filter
     * @return the output stack
     */
    public static <X, Y> StackOfObjects<Y> filter(
        StackOfObjects<X> in,
        Predicate<X> filter
    ) {
        StackOfObjects<Y> out = new StackOfObjects<>();
        while (in.numberOfObjects() > 0) {
            X element = in.pop();
            if (filter.test(element)) {
                out.push((Y)element);
            }
        }
        return out;
    }

    /**
     * Returns a stack containing all objects of the given stack mapped using the given function.
     *
     * @param in       the input stack
     * @param function the function
     * @return the output stack
     */
    public static <X, Y> StackOfObjects<Y> map(
        StackOfObjects<X> in,
        Function<X, Y> function
    ) {
        StackOfObjects<Y> out = new StackOfObjects<>();
        while (in.numberOfObjects() > 0) {
            X from = in.pop();
            Y to = function.apply(from);
            out.push(to);
        }
        return out;
    }

    private StackOfObjectsOperations() {
    }
}
