package h05;

import h05.model.*;


/**
 * Main entry point in executing the program.
 */
public class Main {
    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {
        MainboardImpl mainboard = new MainboardImpl(Socket.AM4, 2, 2, 100);
        CPUImpl cpu = new CPUImpl(Socket.AM4, 10, 3.3, 300);
        //MemoryImpl memory = new MemoryImpl('8', 60);
        VirtualMemory memory = new VirtualMemory( 10);
        memory.setCapacity('8');
        PeripheralImpl peripheral = new PeripheralImpl(PeripheralType.GPU, 300);

        mainboard.addCPU(cpu);
        mainboard.addMemory(memory);
        mainboard.addPeripheral(peripheral);

        TotalCostRater totalCostRater = new TotalCostRater();
        MachineLearningRater machineLearningRater = new MachineLearningRater();

        mainboard.rateBy(totalCostRater);
        double totalCost = totalCostRater.getTotalPrice();
        System.out.println(totalCost);

        mainboard.rateBy(machineLearningRater);
        double score = machineLearningRater.checkModel(2);
        System.out.println(score);



    }
}
