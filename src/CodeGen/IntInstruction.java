package CodeGen;

import java.io.DataOutputStream;

public class IntInstruction extends Instruction {
    int arg;
    public IntInstruction(OpCode opCode, int arg) {
        super(opCode);
        this.arg = arg;
    }

    public void write(DataOutputStream dos) throws Exception {
        dos.writeByte(opCode.ordinal());
        dos.writeInt(arg);
    }

    public int getArg() {
        return arg;
    }
}
