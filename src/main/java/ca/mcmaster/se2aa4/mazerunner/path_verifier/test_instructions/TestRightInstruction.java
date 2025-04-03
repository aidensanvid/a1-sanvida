package ca.mcmaster.se2aa4.mazerunner.path_verifier.test_instructions;
import ca.mcmaster.se2aa4.mazerunner.maze_explorer.MazeExplorer;
import ca.mcmaster.se2aa4.mazerunner.path_finder.commands.TurnRightCommand;
import ca.mcmaster.se2aa4.mazerunner.path_verifier.PathVerifier;

public class TestRightInstruction extends DefaultTestInstruction {

    @Override
    protected void processFactorizedInstructionHook() {

        int repetitions = Integer.parseInt(factorizedCoefficient);

        for (int j = 0; j<repetitions; j++) {

            executeCommand(new TurnRightCommand(mazeExplorer));
        }
    }

    @Override
    protected void processCanonicalInstructionHook() {
        executeCommand(new TurnRightCommand(mazeExplorer));
    }

    public TestRightInstruction(String pFactorizedCoefficient, PathVerifier pPathVerifier, MazeExplorer pMazeExplorer) {
        super(pFactorizedCoefficient, pPathVerifier, pMazeExplorer);
    }
    
}