package CodeGen;

public enum OpCode {
    iprint(0), dprint(0), sprint(0), bprint(0),
    iuminus(0), duminus(0),
    iadd(0), isub(0), imul(0), idiv(0), imod(0), dadd(0), dsub(0), dmul(0), ddiv(0), sadd(0), and(0), or(0), not(0),
    ieq(0), ineq(0), ilt(0), ileq(0), igt(0), igeq(0), deq(0), dneq(0), dlt(0), dleq(0), dgt(0), dgeq(0), beq(0), bneq(0), seq(0), sneq(0),
    iconst(1), dconst(1), sconst(1), tconst(0), fconst(0),
    jump(1), jumpt(1), jumpf(1),
    galloc(1), gload(1), gstore(1),
    itos(0), itod(0), dtos(0), btos(0),
    halt(0), end(0),
    label(1), lalloc(1), lload(1), lstore(1), pop(1), call(1), retval(1), ret(1);

    private final int nArgs;

    OpCode(int nArgs) {
        this.nArgs = nArgs;
    }

    public int getNArgs() {
        return nArgs;
    }

    public static OpCode convert(byte value) {
        return OpCode.values()[value];
    }
}
