package h03.robots;
import fopbot.Robot;
import fopbot.RobotFamily;

/**
 * A Robot that can changes its colors from red to green to blue, or the other way around if it's inverted.
 * */
public class RGBRobot extends MultiFamilyRobot {

    /**
     * Creates a new RGBRobot with following parameters:
     * @param x coordinate in the world.
     * @param y coordinate in the world.
     * @param inverted Defines the order of colors the robot switches to.
     */
    public RGBRobot (int x, int y, boolean inverted) {
        super(x, y , initialiseFamilies(inverted));
    }
    private static RobotFamily[] initialiseFamilies(boolean inverted){
        RobotFamily[] robotFamilies = new RobotFamily[3];
        if (!inverted){
            robotFamilies[0] = RobotFamily.SQUARE_RED;
            robotFamilies[1] = RobotFamily.SQUARE_GREEN;
            robotFamilies[2] = RobotFamily.SQUARE_BLUE;
        }
        else {
            robotFamilies[0] = RobotFamily.SQUARE_BLUE;
            robotFamilies[1] = RobotFamily.SQUARE_GREEN;
            robotFamilies[2] = RobotFamily.SQUARE_RED;
        }
        return robotFamilies;
    }
    /**
     * Tests the RGBRobot by changing its colors 3 times.
     */
    public void testRGB () {
        super.exchange();
        super.exchange();
        super.exchange();
    }
}
