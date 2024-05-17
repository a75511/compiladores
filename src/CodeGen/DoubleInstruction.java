package CodeGen;

import java.io.DataOutputStream;

public class DoubleInstruction extends Instruction {
    double arg;
    public DoubleInstruction(OpCode opCode, double arg) {
        super(opCode);
        this.arg = arg;
    }

    public void write(DataOutputStream dos) throws Exception {
        dos.writeDouble(arg);
    }

    public double getArg() {
        return arg;
    }
}
