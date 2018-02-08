package com.dafei1288.max.express;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MaxEvalVisitor extends MaxExprBaseVisitor {
    Map<String, Integer> memory = new HashMap<String, Integer>();
    @Override
    public Integer visitAssign(MaxExprParser.AssignContext ctx) {
        String id = ctx.ID().getText();

        Integer value = Integer.valueOf(visit(ctx.expr()).toString());
        this.memory.put(id, value);
        return value;

    }

    @Override
    public Integer visitInt(MaxExprParser.IntContext ctx) {
        return Integer.valueOf(ctx.INT().getText());
    }

    @Override
    public Integer visitMulDiv(MaxExprParser.MulDivContext ctx) {
//        Integer left = visit(ctx.expr(0));
//        Integer right = visit(ctx.expr(1));
        Integer left = Integer.valueOf(visit(ctx.expr(0)).toString());
        Integer right = Integer.valueOf(visit(ctx.expr(1)).toString());
        if (ctx.op.getType() == MaxExprParser.MUL){
            return left * right;
        }else{
            return left / right;
        }

    }

    public static void main(String[] args) throws IOException {
//        String inputFile = "data.txt";
//        InputStream is = System.in;

//        if ( inputFile!=null ) is = new FileInputStream(inputFile);
        ANTLRInputStream input = new ANTLRInputStream(new StringReader("10 > 5"));

        MaxExprLexer lexer = new MaxExprLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MaxExprParser parser = new MaxExprParser(tokens);
        ParseTree tree = parser.prog(); // parse
        MaxEvalVisitor eval = new MaxEvalVisitor();
        Object res = eval.visit(tree);
        System.out.println(res);
    }
}
