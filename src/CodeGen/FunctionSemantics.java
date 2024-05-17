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

public class FunctionSemantics extends SolBaseVisitor<Void> {
    ParseTreeProperty<Type> values = new ParseTreeProperty<Type>();
    Scope currentScope;
    SemanticErrors sErr = new SemanticErrors(values);
    Boolean LineError = false;
    Boolean hasMain = false;

    public FunctionSemantics(Scope scope, ParseTreeProperty<Type> values) {
        super();
        this.currentScope = scope;
        this.values = values;
    }

    @Override public Void visitProg(SolParser.ProgContext ctx) {
        visitChildren(ctx);
        if(!currentScope.contains("main")){
            sErr.TesteErro("Main function not found");
            new ErrorHandler("Main function not found");
        }
        return null;
    }

    @Override public Void visitFunctionCallExpression(SolParser.FunctionCallExpressionContext ctx) {
        FunctionSymbol function;
        if (currentScope.resolve(ctx.ID().getText()) == null) {
            sErr.TesteErro("Function " + ctx.ID().getText() + " not found");
        } else {
            Symbol functionTemp = currentScope.resolve(ctx.ID().getText());
            if (!(functionTemp instanceof FunctionSymbol)) {
                sErr.TesteErro(ctx.ID().getText() + " is not a function");
                return null;
            } else
                function = (FunctionSymbol) functionTemp;
            if (function.get_arguments().size() != ctx.inst().size()) {
                sErr.TesteErro("Function " + ctx.ID().getText() + " has " + function.get_arguments().size() + " parameters");
            } else {
                for (int i = 0; i < ctx.inst().size(); i++) {
                    Type type = values.get(ctx.inst(i));
                    if (!type.equals(function.get_arguments().get(i).getType())) {
                        sErr.TesteErro("Function " + ctx.ID().getText() + " parameter " + i + " is of type " + function.get_arguments().get(i).getType());
                    }
                }
            }
            if (function.getType() != Type.VOID){
                sErr.TesteErro("Value of " + ctx.ID().getText() + " should be assigned to a variable");
            }
        }
        return null;
    }

    @Override public Void visitFunction(SolParser.FunctionContext ctx) {
        Scope GlobalScope = currentScope;
        for (Scope child: currentScope.getChildScopes()) {
            if (child.getName().equals(ctx.ID(0).getText())) {
                currentScope = child;
            }
        }
        visitChildren(ctx);
        currentScope = GlobalScope;
        return null;
    }

    @Override public Void visitReturn(SolParser.ReturnContext ctx) {
        visitChildren(ctx);
        return null;
    }

    @Override public Void visitFunctionCall(SolParser.FunctionCallContext ctx) {
        FunctionSymbol function;
        if (currentScope.resolve(ctx.ID().getText()) == null) {
            sErr.TesteErro("Function " + ctx.ID().getText() + " not found");
        } else {
            Symbol functionTemp = currentScope.resolve(ctx.ID().getText());
            if (!(functionTemp instanceof FunctionSymbol)) {
                sErr.TesteErro(ctx.ID().getText() + " is not a function");
                return null;
            } else
                function = (FunctionSymbol) functionTemp;
            if (function.get_arguments().size() != ctx.inst().size()) {
                sErr.TesteErro("Function " + ctx.ID().getText() + " has " + function.get_arguments().size() + " parameters");
            } else {
                for (int i = 0; i < ctx.inst().size(); i++) {
                    Type type = values.get(ctx.inst(i));
                    if (!type.equals(function.get_arguments().get(i).getType())) {
                        sErr.TesteErro("Function " + ctx.ID().getText() + " parameter " + i + " is of type " + function.get_arguments().get(i).getType());
                    }
                }
            }
            values.put(ctx, function.getType());
        }
        return null;
    }


    @Override public Void visitVar(SolParser.VarContext ctx) {
        visitChildren(ctx);
        return null;
    }

    @Override public Void visitId(SolParser.IdContext ctx) {
        return null;
    }

    @Override public Void visitAssign(SolParser.AssignContext ctx) {
        visitChildren(ctx);
        Symbol s = null;
        if(ctx.inst() instanceof SolParser.FunctionCallContext)
            s = currentScope.resolve(ctx.ID().getText());
        if (s == null) {
            return null;
        }
        else if(s.getType() == Type.REAL && values.get(ctx.inst()) == Type.INT){
            values.put(ctx, Type.REAL);
        }
        else if(s.getType() != values.get(ctx.inst())){
            sErr.TesteErro("Type mismatch");
        }
            return null;
    }

    @Override public Void visitAssignInst(SolParser.AssignInstContext ctx) {
        visitChildren(ctx);
        return null;
    }


    @Override public Void visitType(SolParser.TypeContext ctx) {
        visitChildren(ctx);
        return null;
    }

    @Override public Void visitTypeFunction(SolParser.TypeFunctionContext ctx) {
        visitChildren(ctx);
        return null;
    }

    @Override public Void visitInstruction(SolParser.InstructionContext ctx) {
        visitChildren(ctx);
        return null;
    }


    @Override public Void visitPrint(SolParser.PrintContext ctx) {
        visitChildren(ctx);
        return null;
    }

    @Override public Void visitBlockCode(SolParser.BlockCodeContext ctx) {
        visitChildren(ctx);
        return null;
    }

    @Override public Void visitWhile(SolParser.WhileContext ctx) {
        visitChildren(ctx);
        return null;
    }

    @Override public Void visitFor(SolParser.ForContext ctx) {
        visitChildren(ctx);
        return null;
    }

    @Override public Void visitIf(SolParser.IfContext ctx) {
        visitChildren(ctx);
        return null;
    }

    //Check if it is inside of while or for loop
    @Override public Void visitBreakStatement(SolParser.BreakStatementContext ctx) {
        visitChildren(ctx);
        return null;
    }


    @Override public Void visitOr(SolParser.OrContext ctx) {
        visitChildren(ctx);
        return null;
    }

    @Override public Void visitAddSub(SolParser.AddSubContext ctx) {
        visitChildren(ctx);
        return null;
    }

    @Override public Void visitEqual(SolParser.EqualContext ctx) {
        visitChildren(ctx);
        return null;
    }

    @Override public Void visitAnd(SolParser.AndContext ctx) {
        visitChildren(ctx);
        return null;
    }

    @Override public Void visitLiteral(SolParser.LiteralContext ctx) {
        visitChildren(ctx);
        return null;
    }

    @Override public Void visitRel(SolParser.RelContext ctx) {
        visitChildren(ctx);
        return null;
    }

    @Override public Void visitUnary(SolParser.UnaryContext ctx) {
        visitChildren(ctx);
        return null;
    }

    @Override public Void visitParen(SolParser.ParenContext ctx) {
        visitChildren(ctx);
        return null;
    }

    @Override public Void visitMultDiv(SolParser.MultDivContext ctx) {
        visitChildren(ctx);
        return null;
    }

    public ParseTreeProperty<Type> getValues() {
        return values;
    }
    public Scope getCurrentScope(){
        return currentScope;
    }
}
