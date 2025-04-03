package ca.mcmaster.se2aa4.mazerunner.path_verifier.test_instructions;
import ca.mcmaster.se2aa4.mazerunner.maze_explorer.MazeExplorer;
import ca.mcmaster.se2aa4.mazerunner.path_finder.commands.TurnLeftCommand;
import ca.mcmaster.se2aa4.mazerunner.path_verifier.PathVerifier;

public class TestLeftInstruction extends DefaultTestInstruction {

    @Override
    protected void processFactorizedInstructionHook() {

        int repetitions = Integer.parseInt(factorizedCoefficient);

        for (int j = 0; j<repetitions; j++) {

            executeCommand(new TurnLeftCommand(mazeExplorer));
        }
    }

    @Override
    protected void processCanonicalInstructionHook() {
        executeCommand(new TurnLeftCommand(mazeExplorer));
    }

    public TestLeftInstruction(String pFactorizedCoefficient, PathVerifier pPathVerifier, MazeExplorer pMazeExplorer) {
        super(pFactorizedCoefficient, pPathVerifier, pMazeExplorer);
    }
    
}