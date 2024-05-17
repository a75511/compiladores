package CodeGen;

import Sol.SolLexer;
import Sol.SolParser;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

public class SemanticErrors {
    int numErr;
    ParseTreeProperty<Type> values;

    public SemanticErrors( ParseTreeProperty<Type> values) {
        this.numErr = 0;
        this.values = values;
    }

    public int getNumErr() {
        return this.numErr;
    }

    public void TesteErro(String s) {
        System.out.println(s);
        numErr++;
    }
    public void BreakErr(SolParser.BreakStatementContext ctx) {
        System.out.println("line "+ctx.start.getLine()+" error: break must be inside a loop");
        numErr++;
    }
    public void IfErr(SolParser.IfContext ctx) {
        System.out.println("line "+ctx.start.getLine()+" error: "+ values.get(ctx.getChild(1)) + " must be a BOOLEAN expression");
        numErr++;
    }
    public void ForErr(SolParser.ForContext ctx) {
        System.out.println("line "+ctx.start.getLine()+" error: "+ values.get(ctx.getChild(1)) + " must be a INT variable");
        numErr++;
    }
    public void WhileErr(SolParser.WhileContext ctx) {
        System.out.println("line "+ctx.start.getLine()+" error: "+ "while must be of BOOLEAN type");
        numErr++;
    }
    public void AssingErr(SolParser.AssignInstContext ctx) {
        System.out.println("line "+ctx.start.getLine()+" error: "+ ctx.ID().getText() + " is already defined");
        numErr++;
    }

    public void IDErr(SolParser.AssignContext ctx) {
        System.out.println("line "+ctx.start.getLine()+" error: "+ ctx.ID().getText() + " is not defined");
        numErr++;
    }
    public void IDErr(SolParser.IdContext ctx) {
        System.out.println("line "+ctx.start.getLine()+" error: "+ ctx.ID().getText() + " is not defined");
        numErr++;
    }

    public void IDErr(SolParser.ForContext ctx) {
        System.out.println("line "+ctx.start.getLine()+" error: "+ ctx.getChild(1).getText() + " is not defined");
        numErr++;
    }
    public void VarErr(SolParser.VarContext ctx, Type type) {
        System.out.println("line "+ctx.start.getLine()+" error: "+ values.get(ctx.type()) + " and " + type + " are not the same type");
        numErr++;
    }

    public void VarErr(SolParser.ForContext ctx, Type type) {
        System.out.println("line "+ctx.start.getLine()+" error: "+ values.get(ctx.getChild(1)) + " and " + type + " are not the same type");
        numErr++;
    }

    public void VarErr(SolParser.InstContext ctx, Type type) {
        System.out.println("line "+ctx.start.getLine()+" error: "+ values.get(ctx.getChild(0)) + " and " + type + " are not the same type");
        numErr++;
    }
    public void GenericErr(SolParser.InstContext ctx) {
        //line 2:20 error: operator - is invalid between bool and int
        System.out.println("line "+ctx.start.getLine() + " error: " + ctx.getChild(1) + " is invalid between " + values.get(ctx.getChild(0)) + " and " + values.get(ctx.getChild(2)));
        numErr++;
    }

    public void unaryOpErr(SolParser.UnaryContext ctx) {
        System.out.println("line "+ctx.start.getLine()+" error: "+ ctx.op.getText() + " is invalid for type " + values.get(ctx.inst()));
        numErr++;
    }

}
