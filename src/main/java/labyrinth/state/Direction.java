package labyrinth.state;

import org.tinylog.Logger;

/**
 * Represents the four main directions.
 */
public enum Direction {

    UP(-1, 0),
    RIGHT(0, 1),
    DOWN(1, 0),
    LEFT(0, -1);

    private final int rowChange;
    private final int colChange;

    Direction(int rowChange, int colChange) {
        Logger.trace("Direction constructor is called");
        this.rowChange = rowChange;
        this.colChange = colChange;
    }

    /**
     * {@return the change in the row coordinate when moving to the
     * direction}
     */
    public int getRowChange() {
        Logger.trace("getRowChange is called");
        return rowChange;
    }

    /**
     * {@return the change in the column coordinate when moving to the
     * direction}
     */
    public int getColChange() {
        Logger.trace("getColChange is called");
        return colChange;
    }

}
