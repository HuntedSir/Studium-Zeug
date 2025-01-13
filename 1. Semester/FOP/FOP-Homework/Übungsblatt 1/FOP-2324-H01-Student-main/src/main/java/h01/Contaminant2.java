package h01;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.RobotFamily;
import h01.template.Contaminant;
import h01.template.TickBased;
import h01.template.Utils;
import org.tudalgo.algoutils.student.Student;

/**
 * A {@link Contaminant}-{@link Robot} that moves in a predefined way and contaminates the floor.
 */
public class Contaminant2 extends Robot implements Contaminant, TickBased {

    /**
     * Creates a new {@link Contaminant2}.
     *
     * @param x             the initial x coordinate of the robot
     * @param y             the initial y coordinate of the robot
     * @param direction     the initial direction of the robot
     * @param numberOfCoins the initial number of coins of the robot
     */
    public Contaminant2(final int x, final int y, final Direction direction, final int numberOfCoins) {
        super(x, y, direction, numberOfCoins, RobotFamily.SQUARE_AQUA);
    }

    @Override
    public int getUpdateDelay() {
        return 8;
    }

    @Override
    public void doMove() {
        if (!super.hasAnyCoins()){
            super.turnOff();
        }

        if (super.isTurnedOff() || !super.hasAnyCoins()) {
            return;
        }
        int fieldNumberOfCoins = Utils.getCoinAmount(super.getX(), super.getY());
        if (fieldNumberOfCoins == 0){
            if(super.hasAnyCoins()) {
            super.putCoin();
            }
            if(super.hasAnyCoins()) {
                super.putCoin();
            }
        }
        if (fieldNumberOfCoins == 1){
            if(super.hasAnyCoins()) {
                super.putCoin();
            }
        }

        boolean[] clearDirections = new boolean[4];
        int index = 0;
        while (index <= 3) {
            if(super.isFrontClear()){
                clearDirections[index] = true;
            }
            else {
                clearDirections[index] = false;
            }
            super.turnLeft();
            index++;
        }

            if (clearDirections[1]) {
                super.turnLeft();
            } else if (clearDirections[0]) {

            }else if (clearDirections[3]) {
                super.turnLeft();
                super.turnLeft();
                super.turnLeft();
            }
            else if (clearDirections[2]) {
                super.turnLeft();
                super.turnLeft();
            }
        if(super.isFrontClear()){
            super.move();
        }
    }
}
