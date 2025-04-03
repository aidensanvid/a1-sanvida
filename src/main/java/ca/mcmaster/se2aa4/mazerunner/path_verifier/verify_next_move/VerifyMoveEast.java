package ca.mcmaster.se2aa4.mazerunner.path_verifier.verify_next_move;
import ca.mcmaster.se2aa4.mazerunner.maze.Maze;
import ca.mcmaster.se2aa4.mazerunner.maze_explorer.MazeExplorer;

public class VerifyMoveEast extends DefaultVerifyMove {

    @Override
    protected int[] calculateNextLocationHook() {

        int[] next_location = mazeExplorer.locationAfterMovingEast();
        return next_location;
    }

    public VerifyMoveEast(Maze pMaze, MazeExplorer pMazeExplorer) {
        super(pMaze, pMazeExplorer);
    }
    
}