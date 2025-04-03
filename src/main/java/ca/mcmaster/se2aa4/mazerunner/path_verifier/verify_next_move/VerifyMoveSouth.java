package ca.mcmaster.se2aa4.mazerunner.path_verifier.verify_next_move;
import ca.mcmaster.se2aa4.mazerunner.maze.Maze;
import ca.mcmaster.se2aa4.mazerunner.maze_explorer.MazeExplorer;

public class VerifyMoveSouth extends DefaultVerifyMove {

    @Override
    protected int[] calculateNextLocationHook() {

        int[] next_location = mazeExplorer.locationAfterMovingSouth();
        return next_location;
    }

    public VerifyMoveSouth(Maze pMaze, MazeExplorer pMazeExplorer) {
        super(pMaze, pMazeExplorer);
    }
    
}