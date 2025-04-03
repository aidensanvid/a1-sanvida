package ca.mcmaster.se2aa4.mazerunner.path_verifier.test_instructions;
import ca.mcmaster.se2aa4.mazerunner.maze_explorer.MazeExplorer;
import ca.mcmaster.se2aa4.mazerunner.path_finder.commands.Command;
import ca.mcmaster.se2aa4.mazerunner.path_verifier.PathVerifier;


public abstract class DefaultTestInstruction implements TestInstruction {

    protected PathVerifier pathVerifier;
    protected String factorizedCoefficient;
    protected MazeExplorer mazeExplorer;

    protected DefaultTestInstruction(String pFactorizedCoefficient, PathVerifier pPathVerifier, MazeExplorer pMazeExplorer) {
        this.factorizedCoefficient = pFactorizedCoefficient;
        this.pathVerifier = pPathVerifier;
        this.mazeExplorer = pMazeExplorer;
    }

    @Override
    public String test() {

                // Processes canonical inputs (e.g. F, RR, LLL)
                if (factorizedCoefficient.equals("")) {
                    processCanonicalInstructionHook();
                }
                // Processes factorized inputs (e.g. 4F, 2R, 19L)
                else {
                    processFactorizedInstructionHook();
                }

                factorizedCoefficient = "";

                return factorizedCoefficient;
    }

    public void executeCommand(Command command) {
        command.execute();
    }

    protected abstract void processFactorizedInstructionHook();
    protected abstract void processCanonicalInstructionHook();

}