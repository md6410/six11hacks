// $ANTLR 3.0b6 src/org/six11/flatcad/flatlang/FlatLang.g 2008-03-04 16:45:41

package org.six11.flatcad.flatlang;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class FlatLangLexer extends Lexer {
    public static final int N_LTEQ_EXPR=26;
    public static final int N_EQ_EXPR=29;
    public static final int N_DIV_EXPR=19;
    public static final int REPEAT_STATEMENT=34;
    public static final int EXPR=10;
    public static final int OCT_ESC=56;
    public static final int T70=70;
    public static final int FALSE=46;
    public static final int T74=74;
    public static final int T85=85;
    public static final int IF_ALT=36;
    public static final int PROG=4;
    public static final int SHAPE=14;
    public static final int T81=81;
    public static final int ELSE_ALT=37;
    public static final int N_AND_EXPR=31;
    public static final int INDEX_EXPR=39;
    public static final int N_MULT_EXPR=18;
    public static final int WHILE_STATEMENT=33;
    public static final int SAFE=49;
    public static final int T62=62;
    public static final int T68=68;
    public static final int T73=73;
    public static final int T84=84;
    public static final int T78=78;
    public static final int WS=59;
    public static final int PARAM_LIST=8;
    public static final int T71=71;
    public static final int LINE_COMMENT=58;
    public static final int N_NEQ_EXPR=30;
    public static final int T72=72;
    public static final int UNI_ESC=55;
    public static final int N_LT_EXPR=25;
    public static final int T76=76;
    public static final int N_MINUS_EXPR=17;
    public static final int T75=75;
    public static final int T89=89;
    public static final int T67=67;
    public static final int MEMBER_EXPR_DOT=42;
    public static final int N_ADD_EXPR=16;
    public static final int T60=60;
    public static final int T82=82;
    public static final int FUNCTION_DEF_SIGNATURE=5;
    public static final int TRUE=45;
    public static final int FOR_EACH_STATEMENT=43;
    public static final int T79=79;
    public static final int N_UNARY_POS_EXPR=24;
    public static final int STATEMENT=7;
    public static final int N_GTEQ_EXPR=27;
    public static final int N_ASSIGN_EXPR=21;
    public static final int T83=83;
    public static final int T61=61;
    public static final int BLOCK=11;
    public static final int T64=64;
    public static final int T91=91;
    public static final int T86=86;
    public static final int LITERAL=38;
    public static final int LIST_EXPR=40;
    public static final int IF_STATEMENT=35;
    public static final int INT=52;
    public static final int INFINITY=47;
    public static final int PARAM=9;
    public static final int MEMBER_EXPR=41;
    public static final int HEX_DIGIT=57;
    public static final int T77=77;
    public static final int STR_LITERAL=51;
    public static final int N_GT_EXPR=28;
    public static final int T69=69;
    public static final int FROM=15;
    public static final int ID=44;
    public static final int OBJECT=48;
    public static final int LETTER=53;
    public static final int N_MODULO_EXPR=20;
    public static final int T92=92;
    public static final int REPLACE=12;
    public static final int FUNCTION_CALL=6;
    public static final int N_OR_EXPR=32;
    public static final int T66=66;
    public static final int T88=88;
    public static final int T63=63;
    public static final int N_UNARY_NEG_EXPR=23;
    public static final int ESC_SEQ=54;
    public static final int T65=65;
    public static final int T87=87;
    public static final int T80=80;
    public static final int EOF=-1;
    public static final int REPLACE_ALL=13;
    public static final int NUM=50;
    public static final int Tokens=93;
    public static final int T90=90;
    public static final int N_UNARY_EXPR=22;
    public FlatLangLexer() {;} 
    public FlatLangLexer(CharStream input) {
        super(input);
    }
    public String getGrammarFileName() { return "src/org/six11/flatcad/flatlang/FlatLang.g"; }

    // $ANTLR start T60
    public void mT60() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T60;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:6:7: ( 'done' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:6:7: 'done'
            {
            match("done"); 


            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T60

    // $ANTLR start T61
    public void mT61() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T61;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:7:7: ( 'if' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:7:7: 'if'
            {
            match("if"); 


            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T61

    // $ANTLR start T62
    public void mT62() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T62;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:8:7: ( 'else' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:8:7: 'else'
            {
            match("else"); 


            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T62

    // $ANTLR start T63
    public void mT63() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T63;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:9:7: ( 'alt' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:9:7: 'alt'
            {
            match("alt"); 


            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T63

    // $ANTLR start T64
    public void mT64() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T64;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:10:7: ( '(' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:10:7: '('
            {
            match('('); 

            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T64

    // $ANTLR start T65
    public void mT65() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T65;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:11:7: ( ')' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:11:7: ')'
            {
            match(')'); 

            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T65

    // $ANTLR start T66
    public void mT66() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T66;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:12:7: ( 'repeat' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:12:7: 'repeat'
            {
            match("repeat"); 


            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T66

    // $ANTLR start T67
    public void mT67() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T67;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:13:7: ( 'while' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:13:7: 'while'
            {
            match("while"); 


            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T67

    // $ANTLR start T68
    public void mT68() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T68;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:14:7: ( 'for each' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:14:7: 'for each'
            {
            match("for each"); 


            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T68

    // $ANTLR start T69
    public void mT69() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T69;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:15:7: ( ':' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:15:7: ':'
            {
            match(':'); 

            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T69

    // $ANTLR start T70
    public void mT70() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T70;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:16:7: ( '=' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:16:7: '='
            {
            match('='); 

            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T70

    // $ANTLR start T71
    public void mT71() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T71;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:17:7: ( 'or' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:17:7: 'or'
            {
            match("or"); 


            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T71

    // $ANTLR start T72
    public void mT72() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T72;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:18:7: ( 'and' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:18:7: 'and'
            {
            match("and"); 


            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T72

    // $ANTLR start T73
    public void mT73() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T73;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:19:7: ( '==' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:19:7: '=='
            {
            match("=="); 


            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T73

    // $ANTLR start T74
    public void mT74() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T74;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:20:7: ( '!=' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:20:7: '!='
            {
            match("!="); 


            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T74

    // $ANTLR start T75
    public void mT75() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T75;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:21:7: ( '<' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:21:7: '<'
            {
            match('<'); 

            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T75

    // $ANTLR start T76
    public void mT76() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T76;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:22:7: ( '<=' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:22:7: '<='
            {
            match("<="); 


            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T76

    // $ANTLR start T77
    public void mT77() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T77;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:23:7: ( '>' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:23:7: '>'
            {
            match('>'); 

            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T77

    // $ANTLR start T78
    public void mT78() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T78;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:24:7: ( '>=' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:24:7: '>='
            {
            match(">="); 


            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T78

    // $ANTLR start T79
    public void mT79() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T79;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:25:7: ( '+' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:25:7: '+'
            {
            match('+'); 

            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T79

    // $ANTLR start T80
    public void mT80() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T80;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:26:7: ( '-' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:26:7: '-'
            {
            match('-'); 

            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T80

    // $ANTLR start T81
    public void mT81() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T81;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:27:7: ( '*' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:27:7: '*'
            {
            match('*'); 

            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T81

    // $ANTLR start T82
    public void mT82() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T82;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:28:7: ( '/' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:28:7: '/'
            {
            match('/'); 

            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T82

    // $ANTLR start T83
    public void mT83() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T83;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:29:7: ( '%' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:29:7: '%'
            {
            match('%'); 

            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T83

    // $ANTLR start T84
    public void mT84() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T84;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:30:7: ( '.' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:30:7: '.'
            {
            match('.'); 

            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T84

    // $ANTLR start T85
    public void mT85() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T85;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:31:7: ( '[' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:31:7: '['
            {
            match('['); 

            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T85

    // $ANTLR start T86
    public void mT86() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T86;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:32:7: ( ']' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:32:7: ']'
            {
            match(']'); 

            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T86

    // $ANTLR start T87
    public void mT87() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T87;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:33:7: ( 'define' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:33:7: 'define'
            {
            match("define"); 


            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T87

    // $ANTLR start T88
    public void mT88() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T88;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:34:7: ( 'replace' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:34:7: 'replace'
            {
            match("replace"); 


            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T88

    // $ANTLR start T89
    public void mT89() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T89;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:35:7: ( ',' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:35:7: ','
            {
            match(','); 

            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T89

    // $ANTLR start T90
    public void mT90() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T90;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:36:7: ( 'replaceAll' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:36:7: 'replaceAll'
            {
            match("replaceAll"); 


            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T90

    // $ANTLR start T91
    public void mT91() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T91;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:37:7: ( 'from' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:37:7: 'from'
            {
            match("from"); 


            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T91

    // $ANTLR start T92
    public void mT92() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = T92;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:38:7: ( 'shape' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:38:7: 'shape'
            {
            match("shape"); 


            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end T92

    // $ANTLR start SAFE
    public void mSAFE() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = SAFE;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:298:8: ( 'safe' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:298:8: 'safe'
            {
            match("safe"); 


            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end SAFE

    // $ANTLR start OBJECT
    public void mOBJECT() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = OBJECT;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:301:10: ( '__object__' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:301:10: '__object__'
            {
            match("__object__"); 


            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end OBJECT

    // $ANTLR start INFINITY
    public void mINFINITY() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = INFINITY;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:304:11: ( 'infinity' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:304:11: 'infinity'
            {
            match("infinity"); 


            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end INFINITY

    // $ANTLR start TRUE
    public void mTRUE() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = TRUE;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:307:8: ( 'true' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:307:8: 'true'
            {
            match("true"); 


            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end TRUE

    // $ANTLR start FALSE
    public void mFALSE() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = FALSE;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:310:9: ( 'false' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:310:9: 'false'
            {
            match("false"); 


            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end FALSE

    // $ANTLR start NUM
    public void mNUM() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = NUM;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:313:7: ( ( INT )+ | ( INT )* '.' ( INT )+ )
            int alt4=2;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:313:7: ( INT )+
                    {
                    // src/org/six11/flatcad/flatlang/FlatLang.g:313:7: ( INT )+
                    int cnt1=0;
                    loop1:
                    do {
                        int alt1=2;
                        int LA1_0 = input.LA(1);
                        if ( ((LA1_0>='0' && LA1_0<='9')) ) {
                            alt1=1;
                        }


                        switch (alt1) {
                    	case 1 :
                    	    // src/org/six11/flatcad/flatlang/FlatLang.g:313:7: INT
                    	    {
                    	    mINT(); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt1 >= 1 ) break loop1;
                                EarlyExitException eee =
                                    new EarlyExitException(1, input);
                                throw eee;
                        }
                        cnt1++;
                    } while (true);


                    }
                    break;
                case 2 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:314:4: ( INT )* '.' ( INT )+
                    {
                    // src/org/six11/flatcad/flatlang/FlatLang.g:314:4: ( INT )*
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);
                        if ( ((LA2_0>='0' && LA2_0<='9')) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // src/org/six11/flatcad/flatlang/FlatLang.g:314:4: INT
                    	    {
                    	    mINT(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop2;
                        }
                    } while (true);

                    match('.'); 
                    // src/org/six11/flatcad/flatlang/FlatLang.g:314:13: ( INT )+
                    int cnt3=0;
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);
                        if ( ((LA3_0>='0' && LA3_0<='9')) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // src/org/six11/flatcad/flatlang/FlatLang.g:314:13: INT
                    	    {
                    	    mINT(); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt3 >= 1 ) break loop3;
                                EarlyExitException eee =
                                    new EarlyExitException(3, input);
                                throw eee;
                        }
                        cnt3++;
                    } while (true);


                    }
                    break;

            }


                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end NUM

    // $ANTLR start ID
    public void mID() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = ID;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:317:6: ( ( LETTER | '_' ) ( LETTER | '_' | INT )* )
            // src/org/six11/flatcad/flatlang/FlatLang.g:317:6: ( LETTER | '_' ) ( LETTER | '_' | INT )*
            {
            // src/org/six11/flatcad/flatlang/FlatLang.g:317:6: ( LETTER | '_' )
            int alt5=2;
            int LA5_0 = input.LA(1);
            if ( ((LA5_0>='A' && LA5_0<='Z')||(LA5_0>='a' && LA5_0<='z')) ) {
                alt5=1;
            }
            else if ( (LA5_0=='_') ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("317:6: ( LETTER | '_' )", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:317:7: LETTER
                    {
                    mLETTER(); 

                    }
                    break;
                case 2 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:317:16: '_'
                    {
                    match('_'); 

                    }
                    break;

            }

            // src/org/six11/flatcad/flatlang/FlatLang.g:317:21: ( LETTER | '_' | INT )*
            loop6:
            do {
                int alt6=4;
                switch ( input.LA(1) ) {
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                case 'G':
                case 'H':
                case 'I':
                case 'J':
                case 'K':
                case 'L':
                case 'M':
                case 'N':
                case 'O':
                case 'P':
                case 'Q':
                case 'R':
                case 'S':
                case 'T':
                case 'U':
                case 'V':
                case 'W':
                case 'X':
                case 'Y':
                case 'Z':
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'f':
                case 'g':
                case 'h':
                case 'i':
                case 'j':
                case 'k':
                case 'l':
                case 'm':
                case 'n':
                case 'o':
                case 'p':
                case 'q':
                case 'r':
                case 's':
                case 't':
                case 'u':
                case 'v':
                case 'w':
                case 'x':
                case 'y':
                case 'z':
                    alt6=1;
                    break;
                case '_':
                    alt6=2;
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    alt6=3;
                    break;

                }

                switch (alt6) {
            	case 1 :
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:317:22: LETTER
            	    {
            	    mLETTER(); 

            	    }
            	    break;
            	case 2 :
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:317:31: '_'
            	    {
            	    match('_'); 

            	    }
            	    break;
            	case 3 :
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:317:37: INT
            	    {
            	    mINT(); 

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end ID

    // $ANTLR start LETTER
    public void mLETTER() throws RecognitionException {
        try {
            ruleNestingLevel++;
            // src/org/six11/flatcad/flatlang/FlatLang.g:321:10: ( ('a'..'z'|'A'..'Z'))
            // src/org/six11/flatcad/flatlang/FlatLang.g:321:10: ('a'..'z'|'A'..'Z')
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }


            }

        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end LETTER

    // $ANTLR start INT
    public void mINT() throws RecognitionException {
        try {
            ruleNestingLevel++;
            // src/org/six11/flatcad/flatlang/FlatLang.g:325:7: ( ( '0' .. '9' )+ )
            // src/org/six11/flatcad/flatlang/FlatLang.g:325:7: ( '0' .. '9' )+
            {
            // src/org/six11/flatcad/flatlang/FlatLang.g:325:7: ( '0' .. '9' )+
            int cnt7=0;
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);
                if ( ((LA7_0>='0' && LA7_0<='9')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:325:8: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt7 >= 1 ) break loop7;
                        EarlyExitException eee =
                            new EarlyExitException(7, input);
                        throw eee;
                }
                cnt7++;
            } while (true);


            }

        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end INT

    // $ANTLR start STR_LITERAL
    public void mSTR_LITERAL() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = STR_LITERAL;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:329:10: ( '\"' ( ESC_SEQ | ~ ('\\\\'|'\"'))* '\"' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:329:10: '\"' ( ESC_SEQ | ~ ('\\\\'|'\"'))* '\"'
            {
            match('\"'); 
            // src/org/six11/flatcad/flatlang/FlatLang.g:329:14: ( ESC_SEQ | ~ ('\\\\'|'\"'))*
            loop8:
            do {
                int alt8=3;
                int LA8_0 = input.LA(1);
                if ( (LA8_0=='\\') ) {
                    alt8=1;
                }
                else if ( ((LA8_0>='\u0000' && LA8_0<='!')||(LA8_0>='#' && LA8_0<='[')||(LA8_0>=']' && LA8_0<='\uFFFE')) ) {
                    alt8=2;
                }


                switch (alt8) {
            	case 1 :
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:329:16: ESC_SEQ
            	    {
            	    mESC_SEQ(); 

            	    }
            	    break;
            	case 2 :
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:329:26: ~ ('\\\\'|'\"')
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFE') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recover(mse);    throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            match('\"'); 

            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end STR_LITERAL

    // $ANTLR start ESC_SEQ
    public void mESC_SEQ() throws RecognitionException {
        try {
            ruleNestingLevel++;
            // src/org/six11/flatcad/flatlang/FlatLang.g:334:9: ( '\\\\' ('b'|'t'|'n'|'f'|'r'|'\\\"'|'\\''|'\\\\') | UNI_ESC | OCT_ESC )
            int alt9=3;
            int LA9_0 = input.LA(1);
            if ( (LA9_0=='\\') ) {
                switch ( input.LA(2) ) {
                case '\"':
                case '\'':
                case '\\':
                case 'b':
                case 'f':
                case 'n':
                case 'r':
                case 't':
                    alt9=1;
                    break;
                case 'u':
                    alt9=2;
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                    alt9=3;
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("332:1: fragment ESC_SEQ : ( '\\\\' ('b'|'t'|'n'|'f'|'r'|'\\\"'|'\\''|'\\\\') | UNI_ESC | OCT_ESC );", 9, 1, input);

                    throw nvae;
                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("332:1: fragment ESC_SEQ : ( '\\\\' ('b'|'t'|'n'|'f'|'r'|'\\\"'|'\\''|'\\\\') | UNI_ESC | OCT_ESC );", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:334:9: '\\\\' ('b'|'t'|'n'|'f'|'r'|'\\\"'|'\\''|'\\\\')
                    {
                    match('\\'); 
                    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse =
                            new MismatchedSetException(null,input);
                        recover(mse);    throw mse;
                    }


                    }
                    break;
                case 2 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:335:9: UNI_ESC
                    {
                    mUNI_ESC(); 

                    }
                    break;
                case 3 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:336:9: OCT_ESC
                    {
                    mOCT_ESC(); 

                    }
                    break;

            }
        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end ESC_SEQ

    // $ANTLR start OCT_ESC
    public void mOCT_ESC() throws RecognitionException {
        try {
            ruleNestingLevel++;
            // src/org/six11/flatcad/flatlang/FlatLang.g:340:11: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
            int alt10=3;
            int LA10_0 = input.LA(1);
            if ( (LA10_0=='\\') ) {
                int LA10_1 = input.LA(2);
                if ( ((LA10_1>='0' && LA10_1<='3')) ) {
                    int LA10_2 = input.LA(3);
                    if ( ((LA10_2>='0' && LA10_2<='7')) ) {
                        int LA10_4 = input.LA(4);
                        if ( ((LA10_4>='0' && LA10_4<='7')) ) {
                            alt10=1;
                        }
                        else {
                            alt10=2;}
                    }
                    else {
                        alt10=3;}
                }
                else if ( ((LA10_1>='4' && LA10_1<='7')) ) {
                    int LA10_3 = input.LA(3);
                    if ( ((LA10_3>='0' && LA10_3<='7')) ) {
                        alt10=2;
                    }
                    else {
                        alt10=3;}
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("339:1: fragment OCT_ESC : ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) );", 10, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("339:1: fragment OCT_ESC : ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) );", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:340:11: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // src/org/six11/flatcad/flatlang/FlatLang.g:340:16: ( '0' .. '3' )
                    // src/org/six11/flatcad/flatlang/FlatLang.g:340:17: '0' .. '3'
                    {
                    matchRange('0','3'); 

                    }

                    // src/org/six11/flatcad/flatlang/FlatLang.g:340:27: ( '0' .. '7' )
                    // src/org/six11/flatcad/flatlang/FlatLang.g:340:28: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // src/org/six11/flatcad/flatlang/FlatLang.g:340:38: ( '0' .. '7' )
                    // src/org/six11/flatcad/flatlang/FlatLang.g:340:39: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 2 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:341:4: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // src/org/six11/flatcad/flatlang/FlatLang.g:341:9: ( '0' .. '7' )
                    // src/org/six11/flatcad/flatlang/FlatLang.g:341:10: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // src/org/six11/flatcad/flatlang/FlatLang.g:341:20: ( '0' .. '7' )
                    // src/org/six11/flatcad/flatlang/FlatLang.g:341:21: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 3 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:342:4: '\\\\' ( '0' .. '7' )
                    {
                    match('\\'); 
                    // src/org/six11/flatcad/flatlang/FlatLang.g:342:9: ( '0' .. '7' )
                    // src/org/six11/flatcad/flatlang/FlatLang.g:342:10: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;

            }
        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end OCT_ESC

    // $ANTLR start UNI_ESC
    public void mUNI_ESC() throws RecognitionException {
        try {
            ruleNestingLevel++;
            // src/org/six11/flatcad/flatlang/FlatLang.g:346:11: ( '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT )
            // src/org/six11/flatcad/flatlang/FlatLang.g:346:11: '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
            {
            match('\\'); 
            match('u'); 
            mHEX_DIGIT(); 
            mHEX_DIGIT(); 
            mHEX_DIGIT(); 
            mHEX_DIGIT(); 

            }

        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end UNI_ESC

    // $ANTLR start HEX_DIGIT
    public void mHEX_DIGIT() throws RecognitionException {
        try {
            ruleNestingLevel++;
            // src/org/six11/flatcad/flatlang/FlatLang.g:351:4: ( ('0'..'9'|'a'..'f'|'A'..'F'))
            // src/org/six11/flatcad/flatlang/FlatLang.g:351:4: ('0'..'9'|'a'..'f'|'A'..'F')
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='F')||(input.LA(1)>='a' && input.LA(1)<='f') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }


            }

        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end HEX_DIGIT

    // $ANTLR start LINE_COMMENT
    public void mLINE_COMMENT() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = LINE_COMMENT;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:355:4: ( ( ';' ( . )* '\\n' ) )
            // src/org/six11/flatcad/flatlang/FlatLang.g:355:4: ( ';' ( . )* '\\n' )
            {
            // src/org/six11/flatcad/flatlang/FlatLang.g:355:4: ( ';' ( . )* '\\n' )
            // src/org/six11/flatcad/flatlang/FlatLang.g:355:5: ';' ( . )* '\\n'
            {
            match(';'); 
            // src/org/six11/flatcad/flatlang/FlatLang.g:355:9: ( . )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);
                if ( (LA11_0=='\n') ) {
                    alt11=2;
                }
                else if ( ((LA11_0>='\u0000' && LA11_0<='\t')||(LA11_0>='\u000B' && LA11_0<='\uFFFE')) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:355:9: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            match('\n'); 

            }

             _channel = HIDDEN; 

            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end LINE_COMMENT

    // $ANTLR start WS
    public void mWS() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = WS;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // src/org/six11/flatcad/flatlang/FlatLang.g:358:6: ( (' '|'\\t'|'\\r'|'\\n'))
            // src/org/six11/flatcad/flatlang/FlatLang.g:358:6: (' '|'\\t'|'\\r'|'\\n')
            {
            if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }

             _channel = HIDDEN; 

            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end WS

    public void mTokens() throws RecognitionException {
        // src/org/six11/flatcad/flatlang/FlatLang.g:1:10: ( T60 | T61 | T62 | T63 | T64 | T65 | T66 | T67 | T68 | T69 | T70 | T71 | T72 | T73 | T74 | T75 | T76 | T77 | T78 | T79 | T80 | T81 | T82 | T83 | T84 | T85 | T86 | T87 | T88 | T89 | T90 | T91 | T92 | SAFE | OBJECT | INFINITY | TRUE | FALSE | NUM | ID | STR_LITERAL | LINE_COMMENT | WS )
        int alt12=43;
        switch ( input.LA(1) ) {
        case 'd':
            switch ( input.LA(2) ) {
            case 'e':
                int LA12_33 = input.LA(3);
                if ( (LA12_33=='f') ) {
                    int LA12_57 = input.LA(4);
                    if ( (LA12_57=='i') ) {
                        int LA12_74 = input.LA(5);
                        if ( (LA12_74=='n') ) {
                            int LA12_90 = input.LA(6);
                            if ( (LA12_90=='e') ) {
                                int LA12_103 = input.LA(7);
                                if ( ((LA12_103>='0' && LA12_103<='9')||(LA12_103>='A' && LA12_103<='Z')||LA12_103=='_'||(LA12_103>='a' && LA12_103<='z')) ) {
                                    alt12=40;
                                }
                                else {
                                    alt12=28;}
                            }
                            else {
                                alt12=40;}
                        }
                        else {
                            alt12=40;}
                    }
                    else {
                        alt12=40;}
                }
                else {
                    alt12=40;}
                break;
            case 'o':
                int LA12_34 = input.LA(3);
                if ( (LA12_34=='n') ) {
                    int LA12_58 = input.LA(4);
                    if ( (LA12_58=='e') ) {
                        int LA12_75 = input.LA(5);
                        if ( ((LA12_75>='0' && LA12_75<='9')||(LA12_75>='A' && LA12_75<='Z')||LA12_75=='_'||(LA12_75>='a' && LA12_75<='z')) ) {
                            alt12=40;
                        }
                        else {
                            alt12=1;}
                    }
                    else {
                        alt12=40;}
                }
                else {
                    alt12=40;}
                break;
            default:
                alt12=40;}

            break;
        case 'i':
            switch ( input.LA(2) ) {
            case 'f':
                int LA12_35 = input.LA(3);
                if ( ((LA12_35>='0' && LA12_35<='9')||(LA12_35>='A' && LA12_35<='Z')||LA12_35=='_'||(LA12_35>='a' && LA12_35<='z')) ) {
                    alt12=40;
                }
                else {
                    alt12=2;}
                break;
            case 'n':
                int LA12_36 = input.LA(3);
                if ( (LA12_36=='f') ) {
                    int LA12_60 = input.LA(4);
                    if ( (LA12_60=='i') ) {
                        int LA12_76 = input.LA(5);
                        if ( (LA12_76=='n') ) {
                            int LA12_92 = input.LA(6);
                            if ( (LA12_92=='i') ) {
                                int LA12_104 = input.LA(7);
                                if ( (LA12_104=='t') ) {
                                    int LA12_112 = input.LA(8);
                                    if ( (LA12_112=='y') ) {
                                        int LA12_116 = input.LA(9);
                                        if ( ((LA12_116>='0' && LA12_116<='9')||(LA12_116>='A' && LA12_116<='Z')||LA12_116=='_'||(LA12_116>='a' && LA12_116<='z')) ) {
                                            alt12=40;
                                        }
                                        else {
                                            alt12=36;}
                                    }
                                    else {
                                        alt12=40;}
                                }
                                else {
                                    alt12=40;}
                            }
                            else {
                                alt12=40;}
                        }
                        else {
                            alt12=40;}
                    }
                    else {
                        alt12=40;}
                }
                else {
                    alt12=40;}
                break;
            default:
                alt12=40;}

            break;
        case 'e':
            int LA12_3 = input.LA(2);
            if ( (LA12_3=='l') ) {
                int LA12_37 = input.LA(3);
                if ( (LA12_37=='s') ) {
                    int LA12_61 = input.LA(4);
                    if ( (LA12_61=='e') ) {
                        int LA12_77 = input.LA(5);
                        if ( ((LA12_77>='0' && LA12_77<='9')||(LA12_77>='A' && LA12_77<='Z')||LA12_77=='_'||(LA12_77>='a' && LA12_77<='z')) ) {
                            alt12=40;
                        }
                        else {
                            alt12=3;}
                    }
                    else {
                        alt12=40;}
                }
                else {
                    alt12=40;}
            }
            else {
                alt12=40;}
            break;
        case 'a':
            switch ( input.LA(2) ) {
            case 'n':
                int LA12_38 = input.LA(3);
                if ( (LA12_38=='d') ) {
                    int LA12_62 = input.LA(4);
                    if ( ((LA12_62>='0' && LA12_62<='9')||(LA12_62>='A' && LA12_62<='Z')||LA12_62=='_'||(LA12_62>='a' && LA12_62<='z')) ) {
                        alt12=40;
                    }
                    else {
                        alt12=13;}
                }
                else {
                    alt12=40;}
                break;
            case 'l':
                int LA12_39 = input.LA(3);
                if ( (LA12_39=='t') ) {
                    int LA12_63 = input.LA(4);
                    if ( ((LA12_63>='0' && LA12_63<='9')||(LA12_63>='A' && LA12_63<='Z')||LA12_63=='_'||(LA12_63>='a' && LA12_63<='z')) ) {
                        alt12=40;
                    }
                    else {
                        alt12=4;}
                }
                else {
                    alt12=40;}
                break;
            default:
                alt12=40;}

            break;
        case '(':
            alt12=5;
            break;
        case ')':
            alt12=6;
            break;
        case 'r':
            int LA12_7 = input.LA(2);
            if ( (LA12_7=='e') ) {
                int LA12_40 = input.LA(3);
                if ( (LA12_40=='p') ) {
                    switch ( input.LA(4) ) {
                    case 'l':
                        int LA12_80 = input.LA(5);
                        if ( (LA12_80=='a') ) {
                            int LA12_94 = input.LA(6);
                            if ( (LA12_94=='c') ) {
                                int LA12_105 = input.LA(7);
                                if ( (LA12_105=='e') ) {
                                    switch ( input.LA(8) ) {
                                    case 'A':
                                        int LA12_117 = input.LA(9);
                                        if ( (LA12_117=='l') ) {
                                            int LA12_121 = input.LA(10);
                                            if ( (LA12_121=='l') ) {
                                                int LA12_123 = input.LA(11);
                                                if ( ((LA12_123>='0' && LA12_123<='9')||(LA12_123>='A' && LA12_123<='Z')||LA12_123=='_'||(LA12_123>='a' && LA12_123<='z')) ) {
                                                    alt12=40;
                                                }
                                                else {
                                                    alt12=31;}
                                            }
                                            else {
                                                alt12=40;}
                                        }
                                        else {
                                            alt12=40;}
                                        break;
                                    case '0':
                                    case '1':
                                    case '2':
                                    case '3':
                                    case '4':
                                    case '5':
                                    case '6':
                                    case '7':
                                    case '8':
                                    case '9':
                                    case 'B':
                                    case 'C':
                                    case 'D':
                                    case 'E':
                                    case 'F':
                                    case 'G':
                                    case 'H':
                                    case 'I':
                                    case 'J':
                                    case 'K':
                                    case 'L':
                                    case 'M':
                                    case 'N':
                                    case 'O':
                                    case 'P':
                                    case 'Q':
                                    case 'R':
                                    case 'S':
                                    case 'T':
                                    case 'U':
                                    case 'V':
                                    case 'W':
                                    case 'X':
                                    case 'Y':
                                    case 'Z':
                                    case '_':
                                    case 'a':
                                    case 'b':
                                    case 'c':
                                    case 'd':
                                    case 'e':
                                    case 'f':
                                    case 'g':
                                    case 'h':
                                    case 'i':
                                    case 'j':
                                    case 'k':
                                    case 'l':
                                    case 'm':
                                    case 'n':
                                    case 'o':
                                    case 'p':
                                    case 'q':
                                    case 'r':
                                    case 's':
                                    case 't':
                                    case 'u':
                                    case 'v':
                                    case 'w':
                                    case 'x':
                                    case 'y':
                                    case 'z':
                                        alt12=40;
                                        break;
                                    default:
                                        alt12=29;}

                                }
                                else {
                                    alt12=40;}
                            }
                            else {
                                alt12=40;}
                        }
                        else {
                            alt12=40;}
                        break;
                    case 'e':
                        int LA12_81 = input.LA(5);
                        if ( (LA12_81=='a') ) {
                            int LA12_95 = input.LA(6);
                            if ( (LA12_95=='t') ) {
                                int LA12_106 = input.LA(7);
                                if ( ((LA12_106>='0' && LA12_106<='9')||(LA12_106>='A' && LA12_106<='Z')||LA12_106=='_'||(LA12_106>='a' && LA12_106<='z')) ) {
                                    alt12=40;
                                }
                                else {
                                    alt12=7;}
                            }
                            else {
                                alt12=40;}
                        }
                        else {
                            alt12=40;}
                        break;
                    default:
                        alt12=40;}

                }
                else {
                    alt12=40;}
            }
            else {
                alt12=40;}
            break;
        case 'w':
            int LA12_8 = input.LA(2);
            if ( (LA12_8=='h') ) {
                int LA12_41 = input.LA(3);
                if ( (LA12_41=='i') ) {
                    int LA12_65 = input.LA(4);
                    if ( (LA12_65=='l') ) {
                        int LA12_82 = input.LA(5);
                        if ( (LA12_82=='e') ) {
                            int LA12_96 = input.LA(6);
                            if ( ((LA12_96>='0' && LA12_96<='9')||(LA12_96>='A' && LA12_96<='Z')||LA12_96=='_'||(LA12_96>='a' && LA12_96<='z')) ) {
                                alt12=40;
                            }
                            else {
                                alt12=8;}
                        }
                        else {
                            alt12=40;}
                    }
                    else {
                        alt12=40;}
                }
                else {
                    alt12=40;}
            }
            else {
                alt12=40;}
            break;
        case 'f':
            switch ( input.LA(2) ) {
            case 'o':
                int LA12_42 = input.LA(3);
                if ( (LA12_42=='r') ) {
                    int LA12_66 = input.LA(4);
                    if ( (LA12_66==' ') ) {
                        alt12=9;
                    }
                    else {
                        alt12=40;}
                }
                else {
                    alt12=40;}
                break;
            case 'a':
                int LA12_43 = input.LA(3);
                if ( (LA12_43=='l') ) {
                    int LA12_67 = input.LA(4);
                    if ( (LA12_67=='s') ) {
                        int LA12_84 = input.LA(5);
                        if ( (LA12_84=='e') ) {
                            int LA12_97 = input.LA(6);
                            if ( ((LA12_97>='0' && LA12_97<='9')||(LA12_97>='A' && LA12_97<='Z')||LA12_97=='_'||(LA12_97>='a' && LA12_97<='z')) ) {
                                alt12=40;
                            }
                            else {
                                alt12=38;}
                        }
                        else {
                            alt12=40;}
                    }
                    else {
                        alt12=40;}
                }
                else {
                    alt12=40;}
                break;
            case 'r':
                int LA12_44 = input.LA(3);
                if ( (LA12_44=='o') ) {
                    int LA12_68 = input.LA(4);
                    if ( (LA12_68=='m') ) {
                        int LA12_85 = input.LA(5);
                        if ( ((LA12_85>='0' && LA12_85<='9')||(LA12_85>='A' && LA12_85<='Z')||LA12_85=='_'||(LA12_85>='a' && LA12_85<='z')) ) {
                            alt12=40;
                        }
                        else {
                            alt12=32;}
                    }
                    else {
                        alt12=40;}
                }
                else {
                    alt12=40;}
                break;
            default:
                alt12=40;}

            break;
        case ':':
            alt12=10;
            break;
        case '=':
            int LA12_11 = input.LA(2);
            if ( (LA12_11=='=') ) {
                alt12=14;
            }
            else {
                alt12=11;}
            break;
        case 'o':
            int LA12_12 = input.LA(2);
            if ( (LA12_12=='r') ) {
                int LA12_47 = input.LA(3);
                if ( ((LA12_47>='0' && LA12_47<='9')||(LA12_47>='A' && LA12_47<='Z')||LA12_47=='_'||(LA12_47>='a' && LA12_47<='z')) ) {
                    alt12=40;
                }
                else {
                    alt12=12;}
            }
            else {
                alt12=40;}
            break;
        case '!':
            alt12=15;
            break;
        case '<':
            int LA12_14 = input.LA(2);
            if ( (LA12_14=='=') ) {
                alt12=17;
            }
            else {
                alt12=16;}
            break;
        case '>':
            int LA12_15 = input.LA(2);
            if ( (LA12_15=='=') ) {
                alt12=19;
            }
            else {
                alt12=18;}
            break;
        case '+':
            alt12=20;
            break;
        case '-':
            alt12=21;
            break;
        case '*':
            alt12=22;
            break;
        case '/':
            alt12=23;
            break;
        case '%':
            alt12=24;
            break;
        case '.':
            int LA12_21 = input.LA(2);
            if ( ((LA12_21>='0' && LA12_21<='9')) ) {
                alt12=39;
            }
            else {
                alt12=25;}
            break;
        case '[':
            alt12=26;
            break;
        case ']':
            alt12=27;
            break;
        case ',':
            alt12=30;
            break;
        case 's':
            switch ( input.LA(2) ) {
            case 'a':
                int LA12_53 = input.LA(3);
                if ( (LA12_53=='f') ) {
                    int LA12_70 = input.LA(4);
                    if ( (LA12_70=='e') ) {
                        int LA12_86 = input.LA(5);
                        if ( ((LA12_86>='0' && LA12_86<='9')||(LA12_86>='A' && LA12_86<='Z')||LA12_86=='_'||(LA12_86>='a' && LA12_86<='z')) ) {
                            alt12=40;
                        }
                        else {
                            alt12=34;}
                    }
                    else {
                        alt12=40;}
                }
                else {
                    alt12=40;}
                break;
            case 'h':
                int LA12_54 = input.LA(3);
                if ( (LA12_54=='a') ) {
                    int LA12_71 = input.LA(4);
                    if ( (LA12_71=='p') ) {
                        int LA12_87 = input.LA(5);
                        if ( (LA12_87=='e') ) {
                            int LA12_100 = input.LA(6);
                            if ( ((LA12_100>='0' && LA12_100<='9')||(LA12_100>='A' && LA12_100<='Z')||LA12_100=='_'||(LA12_100>='a' && LA12_100<='z')) ) {
                                alt12=40;
                            }
                            else {
                                alt12=33;}
                        }
                        else {
                            alt12=40;}
                    }
                    else {
                        alt12=40;}
                }
                else {
                    alt12=40;}
                break;
            default:
                alt12=40;}

            break;
        case '_':
            int LA12_26 = input.LA(2);
            if ( (LA12_26=='_') ) {
                int LA12_55 = input.LA(3);
                if ( (LA12_55=='o') ) {
                    int LA12_72 = input.LA(4);
                    if ( (LA12_72=='b') ) {
                        int LA12_88 = input.LA(5);
                        if ( (LA12_88=='j') ) {
                            int LA12_101 = input.LA(6);
                            if ( (LA12_101=='e') ) {
                                int LA12_110 = input.LA(7);
                                if ( (LA12_110=='c') ) {
                                    int LA12_115 = input.LA(8);
                                    if ( (LA12_115=='t') ) {
                                        int LA12_119 = input.LA(9);
                                        if ( (LA12_119=='_') ) {
                                            int LA12_122 = input.LA(10);
                                            if ( (LA12_122=='_') ) {
                                                int LA12_124 = input.LA(11);
                                                if ( ((LA12_124>='0' && LA12_124<='9')||(LA12_124>='A' && LA12_124<='Z')||LA12_124=='_'||(LA12_124>='a' && LA12_124<='z')) ) {
                                                    alt12=40;
                                                }
                                                else {
                                                    alt12=35;}
                                            }
                                            else {
                                                alt12=40;}
                                        }
                                        else {
                                            alt12=40;}
                                    }
                                    else {
                                        alt12=40;}
                                }
                                else {
                                    alt12=40;}
                            }
                            else {
                                alt12=40;}
                        }
                        else {
                            alt12=40;}
                    }
                    else {
                        alt12=40;}
                }
                else {
                    alt12=40;}
            }
            else {
                alt12=40;}
            break;
        case 't':
            int LA12_27 = input.LA(2);
            if ( (LA12_27=='r') ) {
                int LA12_56 = input.LA(3);
                if ( (LA12_56=='u') ) {
                    int LA12_73 = input.LA(4);
                    if ( (LA12_73=='e') ) {
                        int LA12_89 = input.LA(5);
                        if ( ((LA12_89>='0' && LA12_89<='9')||(LA12_89>='A' && LA12_89<='Z')||LA12_89=='_'||(LA12_89>='a' && LA12_89<='z')) ) {
                            alt12=40;
                        }
                        else {
                            alt12=37;}
                    }
                    else {
                        alt12=40;}
                }
                else {
                    alt12=40;}
            }
            else {
                alt12=40;}
            break;
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
            alt12=39;
            break;
        case 'A':
        case 'B':
        case 'C':
        case 'D':
        case 'E':
        case 'F':
        case 'G':
        case 'H':
        case 'I':
        case 'J':
        case 'K':
        case 'L':
        case 'M':
        case 'N':
        case 'O':
        case 'P':
        case 'Q':
        case 'R':
        case 'S':
        case 'T':
        case 'U':
        case 'V':
        case 'W':
        case 'X':
        case 'Y':
        case 'Z':
        case 'b':
        case 'c':
        case 'g':
        case 'h':
        case 'j':
        case 'k':
        case 'l':
        case 'm':
        case 'n':
        case 'p':
        case 'q':
        case 'u':
        case 'v':
        case 'x':
        case 'y':
        case 'z':
            alt12=40;
            break;
        case '\"':
            alt12=41;
            break;
        case ';':
            alt12=42;
            break;
        case '\t':
        case '\n':
        case '\r':
        case ' ':
            alt12=43;
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("1:1: Tokens : ( T60 | T61 | T62 | T63 | T64 | T65 | T66 | T67 | T68 | T69 | T70 | T71 | T72 | T73 | T74 | T75 | T76 | T77 | T78 | T79 | T80 | T81 | T82 | T83 | T84 | T85 | T86 | T87 | T88 | T89 | T90 | T91 | T92 | SAFE | OBJECT | INFINITY | TRUE | FALSE | NUM | ID | STR_LITERAL | LINE_COMMENT | WS );", 12, 0, input);

            throw nvae;
        }

        switch (alt12) {
            case 1 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:10: T60
                {
                mT60(); 

                }
                break;
            case 2 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:14: T61
                {
                mT61(); 

                }
                break;
            case 3 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:18: T62
                {
                mT62(); 

                }
                break;
            case 4 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:22: T63
                {
                mT63(); 

                }
                break;
            case 5 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:26: T64
                {
                mT64(); 

                }
                break;
            case 6 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:30: T65
                {
                mT65(); 

                }
                break;
            case 7 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:34: T66
                {
                mT66(); 

                }
                break;
            case 8 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:38: T67
                {
                mT67(); 

                }
                break;
            case 9 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:42: T68
                {
                mT68(); 

                }
                break;
            case 10 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:46: T69
                {
                mT69(); 

                }
                break;
            case 11 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:50: T70
                {
                mT70(); 

                }
                break;
            case 12 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:54: T71
                {
                mT71(); 

                }
                break;
            case 13 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:58: T72
                {
                mT72(); 

                }
                break;
            case 14 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:62: T73
                {
                mT73(); 

                }
                break;
            case 15 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:66: T74
                {
                mT74(); 

                }
                break;
            case 16 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:70: T75
                {
                mT75(); 

                }
                break;
            case 17 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:74: T76
                {
                mT76(); 

                }
                break;
            case 18 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:78: T77
                {
                mT77(); 

                }
                break;
            case 19 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:82: T78
                {
                mT78(); 

                }
                break;
            case 20 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:86: T79
                {
                mT79(); 

                }
                break;
            case 21 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:90: T80
                {
                mT80(); 

                }
                break;
            case 22 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:94: T81
                {
                mT81(); 

                }
                break;
            case 23 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:98: T82
                {
                mT82(); 

                }
                break;
            case 24 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:102: T83
                {
                mT83(); 

                }
                break;
            case 25 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:106: T84
                {
                mT84(); 

                }
                break;
            case 26 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:110: T85
                {
                mT85(); 

                }
                break;
            case 27 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:114: T86
                {
                mT86(); 

                }
                break;
            case 28 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:118: T87
                {
                mT87(); 

                }
                break;
            case 29 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:122: T88
                {
                mT88(); 

                }
                break;
            case 30 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:126: T89
                {
                mT89(); 

                }
                break;
            case 31 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:130: T90
                {
                mT90(); 

                }
                break;
            case 32 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:134: T91
                {
                mT91(); 

                }
                break;
            case 33 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:138: T92
                {
                mT92(); 

                }
                break;
            case 34 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:142: SAFE
                {
                mSAFE(); 

                }
                break;
            case 35 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:147: OBJECT
                {
                mOBJECT(); 

                }
                break;
            case 36 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:154: INFINITY
                {
                mINFINITY(); 

                }
                break;
            case 37 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:163: TRUE
                {
                mTRUE(); 

                }
                break;
            case 38 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:168: FALSE
                {
                mFALSE(); 

                }
                break;
            case 39 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:174: NUM
                {
                mNUM(); 

                }
                break;
            case 40 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:178: ID
                {
                mID(); 

                }
                break;
            case 41 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:181: STR_LITERAL
                {
                mSTR_LITERAL(); 

                }
                break;
            case 42 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:193: LINE_COMMENT
                {
                mLINE_COMMENT(); 

                }
                break;
            case 43 :
                // src/org/six11/flatcad/flatlang/FlatLang.g:1:206: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA4 dfa4 = new DFA4(this);
    public static final String DFA4_eotS =
        "\1\uffff\1\3\2\uffff";
    public static final String DFA4_eofS =
        "\4\uffff";
    public static final String DFA4_minS =
        "\2\56\2\uffff";
    public static final String DFA4_maxS =
        "\2\71\2\uffff";
    public static final String DFA4_acceptS =
        "\2\uffff\1\2\1\1";
    public static final String DFA4_specialS =
        "\4\uffff}>";
    public static final String[] DFA4_transition = {
        "\1\2\1\uffff\12\1",
        "\1\2\1\uffff\12\1",
        "",
        ""
    };

    class DFA4 extends DFA {
        public DFA4(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 4;
            this.eot = DFA.unpackEncodedString(DFA4_eotS);
            this.eof = DFA.unpackEncodedString(DFA4_eofS);
            this.min = DFA.unpackEncodedStringToUnsignedChars(DFA4_minS);
            this.max = DFA.unpackEncodedStringToUnsignedChars(DFA4_maxS);
            this.accept = DFA.unpackEncodedString(DFA4_acceptS);
            this.special = DFA.unpackEncodedString(DFA4_specialS);
            int numStates = DFA4_transition.length;
            this.transition = new short[numStates][];
            for (int i=0; i<numStates; i++) {
                transition[i] = DFA.unpackEncodedString(DFA4_transition[i]);
            }
        }
        public String getDescription() {
            return "313:1: NUM : ( ( INT )+ | ( INT )* '.' ( INT )+ );";
        }
    }
 

}