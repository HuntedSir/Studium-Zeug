package h03.robots;

import fopbot.Robot;
import fopbot.RobotFamily;

/**
 * A Robot that has a specific collection of colors that he can change to.
 */
public class MultiFamilyRobot extends Robot {
    private final RobotFamily[] families;
    public int index;

    /**
     * Creates a new MultiFamilyRobot with following parameters:
     * @param x coordinate in the world.
     * @param y coordinate in the world.
     * @param families Array of colors the robot can have.
     */
    public MultiFamilyRobot (int x, int y, RobotFamily[] families) {
        super(x, y, families[0]);
        this.families = families;
        this.index = 0;
    }

    /**
     * The robot changes his family to the next one in the Array of all families.
     */
    public void exchange() {
        index = index + 1;
        super.setRobotFamily(families[index % families.length]);
    }
    /**
     * The robot moves and changes his family.
     */
    @Override
    public void move() {
        super.move();
        exchange();
    }

    /**
     * The robot moves and exchanges his family depending on whether exchange is true or not
     * @param exchange
     */
    public void move (boolean exchange) {
        super.move();
        if (exchange)  {
            exchange();
        }
    }

}
