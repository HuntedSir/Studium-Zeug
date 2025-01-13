package h04.strategy;

import fopbot.Field;
import fopbot.Robot;

/**
 * Is a MoveStrategy in which a robot teleports to the given field.
 */
public class MoveByTeleport implements  MoveStrategy{
    /**
     * Teleports the robot to the given field.
     * @param robot is the robot that will be teleported.
     * @param field is the field that will be teleported to.
     */
    public void start(Robot robot, Field field) {
        robot.setField(field.getX(), field.getY());
    }
}
