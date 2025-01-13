package h04.robot;

import fopbot.Field;
import fopbot.Robot;
import h04.selection.FieldSelectionListener;
import h04.strategy.MoveStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * RobotMover is used to move an array of Robots using a
 * given MoveStrategy
 */
public class RobotMover implements FieldSelectionListener {
    private MoveStrategy moveStrategy;
    private List<Robot> robots = new ArrayList<Robot>();

    /**
     * Constructs a new RobotMover with a certain MoveStrategy
     * @param moveStrategy defines the method with which the robots will have to move.
     */
    public RobotMover(MoveStrategy moveStrategy){
        this.moveStrategy = moveStrategy;
    }

    /**
     * Adds a robot to the list of robots that will be moved with the given MoveStrategy
     * @param robot The robot that should be added.
     */
    public void addRobot(Robot robot){
        this.robots.add(robot);
    }

    /**
     * Moves all the assigned robots to the given field using the MoveStrategy.
     * @param field is the field the robots will end up on.
     */
    @Override
    public void onFieldSelection(Field field) {
        for (int i = 0; i<robots.size();i++) {
            this.moveStrategy.start(this.robots.get(i), field);
        }
    }
}
