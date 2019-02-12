import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MH_Parser_Test {
	
    private static String outputParseTreeToBuffer(TREE tree) {
    	String out = "";
    	if (tree != null) {
    		out += tree.getLabel();
    		if (!tree.isTerminal()) {
    			for (TREE child : tree.getChildren()) {
    				out += " " + outputParseTreeToBuffer(child);
    			}
    		}
    	}
    	return out;
    }
    
	static MH_Parser MH_Parser1 = MH_Type_Impl.MH_Parser1 ;
	
	@Test
	public void testParseFunction() throws Exception {
		String test_string = "f :: Integer -> Integer; f n = n;";
		Reader reader = new StringReader(test_string);
		LEX_TOKEN_STREAM MH_Lexer = 
				new CheckedSymbolLexer (new MH_Lexer (reader));
			    TREE prog = MH_Parser1.parseTokenStream (MH_Lexer);
		assertEquals("Parse Tree must match expected parse tree for '" + test_string + "'", outputParseTreeToBuffer(prog), "#Prog #Decl #TypeDecl VAR :: #Type #Type0 Integer #TypeRest -> #Type #Type0 Integer #TypeRest ; #TermDecl VAR #Args VAR #Args = #Exp #Exp0 #Exp1 #Exp2 #Exp3 VAR #Rest2 #Rest1 #Rest0 ; #Prog");
	}
	
	@Test
	public void testParseFunctionWithConditional() throws Exception {
		String test_string = "f :: Integer -> Integer; f n = if True then n else 0;";
		Reader reader = new StringReader(test_string);
		LEX_TOKEN_STREAM MH_Lexer = 
				new CheckedSymbolLexer (new MH_Lexer (reader));
			    TREE prog = MH_Parser1.parseTokenStream (MH_Lexer);
		assertEquals("Parse Tree must match expected parse tree for '" + test_string + "'", outputParseTreeToBuffer(prog), "#Prog #Decl #TypeDecl VAR :: #Type #Type0 Integer #TypeRest -> #Type #Type0 Integer #TypeRest ; #TermDecl VAR #Args VAR #Args = #Exp if #Exp #Exp0 #Exp1 #Exp2 #Exp3 BOOLEAN #Rest2 #Rest1 #Rest0 then #Exp #Exp0 #Exp1 #Exp2 #Exp3 VAR #Rest2 #Rest1 #Rest0 else #Exp #Exp0 #Exp1 #Exp2 #Exp3 NUM #Rest2 #Rest1 #Rest0 ; #Prog");
	}
	
	@Test
	public void testParseFunctionWithFunctionArgumentl() throws Exception {
		String test_string = "f :: (Integer -> Bool) -> Integer -> Bool; f g n = g n;";
		Reader reader = new StringReader(test_string);
		LEX_TOKEN_STREAM MH_Lexer = 
				new CheckedSymbolLexer (new MH_Lexer (reader));
			    TREE prog = MH_Parser1.parseTokenStream (MH_Lexer);
		assertEquals("Parse Tree must match expected parse tree for '" + test_string + "'", outputParseTreeToBuffer(prog), "#Prog #Decl #TypeDecl VAR :: #Type #Type0 ( #Type #Type0 Integer #TypeRest -> #Type #Type0 Bool #TypeRest ) #TypeRest -> #Type #Type0 Integer #TypeRest -> #Type #Type0 Bool #TypeRest ; #TermDecl VAR #Args VAR #Args VAR #Args = #Exp #Exp0 #Exp1 #Exp2 #Exp3 VAR #Rest2 #Exp3 VAR #Rest2 #Rest1 #Rest0 ; #Prog");
	}
	
	@Test
	public void testParseFunctionWithFunctionWithArithmatic() throws Exception {
		String test_string = "f :: Integer -> Integer -> Integer; f x y = x + y + (y - x - x);";
		Reader reader = new StringReader(test_string);
		LEX_TOKEN_STREAM MH_Lexer = 
				new CheckedSymbolLexer (new MH_Lexer (reader));
			    TREE prog = MH_Parser1.parseTokenStream (MH_Lexer);
		assertEquals("Parse Tree must match expected parse tree for '" + test_string + "'", outputParseTreeToBuffer(prog), "#Prog #Decl #TypeDecl VAR :: #Type #Type0 Integer #TypeRest -> #Type #Type0 Integer #TypeRest -> #Type #Type0 Integer #TypeRest ; #TermDecl VAR #Args VAR #Args VAR #Args = #Exp #Exp0 #Exp1 #Exp2 #Exp3 VAR #Rest2 #Rest1 + #Exp2 #Exp3 VAR #Rest2 #Rest1 + #Exp2 #Exp3 ( #Exp #Exp0 #Exp1 #Exp2 #Exp3 VAR #Rest2 #Rest1 - #Exp2 #Exp3 VAR #Rest2 #Rest1 - #Exp2 #Exp3 VAR #Rest2 #Rest1 #Rest0 ) #Rest2 #Rest1 #Rest0 ; #Prog");
	}
	
	@Test
	public void testParsingSuccessful() throws Exception {
		Reader reader = new BufferedReader(new FileReader("./haskell_test.txt"));
		LEX_TOKEN_STREAM MH_Lexer = 
				new CheckedSymbolLexer (new MH_Lexer (reader)) ;
			    TREE prog = MH_Parser1.parseTokenStream (MH_Lexer) ;
    }
}
