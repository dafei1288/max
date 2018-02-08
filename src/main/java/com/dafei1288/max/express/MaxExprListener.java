// Generated from I:/pp/opensource/max/src/main/java/com/dafei1288/max/express\MaxExpr.g4 by ANTLR 4.7
package com.dafei1288.max.express;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MaxExprParser}.
 */
public interface MaxExprListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MaxExprParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(MaxExprParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link MaxExprParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(MaxExprParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code printLogicExpr}
	 * labeled alternative in {@link MaxExprParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterPrintLogicExpr(MaxExprParser.PrintLogicExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code printLogicExpr}
	 * labeled alternative in {@link MaxExprParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitPrintLogicExpr(MaxExprParser.PrintLogicExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code printExpr}
	 * labeled alternative in {@link MaxExprParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterPrintExpr(MaxExprParser.PrintExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code printExpr}
	 * labeled alternative in {@link MaxExprParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitPrintExpr(MaxExprParser.PrintExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignLogic}
	 * labeled alternative in {@link MaxExprParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterAssignLogic(MaxExprParser.AssignLogicContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignLogic}
	 * labeled alternative in {@link MaxExprParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitAssignLogic(MaxExprParser.AssignLogicContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignItLogic}
	 * labeled alternative in {@link MaxExprParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterAssignItLogic(MaxExprParser.AssignItLogicContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignItLogic}
	 * labeled alternative in {@link MaxExprParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitAssignItLogic(MaxExprParser.AssignItLogicContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assign}
	 * labeled alternative in {@link MaxExprParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterAssign(MaxExprParser.AssignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assign}
	 * labeled alternative in {@link MaxExprParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitAssign(MaxExprParser.AssignContext ctx);
	/**
	 * Enter a parse tree produced by the {@code itSelf}
	 * labeled alternative in {@link MaxExprParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterItSelf(MaxExprParser.ItSelfContext ctx);
	/**
	 * Exit a parse tree produced by the {@code itSelf}
	 * labeled alternative in {@link MaxExprParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitItSelf(MaxExprParser.ItSelfContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blank}
	 * labeled alternative in {@link MaxExprParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterBlank(MaxExprParser.BlankContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blank}
	 * labeled alternative in {@link MaxExprParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitBlank(MaxExprParser.BlankContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parens}
	 * labeled alternative in {@link MaxExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParens(MaxExprParser.ParensContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link MaxExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParens(MaxExprParser.ParensContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link MaxExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMulDiv(MaxExprParser.MulDivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link MaxExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMulDiv(MaxExprParser.MulDivContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link MaxExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAddSub(MaxExprParser.AddSubContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link MaxExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAddSub(MaxExprParser.AddSubContext ctx);
	/**
	 * Enter a parse tree produced by the {@code it}
	 * labeled alternative in {@link MaxExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIt(MaxExprParser.ItContext ctx);
	/**
	 * Exit a parse tree produced by the {@code it}
	 * labeled alternative in {@link MaxExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIt(MaxExprParser.ItContext ctx);
	/**
	 * Enter a parse tree produced by the {@code id}
	 * labeled alternative in {@link MaxExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterId(MaxExprParser.IdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code id}
	 * labeled alternative in {@link MaxExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitId(MaxExprParser.IdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code int}
	 * labeled alternative in {@link MaxExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterInt(MaxExprParser.IntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code int}
	 * labeled alternative in {@link MaxExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitInt(MaxExprParser.IntContext ctx);
	/**
	 * Enter a parse tree produced by the {@code itExpr}
	 * labeled alternative in {@link MaxExprParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void enterItExpr(MaxExprParser.ItExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code itExpr}
	 * labeled alternative in {@link MaxExprParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void exitItExpr(MaxExprParser.ItExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code topLevelLogic}
	 * labeled alternative in {@link MaxExprParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void enterTopLevelLogic(MaxExprParser.TopLevelLogicContext ctx);
	/**
	 * Exit a parse tree produced by the {@code topLevelLogic}
	 * labeled alternative in {@link MaxExprParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void exitTopLevelLogic(MaxExprParser.TopLevelLogicContext ctx);
	/**
	 * Enter a parse tree produced by the {@code true}
	 * labeled alternative in {@link MaxExprParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void enterTrue(MaxExprParser.TrueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code true}
	 * labeled alternative in {@link MaxExprParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void exitTrue(MaxExprParser.TrueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code false}
	 * labeled alternative in {@link MaxExprParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void enterFalse(MaxExprParser.FalseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code false}
	 * labeled alternative in {@link MaxExprParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void exitFalse(MaxExprParser.FalseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicexpress}
	 * labeled alternative in {@link MaxExprParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void enterLogicexpress(MaxExprParser.LogicexpressContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicexpress}
	 * labeled alternative in {@link MaxExprParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void exitLogicexpress(MaxExprParser.LogicexpressContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parentsLogic}
	 * labeled alternative in {@link MaxExprParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void enterParentsLogic(MaxExprParser.ParentsLogicContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parentsLogic}
	 * labeled alternative in {@link MaxExprParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void exitParentsLogic(MaxExprParser.ParentsLogicContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicnumber}
	 * labeled alternative in {@link MaxExprParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void enterLogicnumber(MaxExprParser.LogicnumberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicnumber}
	 * labeled alternative in {@link MaxExprParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void exitLogicnumber(MaxExprParser.LogicnumberContext ctx);
}