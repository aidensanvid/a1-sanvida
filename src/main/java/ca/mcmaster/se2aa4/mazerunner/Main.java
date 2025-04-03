package ca.mcmaster.se2aa4.mazerunner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.se2aa4.mazerunner.enumerations.Heading;
import ca.mcmaster.se2aa4.mazerunner.maze.Maze;
import ca.mcmaster.se2aa4.mazerunner.path.Path;
import ca.mcmaster.se2aa4.mazerunner.path_finder.PathFinder;
import ca.mcmaster.se2aa4.mazerunner.path_verifier.PathVerifier;

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

                String mazeFile = cmd.getOptionValue("i");
                Maze maze = new Maze(mazeFile);

                logger.info("Starting Maze Runner");
                logger.info("Reading the maze from file " + mazeFile);
            

                // Checks for user-entered path
                if (cmd.hasOption("p")) {

                    Path path = new Path(cmd.getOptionValue("p"));
                    PathVerifier pathVerifier = new PathVerifier(maze);


                    if (pathVerifier.testPathFromBothEntrances(path)) {
                        System.out.println("correct path");
                    }
                    else {
                        System.out.println("incorrect path");
                    }

                    
                }
                // Solves the maze if the user doesn't include their own path to verify
                else {
                    
                    PathFinder path_finder = new PathFinder(maze);
                    System.out.println(path_finder.solveMaze(Heading.WEST, Heading.EAST));

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