package h05.model;

/**
 * The Cpu, one of the components of the computer.
 */
public class CPUImpl extends PurchasedComponent implements CPU{
    private final Socket socket;
    private final int numOfCores;
    private final double frequency;

    /**
     * Instantiates a new Cpu with the given attributes.
     *
     * @param socket     the socket
     * @param numOfCores the num of cores
     * @param frequency  the frequency
     * @param price      the price
     */
    public CPUImpl(Socket socket, int numOfCores, double frequency, double price){
        super(price);
        this.socket = socket;
        this.numOfCores = numOfCores;
        this.frequency = frequency;
    }
    @Override
    public Socket getSocket() {
        return this.socket;
    }

    @Override
    public int getNumberOfCores() {
        return this.numOfCores;
    }

    @Override
    public double getFrequency() {
        return this.frequency;
    }
}
