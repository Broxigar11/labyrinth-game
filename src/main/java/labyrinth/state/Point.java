package labyrinth.state;

import org.tinylog.Logger;

/**
 * Represents a position in the labyrinth
 */
public class Point {
    public int x;
    public int y;

    public Point(int x, int y){
        Logger.trace("Point constructor is called");
        this.x = x;
        this.y = y;
    }

    /**
     * {@return the position of the neighboring cell in the direction specified}
     *
     * @param direction the direction in which the sought cell is located from this cell.
     */
    public Point getPositionAt(Direction direction) {
        Logger.trace("getPositionAt is called. direction = {}", direction);
        return new Point(x + direction.getColChange(), y + direction.getRowChange());
    }

    /**
     * {@return whether the location of the specified {@code Point} is equal to the location of this point}
     *
     * @param point the point which is intended to be compared to this point.
     */
    public boolean isEqual(Point point){
        Logger.trace("isEqual is called. point.x = {} point.y = {}", point.x, point.y);
        return this.x == point.x && this.y == point.y;
    }

}
