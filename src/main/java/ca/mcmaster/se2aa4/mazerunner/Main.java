package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class Maze {

    private char[][] maze;
    private int rows;
    private int cols;

    // Retrieve the dimensions (rows x cols) of a maze in a file
    public int[] getMazeDimensions(String maze_file) throws FileNotFoundException, IOException {

        int[] dimensions = new int[2];

        int rows = 0;
        int cols = 0;

        BufferedReader reader = new BufferedReader(new FileReader(maze_file));
        String line;

        while ((line = reader.readLine()) != null) {
            if (cols == 0) {
                cols = line.length();
            }
            rows++;
        }

        dimensions[0] = rows;
        dimensions[1] = cols;

        return dimensions;

    }

    // Load a maze from a selected file
    public char[][] loadMaze(String maze_file) throws FileNotFoundException, IOException {

        char[][] maze = new char[rows][cols];

        BufferedReader reader = new BufferedReader(new FileReader(maze_file));
        String line;

        int row = 0;
        int col = 0;

        while ((line = reader.readLine()) != null) {
            for (int idx = 0; idx < line.length(); idx++) {
                maze[row][col] = line.charAt(idx);
                col++;
            }
            col = 0;
            row++;
        }

        return maze;
    }

    // Display maze to standard output
    public void displayMaze(){

        for (int row = 0; row<rows; row++) {
            for (int col = 0;col<cols;col++) {
                System.out.print(maze[row][col]);
            }
            System.out.println();
        }
    }

    // Retrieve maze grid
    public char[][] getMaze() {
        return maze;
    }

    // Retrieve number of rows
    public int getRows() {
        return rows;
    }
    
    // Retrieve number of columns   
    public int getCols() {
        return cols;
    }

    public Maze(String maze_file) throws FileNotFoundException, IOException{

        int[] dimensions = getMazeDimensions(maze_file);

        this.rows = dimensions[0];
        this.cols = dimensions[1];
        this.maze = loadMaze(maze_file);

    }

}

class NaiveAlgorithm extends MazeExplorer {

    // Implements the abstract solveMaze() method of the MazeExplorer superclass
    // Can be easily swapped for a more sophisticated algorithm
    public String solveMaze() {

        coordinates = entrance_coordinates;
        facing = 'E';
        String path = "";

        while (!atEnd(coordinates, exit_coordinates)) {
            moveForward();
            path += "F";
        }

        return path;
    }

    public NaiveAlgorithm(Maze maze) {
        super(maze);
    }

}

abstract class MazeExplorer {

    protected char[][] grid;
    protected int rows;
    protected int cols;
    
    protected int[] entrance_coordinates = new int[2];
    protected int[] exit_coordinates = new int[2];
    protected int[] coordinates = new int[2];
    protected char facing;

    public abstract String solveMaze();

    // Turns the maze explorer left or right in-place
    public void turn(char direction) {

        char[] directions = {'N', 'E', 'S', 'W'};

        // Rotates to the next direction, depending on whether the maze explorer turns left or right
        // Left: N -> W, Right: N -> E
        for (int i=0; i<directions.length; i++) {

            if (directions[i] == facing) {

                if (direction == 'L') {
                    facing = directions[(i-1) % directions.length];
                }
                else {
                    facing = directions[(i+1) % directions.length];
                }
                break;

            }
        }
    }

    // Ensures the next move the maze explorer makes is legal
    public boolean isNextMoveValid() {

        // Checks the direction the maze explorer is currently facing (North, East, South, West)
        if (facing == 'N') {

            // Checks the maze explorer doesn't move out of the maze bounds
            if (coordinates[0] - 1 >= 0) {

                // Checks the maze explorer doesn't collide with a wall
                if (grid[coordinates[0] - 1][coordinates[1]] == '#') {
                    return false;
                }
                else {
                    return true;
                }
            }
            else {
                return false;
            }
        }
        else if (facing == 'E') {

            // Checks the maze explorer doesn't move out of the maze bounds
            if (coordinates[1] + 1 < cols) {

                // Checks the maze explorer doesn't collide with a wall
                if (grid[coordinates[0]][coordinates[1] + 1] == '#') {
                    return false;
                }
                else {
                    return true;
                }
            }
            else {
                return false;
            }
        }
        else if (facing == 'S') {

            // Checks the maze explorer doesn't move out of the maze bounds
            if (coordinates[0] + 1 < rows) {
                
                // Checks the maze explorer doesn't collide with a wall
                if (grid[coordinates[0] + 1][coordinates[1]] == '#') {
                    return false;
                }
                else {
                    return true;
                }
            }
            else {
                return false;
            }
        }
        else {

            // Checks the maze explorer doesn't move out of the maze bounds
            if (coordinates[1] - 1 >= cols) {

                // Checks the maze explorer doesn't collide with a wall
                if (grid[coordinates[0]][coordinates[1] - 1] == '#') {
                    return false;
                }
                else {
                    return true;
                }
            }
            else {
                return false;
            }
        }
    }

    // Verifies the maze explorer reached the end of the maze
    public boolean atEnd(int[] current_coordinates, int[] ending_coordinates) {

        return (current_coordinates[0] == ending_coordinates[0] && current_coordinates[1] == ending_coordinates[1]);
    }

    // Tests canonical, factorized (or both) paths for correctness
    public boolean testPath(String path, int[] starting_coordinates, char direction, int[] ending_coordinates) {

        this.coordinates = starting_coordinates;
        this.facing = direction;

        String num_buffer = "";

        // Removes whitespace from input path
        path = path.replaceAll("\\s", "");

        for (int i=0; i<path.length(); i++) {
            char c = path.charAt(i);

            if (c == 'F') {

                // Processes canonical inputs (e.g. F, FFF, FF)
                // Repeatedly moves forward until end of maze is reached, or an illegal move is made
                if (num_buffer.equals("")) {
                    
                    if (atEnd(coordinates, ending_coordinates)) {
                        return true;
                    }
                    else if (isNextMoveValid()) {
                        moveForward();
                    }
                    else {
                        return false;
                    }
                }
                // Processes factorized inputs (e.g. 4F, 2F, 19F)
                // Repeatedly moves forward until end of maze is reached, or an illegal move is made
                else {
                    int repetitions = Integer.parseInt(num_buffer);

                    for (int j = 0; j<repetitions; j++) {

                        if (atEnd(coordinates, ending_coordinates)) {
                            return true;
                        }
                        else if (isNextMoveValid()) {
                            moveForward();
                        }
                        else {
                            return false;
                        }
                    }
                }

                num_buffer = "";

            }
            else if (c == 'R') {

                // Processes canonical inputs (e.g. R, RR, RRR)
                // Turns maze explorer right in-place
                if (num_buffer.equals("")) {
                    turn('R');
                }
                // Processes factorized inputs (e.g. 2R, 4R, 27R)
                // Turns maze explorer right in-place
                else {
                    int repetitions = Integer.parseInt(num_buffer);

                    for (int j = 0; j<repetitions; j++) {
                        turn('R');
                    }
                }

                num_buffer = "";

            }
            else if (c == 'L') {

                // Processes canonical inputs (e.g. L, LL, LLL)
                // Turns maze explorer left in-place
                if (num_buffer.equals("")) {
                    turn('L');
                }
                // Processes factorized inputs (e.g. 2L, 4L, 16L)
                // Turns maze explorer left in-place
                else {
                    int repetitions = Integer.parseInt(num_buffer);

                    for (int j = 0; j<repetitions; j++) {
                        turn('L');
                    }
                }

                num_buffer = "";

            }
            // Tracks the coefficient infront of factorized inputs in a numeric buffer
            else {
                num_buffer += c;
            }
        }

        return false;

    }

    // Moves the maze explorer in the direction its currently facing
    public void moveForward() {

        if (facing == 'N') {
            coordinates[0] -= 1;
        }
        else if (facing == 'E') {
            coordinates[1] += 1;
        }
        else if (facing == 'S') {
            coordinates[0] += 1;
        }
        else {
            coordinates[1] -= 1;
        }
    }

    // Locates the entrance of the maze
    public int[] findEntrance() {

        for (int i=0; i<rows; i++) {
            if (grid[i][0] != '#') {
                int[] location = {i, 0};
                return location;
            }
        }

        int[] location = {-1, -1};
        return location;

    }

    // Locates the exit of the maze
    public int[] findExit() {

        for (int i=0; i<rows; i++) {
            if (grid[i][cols-1] != '#') {
                int[] location = {i, cols-1};
                return location;
            }
        }

        int[] location = {-1, -1};
        return location;

    }

    
    // Retrieve entrance location
    public int[] getEntrance() {
        return this.entrance_coordinates;
    }

    // Retrieve exit location
    public int[] getExit() {
        return this.exit_coordinates;
    }

    public MazeExplorer(Maze maze) {

        this.grid = maze.getMaze();
        this.rows = maze.getRows();
        this.cols = maze.getCols();

        this.entrance_coordinates = findEntrance();
        this.exit_coordinates = findExit();

    }

}

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {

        // Generate flags accepted in command-line (-i)
        Options options = new Options();
        options.addOption("i", true, "select maze file");
        options.addOption("p", true, "enter a canonical or factorized path through the maze");

        CommandLineParser parser = new DefaultParser();

        // Ensure empty -i flags are caught
        try {
            CommandLine cmd = parser.parse(options, args);
            
            // Loads appropriate maze file
            if(cmd.hasOption("i")) {

                String maze_file = cmd.getOptionValue("i");

                logger.info("Starting Maze Runner");
                logger.info("Reading the maze from file " + maze_file);
                
                Maze maze = new Maze(maze_file);
                NaiveAlgorithm explorer = new NaiveAlgorithm(maze);
                

                // Checks for user-entered path
                if (cmd.hasOption("p")) {

                    maze.displayMaze();

                    String path = cmd.getOptionValue("p");

                    // Attempts to enter the path from both ends of the maze

                    boolean trial1 = explorer.testPath(path, explorer.getEntrance(), 'E', explorer.getExit());
                    boolean trial2 = explorer.testPath(path, explorer.getExit(), 'W', explorer.getEntrance());

                    if (trial1 || trial2) {
                        System.out.println("correct path");
                    }
                    else {
                        System.out.println("incorrect path");
                    }

                }
                else {
                    
                    // Solves the maze if the user doesn't include their own path to verify
                    System.out.println("Path: " + explorer.solveMaze());

                }

            }
            else{
                logger.error("No maze file provided.");
            }

        }
        catch (Exception exception) {
            logger.error("Invalid file provided", exception);
        }

    }
}
