package ca.mcmaster.se2aa4.mazerunner.path_finder.commands;
import ca.mcmaster.se2aa4.mazerunner.maze_explorer.MazeExplorer;

public abstract class Command {

    protected MazeExplorer mazeExplorer;
    protected char instruction;

    public Command(MazeExplorer pMazeExplorer) {
        this.mazeExplorer = pMazeExplorer;
    }

    public char getInstruction() {
        return instruction;
    }

    public abstract void execute();

    public abstract void undo();

}