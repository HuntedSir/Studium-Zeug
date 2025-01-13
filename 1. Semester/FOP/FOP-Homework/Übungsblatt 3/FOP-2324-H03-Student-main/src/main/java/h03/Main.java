package h03;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.RobotFamily;
import fopbot.World;
import h03.robots.ChessBoardRobot;
import h03.robots.MultiFamilyRobot;
import h03.robots.RGBRobot;

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
        World.setSize(20, 20);

        // make it possible to see the world window
        World.setVisible(true);

        RobotFamily[] family = new RobotFamily[3];
        family[0] = RobotFamily.SQUARE_ORANGE;
        family[1] = RobotFamily.SQUARE_YELLOW;
        family[2] = RobotFamily.SQUARE_GREEN;


        MultiFamilyRobot familyMan = new MultiFamilyRobot(0, 0, family);

        for (int i = 0; i < family.length + 1; i++) {
            if (familyMan.isFrontClear()) {
                familyMan.move();
            }
        }

        RGBRobot rgb1 = new RGBRobot(1, 1, true);
        RGBRobot rgb2 = new RGBRobot(2, 2, false);

        ChessBoardRobot chessBot1 = new ChessBoardRobot(5,5);
        ChessBoardRobot chessBot2 = new ChessBoardRobot(5,6);
        ChessBoardRobot chessBot3 = new ChessBoardRobot(6,5);
        ChessBoardRobot chessBot4 = new ChessBoardRobot(6,6);

        Robot[] robots = new Robot[7];
        robots[0] = familyMan;
        robots[1] = rgb1;
        robots[2] = rgb2;
        robots[3] = chessBot1;
        robots[4] = chessBot2;
        robots[5] = chessBot3;
        robots[6] = chessBot4;

        for (int i = 3; i < robots.length; i++) {
            robots[i].move();
            robots[i].move();
        }


        RobotSynchronizer synchronizer = new RobotSynchronizer(robots);
        synchronizer.setY(4);
        synchronizer.setX(4);
        synchronizer.setDirection(Direction.DOWN);
        synchronizer.sync();
    }
}
