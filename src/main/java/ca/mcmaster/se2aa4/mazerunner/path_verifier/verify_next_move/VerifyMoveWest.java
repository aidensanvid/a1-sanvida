package ca.mcmaster.se2aa4.mazerunner.path_verifier.verify_next_move;
import ca.mcmaster.se2aa4.mazerunner.maze.Maze;
import ca.mcmaster.se2aa4.mazerunner.maze_explorer.MazeExplorer;

public class VerifyMoveWest extends DefaultVerifyMove {

    @Override
    protected int[] calculateNextLocationHook() {

        int[] next_location = mazeExplorer.locationAfterMovingWest();
        return next_location;
    }

    public VerifyMoveWest(Maze pMaze, MazeExplorer pMazeExplorer) {
        super(pMaze, pMazeExplorer);
    }
    
}