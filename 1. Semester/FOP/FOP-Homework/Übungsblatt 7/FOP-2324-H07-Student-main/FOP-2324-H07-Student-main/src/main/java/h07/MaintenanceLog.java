package h07;

import h07.tree.*;

import java.time.LocalTime;
import java.util.Objects;

/**
 * Represents a maintenance log.
 */
public class MaintenanceLog extends Log {

    @Override
    protected Node generateTree() {
        ValueNode timeStamp = new ValueNode();
        timeStamp.setValueExpression( () -> LocalTime.now().toString() );

        ValueNode separator = new ValueNode();
        separator.setValueExpression( () -> ": " );

        ValueNode level = new ValueNode();
        level.setValueExpression( () -> String.valueOf(this.level));

        ValueNode message = new ValueNode();
        message.setValueExpression(() -> this.message);

        ValueNode emptyMessage = new ValueNode();

        ValueNode backslashN = new ValueNode();
        backslashN.setValueExpression( () -> "\n" );

        ConcatenationNode concatenationNode1 = new ConcatenationNode(separator, message);
        ConcatenationNode concatenationNode2 = new ConcatenationNode(timeStamp, concatenationNode1);
        ConcatenationNode concatenationNode3 = new ConcatenationNode(concatenationNode2, backslashN);

        ConditionNode importance = new ConditionNode(level, concatenationNode3, emptyMessage);
        importance.setConditionExpression(String -> (Objects.equals(String, "3")));

        return importance;
    }
}
