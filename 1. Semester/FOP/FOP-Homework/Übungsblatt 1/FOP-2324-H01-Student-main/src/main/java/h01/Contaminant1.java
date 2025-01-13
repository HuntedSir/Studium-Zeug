package h01;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.RobotFamily;
import h01.template.Contaminant;
import h01.template.GameConstants;
import h01.template.TickBased;
import h01.template.Utils;
import org.tudalgo.algoutils.student.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link Contaminant}-{@link Robot} that moves randomly and contaminates the floor.
 */
public class Contaminant1 extends Robot implements Contaminant, TickBased {

    /**
     * Creates a new {@link Contaminant1}.
     *
     * @param x             the initial x coordinate of the robot
     * @param y             the initial y coordinate of the robot
     * @param direction     the initial direction of the robot
     * @param numberOfCoins the initial number of coins of the robot
     */
    public Contaminant1(final int x, final int y, final Direction direction, final int numberOfCoins) {
        super(x, y, direction, numberOfCoins, RobotFamily.SQUARE_ORANGE);
    }

    @Override
    public int getUpdateDelay() {
        return 10;
    }

    @Override
    public void doMove() {
        if (!super.hasAnyCoins()){
            super.turnOff();
        }

        if (super.isTurnedOff() || !super.hasAnyCoins()) {
            return;
        }

        int numberOfCoins = Utils.getRandomInteger(GameConstants.CONTAMINANT_ONE_MIN_PUT_COINS, GameConstants.CONTAMINANT_ONE_MAX_PUT_COINS);

        while (numberOfCoins > 0) {
            if (Utils.getCoinAmount(super.getX(), super.getY()) < 20){
                if (super.hasAnyCoins()) {
                    super.putCoin();
                    numberOfCoins--;
                }
            }
            else {
                break;
            }
        }

        List<Integer> clearDirectionsList = new ArrayList<>();
        int index = 0;
        while (index <= 3) {
            if(super.isFrontClear()){
                clearDirectionsList.add(index);
            }
            super.turnLeft();
            index++;
        }
        // get random direction and check whether it's clear or not
        int randomIndex = Utils.getRandomInteger(0, clearDirectionsList.size()-1);
        int numberOfTurns = clearDirectionsList.get(randomIndex);
        for (int i = 0; i < numberOfTurns; i++) {
            super.turnLeft();
        }

        if(isFrontClear()){
            super.move();
        }
    }
}
