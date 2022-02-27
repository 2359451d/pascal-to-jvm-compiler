// Generated from D:/Workspace/L4-Source-Repo/src/main/java/grammar\Pascal.g4 by ANTLR 4.9.2
package ast.visitor;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PascalParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WRITE=1, WRITELN=2, READ=3, READLN=4, AND=5, ARRAY=6, BEGIN=7, BOOLEAN=8, 
		CASE=9, CHAR=10, CHR=11, CONST=12, DIV=13, DO=14, DOWNTO=15, ELSE=16, 
		END=17, FILE=18, FOR=19, FUNCTION=20, GOTO=21, IF=22, IN=23, INTEGER=24, 
		LABEL=25, MOD=26, NIL=27, NOT=28, OF=29, OR=30, PACKED=31, PROCEDURE=32, 
		PROGRAM=33, REAL=34, RECORD=35, REPEAT=36, SET=37, THEN=38, TO=39, TYPE=40, 
		UNTIL=41, VAR=42, WHILE=43, WITH=44, PLUS=45, MINUS=46, STAR=47, SLASH=48, 
		ASSIGN=49, COMMA=50, SEMI=51, COLON=52, EQUAL=53, NOT_EQUAL=54, LT=55, 
		LE=56, GE=57, GT=58, LPAREN=59, RPAREN=60, LBRACK=61, LBRACK2=62, RBRACK=63, 
		RBRACK2=64, POINTER=65, AT=66, DOT=67, DOTDOT=68, LCURLY=69, RCURLY=70, 
		UNIT=71, INTERFACE=72, USES=73, STRING=74, IMPLEMENTATION=75, TRUE=76, 
		FALSE=77, FORWARD=78, WS=79, COMMENT_1=80, COMMENT_2=81, IDENT=82, STRING_LITERAL=83, 
		NUM_INT=84, NUM_REAL=85;
	public static final int
		RULE_program = 0, RULE_programHeading = 1, RULE_identifier = 2, RULE_block = 3, 
		RULE_usesUnitsPart = 4, RULE_labelDeclarationPart = 5, RULE_label = 6, 
		RULE_constantDefinitionPart = 7, RULE_constantDefinition = 8, RULE_constantChr = 9, 
		RULE_constant = 10, RULE_unsignedNumber = 11, RULE_sign = 12, RULE_bool_ = 13, 
		RULE_string = 14, RULE_typeDefinitionPart = 15, RULE_typeDefinition = 16, 
		RULE_functionType = 17, RULE_procedureType = 18, RULE_type_ = 19, RULE_simpleType = 20, 
		RULE_scalarType = 21, RULE_subrangeType = 22, RULE_typeIdentifier = 23, 
		RULE_structuredType = 24, RULE_unpackedStructuredType = 25, RULE_stringtype = 26, 
		RULE_arrayType = 27, RULE_typeList = 28, RULE_indexType = 29, RULE_componentType = 30, 
		RULE_recordType = 31, RULE_fieldList = 32, RULE_fixedPart = 33, RULE_recordSection = 34, 
		RULE_variantPart = 35, RULE_tag = 36, RULE_variant = 37, RULE_setType = 38, 
		RULE_baseType = 39, RULE_fileType = 40, RULE_pointerType = 41, RULE_variableDeclarationPart = 42, 
		RULE_variableDeclaration = 43, RULE_procedureAndFunctionDeclarationPart = 44, 
		RULE_procedureOrFunctionDeclaration = 45, RULE_directive = 46, RULE_procedureDeclaration = 47, 
		RULE_formalParameterList = 48, RULE_formalParameterSection = 49, RULE_functionHeading = 50, 
		RULE_procedureHeading = 51, RULE_parameterGroup = 52, RULE_identifierList = 53, 
		RULE_constList = 54, RULE_functionDeclaration = 55, RULE_resultType = 56, 
		RULE_statement = 57, RULE_unlabelledStatement = 58, RULE_simpleStatement = 59, 
		RULE_assignmentStatement = 60, RULE_variable = 61, RULE_variableHead = 62, 
		RULE_arrayScripting = 63, RULE_fieldDesignator = 64, RULE_expression = 65, 
		RULE_simpleExpression = 66, RULE_term = 67, RULE_signedFactor = 68, RULE_factor = 69, 
		RULE_unsignedConstant = 70, RULE_functionDesignator = 71, RULE_parameterList = 72, 
		RULE_set_ = 73, RULE_elementList = 74, RULE_element = 75, RULE_procedureStatement = 76, 
		RULE_writeProcedureStatement = 77, RULE_readProcedureStatement = 78, RULE_writeParameters = 79, 
		RULE_outputValue = 80, RULE_readParameters = 81, RULE_inputValue = 82, 
		RULE_actualParameter = 83, RULE_parameterwidth = 84, RULE_gotoStatement = 85, 
		RULE_emptyStatement = 86, RULE_empty_ = 87, RULE_structuredStatement = 88, 
		RULE_compoundStatement = 89, RULE_statements = 90, RULE_conditionalStatement = 91, 
		RULE_ifStatement = 92, RULE_caseStatement = 93, RULE_caseListElement = 94, 
		RULE_repetetiveStatement = 95, RULE_whileStatement = 96, RULE_repeatStatement = 97, 
		RULE_forStatement = 98, RULE_forList = 99, RULE_initialValue = 100, RULE_finalValue = 101, 
		RULE_withStatement = 102, RULE_recordVariableList = 103;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "programHeading", "identifier", "block", "usesUnitsPart", 
			"labelDeclarationPart", "label", "constantDefinitionPart", "constantDefinition", 
			"constantChr", "constant", "unsignedNumber", "sign", "bool_", "string", 
			"typeDefinitionPart", "typeDefinition", "functionType", "procedureType", 
			"type_", "simpleType", "scalarType", "subrangeType", "typeIdentifier", 
			"structuredType", "unpackedStructuredType", "stringtype", "arrayType", 
			"typeList", "indexType", "componentType", "recordType", "fieldList", 
			"fixedPart", "recordSection", "variantPart", "tag", "variant", "setType", 
			"baseType", "fileType", "pointerType", "variableDeclarationPart", "variableDeclaration", 
			"procedureAndFunctionDeclarationPart", "procedureOrFunctionDeclaration", 
			"directive", "procedureDeclaration", "formalParameterList", "formalParameterSection", 
			"functionHeading", "procedureHeading", "parameterGroup", "identifierList", 
			"constList", "functionDeclaration", "resultType", "statement", "unlabelledStatement", 
			"simpleStatement", "assignmentStatement", "variable", "variableHead", 
			"arrayScripting", "fieldDesignator", "expression", "simpleExpression", 
			"term", "signedFactor", "factor", "unsignedConstant", "functionDesignator", 
			"parameterList", "set_", "elementList", "element", "procedureStatement", 
			"writeProcedureStatement", "readProcedureStatement", "writeParameters", 
			"outputValue", "readParameters", "inputValue", "actualParameter", "parameterwidth", 
			"gotoStatement", "emptyStatement", "empty_", "structuredStatement", "compoundStatement", 
			"statements", "conditionalStatement", "ifStatement", "caseStatement", 
			"caseListElement", "repetetiveStatement", "whileStatement", "repeatStatement", 
			"forStatement", "forList", "initialValue", "finalValue", "withStatement", 
			"recordVariableList"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, "'+'", "'-'", "'*'", 
			"'/'", "':='", "','", "';'", "':'", "'='", "'<>'", "'<'", "'<='", "'>='", 
			"'>'", "'('", "')'", "'['", "'(.'", "']'", "'.)'", "'^'", "'@'", "'.'", 
			"'..'", "'{'", "'}'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WRITE", "WRITELN", "READ", "READLN", "AND", "ARRAY", "BEGIN", 
			"BOOLEAN", "CASE", "CHAR", "CHR", "CONST", "DIV", "DO", "DOWNTO", "ELSE", 
			"END", "FILE", "FOR", "FUNCTION", "GOTO", "IF", "IN", "INTEGER", "LABEL", 
			"MOD", "NIL", "NOT", "OF", "OR", "PACKED", "PROCEDURE", "PROGRAM", "REAL", 
			"RECORD", "REPEAT", "SET", "THEN", "TO", "TYPE", "UNTIL", "VAR", "WHILE", 
			"WITH", "PLUS", "MINUS", "STAR", "SLASH", "ASSIGN", "COMMA", "SEMI", 
			"COLON", "EQUAL", "NOT_EQUAL", "LT", "LE", "GE", "GT", "LPAREN", "RPAREN", 
			"LBRACK", "LBRACK2", "RBRACK", "RBRACK2", "POINTER", "AT", "DOT", "DOTDOT", 
			"LCURLY", "RCURLY", "UNIT", "INTERFACE", "USES", "STRING", "IMPLEMENTATION", 
			"TRUE", "FALSE", "FORWARD", "WS", "COMMENT_1", "COMMENT_2", "IDENT", 
			"STRING_LITERAL", "NUM_INT", "NUM_REAL"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
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

	@Override
	public String getGrammarFileName() { return "Pascal.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public PascalParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public ProgramHeadingContext programHeading() {
			return getRuleContext(ProgramHeadingContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode DOT() { return getToken(PascalParser.DOT, 0); }
		public TerminalNode INTERFACE() { return getToken(PascalParser.INTERFACE, 0); }
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(208);
			programHeading();
			setState(210);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==INTERFACE) {
				{
				setState(209);
				match(INTERFACE);
				}
			}

			setState(212);
			block();
			setState(213);
			match(DOT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProgramHeadingContext extends ParserRuleContext {
		public TerminalNode PROGRAM() { return getToken(PascalParser.PROGRAM, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(PascalParser.SEMI, 0); }
		public TerminalNode LPAREN() { return getToken(PascalParser.LPAREN, 0); }
		public IdentifierListContext identifierList() {
			return getRuleContext(IdentifierListContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(PascalParser.RPAREN, 0); }
		public TerminalNode UNIT() { return getToken(PascalParser.UNIT, 0); }
		public ProgramHeadingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_programHeading; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitProgramHeading(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramHeadingContext programHeading() throws RecognitionException {
		ProgramHeadingContext _localctx = new ProgramHeadingContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_programHeading);
		int _la;
		try {
			setState(229);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PROGRAM:
				enterOuterAlt(_localctx, 1);
				{
				setState(215);
				match(PROGRAM);
				setState(216);
				identifier();
				setState(221);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LPAREN) {
					{
					setState(217);
					match(LPAREN);
					setState(218);
					identifierList();
					setState(219);
					match(RPAREN);
					}
				}

				setState(223);
				match(SEMI);
				}
				break;
			case UNIT:
				enterOuterAlt(_localctx, 2);
				{
				setState(225);
				match(UNIT);
				setState(226);
				identifier();
				setState(227);
				match(SEMI);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentifierContext extends ParserRuleContext {
		public TerminalNode IDENT() { return getToken(PascalParser.IDENT, 0); }
		public IdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifier; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentifierContext identifier() throws RecognitionException {
		IdentifierContext _localctx = new IdentifierContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_identifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(231);
			match(IDENT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public CompoundStatementContext compoundStatement() {
			return getRuleContext(CompoundStatementContext.class,0);
		}
		public List<LabelDeclarationPartContext> labelDeclarationPart() {
			return getRuleContexts(LabelDeclarationPartContext.class);
		}
		public LabelDeclarationPartContext labelDeclarationPart(int i) {
			return getRuleContext(LabelDeclarationPartContext.class,i);
		}
		public List<ConstantDefinitionPartContext> constantDefinitionPart() {
			return getRuleContexts(ConstantDefinitionPartContext.class);
		}
		public ConstantDefinitionPartContext constantDefinitionPart(int i) {
			return getRuleContext(ConstantDefinitionPartContext.class,i);
		}
		public List<TypeDefinitionPartContext> typeDefinitionPart() {
			return getRuleContexts(TypeDefinitionPartContext.class);
		}
		public TypeDefinitionPartContext typeDefinitionPart(int i) {
			return getRuleContext(TypeDefinitionPartContext.class,i);
		}
		public List<VariableDeclarationPartContext> variableDeclarationPart() {
			return getRuleContexts(VariableDeclarationPartContext.class);
		}
		public VariableDeclarationPartContext variableDeclarationPart(int i) {
			return getRuleContext(VariableDeclarationPartContext.class,i);
		}
		public List<ProcedureAndFunctionDeclarationPartContext> procedureAndFunctionDeclarationPart() {
			return getRuleContexts(ProcedureAndFunctionDeclarationPartContext.class);
		}
		public ProcedureAndFunctionDeclarationPartContext procedureAndFunctionDeclarationPart(int i) {
			return getRuleContext(ProcedureAndFunctionDeclarationPartContext.class,i);
		}
		public List<UsesUnitsPartContext> usesUnitsPart() {
			return getRuleContexts(UsesUnitsPartContext.class);
		}
		public UsesUnitsPartContext usesUnitsPart(int i) {
			return getRuleContext(UsesUnitsPartContext.class,i);
		}
		public List<TerminalNode> IMPLEMENTATION() { return getTokens(PascalParser.IMPLEMENTATION); }
		public TerminalNode IMPLEMENTATION(int i) {
			return getToken(PascalParser.IMPLEMENTATION, i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(242);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 12)) & ~0x3f) == 0 && ((1L << (_la - 12)) & ((1L << (CONST - 12)) | (1L << (FUNCTION - 12)) | (1L << (LABEL - 12)) | (1L << (PROCEDURE - 12)) | (1L << (TYPE - 12)) | (1L << (VAR - 12)) | (1L << (USES - 12)) | (1L << (IMPLEMENTATION - 12)))) != 0)) {
				{
				setState(240);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case LABEL:
					{
					setState(233);
					labelDeclarationPart();
					}
					break;
				case CONST:
					{
					setState(234);
					constantDefinitionPart();
					}
					break;
				case TYPE:
					{
					setState(235);
					typeDefinitionPart();
					}
					break;
				case VAR:
					{
					setState(236);
					variableDeclarationPart();
					}
					break;
				case FUNCTION:
				case PROCEDURE:
					{
					setState(237);
					procedureAndFunctionDeclarationPart();
					}
					break;
				case USES:
					{
					setState(238);
					usesUnitsPart();
					}
					break;
				case IMPLEMENTATION:
					{
					setState(239);
					match(IMPLEMENTATION);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(244);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(245);
			compoundStatement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UsesUnitsPartContext extends ParserRuleContext {
		public TerminalNode USES() { return getToken(PascalParser.USES, 0); }
		public IdentifierListContext identifierList() {
			return getRuleContext(IdentifierListContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(PascalParser.SEMI, 0); }
		public UsesUnitsPartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_usesUnitsPart; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitUsesUnitsPart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UsesUnitsPartContext usesUnitsPart() throws RecognitionException {
		UsesUnitsPartContext _localctx = new UsesUnitsPartContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_usesUnitsPart);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(247);
			match(USES);
			setState(248);
			identifierList();
			setState(249);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LabelDeclarationPartContext extends ParserRuleContext {
		public TerminalNode LABEL() { return getToken(PascalParser.LABEL, 0); }
		public List<LabelContext> label() {
			return getRuleContexts(LabelContext.class);
		}
		public LabelContext label(int i) {
			return getRuleContext(LabelContext.class,i);
		}
		public TerminalNode SEMI() { return getToken(PascalParser.SEMI, 0); }
		public List<TerminalNode> COMMA() { return getTokens(PascalParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(PascalParser.COMMA, i);
		}
		public LabelDeclarationPartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_labelDeclarationPart; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitLabelDeclarationPart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelDeclarationPartContext labelDeclarationPart() throws RecognitionException {
		LabelDeclarationPartContext _localctx = new LabelDeclarationPartContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_labelDeclarationPart);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(251);
			match(LABEL);
			setState(252);
			label();
			setState(257);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(253);
				match(COMMA);
				setState(254);
				label();
				}
				}
				setState(259);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(260);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LabelContext extends ParserRuleContext {
		public TerminalNode NUM_INT() { return getToken(PascalParser.NUM_INT, 0); }
		public LabelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_label; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitLabel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelContext label() throws RecognitionException {
		LabelContext _localctx = new LabelContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_label);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(262);
			match(NUM_INT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstantDefinitionPartContext extends ParserRuleContext {
		public TerminalNode CONST() { return getToken(PascalParser.CONST, 0); }
		public List<ConstantDefinitionContext> constantDefinition() {
			return getRuleContexts(ConstantDefinitionContext.class);
		}
		public ConstantDefinitionContext constantDefinition(int i) {
			return getRuleContext(ConstantDefinitionContext.class,i);
		}
		public List<TerminalNode> SEMI() { return getTokens(PascalParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(PascalParser.SEMI, i);
		}
		public ConstantDefinitionPartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constantDefinitionPart; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitConstantDefinitionPart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstantDefinitionPartContext constantDefinitionPart() throws RecognitionException {
		ConstantDefinitionPartContext _localctx = new ConstantDefinitionPartContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_constantDefinitionPart);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(264);
			match(CONST);
			setState(268); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(265);
				constantDefinition();
				setState(266);
				match(SEMI);
				}
				}
				setState(270); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==IDENT );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstantDefinitionContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode EQUAL() { return getToken(PascalParser.EQUAL, 0); }
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public ConstantDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constantDefinition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitConstantDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstantDefinitionContext constantDefinition() throws RecognitionException {
		ConstantDefinitionContext _localctx = new ConstantDefinitionContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_constantDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(272);
			identifier();
			setState(273);
			match(EQUAL);
			setState(274);
			constant();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstantChrContext extends ParserRuleContext {
		public TerminalNode CHR() { return getToken(PascalParser.CHR, 0); }
		public TerminalNode LPAREN() { return getToken(PascalParser.LPAREN, 0); }
		public TerminalNode NUM_INT() { return getToken(PascalParser.NUM_INT, 0); }
		public TerminalNode RPAREN() { return getToken(PascalParser.RPAREN, 0); }
		public ConstantChrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constantChr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitConstantChr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstantChrContext constantChr() throws RecognitionException {
		ConstantChrContext _localctx = new ConstantChrContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_constantChr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(276);
			match(CHR);
			setState(277);
			match(LPAREN);
			setState(278);
			match(NUM_INT);
			setState(279);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstantContext extends ParserRuleContext {
		public ConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constant; }
	 
		public ConstantContext() { }
		public void copyFrom(ConstantContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class BoolConstContext extends ConstantContext {
		public Bool_Context bool_() {
			return getRuleContext(Bool_Context.class,0);
		}
		public BoolConstContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitBoolConst(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ConstantSignedIdentifierContext extends ConstantContext {
		public SignContext sign() {
			return getRuleContext(SignContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public ConstantSignedIdentifierContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitConstantSignedIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnsignedNumberConstContext extends ConstantContext {
		public UnsignedNumberContext unsignedNumber() {
			return getRuleContext(UnsignedNumberContext.class,0);
		}
		public UnsignedNumberConstContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitUnsignedNumberConst(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ConstantIdentifierContext extends ConstantContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public ConstantIdentifierContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitConstantIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SignedNumberConstContext extends ConstantContext {
		public SignContext sign() {
			return getRuleContext(SignContext.class,0);
		}
		public UnsignedNumberContext unsignedNumber() {
			return getRuleContext(UnsignedNumberContext.class,0);
		}
		public SignedNumberConstContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitSignedNumberConst(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StringConstContext extends ConstantContext {
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public StringConstContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitStringConst(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ChrConstContext extends ConstantContext {
		public ConstantChrContext constantChr() {
			return getRuleContext(ConstantChrContext.class,0);
		}
		public ChrConstContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitChrConst(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstantContext constant() throws RecognitionException {
		ConstantContext _localctx = new ConstantContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_constant);
		try {
			setState(292);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				_localctx = new UnsignedNumberConstContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(281);
				unsignedNumber();
				}
				break;
			case 2:
				_localctx = new SignedNumberConstContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(282);
				sign();
				setState(283);
				unsignedNumber();
				}
				break;
			case 3:
				_localctx = new ConstantIdentifierContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(285);
				identifier();
				}
				break;
			case 4:
				_localctx = new ConstantSignedIdentifierContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(286);
				sign();
				setState(287);
				identifier();
				}
				break;
			case 5:
				_localctx = new StringConstContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(289);
				string();
				}
				break;
			case 6:
				_localctx = new ChrConstContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(290);
				constantChr();
				}
				break;
			case 7:
				_localctx = new BoolConstContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(291);
				bool_();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnsignedNumberContext extends ParserRuleContext {
		public Token type;
		public TerminalNode NUM_INT() { return getToken(PascalParser.NUM_INT, 0); }
		public TerminalNode NUM_REAL() { return getToken(PascalParser.NUM_REAL, 0); }
		public UnsignedNumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unsignedNumber; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitUnsignedNumber(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnsignedNumberContext unsignedNumber() throws RecognitionException {
		UnsignedNumberContext _localctx = new UnsignedNumberContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_unsignedNumber);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(294);
			((UnsignedNumberContext)_localctx).type = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==NUM_INT || _la==NUM_REAL) ) {
				((UnsignedNumberContext)_localctx).type = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SignContext extends ParserRuleContext {
		public TerminalNode PLUS() { return getToken(PascalParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(PascalParser.MINUS, 0); }
		public SignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sign; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitSign(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SignContext sign() throws RecognitionException {
		SignContext _localctx = new SignContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_sign);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(296);
			_la = _input.LA(1);
			if ( !(_la==PLUS || _la==MINUS) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Bool_Context extends ParserRuleContext {
		public Bool_Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bool_; }
	 
		public Bool_Context() { }
		public void copyFrom(Bool_Context ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class TrueContext extends Bool_Context {
		public TerminalNode TRUE() { return getToken(PascalParser.TRUE, 0); }
		public TrueContext(Bool_Context ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitTrue(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FalseContext extends Bool_Context {
		public TerminalNode FALSE() { return getToken(PascalParser.FALSE, 0); }
		public FalseContext(Bool_Context ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitFalse(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Bool_Context bool_() throws RecognitionException {
		Bool_Context _localctx = new Bool_Context(_ctx, getState());
		enterRule(_localctx, 26, RULE_bool_);
		try {
			setState(300);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TRUE:
				_localctx = new TrueContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(298);
				match(TRUE);
				}
				break;
			case FALSE:
				_localctx = new FalseContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(299);
				match(FALSE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StringContext extends ParserRuleContext {
		public TerminalNode STRING_LITERAL() { return getToken(PascalParser.STRING_LITERAL, 0); }
		public StringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitString(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringContext string() throws RecognitionException {
		StringContext _localctx = new StringContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_string);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(302);
			match(STRING_LITERAL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeDefinitionPartContext extends ParserRuleContext {
		public TerminalNode TYPE() { return getToken(PascalParser.TYPE, 0); }
		public List<TypeDefinitionContext> typeDefinition() {
			return getRuleContexts(TypeDefinitionContext.class);
		}
		public TypeDefinitionContext typeDefinition(int i) {
			return getRuleContext(TypeDefinitionContext.class,i);
		}
		public List<TerminalNode> SEMI() { return getTokens(PascalParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(PascalParser.SEMI, i);
		}
		public TypeDefinitionPartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeDefinitionPart; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitTypeDefinitionPart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeDefinitionPartContext typeDefinitionPart() throws RecognitionException {
		TypeDefinitionPartContext _localctx = new TypeDefinitionPartContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_typeDefinitionPart);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(304);
			match(TYPE);
			setState(308); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(305);
				typeDefinition();
				setState(306);
				match(SEMI);
				}
				}
				setState(310); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==IDENT );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeDefinitionContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode EQUAL() { return getToken(PascalParser.EQUAL, 0); }
		public Type_Context type_() {
			return getRuleContext(Type_Context.class,0);
		}
		public TypeDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeDefinition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitTypeDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeDefinitionContext typeDefinition() throws RecognitionException {
		TypeDefinitionContext _localctx = new TypeDefinitionContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_typeDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(312);
			identifier();
			setState(313);
			match(EQUAL);
			setState(314);
			type_();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionTypeContext extends ParserRuleContext {
		public TerminalNode FUNCTION() { return getToken(PascalParser.FUNCTION, 0); }
		public TerminalNode COLON() { return getToken(PascalParser.COLON, 0); }
		public ResultTypeContext resultType() {
			return getRuleContext(ResultTypeContext.class,0);
		}
		public FormalParameterListContext formalParameterList() {
			return getRuleContext(FormalParameterListContext.class,0);
		}
		public FunctionTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitFunctionType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionTypeContext functionType() throws RecognitionException {
		FunctionTypeContext _localctx = new FunctionTypeContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_functionType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(316);
			match(FUNCTION);
			setState(318);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(317);
				formalParameterList();
				}
			}

			setState(320);
			match(COLON);
			setState(321);
			resultType();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProcedureTypeContext extends ParserRuleContext {
		public TerminalNode PROCEDURE() { return getToken(PascalParser.PROCEDURE, 0); }
		public FormalParameterListContext formalParameterList() {
			return getRuleContext(FormalParameterListContext.class,0);
		}
		public ProcedureTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_procedureType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitProcedureType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProcedureTypeContext procedureType() throws RecognitionException {
		ProcedureTypeContext _localctx = new ProcedureTypeContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_procedureType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(323);
			match(PROCEDURE);
			setState(325);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(324);
				formalParameterList();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Type_Context extends ParserRuleContext {
		public SimpleTypeContext simpleType() {
			return getRuleContext(SimpleTypeContext.class,0);
		}
		public StructuredTypeContext structuredType() {
			return getRuleContext(StructuredTypeContext.class,0);
		}
		public PointerTypeContext pointerType() {
			return getRuleContext(PointerTypeContext.class,0);
		}
		public Type_Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitType_(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Type_Context type_() throws RecognitionException {
		Type_Context _localctx = new Type_Context(_ctx, getState());
		enterRule(_localctx, 38, RULE_type_);
		try {
			setState(330);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BOOLEAN:
			case CHAR:
			case CHR:
			case INTEGER:
			case REAL:
			case PLUS:
			case MINUS:
			case LPAREN:
			case STRING:
			case TRUE:
			case FALSE:
			case IDENT:
			case STRING_LITERAL:
			case NUM_INT:
			case NUM_REAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(327);
				simpleType();
				}
				break;
			case ARRAY:
			case FILE:
			case PACKED:
			case RECORD:
			case SET:
				enterOuterAlt(_localctx, 2);
				{
				setState(328);
				structuredType();
				}
				break;
			case POINTER:
				enterOuterAlt(_localctx, 3);
				{
				setState(329);
				pointerType();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleTypeContext extends ParserRuleContext {
		public ScalarTypeContext scalarType() {
			return getRuleContext(ScalarTypeContext.class,0);
		}
		public SubrangeTypeContext subrangeType() {
			return getRuleContext(SubrangeTypeContext.class,0);
		}
		public TypeIdentifierContext typeIdentifier() {
			return getRuleContext(TypeIdentifierContext.class,0);
		}
		public StringtypeContext stringtype() {
			return getRuleContext(StringtypeContext.class,0);
		}
		public SimpleTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitSimpleType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleTypeContext simpleType() throws RecognitionException {
		SimpleTypeContext _localctx = new SimpleTypeContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_simpleType);
		try {
			setState(336);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(332);
				scalarType();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(333);
				subrangeType();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(334);
				typeIdentifier();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(335);
				stringtype();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ScalarTypeContext extends ParserRuleContext {
		public ScalarTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_scalarType; }
	 
		public ScalarTypeContext() { }
		public void copyFrom(ScalarTypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class EnumeratedTypeContext extends ScalarTypeContext {
		public TerminalNode LPAREN() { return getToken(PascalParser.LPAREN, 0); }
		public IdentifierListContext identifierList() {
			return getRuleContext(IdentifierListContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(PascalParser.RPAREN, 0); }
		public EnumeratedTypeContext(ScalarTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitEnumeratedType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ScalarTypeContext scalarType() throws RecognitionException {
		ScalarTypeContext _localctx = new ScalarTypeContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_scalarType);
		try {
			_localctx = new EnumeratedTypeContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(338);
			match(LPAREN);
			setState(339);
			identifierList();
			setState(340);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SubrangeTypeContext extends ParserRuleContext {
		public List<ConstantContext> constant() {
			return getRuleContexts(ConstantContext.class);
		}
		public ConstantContext constant(int i) {
			return getRuleContext(ConstantContext.class,i);
		}
		public TerminalNode DOTDOT() { return getToken(PascalParser.DOTDOT, 0); }
		public SubrangeTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subrangeType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitSubrangeType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubrangeTypeContext subrangeType() throws RecognitionException {
		SubrangeTypeContext _localctx = new SubrangeTypeContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_subrangeType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(342);
			constant();
			setState(343);
			match(DOTDOT);
			setState(344);
			constant();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeIdentifierContext extends ParserRuleContext {
		public TypeIdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeIdentifier; }
	 
		public TypeIdentifierContext() { }
		public void copyFrom(TypeIdentifierContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class TypeIdContext extends TypeIdentifierContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TypeIdContext(TypeIdentifierContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitTypeId(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PrimitiveTypeContext extends TypeIdentifierContext {
		public Token primitiveType;
		public TerminalNode CHAR() { return getToken(PascalParser.CHAR, 0); }
		public TerminalNode BOOLEAN() { return getToken(PascalParser.BOOLEAN, 0); }
		public TerminalNode INTEGER() { return getToken(PascalParser.INTEGER, 0); }
		public TerminalNode REAL() { return getToken(PascalParser.REAL, 0); }
		public TerminalNode STRING() { return getToken(PascalParser.STRING, 0); }
		public PrimitiveTypeContext(TypeIdentifierContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitPrimitiveType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeIdentifierContext typeIdentifier() throws RecognitionException {
		TypeIdentifierContext _localctx = new TypeIdentifierContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_typeIdentifier);
		int _la;
		try {
			setState(348);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENT:
				_localctx = new TypeIdContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(346);
				identifier();
				}
				break;
			case BOOLEAN:
			case CHAR:
			case INTEGER:
			case REAL:
			case STRING:
				_localctx = new PrimitiveTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(347);
				((PrimitiveTypeContext)_localctx).primitiveType = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << CHAR) | (1L << INTEGER) | (1L << REAL))) != 0) || _la==STRING) ) {
					((PrimitiveTypeContext)_localctx).primitiveType = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StructuredTypeContext extends ParserRuleContext {
		public TerminalNode PACKED() { return getToken(PascalParser.PACKED, 0); }
		public UnpackedStructuredTypeContext unpackedStructuredType() {
			return getRuleContext(UnpackedStructuredTypeContext.class,0);
		}
		public StructuredTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_structuredType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitStructuredType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StructuredTypeContext structuredType() throws RecognitionException {
		StructuredTypeContext _localctx = new StructuredTypeContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_structuredType);
		try {
			setState(353);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PACKED:
				enterOuterAlt(_localctx, 1);
				{
				setState(350);
				match(PACKED);
				setState(351);
				unpackedStructuredType();
				}
				break;
			case ARRAY:
			case FILE:
			case RECORD:
			case SET:
				enterOuterAlt(_localctx, 2);
				{
				setState(352);
				unpackedStructuredType();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnpackedStructuredTypeContext extends ParserRuleContext {
		public ArrayTypeContext arrayType() {
			return getRuleContext(ArrayTypeContext.class,0);
		}
		public RecordTypeContext recordType() {
			return getRuleContext(RecordTypeContext.class,0);
		}
		public SetTypeContext setType() {
			return getRuleContext(SetTypeContext.class,0);
		}
		public FileTypeContext fileType() {
			return getRuleContext(FileTypeContext.class,0);
		}
		public UnpackedStructuredTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unpackedStructuredType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitUnpackedStructuredType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnpackedStructuredTypeContext unpackedStructuredType() throws RecognitionException {
		UnpackedStructuredTypeContext _localctx = new UnpackedStructuredTypeContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_unpackedStructuredType);
		try {
			setState(359);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ARRAY:
				enterOuterAlt(_localctx, 1);
				{
				setState(355);
				arrayType();
				}
				break;
			case RECORD:
				enterOuterAlt(_localctx, 2);
				{
				setState(356);
				recordType();
				}
				break;
			case SET:
				enterOuterAlt(_localctx, 3);
				{
				setState(357);
				setType();
				}
				break;
			case FILE:
				enterOuterAlt(_localctx, 4);
				{
				setState(358);
				fileType();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StringtypeContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(PascalParser.STRING, 0); }
		public TerminalNode LBRACK() { return getToken(PascalParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(PascalParser.RBRACK, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public UnsignedNumberContext unsignedNumber() {
			return getRuleContext(UnsignedNumberContext.class,0);
		}
		public StringtypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringtype; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitStringtype(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringtypeContext stringtype() throws RecognitionException {
		StringtypeContext _localctx = new StringtypeContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_stringtype);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(361);
			match(STRING);
			setState(362);
			match(LBRACK);
			setState(365);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENT:
				{
				setState(363);
				identifier();
				}
				break;
			case NUM_INT:
			case NUM_REAL:
				{
				setState(364);
				unsignedNumber();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(367);
			match(RBRACK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayTypeContext extends ParserRuleContext {
		public TerminalNode ARRAY() { return getToken(PascalParser.ARRAY, 0); }
		public TerminalNode LBRACK() { return getToken(PascalParser.LBRACK, 0); }
		public TypeListContext typeList() {
			return getRuleContext(TypeListContext.class,0);
		}
		public TerminalNode RBRACK() { return getToken(PascalParser.RBRACK, 0); }
		public TerminalNode OF() { return getToken(PascalParser.OF, 0); }
		public ComponentTypeContext componentType() {
			return getRuleContext(ComponentTypeContext.class,0);
		}
		public TerminalNode LBRACK2() { return getToken(PascalParser.LBRACK2, 0); }
		public TerminalNode RBRACK2() { return getToken(PascalParser.RBRACK2, 0); }
		public ArrayTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitArrayType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayTypeContext arrayType() throws RecognitionException {
		ArrayTypeContext _localctx = new ArrayTypeContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_arrayType);
		try {
			setState(383);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(369);
				match(ARRAY);
				setState(370);
				match(LBRACK);
				setState(371);
				typeList();
				setState(372);
				match(RBRACK);
				setState(373);
				match(OF);
				setState(374);
				componentType();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(376);
				match(ARRAY);
				setState(377);
				match(LBRACK2);
				setState(378);
				typeList();
				setState(379);
				match(RBRACK2);
				setState(380);
				match(OF);
				setState(381);
				componentType();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeListContext extends ParserRuleContext {
		public List<IndexTypeContext> indexType() {
			return getRuleContexts(IndexTypeContext.class);
		}
		public IndexTypeContext indexType(int i) {
			return getRuleContext(IndexTypeContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(PascalParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(PascalParser.COMMA, i);
		}
		public TypeListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitTypeList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeListContext typeList() throws RecognitionException {
		TypeListContext _localctx = new TypeListContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_typeList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(385);
			indexType();
			setState(390);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(386);
				match(COMMA);
				setState(387);
				indexType();
				}
				}
				setState(392);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IndexTypeContext extends ParserRuleContext {
		public SimpleTypeContext simpleType() {
			return getRuleContext(SimpleTypeContext.class,0);
		}
		public IndexTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_indexType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitIndexType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IndexTypeContext indexType() throws RecognitionException {
		IndexTypeContext _localctx = new IndexTypeContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_indexType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(393);
			simpleType();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ComponentTypeContext extends ParserRuleContext {
		public Type_Context type_() {
			return getRuleContext(Type_Context.class,0);
		}
		public ComponentTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_componentType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitComponentType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComponentTypeContext componentType() throws RecognitionException {
		ComponentTypeContext _localctx = new ComponentTypeContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_componentType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(395);
			type_();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RecordTypeContext extends ParserRuleContext {
		public TerminalNode RECORD() { return getToken(PascalParser.RECORD, 0); }
		public TerminalNode END() { return getToken(PascalParser.END, 0); }
		public FieldListContext fieldList() {
			return getRuleContext(FieldListContext.class,0);
		}
		public RecordTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_recordType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitRecordType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RecordTypeContext recordType() throws RecognitionException {
		RecordTypeContext _localctx = new RecordTypeContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_recordType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(397);
			match(RECORD);
			setState(399);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==CASE || _la==IDENT) {
				{
				setState(398);
				fieldList();
				}
			}

			setState(401);
			match(END);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FieldListContext extends ParserRuleContext {
		public FixedPartContext fixedPart() {
			return getRuleContext(FixedPartContext.class,0);
		}
		public List<TerminalNode> SEMI() { return getTokens(PascalParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(PascalParser.SEMI, i);
		}
		public VariantPartContext variantPart() {
			return getRuleContext(VariantPartContext.class,0);
		}
		public FieldListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitFieldList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldListContext fieldList() throws RecognitionException {
		FieldListContext _localctx = new FieldListContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_fieldList);
		int _la;
		try {
			setState(415);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENT:
				enterOuterAlt(_localctx, 1);
				{
				setState(403);
				fixedPart();
				setState(406);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
				case 1:
					{
					setState(404);
					match(SEMI);
					setState(405);
					variantPart();
					}
					break;
				}
				setState(409);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SEMI) {
					{
					setState(408);
					match(SEMI);
					}
				}

				}
				break;
			case CASE:
				enterOuterAlt(_localctx, 2);
				{
				setState(411);
				variantPart();
				setState(413);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SEMI) {
					{
					setState(412);
					match(SEMI);
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FixedPartContext extends ParserRuleContext {
		public List<RecordSectionContext> recordSection() {
			return getRuleContexts(RecordSectionContext.class);
		}
		public RecordSectionContext recordSection(int i) {
			return getRuleContext(RecordSectionContext.class,i);
		}
		public List<TerminalNode> SEMI() { return getTokens(PascalParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(PascalParser.SEMI, i);
		}
		public FixedPartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fixedPart; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitFixedPart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FixedPartContext fixedPart() throws RecognitionException {
		FixedPartContext _localctx = new FixedPartContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_fixedPart);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(417);
			recordSection();
			setState(422);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(418);
					match(SEMI);
					setState(419);
					recordSection();
					}
					} 
				}
				setState(424);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RecordSectionContext extends ParserRuleContext {
		public IdentifierListContext identifierList() {
			return getRuleContext(IdentifierListContext.class,0);
		}
		public TerminalNode COLON() { return getToken(PascalParser.COLON, 0); }
		public Type_Context type_() {
			return getRuleContext(Type_Context.class,0);
		}
		public RecordSectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_recordSection; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitRecordSection(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RecordSectionContext recordSection() throws RecognitionException {
		RecordSectionContext _localctx = new RecordSectionContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_recordSection);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(425);
			identifierList();
			setState(426);
			match(COLON);
			setState(427);
			type_();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariantPartContext extends ParserRuleContext {
		public TerminalNode CASE() { return getToken(PascalParser.CASE, 0); }
		public TagContext tag() {
			return getRuleContext(TagContext.class,0);
		}
		public TerminalNode OF() { return getToken(PascalParser.OF, 0); }
		public List<VariantContext> variant() {
			return getRuleContexts(VariantContext.class);
		}
		public VariantContext variant(int i) {
			return getRuleContext(VariantContext.class,i);
		}
		public List<TerminalNode> SEMI() { return getTokens(PascalParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(PascalParser.SEMI, i);
		}
		public VariantPartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variantPart; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitVariantPart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariantPartContext variantPart() throws RecognitionException {
		VariantPartContext _localctx = new VariantPartContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_variantPart);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(429);
			match(CASE);
			setState(430);
			tag();
			setState(431);
			match(OF);
			setState(432);
			variant();
			setState(437);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(433);
					match(SEMI);
					setState(434);
					variant();
					}
					} 
				}
				setState(439);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TagContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode COLON() { return getToken(PascalParser.COLON, 0); }
		public TypeIdentifierContext typeIdentifier() {
			return getRuleContext(TypeIdentifierContext.class,0);
		}
		public TagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TagContext tag() throws RecognitionException {
		TagContext _localctx = new TagContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_tag);
		try {
			setState(445);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(440);
				identifier();
				setState(441);
				match(COLON);
				setState(442);
				typeIdentifier();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(444);
				typeIdentifier();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariantContext extends ParserRuleContext {
		public ConstListContext constList() {
			return getRuleContext(ConstListContext.class,0);
		}
		public TerminalNode COLON() { return getToken(PascalParser.COLON, 0); }
		public TerminalNode LPAREN() { return getToken(PascalParser.LPAREN, 0); }
		public FieldListContext fieldList() {
			return getRuleContext(FieldListContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(PascalParser.RPAREN, 0); }
		public VariantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variant; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitVariant(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariantContext variant() throws RecognitionException {
		VariantContext _localctx = new VariantContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_variant);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(447);
			constList();
			setState(448);
			match(COLON);
			setState(449);
			match(LPAREN);
			setState(450);
			fieldList();
			setState(451);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SetTypeContext extends ParserRuleContext {
		public TerminalNode SET() { return getToken(PascalParser.SET, 0); }
		public TerminalNode OF() { return getToken(PascalParser.OF, 0); }
		public BaseTypeContext baseType() {
			return getRuleContext(BaseTypeContext.class,0);
		}
		public SetTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitSetType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetTypeContext setType() throws RecognitionException {
		SetTypeContext _localctx = new SetTypeContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_setType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(453);
			match(SET);
			setState(454);
			match(OF);
			setState(455);
			baseType();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BaseTypeContext extends ParserRuleContext {
		public SimpleTypeContext simpleType() {
			return getRuleContext(SimpleTypeContext.class,0);
		}
		public BaseTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_baseType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitBaseType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BaseTypeContext baseType() throws RecognitionException {
		BaseTypeContext _localctx = new BaseTypeContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_baseType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(457);
			simpleType();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FileTypeContext extends ParserRuleContext {
		public TerminalNode FILE() { return getToken(PascalParser.FILE, 0); }
		public TerminalNode OF() { return getToken(PascalParser.OF, 0); }
		public Type_Context type_() {
			return getRuleContext(Type_Context.class,0);
		}
		public FileTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fileType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitFileType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FileTypeContext fileType() throws RecognitionException {
		FileTypeContext _localctx = new FileTypeContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_fileType);
		try {
			setState(463);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(459);
				match(FILE);
				setState(460);
				match(OF);
				setState(461);
				type_();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(462);
				match(FILE);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PointerTypeContext extends ParserRuleContext {
		public TerminalNode POINTER() { return getToken(PascalParser.POINTER, 0); }
		public TypeIdentifierContext typeIdentifier() {
			return getRuleContext(TypeIdentifierContext.class,0);
		}
		public PointerTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pointerType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitPointerType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PointerTypeContext pointerType() throws RecognitionException {
		PointerTypeContext _localctx = new PointerTypeContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_pointerType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(465);
			match(POINTER);
			setState(466);
			typeIdentifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDeclarationPartContext extends ParserRuleContext {
		public TerminalNode VAR() { return getToken(PascalParser.VAR, 0); }
		public List<VariableDeclarationContext> variableDeclaration() {
			return getRuleContexts(VariableDeclarationContext.class);
		}
		public VariableDeclarationContext variableDeclaration(int i) {
			return getRuleContext(VariableDeclarationContext.class,i);
		}
		public List<TerminalNode> SEMI() { return getTokens(PascalParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(PascalParser.SEMI, i);
		}
		public VariableDeclarationPartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclarationPart; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitVariableDeclarationPart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclarationPartContext variableDeclarationPart() throws RecognitionException {
		VariableDeclarationPartContext _localctx = new VariableDeclarationPartContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_variableDeclarationPart);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(468);
			match(VAR);
			setState(469);
			variableDeclaration();
			setState(474);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(470);
					match(SEMI);
					setState(471);
					variableDeclaration();
					}
					} 
				}
				setState(476);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
			}
			setState(477);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDeclarationContext extends ParserRuleContext {
		public IdentifierListContext identifierList() {
			return getRuleContext(IdentifierListContext.class,0);
		}
		public TerminalNode COLON() { return getToken(PascalParser.COLON, 0); }
		public Type_Context type_() {
			return getRuleContext(Type_Context.class,0);
		}
		public VariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitVariableDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclarationContext variableDeclaration() throws RecognitionException {
		VariableDeclarationContext _localctx = new VariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_variableDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(479);
			identifierList();
			setState(480);
			match(COLON);
			setState(481);
			type_();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProcedureAndFunctionDeclarationPartContext extends ParserRuleContext {
		public ProcedureOrFunctionDeclarationContext procedureOrFunctionDeclaration() {
			return getRuleContext(ProcedureOrFunctionDeclarationContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(PascalParser.SEMI, 0); }
		public ProcedureAndFunctionDeclarationPartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_procedureAndFunctionDeclarationPart; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitProcedureAndFunctionDeclarationPart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProcedureAndFunctionDeclarationPartContext procedureAndFunctionDeclarationPart() throws RecognitionException {
		ProcedureAndFunctionDeclarationPartContext _localctx = new ProcedureAndFunctionDeclarationPartContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_procedureAndFunctionDeclarationPart);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(483);
			procedureOrFunctionDeclaration();
			setState(484);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProcedureOrFunctionDeclarationContext extends ParserRuleContext {
		public ProcedureDeclarationContext procedureDeclaration() {
			return getRuleContext(ProcedureDeclarationContext.class,0);
		}
		public FunctionDeclarationContext functionDeclaration() {
			return getRuleContext(FunctionDeclarationContext.class,0);
		}
		public ProcedureOrFunctionDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_procedureOrFunctionDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitProcedureOrFunctionDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProcedureOrFunctionDeclarationContext procedureOrFunctionDeclaration() throws RecognitionException {
		ProcedureOrFunctionDeclarationContext _localctx = new ProcedureOrFunctionDeclarationContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_procedureOrFunctionDeclaration);
		try {
			setState(488);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PROCEDURE:
				enterOuterAlt(_localctx, 1);
				{
				setState(486);
				procedureDeclaration();
				}
				break;
			case FUNCTION:
				enterOuterAlt(_localctx, 2);
				{
				setState(487);
				functionDeclaration();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DirectiveContext extends ParserRuleContext {
		public TerminalNode FORWARD() { return getToken(PascalParser.FORWARD, 0); }
		public DirectiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_directive; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitDirective(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DirectiveContext directive() throws RecognitionException {
		DirectiveContext _localctx = new DirectiveContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_directive);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(490);
			match(FORWARD);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProcedureDeclarationContext extends ParserRuleContext {
		public ProcedureDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_procedureDeclaration; }
	 
		public ProcedureDeclarationContext() { }
		public void copyFrom(ProcedureDeclarationContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ProcedurePrototypeDeclContext extends ProcedureDeclarationContext {
		public ProcedureHeadingContext procedureHeading() {
			return getRuleContext(ProcedureHeadingContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(PascalParser.SEMI, 0); }
		public DirectiveContext directive() {
			return getRuleContext(DirectiveContext.class,0);
		}
		public ProcedurePrototypeDeclContext(ProcedureDeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitProcedurePrototypeDecl(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ProcedureImplContext extends ProcedureDeclarationContext {
		public TerminalNode PROCEDURE() { return getToken(PascalParser.PROCEDURE, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(PascalParser.SEMI, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ProcedureImplContext(ProcedureDeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitProcedureImpl(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ProcedureDeclContext extends ProcedureDeclarationContext {
		public TerminalNode PROCEDURE() { return getToken(PascalParser.PROCEDURE, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(PascalParser.SEMI, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public FormalParameterListContext formalParameterList() {
			return getRuleContext(FormalParameterListContext.class,0);
		}
		public ProcedureDeclContext(ProcedureDeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitProcedureDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProcedureDeclarationContext procedureDeclaration() throws RecognitionException {
		ProcedureDeclarationContext _localctx = new ProcedureDeclarationContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_procedureDeclaration);
		int _la;
		try {
			setState(509);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
			case 1:
				_localctx = new ProcedurePrototypeDeclContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(492);
				procedureHeading();
				setState(493);
				match(SEMI);
				setState(494);
				directive();
				}
				break;
			case 2:
				_localctx = new ProcedureImplContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(496);
				match(PROCEDURE);
				setState(497);
				identifier();
				setState(498);
				match(SEMI);
				setState(499);
				block();
				}
				break;
			case 3:
				_localctx = new ProcedureDeclContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(501);
				match(PROCEDURE);
				setState(502);
				identifier();
				setState(504);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LPAREN) {
					{
					setState(503);
					formalParameterList();
					}
				}

				setState(506);
				match(SEMI);
				setState(507);
				block();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FormalParameterListContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(PascalParser.LPAREN, 0); }
		public List<FormalParameterSectionContext> formalParameterSection() {
			return getRuleContexts(FormalParameterSectionContext.class);
		}
		public FormalParameterSectionContext formalParameterSection(int i) {
			return getRuleContext(FormalParameterSectionContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(PascalParser.RPAREN, 0); }
		public List<TerminalNode> SEMI() { return getTokens(PascalParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(PascalParser.SEMI, i);
		}
		public FormalParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formalParameterList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitFormalParameterList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormalParameterListContext formalParameterList() throws RecognitionException {
		FormalParameterListContext _localctx = new FormalParameterListContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_formalParameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(511);
			match(LPAREN);
			setState(512);
			formalParameterSection();
			setState(517);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SEMI) {
				{
				{
				setState(513);
				match(SEMI);
				setState(514);
				formalParameterSection();
				}
				}
				setState(519);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(520);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FormalParameterSectionContext extends ParserRuleContext {
		public FormalParameterSectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formalParameterSection; }
	 
		public FormalParameterSectionContext() { }
		public void copyFrom(FormalParameterSectionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class VarLabelParamContext extends FormalParameterSectionContext {
		public TerminalNode VAR() { return getToken(PascalParser.VAR, 0); }
		public ParameterGroupContext parameterGroup() {
			return getRuleContext(ParameterGroupContext.class,0);
		}
		public VarLabelParamContext(FormalParameterSectionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitVarLabelParam(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ProcParamContext extends FormalParameterSectionContext {
		public ProcedureHeadingContext procedureHeading() {
			return getRuleContext(ProcedureHeadingContext.class,0);
		}
		public ProcParamContext(FormalParameterSectionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitProcParam(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NoLabelParamContext extends FormalParameterSectionContext {
		public ParameterGroupContext parameterGroup() {
			return getRuleContext(ParameterGroupContext.class,0);
		}
		public NoLabelParamContext(FormalParameterSectionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitNoLabelParam(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FuncParamContext extends FormalParameterSectionContext {
		public FunctionHeadingContext functionHeading() {
			return getRuleContext(FunctionHeadingContext.class,0);
		}
		public FuncParamContext(FormalParameterSectionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitFuncParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormalParameterSectionContext formalParameterSection() throws RecognitionException {
		FormalParameterSectionContext _localctx = new FormalParameterSectionContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_formalParameterSection);
		try {
			setState(527);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENT:
				_localctx = new NoLabelParamContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(522);
				parameterGroup();
				}
				break;
			case VAR:
				_localctx = new VarLabelParamContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(523);
				match(VAR);
				setState(524);
				parameterGroup();
				}
				break;
			case FUNCTION:
				_localctx = new FuncParamContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(525);
				functionHeading();
				}
				break;
			case PROCEDURE:
				_localctx = new ProcParamContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(526);
				procedureHeading();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionHeadingContext extends ParserRuleContext {
		public TerminalNode FUNCTION() { return getToken(PascalParser.FUNCTION, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode COLON() { return getToken(PascalParser.COLON, 0); }
		public ResultTypeContext resultType() {
			return getRuleContext(ResultTypeContext.class,0);
		}
		public FormalParameterListContext formalParameterList() {
			return getRuleContext(FormalParameterListContext.class,0);
		}
		public FunctionHeadingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionHeading; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitFunctionHeading(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionHeadingContext functionHeading() throws RecognitionException {
		FunctionHeadingContext _localctx = new FunctionHeadingContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_functionHeading);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(529);
			match(FUNCTION);
			setState(530);
			identifier();
			setState(532);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(531);
				formalParameterList();
				}
			}

			setState(534);
			match(COLON);
			setState(535);
			resultType();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProcedureHeadingContext extends ParserRuleContext {
		public TerminalNode PROCEDURE() { return getToken(PascalParser.PROCEDURE, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public FormalParameterListContext formalParameterList() {
			return getRuleContext(FormalParameterListContext.class,0);
		}
		public ProcedureHeadingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_procedureHeading; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitProcedureHeading(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProcedureHeadingContext procedureHeading() throws RecognitionException {
		ProcedureHeadingContext _localctx = new ProcedureHeadingContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_procedureHeading);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(537);
			match(PROCEDURE);
			setState(538);
			identifier();
			setState(540);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(539);
				formalParameterList();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterGroupContext extends ParserRuleContext {
		public IdentifierListContext identifierList() {
			return getRuleContext(IdentifierListContext.class,0);
		}
		public TerminalNode COLON() { return getToken(PascalParser.COLON, 0); }
		public TypeIdentifierContext typeIdentifier() {
			return getRuleContext(TypeIdentifierContext.class,0);
		}
		public ParameterGroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterGroup; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitParameterGroup(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterGroupContext parameterGroup() throws RecognitionException {
		ParameterGroupContext _localctx = new ParameterGroupContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_parameterGroup);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(542);
			identifierList();
			setState(543);
			match(COLON);
			setState(544);
			typeIdentifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentifierListContext extends ParserRuleContext {
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(PascalParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(PascalParser.COMMA, i);
		}
		public IdentifierListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifierList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitIdentifierList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentifierListContext identifierList() throws RecognitionException {
		IdentifierListContext _localctx = new IdentifierListContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_identifierList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(546);
			identifier();
			setState(551);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(547);
				match(COMMA);
				setState(548);
				identifier();
				}
				}
				setState(553);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstListContext extends ParserRuleContext {
		public List<ConstantContext> constant() {
			return getRuleContexts(ConstantContext.class);
		}
		public ConstantContext constant(int i) {
			return getRuleContext(ConstantContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(PascalParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(PascalParser.COMMA, i);
		}
		public ConstListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitConstList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstListContext constList() throws RecognitionException {
		ConstListContext _localctx = new ConstListContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_constList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(554);
			constant();
			setState(559);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(555);
				match(COMMA);
				setState(556);
				constant();
				}
				}
				setState(561);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDeclarationContext extends ParserRuleContext {
		public FunctionDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDeclaration; }
	 
		public FunctionDeclarationContext() { }
		public void copyFrom(FunctionDeclarationContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class FunctionDeclContext extends FunctionDeclarationContext {
		public TerminalNode FUNCTION() { return getToken(PascalParser.FUNCTION, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode COLON() { return getToken(PascalParser.COLON, 0); }
		public ResultTypeContext resultType() {
			return getRuleContext(ResultTypeContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(PascalParser.SEMI, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public FormalParameterListContext formalParameterList() {
			return getRuleContext(FormalParameterListContext.class,0);
		}
		public FunctionDeclContext(FunctionDeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitFunctionDecl(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FunctionPrototypeDeclContext extends FunctionDeclarationContext {
		public FunctionHeadingContext functionHeading() {
			return getRuleContext(FunctionHeadingContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(PascalParser.SEMI, 0); }
		public DirectiveContext directive() {
			return getRuleContext(DirectiveContext.class,0);
		}
		public FunctionPrototypeDeclContext(FunctionDeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitFunctionPrototypeDecl(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FunctionImplContext extends FunctionDeclarationContext {
		public TerminalNode FUNCTION() { return getToken(PascalParser.FUNCTION, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(PascalParser.SEMI, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public FunctionImplContext(FunctionDeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitFunctionImpl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDeclarationContext functionDeclaration() throws RecognitionException {
		FunctionDeclarationContext _localctx = new FunctionDeclarationContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_functionDeclaration);
		int _la;
		try {
			setState(581);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
			case 1:
				_localctx = new FunctionPrototypeDeclContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(562);
				functionHeading();
				setState(563);
				match(SEMI);
				setState(564);
				directive();
				}
				break;
			case 2:
				_localctx = new FunctionImplContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(566);
				match(FUNCTION);
				setState(567);
				identifier();
				setState(568);
				match(SEMI);
				setState(569);
				block();
				}
				break;
			case 3:
				_localctx = new FunctionDeclContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(571);
				match(FUNCTION);
				setState(572);
				identifier();
				setState(574);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LPAREN) {
					{
					setState(573);
					formalParameterList();
					}
				}

				setState(576);
				match(COLON);
				setState(577);
				resultType();
				setState(578);
				match(SEMI);
				setState(579);
				block();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ResultTypeContext extends ParserRuleContext {
		public TypeIdentifierContext typeIdentifier() {
			return getRuleContext(TypeIdentifierContext.class,0);
		}
		public ResultTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_resultType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitResultType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ResultTypeContext resultType() throws RecognitionException {
		ResultTypeContext _localctx = new ResultTypeContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_resultType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(583);
			typeIdentifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public LabelContext label() {
			return getRuleContext(LabelContext.class,0);
		}
		public TerminalNode COLON() { return getToken(PascalParser.COLON, 0); }
		public UnlabelledStatementContext unlabelledStatement() {
			return getRuleContext(UnlabelledStatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_statement);
		try {
			setState(590);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NUM_INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(585);
				label();
				setState(586);
				match(COLON);
				setState(587);
				unlabelledStatement();
				}
				break;
			case WRITE:
			case WRITELN:
			case READ:
			case READLN:
			case BEGIN:
			case CASE:
			case ELSE:
			case END:
			case FOR:
			case GOTO:
			case IF:
			case REPEAT:
			case UNTIL:
			case WHILE:
			case WITH:
			case SEMI:
			case AT:
			case IDENT:
				enterOuterAlt(_localctx, 2);
				{
				setState(589);
				unlabelledStatement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnlabelledStatementContext extends ParserRuleContext {
		public SimpleStatementContext simpleStatement() {
			return getRuleContext(SimpleStatementContext.class,0);
		}
		public StructuredStatementContext structuredStatement() {
			return getRuleContext(StructuredStatementContext.class,0);
		}
		public UnlabelledStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unlabelledStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitUnlabelledStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnlabelledStatementContext unlabelledStatement() throws RecognitionException {
		UnlabelledStatementContext _localctx = new UnlabelledStatementContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_unlabelledStatement);
		try {
			setState(594);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case WRITE:
			case WRITELN:
			case READ:
			case READLN:
			case ELSE:
			case END:
			case GOTO:
			case UNTIL:
			case SEMI:
			case AT:
			case IDENT:
				enterOuterAlt(_localctx, 1);
				{
				setState(592);
				simpleStatement();
				}
				break;
			case BEGIN:
			case CASE:
			case FOR:
			case IF:
			case REPEAT:
			case WHILE:
			case WITH:
				enterOuterAlt(_localctx, 2);
				{
				setState(593);
				structuredStatement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleStatementContext extends ParserRuleContext {
		public AssignmentStatementContext assignmentStatement() {
			return getRuleContext(AssignmentStatementContext.class,0);
		}
		public ProcedureStatementContext procedureStatement() {
			return getRuleContext(ProcedureStatementContext.class,0);
		}
		public GotoStatementContext gotoStatement() {
			return getRuleContext(GotoStatementContext.class,0);
		}
		public EmptyStatementContext emptyStatement() {
			return getRuleContext(EmptyStatementContext.class,0);
		}
		public SimpleStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitSimpleStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleStatementContext simpleStatement() throws RecognitionException {
		SimpleStatementContext _localctx = new SimpleStatementContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_simpleStatement);
		try {
			setState(600);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,43,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(596);
				assignmentStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(597);
				procedureStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(598);
				gotoStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(599);
				emptyStatement();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentStatementContext extends ParserRuleContext {
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(PascalParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AssignmentStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignmentStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitAssignmentStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentStatementContext assignmentStatement() throws RecognitionException {
		AssignmentStatementContext _localctx = new AssignmentStatementContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_assignmentStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(602);
			variable();
			setState(603);
			match(ASSIGN);
			setState(604);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableContext extends ParserRuleContext {
		public VariableHeadContext variableHead() {
			return getRuleContext(VariableHeadContext.class,0);
		}
		public List<ArrayScriptingContext> arrayScripting() {
			return getRuleContexts(ArrayScriptingContext.class);
		}
		public ArrayScriptingContext arrayScripting(int i) {
			return getRuleContext(ArrayScriptingContext.class,i);
		}
		public List<FieldDesignatorContext> fieldDesignator() {
			return getRuleContexts(FieldDesignatorContext.class);
		}
		public FieldDesignatorContext fieldDesignator(int i) {
			return getRuleContext(FieldDesignatorContext.class,i);
		}
		public List<TerminalNode> POINTER() { return getTokens(PascalParser.POINTER); }
		public TerminalNode POINTER(int i) {
			return getToken(PascalParser.POINTER, i);
		}
		public VariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 122, RULE_variable);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(606);
			variableHead();
			setState(612);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,45,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					setState(610);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case LBRACK:
					case LBRACK2:
						{
						setState(607);
						arrayScripting();
						}
						break;
					case DOT:
						{
						setState(608);
						fieldDesignator();
						}
						break;
					case POINTER:
						{
						setState(609);
						match(POINTER);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					} 
				}
				setState(614);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,45,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableHeadContext extends ParserRuleContext {
		public TerminalNode AT() { return getToken(PascalParser.AT, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public VariableHeadContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableHead; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitVariableHead(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableHeadContext variableHead() throws RecognitionException {
		VariableHeadContext _localctx = new VariableHeadContext(_ctx, getState());
		enterRule(_localctx, 124, RULE_variableHead);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(618);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AT:
				{
				setState(615);
				match(AT);
				setState(616);
				identifier();
				}
				break;
			case IDENT:
				{
				setState(617);
				identifier();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayScriptingContext extends ParserRuleContext {
		public TerminalNode LBRACK() { return getToken(PascalParser.LBRACK, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode RBRACK() { return getToken(PascalParser.RBRACK, 0); }
		public List<TerminalNode> COMMA() { return getTokens(PascalParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(PascalParser.COMMA, i);
		}
		public TerminalNode LBRACK2() { return getToken(PascalParser.LBRACK2, 0); }
		public TerminalNode RBRACK2() { return getToken(PascalParser.RBRACK2, 0); }
		public ArrayScriptingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayScripting; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitArrayScripting(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayScriptingContext arrayScripting() throws RecognitionException {
		ArrayScriptingContext _localctx = new ArrayScriptingContext(_ctx, getState());
		enterRule(_localctx, 126, RULE_arrayScripting);
		int _la;
		try {
			setState(642);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACK:
				enterOuterAlt(_localctx, 1);
				{
				setState(620);
				match(LBRACK);
				setState(621);
				expression();
				setState(626);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(622);
					match(COMMA);
					setState(623);
					expression();
					}
					}
					setState(628);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(629);
				match(RBRACK);
				}
				break;
			case LBRACK2:
				enterOuterAlt(_localctx, 2);
				{
				setState(631);
				match(LBRACK2);
				setState(632);
				expression();
				setState(637);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(633);
					match(COMMA);
					setState(634);
					expression();
					}
					}
					setState(639);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(640);
				match(RBRACK2);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FieldDesignatorContext extends ParserRuleContext {
		public TerminalNode DOT() { return getToken(PascalParser.DOT, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public FieldDesignatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldDesignator; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitFieldDesignator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldDesignatorContext fieldDesignator() throws RecognitionException {
		FieldDesignatorContext _localctx = new FieldDesignatorContext(_ctx, getState());
		enterRule(_localctx, 128, RULE_fieldDesignator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(644);
			match(DOT);
			setState(645);
			identifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public Token relationalOperator;
		public ExpressionContext e2;
		public SimpleExpressionContext simpleExpression() {
			return getRuleContext(SimpleExpressionContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode EQUAL() { return getToken(PascalParser.EQUAL, 0); }
		public TerminalNode NOT_EQUAL() { return getToken(PascalParser.NOT_EQUAL, 0); }
		public TerminalNode LT() { return getToken(PascalParser.LT, 0); }
		public TerminalNode LE() { return getToken(PascalParser.LE, 0); }
		public TerminalNode GE() { return getToken(PascalParser.GE, 0); }
		public TerminalNode GT() { return getToken(PascalParser.GT, 0); }
		public TerminalNode IN() { return getToken(PascalParser.IN, 0); }
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 130, RULE_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(647);
			simpleExpression();
			setState(650);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IN) | (1L << EQUAL) | (1L << NOT_EQUAL) | (1L << LT) | (1L << LE) | (1L << GE) | (1L << GT))) != 0)) {
				{
				setState(648);
				((ExpressionContext)_localctx).relationalOperator = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IN) | (1L << EQUAL) | (1L << NOT_EQUAL) | (1L << LT) | (1L << LE) | (1L << GE) | (1L << GT))) != 0)) ) {
					((ExpressionContext)_localctx).relationalOperator = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(649);
				((ExpressionContext)_localctx).e2 = expression();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleExpressionContext extends ParserRuleContext {
		public Token additiveOperator;
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public SimpleExpressionContext simpleExpression() {
			return getRuleContext(SimpleExpressionContext.class,0);
		}
		public TerminalNode PLUS() { return getToken(PascalParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(PascalParser.MINUS, 0); }
		public TerminalNode OR() { return getToken(PascalParser.OR, 0); }
		public SimpleExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitSimpleExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleExpressionContext simpleExpression() throws RecognitionException {
		SimpleExpressionContext _localctx = new SimpleExpressionContext(_ctx, getState());
		enterRule(_localctx, 132, RULE_simpleExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(652);
			term();
			setState(655);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,51,_ctx) ) {
			case 1:
				{
				setState(653);
				((SimpleExpressionContext)_localctx).additiveOperator = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OR) | (1L << PLUS) | (1L << MINUS))) != 0)) ) {
					((SimpleExpressionContext)_localctx).additiveOperator = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(654);
				simpleExpression();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TermContext extends ParserRuleContext {
		public Token multiplicativeOperator;
		public SignedFactorContext signedFactor() {
			return getRuleContext(SignedFactorContext.class,0);
		}
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public TerminalNode STAR() { return getToken(PascalParser.STAR, 0); }
		public TerminalNode SLASH() { return getToken(PascalParser.SLASH, 0); }
		public TerminalNode DIV() { return getToken(PascalParser.DIV, 0); }
		public TerminalNode MOD() { return getToken(PascalParser.MOD, 0); }
		public TerminalNode AND() { return getToken(PascalParser.AND, 0); }
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 134, RULE_term);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(657);
			signedFactor();
			setState(660);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AND) | (1L << DIV) | (1L << MOD) | (1L << STAR) | (1L << SLASH))) != 0)) {
				{
				setState(658);
				((TermContext)_localctx).multiplicativeOperator = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AND) | (1L << DIV) | (1L << MOD) | (1L << STAR) | (1L << SLASH))) != 0)) ) {
					((TermContext)_localctx).multiplicativeOperator = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(659);
				term();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SignedFactorContext extends ParserRuleContext {
		public Token monadicOperator;
		public FactorContext factor() {
			return getRuleContext(FactorContext.class,0);
		}
		public TerminalNode PLUS() { return getToken(PascalParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(PascalParser.MINUS, 0); }
		public SignedFactorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_signedFactor; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitSignedFactor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SignedFactorContext signedFactor() throws RecognitionException {
		SignedFactorContext _localctx = new SignedFactorContext(_ctx, getState());
		enterRule(_localctx, 136, RULE_signedFactor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(663);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==PLUS || _la==MINUS) {
				{
				setState(662);
				((SignedFactorContext)_localctx).monadicOperator = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==PLUS || _la==MINUS) ) {
					((SignedFactorContext)_localctx).monadicOperator = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(665);
			factor();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FactorContext extends ParserRuleContext {
		public FactorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_factor; }
	 
		public FactorContext() { }
		public void copyFrom(FactorContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class FactorSetContext extends FactorContext {
		public Set_Context set_() {
			return getRuleContext(Set_Context.class,0);
		}
		public FactorSetContext(FactorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitFactorSet(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FactorBoolContext extends FactorContext {
		public Bool_Context bool_() {
			return getRuleContext(Bool_Context.class,0);
		}
		public FactorBoolContext(FactorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitFactorBool(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FactorFuncDesignatorContext extends FactorContext {
		public FunctionDesignatorContext functionDesignator() {
			return getRuleContext(FunctionDesignatorContext.class,0);
		}
		public FactorFuncDesignatorContext(FactorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitFactorFuncDesignator(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FactorUnConstContext extends FactorContext {
		public UnsignedConstantContext unsignedConstant() {
			return getRuleContext(UnsignedConstantContext.class,0);
		}
		public FactorUnConstContext(FactorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitFactorUnConst(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NotFactorContext extends FactorContext {
		public TerminalNode NOT() { return getToken(PascalParser.NOT, 0); }
		public FactorContext factor() {
			return getRuleContext(FactorContext.class,0);
		}
		public NotFactorContext(FactorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitNotFactor(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FactorVarContext extends FactorContext {
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public FactorVarContext(FactorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitFactorVar(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FactorExprContext extends FactorContext {
		public TerminalNode LPAREN() { return getToken(PascalParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(PascalParser.RPAREN, 0); }
		public FactorExprContext(FactorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitFactorExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FactorContext factor() throws RecognitionException {
		FactorContext _localctx = new FactorContext(_ctx, getState());
		enterRule(_localctx, 138, RULE_factor);
		try {
			setState(678);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
			case 1:
				_localctx = new FactorFuncDesignatorContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(667);
				functionDesignator();
				}
				break;
			case 2:
				_localctx = new FactorVarContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(668);
				variable();
				}
				break;
			case 3:
				_localctx = new FactorExprContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(669);
				match(LPAREN);
				setState(670);
				expression();
				setState(671);
				match(RPAREN);
				}
				break;
			case 4:
				_localctx = new FactorUnConstContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(673);
				unsignedConstant();
				}
				break;
			case 5:
				_localctx = new FactorSetContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(674);
				set_();
				}
				break;
			case 6:
				_localctx = new NotFactorContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(675);
				match(NOT);
				setState(676);
				factor();
				}
				break;
			case 7:
				_localctx = new FactorBoolContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(677);
				bool_();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnsignedConstantContext extends ParserRuleContext {
		public UnsignedNumberContext unsignedNumber() {
			return getRuleContext(UnsignedNumberContext.class,0);
		}
		public ConstantChrContext constantChr() {
			return getRuleContext(ConstantChrContext.class,0);
		}
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public TerminalNode NIL() { return getToken(PascalParser.NIL, 0); }
		public UnsignedConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unsignedConstant; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitUnsignedConstant(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnsignedConstantContext unsignedConstant() throws RecognitionException {
		UnsignedConstantContext _localctx = new UnsignedConstantContext(_ctx, getState());
		enterRule(_localctx, 140, RULE_unsignedConstant);
		try {
			setState(684);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NUM_INT:
			case NUM_REAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(680);
				unsignedNumber();
				}
				break;
			case CHR:
				enterOuterAlt(_localctx, 2);
				{
				setState(681);
				constantChr();
				}
				break;
			case STRING_LITERAL:
				enterOuterAlt(_localctx, 3);
				{
				setState(682);
				string();
				}
				break;
			case NIL:
				enterOuterAlt(_localctx, 4);
				{
				setState(683);
				match(NIL);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDesignatorContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(PascalParser.LPAREN, 0); }
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(PascalParser.RPAREN, 0); }
		public FunctionDesignatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDesignator; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitFunctionDesignator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDesignatorContext functionDesignator() throws RecognitionException {
		FunctionDesignatorContext _localctx = new FunctionDesignatorContext(_ctx, getState());
		enterRule(_localctx, 142, RULE_functionDesignator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(686);
			identifier();
			setState(687);
			match(LPAREN);
			setState(688);
			parameterList();
			setState(689);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterListContext extends ParserRuleContext {
		public List<ActualParameterContext> actualParameter() {
			return getRuleContexts(ActualParameterContext.class);
		}
		public ActualParameterContext actualParameter(int i) {
			return getRuleContext(ActualParameterContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(PascalParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(PascalParser.COMMA, i);
		}
		public WriteParametersContext writeParameters() {
			return getRuleContext(WriteParametersContext.class,0);
		}
		public ReadParametersContext readParameters() {
			return getRuleContext(ReadParametersContext.class,0);
		}
		public ParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitParameterList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterListContext parameterList() throws RecognitionException {
		ParameterListContext _localctx = new ParameterListContext(_ctx, getState());
		enterRule(_localctx, 144, RULE_parameterList);
		int _la;
		try {
			setState(701);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,57,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(691);
				actualParameter();
				setState(696);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(692);
					match(COMMA);
					setState(693);
					actualParameter();
					}
					}
					setState(698);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(699);
				writeParameters();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(700);
				readParameters();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Set_Context extends ParserRuleContext {
		public TerminalNode LBRACK() { return getToken(PascalParser.LBRACK, 0); }
		public ElementListContext elementList() {
			return getRuleContext(ElementListContext.class,0);
		}
		public TerminalNode RBRACK() { return getToken(PascalParser.RBRACK, 0); }
		public TerminalNode LBRACK2() { return getToken(PascalParser.LBRACK2, 0); }
		public TerminalNode RBRACK2() { return getToken(PascalParser.RBRACK2, 0); }
		public Set_Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_set_; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitSet_(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Set_Context set_() throws RecognitionException {
		Set_Context _localctx = new Set_Context(_ctx, getState());
		enterRule(_localctx, 146, RULE_set_);
		try {
			setState(711);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACK:
				enterOuterAlt(_localctx, 1);
				{
				setState(703);
				match(LBRACK);
				setState(704);
				elementList();
				setState(705);
				match(RBRACK);
				}
				break;
			case LBRACK2:
				enterOuterAlt(_localctx, 2);
				{
				setState(707);
				match(LBRACK2);
				setState(708);
				elementList();
				setState(709);
				match(RBRACK2);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementListContext extends ParserRuleContext {
		public List<ElementContext> element() {
			return getRuleContexts(ElementContext.class);
		}
		public ElementContext element(int i) {
			return getRuleContext(ElementContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(PascalParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(PascalParser.COMMA, i);
		}
		public ElementListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitElementList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementListContext elementList() throws RecognitionException {
		ElementListContext _localctx = new ElementListContext(_ctx, getState());
		enterRule(_localctx, 148, RULE_elementList);
		int _la;
		try {
			setState(722);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CHR:
			case NIL:
			case NOT:
			case PLUS:
			case MINUS:
			case LPAREN:
			case LBRACK:
			case LBRACK2:
			case AT:
			case TRUE:
			case FALSE:
			case IDENT:
			case STRING_LITERAL:
			case NUM_INT:
			case NUM_REAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(713);
				element();
				setState(718);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(714);
					match(COMMA);
					setState(715);
					element();
					}
					}
					setState(720);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case RBRACK:
			case RBRACK2:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode DOTDOT() { return getToken(PascalParser.DOTDOT, 0); }
		public ElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_element; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementContext element() throws RecognitionException {
		ElementContext _localctx = new ElementContext(_ctx, getState());
		enterRule(_localctx, 150, RULE_element);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(724);
			expression();
			setState(727);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DOTDOT) {
				{
				setState(725);
				match(DOTDOT);
				setState(726);
				expression();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProcedureStatementContext extends ParserRuleContext {
		public ReadProcedureStatementContext readProcedureStatement() {
			return getRuleContext(ReadProcedureStatementContext.class,0);
		}
		public WriteProcedureStatementContext writeProcedureStatement() {
			return getRuleContext(WriteProcedureStatementContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(PascalParser.LPAREN, 0); }
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(PascalParser.RPAREN, 0); }
		public ProcedureStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_procedureStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitProcedureStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProcedureStatementContext procedureStatement() throws RecognitionException {
		ProcedureStatementContext _localctx = new ProcedureStatementContext(_ctx, getState());
		enterRule(_localctx, 152, RULE_procedureStatement);
		int _la;
		try {
			setState(738);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case READ:
			case READLN:
				enterOuterAlt(_localctx, 1);
				{
				setState(729);
				readProcedureStatement();
				}
				break;
			case WRITE:
			case WRITELN:
				enterOuterAlt(_localctx, 2);
				{
				setState(730);
				writeProcedureStatement();
				}
				break;
			case IDENT:
				enterOuterAlt(_localctx, 3);
				{
				setState(731);
				identifier();
				setState(736);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LPAREN) {
					{
					setState(732);
					match(LPAREN);
					setState(733);
					parameterList();
					setState(734);
					match(RPAREN);
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WriteProcedureStatementContext extends ParserRuleContext {
		public TerminalNode WRITE() { return getToken(PascalParser.WRITE, 0); }
		public TerminalNode WRITELN() { return getToken(PascalParser.WRITELN, 0); }
		public TerminalNode LPAREN() { return getToken(PascalParser.LPAREN, 0); }
		public WriteParametersContext writeParameters() {
			return getRuleContext(WriteParametersContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(PascalParser.RPAREN, 0); }
		public WriteProcedureStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_writeProcedureStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitWriteProcedureStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WriteProcedureStatementContext writeProcedureStatement() throws RecognitionException {
		WriteProcedureStatementContext _localctx = new WriteProcedureStatementContext(_ctx, getState());
		enterRule(_localctx, 154, RULE_writeProcedureStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(740);
			_la = _input.LA(1);
			if ( !(_la==WRITE || _la==WRITELN) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(745);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(741);
				match(LPAREN);
				setState(742);
				writeParameters();
				setState(743);
				match(RPAREN);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReadProcedureStatementContext extends ParserRuleContext {
		public TerminalNode READ() { return getToken(PascalParser.READ, 0); }
		public TerminalNode READLN() { return getToken(PascalParser.READLN, 0); }
		public TerminalNode LPAREN() { return getToken(PascalParser.LPAREN, 0); }
		public ReadParametersContext readParameters() {
			return getRuleContext(ReadParametersContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(PascalParser.RPAREN, 0); }
		public ReadProcedureStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_readProcedureStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitReadProcedureStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReadProcedureStatementContext readProcedureStatement() throws RecognitionException {
		ReadProcedureStatementContext _localctx = new ReadProcedureStatementContext(_ctx, getState());
		enterRule(_localctx, 156, RULE_readProcedureStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(747);
			_la = _input.LA(1);
			if ( !(_la==READ || _la==READLN) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(752);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(748);
				match(LPAREN);
				setState(749);
				readParameters();
				setState(750);
				match(RPAREN);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WriteParametersContext extends ParserRuleContext {
		public List<OutputValueContext> outputValue() {
			return getRuleContexts(OutputValueContext.class);
		}
		public OutputValueContext outputValue(int i) {
			return getRuleContext(OutputValueContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(PascalParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(PascalParser.COMMA, i);
		}
		public WriteParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_writeParameters; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitWriteParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WriteParametersContext writeParameters() throws RecognitionException {
		WriteParametersContext _localctx = new WriteParametersContext(_ctx, getState());
		enterRule(_localctx, 158, RULE_writeParameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(764);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << CHR) | (1L << NIL) | (1L << NOT) | (1L << PLUS) | (1L << MINUS) | (1L << LPAREN) | (1L << LBRACK) | (1L << LBRACK2))) != 0) || ((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (AT - 66)) | (1L << (TRUE - 66)) | (1L << (FALSE - 66)) | (1L << (IDENT - 66)) | (1L << (STRING_LITERAL - 66)) | (1L << (NUM_INT - 66)) | (1L << (NUM_REAL - 66)))) != 0)) {
				{
				{
				setState(754);
				outputValue();
				setState(759);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(755);
					match(COMMA);
					setState(756);
					outputValue();
					}
					}
					setState(761);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				}
				setState(766);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OutputValueContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COLON() { return getTokens(PascalParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(PascalParser.COLON, i);
		}
		public OutputValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_outputValue; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitOutputValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OutputValueContext outputValue() throws RecognitionException {
		OutputValueContext _localctx = new OutputValueContext(_ctx, getState());
		enterRule(_localctx, 160, RULE_outputValue);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(767);
			expression();
			setState(779);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COLON) {
				{
				{
				setState(768);
				match(COLON);
				setState(769);
				expression();
				setState(774);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,68,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(770);
						match(COLON);
						setState(771);
						expression();
						}
						} 
					}
					setState(776);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,68,_ctx);
				}
				}
				}
				setState(781);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReadParametersContext extends ParserRuleContext {
		public List<InputValueContext> inputValue() {
			return getRuleContexts(InputValueContext.class);
		}
		public InputValueContext inputValue(int i) {
			return getRuleContext(InputValueContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(PascalParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(PascalParser.COMMA, i);
		}
		public ReadParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_readParameters; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitReadParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReadParametersContext readParameters() throws RecognitionException {
		ReadParametersContext _localctx = new ReadParametersContext(_ctx, getState());
		enterRule(_localctx, 162, RULE_readParameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(782);
			inputValue();
			setState(787);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(783);
				match(COMMA);
				setState(784);
				inputValue();
				}
				}
				setState(789);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InputValueContext extends ParserRuleContext {
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public InputValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inputValue; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitInputValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InputValueContext inputValue() throws RecognitionException {
		InputValueContext _localctx = new InputValueContext(_ctx, getState());
		enterRule(_localctx, 164, RULE_inputValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(790);
			variable();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ActualParameterContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<ParameterwidthContext> parameterwidth() {
			return getRuleContexts(ParameterwidthContext.class);
		}
		public ParameterwidthContext parameterwidth(int i) {
			return getRuleContext(ParameterwidthContext.class,i);
		}
		public ActualParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_actualParameter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitActualParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ActualParameterContext actualParameter() throws RecognitionException {
		ActualParameterContext _localctx = new ActualParameterContext(_ctx, getState());
		enterRule(_localctx, 166, RULE_actualParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(792);
			expression();
			setState(796);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COLON) {
				{
				{
				setState(793);
				parameterwidth();
				}
				}
				setState(798);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterwidthContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(PascalParser.COLON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ParameterwidthContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterwidth; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitParameterwidth(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterwidthContext parameterwidth() throws RecognitionException {
		ParameterwidthContext _localctx = new ParameterwidthContext(_ctx, getState());
		enterRule(_localctx, 168, RULE_parameterwidth);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(799);
			match(COLON);
			setState(800);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GotoStatementContext extends ParserRuleContext {
		public TerminalNode GOTO() { return getToken(PascalParser.GOTO, 0); }
		public LabelContext label() {
			return getRuleContext(LabelContext.class,0);
		}
		public GotoStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gotoStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitGotoStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GotoStatementContext gotoStatement() throws RecognitionException {
		GotoStatementContext _localctx = new GotoStatementContext(_ctx, getState());
		enterRule(_localctx, 170, RULE_gotoStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(802);
			match(GOTO);
			setState(803);
			label();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EmptyStatementContext extends ParserRuleContext {
		public EmptyStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_emptyStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitEmptyStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EmptyStatementContext emptyStatement() throws RecognitionException {
		EmptyStatementContext _localctx = new EmptyStatementContext(_ctx, getState());
		enterRule(_localctx, 172, RULE_emptyStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Empty_Context extends ParserRuleContext {
		public Empty_Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_empty_; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitEmpty_(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Empty_Context empty_() throws RecognitionException {
		Empty_Context _localctx = new Empty_Context(_ctx, getState());
		enterRule(_localctx, 174, RULE_empty_);
		try {
			enterOuterAlt(_localctx, 1);
			{
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StructuredStatementContext extends ParserRuleContext {
		public CompoundStatementContext compoundStatement() {
			return getRuleContext(CompoundStatementContext.class,0);
		}
		public ConditionalStatementContext conditionalStatement() {
			return getRuleContext(ConditionalStatementContext.class,0);
		}
		public RepetetiveStatementContext repetetiveStatement() {
			return getRuleContext(RepetetiveStatementContext.class,0);
		}
		public WithStatementContext withStatement() {
			return getRuleContext(WithStatementContext.class,0);
		}
		public StructuredStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_structuredStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitStructuredStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StructuredStatementContext structuredStatement() throws RecognitionException {
		StructuredStatementContext _localctx = new StructuredStatementContext(_ctx, getState());
		enterRule(_localctx, 176, RULE_structuredStatement);
		try {
			setState(813);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BEGIN:
				enterOuterAlt(_localctx, 1);
				{
				setState(809);
				compoundStatement();
				}
				break;
			case CASE:
			case IF:
				enterOuterAlt(_localctx, 2);
				{
				setState(810);
				conditionalStatement();
				}
				break;
			case FOR:
			case REPEAT:
			case WHILE:
				enterOuterAlt(_localctx, 3);
				{
				setState(811);
				repetetiveStatement();
				}
				break;
			case WITH:
				enterOuterAlt(_localctx, 4);
				{
				setState(812);
				withStatement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CompoundStatementContext extends ParserRuleContext {
		public TerminalNode BEGIN() { return getToken(PascalParser.BEGIN, 0); }
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public TerminalNode END() { return getToken(PascalParser.END, 0); }
		public CompoundStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compoundStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitCompoundStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompoundStatementContext compoundStatement() throws RecognitionException {
		CompoundStatementContext _localctx = new CompoundStatementContext(_ctx, getState());
		enterRule(_localctx, 178, RULE_compoundStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(815);
			match(BEGIN);
			setState(816);
			statements();
			setState(817);
			match(END);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementsContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public List<TerminalNode> SEMI() { return getTokens(PascalParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(PascalParser.SEMI, i);
		}
		public StatementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statements; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitStatements(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementsContext statements() throws RecognitionException {
		StatementsContext _localctx = new StatementsContext(_ctx, getState());
		enterRule(_localctx, 180, RULE_statements);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(819);
			statement();
			setState(824);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,73,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(820);
					match(SEMI);
					setState(821);
					statement();
					}
					} 
				}
				setState(826);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,73,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionalStatementContext extends ParserRuleContext {
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public CaseStatementContext caseStatement() {
			return getRuleContext(CaseStatementContext.class,0);
		}
		public ConditionalStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditionalStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitConditionalStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionalStatementContext conditionalStatement() throws RecognitionException {
		ConditionalStatementContext _localctx = new ConditionalStatementContext(_ctx, getState());
		enterRule(_localctx, 182, RULE_conditionalStatement);
		try {
			setState(829);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IF:
				enterOuterAlt(_localctx, 1);
				{
				setState(827);
				ifStatement();
				}
				break;
			case CASE:
				enterOuterAlt(_localctx, 2);
				{
				setState(828);
				caseStatement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfStatementContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(PascalParser.IF, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode THEN() { return getToken(PascalParser.THEN, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(PascalParser.ELSE, 0); }
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 184, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(831);
			match(IF);
			setState(832);
			expression();
			setState(833);
			match(THEN);
			setState(834);
			statement();
			setState(837);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,75,_ctx) ) {
			case 1:
				{
				setState(835);
				match(ELSE);
				setState(836);
				statement();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CaseStatementContext extends ParserRuleContext {
		public TerminalNode CASE() { return getToken(PascalParser.CASE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode OF() { return getToken(PascalParser.OF, 0); }
		public List<CaseListElementContext> caseListElement() {
			return getRuleContexts(CaseListElementContext.class);
		}
		public CaseListElementContext caseListElement(int i) {
			return getRuleContext(CaseListElementContext.class,i);
		}
		public TerminalNode END() { return getToken(PascalParser.END, 0); }
		public List<TerminalNode> SEMI() { return getTokens(PascalParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(PascalParser.SEMI, i);
		}
		public TerminalNode ELSE() { return getToken(PascalParser.ELSE, 0); }
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public CaseStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_caseStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitCaseStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CaseStatementContext caseStatement() throws RecognitionException {
		CaseStatementContext _localctx = new CaseStatementContext(_ctx, getState());
		enterRule(_localctx, 186, RULE_caseStatement);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(839);
			match(CASE);
			setState(840);
			expression();
			setState(841);
			match(OF);
			setState(842);
			caseListElement();
			setState(847);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,76,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(843);
					match(SEMI);
					setState(844);
					caseListElement();
					}
					} 
				}
				setState(849);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,76,_ctx);
			}
			setState(853);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,77,_ctx) ) {
			case 1:
				{
				setState(850);
				match(SEMI);
				setState(851);
				match(ELSE);
				setState(852);
				statements();
				}
				break;
			}
			setState(856);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMI) {
				{
				setState(855);
				match(SEMI);
				}
			}

			setState(858);
			match(END);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CaseListElementContext extends ParserRuleContext {
		public ConstListContext constList() {
			return getRuleContext(ConstListContext.class,0);
		}
		public TerminalNode COLON() { return getToken(PascalParser.COLON, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public CaseListElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_caseListElement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitCaseListElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CaseListElementContext caseListElement() throws RecognitionException {
		CaseListElementContext _localctx = new CaseListElementContext(_ctx, getState());
		enterRule(_localctx, 188, RULE_caseListElement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(860);
			constList();
			setState(861);
			match(COLON);
			setState(862);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RepetetiveStatementContext extends ParserRuleContext {
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public RepeatStatementContext repeatStatement() {
			return getRuleContext(RepeatStatementContext.class,0);
		}
		public ForStatementContext forStatement() {
			return getRuleContext(ForStatementContext.class,0);
		}
		public RepetetiveStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_repetetiveStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitRepetetiveStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RepetetiveStatementContext repetetiveStatement() throws RecognitionException {
		RepetetiveStatementContext _localctx = new RepetetiveStatementContext(_ctx, getState());
		enterRule(_localctx, 190, RULE_repetetiveStatement);
		try {
			setState(867);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case WHILE:
				enterOuterAlt(_localctx, 1);
				{
				setState(864);
				whileStatement();
				}
				break;
			case REPEAT:
				enterOuterAlt(_localctx, 2);
				{
				setState(865);
				repeatStatement();
				}
				break;
			case FOR:
				enterOuterAlt(_localctx, 3);
				{
				setState(866);
				forStatement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhileStatementContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(PascalParser.WHILE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode DO() { return getToken(PascalParser.DO, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public WhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 192, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(869);
			match(WHILE);
			setState(870);
			expression();
			setState(871);
			match(DO);
			setState(872);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RepeatStatementContext extends ParserRuleContext {
		public TerminalNode REPEAT() { return getToken(PascalParser.REPEAT, 0); }
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public TerminalNode UNTIL() { return getToken(PascalParser.UNTIL, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public RepeatStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_repeatStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitRepeatStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RepeatStatementContext repeatStatement() throws RecognitionException {
		RepeatStatementContext _localctx = new RepeatStatementContext(_ctx, getState());
		enterRule(_localctx, 194, RULE_repeatStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(874);
			match(REPEAT);
			setState(875);
			statements();
			setState(876);
			match(UNTIL);
			setState(877);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForStatementContext extends ParserRuleContext {
		public TerminalNode FOR() { return getToken(PascalParser.FOR, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(PascalParser.ASSIGN, 0); }
		public ForListContext forList() {
			return getRuleContext(ForListContext.class,0);
		}
		public TerminalNode DO() { return getToken(PascalParser.DO, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ForStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitForStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStatementContext forStatement() throws RecognitionException {
		ForStatementContext _localctx = new ForStatementContext(_ctx, getState());
		enterRule(_localctx, 196, RULE_forStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(879);
			match(FOR);
			setState(880);
			identifier();
			setState(881);
			match(ASSIGN);
			setState(882);
			forList();
			setState(883);
			match(DO);
			setState(884);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForListContext extends ParserRuleContext {
		public InitialValueContext initialValue() {
			return getRuleContext(InitialValueContext.class,0);
		}
		public FinalValueContext finalValue() {
			return getRuleContext(FinalValueContext.class,0);
		}
		public TerminalNode TO() { return getToken(PascalParser.TO, 0); }
		public TerminalNode DOWNTO() { return getToken(PascalParser.DOWNTO, 0); }
		public ForListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitForList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForListContext forList() throws RecognitionException {
		ForListContext _localctx = new ForListContext(_ctx, getState());
		enterRule(_localctx, 198, RULE_forList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(886);
			initialValue();
			setState(887);
			_la = _input.LA(1);
			if ( !(_la==DOWNTO || _la==TO) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(888);
			finalValue();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InitialValueContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public InitialValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_initialValue; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitInitialValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InitialValueContext initialValue() throws RecognitionException {
		InitialValueContext _localctx = new InitialValueContext(_ctx, getState());
		enterRule(_localctx, 200, RULE_initialValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(890);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FinalValueContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public FinalValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_finalValue; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitFinalValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FinalValueContext finalValue() throws RecognitionException {
		FinalValueContext _localctx = new FinalValueContext(_ctx, getState());
		enterRule(_localctx, 202, RULE_finalValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(892);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WithStatementContext extends ParserRuleContext {
		public TerminalNode WITH() { return getToken(PascalParser.WITH, 0); }
		public RecordVariableListContext recordVariableList() {
			return getRuleContext(RecordVariableListContext.class,0);
		}
		public TerminalNode DO() { return getToken(PascalParser.DO, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public WithStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_withStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitWithStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WithStatementContext withStatement() throws RecognitionException {
		WithStatementContext _localctx = new WithStatementContext(_ctx, getState());
		enterRule(_localctx, 204, RULE_withStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(894);
			match(WITH);
			setState(895);
			recordVariableList();
			setState(896);
			match(DO);
			setState(897);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RecordVariableListContext extends ParserRuleContext {
		public List<VariableContext> variable() {
			return getRuleContexts(VariableContext.class);
		}
		public VariableContext variable(int i) {
			return getRuleContext(VariableContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(PascalParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(PascalParser.COMMA, i);
		}
		public RecordVariableListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_recordVariableList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PascalVisitor ) return ((PascalVisitor<? extends T>)visitor).visitRecordVariableList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RecordVariableListContext recordVariableList() throws RecognitionException {
		RecordVariableListContext _localctx = new RecordVariableListContext(_ctx, getState());
		enterRule(_localctx, 206, RULE_recordVariableList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(899);
			variable();
			setState(904);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(900);
				match(COMMA);
				setState(901);
				variable();
				}
				}
				setState(906);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3W\u038e\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\tT"+
		"\4U\tU\4V\tV\4W\tW\4X\tX\4Y\tY\4Z\tZ\4[\t[\4\\\t\\\4]\t]\4^\t^\4_\t_\4"+
		"`\t`\4a\ta\4b\tb\4c\tc\4d\td\4e\te\4f\tf\4g\tg\4h\th\4i\ti\3\2\3\2\5\2"+
		"\u00d5\n\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\5\3\u00e0\n\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\5\3\u00e8\n\3\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\7\5\u00f3"+
		"\n\5\f\5\16\5\u00f6\13\5\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\7\7\u0102"+
		"\n\7\f\7\16\7\u0105\13\7\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\6\t\u010f\n\t"+
		"\r\t\16\t\u0110\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u0127\n\f\3\r\3\r\3\16\3\16\3\17\3"+
		"\17\5\17\u012f\n\17\3\20\3\20\3\21\3\21\3\21\3\21\6\21\u0137\n\21\r\21"+
		"\16\21\u0138\3\22\3\22\3\22\3\22\3\23\3\23\5\23\u0141\n\23\3\23\3\23\3"+
		"\23\3\24\3\24\5\24\u0148\n\24\3\25\3\25\3\25\5\25\u014d\n\25\3\26\3\26"+
		"\3\26\3\26\5\26\u0153\n\26\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\31"+
		"\3\31\5\31\u015f\n\31\3\32\3\32\3\32\5\32\u0164\n\32\3\33\3\33\3\33\3"+
		"\33\5\33\u016a\n\33\3\34\3\34\3\34\3\34\5\34\u0170\n\34\3\34\3\34\3\35"+
		"\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\5\35"+
		"\u0182\n\35\3\36\3\36\3\36\7\36\u0187\n\36\f\36\16\36\u018a\13\36\3\37"+
		"\3\37\3 \3 \3!\3!\5!\u0192\n!\3!\3!\3\"\3\"\3\"\5\"\u0199\n\"\3\"\5\""+
		"\u019c\n\"\3\"\3\"\5\"\u01a0\n\"\5\"\u01a2\n\"\3#\3#\3#\7#\u01a7\n#\f"+
		"#\16#\u01aa\13#\3$\3$\3$\3$\3%\3%\3%\3%\3%\3%\7%\u01b6\n%\f%\16%\u01b9"+
		"\13%\3&\3&\3&\3&\3&\5&\u01c0\n&\3\'\3\'\3\'\3\'\3\'\3\'\3(\3(\3(\3(\3"+
		")\3)\3*\3*\3*\3*\5*\u01d2\n*\3+\3+\3+\3,\3,\3,\3,\7,\u01db\n,\f,\16,\u01de"+
		"\13,\3,\3,\3-\3-\3-\3-\3.\3.\3.\3/\3/\5/\u01eb\n/\3\60\3\60\3\61\3\61"+
		"\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\5\61\u01fb\n\61\3\61"+
		"\3\61\3\61\5\61\u0200\n\61\3\62\3\62\3\62\3\62\7\62\u0206\n\62\f\62\16"+
		"\62\u0209\13\62\3\62\3\62\3\63\3\63\3\63\3\63\3\63\5\63\u0212\n\63\3\64"+
		"\3\64\3\64\5\64\u0217\n\64\3\64\3\64\3\64\3\65\3\65\3\65\5\65\u021f\n"+
		"\65\3\66\3\66\3\66\3\66\3\67\3\67\3\67\7\67\u0228\n\67\f\67\16\67\u022b"+
		"\13\67\38\38\38\78\u0230\n8\f8\168\u0233\138\39\39\39\39\39\39\39\39\3"+
		"9\39\39\39\59\u0241\n9\39\39\39\39\39\59\u0248\n9\3:\3:\3;\3;\3;\3;\3"+
		";\5;\u0251\n;\3<\3<\5<\u0255\n<\3=\3=\3=\3=\5=\u025b\n=\3>\3>\3>\3>\3"+
		"?\3?\3?\3?\7?\u0265\n?\f?\16?\u0268\13?\3@\3@\3@\5@\u026d\n@\3A\3A\3A"+
		"\3A\7A\u0273\nA\fA\16A\u0276\13A\3A\3A\3A\3A\3A\3A\7A\u027e\nA\fA\16A"+
		"\u0281\13A\3A\3A\5A\u0285\nA\3B\3B\3B\3C\3C\3C\5C\u028d\nC\3D\3D\3D\5"+
		"D\u0292\nD\3E\3E\3E\5E\u0297\nE\3F\5F\u029a\nF\3F\3F\3G\3G\3G\3G\3G\3"+
		"G\3G\3G\3G\3G\3G\5G\u02a9\nG\3H\3H\3H\3H\5H\u02af\nH\3I\3I\3I\3I\3I\3"+
		"J\3J\3J\7J\u02b9\nJ\fJ\16J\u02bc\13J\3J\3J\5J\u02c0\nJ\3K\3K\3K\3K\3K"+
		"\3K\3K\3K\5K\u02ca\nK\3L\3L\3L\7L\u02cf\nL\fL\16L\u02d2\13L\3L\5L\u02d5"+
		"\nL\3M\3M\3M\5M\u02da\nM\3N\3N\3N\3N\3N\3N\3N\5N\u02e3\nN\5N\u02e5\nN"+
		"\3O\3O\3O\3O\3O\5O\u02ec\nO\3P\3P\3P\3P\3P\5P\u02f3\nP\3Q\3Q\3Q\7Q\u02f8"+
		"\nQ\fQ\16Q\u02fb\13Q\7Q\u02fd\nQ\fQ\16Q\u0300\13Q\3R\3R\3R\3R\3R\7R\u0307"+
		"\nR\fR\16R\u030a\13R\7R\u030c\nR\fR\16R\u030f\13R\3S\3S\3S\7S\u0314\n"+
		"S\fS\16S\u0317\13S\3T\3T\3U\3U\7U\u031d\nU\fU\16U\u0320\13U\3V\3V\3V\3"+
		"W\3W\3W\3X\3X\3Y\3Y\3Z\3Z\3Z\3Z\5Z\u0330\nZ\3[\3[\3[\3[\3\\\3\\\3\\\7"+
		"\\\u0339\n\\\f\\\16\\\u033c\13\\\3]\3]\5]\u0340\n]\3^\3^\3^\3^\3^\3^\5"+
		"^\u0348\n^\3_\3_\3_\3_\3_\3_\7_\u0350\n_\f_\16_\u0353\13_\3_\3_\3_\5_"+
		"\u0358\n_\3_\5_\u035b\n_\3_\3_\3`\3`\3`\3`\3a\3a\3a\5a\u0366\na\3b\3b"+
		"\3b\3b\3b\3c\3c\3c\3c\3c\3d\3d\3d\3d\3d\3d\3d\3e\3e\3e\3e\3f\3f\3g\3g"+
		"\3h\3h\3h\3h\3h\3i\3i\3i\7i\u0389\ni\fi\16i\u038c\13i\3i\2\2j\2\4\6\b"+
		"\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVX"+
		"Z\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084\u0086\u0088\u008a\u008c\u008e\u0090"+
		"\u0092\u0094\u0096\u0098\u009a\u009c\u009e\u00a0\u00a2\u00a4\u00a6\u00a8"+
		"\u00aa\u00ac\u00ae\u00b0\u00b2\u00b4\u00b6\u00b8\u00ba\u00bc\u00be\u00c0"+
		"\u00c2\u00c4\u00c6\u00c8\u00ca\u00cc\u00ce\u00d0\2\13\3\2VW\3\2/\60\7"+
		"\2\n\n\f\f\32\32$$LL\4\2\31\31\67<\4\2  /\60\6\2\7\7\17\17\34\34\61\62"+
		"\3\2\3\4\3\2\5\6\4\2\21\21))\2\u0398\2\u00d2\3\2\2\2\4\u00e7\3\2\2\2\6"+
		"\u00e9\3\2\2\2\b\u00f4\3\2\2\2\n\u00f9\3\2\2\2\f\u00fd\3\2\2\2\16\u0108"+
		"\3\2\2\2\20\u010a\3\2\2\2\22\u0112\3\2\2\2\24\u0116\3\2\2\2\26\u0126\3"+
		"\2\2\2\30\u0128\3\2\2\2\32\u012a\3\2\2\2\34\u012e\3\2\2\2\36\u0130\3\2"+
		"\2\2 \u0132\3\2\2\2\"\u013a\3\2\2\2$\u013e\3\2\2\2&\u0145\3\2\2\2(\u014c"+
		"\3\2\2\2*\u0152\3\2\2\2,\u0154\3\2\2\2.\u0158\3\2\2\2\60\u015e\3\2\2\2"+
		"\62\u0163\3\2\2\2\64\u0169\3\2\2\2\66\u016b\3\2\2\28\u0181\3\2\2\2:\u0183"+
		"\3\2\2\2<\u018b\3\2\2\2>\u018d\3\2\2\2@\u018f\3\2\2\2B\u01a1\3\2\2\2D"+
		"\u01a3\3\2\2\2F\u01ab\3\2\2\2H\u01af\3\2\2\2J\u01bf\3\2\2\2L\u01c1\3\2"+
		"\2\2N\u01c7\3\2\2\2P\u01cb\3\2\2\2R\u01d1\3\2\2\2T\u01d3\3\2\2\2V\u01d6"+
		"\3\2\2\2X\u01e1\3\2\2\2Z\u01e5\3\2\2\2\\\u01ea\3\2\2\2^\u01ec\3\2\2\2"+
		"`\u01ff\3\2\2\2b\u0201\3\2\2\2d\u0211\3\2\2\2f\u0213\3\2\2\2h\u021b\3"+
		"\2\2\2j\u0220\3\2\2\2l\u0224\3\2\2\2n\u022c\3\2\2\2p\u0247\3\2\2\2r\u0249"+
		"\3\2\2\2t\u0250\3\2\2\2v\u0254\3\2\2\2x\u025a\3\2\2\2z\u025c\3\2\2\2|"+
		"\u0260\3\2\2\2~\u026c\3\2\2\2\u0080\u0284\3\2\2\2\u0082\u0286\3\2\2\2"+
		"\u0084\u0289\3\2\2\2\u0086\u028e\3\2\2\2\u0088\u0293\3\2\2\2\u008a\u0299"+
		"\3\2\2\2\u008c\u02a8\3\2\2\2\u008e\u02ae\3\2\2\2\u0090\u02b0\3\2\2\2\u0092"+
		"\u02bf\3\2\2\2\u0094\u02c9\3\2\2\2\u0096\u02d4\3\2\2\2\u0098\u02d6\3\2"+
		"\2\2\u009a\u02e4\3\2\2\2\u009c\u02e6\3\2\2\2\u009e\u02ed\3\2\2\2\u00a0"+
		"\u02fe\3\2\2\2\u00a2\u0301\3\2\2\2\u00a4\u0310\3\2\2\2\u00a6\u0318\3\2"+
		"\2\2\u00a8\u031a\3\2\2\2\u00aa\u0321\3\2\2\2\u00ac\u0324\3\2\2\2\u00ae"+
		"\u0327\3\2\2\2\u00b0\u0329\3\2\2\2\u00b2\u032f\3\2\2\2\u00b4\u0331\3\2"+
		"\2\2\u00b6\u0335\3\2\2\2\u00b8\u033f\3\2\2\2\u00ba\u0341\3\2\2\2\u00bc"+
		"\u0349\3\2\2\2\u00be\u035e\3\2\2\2\u00c0\u0365\3\2\2\2\u00c2\u0367\3\2"+
		"\2\2\u00c4\u036c\3\2\2\2\u00c6\u0371\3\2\2\2\u00c8\u0378\3\2\2\2\u00ca"+
		"\u037c\3\2\2\2\u00cc\u037e\3\2\2\2\u00ce\u0380\3\2\2\2\u00d0\u0385\3\2"+
		"\2\2\u00d2\u00d4\5\4\3\2\u00d3\u00d5\7J\2\2\u00d4\u00d3\3\2\2\2\u00d4"+
		"\u00d5\3\2\2\2\u00d5\u00d6\3\2\2\2\u00d6\u00d7\5\b\5\2\u00d7\u00d8\7E"+
		"\2\2\u00d8\3\3\2\2\2\u00d9\u00da\7#\2\2\u00da\u00df\5\6\4\2\u00db\u00dc"+
		"\7=\2\2\u00dc\u00dd\5l\67\2\u00dd\u00de\7>\2\2\u00de\u00e0\3\2\2\2\u00df"+
		"\u00db\3\2\2\2\u00df\u00e0\3\2\2\2\u00e0\u00e1\3\2\2\2\u00e1\u00e2\7\65"+
		"\2\2\u00e2\u00e8\3\2\2\2\u00e3\u00e4\7I\2\2\u00e4\u00e5\5\6\4\2\u00e5"+
		"\u00e6\7\65\2\2\u00e6\u00e8\3\2\2\2\u00e7\u00d9\3\2\2\2\u00e7\u00e3\3"+
		"\2\2\2\u00e8\5\3\2\2\2\u00e9\u00ea\7T\2\2\u00ea\7\3\2\2\2\u00eb\u00f3"+
		"\5\f\7\2\u00ec\u00f3\5\20\t\2\u00ed\u00f3\5 \21\2\u00ee\u00f3\5V,\2\u00ef"+
		"\u00f3\5Z.\2\u00f0\u00f3\5\n\6\2\u00f1\u00f3\7M\2\2\u00f2\u00eb\3\2\2"+
		"\2\u00f2\u00ec\3\2\2\2\u00f2\u00ed\3\2\2\2\u00f2\u00ee\3\2\2\2\u00f2\u00ef"+
		"\3\2\2\2\u00f2\u00f0\3\2\2\2\u00f2\u00f1\3\2\2\2\u00f3\u00f6\3\2\2\2\u00f4"+
		"\u00f2\3\2\2\2\u00f4\u00f5\3\2\2\2\u00f5\u00f7\3\2\2\2\u00f6\u00f4\3\2"+
		"\2\2\u00f7\u00f8\5\u00b4[\2\u00f8\t\3\2\2\2\u00f9\u00fa\7K\2\2\u00fa\u00fb"+
		"\5l\67\2\u00fb\u00fc\7\65\2\2\u00fc\13\3\2\2\2\u00fd\u00fe\7\33\2\2\u00fe"+
		"\u0103\5\16\b\2\u00ff\u0100\7\64\2\2\u0100\u0102\5\16\b\2\u0101\u00ff"+
		"\3\2\2\2\u0102\u0105\3\2\2\2\u0103\u0101\3\2\2\2\u0103\u0104\3\2\2\2\u0104"+
		"\u0106\3\2\2\2\u0105\u0103\3\2\2\2\u0106\u0107\7\65\2\2\u0107\r\3\2\2"+
		"\2\u0108\u0109\7V\2\2\u0109\17\3\2\2\2\u010a\u010e\7\16\2\2\u010b\u010c"+
		"\5\22\n\2\u010c\u010d\7\65\2\2\u010d\u010f\3\2\2\2\u010e\u010b\3\2\2\2"+
		"\u010f\u0110\3\2\2\2\u0110\u010e\3\2\2\2\u0110\u0111\3\2\2\2\u0111\21"+
		"\3\2\2\2\u0112\u0113\5\6\4\2\u0113\u0114\7\67\2\2\u0114\u0115\5\26\f\2"+
		"\u0115\23\3\2\2\2\u0116\u0117\7\r\2\2\u0117\u0118\7=\2\2\u0118\u0119\7"+
		"V\2\2\u0119\u011a\7>\2\2\u011a\25\3\2\2\2\u011b\u0127\5\30\r\2\u011c\u011d"+
		"\5\32\16\2\u011d\u011e\5\30\r\2\u011e\u0127\3\2\2\2\u011f\u0127\5\6\4"+
		"\2\u0120\u0121\5\32\16\2\u0121\u0122\5\6\4\2\u0122\u0127\3\2\2\2\u0123"+
		"\u0127\5\36\20\2\u0124\u0127\5\24\13\2\u0125\u0127\5\34\17\2\u0126\u011b"+
		"\3\2\2\2\u0126\u011c\3\2\2\2\u0126\u011f\3\2\2\2\u0126\u0120\3\2\2\2\u0126"+
		"\u0123\3\2\2\2\u0126\u0124\3\2\2\2\u0126\u0125\3\2\2\2\u0127\27\3\2\2"+
		"\2\u0128\u0129\t\2\2\2\u0129\31\3\2\2\2\u012a\u012b\t\3\2\2\u012b\33\3"+
		"\2\2\2\u012c\u012f\7N\2\2\u012d\u012f\7O\2\2\u012e\u012c\3\2\2\2\u012e"+
		"\u012d\3\2\2\2\u012f\35\3\2\2\2\u0130\u0131\7U\2\2\u0131\37\3\2\2\2\u0132"+
		"\u0136\7*\2\2\u0133\u0134\5\"\22\2\u0134\u0135\7\65\2\2\u0135\u0137\3"+
		"\2\2\2\u0136\u0133\3\2\2\2\u0137\u0138\3\2\2\2\u0138\u0136\3\2\2\2\u0138"+
		"\u0139\3\2\2\2\u0139!\3\2\2\2\u013a\u013b\5\6\4\2\u013b\u013c\7\67\2\2"+
		"\u013c\u013d\5(\25\2\u013d#\3\2\2\2\u013e\u0140\7\26\2\2\u013f\u0141\5"+
		"b\62\2\u0140\u013f\3\2\2\2\u0140\u0141\3\2\2\2\u0141\u0142\3\2\2\2\u0142"+
		"\u0143\7\66\2\2\u0143\u0144\5r:\2\u0144%\3\2\2\2\u0145\u0147\7\"\2\2\u0146"+
		"\u0148\5b\62\2\u0147\u0146\3\2\2\2\u0147\u0148\3\2\2\2\u0148\'\3\2\2\2"+
		"\u0149\u014d\5*\26\2\u014a\u014d\5\62\32\2\u014b\u014d\5T+\2\u014c\u0149"+
		"\3\2\2\2\u014c\u014a\3\2\2\2\u014c\u014b\3\2\2\2\u014d)\3\2\2\2\u014e"+
		"\u0153\5,\27\2\u014f\u0153\5.\30\2\u0150\u0153\5\60\31\2\u0151\u0153\5"+
		"\66\34\2\u0152\u014e\3\2\2\2\u0152\u014f\3\2\2\2\u0152\u0150\3\2\2\2\u0152"+
		"\u0151\3\2\2\2\u0153+\3\2\2\2\u0154\u0155\7=\2\2\u0155\u0156\5l\67\2\u0156"+
		"\u0157\7>\2\2\u0157-\3\2\2\2\u0158\u0159\5\26\f\2\u0159\u015a\7F\2\2\u015a"+
		"\u015b\5\26\f\2\u015b/\3\2\2\2\u015c\u015f\5\6\4\2\u015d\u015f\t\4\2\2"+
		"\u015e\u015c\3\2\2\2\u015e\u015d\3\2\2\2\u015f\61\3\2\2\2\u0160\u0161"+
		"\7!\2\2\u0161\u0164\5\64\33\2\u0162\u0164\5\64\33\2\u0163\u0160\3\2\2"+
		"\2\u0163\u0162\3\2\2\2\u0164\63\3\2\2\2\u0165\u016a\58\35\2\u0166\u016a"+
		"\5@!\2\u0167\u016a\5N(\2\u0168\u016a\5R*\2\u0169\u0165\3\2\2\2\u0169\u0166"+
		"\3\2\2\2\u0169\u0167\3\2\2\2\u0169\u0168\3\2\2\2\u016a\65\3\2\2\2\u016b"+
		"\u016c\7L\2\2\u016c\u016f\7?\2\2\u016d\u0170\5\6\4\2\u016e\u0170\5\30"+
		"\r\2\u016f\u016d\3\2\2\2\u016f\u016e\3\2\2\2\u0170\u0171\3\2\2\2\u0171"+
		"\u0172\7A\2\2\u0172\67\3\2\2\2\u0173\u0174\7\b\2\2\u0174\u0175\7?\2\2"+
		"\u0175\u0176\5:\36\2\u0176\u0177\7A\2\2\u0177\u0178\7\37\2\2\u0178\u0179"+
		"\5> \2\u0179\u0182\3\2\2\2\u017a\u017b\7\b\2\2\u017b\u017c\7@\2\2\u017c"+
		"\u017d\5:\36\2\u017d\u017e\7B\2\2\u017e\u017f\7\37\2\2\u017f\u0180\5>"+
		" \2\u0180\u0182\3\2\2\2\u0181\u0173\3\2\2\2\u0181\u017a\3\2\2\2\u0182"+
		"9\3\2\2\2\u0183\u0188\5<\37\2\u0184\u0185\7\64\2\2\u0185\u0187\5<\37\2"+
		"\u0186\u0184\3\2\2\2\u0187\u018a\3\2\2\2\u0188\u0186\3\2\2\2\u0188\u0189"+
		"\3\2\2\2\u0189;\3\2\2\2\u018a\u0188\3\2\2\2\u018b\u018c\5*\26\2\u018c"+
		"=\3\2\2\2\u018d\u018e\5(\25\2\u018e?\3\2\2\2\u018f\u0191\7%\2\2\u0190"+
		"\u0192\5B\"\2\u0191\u0190\3\2\2\2\u0191\u0192\3\2\2\2\u0192\u0193\3\2"+
		"\2\2\u0193\u0194\7\23\2\2\u0194A\3\2\2\2\u0195\u0198\5D#\2\u0196\u0197"+
		"\7\65\2\2\u0197\u0199\5H%\2\u0198\u0196\3\2\2\2\u0198\u0199\3\2\2\2\u0199"+
		"\u019b\3\2\2\2\u019a\u019c\7\65\2\2\u019b\u019a\3\2\2\2\u019b\u019c\3"+
		"\2\2\2\u019c\u01a2\3\2\2\2\u019d\u019f\5H%\2\u019e\u01a0\7\65\2\2\u019f"+
		"\u019e\3\2\2\2\u019f\u01a0\3\2\2\2\u01a0\u01a2\3\2\2\2\u01a1\u0195\3\2"+
		"\2\2\u01a1\u019d\3\2\2\2\u01a2C\3\2\2\2\u01a3\u01a8\5F$\2\u01a4\u01a5"+
		"\7\65\2\2\u01a5\u01a7\5F$\2\u01a6\u01a4\3\2\2\2\u01a7\u01aa\3\2\2\2\u01a8"+
		"\u01a6\3\2\2\2\u01a8\u01a9\3\2\2\2\u01a9E\3\2\2\2\u01aa\u01a8\3\2\2\2"+
		"\u01ab\u01ac\5l\67\2\u01ac\u01ad\7\66\2\2\u01ad\u01ae\5(\25\2\u01aeG\3"+
		"\2\2\2\u01af\u01b0\7\13\2\2\u01b0\u01b1\5J&\2\u01b1\u01b2\7\37\2\2\u01b2"+
		"\u01b7\5L\'\2\u01b3\u01b4\7\65\2\2\u01b4\u01b6\5L\'\2\u01b5\u01b3\3\2"+
		"\2\2\u01b6\u01b9\3\2\2\2\u01b7\u01b5\3\2\2\2\u01b7\u01b8\3\2\2\2\u01b8"+
		"I\3\2\2\2\u01b9\u01b7\3\2\2\2\u01ba\u01bb\5\6\4\2\u01bb\u01bc\7\66\2\2"+
		"\u01bc\u01bd\5\60\31\2\u01bd\u01c0\3\2\2\2\u01be\u01c0\5\60\31\2\u01bf"+
		"\u01ba\3\2\2\2\u01bf\u01be\3\2\2\2\u01c0K\3\2\2\2\u01c1\u01c2\5n8\2\u01c2"+
		"\u01c3\7\66\2\2\u01c3\u01c4\7=\2\2\u01c4\u01c5\5B\"\2\u01c5\u01c6\7>\2"+
		"\2\u01c6M\3\2\2\2\u01c7\u01c8\7\'\2\2\u01c8\u01c9\7\37\2\2\u01c9\u01ca"+
		"\5P)\2\u01caO\3\2\2\2\u01cb\u01cc\5*\26\2\u01ccQ\3\2\2\2\u01cd\u01ce\7"+
		"\24\2\2\u01ce\u01cf\7\37\2\2\u01cf\u01d2\5(\25\2\u01d0\u01d2\7\24\2\2"+
		"\u01d1\u01cd\3\2\2\2\u01d1\u01d0\3\2\2\2\u01d2S\3\2\2\2\u01d3\u01d4\7"+
		"C\2\2\u01d4\u01d5\5\60\31\2\u01d5U\3\2\2\2\u01d6\u01d7\7,\2\2\u01d7\u01dc"+
		"\5X-\2\u01d8\u01d9\7\65\2\2\u01d9\u01db\5X-\2\u01da\u01d8\3\2\2\2\u01db"+
		"\u01de\3\2\2\2\u01dc\u01da\3\2\2\2\u01dc\u01dd\3\2\2\2\u01dd\u01df\3\2"+
		"\2\2\u01de\u01dc\3\2\2\2\u01df\u01e0\7\65\2\2\u01e0W\3\2\2\2\u01e1\u01e2"+
		"\5l\67\2\u01e2\u01e3\7\66\2\2\u01e3\u01e4\5(\25\2\u01e4Y\3\2\2\2\u01e5"+
		"\u01e6\5\\/\2\u01e6\u01e7\7\65\2\2\u01e7[\3\2\2\2\u01e8\u01eb\5`\61\2"+
		"\u01e9\u01eb\5p9\2\u01ea\u01e8\3\2\2\2\u01ea\u01e9\3\2\2\2\u01eb]\3\2"+
		"\2\2\u01ec\u01ed\7P\2\2\u01ed_\3\2\2\2\u01ee\u01ef\5h\65\2\u01ef\u01f0"+
		"\7\65\2\2\u01f0\u01f1\5^\60\2\u01f1\u0200\3\2\2\2\u01f2\u01f3\7\"\2\2"+
		"\u01f3\u01f4\5\6\4\2\u01f4\u01f5\7\65\2\2\u01f5\u01f6\5\b\5\2\u01f6\u0200"+
		"\3\2\2\2\u01f7\u01f8\7\"\2\2\u01f8\u01fa\5\6\4\2\u01f9\u01fb\5b\62\2\u01fa"+
		"\u01f9\3\2\2\2\u01fa\u01fb\3\2\2\2\u01fb\u01fc\3\2\2\2\u01fc\u01fd\7\65"+
		"\2\2\u01fd\u01fe\5\b\5\2\u01fe\u0200\3\2\2\2\u01ff\u01ee\3\2\2\2\u01ff"+
		"\u01f2\3\2\2\2\u01ff\u01f7\3\2\2\2\u0200a\3\2\2\2\u0201\u0202\7=\2\2\u0202"+
		"\u0207\5d\63\2\u0203\u0204\7\65\2\2\u0204\u0206\5d\63\2\u0205\u0203\3"+
		"\2\2\2\u0206\u0209\3\2\2\2\u0207\u0205\3\2\2\2\u0207\u0208\3\2\2\2\u0208"+
		"\u020a\3\2\2\2\u0209\u0207\3\2\2\2\u020a\u020b\7>\2\2\u020bc\3\2\2\2\u020c"+
		"\u0212\5j\66\2\u020d\u020e\7,\2\2\u020e\u0212\5j\66\2\u020f\u0212\5f\64"+
		"\2\u0210\u0212\5h\65\2\u0211\u020c\3\2\2\2\u0211\u020d\3\2\2\2\u0211\u020f"+
		"\3\2\2\2\u0211\u0210\3\2\2\2\u0212e\3\2\2\2\u0213\u0214\7\26\2\2\u0214"+
		"\u0216\5\6\4\2\u0215\u0217\5b\62\2\u0216\u0215\3\2\2\2\u0216\u0217\3\2"+
		"\2\2\u0217\u0218\3\2\2\2\u0218\u0219\7\66\2\2\u0219\u021a\5r:\2\u021a"+
		"g\3\2\2\2\u021b\u021c\7\"\2\2\u021c\u021e\5\6\4\2\u021d\u021f\5b\62\2"+
		"\u021e\u021d\3\2\2\2\u021e\u021f\3\2\2\2\u021fi\3\2\2\2\u0220\u0221\5"+
		"l\67\2\u0221\u0222\7\66\2\2\u0222\u0223\5\60\31\2\u0223k\3\2\2\2\u0224"+
		"\u0229\5\6\4\2\u0225\u0226\7\64\2\2\u0226\u0228\5\6\4\2\u0227\u0225\3"+
		"\2\2\2\u0228\u022b\3\2\2\2\u0229\u0227\3\2\2\2\u0229\u022a\3\2\2\2\u022a"+
		"m\3\2\2\2\u022b\u0229\3\2\2\2\u022c\u0231\5\26\f\2\u022d\u022e\7\64\2"+
		"\2\u022e\u0230\5\26\f\2\u022f\u022d\3\2\2\2\u0230\u0233\3\2\2\2\u0231"+
		"\u022f\3\2\2\2\u0231\u0232\3\2\2\2\u0232o\3\2\2\2\u0233\u0231\3\2\2\2"+
		"\u0234\u0235\5f\64\2\u0235\u0236\7\65\2\2\u0236\u0237\5^\60\2\u0237\u0248"+
		"\3\2\2\2\u0238\u0239\7\26\2\2\u0239\u023a\5\6\4\2\u023a\u023b\7\65\2\2"+
		"\u023b\u023c\5\b\5\2\u023c\u0248\3\2\2\2\u023d\u023e\7\26\2\2\u023e\u0240"+
		"\5\6\4\2\u023f\u0241\5b\62\2\u0240\u023f\3\2\2\2\u0240\u0241\3\2\2\2\u0241"+
		"\u0242\3\2\2\2\u0242\u0243\7\66\2\2\u0243\u0244\5r:\2\u0244\u0245\7\65"+
		"\2\2\u0245\u0246\5\b\5\2\u0246\u0248\3\2\2\2\u0247\u0234\3\2\2\2\u0247"+
		"\u0238\3\2\2\2\u0247\u023d\3\2\2\2\u0248q\3\2\2\2\u0249\u024a\5\60\31"+
		"\2\u024as\3\2\2\2\u024b\u024c\5\16\b\2\u024c\u024d\7\66\2\2\u024d\u024e"+
		"\5v<\2\u024e\u0251\3\2\2\2\u024f\u0251\5v<\2\u0250\u024b\3\2\2\2\u0250"+
		"\u024f\3\2\2\2\u0251u\3\2\2\2\u0252\u0255\5x=\2\u0253\u0255\5\u00b2Z\2"+
		"\u0254\u0252\3\2\2\2\u0254\u0253\3\2\2\2\u0255w\3\2\2\2\u0256\u025b\5"+
		"z>\2\u0257\u025b\5\u009aN\2\u0258\u025b\5\u00acW\2\u0259\u025b\5\u00ae"+
		"X\2\u025a\u0256\3\2\2\2\u025a\u0257\3\2\2\2\u025a\u0258\3\2\2\2\u025a"+
		"\u0259\3\2\2\2\u025by\3\2\2\2\u025c\u025d\5|?\2\u025d\u025e\7\63\2\2\u025e"+
		"\u025f\5\u0084C\2\u025f{\3\2\2\2\u0260\u0266\5~@\2\u0261\u0265\5\u0080"+
		"A\2\u0262\u0265\5\u0082B\2\u0263\u0265\7C\2\2\u0264\u0261\3\2\2\2\u0264"+
		"\u0262\3\2\2\2\u0264\u0263\3\2\2\2\u0265\u0268\3\2\2\2\u0266\u0264\3\2"+
		"\2\2\u0266\u0267\3\2\2\2\u0267}\3\2\2\2\u0268\u0266\3\2\2\2\u0269\u026a"+
		"\7D\2\2\u026a\u026d\5\6\4\2\u026b\u026d\5\6\4\2\u026c\u0269\3\2\2\2\u026c"+
		"\u026b\3\2\2\2\u026d\177\3\2\2\2\u026e\u026f\7?\2\2\u026f\u0274\5\u0084"+
		"C\2\u0270\u0271\7\64\2\2\u0271\u0273\5\u0084C\2\u0272\u0270\3\2\2\2\u0273"+
		"\u0276\3\2\2\2\u0274\u0272\3\2\2\2\u0274\u0275\3\2\2\2\u0275\u0277\3\2"+
		"\2\2\u0276\u0274\3\2\2\2\u0277\u0278\7A\2\2\u0278\u0285\3\2\2\2\u0279"+
		"\u027a\7@\2\2\u027a\u027f\5\u0084C\2\u027b\u027c\7\64\2\2\u027c\u027e"+
		"\5\u0084C\2\u027d\u027b\3\2\2\2\u027e\u0281\3\2\2\2\u027f\u027d\3\2\2"+
		"\2\u027f\u0280\3\2\2\2\u0280\u0282\3\2\2\2\u0281\u027f\3\2\2\2\u0282\u0283"+
		"\7B\2\2\u0283\u0285\3\2\2\2\u0284\u026e\3\2\2\2\u0284\u0279\3\2\2\2\u0285"+
		"\u0081\3\2\2\2\u0286\u0287\7E\2\2\u0287\u0288\5\6\4\2\u0288\u0083\3\2"+
		"\2\2\u0289\u028c\5\u0086D\2\u028a\u028b\t\5\2\2\u028b\u028d\5\u0084C\2"+
		"\u028c\u028a\3\2\2\2\u028c\u028d\3\2\2\2\u028d\u0085\3\2\2\2\u028e\u0291"+
		"\5\u0088E\2\u028f\u0290\t\6\2\2\u0290\u0292\5\u0086D\2\u0291\u028f\3\2"+
		"\2\2\u0291\u0292\3\2\2\2\u0292\u0087\3\2\2\2\u0293\u0296\5\u008aF\2\u0294"+
		"\u0295\t\7\2\2\u0295\u0297\5\u0088E\2\u0296\u0294\3\2\2\2\u0296\u0297"+
		"\3\2\2\2\u0297\u0089\3\2\2\2\u0298\u029a\t\3\2\2\u0299\u0298\3\2\2\2\u0299"+
		"\u029a\3\2\2\2\u029a\u029b\3\2\2\2\u029b\u029c\5\u008cG\2\u029c\u008b"+
		"\3\2\2\2\u029d\u02a9\5\u0090I\2\u029e\u02a9\5|?\2\u029f\u02a0\7=\2\2\u02a0"+
		"\u02a1\5\u0084C\2\u02a1\u02a2\7>\2\2\u02a2\u02a9\3\2\2\2\u02a3\u02a9\5"+
		"\u008eH\2\u02a4\u02a9\5\u0094K\2\u02a5\u02a6\7\36\2\2\u02a6\u02a9\5\u008c"+
		"G\2\u02a7\u02a9\5\34\17\2\u02a8\u029d\3\2\2\2\u02a8\u029e\3\2\2\2\u02a8"+
		"\u029f\3\2\2\2\u02a8\u02a3\3\2\2\2\u02a8\u02a4\3\2\2\2\u02a8\u02a5\3\2"+
		"\2\2\u02a8\u02a7\3\2\2\2\u02a9\u008d\3\2\2\2\u02aa\u02af\5\30\r\2\u02ab"+
		"\u02af\5\24\13\2\u02ac\u02af\5\36\20\2\u02ad\u02af\7\35\2\2\u02ae\u02aa"+
		"\3\2\2\2\u02ae\u02ab\3\2\2\2\u02ae\u02ac\3\2\2\2\u02ae\u02ad\3\2\2\2\u02af"+
		"\u008f\3\2\2\2\u02b0\u02b1\5\6\4\2\u02b1\u02b2\7=\2\2\u02b2\u02b3\5\u0092"+
		"J\2\u02b3\u02b4\7>\2\2\u02b4\u0091\3\2\2\2\u02b5\u02ba\5\u00a8U\2\u02b6"+
		"\u02b7\7\64\2\2\u02b7\u02b9\5\u00a8U\2\u02b8\u02b6\3\2\2\2\u02b9\u02bc"+
		"\3\2\2\2\u02ba\u02b8\3\2\2\2\u02ba\u02bb\3\2\2\2\u02bb\u02c0\3\2\2\2\u02bc"+
		"\u02ba\3\2\2\2\u02bd\u02c0\5\u00a0Q\2\u02be\u02c0\5\u00a4S\2\u02bf\u02b5"+
		"\3\2\2\2\u02bf\u02bd\3\2\2\2\u02bf\u02be\3\2\2\2\u02c0\u0093\3\2\2\2\u02c1"+
		"\u02c2\7?\2\2\u02c2\u02c3\5\u0096L\2\u02c3\u02c4\7A\2\2\u02c4\u02ca\3"+
		"\2\2\2\u02c5\u02c6\7@\2\2\u02c6\u02c7\5\u0096L\2\u02c7\u02c8\7B\2\2\u02c8"+
		"\u02ca\3\2\2\2\u02c9\u02c1\3\2\2\2\u02c9\u02c5\3\2\2\2\u02ca\u0095\3\2"+
		"\2\2\u02cb\u02d0\5\u0098M\2\u02cc\u02cd\7\64\2\2\u02cd\u02cf\5\u0098M"+
		"\2\u02ce\u02cc\3\2\2\2\u02cf\u02d2\3\2\2\2\u02d0\u02ce\3\2\2\2\u02d0\u02d1"+
		"\3\2\2\2\u02d1\u02d5\3\2\2\2\u02d2\u02d0\3\2\2\2\u02d3\u02d5\3\2\2\2\u02d4"+
		"\u02cb\3\2\2\2\u02d4\u02d3\3\2\2\2\u02d5\u0097\3\2\2\2\u02d6\u02d9\5\u0084"+
		"C\2\u02d7\u02d8\7F\2\2\u02d8\u02da\5\u0084C\2\u02d9\u02d7\3\2\2\2\u02d9"+
		"\u02da\3\2\2\2\u02da\u0099\3\2\2\2\u02db\u02e5\5\u009eP\2\u02dc\u02e5"+
		"\5\u009cO\2\u02dd\u02e2\5\6\4\2\u02de\u02df\7=\2\2\u02df\u02e0\5\u0092"+
		"J\2\u02e0\u02e1\7>\2\2\u02e1\u02e3\3\2\2\2\u02e2\u02de\3\2\2\2\u02e2\u02e3"+
		"\3\2\2\2\u02e3\u02e5\3\2\2\2\u02e4\u02db\3\2\2\2\u02e4\u02dc\3\2\2\2\u02e4"+
		"\u02dd\3\2\2\2\u02e5\u009b\3\2\2\2\u02e6\u02eb\t\b\2\2\u02e7\u02e8\7="+
		"\2\2\u02e8\u02e9\5\u00a0Q\2\u02e9\u02ea\7>\2\2\u02ea\u02ec\3\2\2\2\u02eb"+
		"\u02e7\3\2\2\2\u02eb\u02ec\3\2\2\2\u02ec\u009d\3\2\2\2\u02ed\u02f2\t\t"+
		"\2\2\u02ee\u02ef\7=\2\2\u02ef\u02f0\5\u00a4S\2\u02f0\u02f1\7>\2\2\u02f1"+
		"\u02f3\3\2\2\2\u02f2\u02ee\3\2\2\2\u02f2\u02f3\3\2\2\2\u02f3\u009f\3\2"+
		"\2\2\u02f4\u02f9\5\u00a2R\2\u02f5\u02f6\7\64\2\2\u02f6\u02f8\5\u00a2R"+
		"\2\u02f7\u02f5\3\2\2\2\u02f8\u02fb\3\2\2\2\u02f9\u02f7\3\2\2\2\u02f9\u02fa"+
		"\3\2\2\2\u02fa\u02fd\3\2\2\2\u02fb\u02f9\3\2\2\2\u02fc\u02f4\3\2\2\2\u02fd"+
		"\u0300\3\2\2\2\u02fe\u02fc\3\2\2\2\u02fe\u02ff\3\2\2\2\u02ff\u00a1\3\2"+
		"\2\2\u0300\u02fe\3\2\2\2\u0301\u030d\5\u0084C\2\u0302\u0303\7\66\2\2\u0303"+
		"\u0308\5\u0084C\2\u0304\u0305\7\66\2\2\u0305\u0307\5\u0084C\2\u0306\u0304"+
		"\3\2\2\2\u0307\u030a\3\2\2\2\u0308\u0306\3\2\2\2\u0308\u0309\3\2\2\2\u0309"+
		"\u030c\3\2\2\2\u030a\u0308\3\2\2\2\u030b\u0302\3\2\2\2\u030c\u030f\3\2"+
		"\2\2\u030d\u030b\3\2\2\2\u030d\u030e\3\2\2\2\u030e\u00a3\3\2\2\2\u030f"+
		"\u030d\3\2\2\2\u0310\u0315\5\u00a6T\2\u0311\u0312\7\64\2\2\u0312\u0314"+
		"\5\u00a6T\2\u0313\u0311\3\2\2\2\u0314\u0317\3\2\2\2\u0315\u0313\3\2\2"+
		"\2\u0315\u0316\3\2\2\2\u0316\u00a5\3\2\2\2\u0317\u0315\3\2\2\2\u0318\u0319"+
		"\5|?\2\u0319\u00a7\3\2\2\2\u031a\u031e\5\u0084C\2\u031b\u031d\5\u00aa"+
		"V\2\u031c\u031b\3\2\2\2\u031d\u0320\3\2\2\2\u031e\u031c\3\2\2\2\u031e"+
		"\u031f\3\2\2\2\u031f\u00a9\3\2\2\2\u0320\u031e\3\2\2\2\u0321\u0322\7\66"+
		"\2\2\u0322\u0323\5\u0084C\2\u0323\u00ab\3\2\2\2\u0324\u0325\7\27\2\2\u0325"+
		"\u0326\5\16\b\2\u0326\u00ad\3\2\2\2\u0327\u0328\3\2\2\2\u0328\u00af\3"+
		"\2\2\2\u0329\u032a\3\2\2\2\u032a\u00b1\3\2\2\2\u032b\u0330\5\u00b4[\2"+
		"\u032c\u0330\5\u00b8]\2\u032d\u0330\5\u00c0a\2\u032e\u0330\5\u00ceh\2"+
		"\u032f\u032b\3\2\2\2\u032f\u032c\3\2\2\2\u032f\u032d\3\2\2\2\u032f\u032e"+
		"\3\2\2\2\u0330\u00b3\3\2\2\2\u0331\u0332\7\t\2\2\u0332\u0333\5\u00b6\\"+
		"\2\u0333\u0334\7\23\2\2\u0334\u00b5\3\2\2\2\u0335\u033a\5t;\2\u0336\u0337"+
		"\7\65\2\2\u0337\u0339\5t;\2\u0338\u0336\3\2\2\2\u0339\u033c\3\2\2\2\u033a"+
		"\u0338\3\2\2\2\u033a\u033b\3\2\2\2\u033b\u00b7\3\2\2\2\u033c\u033a\3\2"+
		"\2\2\u033d\u0340\5\u00ba^\2\u033e\u0340\5\u00bc_\2\u033f\u033d\3\2\2\2"+
		"\u033f\u033e\3\2\2\2\u0340\u00b9\3\2\2\2\u0341\u0342\7\30\2\2\u0342\u0343"+
		"\5\u0084C\2\u0343\u0344\7(\2\2\u0344\u0347\5t;\2\u0345\u0346\7\22\2\2"+
		"\u0346\u0348\5t;\2\u0347\u0345\3\2\2\2\u0347\u0348\3\2\2\2\u0348\u00bb"+
		"\3\2\2\2\u0349\u034a\7\13\2\2\u034a\u034b\5\u0084C\2\u034b\u034c\7\37"+
		"\2\2\u034c\u0351\5\u00be`\2\u034d\u034e\7\65\2\2\u034e\u0350\5\u00be`"+
		"\2\u034f\u034d\3\2\2\2\u0350\u0353\3\2\2\2\u0351\u034f\3\2\2\2\u0351\u0352"+
		"\3\2\2\2\u0352\u0357\3\2\2\2\u0353\u0351\3\2\2\2\u0354\u0355\7\65\2\2"+
		"\u0355\u0356\7\22\2\2\u0356\u0358\5\u00b6\\\2\u0357\u0354\3\2\2\2\u0357"+
		"\u0358\3\2\2\2\u0358\u035a\3\2\2\2\u0359\u035b\7\65\2\2\u035a\u0359\3"+
		"\2\2\2\u035a\u035b\3\2\2\2\u035b\u035c\3\2\2\2\u035c\u035d\7\23\2\2\u035d"+
		"\u00bd\3\2\2\2\u035e\u035f\5n8\2\u035f\u0360\7\66\2\2\u0360\u0361\5t;"+
		"\2\u0361\u00bf\3\2\2\2\u0362\u0366\5\u00c2b\2\u0363\u0366\5\u00c4c\2\u0364"+
		"\u0366\5\u00c6d\2\u0365\u0362\3\2\2\2\u0365\u0363\3\2\2\2\u0365\u0364"+
		"\3\2\2\2\u0366\u00c1\3\2\2\2\u0367\u0368\7-\2\2\u0368\u0369\5\u0084C\2"+
		"\u0369\u036a\7\20\2\2\u036a\u036b\5t;\2\u036b\u00c3\3\2\2\2\u036c\u036d"+
		"\7&\2\2\u036d\u036e\5\u00b6\\\2\u036e\u036f\7+\2\2\u036f\u0370\5\u0084"+
		"C\2\u0370\u00c5\3\2\2\2\u0371\u0372\7\25\2\2\u0372\u0373\5\6\4\2\u0373"+
		"\u0374\7\63\2\2\u0374\u0375\5\u00c8e\2\u0375\u0376\7\20\2\2\u0376\u0377"+
		"\5t;\2\u0377\u00c7\3\2\2\2\u0378\u0379\5\u00caf\2\u0379\u037a\t\n\2\2"+
		"\u037a\u037b\5\u00ccg\2\u037b\u00c9\3\2\2\2\u037c\u037d\5\u0084C\2\u037d"+
		"\u00cb\3\2\2\2\u037e\u037f\5\u0084C\2\u037f\u00cd\3\2\2\2\u0380\u0381"+
		"\7.\2\2\u0381\u0382\5\u00d0i\2\u0382\u0383\7\20\2\2\u0383\u0384\5t;\2"+
		"\u0384\u00cf\3\2\2\2\u0385\u038a\5|?\2\u0386\u0387\7\64\2\2\u0387\u0389"+
		"\5|?\2\u0388\u0386\3\2\2\2\u0389\u038c\3\2\2\2\u038a\u0388\3\2\2\2\u038a"+
		"\u038b\3\2\2\2\u038b\u00d1\3\2\2\2\u038c\u038a\3\2\2\2S\u00d4\u00df\u00e7"+
		"\u00f2\u00f4\u0103\u0110\u0126\u012e\u0138\u0140\u0147\u014c\u0152\u015e"+
		"\u0163\u0169\u016f\u0181\u0188\u0191\u0198\u019b\u019f\u01a1\u01a8\u01b7"+
		"\u01bf\u01d1\u01dc\u01ea\u01fa\u01ff\u0207\u0211\u0216\u021e\u0229\u0231"+
		"\u0240\u0247\u0250\u0254\u025a\u0264\u0266\u026c\u0274\u027f\u0284\u028c"+
		"\u0291\u0296\u0299\u02a8\u02ae\u02ba\u02bf\u02c9\u02d0\u02d4\u02d9\u02e2"+
		"\u02e4\u02eb\u02f2\u02f9\u02fe\u0308\u030d\u0315\u031e\u032f\u033a\u033f"+
		"\u0347\u0351\u0357\u035a\u0365\u038a";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}