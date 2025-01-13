package h07.tree;

import h07.expression.ConditionExpression;

public class ConditionNode implements Node{
    private ConditionExpression conditionExpression = String -> false;
    private Node node1;
    private Node node2;
    private Node node3;
    public ConditionNode(Node node1, Node node2, Node node3){
        this.node1 = node1;
        this.node2 = node2;
        this.node3 = node3;
    }
    @Override
    public String evaluate() {
        boolean result = conditionExpression.check(this.node1.evaluate());
        if(result){
            return this.node2.evaluate();
        }
        else{
            return this.node3.evaluate();
        }
    }
    public void setConditionExpression(ConditionExpression conditionExpression){
        this.conditionExpression = conditionExpression;
    }
}
