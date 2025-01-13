package h04.robot;

import fopbot.Field;
import fopbot.Robot;
import h04.selection.FieldSelectionListener;
import h04.strategy.MoveStrategy;
import h04.strategy.MoveStrategyWithCounter;

/**
 * A Robot that can move to a selected Field.
 */
public class MoveableRobot extends Robot implements FieldSelectionListener {
    public MoveStrategy moveStrategy;

    /**
     * Constructs a Robot that moves using the given MoveStrategy.
     * @param moveStrategy describes the method with which the robot moves.
     */
    public MoveableRobot(MoveStrategy moveStrategy) {
        super(0, 0);
        this.moveStrategy = moveStrategy;
    }

    /**
     * Teleports or moves the robot to the selected field, depending on the selected
     * MoveStrategy
     * @param field is the field the robot will end up on.
     */
    @Override
    public void onFieldSelection(Field field) {
        this.moveStrategy.start(this, field);
        if(this instanceof MoveStrategyWithCounter){
            int counter = ((MoveStrategyWithCounter) this).getMoveCount();
            for (int i = 0; i < counter; i++) {
                this.turnLeft();
            }
        }
    }
}
