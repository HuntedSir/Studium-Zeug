package h05.model;

/**
 * The Mainboard, a component of the computer, which has certain slots that fits other components into them.
 */
public class MainboardImpl extends PurchasedComponent implements Mainboard{
    private final Socket socket;
    private CPU cpu;
    private Memory[] memories;
    private Peripheral[] peripherals;

    /**
     * Instantiates a new Mainboard with given attributes.
     *
     * @param socket                  the socket
     * @param numberOfMemorySlots     the number of memory slots
     * @param numberOfPeripheralSlots the number of peripheral slots
     * @param price                   the price
     */
    public MainboardImpl(Socket socket, int numberOfMemorySlots, int numberOfPeripheralSlots, double price){
        super(price);
        this.socket = socket;
        this.memories = new Memory[numberOfMemorySlots];
        this.peripherals = new Peripheral[numberOfPeripheralSlots];
    }

    /**
     * Adds a cpu to the motherboard if the socket is free.
     *
     * @param cpu the cpu
     * @return Returns whether the cpu could be added or not.
     */
    public boolean addCPU(CPU cpu){
        boolean cpuInsterted = false;

        if(this.cpu==null && cpu != null && this.socket == cpu.getSocket()) {
            this.cpu = cpu;
            cpuInsterted=true;
        }

        return cpuInsterted;
    }

    /**
     * Adds memory to the motherboard if there is a free slot.
     *
     * @param memory the memory
     * @return Returns whether a memory was added or not as a boolean.
     */
    public boolean addMemory(Memory memory){
        boolean memoryInserted = false;

        for (int i = 0; i < this.memories.length; i++) {
            if(this.memories[i]== null){
                this.memories[i]=memory;
                memoryInserted = true;
                break;
            }
        }

        return memoryInserted;
    }

    /**
     * Adds a peripheral to the motherboard if there is a free slot.
     *
     * @param peripheral the peripheral
     * @return Returns whether a peripheral was added or not.
     */
    public boolean addPeripheral(Peripheral peripheral){
        boolean peripheralInserted = false;

        for (int i = 0; i < this.peripherals.length; i++) {
            if(this.peripherals[i]== null){
                this.peripherals[i]=peripheral;
                peripheralInserted = true;
                break;
            }
        }

        return peripheralInserted;
    }

    @Override
    public void rateBy(ComponentRater componentRater) {
        componentRater.consumeMainboard(this);
        if(this.cpu!=null){
            componentRater.consumeCPU(this.cpu);
        }
        if (this.memories != null) {
            for (int i = 0; i < this.memories.length; i++) {
                if (this.memories[i] != null) {
                    componentRater.consumeMemory(this.memories[i]);
                }
            }
        }
        if(this.peripherals!=null) {
            for (int i = 0; i < this.peripherals.length; i++) {
                if (this.peripherals[i] != null) {
                    componentRater.consumePeripheral(this.peripherals[i]);
                }
            }
        }
    }
}
