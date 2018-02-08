// Generated from I:/pp/opensource/max/src/main/java/com/dafei1288/max/express\MaxExpr.g4 by ANTLR 4.7
package com.dafei1288.max.express;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MaxExprLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, MUL=4, DIV=5, ADD=6, SUB=7, GT=8, GTE=9, LT=10, 
		LTE=11, EQ=12, NEQ=13, OR=14, AND=15, NOT=16, IT=17, TRUE=18, FALSE=19, 
		ID=20, INT=21, NEWLINE=22, SEM=23, WS=24;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "MUL", "DIV", "ADD", "SUB", "GT", "GTE", "LT", 
		"LTE", "EQ", "NEQ", "OR", "AND", "NOT", "IT", "TRUE", "FALSE", "ID", "INT", 
		"NEWLINE", "SEM", "WS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'='", "'('", "')'", "'*'", "'/'", "'+'", "'-'", "'>'", "'>='", 
		"'<'", "'<='", "'=='", "'!='", "'||'", "'&&'", "'!'", "'it'", "'true'", 
		"'flase'", null, null, null, "';'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, "MUL", "DIV", "ADD", "SUB", "GT", "GTE", "LT", 
		"LTE", "EQ", "NEQ", "OR", "AND", "NOT", "IT", "TRUE", "FALSE", "ID", "INT", 
		"NEWLINE", "SEM", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public MaxExprLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "MaxExpr.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\32\177\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3"+
		"\n\3\n\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17"+
		"\3\20\3\20\3\20\3\21\3\21\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\25\6\25i\n\25\r\25\16\25j\3\26\6\26n\n\26"+
		"\r\26\16\26o\3\27\5\27s\n\27\3\27\3\27\3\30\3\30\3\31\6\31z\n\31\r\31"+
		"\16\31{\3\31\3\31\2\2\32\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f"+
		"\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\3"+
		"\2\5\4\2C\\c|\3\2\62;\4\2\13\13\"\"\2\u0082\2\3\3\2\2\2\2\5\3\2\2\2\2"+
		"\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2"+
		"\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2"+
		"\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2"+
		"\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\3\63\3\2\2"+
		"\2\5\65\3\2\2\2\7\67\3\2\2\2\t9\3\2\2\2\13;\3\2\2\2\r=\3\2\2\2\17?\3\2"+
		"\2\2\21A\3\2\2\2\23C\3\2\2\2\25F\3\2\2\2\27H\3\2\2\2\31K\3\2\2\2\33N\3"+
		"\2\2\2\35Q\3\2\2\2\37T\3\2\2\2!W\3\2\2\2#Y\3\2\2\2%\\\3\2\2\2\'a\3\2\2"+
		"\2)h\3\2\2\2+m\3\2\2\2-r\3\2\2\2/v\3\2\2\2\61y\3\2\2\2\63\64\7?\2\2\64"+
		"\4\3\2\2\2\65\66\7*\2\2\66\6\3\2\2\2\678\7+\2\28\b\3\2\2\29:\7,\2\2:\n"+
		"\3\2\2\2;<\7\61\2\2<\f\3\2\2\2=>\7-\2\2>\16\3\2\2\2?@\7/\2\2@\20\3\2\2"+
		"\2AB\7@\2\2B\22\3\2\2\2CD\7@\2\2DE\7?\2\2E\24\3\2\2\2FG\7>\2\2G\26\3\2"+
		"\2\2HI\7>\2\2IJ\7?\2\2J\30\3\2\2\2KL\7?\2\2LM\7?\2\2M\32\3\2\2\2NO\7#"+
		"\2\2OP\7?\2\2P\34\3\2\2\2QR\7~\2\2RS\7~\2\2S\36\3\2\2\2TU\7(\2\2UV\7("+
		"\2\2V \3\2\2\2WX\7#\2\2X\"\3\2\2\2YZ\7k\2\2Z[\7v\2\2[$\3\2\2\2\\]\7v\2"+
		"\2]^\7t\2\2^_\7w\2\2_`\7g\2\2`&\3\2\2\2ab\7h\2\2bc\7n\2\2cd\7c\2\2de\7"+
		"u\2\2ef\7g\2\2f(\3\2\2\2gi\t\2\2\2hg\3\2\2\2ij\3\2\2\2jh\3\2\2\2jk\3\2"+
		"\2\2k*\3\2\2\2ln\t\3\2\2ml\3\2\2\2no\3\2\2\2om\3\2\2\2op\3\2\2\2p,\3\2"+
		"\2\2qs\7\17\2\2rq\3\2\2\2rs\3\2\2\2st\3\2\2\2tu\7\f\2\2u.\3\2\2\2vw\7"+
		"=\2\2w\60\3\2\2\2xz\t\4\2\2yx\3\2\2\2z{\3\2\2\2{y\3\2\2\2{|\3\2\2\2|}"+
		"\3\2\2\2}~\b\31\2\2~\62\3\2\2\2\7\2jor{\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}