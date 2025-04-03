package ca.mcmaster.se2aa4.mazerunner.path_verifier;

import ca.mcmaster.se2aa4.mazerunner.enumerations.Heading;
import ca.mcmaster.se2aa4.mazerunner.maze.Maze;
import ca.mcmaster.se2aa4.mazerunner.maze_explorer.MazeExplorer;
import ca.mcmaster.se2aa4.mazerunner.path.Path;
import ca.mcmaster.se2aa4.mazerunner.path_verifier.test_instructions.TestForwardInstruction;
import ca.mcmaster.se2aa4.mazerunner.path_verifier.test_instructions.TestLeftInstruction;
import ca.mcmaster.se2aa4.mazerunner.path_verifier.test_instructions.TestRightInstruction;

public class PathVerifier {

    private Path path;
    private MazeExplorer mazeExplorer;
    private Maze maze;

    public boolean testPathFromBothEntrances(Path path) {

        boolean isPathValidFromWestEntrance = testPathFromEntrance(path, Heading.WEST, Heading.EAST);
        boolean isPathValidFromEastEntrance = testPathFromEntrance(path, Heading.EAST, Heading.WEST);

        // Checks if path works from either entrance
        return (isPathValidFromWestEntrance || isPathValidFromEastEntrance);

    }

    // Tests the path provided by the user
    public boolean testPathFromEntrance(Path path, Heading entranceDirection, Heading exitDirection) {

        // Determines the starting position of the maze explorer
        if (entranceDirection == Heading.WEST) {
            mazeExplorer = new MazeExplorer(maze.getWestEntrance(), Heading.EAST, maze);
        }
        else {
            mazeExplorer = new MazeExplorer(maze.getEastEntrance(), Heading.WEST, maze);
        }

        String factorizedCoefficient = "";


        // Removes whitespace from input path
        path.removeWhitespace();

        for (int i=0; i<path.length(); i++) {

            char currentInstruction = path.getInstruction(i);

            if (currentInstruction == 'F') {
                factorizedCoefficient = new TestForwardInstruction(factorizedCoefficient, this, mazeExplorer).test();
            }
            else if (currentInstruction == 'R') {
                factorizedCoefficient = new TestRightInstruction(factorizedCoefficient, this, mazeExplorer).test();

            }
            else if (currentInstruction == 'L') {
                factorizedCoefficient = new TestLeftInstruction(factorizedCoefficient, this, mazeExplorer).test();

            }
            // Tracks the coefficient infront of factorized inputs in a numeric buffer
            else {
                factorizedCoefficient += currentInstruction;
            }
        }

        if (maze.atEnd(mazeExplorer.location(), exitDirection)) {
            return true;
        }
        else {
            return false;
        }

    }

    public PathVerifier(Maze pMaze) {
        this.maze = pMaze;
    }
}