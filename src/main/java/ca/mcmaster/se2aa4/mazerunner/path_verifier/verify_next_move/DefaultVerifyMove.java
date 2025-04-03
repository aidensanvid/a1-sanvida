package ca.mcmaster.se2aa4.mazerunner.path_verifier.verify_next_move;
import ca.mcmaster.se2aa4.mazerunner.maze.Maze;
import ca.mcmaster.se2aa4.mazerunner.maze_explorer.MazeExplorer;

public abstract class DefaultVerifyMove implements VerifyMove {

    protected Maze maze;
    protected MazeExplorer mazeExplorer;

    protected DefaultVerifyMove(Maze pMaze, MazeExplorer pMazeExplorer) {
        this.maze = pMaze;
        this.mazeExplorer = pMazeExplorer;
    }

    @Override
    public boolean verify() {

        int[] nextLocation = calculateNextLocationHook();

        // Checks the maze explorer doesn't leave maze bounds
        if (maze.inBounds(nextLocation)) {

                // Checks the maze explorer doesn't collide with a wall
                if (maze.isWall(nextLocation)) {
                    return false;
                }
                else {
                    return true;
                }
            }
            else {
                return false;
            }
    }

    protected abstract int[] calculateNextLocationHook();


}