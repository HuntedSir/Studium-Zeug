package h05.model;

/**
 * The interface Cpu, which defines the methods a cpu can have.
 */
public interface CPU extends Component{
    /**
     * Gets socket, which can fit only certain type of CPUs.
     *
     * @return the socket
     */
    Socket getSocket();

    /**
     * Gets number of cores.
     *
     * @return the number of cores
     */
    int getNumberOfCores();

    /**
     * Gets frequency.
     *
     * @return the frequency
     */
    double getFrequency();
}
