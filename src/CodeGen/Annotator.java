package CodeGen; /***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
 ***/


import Sol.*;
import SymbolTable.FunctionSymbol;
import SymbolTable.Scope;
import SymbolTable.Symbol;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.*;

import java.util.*;

public class Annotator extends SolBaseVisitor<Void> {
    ParseTreeProperty<Type> values = new ParseTreeProperty<Type>();
    Map<String, Object> vars = new HashMap<>();
    Scope currentScope;
    SemanticErrors sErr = new SemanticErrors(values);
    Boolean LineError = false;
    Type Return = Type.VOID;
    Boolean hasReturn = false;
    int numIfLines = 0;
    int numReturnIf = 0;

    public Annotator(Scope scope) {
        super();
        this.currentScope = scope;
    }
    @Override public Void visitProg(SolParser.ProgContext ctx) {
        visitChildren(ctx);
        int nErr = this.sErr.getNumErr();
        //if(nErr > 0) {
          //  new ErrorHandler(nErr + " errors found!");
        //}
        return null;
    }

    @Override public Void visitFunctionCallExpression(SolParser.FunctionCallExpressionContext ctx) {
        visitChildren(ctx);
        return null;
    }

    @Override public Void visitFunction(SolParser.FunctionContext ctx) {
        Scope Global = currentScope;
        visitTypeFunction(ctx.typeFunction());
        for (int i = 0; i < ctx.type().size(); i++) {
            visitType(ctx.type(i));
        }
        for (Scope child: currentScope.getChildScopes()) {
            if (child.getName().equals(ctx.ID(0).getText())) {
                currentScope = child;
            }
        }
        visitBlock(ctx.block());
        if (!hasReturn && Return != Type.VOID)
            sErr.TesteErro("Function " + ctx.ID(0).getText() + " has no return statement");
        currentScope = Global;
        hasReturn = false;
        return null;
    }

    @Override public Void visitReturn(SolParser.ReturnContext ctx) {
        visitChildren(ctx);
        hasReturn = true;
        if (Return == Type.REAL && values.get(ctx.inst()) == Type.INT) {
            values.put(ctx, Type.REAL);
            return null;
        }
        if (Return != values.get(ctx.inst())) {
            sErr.TesteErro("Return type does not match function type");
            //LineError = true;
        }
        ParserRuleContext c = ctx.getParent();
        while (true){
            //A linha de baixo tá mal
            if (c instanceof SolParser.FunctionContext)
                break;
            if (c instanceof SolParser.IfStatementContext){
                numReturnIf++;
                break;
            }
            c = c.getParent();
        }
        values.put(ctx, Return);
        return null;
    }

    @Override public Void visitFunctionCall(SolParser.FunctionCallContext ctx) {
        visitChildren(ctx);
        Type t = null;
        if (currentScope.resolve(ctx.ID().getText()) != null){
            t = currentScope.resolve(ctx.ID().getText()).getType();
            values.put(ctx, t);
        }else
            sErr.TesteErro("Function " + ctx.ID().getText() + " not found");
        return null;
    }


    @Override public Void visitVar(SolParser.VarContext ctx) {
        LineError = false;
        visitChildren(ctx);
        Type type = values.get(ctx.type());
        for (SolParser.AssignInstContext assignCtx: ctx.assignInst() ) {
            Type instType = values.get(assignCtx);
            if (instType == null)
                continue;
            if (type == Type.REAL && instType == Type.INT) {
                continue;
            }
            //if (type == Type.REAL && instType == Type.INT){
                //vars.put(assignCtx.ID().getText(), Type.REAL);
                //currentScope.define(new SymbolTable.VariableSymbol(assignCtx.ID().getSymbol(), Type.REAL));
                //continue;
            //}
            if (instType != type) {
                sErr.VarErr(ctx, instType);
                //LineError = true;
                break;
            }
        }
        return null;
    }

    @Override public Void visitId(SolParser.IdContext ctx) {
        if (LineError) return null;
       /* if (!vars.containsKey(ctx.ID().getText())) {
            sErr.IDErr(ctx);
            LineError = true;
        }
        else
            values.put(ctx, (Type) vars.get(ctx.ID().getText()));*/
        Symbol currentFunction = currentScope.resolve(currentScope.getName());
        if (currentFunction != null && currentFunction instanceof FunctionSymbol) {
            FunctionSymbol fs = (FunctionSymbol) currentFunction;
            for (Symbol s : fs.get_arguments()) {
                if (s.lexeme().equals(ctx.ID().getText())) {
                    values.put(ctx, s.getType());
                    return null;
                }
            }
        }
        if (currentScope.resolve(ctx.ID().getText()) == null) {
            sErr.IDErr(ctx);
            //LineError = true;
        } else if(currentScope.resolve(ctx.ID().getText()) instanceof FunctionSymbol){
            sErr.TesteErro(ctx.ID().getText() + " é uma função");
        }else {
            Symbol s = currentScope.resolve(ctx.ID().getText());
            values.put(ctx, s.getType());
        }
        return null;
    }

    @Override public Void visitAssign(SolParser.AssignContext ctx) {
        if (LineError) return null;
        visitChildren(ctx);
        if(ctx.inst() instanceof SolParser.FunctionCallContext)
            return null;
        Type type = values.get(ctx.inst());
        Symbol currentFunction = currentScope.resolve(currentScope.getName());
        if (currentFunction != null && currentFunction instanceof FunctionSymbol) {
            FunctionSymbol fs = (FunctionSymbol) currentFunction;
            for (Symbol s : fs.get_arguments()) {
                if (s.lexeme().equals(ctx.ID().getText())) {
                    if (s.getType() == Type.REAL && type == Type.INT) {
                        values.put(ctx, Type.REAL);
                    } else if (type != s.getType()) {
                        sErr.VarErr(ctx, type);
                        //LineError = true;
                    } else
                        values.put(ctx, s.getType());
                    return null;
                }
            }
        }
        //if (!currentScope.contains(ctx.ID().getText())) {
        if (currentScope.resolve(ctx.ID().getText()) == null) {
            sErr.IDErr(ctx);
            //LineError = true;
        } else {
            Symbol s = currentScope.resolve(ctx.ID().getText());
            Type idType = s.getType();
            if (idType == Type.REAL && type == Type.INT) {
                //currentScope.define(new SymbolTable.VariableSymbol(ctx.ID().getSymbol(), Type.REAL));
                values.put(ctx, Type.REAL);
            } else if (type != idType) {
                sErr.VarErr(ctx, type);
                //LineError = true;
            } else
                //currentScope.define(new SymbolTable.VariableSymbol(ctx.ID().getSymbol(), type));
                values.put(ctx, type);
        }
        /*Type idType = (Type) vars.get(ctx.ID().getText());
        if (!vars.containsKey(ctx.ID().getText())){
            sErr.IDErr(ctx);
            LineError = true;
        } else
            values.put(ctx.ID(), idType);
        if (idType == Type.REAL && type == Type.INT) {
            values.put(ctx, Type.REAL);
        }else if (type != idType){
            sErr.VarErr(ctx, type);
            LineError = true;
        } else
            values.put(ctx, type);*/
        return null;
    }

    @Override public Void visitAssignInst(SolParser.AssignInstContext ctx) {
        if (LineError) return null;
        Type parentType = values.get(ctx.getParent().getChild(0));
        visitChildren(ctx);
        Type type = values.get(ctx.inst());
        values.put(ctx, type);
        //if (vars.containsKey(ctx.ID().getText())){
            //sErr.AssingErr(ctx);
        //}
        if (currentScope.resolve(ctx.ID().getText()) != null) {
            sErr.AssingErr(ctx);
            return null;
        }

        if (parentType == Type.REAL && type == Type.INT){
            //vars.put(ctx.ID().getText(), Type.REAL);
            currentScope.define(new SymbolTable.VariableSymbol(ctx.ID().getSymbol(), Type.REAL));
            return null;
        }
        currentScope.define(new SymbolTable.VariableSymbol(ctx.ID().getSymbol(), parentType));
        //vars.put(ctx.ID().getText(), values.get(ctx.getParent().getChild(0)));
        return null;
    }


    @Override public Void visitType(SolParser.TypeContext ctx) {
        if (LineError) return null;
        switch (ctx.op.getType()) {
            case SolParser.TYPEINT:
                values.put(ctx, Type.INT);
                break;
            case SolParser.TYPEDOUBLE:
                values.put(ctx, Type.REAL);
                break;
            case SolParser.TYPESTRING:
                values.put(ctx, Type.STRING);
                break;
            case SolParser.TYPEBOOL:
                values.put(ctx, Type.BOOL);
                break;
        }
        return null;
    }

    @Override public Void visitTypeFunction(SolParser.TypeFunctionContext ctx) {
        if (LineError) return null;
        switch (ctx.op.getType()) {
            case SolParser.TYPEINT:
                values.put(ctx, Type.INT);
                break;
            case SolParser.TYPEDOUBLE:
                values.put(ctx, Type.REAL);
                break;
            case SolParser.TYPESTRING:
                values.put(ctx, Type.STRING);
                break;
            case SolParser.TYPEBOOL:
                values.put(ctx, Type.BOOL);
                break;
            case SolParser.TYPEVOID:
                values.put(ctx, Type.VOID);
                break;
        }
        Return = values.get(ctx);
        return null;
    }

    @Override public Void visitInstruction(SolParser.InstructionContext ctx) {
        LineError = false;
        visitChildren(ctx);
        return null;
    }


    @Override public Void visitPrint(SolParser.PrintContext ctx) {
        LineError = false;
        visitChildren(ctx);
        Type type = values.get(ctx.inst());
        values.put(ctx, type);
        return null;
    }

    @Override public Void visitBlockCode(SolParser.BlockCodeContext ctx) {
        LineError = false;
        Scope Global = currentScope;
        if (ctx.getParent() instanceof SolParser.BlockContext){
            currentScope = new Scope(currentScope, "Block" + Global.getName() + Global.getChildScopes().size());
        }
        visitChildren(ctx);
        currentScope = Global;
        return null;
    }

    @Override public Void visitWhile(SolParser.WhileContext ctx) {
        LineError = false;
        visit(ctx.getChild(1));
        Type type = values.get(ctx.getChild(1));
        if (type != Type.BOOL) {
            sErr.WhileErr(ctx);
        }
        visit(ctx.getChild(3));
        return null;
    }

    @Override public Void visitFor(SolParser.ForContext ctx) {
        LineError = false;
        visitChildren(ctx);
        Type type1 = values.get(ctx.getChild(3));
        if (currentScope.resolve(ctx.getChild(1).getText()) == null) {
            sErr.IDErr(ctx);
            LineError = true;
        }
        //if(!vars.containsKey(ctx.getChild(1).getText())) {
            //sErr.IDErr(ctx);
          //  LineError = true;
        //}
        else {
           // Type type2 = (Type) vars.get(ctx.getChild(1).getText());
            Type type2 = currentScope.resolve(ctx.getChild(1).getText()).getType();
            if (type1 != type2) {
                sErr.VarErr(ctx, type1);
                LineError = true;
            } else if (type1 != Type.INT) {
                sErr.ForErr(ctx);
                LineError = true;
            }
        }
        Type type3 = values.get(ctx.getChild(5));
        if ( type3 != Type.INT)
            sErr.ForErr(ctx);
        return null;
    }

    @Override public Void visitIf(SolParser.IfContext ctx) {
        LineError = false;
        numIfLines = 0;
        numReturnIf = 0;
        visitChildren(ctx);
        numIfLines = ctx.line().size();
        if (numIfLines != 2)
            hasReturn = false;
        else if (numReturnIf == 2)
            hasReturn = true;
        else hasReturn = false;
        Type type = values.get(ctx.getChild(1));
        if (type != Type.BOOL) {
            sErr.IfErr(ctx);
            LineError = true;
        }
        return null;
    }

    //Check if it is inside of while or for loop
    @Override public Void visitBreakStatement(SolParser.BreakStatementContext ctx) {
        LineError = false;
        ParserRuleContext prog = ctx.getParent();
        while (true) {
            if (prog instanceof SolParser.WhileCycleContext || prog instanceof SolParser.ForCycleContext)
                break;
            if (prog instanceof SolParser.ProgContext){
                sErr.BreakErr(ctx);
                LineError = true;
                break;
            }
            prog = prog.getParent();
        }
        return null;
    }


    @Override public Void visitOr(SolParser.OrContext ctx) {
        visitChildren(ctx);
        if (LineError) return null;
        Type type1 = values.get(ctx.inst(0));
        Type type2 = values.get(ctx.inst(1));
        Type finalType = TypeChecker.BinaryOperationCheck(type1, type2);
        if(finalType == Type.ERRO) {
            sErr.GenericErr(ctx);
            LineError = true;
        }
        values.put(ctx, finalType);
        return null;
    }

    @Override public Void visitAddSub(SolParser.AddSubContext ctx) {
        visitChildren(ctx);
        if (LineError) return null;
        Type type1 = values.get(ctx.inst(0));
        Type type2 = values.get(ctx.inst(1));
        Type finalType = Type.ERRO;

        if (ctx.op.getType() == SolParser.ADD)
            finalType = TypeChecker.addCheck(type1, type2);
        else if (ctx.op.getType() == SolParser.SUB)
            finalType = TypeChecker.genericOperationCheck(type1, type2);
        if(finalType == Type.ERRO) {
            sErr.GenericErr(ctx);
            LineError = true;
        }
        values.put(ctx, finalType);
        return null;
    }

    @Override public Void visitEqual(SolParser.EqualContext ctx) {
        visitChildren(ctx);
        if (LineError) return null;
        Type type1 = values.get(ctx.inst(0));
        Type type2 = values.get(ctx.inst(1));
        Type finalType = TypeChecker.equalCheck(type1, type2);
        if(finalType == Type.ERRO) {
            sErr.GenericErr(ctx);
            LineError = true;
        }
        values.put(ctx, finalType);
        return null;
    }

    @Override public Void visitAnd(SolParser.AndContext ctx) {
        visitChildren(ctx);
        if (LineError) return null;
        Type type1 = values.get(ctx.inst(0));
        Type type2 = values.get(ctx.inst(1));
        Type finalType = TypeChecker.BinaryOperationCheck(type1, type2);
        if(finalType == Type.ERRO) {
            sErr.GenericErr(ctx);
            LineError = true;
        }
        values.put(ctx, finalType);
        return null;
    }

    @Override public Void visitLiteral(SolParser.LiteralContext ctx) {
        if (LineError) return null;
        switch (ctx.op.getType()) {
            case SolParser.INT:
                values.put(ctx, Type.INT);
                break;
            case SolParser.DOUBLE:
                values.put(ctx, Type.REAL);
                break;
            case SolParser.STRING:
                values.put(ctx, Type.STRING);
                break;
            case SolParser.TRUE, SolParser.FALSE:
                values.put(ctx, Type.BOOL);
                break;
        }
        return null;
    }

    @Override public Void visitRel(SolParser.RelContext ctx) {
        visitChildren(ctx);
        if (LineError) return null;
        Type type1 = values.get(ctx.inst(0));
        Type type2 = values.get(ctx.inst(1));
        Type finalType = TypeChecker.RelOpCheck(type1, type2);
        if(finalType == Type.ERRO) {
            sErr.GenericErr(ctx);
            LineError = true;
        }
        values.put(ctx, finalType);
        return null;
    }

    @Override public Void visitUnary(SolParser.UnaryContext ctx) {
        visitChildren(ctx);
        if (LineError) return null;
        Type type = values.get(ctx.inst());
        Type finalType = Type.ERRO;
        if (ctx.op.getType() == SolParser.SUB)
            finalType = TypeChecker.UnaryCheck(type);
        else if (ctx.op.getType() == SolParser.NOT)
            finalType = TypeChecker.NotCheck(type);
        if(finalType == Type.ERRO) {
            sErr.unaryOpErr(ctx);
            LineError = true;
        }
            values.put(ctx, finalType);
        return null;
    }

    @Override public Void visitParen(SolParser.ParenContext ctx) {
        visitChildren(ctx);
        if (LineError) return null;
        Type type = values.get(ctx.inst());
        values.put(ctx, type);
        return null;
    }

    @Override public Void visitMultDiv(SolParser.MultDivContext ctx) {
        visitChildren(ctx);
        if (LineError) return null;
        Type type1 = values.get(ctx.inst(0));
        Type type2 = values.get(ctx.inst(1));
        Type finalType;
        if (ctx.op.getType() == SolParser.MOD)
            finalType = TypeChecker.modCheck(type1, type2);
        else
            finalType = TypeChecker.genericOperationCheck(type1, type2);
        if(finalType == Type.ERRO) {
            sErr.GenericErr(ctx);
            LineError = true;
        }
        values.put(ctx, finalType);
        return null;
    }

    public ParseTreeProperty<Type> getValues() {
        return values;
    }

    public Map<String, Object> getVars() {
        return vars;
    }

    public Scope getCurrentScope(){
        return currentScope;
    }
}
