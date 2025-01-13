package h05.model;

/**
 * The interface Peripheral, which defines the methods a peripheral can have.
 */
public interface Peripheral extends Component{
    /**
     * Gets peripheral type.
     *
     * @return the peripheral type
     */
    PeripheralType getPeripheralType();
}
