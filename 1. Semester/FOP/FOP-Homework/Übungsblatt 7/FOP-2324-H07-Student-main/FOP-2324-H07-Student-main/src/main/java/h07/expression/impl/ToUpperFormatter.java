package h07.expression.impl;

import h07.expression.MapExpression;

public class ToUpperFormatter implements MapExpression {
    @Override
    public String map(String string) {
        return string.toUpperCase();
    }
}
