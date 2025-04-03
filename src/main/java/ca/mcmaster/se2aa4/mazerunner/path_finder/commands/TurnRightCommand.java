package ca.mcmaster.se2aa4.mazerunner.path_finder.commands;
import ca.mcmaster.se2aa4.mazerunner.maze_explorer.MazeExplorer;

public class TurnRightCommand extends Command {

    public TurnRightCommand(MazeExplorer mazeExplorer) {
        super(mazeExplorer);
        instruction = 'R';
    }

    @Override
    public void execute() {
        mazeExplorer.turn(instruction);
    }

    @Override
    public void undo() { 
        mazeExplorer.turn('L');
    }
}