// Generated from I:/pp/opensource/max/src/main/java/com/dafei1288/max/express\MaxExpr.g4 by ANTLR 4.7
package com.dafei1288.max.express;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MaxExprParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MaxExprVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MaxExprParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(MaxExprParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code printLogicExpr}
	 * labeled alternative in {@link MaxExprParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintLogicExpr(MaxExprParser.PrintLogicExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code printExpr}
	 * labeled alternative in {@link MaxExprParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintExpr(MaxExprParser.PrintExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignLogic}
	 * labeled alternative in {@link MaxExprParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignLogic(MaxExprParser.AssignLogicContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignItLogic}
	 * labeled alternative in {@link MaxExprParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignItLogic(MaxExprParser.AssignItLogicContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assign}
	 * labeled alternative in {@link MaxExprParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign(MaxExprParser.AssignContext ctx);
	/**
	 * Visit a parse tree produced by the {@code itSelf}
	 * labeled alternative in {@link MaxExprParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitItSelf(MaxExprParser.ItSelfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code blank}
	 * labeled alternative in {@link MaxExprParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlank(MaxExprParser.BlankContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link MaxExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParens(MaxExprParser.ParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link MaxExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDiv(MaxExprParser.MulDivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link MaxExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSub(MaxExprParser.AddSubContext ctx);
	/**
	 * Visit a parse tree produced by the {@code it}
	 * labeled alternative in {@link MaxExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIt(MaxExprParser.ItContext ctx);
	/**
	 * Visit a parse tree produced by the {@code id}
	 * labeled alternative in {@link MaxExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(MaxExprParser.IdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code int}
	 * labeled alternative in {@link MaxExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt(MaxExprParser.IntContext ctx);
	/**
	 * Visit a parse tree produced by the {@code itExpr}
	 * labeled alternative in {@link MaxExprParser#logicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitItExpr(MaxExprParser.ItExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assIt}
	 * labeled alternative in {@link MaxExprParser#logicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssIt(MaxExprParser.AssItContext ctx);
	/**
	 * Visit a parse tree produced by the {@code topLevelLogic}
	 * labeled alternative in {@link MaxExprParser#logicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTopLevelLogic(MaxExprParser.TopLevelLogicContext ctx);
	/**
	 * Visit a parse tree produced by the {@code true}
	 * labeled alternative in {@link MaxExprParser#logicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrue(MaxExprParser.TrueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code false}
	 * labeled alternative in {@link MaxExprParser#logicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalse(MaxExprParser.FalseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicexpress}
	 * labeled alternative in {@link MaxExprParser#logicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicexpress(MaxExprParser.LogicexpressContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parentsLogic}
	 * labeled alternative in {@link MaxExprParser#logicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParentsLogic(MaxExprParser.ParentsLogicContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicnumber}
	 * labeled alternative in {@link MaxExprParser#logicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicnumber(MaxExprParser.LogicnumberContext ctx);
}