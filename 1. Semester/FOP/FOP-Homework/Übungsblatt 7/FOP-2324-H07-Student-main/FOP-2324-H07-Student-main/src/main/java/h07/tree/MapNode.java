package h07.tree;

import h07.expression.MapExpression;

public class MapNode implements Node{
    private MapExpression mapExpression = String -> String;
    private Node node;
    public MapNode(Node node){
        this.node=node;
    }
    public void setMapExpression(MapExpression mapExpression){
        this.mapExpression = mapExpression;
    }

    @Override
    public String evaluate() {
        return mapExpression.map(node.evaluate());
    }
}
