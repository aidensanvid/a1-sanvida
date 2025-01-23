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

    public void displayMaze(){

        for (int row = 0; row<rows; row++) {
            for (int col = 0;col<cols;col++) {
                System.out.print(maze[row][col]);
            }
            System.out.println();
        }
    }

    public char[][] getMaze() {
        return maze;
    }

    public int getRows() {
        return rows;
    }

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

class MazeExplorer {

    private char[][] grid;
    private int rows;
    private int cols;
    
    private int[] entrance_coordinates = new int[2];
    private int[] exit_coordinates = new int[2];
    private int[] coordinates = new int[2];
    private char facing;

    public void turn(char direction) {

        char[] directions = {'N', 'E', 'S', 'W'};

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

    public boolean isNextMoveValid() {

        if (facing == 'N') {
            if (coordinates[0] - 1 >= 0) {

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
            if (coordinates[1] + 1 < cols) {

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
            if (coordinates[0] + 1 < rows) {
                
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
            if (coordinates[1] - 1 >= cols) {

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

    public boolean testPath(String path, int[] starting_coordinates, char direction, int[] ending_coordinates) {

        this.coordinates = starting_coordinates;
        this.facing = direction;

        String num_buffer = "";

        path = path.replaceAll("\\s", "");

        for (int i=0; i<path.length(); i++) {
            char c = path.charAt(i);

            if (c == 'F') {

                if (num_buffer.equals("")) {
                    
                    if (coordinates[0] == ending_coordinates[0] && coordinates[1] == ending_coordinates[1]) {
                        return true;
                    }
                    else if (isNextMoveValid()) {
                        moveForward();
                    }
                    else {
                        return false;
                    }
                }
                else {
                    int repetitions = Integer.parseInt(num_buffer);

                    for (int j = 0; j<repetitions; j++) {

                        if (coordinates[0] == ending_coordinates[0] && coordinates[1] == ending_coordinates[1]) {
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

                if (num_buffer.equals("")) {
                    turn('R');
                }
                else {
                    int repetitions = Integer.parseInt(num_buffer);

                    for (int j = 0; j<repetitions; j++) {
                        turn('R');
                    }
                }

                num_buffer = "";

            }
            else if (c == 'L') {

                if (num_buffer.equals("")) {
                    turn('L');
                }
                else {
                    int repetitions = Integer.parseInt(num_buffer);

                    for (int j = 0; j<repetitions; j++) {
                        turn('L');
                    }
                }

                num_buffer = "";

            }
            else {
                num_buffer += c;
            }
        }

        return false;

    }


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

    public int[] getEntrance() {
        return this.entrance_coordinates;
    }

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
                MazeExplorer explorer = new MazeExplorer(maze);

                if (cmd.hasOption("p")) {

                    maze.displayMaze();

                    String path = cmd.getOptionValue("p");

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

                    maze.displayMaze();

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
