// Generated from C:/Users/fpgue/Desktop/Trabalhos Uni/Compiladores2324/UNI/SolCompiler/Sol.g4 by ANTLR 4.13.1
package Sol;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SolParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SolVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SolParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(SolParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link SolParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(SolParser.FunctionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Print}
	 * labeled alternative in {@link SolParser#line}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint(SolParser.PrintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionCallExpression}
	 * labeled alternative in {@link SolParser#line}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCallExpression(SolParser.FunctionCallExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Instruction}
	 * labeled alternative in {@link SolParser#line}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstruction(SolParser.InstructionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BlockCode}
	 * labeled alternative in {@link SolParser#line}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockCode(SolParser.BlockCodeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code WhileCycle}
	 * labeled alternative in {@link SolParser#line}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileCycle(SolParser.WhileCycleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ForCycle}
	 * labeled alternative in {@link SolParser#line}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForCycle(SolParser.ForCycleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IfStatement}
	 * labeled alternative in {@link SolParser#line}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(SolParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BreakStatement}
	 * labeled alternative in {@link SolParser#line}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStatement(SolParser.BreakStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Empty}
	 * labeled alternative in {@link SolParser#line}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmpty(SolParser.EmptyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Return}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn(SolParser.ReturnContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Or}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOr(SolParser.OrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSub(SolParser.AddSubContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Unary}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary(SolParser.UnaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MultDiv}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultDiv(SolParser.MultDivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Equal}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqual(SolParser.EqualContext ctx);
	/**
	 * Visit a parse tree produced by the {@code And}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd(SolParser.AndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Literal}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(SolParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Rel}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRel(SolParser.RelContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Assign}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign(SolParser.AssignContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Id}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(SolParser.IdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionCall}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(SolParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Paren}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParen(SolParser.ParenContext ctx);
	/**
	 * Visit a parse tree produced by {@link SolParser#assignInst}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignInst(SolParser.AssignInstContext ctx);
	/**
	 * Visit a parse tree produced by {@link SolParser#var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(SolParser.VarContext ctx);
	/**
	 * Visit a parse tree produced by {@link SolParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(SolParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link SolParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(SolParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SolParser#typeFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeFunction(SolParser.TypeFunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SolParser#while}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile(SolParser.WhileContext ctx);
	/**
	 * Visit a parse tree produced by {@link SolParser#for}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor(SolParser.ForContext ctx);
	/**
	 * Visit a parse tree produced by {@link SolParser#if}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf(SolParser.IfContext ctx);
}