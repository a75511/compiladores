package CodeGen;

import java.io.DataOutputStream;

public class BooleanInstruction extends Instruction{
    boolean arg;
    public BooleanInstruction(OpCode opCode, boolean arg) {
        super(opCode);
        this.arg = arg;
    }

    public void write(DataOutputStream dos) throws Exception {
        dos.writeByte(opCode.ordinal());
        dos.writeBoolean(arg);
    }

    public boolean getArg() {
        return arg;
    }
}
