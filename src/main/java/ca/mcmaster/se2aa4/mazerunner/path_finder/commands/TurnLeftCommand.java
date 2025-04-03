package ca.mcmaster.se2aa4.mazerunner.path_finder.commands;
import ca.mcmaster.se2aa4.mazerunner.maze_explorer.MazeExplorer;

public class TurnLeftCommand extends Command {

    public TurnLeftCommand(MazeExplorer mazeExplorer) {
        super(mazeExplorer);
        instruction = 'L';
    }

    @Override
    public void execute() {
        mazeExplorer.turn(instruction);
    }

    @Override
    public void undo() {
        mazeExplorer.turn('R');
    }
}