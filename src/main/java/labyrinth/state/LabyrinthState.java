package labyrinth.state;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.tinylog.Logger;

/**
 * Represents the state of the labyrinth
 */

public class LabyrinthState {

    /**
     * The size of the labyrinth.
     */
    private static final int LABYRINTH_SIZE = 7;

    /**
     * The starting position of the ball.
     */
    public static final Point BALL_STARTING_POSITION = new Point(4,1);

    /**
     * The position of the goal.
     */
    public static final Point GOAL_POSITION = new Point(2,5);

    /**
     * The current position of the ball.
     */
    public ReadOnlyObjectWrapper<Point> ballPosition;

    /**
     * The storage for the cells of the labyrinth.
     */
    public Cell[][] labyrinth = new Cell[LABYRINTH_SIZE][LABYRINTH_SIZE];

    /**
     * Creates a {@code LabyrinthState} object that corresponds to the original
     * initial state of the puzzle.
     */
    public LabyrinthState() {
        Logger.trace("Labyrinth constructor is called");
        ballPosition = new ReadOnlyObjectWrapper<>(BALL_STARTING_POSITION);
        for(int i = 0; i < LABYRINTH_SIZE; i++) {
            for(int j = 0; j < LABYRINTH_SIZE; j++){
                labyrinth[i][j] = new Cell();
            }
        }
        setLabyrinthWalls();
    }

    /**
     * Moves the ball to the nearest wall in the specified direction.
     *
     * @param direction the direction in which the ball is intended to be moved.
     */
    public void move(Direction direction) {
        Logger.trace("move is called. direction = {}", direction);
        while (canMove(direction)) {
            step(direction);
        }
    }

    private void step(Direction direction){
        Logger.trace("step is called. direction = {}", direction);
        ballPosition.set(ballPosition.get().getPositionAt(direction));
    }

    private void setLabyrinthWalls()
    {
        Logger.trace("setLabyrinthWalls is called");
        for(int i = 0; i < LABYRINTH_SIZE; i++)
        {
            createWallInDir(i, 0, Direction.UP);
        }

        for(int i = 0; i < LABYRINTH_SIZE; i++)
        {
            createWallInDir(i, 6, Direction.DOWN);
        }

        for(int i = 0; i < LABYRINTH_SIZE; i++)
        {
            createWallInDir(0, i, Direction.LEFT);
        }

        for(int i = 0; i < LABYRINTH_SIZE; i++)
        {
            createWallInDir(6, i, Direction.RIGHT);
        }

        createWallInDir(0,0, Direction.RIGHT);
        createWallInDir(2,0, Direction.DOWN);

        createWallInDir(3,0, Direction.RIGHT);
        createWallInDir(6,0, Direction.DOWN);
        createWallInDir(1,2, Direction.DOWN);
        createWallInDir(2,2, Direction.RIGHT);
        createWallInDir(5,2, Direction.RIGHT);
        createWallInDir(3,3, Direction.DOWN);
        createWallInDir(3,3, Direction.RIGHT);
        createWallInDir(4,3, Direction.RIGHT);
        createWallInDir(6,3, Direction.DOWN);
        createWallInDir(0,4, Direction.DOWN);
        createWallInDir(4,4, Direction.DOWN);
        createWalls(2, 5,  false, true, true, true);
        createWallInDir(4,6, Direction.LEFT);
        createWallInDir(5,6, Direction.RIGHT);
    }

    private void createWallInDir(int x, int y, Direction direction) {
        Logger.trace("createWallInDir is called. x = {} y = {} direction = {}", x, y, direction);
        switch (direction){
            case UP -> createWalls(x, y, true, false, false, false);
            case RIGHT -> createWalls(x, y, false, true, false, false);
            case DOWN -> createWalls(x, y, false, false, true, false);
            case LEFT -> createWalls(x, y, false, false, false, true);
        }
    }

    //only sets true walls, does not unset false walls
    private void createWalls(int x, int y, boolean t, boolean r, boolean b, boolean l){
        Logger.trace("createWalls is called. x = {} y = {}", x, y);
        boolean[] walls = new boolean[] {t, r, b, l};
        Direction[] dirs = new Direction[] {Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT};
        Point p = new Point(x, y);
        for(int i = 0; i < 4; i++) {
            if(walls[i]){
                labyrinth[x][y].setWall(dirs[i], true);
            }
            Point neigh = p.getPositionAt(dirs[i]);
            if(walls[i] && (0 <= neigh.x && neigh.x <= LABYRINTH_SIZE-1) && (0 <= neigh.y && neigh.y <= LABYRINTH_SIZE-1)){
                labyrinth[neigh.x][neigh.y].setOppositeWall(dirs[i], true);
            }
        }
    }

    /**
     * {@return whether the ball can be moved in the specified direction}
     *
     * @param direction the direction in which the ball is intended to be moved.
     */
    public boolean canMove(Direction direction){
        Logger.trace("canMove is called. direction = {}", direction);
        return !hasWall(ballPosition.get().x, ballPosition.get().y, direction);
    }

    /**
     * {@return whether the ball has reached the goal}
     */
    public boolean reachedGoal(){
        Logger.trace("reachedGoal is called");
        return GOAL_POSITION.isEqual(ballPosition.get());
    }

    /**
     * {@return whether a cell at the specified position has a wall in the specified direction}
     *
     * @param x the column index of the cell of which wall is intended to be checked.
     * @param y the row index of the cell of which wall is intended to be checked.
     * @param direction the direction of the wall from the specified position that is intended to be checked.
     */
    public boolean hasWall(int x, int y, Direction direction) {
        Logger.trace("hasWall is called. x = {} y = {} direction = {}", x, y, direction);
        return switch (direction) {
            case UP -> labyrinth[x][y].wallTop;
            case DOWN -> labyrinth[x][y].wallBottom;
            case LEFT -> labyrinth[x][y].wallLeft;
            case RIGHT -> labyrinth[x][y].wallRight;
        };
    }

    /**
     * {@return whether the goal is at the specified position}
     *
     * @param x column index of the position in which the goal is intended to be checked.
     * @param y row index of the position in which the goal is intended to be checked.
     */
    public boolean isGoal(int x, int y) {
        Logger.trace("isGoal is called. x = {} y = {}", x, y);
        return GOAL_POSITION.isEqual(new Point(x, y));
    }

    public ReadOnlyObjectProperty<Point> ballPositionProperty() {
        Logger.trace("ballPositionProperty is called");
        return ballPosition.getReadOnlyProperty();
    }
}
