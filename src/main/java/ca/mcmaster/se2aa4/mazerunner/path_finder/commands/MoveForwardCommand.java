package ca.mcmaster.se2aa4.mazerunner.path_finder.commands;
import ca.mcmaster.se2aa4.mazerunner.maze_explorer.MazeExplorer;

public class MoveForwardCommand extends Command {

    public MoveForwardCommand(MazeExplorer mazeExplorer) {
        super(mazeExplorer);
        instruction = 'F';
    }  

    @Override
    public void execute() {
        mazeExplorer.moveForward();
    }

    @Override
    public void undo() {
        mazeExplorer.turn('R');
        mazeExplorer.turn('R');
        mazeExplorer.moveForward();
        mazeExplorer.turn('R');
        mazeExplorer.turn('R');
    }

}