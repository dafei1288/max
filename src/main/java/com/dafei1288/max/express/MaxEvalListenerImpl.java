package com.dafei1288.max.express;


import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MaxEvalListenerImpl extends MaxExprBaseListener {
    private Stack<Integer> numStack = new Stack<>();
    private Queue<String> symbolQueue = new ConcurrentLinkedQueue<String>();
//    private Double it;
    public ThreadLocal<Double> tld = new ThreadLocal<>();

    @Override public void enterPrintLogicExpr(MaxExprParser.PrintLogicExprContext ctx) {
        super.enterPrintLogicExpr(ctx);
        System.out.println(" enterPrintLogicExpr = "+ctx.getText());

    }

    @Override public void enterItExpr(MaxExprParser.ItExprContext ctx) {
        super.enterItExpr(ctx);
        System.out.println(" enterItExpr = "+ctx.getText());
    }

    @Override
    public void enterLogicexpress(MaxExprParser.LogicexpressContext ctx) {
        super.enterLogicexpress(ctx);
    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        super.enterEveryRule(ctx);
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        super.visitTerminal(node);
        String text = node.getText();
//        System.out.println(text);
        if(text.equals("it")){
            System.out.println(tld.get());
        }
        switch(text){
            case ">":
        }

//        String text = node.getText();
//        if (isCalSymbol(text))
//            symbolQueue.add(text);
//        else
//            cal(text);
    }



}
