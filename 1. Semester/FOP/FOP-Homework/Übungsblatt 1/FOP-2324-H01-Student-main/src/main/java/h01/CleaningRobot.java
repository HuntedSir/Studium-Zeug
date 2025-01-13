package h01;

import fopbot.Direction;
import fopbot.Robot;
import h01.template.Cleaner;
import h01.template.GameConstants;
import h01.template.TickBased;
import org.tudalgo.algoutils.student.Student;

/**
 * A robot that can clean the floor.
 */
public class CleaningRobot extends Robot implements Cleaner, TickBased {

    /**
     * Creates a new {@link CleaningRobot}.
     *
     * @param x             the initial x coordinate of the robot
     * @param y             the initial y coordinate of the robot
     * @param direction     the initial direction of the robot
     * @param numberOfCoins the initial number of coins of the robot
     */
    public CleaningRobot(final int x, final int y, final Direction direction, final int numberOfCoins) {
        super(x, y, direction, numberOfCoins);
    }

    @Override
    public void handleKeyInput(final int direction, final boolean shouldPutCoins, final boolean shouldPickCoins) {
        if (shouldPickCoins == true && super.getNumberOfCoins() < GameConstants.CLEANER_CAPACITY) {
            if(super.isOnACoin()) {
                super.pickCoin();
            }
        }

        if (shouldPutCoins == true) {
            if(super.hasAnyCoins()) {
                super.putCoin();
            }
        }

        boolean superHasMoved = false;

        if(direction == 0) {
            while (superHasMoved == false) {
                if (super.isFacingUp() == true) {
                    if (super.isFrontClear()) {
                        super.move();
                    }
                    superHasMoved = true;
                }
                else {
                    super.turnLeft();
                }
            }
        }
        if(direction == 1) {
            while (superHasMoved == false) {
                if (super.isFacingRight() == true) {
                    if (super.isFrontClear()) {
                        super.move();
                    }
                    superHasMoved = true;
                }
                else {
                    super.turnLeft();
                }
            }
        }
        if(direction == 2) {
            while (superHasMoved == false) {
                if (super.isFacingDown() == true) {
                    if (super.isFrontClear()) {
                        super.move();
                    }
                    superHasMoved = true;
                }
                else {
                    super.turnLeft();
                }
            }
        }
        if(direction == 3) {
            while (superHasMoved == false) {
                if (super.isFacingLeft() == true) {
                    if (super.isFrontClear()) {
                        super.move();
                    }
                    superHasMoved = true;
                }
                else {
                    super.turnLeft();
                }
            }
        }
        superHasMoved = false;
    }
}
