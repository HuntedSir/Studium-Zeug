package h04.strategy;

import fopbot.Direction;
import fopbot.Field;
import fopbot.Robot;
import fopbot.World;

/**
 * Is a MoveStrategy in which the robot is moved to a field using turns and moves.
 */
public class MoveByWalk implements  MoveStrategyWithCounter{
    private int moveCount = -1;

    /**
     * Move the robot to the given field
     * @param robot is the robot that will move.
     * @param field is the field that the robot will be moved to.
     */
    public void start(Robot robot, Field field) {

        this.moveCount = 0;

        // Align the robot to the set Y coordinate
        int yVariable = field.getY();
        while (robot.getY() != yVariable) {
            if (robot.getY() < yVariable) {
                while (robot.getDirection() != Direction.UP){
                    robot.turnLeft();
                }
                robot.move();
                this.moveCount++;
            }
            else {
                while (robot.getDirection() != Direction.DOWN){
                    robot.turnLeft();
                }
                robot.move();
                this.moveCount++;
            }
        }

        // Align the robot to the set X coordinate
        int xVariable = field.getX();
        while (robot.getX() != xVariable) {
            if (robot.getX() < xVariable) {
                while (robot.getDirection() != Direction.RIGHT){
                    robot.turnLeft();
                }
                robot.move();
                this.moveCount++;
            }
            else {
                while (robot.getDirection() != Direction.LEFT){
                    robot.turnLeft();
                }
                robot.move();
                this.moveCount++;
            }
        }
    }

    /**
     * Returns the number of moves the robot took.
     * @return
     */
    public int getMoveCount() {
        return this.moveCount;
    }
}
