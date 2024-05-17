package CodeGen;

import java.io.DataOutputStream;

public class StringInstruction extends Instruction {
    String arg;
    public StringInstruction(OpCode opCode, String arg) {
        super(opCode);
        this.arg = arg;
    }

    public void write(DataOutputStream dos) throws Exception {
        dos.writeInt(arg.length());
        dos.writeChars(arg);
    }

    public String getArg() {
        return arg;
    }
}
