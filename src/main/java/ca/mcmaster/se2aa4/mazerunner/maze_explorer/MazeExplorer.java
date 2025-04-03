package ca.mcmaster.se2aa4.mazerunner.maze_explorer;
import ca.mcmaster.se2aa4.mazerunner.enumerations.Heading;
import ca.mcmaster.se2aa4.mazerunner.maze.Maze;
import ca.mcmaster.se2aa4.mazerunner.path_verifier.verify_next_move.VerifyMoveEast;
import ca.mcmaster.se2aa4.mazerunner.path_verifier.verify_next_move.VerifyMoveNorth;
import ca.mcmaster.se2aa4.mazerunner.path_verifier.verify_next_move.VerifyMoveSouth;
import ca.mcmaster.se2aa4.mazerunner.path_verifier.verify_next_move.VerifyMoveWest;

public class MazeExplorer {

    protected Maze maze;
    protected int[] location = new int[2];
    protected Heading heading;


    public boolean isNextMoveValid() {

        if (heading == Heading.NORTH) {
            return(new VerifyMoveNorth(maze, this).verify()); 
        }
        else if (heading == Heading.EAST) {
            return(new VerifyMoveEast(maze, this).verify()); 
        }
        else if (heading == Heading.SOUTH) {
            return(new VerifyMoveSouth(maze, this).verify()); 
        }
        else {
            return(new VerifyMoveWest(maze, this).verify()); 
        }
    }

    // Turns the maze explorer left or right in-place
    public void turn(char direction) {

        Heading[] directions = {Heading.NORTH, Heading.EAST, Heading.SOUTH, Heading.WEST};

        // Rotates to the next direction, depending on whether the maze explorer turns left or right
        // Left: N -> W, Right: N -> E
        for (int i=0; i<directions.length; i++) {

            if (directions[i] == heading) {

                if (direction == 'L') {
                    heading = directions[Math.floorMod(i-1, directions.length)];
                }
                else {
                    heading = directions[Math.floorMod(i+1, directions.length)];
                }
                break;

            }
        }
    }

    // Moves the maze explorer in the direction its currently facing
    public void moveForward() {

        if (heading == Heading.NORTH) {
            location[0] -= 1;
        }
        else if (heading == Heading.EAST) {
            location[1] += 1;
        }
        else if (heading == Heading.SOUTH) {
            location[0] += 1;
        }
        else {
            location[1] -= 1;
        }
    }

    public int[] locationAfterMovingNorth() {

        int[] nextLocation = {location[0] - 1, location[1]};
        return nextLocation;
    }

    public int[] locationAfterMovingSouth() {

        int[] nextLocation = {location[0] + 1, location[1]};
        return nextLocation;
    }

    public int[] locationAfterMovingEast() {

        int[] nextLocation = {location[0], location[1] + 1};
        return nextLocation;

    }

    public int[] locationAfterMovingWest() {

        int[] nextLocation = {location[0], location[1] - 1};
        return nextLocation;

    }

    public int[] location() {
        return this.location;
    }

    public MazeExplorer (int[] pLocation, Heading pHeading, Maze pMaze) {

        this.location[0] = pLocation[0];
        this.location[1] = pLocation[1];
        this.heading = pHeading;
        this.maze = pMaze;
        
    }

}

