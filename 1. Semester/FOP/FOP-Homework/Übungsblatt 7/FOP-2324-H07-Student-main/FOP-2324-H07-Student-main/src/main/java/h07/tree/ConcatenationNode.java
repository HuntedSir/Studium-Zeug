package h07.tree;



public class ConcatenationNode implements Node{
    private Node leftNode;
    private Node rightNode;
    public ConcatenationNode(Node left, Node right){
        this.leftNode=left;
        this.rightNode=right;
    }
    @Override
    public String evaluate() {
        return this.leftNode.evaluate() + this.rightNode.evaluate();
    }
}
