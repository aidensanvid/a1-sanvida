package ca.mcmaster.se2aa4.mazerunnertest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.mazerunner.enumerations.Heading;
import ca.mcmaster.se2aa4.mazerunner.maze.Maze;
import ca.mcmaster.se2aa4.mazerunner.maze_explorer.MazeExplorer;
import ca.mcmaster.se2aa4.mazerunner.path.Path;
import ca.mcmaster.se2aa4.mazerunner.path_finder.PathFinder;
import ca.mcmaster.se2aa4.mazerunner.path_verifier.PathVerifier;


public class MazeRunnerTest {

    private static final Logger logger = LogManager.getLogger();

    private Maze maze;
    private MazeExplorer mazeExplorer;
    private PathFinder pathFinder;
    private PathVerifier pathVerifier;
    private Path path;

    @Test
    public void testFindWestEntrance() {
        
        try {

            maze = new Maze("./examples/regular.maz.txt");
            int[] result = maze.findWestEntrance();
            int[] expectedResult = {33, 0};
            
            assertArrayEquals(result, expectedResult);

        } catch (Exception e) {
            fail("Invalid test path");
        }
    }

    @Test
    public void testFindEastEntrance() {
        
        try {
            maze = new Maze("./examples/small.maz.txt");
            int[] result = maze.findEastEntrance();
            int[] expectedResult = {5, 10};
            
            assertArrayEquals(result, expectedResult);

        } catch (Exception e) {
            fail("Invalid test path");
        }
    }

    @Test
    public void testIsWall() {
        
        try {
            maze = new Maze("./examples/straight.maz.txt");
            
            int[] coords = {1, 1};

            assertTrue(maze.isWall(coords));

        } catch (Exception e) {
            fail("Invalid test path");
        }
    }

    @Test
    public void testInBounds() {
        
        try {
            maze = new Maze("./examples/huge.maz.txt");
            
            int[] coords = {-1, 10};

            assertFalse(maze.inBounds(coords));

        } catch (Exception e) {
            fail("Invalid test path");
        }
    }

    @Test
    public void testAtEnd() {
        
        try {
            maze = new Maze("./examples/small.maz.txt");
            
            int[] coords = {8, 0};

            assertTrue(maze.atEnd(coords, Heading.WEST));

        } catch (Exception e) {
            fail("Invalid test path");
        }
    }

    @Test
    public void testCanonToFactorized() {

        try {
            path = new Path("FFLRRLF");

            String result = path.canonToFactorized();
            String expectedResult = "2FL2RLF";
            assertEquals(result, expectedResult);

        } catch (Exception e) {
            fail("Invalid test path");
        }
    }

    @Test
    public void testSolveMaze() {

        try {

            maze = new Maze("./examples/medium.maz.txt");
            pathFinder = new PathFinder(maze);

            String result = pathFinder.solveMaze(Heading.WEST, Heading.EAST);
            String expectedResult = "FR6F2L8FR2FR2F2L2FR2FR4FR2FL4FL2F2L2FR4FR2FL2FR2FR4FR2F2L2FL2FR2FR4FR2F2L2FL2FR2F2L2FR2FR2F2L4FR2FR2F2L4FR2FR2F2L4FR2FR2F2L2FR10FR2FR8F2L8FL2FR4FR2FR2F2L2FR2FR14F2L12FR2FR6F2L4FR2FR6FR2FL6F2L6FR2FR8F2L12FR2FR10F2L6FR2FR4F2L4FL2FR4FL2FR2FL2FR2FL2FR2FL4FR2FR2F2L4FR2FR6FR2F2L2FR2FR4F2L2FR2FR4F2L4FR2FR2F2L2FR2FR4FR2FL2F2L2FR2FR6FL2FR8F2L8FR2FR10FR4FR2F2L2FR2F2L2FR2FR2FL4FR2F2L4F2L2FR4FR2FR2F2L4FR2FR6F2L6FR4FR2FR2FL2F2L2FR4FR2FR2F2L2FR2FR4F2L4FL4FR2FR4F2L2FR2F2L2FR2FR2F2L6FR2FR8FR6FR2F2L2FL2FRF";
            assertEquals(result, expectedResult);

        } catch (Exception e) {
            fail("Invalid test path");
        }
    }

    @Test
    public void testPathFromBothEntrances() {

        try {
            maze = new Maze("./examples/direct.maz.txt");
            pathVerifier = new PathVerifier(maze);
            path = new Path("FR2FL3FRFLFRFL2F");
            assertTrue(pathVerifier.testPathFromBothEntrances(path));

        } catch (Exception e) {
            fail("Invalid test path:" + e);
        }
    }

    @Test
    public void testPathFromEntrance() {

        try {
            maze = new Maze("./examples/tiny.maz.txt");
            pathVerifier = new PathVerifier(maze);
            path = new Path("3F L 4F R 3F");
            assertTrue(pathVerifier.testPathFromEntrance(path, Heading.WEST, Heading.EAST));

        } catch (Exception e) {
            fail("Invalid test path:" + e);
        }
    }

    @Test
    public void testGetMazeDimensions() {

        try {
            maze = new Maze("./examples/rectangle.maz.txt");

            int[] result = maze.getMazeDimensions("./examples/rectangle.maz.txt");
            int[] expectedResult = {21,51};

            assertArrayEquals(result, expectedResult);

        } catch (Exception e) {
            fail("Invalid test path");
        }
    }
    
}
