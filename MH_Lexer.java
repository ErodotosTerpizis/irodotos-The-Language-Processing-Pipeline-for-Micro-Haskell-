
// File:   MH_Lexer.java

// Java template file for lexer component of Informatics 2A Assignment 1.
// Concerns lexical classes and lexer for the language MH (`Micro-Haskell').


import java.io.* ;

class MH_Lexer extends GenLexer implements LEX_TOKEN_STREAM {

static class VarAcceptor extends Acceptor implements DFA {
    public String lexClass() {return "VAR" ;} ;
    public int numberOfStates() {return 3 ;} ;

    
    int next (int state, char c) {
	 switch (state) {
	 case 0: if (CharTypes.isSmall(c)) return 1 ; else return 2 ;
	 case 1: if (CharTypes.isSmall(c)) return 1 ; //if you see a small char go to accepting state
	        else if(CharTypes.isLarge(c)) return 1 ; //if you see a large char or a digit or a ' remain in the accepting state
	        else if (CharTypes.isDigit(c)) return 1;
	        else if (c=='\'') return 1;
	        else return 2;
        default: return 2 ; // garbage state, declared "dead" below
	}
    }

    boolean accepting (int state) {return (state == 1) ;}
    int dead () {return 2 ;}
}

static class NumAcceptor extends Acceptor implements DFA {
	public String lexClass() {return "NUM" ;} ;
    public int numberOfStates() {return 4 ;} ;

    int next (int state, char c) {
	 switch (state) {
	 case 0: if (c=='0') return 1 ; // if you see zero go to an accepting state
	         else if ('1'<= c && c<='9') return 2; //else if you see a non-zero digit go to another accepting state
	         else return 3;
		
	 
	 case 2: if (CharTypes.isDigit(c)) return 2; else return 3;
        default: return 3 ; // garbage state, declared "dead" below
	}
    }

    boolean accepting (int state) {return ((state == 2)|| (state==1)) ;}
    int dead () {return 3 ;}}

static class BooleanAcceptor extends Acceptor implements DFA {
	public String lexClass() {return "BOOLEAN" ;} ;
    public int numberOfStates() {return 11 ;} ;

     int next (int state, char c) {
	 switch (state) {
	 case 0: if (c=='T') return 1 ; 
	         else if (c=='F') return 5;
	         else return 10 ;
	 case 1: if (c=='r') return 2; else return 10;
	 case 2: if (c=='u') return 3; else return 10;
	 case 3: if (c=='e') return 4; else return 10;
	 case 5: if (c=='a') return 6; else return 10;
	 case 6: if (c=='l') return 7; else return 10;
	 case 7: if (c=='s') return 8; else return 10;
	 case 8: if (c=='e') return 9; else return 10;
	 
        default: return 10 ; // garbage state, declared "dead" below
	}
    }

    boolean accepting (int state) {return ((state == 4)||(state==9)) ;}
    int dead () {return 10 ;}}


static class SymAcceptor extends Acceptor implements DFA {
	    public String lexClass() {return "SYM" ;} ;
	    public int numberOfStates() {return 3 ;} ;

	    int next (int state, char c) {
		 switch (state) {
		 case 0: if (CharTypes.isSymbolic(c)) return 1 ; else return 2 ;
		 case 1: if (CharTypes.isSymbolic(c)) return 1 ; else return 2;
	        default: return 2 ; // garbage state, declared "dead" below
		}
	    }

	    boolean accepting (int state) {return (state == 1) ;}
	    int dead () {return 2 ;}
}

static class WhitespaceAcceptor extends Acceptor implements DFA {
	public String lexClass() {return "" ;} ;
    public int numberOfStates() {return 3 ;} ;

    int next (int state, char c) {
	 switch (state) {
	 case 0: if (CharTypes.isWhitespace(c)) return 1 ; else return 2 ;
	 case 1: if (CharTypes.isWhitespace(c)) return 1 ; else return 2;
        default: return 2 ; // garbage state, declared "dead" below
	}
    }

    boolean accepting (int state) {return (state == 1) ;}
    int dead () {return 2 ;}
}

static class CommentAcceptor extends Acceptor implements DFA {
	public String lexClass() {return "" ;} ;
    public int numberOfStates() {return 5 ;} ;

    int next (int state, char c) {
	 switch (state) {
	 case 0: if (c=='-') return 1 ; else return 4 ;
	 case 1: if (c=='-') return 2 ; else return 4;
	 case 2: if (c=='-') return 2;  // as long as you see 2 or more dashes stay in accepting state
	         else if (!(CharTypes.isSymbolic(c)) && !(CharTypes.isNewline(c))) return 3; else return 4; // as long as the char is not symbolic nor a newline stay 
	                                                                                                   // in accepting state
	 case 3: if (!(CharTypes.isNewline(c))) return 3; else return 4;
        default: return 4 ; // garbage state, declared "dead" below
	}
	 //return dead();
    }

    boolean accepting (int state) {return ((state == 2)|| (state==3)) ;}
    int dead () {return 4 ;}
}



static class TokAcceptor extends Acceptor implements DFA {

    String tok ;
    int tokLen ;
    TokAcceptor (String tok) {this.tok = tok ; tokLen = tok.length() ;}
    public String lexClass() {return tok ;} ;
    public int numberOfStates() {return tokLen+2 ;} ;
    
    int next(int state, char c) {
    	//if we are in the accepting state and we see another symbol go to the garbage state
     if (state==tokLen) {
    	 return state+1;
    	 }
     //if you are in the garbage state stay there
     else if (state==(tokLen+1)) {
    	 return tokLen+1;
    	 }   
     //else,check if the char at that position in our string is the same as the character given
     else if (c==tok.charAt(state)) { 
    	 return state+1;
    	 }
     else {return tokLen+1;}
    }
     
     boolean accepting (int state) {return (state==tokLen) ;}
     int dead () {return tokLen+1 ;}
    
	
}    
   	

    // add definitions of MH_acceptors here
   static DFA VarAc = new VarAcceptor() ;
   static DFA NumAc = new NumAcceptor() ;
   static DFA BoolAc = new BooleanAcceptor() ;
   static DFA SymAc = new SymAcceptor() ;
   static DFA WhitespaceAc = new WhitespaceAcceptor() ;
   static DFA CommentAc = new CommentAcceptor() ;
   static DFA IntAc = new TokAcceptor("Integer") ;
   static DFA BAc = new TokAcceptor("Bool") ;
   static DFA IfAc = new TokAcceptor("if") ;
   static DFA ThenAc = new TokAcceptor("then") ;
   static DFA ElseAc = new TokAcceptor("else") ;
   static DFA RbAc = new TokAcceptor("(") ;
   static DFA LbAc = new TokAcceptor(")") ;
   static DFA SemiAc = new TokAcceptor(";") ;
   
   
   
   static DFA[] MH_acceptors = 
			new DFA[] {CommentAc,IntAc,IfAc,ThenAc,ElseAc,VarAc,NumAc,BAc,RbAc,LbAc,SemiAc,WhitespaceAc, BoolAc, SymAc} ;
		    

    MH_Lexer (Reader reader) {
	 super(reader,MH_acceptors) ;
    }

}






