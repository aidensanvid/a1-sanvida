package ca.mcmaster.se2aa4.mazerunner.maze;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import ca.mcmaster.se2aa4.mazerunner.enumerations.Heading;

public class Maze {

    private char[][] maze;
    private int rows, cols;
    private int[] westEntrance, eastEntrance;

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

    // Shares location of the west entrance
    public int[] getWestEntrance() {
        return westEntrance;
    }

    // Shares location of the east entrance
    public int[] getEastEntrance() {
        return eastEntrance;
    }
    
    // Locates the west entrance of the maze
    public int[] findWestEntrance() {

        for (int i=0; i<rows; i++) {
            if (maze[i][0] != '#') {
                int[] location = {i, 0};
                return location;
            }
        }

        int[] location = {-1, -1};
        return location;

    }

    // Locates the east entrance of the maze
    public int[] findEastEntrance() {

        for (int i=0; i<rows; i++) {
            if (maze[i][cols-1] != '#') {
                int[] location = {i, cols-1};
                return location;
            }
        }

        int[] location = {-1, -1};
        return location;

    }

    // Check if maze explorer has reached the end
    public boolean atEnd(int[] coordinates, Heading exitDirection) {

        if (exitDirection == Heading.WEST) {
            return (coordinates[0] == westEntrance[0] && coordinates[1] == westEntrance[1]);
        }
        else {
            return (coordinates[0] == eastEntrance[0] && coordinates[1] == eastEntrance[1]);
        }
        
    }

    // Check if maze explorer is within maze boundaries
    public boolean inBounds(int[] coordinates) {

        return (coordinates[0] >= 0 && coordinates[0] < rows && coordinates[1] >= 0 && coordinates[1] < cols);

    }

    // Check if tile is a wall
    public boolean isWall(int[] coordinates) {

        return (maze[coordinates[0]][coordinates[1]] == '#');
 
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

    public Maze(String maze_file) throws FileNotFoundException, IOException {

        int[] dimensions = getMazeDimensions(maze_file);

        this.rows = dimensions[0];
        this.cols = dimensions[1];
        this.maze = loadMaze(maze_file);
        this.westEntrance = findWestEntrance();
        this.eastEntrance = findEastEntrance();
    }


}