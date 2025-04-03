package ca.mcmaster.se2aa4.mazerunner.path;

public class Path {

    private String instructionSequence;

    public void removeWhitespace() {
        this.instructionSequence = instructionSequence.replaceAll("\\s", "");
    }

    public char getInstruction(int index) {
        return instructionSequence.charAt(index);
    }

    public void addInstruction(char instruction) {
        instructionSequence += instruction;
    }

    public int length() {
        return instructionSequence.length();
    }

    public Path(String pInstructionSequence) {
        this.instructionSequence = pInstructionSequence;
    }

    public Path() {
        this.instructionSequence = "";
    }

    public String canonToFactorized() {

        String factorizedSequence = "";

        char prevChar = ' ';
        String buffer = "";

        for (int i=0; i<instructionSequence.length(); i++) {
            char c = instructionSequence.charAt(i);    
            
            if (c == prevChar) {
                buffer += c;
            }
            else {
                
                if (i > 0) {

                    if (buffer.length() > 0) {
                        factorizedSequence += buffer.length() + 1;
                    }
                    factorizedSequence += prevChar;
                }

                prevChar = c;
                buffer = "";
                
            }
        }

        if (buffer.length() > 0) {
            factorizedSequence += buffer.length() + 1;
        }
        
        factorizedSequence += prevChar;

        return factorizedSequence;

    }

}