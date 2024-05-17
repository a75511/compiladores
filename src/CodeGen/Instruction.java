package CodeGen;

import java.io.DataOutputStream;

public class Instruction {
    OpCode opCode;

    public Instruction(OpCode opCode) {
        this.opCode = opCode;
    }

    public void write(DataOutputStream dos) throws Exception {
        dos.writeByte(opCode.ordinal());}

    public OpCode getOp() {
        return opCode;
    }
}

