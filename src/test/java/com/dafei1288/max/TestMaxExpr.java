package com.dafei1288.max;

import com.dafei1288.max.express.MaxEvalListenerImpl;
import com.dafei1288.max.express.MaxExprLexer;
import com.dafei1288.max.express.MaxExprParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

public class TestMaxExpr {

    @Test
    public void testWalker() throws IOException {
        ANTLRInputStream input = new ANTLRInputStream(new StringReader("(it >=5 ||  it < 10) && true"));
        MaxExprLexer lexer = new MaxExprLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MaxExprParser parser = new MaxExprParser(tokens);
        MaxEvalListenerImpl eval = new MaxEvalListenerImpl();

        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(eval, parser.prog());
    }

}
