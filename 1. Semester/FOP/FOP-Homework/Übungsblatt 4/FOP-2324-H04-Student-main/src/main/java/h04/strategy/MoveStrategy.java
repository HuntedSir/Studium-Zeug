package h04.strategy;

import fopbot.Field;
import fopbot.Robot;

/**
 * Describes the method in which the robot will move.
 */
public interface MoveStrategy {
    /**
     * Moves the robot with a given move strategy.
     * @param robot which will be moved.
     * @param field is the field that the robot will move to.
     */
    public void start(Robot robot, Field field);
}
