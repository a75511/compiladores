// Generated from C:/Users/fpgue/Desktop/Trabalhos Uni/Compiladores2324/UNI/SolCompiler/Sol.g4 by ANTLR 4.13.1
package Sol;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SolParser}.
 */
public interface SolListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SolParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(SolParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link SolParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(SolParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link SolParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(SolParser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SolParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(SolParser.FunctionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Print}
	 * labeled alternative in {@link SolParser#line}.
	 * @param ctx the parse tree
	 */
	void enterPrint(SolParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Print}
	 * labeled alternative in {@link SolParser#line}.
	 * @param ctx the parse tree
	 */
	void exitPrint(SolParser.PrintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionCallExpression}
	 * labeled alternative in {@link SolParser#line}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCallExpression(SolParser.FunctionCallExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionCallExpression}
	 * labeled alternative in {@link SolParser#line}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCallExpression(SolParser.FunctionCallExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Instruction}
	 * labeled alternative in {@link SolParser#line}.
	 * @param ctx the parse tree
	 */
	void enterInstruction(SolParser.InstructionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Instruction}
	 * labeled alternative in {@link SolParser#line}.
	 * @param ctx the parse tree
	 */
	void exitInstruction(SolParser.InstructionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BlockCode}
	 * labeled alternative in {@link SolParser#line}.
	 * @param ctx the parse tree
	 */
	void enterBlockCode(SolParser.BlockCodeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BlockCode}
	 * labeled alternative in {@link SolParser#line}.
	 * @param ctx the parse tree
	 */
	void exitBlockCode(SolParser.BlockCodeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code WhileCycle}
	 * labeled alternative in {@link SolParser#line}.
	 * @param ctx the parse tree
	 */
	void enterWhileCycle(SolParser.WhileCycleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code WhileCycle}
	 * labeled alternative in {@link SolParser#line}.
	 * @param ctx the parse tree
	 */
	void exitWhileCycle(SolParser.WhileCycleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ForCycle}
	 * labeled alternative in {@link SolParser#line}.
	 * @param ctx the parse tree
	 */
	void enterForCycle(SolParser.ForCycleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ForCycle}
	 * labeled alternative in {@link SolParser#line}.
	 * @param ctx the parse tree
	 */
	void exitForCycle(SolParser.ForCycleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IfStatement}
	 * labeled alternative in {@link SolParser#line}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(SolParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IfStatement}
	 * labeled alternative in {@link SolParser#line}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(SolParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BreakStatement}
	 * labeled alternative in {@link SolParser#line}.
	 * @param ctx the parse tree
	 */
	void enterBreakStatement(SolParser.BreakStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BreakStatement}
	 * labeled alternative in {@link SolParser#line}.
	 * @param ctx the parse tree
	 */
	void exitBreakStatement(SolParser.BreakStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Empty}
	 * labeled alternative in {@link SolParser#line}.
	 * @param ctx the parse tree
	 */
	void enterEmpty(SolParser.EmptyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Empty}
	 * labeled alternative in {@link SolParser#line}.
	 * @param ctx the parse tree
	 */
	void exitEmpty(SolParser.EmptyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Return}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 */
	void enterReturn(SolParser.ReturnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Return}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 */
	void exitReturn(SolParser.ReturnContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Or}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 */
	void enterOr(SolParser.OrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Or}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 */
	void exitOr(SolParser.OrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 */
	void enterAddSub(SolParser.AddSubContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 */
	void exitAddSub(SolParser.AddSubContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Unary}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 */
	void enterUnary(SolParser.UnaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Unary}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 */
	void exitUnary(SolParser.UnaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MultDiv}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 */
	void enterMultDiv(SolParser.MultDivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MultDiv}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 */
	void exitMultDiv(SolParser.MultDivContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Equal}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 */
	void enterEqual(SolParser.EqualContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Equal}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 */
	void exitEqual(SolParser.EqualContext ctx);
	/**
	 * Enter a parse tree produced by the {@code And}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 */
	void enterAnd(SolParser.AndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code And}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 */
	void exitAnd(SolParser.AndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Literal}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(SolParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Literal}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(SolParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Rel}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 */
	void enterRel(SolParser.RelContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Rel}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 */
	void exitRel(SolParser.RelContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Assign}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 */
	void enterAssign(SolParser.AssignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Assign}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 */
	void exitAssign(SolParser.AssignContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Id}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 */
	void enterId(SolParser.IdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Id}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 */
	void exitId(SolParser.IdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionCall}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(SolParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionCall}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(SolParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Paren}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 */
	void enterParen(SolParser.ParenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Paren}
	 * labeled alternative in {@link SolParser#inst}.
	 * @param ctx the parse tree
	 */
	void exitParen(SolParser.ParenContext ctx);
	/**
	 * Enter a parse tree produced by {@link SolParser#assignInst}.
	 * @param ctx the parse tree
	 */
	void enterAssignInst(SolParser.AssignInstContext ctx);
	/**
	 * Exit a parse tree produced by {@link SolParser#assignInst}.
	 * @param ctx the parse tree
	 */
	void exitAssignInst(SolParser.AssignInstContext ctx);
	/**
	 * Enter a parse tree produced by {@link SolParser#var}.
	 * @param ctx the parse tree
	 */
	void enterVar(SolParser.VarContext ctx);
	/**
	 * Exit a parse tree produced by {@link SolParser#var}.
	 * @param ctx the parse tree
	 */
	void exitVar(SolParser.VarContext ctx);
	/**
	 * Enter a parse tree produced by {@link SolParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(SolParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link SolParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(SolParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link SolParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(SolParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SolParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(SolParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SolParser#typeFunction}.
	 * @param ctx the parse tree
	 */
	void enterTypeFunction(SolParser.TypeFunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SolParser#typeFunction}.
	 * @param ctx the parse tree
	 */
	void exitTypeFunction(SolParser.TypeFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SolParser#while}.
	 * @param ctx the parse tree
	 */
	void enterWhile(SolParser.WhileContext ctx);
	/**
	 * Exit a parse tree produced by {@link SolParser#while}.
	 * @param ctx the parse tree
	 */
	void exitWhile(SolParser.WhileContext ctx);
	/**
	 * Enter a parse tree produced by {@link SolParser#for}.
	 * @param ctx the parse tree
	 */
	void enterFor(SolParser.ForContext ctx);
	/**
	 * Exit a parse tree produced by {@link SolParser#for}.
	 * @param ctx the parse tree
	 */
	void exitFor(SolParser.ForContext ctx);
	/**
	 * Enter a parse tree produced by {@link SolParser#if}.
	 * @param ctx the parse tree
	 */
	void enterIf(SolParser.IfContext ctx);
	/**
	 * Exit a parse tree produced by {@link SolParser#if}.
	 * @param ctx the parse tree
	 */
	void exitIf(SolParser.IfContext ctx);
}