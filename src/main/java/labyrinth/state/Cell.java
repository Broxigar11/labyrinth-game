package labyrinth.state;

import org.tinylog.Logger;

/**
 * Represents a cell in the labyrinth.
 */
public class Cell {
    public boolean wallRight;
    public boolean wallLeft;
    public boolean wallTop;
    public boolean wallBottom;

    public Cell() {
        Logger.trace("Cell constructor is called");
        this.wallRight = false;
        this.wallLeft = false;
        this.wallTop = false;
        this.wallBottom = false;
    }

    /**
     * Creates or deletes a wall in the specified direction, depending on the specified boolean.
     *
     * @param direction the direction in which the wall is intended to be set.
     * @param b the boolean on which it depends whether tha wall is created or deleted.
     */
    public void setWall(Direction direction, boolean b){
        Logger.trace("setWall is called. direction = {} b = {}", direction, b);
        switch (direction) {
            case UP -> wallTop = b;
            case RIGHT -> wallRight = b;
            case DOWN -> wallBottom = b;
            case LEFT -> wallLeft = b;
        };
    }

    /**
     * Creates or deletes a wall in the opposite of the specified direction, depending on the specified boolean.
     *
     * @param direction the opposite direction from which the wall is intended to be set.
     * @param b the boolean on which it depends whether tha wall is created or deleted.
     */
    public void setOppositeWall(Direction direction, boolean b){
        Logger.trace("setOppositeWall is called. direction = {} b = {}", direction, b);
        switch (direction) {
            case UP -> wallBottom = b;
            case DOWN -> wallTop = b;
            case LEFT -> wallRight = b;
            case RIGHT -> wallLeft = b;
        };
    }

}
