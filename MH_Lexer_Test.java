import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class MH_Lexer_Test {
	private static Reader reader;
	private static GenLexer mh_lexer;
	
	@BeforeClass
	public static void init() throws FileNotFoundException {
		reader = new BufferedReader(new FileReader("./haskell_test.txt"));
		mh_lexer = new MH_Lexer(reader);
	}
	
	@Test
	public void testLexWHITESPACE() throws LexError, StateOutOfRange, IOException {
		Reader reader = new StringReader("    \t\r\n\f      ");
		mh_lexer = new MH_Lexer(reader);
        LexToken currTok = mh_lexer.pullProperToken();
        while (currTok != null) {
        	assertEquals("WHITESPACE must return the empty string for its LEX token.", "", currTok.lexClass());
            currTok = mh_lexer.pullProperToken() ;
        }
	}
	
	@Test
	public void testLexCOMMENT() throws LexError, StateOutOfRange, IOException {
		Reader reader = new StringReader("-- This is a MH %!# comment");
		mh_lexer = new MH_Lexer(reader);
        LexToken currTok = mh_lexer.pullProperToken();
        while (currTok != null) {
        	assertEquals("COMMENT must return the empty string for its LEX token.", "", currTok.lexClass());
            currTok = mh_lexer.pullProperToken() ;
        }
	}
	
	@Test
	public void testLexVAR() throws LexError, StateOutOfRange, IOException {
		Reader reader = new StringReader("f ff fff f1 f12 f123 methodName1 methodName2 snake_case_name _before_var");
		mh_lexer = new MH_Lexer(reader);
        LexToken currTok = mh_lexer.pullProperToken();
        while (currTok != null) {
        	assertEquals("" + currTok.value() + " must be lexed as VAR", "VAR", currTok.lexClass());
            currTok = mh_lexer.pullProperToken() ;
        }
	}
	
	@Test
	public void testLexNUM() throws LexError, StateOutOfRange, IOException {
		Reader reader = new StringReader("0 1 2 3 4 5 6 7 8 9 10 100 1001 50000 678910");
		mh_lexer = new MH_Lexer(reader);
        LexToken currTok = mh_lexer.pullProperToken();
        while (currTok != null) {
        	assertEquals("" + currTok.value() + " must be lexed as NUM", "NUM", currTok.lexClass());
            currTok = mh_lexer.pullProperToken() ;
        }
	}
	
	@Test
	public void testLexBOOL() throws LexError, StateOutOfRange, IOException {
		Reader reader = new StringReader("True False");
		mh_lexer = new MH_Lexer(reader);
        LexToken currTok = mh_lexer.pullProperToken();
        while (currTok != null) {
        	assertEquals("" + currTok.value() + " must be lexed as BOOLEAN", "BOOLEAN", currTok.lexClass());
            currTok = mh_lexer.pullProperToken() ;
        }
	}
	
	@Test
	public void testLexSYM() throws LexError, StateOutOfRange, IOException {
		Reader reader = new StringReader("! !# $$ % \\\\ \\ | ~");
		mh_lexer = new MH_Lexer(reader);
        LexToken currTok = mh_lexer.pullProperToken();
        while (currTok != null) {
        	assertEquals("" + currTok.value() + " must be lexed as SYM", "SYM", currTok.lexClass());
            currTok = mh_lexer.pullProperToken() ;
        }
	}
	
	
	@Test
	public void testLexingSuccessful() throws LexError, StateOutOfRange, IOException {
        LexToken currTok = mh_lexer.pullProperToken();
        while (currTok != null) {
            currTok = mh_lexer.pullProperToken() ;
        }
	}
}
