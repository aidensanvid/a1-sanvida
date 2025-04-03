package ca.mcmaster.se2aa4.mazerunner.path_finder;

import ca.mcmaster.se2aa4.mazerunner.enumerations.Heading;
import ca.mcmaster.se2aa4.mazerunner.maze.Maze;
import ca.mcmaster.se2aa4.mazerunner.maze_explorer.MazeExplorer;
import ca.mcmaster.se2aa4.mazerunner.path.Path;
import ca.mcmaster.se2aa4.mazerunner.path_finder.commands.Command;
import ca.mcmaster.se2aa4.mazerunner.path_finder.commands.CommandHistory;
import ca.mcmaster.se2aa4.mazerunner.path_finder.commands.MoveForwardCommand;
import ca.mcmaster.se2aa4.mazerunner.path_finder.commands.TurnLeftCommand;
import ca.mcmaster.se2aa4.mazerunner.path_finder.commands.TurnRightCommand;

public class PathFinder {
    
    private Maze maze;
    private MazeExplorer mazeExplorer;
    private CommandHistory commandHistory;

    public String solveMaze(Heading entranceDirection, Heading exitDirection) {

        commandHistory = new CommandHistory();

        // Determines starting location
        if (entranceDirection == Heading.WEST) {
            mazeExplorer = new MazeExplorer(maze.getWestEntrance(), Heading.EAST, maze);
        }
        else {
            mazeExplorer = new MazeExplorer(maze.getEastEntrance(), Heading.WEST, maze);
        }

        // Right-hand maze navigation algorithm
        while (!maze.atEnd(mazeExplorer.location(), exitDirection)) {
            
            executeCommand(new TurnRightCommand(mazeExplorer));

            if (mazeExplorer.isNextMoveValid()){
                
                executeCommand(new MoveForwardCommand(mazeExplorer));
            }
            else{

                undo();
                
                if (mazeExplorer.isNextMoveValid()) {
                    executeCommand(new MoveForwardCommand(mazeExplorer));
            
                }
                else {
                    executeCommand(new TurnLeftCommand(mazeExplorer));
                }
            }
        }

        Path path = new Path();

        commandHistory = reverseCommandHistory(commandHistory);

        while (!commandHistory.isEmpty()) {

            Command command = commandHistory.pop();
            path.addInstruction(command.getInstruction());

        }

        String factorizedPath = path.canonToFactorized();

        return factorizedPath;
    }

    public CommandHistory reverseCommandHistory (CommandHistory pCommandHistory) {

        CommandHistory commandHistory = new CommandHistory();

        while (!pCommandHistory.isEmpty()) {
            Command command = pCommandHistory.pop();
            commandHistory.push(command);
        }

        return commandHistory;
    }

    public void executeCommand(Command command) {
        command.execute();
        commandHistory.push(command);
    }

    public void undo() {
        
        Command command = commandHistory.pop();

        if (command != null) {
            command.undo();
        }
    }

    public PathFinder(Maze pMaze) {
        this.maze = pMaze;
    }

}

