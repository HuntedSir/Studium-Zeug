package h07.tree;

import h07.expression.ValueExpression;

public class ValueNode implements Node{

    private ValueExpression expression = () -> "";

    public void setValueExpression(ValueExpression valueExpression){
        this.expression=valueExpression;
    }
    @Override
    public String evaluate() {
        return expression.get();
    }
}
