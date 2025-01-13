package h05.model;

/**
 * The Machine learning rater, which rates the computer based on its ability in machine learning.
 */
public class MachineLearningRater implements ComponentRater{
    private int numberOfTPUs = 0;
    private int memoryCapacity = 0;

    /**
     * Instantiates a new Machine learning rater.
     */
    public MachineLearningRater(){

    }

    /**
     * Check model rates the computer based on the attributes of the parts in the computer.
     *
     * @param modelSize the model size
     * @return the double
     */
    public double checkModel(int modelSize){
        double score = 0;

        double tpuFactor = 100-(100-1)*Math.pow(1.02, -numberOfTPUs);

        score = (memoryCapacity/modelSize)*tpuFactor;

        return score;
    }
    @Override
    public void consumeMainboard(Mainboard mainboard) {

    }

    @Override
    public void consumeCPU(CPU cpu) {

    }

    @Override
    public void consumeMemory(Memory memory) {
        int memoryCapacity = memory.getCapacity() - '0';
        this.memoryCapacity = this.memoryCapacity + memoryCapacity;
    }

    @Override
    public void consumePeripheral(Peripheral peripheral) {
        if(peripheral.getPeripheralType() == PeripheralType.TPU){
            this.numberOfTPUs++;
        }
    }
}
