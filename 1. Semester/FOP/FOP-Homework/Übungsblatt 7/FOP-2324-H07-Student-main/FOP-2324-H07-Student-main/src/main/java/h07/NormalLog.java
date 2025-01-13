package h07;

import h07.expression.MapExpression;
import h07.expression.ValueExpression;
import h07.tree.*;

import java.time.LocalTime;
import java.util.Objects;

/**
 * Represents a normal log.
 */
public class NormalLog extends Log{

    @Override
    protected Node generateTree(){
        ValueNode timeStamp = new ValueNode();
        timeStamp.setValueExpression( () -> LocalTime.now().toString() );

        ValueNode separator = new ValueNode();
        separator.setValueExpression( () -> ": " );

        ValueNode level = new ValueNode();
        level.setValueExpression( () -> String.valueOf(this.level));

        ValueNode message = new ValueNode();

        ValueNode backslashN = new ValueNode();
        backslashN.setValueExpression( () -> "\n" );

        MapNode replaceNewline = new MapNode(message);

        MapNode red = new MapNode(replaceNewline);
        MapNode yellow = new MapNode(replaceNewline);
        MapNode blue = new MapNode(replaceNewline);


        ConditionNode warningPriority = new ConditionNode(level, yellow, red);
        warningPriority.setConditionExpression(String -> (Objects.equals(String, "2") || Objects.equals(String, "3")));

        ConditionNode informationPriority = new ConditionNode(level, blue, warningPriority);
        informationPriority.setConditionExpression(String -> (Objects.equals(String, "0") || Objects.equals(String, "1")));

        red.setMapExpression(createColorExpression(ANSI_RED));
        yellow.setMapExpression(createColorExpression(ANSI_YELLOW));
        blue.setMapExpression(createColorExpression(ANSI_BLUE));

        ConcatenationNode concatenationNode1 = new ConcatenationNode(separator, informationPriority);
        ConcatenationNode concatenationNode2 = new ConcatenationNode(timeStamp, concatenationNode1);
        ConcatenationNode concatenationNode3 = new ConcatenationNode(concatenationNode2, backslashN);

        message.setValueExpression(() -> this.message);

        return concatenationNode3;
    }

}
