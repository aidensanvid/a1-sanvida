package ca.mcmaster.se2aa4.mazerunner.path_verifier.test_instructions;
import ca.mcmaster.se2aa4.mazerunner.maze_explorer.MazeExplorer;
import ca.mcmaster.se2aa4.mazerunner.path_finder.commands.MoveForwardCommand;
import ca.mcmaster.se2aa4.mazerunner.path_verifier.PathVerifier;

public class TestForwardInstruction extends DefaultTestInstruction {

    @Override
    protected void processFactorizedInstructionHook() {

        int repetitions = Integer.parseInt(factorizedCoefficient);

        for (int j = 0; j<repetitions; j++) {

            if (mazeExplorer.isNextMoveValid()) {
                executeCommand(new MoveForwardCommand(mazeExplorer));
            }
        }
    }

    @Override
    protected void processCanonicalInstructionHook() {

        if (mazeExplorer.isNextMoveValid()) {
            executeCommand(new MoveForwardCommand(mazeExplorer));
        }
    }

    public TestForwardInstruction(String pFactorizedCoefficient, PathVerifier pPathVerifier, MazeExplorer pMazeExplorer) {
        super(pFactorizedCoefficient, pPathVerifier, pMazeExplorer);
    }
    
}