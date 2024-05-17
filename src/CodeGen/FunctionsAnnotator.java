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

public class FunctionsAnnotator extends SolBaseVisitor<Void> {
    ParseTreeProperty<Type> values = new ParseTreeProperty<Type>();
    Map<String, Object> vars = new HashMap<>();
    Scope currentScope = new Scope(null);
    SemanticErrors sErr = new SemanticErrors(values);
    Boolean LineError = false;
    Type Return = Type.VOID;
    Boolean hasReturn = false;
    int numIfLines = 0;
    int numReturnIf = 0;

    @Override public Void visitProg(SolParser.ProgContext ctx) {
        visitChildren(ctx);
        return null;
    }

    @Override public Void visitFunctionCallExpression(SolParser.FunctionCallExpressionContext ctx) {
        visitChildren(ctx);
        return null;
    }

    @Override public Void visitFunction(SolParser.FunctionContext ctx) {
        Scope Global = currentScope;
        Scope functionScope = new Scope(currentScope, ctx.ID(0).getText());
        visitTypeFunction(ctx.typeFunction());
        for (int i = 0; i < ctx.type().size(); i++) {
            visitType(ctx.type(i));
        }
        FunctionSymbol fs = new FunctionSymbol(ctx.ID(0).getSymbol(), values.get(ctx.typeFunction()));
        int size = ctx.ID().size();
        for (int i = 1; i < size; i++) {
            fs.add_argument(new SymbolTable.Symbol(ctx.ID(i).getSymbol(), values.get(ctx.type(i-1))));
        }
        currentScope.define(fs);
        currentScope = functionScope;
        for (Symbol a : fs.get_arguments())
            currentScope.define(a);
        visitBlock(ctx.block());
        currentScope = Global;
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

    public Scope getCurrentScope(){
        return currentScope;
    }
}
