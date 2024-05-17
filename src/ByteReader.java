import CodeGen.*;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.*;

public class ByteReader {
    ArrayList<Instruction> code;
    byte[] byteCode;
    int IP;
    int FP;
    boolean hasHalt;
    Stack<Object> stack;
    ArrayList<Object> global;
    boolean trace;

    HashMap<Integer, Object> constantPool = new HashMap<>();

    public ByteReader(byte[] byteCode, boolean trace) {
        this.byteCode = byteCode;
        this.code = new ArrayList<>();
        this.stack = new Stack<>();
        this.IP = 0;
        this.FP = 0;
        this.global = new ArrayList<>();
        this.hasHalt = false;
        this.trace = trace;
        decode(this.byteCode);
    }

    private void decode(byte[] byteCode) {
        DataInputStream din = new DataInputStream(new ByteArrayInputStream(byteCode));
        int i = 0;
        int debug = 0;
        try {
            while (din.available() > 0) {
                byte b = din.readByte();
                OpCode op = OpCode.convert(b);
                if (op == OpCode.end) break;
                switch (op.getNArgs()) {
                    case 0:
                        code.add(new Instruction(op));
                        debug++;
                        break;
                    case 1:
                        int arg = din.readInt();
                        debug++;
                        code.add(new IntInstruction(op, arg));
                        if (op == OpCode.dconst) {
                            constantPool.put(arg, OpCode.dconst);
                        }
                        if (op == OpCode.sconst) {
                            constantPool.put(arg, OpCode.sconst);
                        }
                        break;
                    default:
                        throw new RuntimeException("Invalid instruction");
                }
            }
            while (din.available() > 0) {
                Object op = constantPool.get(i);
                debug++;
                if (op == OpCode.dconst) {
                    constantPool.put(i, din.readDouble());
                }
                if (op == OpCode.sconst) {
                    int length = din.readInt();
                    char[] chars = new char[length];
                    for (int j = 0; j < length; j++) {
                        chars[j] = din.readChar();
                    }
                    String str = new String(chars);
                    constantPool.put(i, str);
                }
                i++;
            }
        } catch (Exception e) {
            System.out.println(debug);
            throw new RuntimeException(e);
        }
    }

    public void execInst(Instruction inst) {
        OpCode op = inst.getOp();
        switch (op) {
            case iprint:
                if(stack.empty())
                    new ErrorHandler("line " + IP + " ERROR: Empty Stack!");
                if (!(stack.peek() instanceof Integer))
                    new ErrorHandler("line " + IP + " ERROR: Invalid type at top of stack: Must be Integer!");
                System.out.println(stack.pop());
                break;
            case dprint:
                if(stack.empty())
                    new ErrorHandler("line " + IP + " ERROR: Empty Stack!");
                if (!(stack.peek() instanceof Double))
                    new ErrorHandler("line " + IP + " ERROR: Invalid type at top of stack: Must be Double!");
                System.out.println(stack.pop());
                break;
            case sprint:
                if(stack.empty())
                    new ErrorHandler("line " + IP + " ERROR: Empty Stack!");
                if (!(stack.peek() instanceof String))
                    new ErrorHandler("line " + IP + " ERROR: Invalid type at top of stack: Must be String!");
                System.out.println(stack.pop());
                break;
            case bprint:
                if(stack.empty())
                    new ErrorHandler("line " + IP + " ERROR: Empty Stack!");
                if (!(stack.peek() instanceof Boolean))
                    new ErrorHandler("line " + IP + " ERROR: Invalid type at top of stack: Must be Boolean!");
                System.out.println(stack.pop());
                break;
            case iuminus:
                if(stack.empty())
                    new ErrorHandler("line " + IP + " ERROR: Empty Stack!");
                if (!(stack.peek() instanceof Integer))
                    new ErrorHandler("line " + IP + " ERROR: Invalid type at top of stack: Must be Integer!");
                stack.push(-(int) stack.pop());
                break;
            case duminus:
                if(stack.empty())
                    new ErrorHandler("line " + IP + " ERROR: Empty Stack!");
                if (!(stack.peek() instanceof Double))
                    new ErrorHandler("line " + IP + " ERROR: Invalid type at top of stack: Must be Double!");
                stack.push(-(double) stack.pop());
                break;
            case iadd:
                if(stack.size() < 2)
                    new ErrorHandler("line " + IP + " ERROR: iadd needs 2 elements on top of stack!");
                if (!(stack.peek() instanceof Integer) || !(stack.elementAt(stack.size()-2) instanceof Integer))
                    new ErrorHandler("line " + IP + " ERROR: Invalid types at top of stack: Must be Integers!");
                stack.push((int) stack.pop() + (int) stack.pop());
                break;
            case isub:
                if(stack.size() < 2)
                    new ErrorHandler("line " + IP + " ERROR: isub needs 2 elements on top of stack!");
                if (!(stack.peek() instanceof Integer) || !(stack.elementAt(stack.size()-2) instanceof Integer))
                    new ErrorHandler("line " + IP + " ERROR: Invalid types at top of stack: Must be Integers!");
                int temp6 = (int) stack.pop();
                stack.push((int) stack.pop() - temp6);
                break;
            case imul:
                if(stack.size() < 2)
                    new ErrorHandler("line " + IP + " ERROR: imul needs 2 elements on top of stack!");
                if (!(stack.peek() instanceof Integer) || !(stack.elementAt(stack.size()-2) instanceof Integer))
                    new ErrorHandler("line " + IP + " ERROR: Invalid types at top of stack: Must be Integers!");
                stack.push((int) stack.pop() * (int) stack.pop());
                break;
            case idiv:
                if(stack.size() < 2)
                    new ErrorHandler("line " + IP + " ERROR: idiv needs 2 elements on top of stack!");
                if (!(stack.peek() instanceof Integer) || !(stack.elementAt(stack.size()-2) instanceof Integer))
                    new ErrorHandler("line " + IP + " ERROR: Invalid types at top of stack: Must be Integers!");
                if( (int)stack.peek() == 0 )
                    new ErrorHandler("line " + IP + " ERROR: Division by 0!");
                int temp12 = (int) stack.pop();
                stack.push((int) stack.pop() / temp12);
                break;
            case imod:
                if(stack.size() < 2)
                    new ErrorHandler("line " + IP + " ERROR: imod needs 2 elements on top of stack!");
                if (!(stack.peek() instanceof Integer) || !(stack.elementAt(stack.size()-2) instanceof Integer))
                    new ErrorHandler("line " + IP + " ERROR: Invalid types at top of stack: Must be Integers!");
                if( (int)stack.peek() == 0 )
                    new ErrorHandler("line " + IP + " ERROR: Division by 0!");
                int temp7 = (int) stack.pop();
                stack.push((int) stack.pop() % temp7);
                break;
            case dadd:
                if(stack.size() < 2)
                    new ErrorHandler("line " + IP + " ERROR: dadd needs 2 elements on top of stack!");
                if (!(stack.peek() instanceof Double) || !(stack.elementAt(stack.size()-2) instanceof Double))
                    new ErrorHandler("line " + IP + " ERROR: Invalid types at top of stack: Must be Doubles!");
                stack.push((double) stack.pop() + (double) stack.pop());
                break;
            case dsub:
                if(stack.size() < 2)
                    new ErrorHandler("line " + IP + " ERROR: dsub needs 2 elements on top of stack!");
                if (!(stack.peek() instanceof Double) || !(stack.elementAt(stack.size()-2) instanceof Double))
                    new ErrorHandler("line " + IP + " ERROR: Invalid types at top of stack: Must be Doubles!");
                double temp = (double) stack.pop();
                stack.push((double) stack.pop() - temp);
                break;
            case dmul:
                if(stack.size() < 2)
                    new ErrorHandler("line " + IP + " ERROR: dmul needs 2 elements on top of stack!");
                if (!(stack.peek() instanceof Double) || !(stack.elementAt(stack.size()-2) instanceof Double))
                    new ErrorHandler("line " + IP + " ERROR: Invalid types at top of stack: Must be Doubles!");
                stack.push((double) stack.pop() * (double) stack.pop());
                break;
            case ddiv:
                if(stack.size() < 2)
                    new ErrorHandler("line " + IP + " ERROR: ddiv needs 2 elements on top of stack!");
                if (!(stack.peek() instanceof Double) || !(stack.elementAt(stack.size()-2) instanceof Double))
                    new ErrorHandler("line " + IP + " ERROR: Invalid types at top of stack: Must be Doubles!");
                if( (double)stack.peek() == 0 )
                    new ErrorHandler("line " + IP + " ERROR: Division by 0!");
                double temp15 = (double) stack.pop();
                stack.push((double) stack.pop() / temp15);
                break;
            case sadd:
                if(stack.size() < 2)
                    new ErrorHandler("line " + IP + " ERROR: sadd needs 2 elements on top of stack!");
                if (!(stack.peek() instanceof String) || !(stack.elementAt(stack.size()-2) instanceof String))
                    new ErrorHandler("line " + IP + " ERROR: Invalid types at top of stack: Must be Strings!");
                String temp2 =  (String) stack.pop();
                stack.push((String) stack.pop() + temp2);
                break;
            case and:
                if(stack.size() < 2)
                    new ErrorHandler("line " + IP + " ERROR: and needs 2 elements on top of stack!");
                if (!(stack.peek() instanceof Boolean) || !(stack.elementAt(stack.size()-2) instanceof Boolean))
                    new ErrorHandler("line " + IP + " ERROR: Invalid types at top of stack: Must be Booleans!");
                boolean temp5 = (boolean) stack.pop();
                boolean temp16 = (boolean) stack.pop();
                stack.push(temp5 && temp16);
                break;
            case or:
                if(stack.size() < 2)
                    new ErrorHandler("line " + IP + " ERROR: or needs 2 elements on top of stack!");
                if (!(stack.peek() instanceof Boolean) || !(stack.elementAt(stack.size()-2) instanceof Boolean))
                    new ErrorHandler("line " + IP + " ERROR: Invalid types at top of stack: Must be Booleans!");
                stack.push((boolean) stack.pop() || (boolean) stack.pop());
                break;
            case not:
                if(stack.empty())
                    new ErrorHandler("line " + IP + " ERROR: Empty Stack!");
                if (!(stack.peek() instanceof Boolean))
                    new ErrorHandler("line " + IP + " ERROR: Invalid type at top of stack: Must be Boolean!");
                stack.push(!(boolean) stack.pop());
                break;
            case ieq:
                if(stack.size() < 2)
                    new ErrorHandler("line " + IP + " ERROR: ieq needs 2 elements on top of stack!");
                if (!(stack.peek() instanceof Integer) || !(stack.elementAt(stack.size()-2) instanceof Integer))
                    new ErrorHandler("line " + IP + " ERROR: Invalid types at top of stack: Must be Integers!");
                stack.push((int) stack.pop() == (int) stack.pop());
                break;
            case ineq:
                if(stack.size() < 2)
                    new ErrorHandler("line " + IP + " ERROR: ineq needs 2 elements on top of stack!");
                if (!(stack.peek() instanceof Integer) || !(stack.elementAt(stack.size()-2) instanceof Integer))
                    new ErrorHandler("line " + IP + " ERROR: Invalid types at top of stack: Must be Integers!");
                stack.push((int) stack.pop() != (int) stack.pop());
                break;
            case ilt:
                if(stack.size() < 2)
                    new ErrorHandler("line " + IP + " ERROR: ilt needs 2 elements on top of stack!");
                if (!(stack.peek() instanceof Integer) || !(stack.elementAt(stack.size()-2) instanceof Integer))
                    new ErrorHandler("line " + IP + " ERROR: Invalid types at top of stack: Must be Integers!");
                int temp17 = (int) stack.pop();
                stack.push((int) stack.pop() < temp17);
                break;
            case ileq:
                if(stack.size() < 2)
                    new ErrorHandler("line " + IP + " ERROR: ileq needs 2 elements on top of stack!");
                if (!(stack.peek() instanceof Integer) || !(stack.elementAt(stack.size()-2) instanceof Integer))
                    new ErrorHandler("line " + IP + " ERROR: Invalid types at top of stack: Must be Integers!");
                int temp3 = (int) stack.pop();
                stack.push((int) stack.pop() <= temp3);
                break;
            case igt:
                if(stack.size() < 2)
                    new ErrorHandler("line " + IP + " ERROR: ilt needs 2 elements on top of stack!");
                if (!(stack.peek() instanceof Integer) || !(stack.elementAt(stack.size()-2) instanceof Integer))
                    new ErrorHandler("line " + IP + " ERROR: Invalid types at top of stack: Must be Integers!");
                int temp1 = (int) stack.pop();
                stack.push((int) stack.pop() > temp1);
                break;
            case igeq:
                if(stack.size() < 2)
                    new ErrorHandler("line " + IP + " ERROR: ileq needs 2 elements on top of stack!");
                if (!(stack.peek() instanceof Integer) || !(stack.elementAt(stack.size()-2) instanceof Integer))
                    new ErrorHandler("line " + IP + " ERROR: Invalid types at top of stack: Must be Integers!");
                int temp4 = (int) stack.pop();
                stack.push((int) stack.pop() >= temp4);
                break;
            case deq:
                if(stack.size() < 2)
                    new ErrorHandler("line " + IP + " ERROR: deq needs 2 elements on top of stack!");
                if (!(stack.peek() instanceof Double) || !(stack.elementAt(stack.size()-2) instanceof Double))
                    new ErrorHandler("line " + IP + " ERROR: Invalid types at top of stack: Must be Doubles!");
                stack.push((double) stack.pop() == (double) stack.pop());
                break;
            case dneq:
                if(stack.size() < 2)
                    new ErrorHandler("line " + IP + " ERROR: dneq needs 2 elements on top of stack!");
                if (!(stack.peek() instanceof Double) || !(stack.elementAt(stack.size()-2) instanceof Double))
                    new ErrorHandler("line " + IP + " ERROR: Invalid types at top of stack: Must be Doubles!");
                stack.push((double) stack.pop() != (double) stack.pop());
                break;
            case dlt:
                if(stack.size() < 2)
                    new ErrorHandler("line " + IP + " ERROR: dlt needs 2 elements on top of stack!");
                if (!(stack.peek() instanceof Double) || !(stack.elementAt(stack.size()-2) instanceof Double))
                    new ErrorHandler("line " + IP + " ERROR: Invalid types at top of stack: Must be Doubles!");
                double temp8 = (double) stack.pop();
                stack.push((double) stack.pop() < temp8);
                break;
            case dleq:
                if(stack.size() < 2)
                    new ErrorHandler("line " + IP + " ERROR: dleq needs 2 elements on top of stack!");
                if (!(stack.peek() instanceof Double) || !(stack.elementAt(stack.size()-2) instanceof Double))
                    new ErrorHandler("line " + IP + " ERROR: Invalid types at top of stack: Must be Doubles!");
                double temp9 = (double) stack.pop();
                stack.push((double) stack.pop() <= temp9);
                break;
            case dgt:
                if(stack.size() < 2)
                    new ErrorHandler("line " + IP + " ERROR: dlt needs 2 elements on top of stack!");
                if (!(stack.peek() instanceof Double) || !(stack.elementAt(stack.size()-2) instanceof Double))
                    new ErrorHandler("line " + IP + " ERROR: Invalid types at top of stack: Must be Doubles!");
                double temp10 = (double) stack.pop();
                stack.push((double) stack.pop() > temp10);
                break;
            case dgeq:
                if(stack.size() < 2)
                    new ErrorHandler("line " + IP + " ERROR: dleq needs 2 elements on top of stack!");
                if (!(stack.peek() instanceof Double) || !(stack.elementAt(stack.size()-2) instanceof Double))
                    new ErrorHandler("line " + IP + " ERROR: Invalid types at top of stack: Must be Doubles!");
                double temp11 = (double) stack.pop();
                stack.push((double) stack.pop() >= temp11);
                break;
            case beq:
                if(stack.size() < 2)
                    new ErrorHandler("line " + IP + " ERROR: beq needs 2 elements on top of stack!");
                if (!(stack.peek() instanceof Boolean) || !(stack.elementAt(stack.size()-2) instanceof Boolean))
                    new ErrorHandler("line " + IP + " ERROR: Invalid types at top of stack: Must be Booleans!");
                stack.push((boolean) stack.pop() == (boolean) stack.pop());
                break;
            case bneq:
                if(stack.size() < 2)
                    new ErrorHandler("line " + IP + " ERROR: bneq needs 2 elements on top of stack!");
                if (!(stack.peek() instanceof Boolean) || !(stack.elementAt(stack.size()-2) instanceof Boolean))
                    new ErrorHandler("line " + IP + " ERROR: Invalid types at top of stack: Must be Booleans!");
                stack.push((boolean) stack.pop() != (boolean) stack.pop());
                break;
            case seq:
                if(stack.size() < 2)
                    new ErrorHandler("line " + IP + " ERROR: seq needs 2 elements on top of stack!");
                if (!(stack.peek() instanceof String) || !(stack.elementAt(stack.size()-2) instanceof String))
                    new ErrorHandler("line " + IP + " ERROR: Invalid types at top of stack: Must be Strings!");
                stack.push(((String) stack.pop()).equals((String) stack.pop()));
                break;
            case sneq:
                if(stack.size() < 2)
                    new ErrorHandler("line " + IP + " ERROR: sneq needs 2 elements on top of stack!");
                if (!(stack.peek() instanceof String) || !(stack.elementAt(stack.size()-2) instanceof String))
                    new ErrorHandler("line " + IP + " ERROR: Invalid types at top of stack: Must be Strings!");
                stack.push(!((String) stack.pop()).equals((String) stack.pop()));
                break;
            case iconst:
                stack.push(((IntInstruction) inst).getArg());
                break;
            case dconst:
                stack.push(constantPool.get(((IntInstruction) inst).getArg()));
                break;
            case sconst:
                stack.push(constantPool.get(((IntInstruction) inst).getArg()));
                break;
            case fconst:
                stack.push(false);
                break;
            case tconst:
                stack.push(true);
                break;
            case jump:
                IP = ((IntInstruction) inst).getArg();
                break;
            case jumpt:
                if(stack.empty())
                    new ErrorHandler("line " + IP + " ERROR: Empty Stack!");
                if (!(stack.peek() instanceof Boolean))
                    new ErrorHandler("line " + IP + " ERROR: Invalid type at top of stack: Must be Boolean!");
                if ((boolean) stack.pop()) IP = ((IntInstruction) inst).getArg();
                break;
            case jumpf:
                if(stack.empty())
                    new ErrorHandler("line " + IP + " ERROR: Empty Stack!");
                if (!(stack.peek() instanceof Boolean))
                    new ErrorHandler("line " + IP + " ERROR: Invalid type at top of stack: Must be Boolean!");
                if (!(boolean) stack.pop()) IP = ((IntInstruction) inst).getArg();
                break;
            case galloc:
                for(int j = 0; j < ((IntInstruction) inst).getArg(); j++) {
                    this.global.add(null);
                }
                break;
            case gload:
                if(!(((IntInstruction) inst).getArg() >= 0) || !(((IntInstruction) inst).getArg() < global.size()))
                    new ErrorHandler("line " + IP + " ERROR: Invalid Globals address!");
                if(global.get(((IntInstruction) inst).getArg()) == null)
                    new ErrorHandler("line " + IP + " ERROR: Loading NULL element!");
                stack.push(global.get(((IntInstruction) inst).getArg()));
                break;
            case gstore:
                if(!(((IntInstruction) inst).getArg() >= 0) || !(((IntInstruction) inst).getArg() < global.size()))
                    new ErrorHandler("line " + IP + " ERROR: Invalid Globals address!");
                if(stack.empty())
                    new ErrorHandler("line " + IP + " ERROR: Empty stack!");
                global.set(((IntInstruction) inst).getArg(), stack.pop());
                break;
            case itos:
                if(stack.empty())
                    new ErrorHandler("line " + IP + " ERROR: Empty Stack!");
                if (!(stack.peek() instanceof Integer))
                    new ErrorHandler("line " + IP + " ERROR: Invalid type at top of stack: Must be Integer!");
                stack.push(Integer.toString((int) stack.pop()));
                break;
            case itod:
                if(stack.empty())
                    new ErrorHandler("line " + IP + " ERROR: Empty Stack!");
                if (!(stack.peek() instanceof Integer))
                    new ErrorHandler("line " + IP + " ERROR: Invalid type at top of stack: Must be Integer!");
                stack.push((double) (int) stack.pop());
                break;
            case dtos:
                if(stack.empty())
                    new ErrorHandler("line " + IP + " ERROR: Empty Stack!");
                if (!(stack.peek() instanceof Double))
                    new ErrorHandler("line " + IP + " ERROR: Invalid type at top of stack: Must be Double!");
                stack.push(Double.toString((double) stack.pop()));
                break;
            case btos:
                if(stack.empty())
                    new ErrorHandler("line " + IP + " ERROR: Empty Stack!");
                if (!(stack.peek() instanceof Boolean))
                    new ErrorHandler("line " + IP + " ERROR: Invalid type at top of stack: Must be Boolean!");
                stack.push(Boolean.toString((boolean) stack.pop()));
                break;
            case lalloc:
                if (((IntInstruction) inst).getArg() <= 0)
                    new ErrorHandler("ERROR: Invalid number of slots for local allocation");
                for (int j = 0; j < ((IntInstruction) inst).getArg(); j++) {
                    stack.push(null);
                }
                break;
            case lload:
                if (((IntInstruction) inst).getArg() + FP >= stack.size() || ((IntInstruction) inst).getArg() + FP < 0)
                    new ErrorHandler("ERROR: Invalid local address");
                stack.push(stack.elementAt(FP + ((IntInstruction) inst).getArg()));
                break;
            case lstore:
                if (((IntInstruction) inst).getArg() + FP >= stack.size() || ((IntInstruction) inst).getArg() + FP < 0)
                    new ErrorHandler("ERROR: Invalid local address");
                stack.add(FP + ((IntInstruction) inst).getArg(), stack.pop());
                break;
            case pop:
                if (((IntInstruction) inst).getArg() <= 0)
                    new ErrorHandler("ERROR: Invalid number of elements to pop");
                for (int j = 0; j < ((IntInstruction) inst).getArg(); j++) {
                    if (stack.empty())
                        new ErrorHandler("ERROR: Empty stack");
                    stack.pop();
                }
                break;
            case call:
                stack.push(FP);
                FP = stack.size();
                stack.push(IP);
                IP = ((IntInstruction) inst).getArg();
                break;
            case retval:
                int desempilhar = ((IntInstruction) inst).getArg();
                Object x = stack.pop();
                while (stack.size() > FP+2) {
                    stack.pop();
                }
                Object returnAddress = stack.pop();
                IP = (int) returnAddress;
                Object FpValue = stack.pop();
                FP = (int) FpValue;
                for ( int i=0; i<desempilhar; i++){
                    stack.pop();
                }
                stack.push(x);
                break;
            case ret:
                int desempilhar2 = ((IntInstruction) inst).getArg();
                while (stack.size() > FP+2) {
                    stack.pop();
                }
                Object returnAddress2 = stack.pop();
                IP = (int) returnAddress2;
                Object FpValue2 = stack.pop();
                FP = (int) FpValue2;
                for ( int i=0; i<desempilhar2; i++){
                    stack.pop();
                }
                break;
            case halt:
                IP = code.size();
                hasHalt = true;
                break;
            default:
                new ErrorHandler("ERROR: Invalid instruction");
        }
    }

    public void run() {
        if(trace) {
            if (!constantPool.isEmpty()) {
                System.out.println("Constant Pool:");
                for (int j = 0; j < constantPool.size(); j++) {
                    System.out.println(j + ": \"" + constantPool.get(j) + "\"");
                }
            }
        }
        while (IP < code.size()) {
            if(trace)
                print(code.get(IP));
            execInst(code.get(IP++));
        }
    }

    public void print(Instruction instr) {
        if (instr instanceof IntInstruction) {
            System.out.println(IP + ": " + instr.getOp() + " " + ((IntInstruction) instr).getArg());
        } else if (instr instanceof DoubleInstruction){
            System.out.println(IP + ": " + instr.getOp() + " " + ((DoubleInstruction) instr).getArg());
        } else if (instr instanceof StringInstruction){
            System.out.println(IP + ": " + instr.getOp() + " " + ((StringInstruction) instr).getArg());
        } else if (instr instanceof BooleanInstruction){
            System.out.println(IP + ": " + instr.getOp() + " " + ((BooleanInstruction) instr).getArg());
        } else{
            System.out.println(IP + ": " + instr.getOp());
        }

        System.out.println("Stack: " + stack);

        if(global != null){
            System.out.println("Globals: " + global);
        }
    }

}
