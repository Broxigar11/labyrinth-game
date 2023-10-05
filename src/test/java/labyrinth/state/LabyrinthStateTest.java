package labyrinth.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LabyrinthStateTest {

    LabyrinthState state = new LabyrinthState();

    @Test
    void isGoal() {
        assertTrue(state.isGoal(2,5));
    }


    @Test
    void canMove() {
        assertTrue(state.canMove(Direction.DOWN));
        assertTrue(state.canMove(Direction.UP));
        assertTrue(state.canMove(Direction.LEFT));
        assertTrue(state.canMove(Direction.RIGHT));
    }

    @Test
    void hasWall() {
        assertTrue(state.hasWall(3, 2, Direction.LEFT));
        assertTrue(state.hasWall(3, 3, Direction.RIGHT));
        assertTrue(state.hasWall(2, 1, Direction.UP));
        assertTrue(state.hasWall(0, 4, Direction.DOWN));
    }

    @Test
    void move(){
        state = new LabyrinthState();
        state.move(Direction.RIGHT);
        assertTrue(state.ballPosition.get().isEqual(new Point(6, 1)));
        state.move(Direction.DOWN);
        assertTrue(state.ballPosition.get().isEqual(new Point(6, 3)));
        state.move(Direction.LEFT);
        assertTrue(state.ballPosition.get().isEqual(new Point(5, 3)));
        state.move(Direction.UP);
        assertTrue(state.ballPosition.get().isEqual(new Point(5, 0)));
    }

    @Test
    void reachedGoal(){
        state = new LabyrinthState();
        Direction[] winningSequence = new Direction[] {
                Direction.RIGHT, Direction.DOWN, Direction.LEFT, Direction.DOWN, Direction.LEFT, Direction.UP,
                Direction.LEFT, Direction.DOWN, Direction.LEFT, Direction.UP, Direction.RIGHT, Direction.UP,
                Direction.RIGHT, Direction.UP, Direction.LEFT, Direction.DOWN, Direction.RIGHT, Direction.DOWN
        };
        for(var direction : winningSequence){
            state.move(direction);
        }
        assertTrue(state.reachedGoal());
    }

}