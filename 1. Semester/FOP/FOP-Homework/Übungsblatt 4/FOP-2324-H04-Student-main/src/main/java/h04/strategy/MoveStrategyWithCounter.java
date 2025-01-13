package h04.strategy;

/**
 * Is a MoveStrategy in which the moves a robot takes are counted.
 */
public interface MoveStrategyWithCounter extends  MoveStrategy{
    /**
     * Returns the number of moves.
     * @return
     */
    public int getMoveCount();
}
