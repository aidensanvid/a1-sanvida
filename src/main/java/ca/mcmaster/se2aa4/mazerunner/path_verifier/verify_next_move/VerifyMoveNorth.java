package ca.mcmaster.se2aa4.mazerunner.path_verifier.verify_next_move;
import ca.mcmaster.se2aa4.mazerunner.maze.Maze;
import ca.mcmaster.se2aa4.mazerunner.maze_explorer.MazeExplorer;

public class VerifyMoveNorth extends DefaultVerifyMove {

    @Override
    protected int[] calculateNextLocationHook() {

        int[] next_location = mazeExplorer.locationAfterMovingNorth();
        return next_location;
    }

    public VerifyMoveNorth(Maze pMaze, MazeExplorer pMazeExplorer) {
        super(pMaze, pMazeExplorer);
    }
    
}