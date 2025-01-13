package h03.robots;

import fopbot.RobotFamily;
/**
 * A Robot that can be either black or white, depending on the coordinate he is standing on.
 * */
public class ChessBoardRobot  extends MultiFamilyRobot{

    /**
     * Creates a new ChessBoardRobot with following parameters:
     * @param x coordinate in the world.
     * @param y coordinate in the world.
     * @param initialEven color of the robot if he is on an uneven coordinate.
     * @param initialOdd color of the robot if he is on an odd coordinate.
     */
    public ChessBoardRobot (int x, int y, RobotFamily initialEven, RobotFamily initialOdd) {
        super(x, y, ((x+y)%2 == 0) ? new RobotFamily[] {initialEven, initialOdd}
                                    : new RobotFamily[] {initialOdd, initialEven});
    }

    /**
     * Creates a new ChessBoardRobot with following parameters:
     * @param x coordinate in the world.
     * @param y coordinate in the world.
     * */
    public ChessBoardRobot (int x, int y) {
        this(x, y, RobotFamily.SQUARE_BLACK, RobotFamily.SQUARE_WHITE);
    }
}
