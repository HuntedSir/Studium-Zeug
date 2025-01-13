package h04;

import fopbot.Robot;
import fopbot.World;
import h04.robot.MoveableRobot;
import h04.robot.RobotMover;
import h04.selection.FieldSelectionListener;
import h04.selection.KeyboardFieldSelector;
import h04.selection.MouseFieldSelector;
import h04.strategy.MoveByTeleport;
import h04.strategy.MoveByWalk;

import static org.tudalgo.algoutils.student.Student.crash;

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

//        main01();
        main02();
    }

    /**
     * Test method for MouseFieldSelector
     */
    public static void main01() {
        MouseFieldSelector mouseFieldSelector = new MouseFieldSelector();
        MoveByWalk moveByWalk = new MoveByWalk();
        RobotMover robotMover = new RobotMover(moveByWalk);
        mouseFieldSelector.setFieldSelectionListener(robotMover);
        Robot robot1 = new Robot(1,1);
        Robot robot2 = new Robot(2,2);
        Robot robot3 = new Robot(3,3);
        robotMover.addRobot(robot1);
        robotMover.addRobot(robot2);
        robotMover.addRobot(robot3);
    }

    /**
     * Test method for KeyboardFieldSelector
     */
    public static void main02() {
        KeyboardFieldSelector keyboardFieldSelector = new KeyboardFieldSelector();
        MoveByTeleport moveByTeleport = new MoveByTeleport();
        MoveableRobot moveableRobot = new MoveableRobot(moveByTeleport);
        keyboardFieldSelector.setFieldSelectionListener(moveableRobot);
    }
}
