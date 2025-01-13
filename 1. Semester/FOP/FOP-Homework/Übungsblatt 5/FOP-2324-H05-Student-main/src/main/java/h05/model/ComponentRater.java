package h05.model;

/**
 * The interface Component rater, which rates the computer based on its components with a given rating algorythm.
 */
public interface ComponentRater {
    /**
     * Get the needed mainboard attributes to rate it with.
     *
     * @param mainboard the mainboard
     */
    void consumeMainboard(Mainboard mainboard);

    /**
     * Get the needed cpu attributes to rate it with.
     *
     * @param cpu the cpu
     */
    void consumeCPU(CPU cpu);

    /**
     * Get the needed memory attributes to rate it with.
     *
     * @param memory the memory
     */
    void consumeMemory(Memory memory);

    /**
     * Get the needed peripheral attributes to rate it with.
     *
     * @param peripheral the peripheral
     */
    void consumePeripheral(Peripheral peripheral);
}
