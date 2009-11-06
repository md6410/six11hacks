// $ANTLR 3.0b6 src/org/six11/flatcad/flatlang/FlatLang.g 2008-03-04 16:45:40

package org.six11.flatcad.flatlang;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class FlatLangParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "PROG", "FUNCTION_DEF_SIGNATURE", "FUNCTION_CALL", "STATEMENT", "PARAM_LIST", "PARAM", "EXPR", "BLOCK", "REPLACE", "REPLACE_ALL", "SHAPE", "FROM", "N_ADD_EXPR", "N_MINUS_EXPR", "N_MULT_EXPR", "N_DIV_EXPR", "N_MODULO_EXPR", "N_ASSIGN_EXPR", "N_UNARY_EXPR", "N_UNARY_NEG_EXPR", "N_UNARY_POS_EXPR", "N_LT_EXPR", "N_LTEQ_EXPR", "N_GTEQ_EXPR", "N_GT_EXPR", "N_EQ_EXPR", "N_NEQ_EXPR", "N_AND_EXPR", "N_OR_EXPR", "WHILE_STATEMENT", "REPEAT_STATEMENT", "IF_STATEMENT", "IF_ALT", "ELSE_ALT", "LITERAL", "INDEX_EXPR", "LIST_EXPR", "MEMBER_EXPR", "MEMBER_EXPR_DOT", "FOR_EACH_STATEMENT", "ID", "TRUE", "FALSE", "INFINITY", "OBJECT", "SAFE", "NUM", "STR_LITERAL", "INT", "LETTER", "ESC_SEQ", "UNI_ESC", "OCT_ESC", "HEX_DIGIT", "LINE_COMMENT", "WS", "'done'", "'if'", "'else'", "'alt'", "'('", "')'", "'repeat'", "'while'", "'for each'", "':'", "'='", "'or'", "'and'", "'=='", "'!='", "'<'", "'<='", "'>'", "'>='", "'+'", "'-'", "'*'", "'/'", "'%'", "'.'", "'['", "']'", "'define'", "'replace'", "','", "'replaceAll'", "'from'", "'shape'"
    };
    public static final int N_LTEQ_EXPR=26;
    public static final int N_UNARY_POS_EXPR=24;
    public static final int STATEMENT=7;
    public static final int N_EQ_EXPR=29;
    public static final int N_DIV_EXPR=19;
    public static final int N_GTEQ_EXPR=27;
    public static final int REPEAT_STATEMENT=34;
    public static final int EXPR=10;
    public static final int OCT_ESC=56;
    public static final int FALSE=46;
    public static final int N_ASSIGN_EXPR=21;
    public static final int IF_ALT=36;
    public static final int PROG=4;
    public static final int SHAPE=14;
    public static final int BLOCK=11;
    public static final int LITERAL=38;
    public static final int LIST_EXPR=40;
    public static final int IF_STATEMENT=35;
    public static final int INT=52;
    public static final int INFINITY=47;
    public static final int PARAM=9;
    public static final int ELSE_ALT=37;
    public static final int N_AND_EXPR=31;
    public static final int MEMBER_EXPR=41;
    public static final int INDEX_EXPR=39;
    public static final int HEX_DIGIT=57;
    public static final int STR_LITERAL=51;
    public static final int N_MULT_EXPR=18;
    public static final int N_GT_EXPR=28;
    public static final int WHILE_STATEMENT=33;
    public static final int SAFE=49;
    public static final int ID=44;
    public static final int FROM=15;
    public static final int OBJECT=48;
    public static final int LETTER=53;
    public static final int N_MODULO_EXPR=20;
    public static final int WS=59;
    public static final int PARAM_LIST=8;
    public static final int REPLACE=12;
    public static final int FUNCTION_CALL=6;
    public static final int N_OR_EXPR=32;
    public static final int LINE_COMMENT=58;
    public static final int N_NEQ_EXPR=30;
    public static final int N_UNARY_NEG_EXPR=23;
    public static final int ESC_SEQ=54;
    public static final int UNI_ESC=55;
    public static final int N_LT_EXPR=25;
    public static final int N_MINUS_EXPR=17;
    public static final int REPLACE_ALL=13;
    public static final int EOF=-1;
    public static final int NUM=50;
    public static final int MEMBER_EXPR_DOT=42;
    public static final int N_ADD_EXPR=16;
    public static final int FUNCTION_DEF_SIGNATURE=5;
    public static final int TRUE=45;
    public static final int FOR_EACH_STATEMENT=43;
    public static final int N_UNARY_EXPR=22;

        public FlatLangParser(TokenStream input) {
            super(input);
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return tokenNames; }
    public String getGrammarFileName() { return "src/org/six11/flatcad/flatlang/FlatLang.g"; }


    public static class prog_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start prog
    // src/org/six11/flatcad/flatlang/FlatLang.g:60:1: prog : ( statement )* -> ^( PROG ( statement )* ) ;
    public prog_return prog() throws RecognitionException {
        prog_return retval = new prog_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        statement_return statement1 = null;

        List list_statement=new ArrayList();

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:60:8: ( ( statement )* -> ^( PROG ( statement )* ) )
            // src/org/six11/flatcad/flatlang/FlatLang.g:60:8: ( statement )*
            {
            // src/org/six11/flatcad/flatlang/FlatLang.g:60:8: ( statement )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);
                if ( ((LA1_0>=ID && LA1_0<=STR_LITERAL)||LA1_0==61||LA1_0==64||(LA1_0>=66 && LA1_0<=68)||(LA1_0>=79 && LA1_0<=80)||LA1_0==85||(LA1_0>=87 && LA1_0<=88)||(LA1_0>=90 && LA1_0<=92)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:60:8: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_prog262);
            	    statement1=statement();
            	    _fsp--;

            	    list_statement.add(statement1.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 60:26: -> ^( PROG ( statement )* )
            {
                // src/org/six11/flatcad/flatlang/FlatLang.g:60:29: ^( PROG ( statement )* )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(PROG, "PROG"), root_1);

                // src/org/six11/flatcad/flatlang/FlatLang.g:60:36: ( statement )*
                {
                int n_1 = list_statement == null ? 0 : list_statement.size();
                 


                for (int i_1=0; i_1<n_1; i_1++) {
                    adaptor.addChild(root_1, list_statement.get(i_1));

                }
                }

                adaptor.addChild(root_0, root_1);
                }

            }



            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end prog

    public static class block_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start block
    // src/org/six11/flatcad/flatlang/FlatLang.g:63:1: block : ( nonDefStatement )+ -> ^( BLOCK ( nonDefStatement )+ ) ;
    public block_return block() throws RecognitionException {
        block_return retval = new block_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        nonDefStatement_return nonDefStatement2 = null;

        List list_nonDefStatement=new ArrayList();

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:64:9: ( ( nonDefStatement )+ -> ^( BLOCK ( nonDefStatement )+ ) )
            // src/org/six11/flatcad/flatlang/FlatLang.g:64:9: ( nonDefStatement )+
            {
            // src/org/six11/flatcad/flatlang/FlatLang.g:64:9: ( nonDefStatement )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);
                if ( ((LA2_0>=ID && LA2_0<=OBJECT)||(LA2_0>=NUM && LA2_0<=STR_LITERAL)||LA2_0==61||LA2_0==64||(LA2_0>=66 && LA2_0<=68)||(LA2_0>=79 && LA2_0<=80)||LA2_0==85||LA2_0==88||(LA2_0>=90 && LA2_0<=92)) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:64:9: nonDefStatement
            	    {
            	    pushFollow(FOLLOW_nonDefStatement_in_block295);
            	    nonDefStatement2=nonDefStatement();
            	    _fsp--;

            	    list_nonDefStatement.add(nonDefStatement2.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);


            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 64:31: -> ^( BLOCK ( nonDefStatement )+ )
            {
                // src/org/six11/flatcad/flatlang/FlatLang.g:64:34: ^( BLOCK ( nonDefStatement )+ )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(BLOCK, "BLOCK"), root_1);

                // src/org/six11/flatcad/flatlang/FlatLang.g:64:42: ( nonDefStatement )+
                {
                int n_1 = list_nonDefStatement == null ? 0 : list_nonDefStatement.size();
                 


                if ( n_1==0 ) throw new RuntimeException("Must have more than one element for (...)+ loops");
                for (int i_1=0; i_1<n_1; i_1++) {
                    adaptor.addChild(root_1, list_nonDefStatement.get(i_1));

                }
                }

                adaptor.addChild(root_0, root_1);
                }

            }



            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end block

    public static class statement_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start statement
    // src/org/six11/flatcad/flatlang/FlatLang.g:67:1: statement : ( n_definition | nonDefStatement );
    public statement_return statement() throws RecognitionException {
        statement_return retval = new statement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        n_definition_return n_definition3 = null;

        nonDefStatement_return nonDefStatement4 = null;



        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:68:4: ( n_definition | nonDefStatement )
            int alt3=2;
            int LA3_0 = input.LA(1);
            if ( (LA3_0==SAFE||LA3_0==87) ) {
                alt3=1;
            }
            else if ( ((LA3_0>=ID && LA3_0<=OBJECT)||(LA3_0>=NUM && LA3_0<=STR_LITERAL)||LA3_0==61||LA3_0==64||(LA3_0>=66 && LA3_0<=68)||(LA3_0>=79 && LA3_0<=80)||LA3_0==85||LA3_0==88||(LA3_0>=90 && LA3_0<=92)) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("67:1: statement : ( n_definition | nonDefStatement );", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:68:4: n_definition
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_n_definition_in_statement324);
                    n_definition3=n_definition();
                    _fsp--;

                    adaptor.addChild(root_0, n_definition3.getTree());

                    }
                    break;
                case 2 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:69:4: nonDefStatement
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_nonDefStatement_in_statement329);
                    nonDefStatement4=nonDefStatement();
                    _fsp--;

                    adaptor.addChild(root_0, nonDefStatement4.getTree());

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end statement

    public static class nonDefStatement_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start nonDefStatement
    // src/org/six11/flatcad/flatlang/FlatLang.g:72:1: nonDefStatement : ( replaceStatement | replaceAllStatement | shapeStatement | fromStatement | whileStatement | repeatStatement | ifStatement | forEachStatement | statementExpression -> ^( STATEMENT statementExpression ) );
    public nonDefStatement_return nonDefStatement() throws RecognitionException {
        nonDefStatement_return retval = new nonDefStatement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        replaceStatement_return replaceStatement5 = null;

        replaceAllStatement_return replaceAllStatement6 = null;

        shapeStatement_return shapeStatement7 = null;

        fromStatement_return fromStatement8 = null;

        whileStatement_return whileStatement9 = null;

        repeatStatement_return repeatStatement10 = null;

        ifStatement_return ifStatement11 = null;

        forEachStatement_return forEachStatement12 = null;

        statementExpression_return statementExpression13 = null;

        List list_statementExpression=new ArrayList();

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:73:9: ( replaceStatement | replaceAllStatement | shapeStatement | fromStatement | whileStatement | repeatStatement | ifStatement | forEachStatement | statementExpression -> ^( STATEMENT statementExpression ) )
            int alt4=9;
            switch ( input.LA(1) ) {
            case 88:
                alt4=1;
                break;
            case 90:
                alt4=2;
                break;
            case 92:
                alt4=3;
                break;
            case 91:
                alt4=4;
                break;
            case 67:
                alt4=5;
                break;
            case 66:
                alt4=6;
                break;
            case 61:
                alt4=7;
                break;
            case 68:
                alt4=8;
                break;
            case ID:
            case TRUE:
            case FALSE:
            case INFINITY:
            case OBJECT:
            case NUM:
            case STR_LITERAL:
            case 64:
            case 79:
            case 80:
            case 85:
                alt4=9;
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("72:1: nonDefStatement : ( replaceStatement | replaceAllStatement | shapeStatement | fromStatement | whileStatement | repeatStatement | ifStatement | forEachStatement | statementExpression -> ^( STATEMENT statementExpression ) );", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:73:9: replaceStatement
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_replaceStatement_in_nonDefStatement345);
                    replaceStatement5=replaceStatement();
                    _fsp--;

                    adaptor.addChild(root_0, replaceStatement5.getTree());

                    }
                    break;
                case 2 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:74:9: replaceAllStatement
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_replaceAllStatement_in_nonDefStatement355);
                    replaceAllStatement6=replaceAllStatement();
                    _fsp--;

                    adaptor.addChild(root_0, replaceAllStatement6.getTree());

                    }
                    break;
                case 3 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:75:9: shapeStatement
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_shapeStatement_in_nonDefStatement365);
                    shapeStatement7=shapeStatement();
                    _fsp--;

                    adaptor.addChild(root_0, shapeStatement7.getTree());

                    }
                    break;
                case 4 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:76:9: fromStatement
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_fromStatement_in_nonDefStatement375);
                    fromStatement8=fromStatement();
                    _fsp--;

                    adaptor.addChild(root_0, fromStatement8.getTree());

                    }
                    break;
                case 5 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:77:9: whileStatement
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_whileStatement_in_nonDefStatement385);
                    whileStatement9=whileStatement();
                    _fsp--;

                    adaptor.addChild(root_0, whileStatement9.getTree());

                    }
                    break;
                case 6 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:78:9: repeatStatement
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_repeatStatement_in_nonDefStatement395);
                    repeatStatement10=repeatStatement();
                    _fsp--;

                    adaptor.addChild(root_0, repeatStatement10.getTree());

                    }
                    break;
                case 7 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:79:9: ifStatement
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_ifStatement_in_nonDefStatement405);
                    ifStatement11=ifStatement();
                    _fsp--;

                    adaptor.addChild(root_0, ifStatement11.getTree());

                    }
                    break;
                case 8 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:80:7: forEachStatement
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_forEachStatement_in_nonDefStatement413);
                    forEachStatement12=forEachStatement();
                    _fsp--;

                    adaptor.addChild(root_0, forEachStatement12.getTree());

                    }
                    break;
                case 9 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:81:9: statementExpression
                    {
                    pushFollow(FOLLOW_statementExpression_in_nonDefStatement423);
                    statementExpression13=statementExpression();
                    _fsp--;

                    list_statementExpression.add(statementExpression13.getTree());

                    // AST REWRITE
                    int i_0 = 0;
                    retval.tree = root_0;
                    root_0 = (CommonTree)adaptor.nil();
                    // 81:31: -> ^( STATEMENT statementExpression )
                    {
                        // src/org/six11/flatcad/flatlang/FlatLang.g:81:34: ^( STATEMENT statementExpression )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(STATEMENT, "STATEMENT"), root_1);

                        adaptor.addChild(root_1, list_statementExpression.get(i_0));

                        adaptor.addChild(root_0, root_1);
                        }

                    }



                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end nonDefStatement

    public static class ifStatement_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start ifStatement
    // src/org/six11/flatcad/flatlang/FlatLang.g:84:1: ifStatement : ifStatementStart ( altStatement )* ( elseStatement )? 'done' -> ^( IF_STATEMENT ifStatementStart ( altStatement )* ( elseStatement )? ) ;
    public ifStatement_return ifStatement() throws RecognitionException {
        ifStatement_return retval = new ifStatement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal17=null;
        ifStatementStart_return ifStatementStart14 = null;

        altStatement_return altStatement15 = null;

        elseStatement_return elseStatement16 = null;

        List list_altStatement=new ArrayList();
        List list_elseStatement=new ArrayList();
        List list_ifStatementStart=new ArrayList();
        List list_60=new ArrayList();
        CommonTree string_literal17_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:85:4: ( ifStatementStart ( altStatement )* ( elseStatement )? 'done' -> ^( IF_STATEMENT ifStatementStart ( altStatement )* ( elseStatement )? ) )
            // src/org/six11/flatcad/flatlang/FlatLang.g:85:4: ifStatementStart ( altStatement )* ( elseStatement )? 'done'
            {
            pushFollow(FOLLOW_ifStatementStart_in_ifStatement447);
            ifStatementStart14=ifStatementStart();
            _fsp--;

            list_ifStatementStart.add(ifStatementStart14.getTree());
            // src/org/six11/flatcad/flatlang/FlatLang.g:86:3: ( altStatement )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);
                if ( (LA5_0==63) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:86:3: altStatement
            	    {
            	    pushFollow(FOLLOW_altStatement_in_ifStatement452);
            	    altStatement15=altStatement();
            	    _fsp--;

            	    list_altStatement.add(altStatement15.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            // src/org/six11/flatcad/flatlang/FlatLang.g:87:3: ( elseStatement )?
            int alt6=2;
            int LA6_0 = input.LA(1);
            if ( (LA6_0==62) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:87:3: elseStatement
                    {
                    pushFollow(FOLLOW_elseStatement_in_ifStatement457);
                    elseStatement16=elseStatement();
                    _fsp--;

                    list_elseStatement.add(elseStatement16.getTree());

                    }
                    break;

            }

            string_literal17=(Token)input.LT(1);
            match(input,60,FOLLOW_60_in_ifStatement462); 
            list_60.add(string_literal17);


            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 89:3: -> ^( IF_STATEMENT ifStatementStart ( altStatement )* ( elseStatement )? )
            {
                // src/org/six11/flatcad/flatlang/FlatLang.g:89:6: ^( IF_STATEMENT ifStatementStart ( altStatement )* ( elseStatement )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(IF_STATEMENT, "IF_STATEMENT"), root_1);

                adaptor.addChild(root_1, list_ifStatementStart.get(i_0));
                // src/org/six11/flatcad/flatlang/FlatLang.g:89:38: ( altStatement )*
                {
                int n_1 = list_altStatement == null ? 0 : list_altStatement.size();
                 


                for (int i_1=0; i_1<n_1; i_1++) {
                    adaptor.addChild(root_1, list_altStatement.get(i_1));

                }
                }
                // src/org/six11/flatcad/flatlang/FlatLang.g:89:52: ( elseStatement )?
                {
                int n_1 = list_elseStatement == null ? 0 : list_elseStatement.size();
                 


                if ( n_1 > 1 ) throw new RuntimeException("elseStatement list has > 1 elements");
                if ( n_1==1 ) {
                    int i_1 = 0;
                    adaptor.addChild(root_1, list_elseStatement.get(i_1));

                    }
                }

                adaptor.addChild(root_0, root_1);
                }

            }



            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end ifStatement

    public static class ifStatementStart_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start ifStatementStart
    // src/org/six11/flatcad/flatlang/FlatLang.g:92:1: ifStatementStart : 'if' parExpression ( block )? -> ^( IF_ALT parExpression ( block )? ) ;
    public ifStatementStart_return ifStatementStart() throws RecognitionException {
        ifStatementStart_return retval = new ifStatementStart_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal18=null;
        parExpression_return parExpression19 = null;

        block_return block20 = null;

        List list_parExpression=new ArrayList();
        List list_block=new ArrayList();
        List list_61=new ArrayList();
        CommonTree string_literal18_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:93:4: ( 'if' parExpression ( block )? -> ^( IF_ALT parExpression ( block )? ) )
            // src/org/six11/flatcad/flatlang/FlatLang.g:93:4: 'if' parExpression ( block )?
            {
            string_literal18=(Token)input.LT(1);
            match(input,61,FOLLOW_61_in_ifStatementStart489); 
            list_61.add(string_literal18);

            pushFollow(FOLLOW_parExpression_in_ifStatementStart491);
            parExpression19=parExpression();
            _fsp--;

            list_parExpression.add(parExpression19.getTree());
            // src/org/six11/flatcad/flatlang/FlatLang.g:93:23: ( block )?
            int alt7=2;
            int LA7_0 = input.LA(1);
            if ( ((LA7_0>=ID && LA7_0<=OBJECT)||(LA7_0>=NUM && LA7_0<=STR_LITERAL)||LA7_0==61||LA7_0==64||(LA7_0>=66 && LA7_0<=68)||(LA7_0>=79 && LA7_0<=80)||LA7_0==85||LA7_0==88||(LA7_0>=90 && LA7_0<=92)) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:93:23: block
                    {
                    pushFollow(FOLLOW_block_in_ifStatementStart493);
                    block20=block();
                    _fsp--;

                    list_block.add(block20.getTree());

                    }
                    break;

            }


            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 93:30: -> ^( IF_ALT parExpression ( block )? )
            {
                // src/org/six11/flatcad/flatlang/FlatLang.g:93:33: ^( IF_ALT parExpression ( block )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(IF_ALT, "IF_ALT"), root_1);

                adaptor.addChild(root_1, list_parExpression.get(i_0));
                // src/org/six11/flatcad/flatlang/FlatLang.g:93:56: ( block )?
                {
                int n_1 = list_block == null ? 0 : list_block.size();
                 


                if ( n_1 > 1 ) throw new RuntimeException("block list has > 1 elements");
                if ( n_1==1 ) {
                    int i_1 = 0;
                    adaptor.addChild(root_1, list_block.get(i_1));

                    }
                }

                adaptor.addChild(root_0, root_1);
                }

            }



            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end ifStatementStart

    public static class elseStatement_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start elseStatement
    // src/org/six11/flatcad/flatlang/FlatLang.g:97:1: elseStatement : 'else' ( block )? -> ^( ELSE_ALT ( block )? ) ;
    public elseStatement_return elseStatement() throws RecognitionException {
        elseStatement_return retval = new elseStatement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal21=null;
        block_return block22 = null;

        List list_block=new ArrayList();
        List list_62=new ArrayList();
        CommonTree string_literal21_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:98:4: ( 'else' ( block )? -> ^( ELSE_ALT ( block )? ) )
            // src/org/six11/flatcad/flatlang/FlatLang.g:98:4: 'else' ( block )?
            {
            string_literal21=(Token)input.LT(1);
            match(input,62,FOLLOW_62_in_elseStatement517); 
            list_62.add(string_literal21);

            // src/org/six11/flatcad/flatlang/FlatLang.g:98:11: ( block )?
            int alt8=2;
            int LA8_0 = input.LA(1);
            if ( ((LA8_0>=ID && LA8_0<=OBJECT)||(LA8_0>=NUM && LA8_0<=STR_LITERAL)||LA8_0==61||LA8_0==64||(LA8_0>=66 && LA8_0<=68)||(LA8_0>=79 && LA8_0<=80)||LA8_0==85||LA8_0==88||(LA8_0>=90 && LA8_0<=92)) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:98:11: block
                    {
                    pushFollow(FOLLOW_block_in_elseStatement519);
                    block22=block();
                    _fsp--;

                    list_block.add(block22.getTree());

                    }
                    break;

            }


            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 98:18: -> ^( ELSE_ALT ( block )? )
            {
                // src/org/six11/flatcad/flatlang/FlatLang.g:98:21: ^( ELSE_ALT ( block )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(ELSE_ALT, "ELSE_ALT"), root_1);

                // src/org/six11/flatcad/flatlang/FlatLang.g:98:32: ( block )?
                {
                int n_1 = list_block == null ? 0 : list_block.size();
                 


                if ( n_1 > 1 ) throw new RuntimeException("block list has > 1 elements");
                if ( n_1==1 ) {
                    int i_1 = 0;
                    adaptor.addChild(root_1, list_block.get(i_1));

                    }
                }

                adaptor.addChild(root_0, root_1);
                }

            }



            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end elseStatement

    public static class altStatement_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start altStatement
    // src/org/six11/flatcad/flatlang/FlatLang.g:101:1: altStatement : 'alt' parExpression ( block )? -> ^( IF_ALT parExpression ( block )? ) ;
    public altStatement_return altStatement() throws RecognitionException {
        altStatement_return retval = new altStatement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal23=null;
        parExpression_return parExpression24 = null;

        block_return block25 = null;

        List list_parExpression=new ArrayList();
        List list_block=new ArrayList();
        List list_63=new ArrayList();
        CommonTree string_literal23_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:102:4: ( 'alt' parExpression ( block )? -> ^( IF_ALT parExpression ( block )? ) )
            // src/org/six11/flatcad/flatlang/FlatLang.g:102:4: 'alt' parExpression ( block )?
            {
            string_literal23=(Token)input.LT(1);
            match(input,63,FOLLOW_63_in_altStatement540); 
            list_63.add(string_literal23);

            pushFollow(FOLLOW_parExpression_in_altStatement542);
            parExpression24=parExpression();
            _fsp--;

            list_parExpression.add(parExpression24.getTree());
            // src/org/six11/flatcad/flatlang/FlatLang.g:102:24: ( block )?
            int alt9=2;
            int LA9_0 = input.LA(1);
            if ( ((LA9_0>=ID && LA9_0<=OBJECT)||(LA9_0>=NUM && LA9_0<=STR_LITERAL)||LA9_0==61||LA9_0==64||(LA9_0>=66 && LA9_0<=68)||(LA9_0>=79 && LA9_0<=80)||LA9_0==85||LA9_0==88||(LA9_0>=90 && LA9_0<=92)) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:102:24: block
                    {
                    pushFollow(FOLLOW_block_in_altStatement544);
                    block25=block();
                    _fsp--;

                    list_block.add(block25.getTree());

                    }
                    break;

            }


            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 102:31: -> ^( IF_ALT parExpression ( block )? )
            {
                // src/org/six11/flatcad/flatlang/FlatLang.g:102:34: ^( IF_ALT parExpression ( block )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(IF_ALT, "IF_ALT"), root_1);

                adaptor.addChild(root_1, list_parExpression.get(i_0));
                // src/org/six11/flatcad/flatlang/FlatLang.g:102:57: ( block )?
                {
                int n_1 = list_block == null ? 0 : list_block.size();
                 


                if ( n_1 > 1 ) throw new RuntimeException("block list has > 1 elements");
                if ( n_1==1 ) {
                    int i_1 = 0;
                    adaptor.addChild(root_1, list_block.get(i_1));

                    }
                }

                adaptor.addChild(root_0, root_1);
                }

            }



            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end altStatement

    public static class parExpression_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start parExpression
    // src/org/six11/flatcad/flatlang/FlatLang.g:105:1: parExpression : '(' n_assignExpression ')' -> n_assignExpression ;
    public parExpression_return parExpression() throws RecognitionException {
        parExpression_return retval = new parExpression_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal26=null;
        Token char_literal28=null;
        n_assignExpression_return n_assignExpression27 = null;

        List list_n_assignExpression=new ArrayList();
        List list_65=new ArrayList();
        List list_64=new ArrayList();
        CommonTree char_literal26_tree=null;
        CommonTree char_literal28_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:106:4: ( '(' n_assignExpression ')' -> n_assignExpression )
            // src/org/six11/flatcad/flatlang/FlatLang.g:106:4: '(' n_assignExpression ')'
            {
            char_literal26=(Token)input.LT(1);
            match(input,64,FOLLOW_64_in_parExpression567); 
            list_64.add(char_literal26);

            pushFollow(FOLLOW_n_assignExpression_in_parExpression569);
            n_assignExpression27=n_assignExpression();
            _fsp--;

            list_n_assignExpression.add(n_assignExpression27.getTree());
            char_literal28=(Token)input.LT(1);
            match(input,65,FOLLOW_65_in_parExpression571); 
            list_65.add(char_literal28);


            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 106:31: -> n_assignExpression
            {
                adaptor.addChild(root_0, list_n_assignExpression.get(i_0));

            }



            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end parExpression

    public static class repeatStatement_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start repeatStatement
    // src/org/six11/flatcad/flatlang/FlatLang.g:109:1: repeatStatement : 'repeat' '(' n_assignExpression ')' ( block )? 'done' -> ^( REPEAT_STATEMENT n_assignExpression ( block )? ) ;
    public repeatStatement_return repeatStatement() throws RecognitionException {
        repeatStatement_return retval = new repeatStatement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal29=null;
        Token char_literal30=null;
        Token char_literal32=null;
        Token string_literal34=null;
        n_assignExpression_return n_assignExpression31 = null;

        block_return block33 = null;

        List list_n_assignExpression=new ArrayList();
        List list_block=new ArrayList();
        List list_65=new ArrayList();
        List list_64=new ArrayList();
        List list_60=new ArrayList();
        List list_66=new ArrayList();
        CommonTree string_literal29_tree=null;
        CommonTree char_literal30_tree=null;
        CommonTree char_literal32_tree=null;
        CommonTree string_literal34_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:110:4: ( 'repeat' '(' n_assignExpression ')' ( block )? 'done' -> ^( REPEAT_STATEMENT n_assignExpression ( block )? ) )
            // src/org/six11/flatcad/flatlang/FlatLang.g:110:4: 'repeat' '(' n_assignExpression ')' ( block )? 'done'
            {
            string_literal29=(Token)input.LT(1);
            match(input,66,FOLLOW_66_in_repeatStatement587); 
            list_66.add(string_literal29);

            char_literal30=(Token)input.LT(1);
            match(input,64,FOLLOW_64_in_repeatStatement589); 
            list_64.add(char_literal30);

            pushFollow(FOLLOW_n_assignExpression_in_repeatStatement591);
            n_assignExpression31=n_assignExpression();
            _fsp--;

            list_n_assignExpression.add(n_assignExpression31.getTree());
            char_literal32=(Token)input.LT(1);
            match(input,65,FOLLOW_65_in_repeatStatement593); 
            list_65.add(char_literal32);

            // src/org/six11/flatcad/flatlang/FlatLang.g:110:40: ( block )?
            int alt10=2;
            int LA10_0 = input.LA(1);
            if ( ((LA10_0>=ID && LA10_0<=OBJECT)||(LA10_0>=NUM && LA10_0<=STR_LITERAL)||LA10_0==61||LA10_0==64||(LA10_0>=66 && LA10_0<=68)||(LA10_0>=79 && LA10_0<=80)||LA10_0==85||LA10_0==88||(LA10_0>=90 && LA10_0<=92)) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:110:40: block
                    {
                    pushFollow(FOLLOW_block_in_repeatStatement595);
                    block33=block();
                    _fsp--;

                    list_block.add(block33.getTree());

                    }
                    break;

            }

            string_literal34=(Token)input.LT(1);
            match(input,60,FOLLOW_60_in_repeatStatement598); 
            list_60.add(string_literal34);


            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 111:3: -> ^( REPEAT_STATEMENT n_assignExpression ( block )? )
            {
                // src/org/six11/flatcad/flatlang/FlatLang.g:111:6: ^( REPEAT_STATEMENT n_assignExpression ( block )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(REPEAT_STATEMENT, "REPEAT_STATEMENT"), root_1);

                adaptor.addChild(root_1, list_n_assignExpression.get(i_0));
                // src/org/six11/flatcad/flatlang/FlatLang.g:111:44: ( block )?
                {
                int n_1 = list_block == null ? 0 : list_block.size();
                 


                if ( n_1 > 1 ) throw new RuntimeException("block list has > 1 elements");
                if ( n_1==1 ) {
                    int i_1 = 0;
                    adaptor.addChild(root_1, list_block.get(i_1));

                    }
                }

                adaptor.addChild(root_0, root_1);
                }

            }



            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end repeatStatement

    public static class whileStatement_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start whileStatement
    // src/org/six11/flatcad/flatlang/FlatLang.g:114:1: whileStatement : 'while' '(' n_assignExpression ')' ( block )? 'done' -> ^( WHILE_STATEMENT n_assignExpression ( block )? ) ;
    public whileStatement_return whileStatement() throws RecognitionException {
        whileStatement_return retval = new whileStatement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal35=null;
        Token char_literal36=null;
        Token char_literal38=null;
        Token string_literal40=null;
        n_assignExpression_return n_assignExpression37 = null;

        block_return block39 = null;

        List list_n_assignExpression=new ArrayList();
        List list_block=new ArrayList();
        List list_65=new ArrayList();
        List list_64=new ArrayList();
        List list_60=new ArrayList();
        List list_67=new ArrayList();
        CommonTree string_literal35_tree=null;
        CommonTree char_literal36_tree=null;
        CommonTree char_literal38_tree=null;
        CommonTree string_literal40_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:115:4: ( 'while' '(' n_assignExpression ')' ( block )? 'done' -> ^( WHILE_STATEMENT n_assignExpression ( block )? ) )
            // src/org/six11/flatcad/flatlang/FlatLang.g:115:4: 'while' '(' n_assignExpression ')' ( block )? 'done'
            {
            string_literal35=(Token)input.LT(1);
            match(input,67,FOLLOW_67_in_whileStatement622); 
            list_67.add(string_literal35);

            char_literal36=(Token)input.LT(1);
            match(input,64,FOLLOW_64_in_whileStatement624); 
            list_64.add(char_literal36);

            pushFollow(FOLLOW_n_assignExpression_in_whileStatement626);
            n_assignExpression37=n_assignExpression();
            _fsp--;

            list_n_assignExpression.add(n_assignExpression37.getTree());
            char_literal38=(Token)input.LT(1);
            match(input,65,FOLLOW_65_in_whileStatement628); 
            list_65.add(char_literal38);

            // src/org/six11/flatcad/flatlang/FlatLang.g:115:39: ( block )?
            int alt11=2;
            int LA11_0 = input.LA(1);
            if ( ((LA11_0>=ID && LA11_0<=OBJECT)||(LA11_0>=NUM && LA11_0<=STR_LITERAL)||LA11_0==61||LA11_0==64||(LA11_0>=66 && LA11_0<=68)||(LA11_0>=79 && LA11_0<=80)||LA11_0==85||LA11_0==88||(LA11_0>=90 && LA11_0<=92)) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:115:39: block
                    {
                    pushFollow(FOLLOW_block_in_whileStatement630);
                    block39=block();
                    _fsp--;

                    list_block.add(block39.getTree());

                    }
                    break;

            }

            string_literal40=(Token)input.LT(1);
            match(input,60,FOLLOW_60_in_whileStatement633); 
            list_60.add(string_literal40);


            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 116:3: -> ^( WHILE_STATEMENT n_assignExpression ( block )? )
            {
                // src/org/six11/flatcad/flatlang/FlatLang.g:116:6: ^( WHILE_STATEMENT n_assignExpression ( block )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(WHILE_STATEMENT, "WHILE_STATEMENT"), root_1);

                adaptor.addChild(root_1, list_n_assignExpression.get(i_0));
                // src/org/six11/flatcad/flatlang/FlatLang.g:116:43: ( block )?
                {
                int n_1 = list_block == null ? 0 : list_block.size();
                 


                if ( n_1 > 1 ) throw new RuntimeException("block list has > 1 elements");
                if ( n_1==1 ) {
                    int i_1 = 0;
                    adaptor.addChild(root_1, list_block.get(i_1));

                    }
                }

                adaptor.addChild(root_0, root_1);
                }

            }



            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end whileStatement

    public static class forEachStatement_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start forEachStatement
    // src/org/six11/flatcad/flatlang/FlatLang.g:119:1: forEachStatement : 'for each' '(' v= ID ':' l= n_assignExpression ')' ( '(' c= n_assignExpression ')' )? ( block )? 'done' -> ^( FOR_EACH_STATEMENT $v $l ( $c)? ( block )? ) ;
    public forEachStatement_return forEachStatement() throws RecognitionException {
        forEachStatement_return retval = new forEachStatement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token v=null;
        Token string_literal41=null;
        Token char_literal42=null;
        Token char_literal43=null;
        Token char_literal44=null;
        Token char_literal45=null;
        Token char_literal46=null;
        Token string_literal48=null;
        n_assignExpression_return l = null;

        n_assignExpression_return c = null;

        block_return block47 = null;

        List list_n_assignExpression=new ArrayList();
        List list_block=new ArrayList();
        List list_65=new ArrayList();
        List list_68=new ArrayList();
        List list_64=new ArrayList();
        List list_69=new ArrayList();
        List list_60=new ArrayList();
        List list_ID=new ArrayList();
        CommonTree v_tree=null;
        CommonTree string_literal41_tree=null;
        CommonTree char_literal42_tree=null;
        CommonTree char_literal43_tree=null;
        CommonTree char_literal44_tree=null;
        CommonTree char_literal45_tree=null;
        CommonTree char_literal46_tree=null;
        CommonTree string_literal48_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:120:4: ( 'for each' '(' v= ID ':' l= n_assignExpression ')' ( '(' c= n_assignExpression ')' )? ( block )? 'done' -> ^( FOR_EACH_STATEMENT $v $l ( $c)? ( block )? ) )
            // src/org/six11/flatcad/flatlang/FlatLang.g:120:4: 'for each' '(' v= ID ':' l= n_assignExpression ')' ( '(' c= n_assignExpression ')' )? ( block )? 'done'
            {
            string_literal41=(Token)input.LT(1);
            match(input,68,FOLLOW_68_in_forEachStatement657); 
            list_68.add(string_literal41);

            char_literal42=(Token)input.LT(1);
            match(input,64,FOLLOW_64_in_forEachStatement659); 
            list_64.add(char_literal42);

            v=(Token)input.LT(1);
            match(input,ID,FOLLOW_ID_in_forEachStatement663); 
            list_ID.add(v);

            char_literal43=(Token)input.LT(1);
            match(input,69,FOLLOW_69_in_forEachStatement665); 
            list_69.add(char_literal43);

            pushFollow(FOLLOW_n_assignExpression_in_forEachStatement669);
            l=n_assignExpression();
            _fsp--;

            list_n_assignExpression.add(l.getTree());
            char_literal44=(Token)input.LT(1);
            match(input,65,FOLLOW_65_in_forEachStatement671); 
            list_65.add(char_literal44);

            // src/org/six11/flatcad/flatlang/FlatLang.g:120:53: ( '(' c= n_assignExpression ')' )?
            int alt12=2;
            int LA12_0 = input.LA(1);
            if ( (LA12_0==64) ) {
                switch ( input.LA(2) ) {
                    case 80:
                        alt12=1;
                        break;
                    case 79:
                        alt12=1;
                        break;
                    case NUM:
                        alt12=1;
                        break;
                    case STR_LITERAL:
                        alt12=1;
                        break;
                    case TRUE:
                        alt12=1;
                        break;
                    case FALSE:
                        alt12=1;
                        break;
                    case INFINITY:
                        alt12=1;
                        break;
                    case OBJECT:
                        alt12=1;
                        break;
                    case ID:
                        alt12=1;
                        break;
                    case 64:
                        alt12=1;
                        break;
                    case 85:
                        alt12=1;
                        break;
                }

            }
            switch (alt12) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:120:55: '(' c= n_assignExpression ')'
                    {
                    char_literal45=(Token)input.LT(1);
                    match(input,64,FOLLOW_64_in_forEachStatement675); 
                    list_64.add(char_literal45);

                    pushFollow(FOLLOW_n_assignExpression_in_forEachStatement679);
                    c=n_assignExpression();
                    _fsp--;

                    list_n_assignExpression.add(c.getTree());
                    char_literal46=(Token)input.LT(1);
                    match(input,65,FOLLOW_65_in_forEachStatement681); 
                    list_65.add(char_literal46);


                    }
                    break;

            }

            // src/org/six11/flatcad/flatlang/FlatLang.g:120:87: ( block )?
            int alt13=2;
            int LA13_0 = input.LA(1);
            if ( ((LA13_0>=ID && LA13_0<=OBJECT)||(LA13_0>=NUM && LA13_0<=STR_LITERAL)||LA13_0==61||LA13_0==64||(LA13_0>=66 && LA13_0<=68)||(LA13_0>=79 && LA13_0<=80)||LA13_0==85||LA13_0==88||(LA13_0>=90 && LA13_0<=92)) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:120:87: block
                    {
                    pushFollow(FOLLOW_block_in_forEachStatement686);
                    block47=block();
                    _fsp--;

                    list_block.add(block47.getTree());

                    }
                    break;

            }

            string_literal48=(Token)input.LT(1);
            match(input,60,FOLLOW_60_in_forEachStatement689); 
            list_60.add(string_literal48);


            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 121:3: -> ^( FOR_EACH_STATEMENT $v $l ( $c)? ( block )? )
            {
                // src/org/six11/flatcad/flatlang/FlatLang.g:121:6: ^( FOR_EACH_STATEMENT $v $l ( $c)? ( block )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(FOR_EACH_STATEMENT, "FOR_EACH_STATEMENT"), root_1);

                adaptor.addChild(root_1, v);
                adaptor.addChild(root_1, l.tree);
                // src/org/six11/flatcad/flatlang/FlatLang.g:121:33: ( $c)?
                {
                int n_1 = c==null ? 0 : 1;


                if ( n_1 > 1 ) throw new RuntimeException(" list has > 1 elements");
                if ( n_1==1 ) {
                    int i_1 = 0;
                    adaptor.addChild(root_1, c.tree);

                    }
                }
                // src/org/six11/flatcad/flatlang/FlatLang.g:121:37: ( block )?
                {
                int n_1 = list_block == null ? 0 : list_block.size();
                 


                if ( n_1 > 1 ) throw new RuntimeException("block list has > 1 elements");
                if ( n_1==1 ) {
                    int i_1 = 0;
                    adaptor.addChild(root_1, list_block.get(i_1));

                    }
                }

                adaptor.addChild(root_0, root_1);
                }

            }



            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end forEachStatement

    public static class statementExpression_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start statementExpression
    // src/org/six11/flatcad/flatlang/FlatLang.g:124:1: statementExpression : n_assignExpression -> ^( EXPR n_assignExpression ) ;
    public statementExpression_return statementExpression() throws RecognitionException {
        statementExpression_return retval = new statementExpression_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        n_assignExpression_return n_assignExpression49 = null;

        List list_n_assignExpression=new ArrayList();

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:125:4: ( n_assignExpression -> ^( EXPR n_assignExpression ) )
            // src/org/six11/flatcad/flatlang/FlatLang.g:125:4: n_assignExpression
            {
            pushFollow(FOLLOW_n_assignExpression_in_statementExpression721);
            n_assignExpression49=n_assignExpression();
            _fsp--;

            list_n_assignExpression.add(n_assignExpression49.getTree());

            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 125:23: -> ^( EXPR n_assignExpression )
            {
                // src/org/six11/flatcad/flatlang/FlatLang.g:125:26: ^( EXPR n_assignExpression )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(EXPR, "EXPR"), root_1);

                adaptor.addChild(root_1, list_n_assignExpression.get(i_0));

                adaptor.addChild(root_0, root_1);
                }

            }



            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end statementExpression

    public static class n_assignExpression_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start n_assignExpression
    // src/org/six11/flatcad/flatlang/FlatLang.g:128:1: n_assignExpression : ( n_conditionalOrExpr -> n_conditionalOrExpr ) ( '=' r= n_conditionalOrExpr -> ^( N_ASSIGN_EXPR $n_assignExpression $r) )? ;
    public n_assignExpression_return n_assignExpression() throws RecognitionException {
        n_assignExpression_return retval = new n_assignExpression_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal51=null;
        n_conditionalOrExpr_return r = null;

        n_conditionalOrExpr_return n_conditionalOrExpr50 = null;

        List list_n_conditionalOrExpr=new ArrayList();
        List list_70=new ArrayList();
        CommonTree char_literal51_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:129:4: ( ( n_conditionalOrExpr -> n_conditionalOrExpr ) ( '=' r= n_conditionalOrExpr -> ^( N_ASSIGN_EXPR $n_assignExpression $r) )? )
            // src/org/six11/flatcad/flatlang/FlatLang.g:129:4: ( n_conditionalOrExpr -> n_conditionalOrExpr ) ( '=' r= n_conditionalOrExpr -> ^( N_ASSIGN_EXPR $n_assignExpression $r) )?
            {
            // src/org/six11/flatcad/flatlang/FlatLang.g:129:4: ( n_conditionalOrExpr -> n_conditionalOrExpr )
            // src/org/six11/flatcad/flatlang/FlatLang.g:129:5: n_conditionalOrExpr
            {
            pushFollow(FOLLOW_n_conditionalOrExpr_in_n_assignExpression741);
            n_conditionalOrExpr50=n_conditionalOrExpr();
            _fsp--;

            list_n_conditionalOrExpr.add(n_conditionalOrExpr50.getTree());

            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 129:25: -> n_conditionalOrExpr
            {
                adaptor.addChild(root_0, list_n_conditionalOrExpr.get(i_0));

            }



            }

            // src/org/six11/flatcad/flatlang/FlatLang.g:129:49: ( '=' r= n_conditionalOrExpr -> ^( N_ASSIGN_EXPR $n_assignExpression $r) )?
            int alt14=2;
            int LA14_0 = input.LA(1);
            if ( (LA14_0==70) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:130:4: '=' r= n_conditionalOrExpr
                    {
                    char_literal51=(Token)input.LT(1);
                    match(input,70,FOLLOW_70_in_n_assignExpression753); 
                    list_70.add(char_literal51);

                    pushFollow(FOLLOW_n_conditionalOrExpr_in_n_assignExpression757);
                    r=n_conditionalOrExpr();
                    _fsp--;

                    list_n_conditionalOrExpr.add(r.getTree());

                    // AST REWRITE
                    int i_0 = 0;
                    retval.tree = root_0;
                    root_0 = (CommonTree)adaptor.nil();
                    // 130:30: -> ^( N_ASSIGN_EXPR $n_assignExpression $r)
                    {
                        // src/org/six11/flatcad/flatlang/FlatLang.g:130:33: ^( N_ASSIGN_EXPR $n_assignExpression $r)
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(N_ASSIGN_EXPR, "N_ASSIGN_EXPR"), root_1);

                        adaptor.addChild(root_1, retval.tree);
                        adaptor.addChild(root_1, r.tree);

                        adaptor.addChild(root_0, root_1);
                        }

                    }



                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end n_assignExpression

    public static class n_conditionalOrExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start n_conditionalOrExpr
    // src/org/six11/flatcad/flatlang/FlatLang.g:134:1: n_conditionalOrExpr : ( n_conditionalAndExpr -> n_conditionalAndExpr ) ( ( 'or' r= n_conditionalAndExpr -> ^( N_OR_EXPR $n_conditionalOrExpr $r) ) )* ;
    public n_conditionalOrExpr_return n_conditionalOrExpr() throws RecognitionException {
        n_conditionalOrExpr_return retval = new n_conditionalOrExpr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal53=null;
        n_conditionalAndExpr_return r = null;

        n_conditionalAndExpr_return n_conditionalAndExpr52 = null;

        List list_n_conditionalAndExpr=new ArrayList();
        List list_71=new ArrayList();
        CommonTree string_literal53_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:135:4: ( ( n_conditionalAndExpr -> n_conditionalAndExpr ) ( ( 'or' r= n_conditionalAndExpr -> ^( N_OR_EXPR $n_conditionalOrExpr $r) ) )* )
            // src/org/six11/flatcad/flatlang/FlatLang.g:135:4: ( n_conditionalAndExpr -> n_conditionalAndExpr ) ( ( 'or' r= n_conditionalAndExpr -> ^( N_OR_EXPR $n_conditionalOrExpr $r) ) )*
            {
            // src/org/six11/flatcad/flatlang/FlatLang.g:135:4: ( n_conditionalAndExpr -> n_conditionalAndExpr )
            // src/org/six11/flatcad/flatlang/FlatLang.g:135:5: n_conditionalAndExpr
            {
            pushFollow(FOLLOW_n_conditionalAndExpr_in_n_conditionalOrExpr787);
            n_conditionalAndExpr52=n_conditionalAndExpr();
            _fsp--;

            list_n_conditionalAndExpr.add(n_conditionalAndExpr52.getTree());

            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 135:26: -> n_conditionalAndExpr
            {
                adaptor.addChild(root_0, list_n_conditionalAndExpr.get(i_0));

            }



            }

            // src/org/six11/flatcad/flatlang/FlatLang.g:135:51: ( ( 'or' r= n_conditionalAndExpr -> ^( N_OR_EXPR $n_conditionalOrExpr $r) ) )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);
                if ( (LA15_0==71) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:136:4: ( 'or' r= n_conditionalAndExpr -> ^( N_OR_EXPR $n_conditionalOrExpr $r) )
            	    {
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:136:4: ( 'or' r= n_conditionalAndExpr -> ^( N_OR_EXPR $n_conditionalOrExpr $r) )
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:136:6: 'or' r= n_conditionalAndExpr
            	    {
            	    string_literal53=(Token)input.LT(1);
            	    match(input,71,FOLLOW_71_in_n_conditionalOrExpr801); 
            	    list_71.add(string_literal53);

            	    pushFollow(FOLLOW_n_conditionalAndExpr_in_n_conditionalOrExpr805);
            	    r=n_conditionalAndExpr();
            	    _fsp--;

            	    list_n_conditionalAndExpr.add(r.getTree());

            	    // AST REWRITE
            	    int i_0 = 0;
            	    retval.tree = root_0;
            	    root_0 = (CommonTree)adaptor.nil();
            	    // 136:34: -> ^( N_OR_EXPR $n_conditionalOrExpr $r)
            	    {
            	        // src/org/six11/flatcad/flatlang/FlatLang.g:136:37: ^( N_OR_EXPR $n_conditionalOrExpr $r)
            	        {
            	        CommonTree root_1 = (CommonTree)adaptor.nil();
            	        root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(N_OR_EXPR, "N_OR_EXPR"), root_1);

            	        adaptor.addChild(root_1, retval.tree);
            	        adaptor.addChild(root_1, r.tree);

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }



            	    }


            	    }
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end n_conditionalOrExpr

    public static class n_conditionalAndExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start n_conditionalAndExpr
    // src/org/six11/flatcad/flatlang/FlatLang.g:140:1: n_conditionalAndExpr : ( n_equalityExpression -> n_equalityExpression ) ( ( 'and' r= n_equalityExpression -> ^( N_AND_EXPR $n_conditionalAndExpr $r) ) )* ;
    public n_conditionalAndExpr_return n_conditionalAndExpr() throws RecognitionException {
        n_conditionalAndExpr_return retval = new n_conditionalAndExpr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal55=null;
        n_equalityExpression_return r = null;

        n_equalityExpression_return n_equalityExpression54 = null;

        List list_n_equalityExpression=new ArrayList();
        List list_72=new ArrayList();
        CommonTree string_literal55_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:141:4: ( ( n_equalityExpression -> n_equalityExpression ) ( ( 'and' r= n_equalityExpression -> ^( N_AND_EXPR $n_conditionalAndExpr $r) ) )* )
            // src/org/six11/flatcad/flatlang/FlatLang.g:141:4: ( n_equalityExpression -> n_equalityExpression ) ( ( 'and' r= n_equalityExpression -> ^( N_AND_EXPR $n_conditionalAndExpr $r) ) )*
            {
            // src/org/six11/flatcad/flatlang/FlatLang.g:141:4: ( n_equalityExpression -> n_equalityExpression )
            // src/org/six11/flatcad/flatlang/FlatLang.g:141:5: n_equalityExpression
            {
            pushFollow(FOLLOW_n_equalityExpression_in_n_conditionalAndExpr835);
            n_equalityExpression54=n_equalityExpression();
            _fsp--;

            list_n_equalityExpression.add(n_equalityExpression54.getTree());

            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 141:26: -> n_equalityExpression
            {
                adaptor.addChild(root_0, list_n_equalityExpression.get(i_0));

            }



            }

            // src/org/six11/flatcad/flatlang/FlatLang.g:141:51: ( ( 'and' r= n_equalityExpression -> ^( N_AND_EXPR $n_conditionalAndExpr $r) ) )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);
                if ( (LA16_0==72) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:142:4: ( 'and' r= n_equalityExpression -> ^( N_AND_EXPR $n_conditionalAndExpr $r) )
            	    {
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:142:4: ( 'and' r= n_equalityExpression -> ^( N_AND_EXPR $n_conditionalAndExpr $r) )
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:142:5: 'and' r= n_equalityExpression
            	    {
            	    string_literal55=(Token)input.LT(1);
            	    match(input,72,FOLLOW_72_in_n_conditionalAndExpr849); 
            	    list_72.add(string_literal55);

            	    pushFollow(FOLLOW_n_equalityExpression_in_n_conditionalAndExpr853);
            	    r=n_equalityExpression();
            	    _fsp--;

            	    list_n_equalityExpression.add(r.getTree());

            	    // AST REWRITE
            	    int i_0 = 0;
            	    retval.tree = root_0;
            	    root_0 = (CommonTree)adaptor.nil();
            	    // 142:34: -> ^( N_AND_EXPR $n_conditionalAndExpr $r)
            	    {
            	        // src/org/six11/flatcad/flatlang/FlatLang.g:142:37: ^( N_AND_EXPR $n_conditionalAndExpr $r)
            	        {
            	        CommonTree root_1 = (CommonTree)adaptor.nil();
            	        root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(N_AND_EXPR, "N_AND_EXPR"), root_1);

            	        adaptor.addChild(root_1, retval.tree);
            	        adaptor.addChild(root_1, r.tree);

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }



            	    }


            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end n_conditionalAndExpr

    public static class n_equalityExpression_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start n_equalityExpression
    // src/org/six11/flatcad/flatlang/FlatLang.g:146:1: n_equalityExpression : ( n_gtltExpression -> n_gtltExpression ) ( ( '==' r= n_gtltExpression -> ^( N_EQ_EXPR $n_equalityExpression $r) ) | ( '!=' r= n_gtltExpression -> ^( N_NEQ_EXPR $n_equalityExpression $r) ) )* ;
    public n_equalityExpression_return n_equalityExpression() throws RecognitionException {
        n_equalityExpression_return retval = new n_equalityExpression_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal57=null;
        Token string_literal58=null;
        n_gtltExpression_return r = null;

        n_gtltExpression_return n_gtltExpression56 = null;

        List list_n_gtltExpression=new ArrayList();
        List list_74=new ArrayList();
        List list_73=new ArrayList();
        CommonTree string_literal57_tree=null;
        CommonTree string_literal58_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:147:4: ( ( n_gtltExpression -> n_gtltExpression ) ( ( '==' r= n_gtltExpression -> ^( N_EQ_EXPR $n_equalityExpression $r) ) | ( '!=' r= n_gtltExpression -> ^( N_NEQ_EXPR $n_equalityExpression $r) ) )* )
            // src/org/six11/flatcad/flatlang/FlatLang.g:147:4: ( n_gtltExpression -> n_gtltExpression ) ( ( '==' r= n_gtltExpression -> ^( N_EQ_EXPR $n_equalityExpression $r) ) | ( '!=' r= n_gtltExpression -> ^( N_NEQ_EXPR $n_equalityExpression $r) ) )*
            {
            // src/org/six11/flatcad/flatlang/FlatLang.g:147:4: ( n_gtltExpression -> n_gtltExpression )
            // src/org/six11/flatcad/flatlang/FlatLang.g:147:5: n_gtltExpression
            {
            pushFollow(FOLLOW_n_gtltExpression_in_n_equalityExpression883);
            n_gtltExpression56=n_gtltExpression();
            _fsp--;

            list_n_gtltExpression.add(n_gtltExpression56.getTree());

            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 147:22: -> n_gtltExpression
            {
                adaptor.addChild(root_0, list_n_gtltExpression.get(i_0));

            }



            }

            // src/org/six11/flatcad/flatlang/FlatLang.g:147:43: ( ( '==' r= n_gtltExpression -> ^( N_EQ_EXPR $n_equalityExpression $r) ) | ( '!=' r= n_gtltExpression -> ^( N_NEQ_EXPR $n_equalityExpression $r) ) )*
            loop17:
            do {
                int alt17=3;
                int LA17_0 = input.LA(1);
                if ( (LA17_0==73) ) {
                    alt17=1;
                }
                else if ( (LA17_0==74) ) {
                    alt17=2;
                }


                switch (alt17) {
            	case 1 :
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:148:4: ( '==' r= n_gtltExpression -> ^( N_EQ_EXPR $n_equalityExpression $r) )
            	    {
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:148:4: ( '==' r= n_gtltExpression -> ^( N_EQ_EXPR $n_equalityExpression $r) )
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:148:5: '==' r= n_gtltExpression
            	    {
            	    string_literal57=(Token)input.LT(1);
            	    match(input,73,FOLLOW_73_in_n_equalityExpression896); 
            	    list_73.add(string_literal57);

            	    pushFollow(FOLLOW_n_gtltExpression_in_n_equalityExpression900);
            	    r=n_gtltExpression();
            	    _fsp--;

            	    list_n_gtltExpression.add(r.getTree());

            	    // AST REWRITE
            	    int i_0 = 0;
            	    retval.tree = root_0;
            	    root_0 = (CommonTree)adaptor.nil();
            	    // 148:29: -> ^( N_EQ_EXPR $n_equalityExpression $r)
            	    {
            	        // src/org/six11/flatcad/flatlang/FlatLang.g:148:32: ^( N_EQ_EXPR $n_equalityExpression $r)
            	        {
            	        CommonTree root_1 = (CommonTree)adaptor.nil();
            	        root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(N_EQ_EXPR, "N_EQ_EXPR"), root_1);

            	        adaptor.addChild(root_1, retval.tree);
            	        adaptor.addChild(root_1, r.tree);

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }



            	    }


            	    }
            	    break;
            	case 2 :
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:149:4: ( '!=' r= n_gtltExpression -> ^( N_NEQ_EXPR $n_equalityExpression $r) )
            	    {
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:149:4: ( '!=' r= n_gtltExpression -> ^( N_NEQ_EXPR $n_equalityExpression $r) )
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:149:5: '!=' r= n_gtltExpression
            	    {
            	    string_literal58=(Token)input.LT(1);
            	    match(input,74,FOLLOW_74_in_n_equalityExpression922); 
            	    list_74.add(string_literal58);

            	    pushFollow(FOLLOW_n_gtltExpression_in_n_equalityExpression926);
            	    r=n_gtltExpression();
            	    _fsp--;

            	    list_n_gtltExpression.add(r.getTree());

            	    // AST REWRITE
            	    int i_0 = 0;
            	    retval.tree = root_0;
            	    root_0 = (CommonTree)adaptor.nil();
            	    // 149:29: -> ^( N_NEQ_EXPR $n_equalityExpression $r)
            	    {
            	        // src/org/six11/flatcad/flatlang/FlatLang.g:149:32: ^( N_NEQ_EXPR $n_equalityExpression $r)
            	        {
            	        CommonTree root_1 = (CommonTree)adaptor.nil();
            	        root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(N_NEQ_EXPR, "N_NEQ_EXPR"), root_1);

            	        adaptor.addChild(root_1, retval.tree);
            	        adaptor.addChild(root_1, r.tree);

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }



            	    }


            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end n_equalityExpression

    public static class n_gtltExpression_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start n_gtltExpression
    // src/org/six11/flatcad/flatlang/FlatLang.g:153:1: n_gtltExpression : ( n_additionExpr -> n_additionExpr ) ( ( '<' r= n_additionExpr -> ^( N_LT_EXPR $n_gtltExpression $r) ) | ( '<=' r= n_additionExpr -> ^( N_LTEQ_EXPR $n_gtltExpression $r) ) | ( '>' r= n_additionExpr -> ^( N_GT_EXPR $n_gtltExpression $r) ) | ( '>=' r= n_additionExpr -> ^( N_GTEQ_EXPR $n_gtltExpression $r) ) )* ;
    public n_gtltExpression_return n_gtltExpression() throws RecognitionException {
        n_gtltExpression_return retval = new n_gtltExpression_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal60=null;
        Token string_literal61=null;
        Token char_literal62=null;
        Token string_literal63=null;
        n_additionExpr_return r = null;

        n_additionExpr_return n_additionExpr59 = null;

        List list_n_additionExpr=new ArrayList();
        List list_76=new ArrayList();
        List list_75=new ArrayList();
        List list_77=new ArrayList();
        List list_78=new ArrayList();
        CommonTree char_literal60_tree=null;
        CommonTree string_literal61_tree=null;
        CommonTree char_literal62_tree=null;
        CommonTree string_literal63_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:154:4: ( ( n_additionExpr -> n_additionExpr ) ( ( '<' r= n_additionExpr -> ^( N_LT_EXPR $n_gtltExpression $r) ) | ( '<=' r= n_additionExpr -> ^( N_LTEQ_EXPR $n_gtltExpression $r) ) | ( '>' r= n_additionExpr -> ^( N_GT_EXPR $n_gtltExpression $r) ) | ( '>=' r= n_additionExpr -> ^( N_GTEQ_EXPR $n_gtltExpression $r) ) )* )
            // src/org/six11/flatcad/flatlang/FlatLang.g:154:4: ( n_additionExpr -> n_additionExpr ) ( ( '<' r= n_additionExpr -> ^( N_LT_EXPR $n_gtltExpression $r) ) | ( '<=' r= n_additionExpr -> ^( N_LTEQ_EXPR $n_gtltExpression $r) ) | ( '>' r= n_additionExpr -> ^( N_GT_EXPR $n_gtltExpression $r) ) | ( '>=' r= n_additionExpr -> ^( N_GTEQ_EXPR $n_gtltExpression $r) ) )*
            {
            // src/org/six11/flatcad/flatlang/FlatLang.g:154:4: ( n_additionExpr -> n_additionExpr )
            // src/org/six11/flatcad/flatlang/FlatLang.g:154:5: n_additionExpr
            {
            pushFollow(FOLLOW_n_additionExpr_in_n_gtltExpression956);
            n_additionExpr59=n_additionExpr();
            _fsp--;

            list_n_additionExpr.add(n_additionExpr59.getTree());

            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 154:20: -> n_additionExpr
            {
                adaptor.addChild(root_0, list_n_additionExpr.get(i_0));

            }



            }

            // src/org/six11/flatcad/flatlang/FlatLang.g:154:39: ( ( '<' r= n_additionExpr -> ^( N_LT_EXPR $n_gtltExpression $r) ) | ( '<=' r= n_additionExpr -> ^( N_LTEQ_EXPR $n_gtltExpression $r) ) | ( '>' r= n_additionExpr -> ^( N_GT_EXPR $n_gtltExpression $r) ) | ( '>=' r= n_additionExpr -> ^( N_GTEQ_EXPR $n_gtltExpression $r) ) )*
            loop18:
            do {
                int alt18=5;
                switch ( input.LA(1) ) {
                case 75:
                    alt18=1;
                    break;
                case 76:
                    alt18=2;
                    break;
                case 77:
                    alt18=3;
                    break;
                case 78:
                    alt18=4;
                    break;

                }

                switch (alt18) {
            	case 1 :
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:155:4: ( '<' r= n_additionExpr -> ^( N_LT_EXPR $n_gtltExpression $r) )
            	    {
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:155:4: ( '<' r= n_additionExpr -> ^( N_LT_EXPR $n_gtltExpression $r) )
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:155:5: '<' r= n_additionExpr
            	    {
            	    char_literal60=(Token)input.LT(1);
            	    match(input,75,FOLLOW_75_in_n_gtltExpression969); 
            	    list_75.add(char_literal60);

            	    pushFollow(FOLLOW_n_additionExpr_in_n_gtltExpression974);
            	    r=n_additionExpr();
            	    _fsp--;

            	    list_n_additionExpr.add(r.getTree());

            	    // AST REWRITE
            	    int i_0 = 0;
            	    retval.tree = root_0;
            	    root_0 = (CommonTree)adaptor.nil();
            	    // 155:27: -> ^( N_LT_EXPR $n_gtltExpression $r)
            	    {
            	        // src/org/six11/flatcad/flatlang/FlatLang.g:155:30: ^( N_LT_EXPR $n_gtltExpression $r)
            	        {
            	        CommonTree root_1 = (CommonTree)adaptor.nil();
            	        root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(N_LT_EXPR, "N_LT_EXPR"), root_1);

            	        adaptor.addChild(root_1, retval.tree);
            	        adaptor.addChild(root_1, r.tree);

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }



            	    }


            	    }
            	    break;
            	case 2 :
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:156:4: ( '<=' r= n_additionExpr -> ^( N_LTEQ_EXPR $n_gtltExpression $r) )
            	    {
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:156:4: ( '<=' r= n_additionExpr -> ^( N_LTEQ_EXPR $n_gtltExpression $r) )
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:156:5: '<=' r= n_additionExpr
            	    {
            	    string_literal61=(Token)input.LT(1);
            	    match(input,76,FOLLOW_76_in_n_gtltExpression995); 
            	    list_76.add(string_literal61);

            	    pushFollow(FOLLOW_n_additionExpr_in_n_gtltExpression999);
            	    r=n_additionExpr();
            	    _fsp--;

            	    list_n_additionExpr.add(r.getTree());

            	    // AST REWRITE
            	    int i_0 = 0;
            	    retval.tree = root_0;
            	    root_0 = (CommonTree)adaptor.nil();
            	    // 156:27: -> ^( N_LTEQ_EXPR $n_gtltExpression $r)
            	    {
            	        // src/org/six11/flatcad/flatlang/FlatLang.g:156:30: ^( N_LTEQ_EXPR $n_gtltExpression $r)
            	        {
            	        CommonTree root_1 = (CommonTree)adaptor.nil();
            	        root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(N_LTEQ_EXPR, "N_LTEQ_EXPR"), root_1);

            	        adaptor.addChild(root_1, retval.tree);
            	        adaptor.addChild(root_1, r.tree);

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }



            	    }


            	    }
            	    break;
            	case 3 :
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:157:4: ( '>' r= n_additionExpr -> ^( N_GT_EXPR $n_gtltExpression $r) )
            	    {
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:157:4: ( '>' r= n_additionExpr -> ^( N_GT_EXPR $n_gtltExpression $r) )
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:157:5: '>' r= n_additionExpr
            	    {
            	    char_literal62=(Token)input.LT(1);
            	    match(input,77,FOLLOW_77_in_n_gtltExpression1020); 
            	    list_77.add(char_literal62);

            	    pushFollow(FOLLOW_n_additionExpr_in_n_gtltExpression1025);
            	    r=n_additionExpr();
            	    _fsp--;

            	    list_n_additionExpr.add(r.getTree());

            	    // AST REWRITE
            	    int i_0 = 0;
            	    retval.tree = root_0;
            	    root_0 = (CommonTree)adaptor.nil();
            	    // 157:27: -> ^( N_GT_EXPR $n_gtltExpression $r)
            	    {
            	        // src/org/six11/flatcad/flatlang/FlatLang.g:157:30: ^( N_GT_EXPR $n_gtltExpression $r)
            	        {
            	        CommonTree root_1 = (CommonTree)adaptor.nil();
            	        root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(N_GT_EXPR, "N_GT_EXPR"), root_1);

            	        adaptor.addChild(root_1, retval.tree);
            	        adaptor.addChild(root_1, r.tree);

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }



            	    }


            	    }
            	    break;
            	case 4 :
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:158:4: ( '>=' r= n_additionExpr -> ^( N_GTEQ_EXPR $n_gtltExpression $r) )
            	    {
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:158:4: ( '>=' r= n_additionExpr -> ^( N_GTEQ_EXPR $n_gtltExpression $r) )
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:158:5: '>=' r= n_additionExpr
            	    {
            	    string_literal63=(Token)input.LT(1);
            	    match(input,78,FOLLOW_78_in_n_gtltExpression1046); 
            	    list_78.add(string_literal63);

            	    pushFollow(FOLLOW_n_additionExpr_in_n_gtltExpression1050);
            	    r=n_additionExpr();
            	    _fsp--;

            	    list_n_additionExpr.add(r.getTree());

            	    // AST REWRITE
            	    int i_0 = 0;
            	    retval.tree = root_0;
            	    root_0 = (CommonTree)adaptor.nil();
            	    // 158:27: -> ^( N_GTEQ_EXPR $n_gtltExpression $r)
            	    {
            	        // src/org/six11/flatcad/flatlang/FlatLang.g:158:30: ^( N_GTEQ_EXPR $n_gtltExpression $r)
            	        {
            	        CommonTree root_1 = (CommonTree)adaptor.nil();
            	        root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(N_GTEQ_EXPR, "N_GTEQ_EXPR"), root_1);

            	        adaptor.addChild(root_1, retval.tree);
            	        adaptor.addChild(root_1, r.tree);

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }



            	    }


            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end n_gtltExpression

    public static class n_additionExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start n_additionExpr
    // src/org/six11/flatcad/flatlang/FlatLang.g:162:1: n_additionExpr : ( n_multExpr -> n_multExpr ) ( ( '+' r= n_multExpr -> ^( N_ADD_EXPR $n_additionExpr $r) ) | ( '-' r= n_multExpr -> ^( N_MINUS_EXPR $n_additionExpr $r) ) )* ;
    public n_additionExpr_return n_additionExpr() throws RecognitionException {
        n_additionExpr_return retval = new n_additionExpr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal65=null;
        Token char_literal66=null;
        n_multExpr_return r = null;

        n_multExpr_return n_multExpr64 = null;

        List list_n_multExpr=new ArrayList();
        List list_79=new ArrayList();
        List list_80=new ArrayList();
        CommonTree char_literal65_tree=null;
        CommonTree char_literal66_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:163:4: ( ( n_multExpr -> n_multExpr ) ( ( '+' r= n_multExpr -> ^( N_ADD_EXPR $n_additionExpr $r) ) | ( '-' r= n_multExpr -> ^( N_MINUS_EXPR $n_additionExpr $r) ) )* )
            // src/org/six11/flatcad/flatlang/FlatLang.g:163:4: ( n_multExpr -> n_multExpr ) ( ( '+' r= n_multExpr -> ^( N_ADD_EXPR $n_additionExpr $r) ) | ( '-' r= n_multExpr -> ^( N_MINUS_EXPR $n_additionExpr $r) ) )*
            {
            // src/org/six11/flatcad/flatlang/FlatLang.g:163:4: ( n_multExpr -> n_multExpr )
            // src/org/six11/flatcad/flatlang/FlatLang.g:163:5: n_multExpr
            {
            pushFollow(FOLLOW_n_multExpr_in_n_additionExpr1080);
            n_multExpr64=n_multExpr();
            _fsp--;

            list_n_multExpr.add(n_multExpr64.getTree());

            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 163:16: -> n_multExpr
            {
                adaptor.addChild(root_0, list_n_multExpr.get(i_0));

            }



            }

            // src/org/six11/flatcad/flatlang/FlatLang.g:163:31: ( ( '+' r= n_multExpr -> ^( N_ADD_EXPR $n_additionExpr $r) ) | ( '-' r= n_multExpr -> ^( N_MINUS_EXPR $n_additionExpr $r) ) )*
            loop19:
            do {
                int alt19=3;
                int LA19_0 = input.LA(1);
                if ( (LA19_0==80) ) {
                    switch ( input.LA(2) ) {
                    case NUM:
                        alt19=2;
                        break;
                    case STR_LITERAL:
                        alt19=2;
                        break;
                    case TRUE:
                        alt19=2;
                        break;
                    case FALSE:
                        alt19=2;
                        break;
                    case INFINITY:
                        alt19=2;
                        break;
                    case OBJECT:
                        alt19=2;
                        break;
                    case ID:
                    case 79:
                    case 80:
                        alt19=2;
                        break;
                    case 64:
                        alt19=2;
                        break;
                    case 85:
                        alt19=2;
                        break;

                    }

                }
                else if ( (LA19_0==79) ) {
                    switch ( input.LA(2) ) {
                    case 79:
                    case 80:
                        alt19=1;
                        break;
                    case NUM:
                        alt19=1;
                        break;
                    case STR_LITERAL:
                        alt19=1;
                        break;
                    case TRUE:
                        alt19=1;
                        break;
                    case FALSE:
                        alt19=1;
                        break;
                    case INFINITY:
                        alt19=1;
                        break;
                    case OBJECT:
                        alt19=1;
                        break;
                    case ID:
                        alt19=1;
                        break;
                    case 64:
                        alt19=1;
                        break;
                    case 85:
                        alt19=1;
                        break;

                    }

                }


                switch (alt19) {
            	case 1 :
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:164:3: ( '+' r= n_multExpr -> ^( N_ADD_EXPR $n_additionExpr $r) )
            	    {
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:164:3: ( '+' r= n_multExpr -> ^( N_ADD_EXPR $n_additionExpr $r) )
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:164:4: '+' r= n_multExpr
            	    {
            	    char_literal65=(Token)input.LT(1);
            	    match(input,79,FOLLOW_79_in_n_additionExpr1092); 
            	    list_79.add(char_literal65);

            	    pushFollow(FOLLOW_n_multExpr_in_n_additionExpr1096);
            	    r=n_multExpr();
            	    _fsp--;

            	    list_n_multExpr.add(r.getTree());

            	    // AST REWRITE
            	    int i_0 = 0;
            	    retval.tree = root_0;
            	    root_0 = (CommonTree)adaptor.nil();
            	    // 164:21: -> ^( N_ADD_EXPR $n_additionExpr $r)
            	    {
            	        // src/org/six11/flatcad/flatlang/FlatLang.g:164:24: ^( N_ADD_EXPR $n_additionExpr $r)
            	        {
            	        CommonTree root_1 = (CommonTree)adaptor.nil();
            	        root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(N_ADD_EXPR, "N_ADD_EXPR"), root_1);

            	        adaptor.addChild(root_1, retval.tree);
            	        adaptor.addChild(root_1, r.tree);

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }



            	    }


            	    }
            	    break;
            	case 2 :
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:165:3: ( '-' r= n_multExpr -> ^( N_MINUS_EXPR $n_additionExpr $r) )
            	    {
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:165:3: ( '-' r= n_multExpr -> ^( N_MINUS_EXPR $n_additionExpr $r) )
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:165:4: '-' r= n_multExpr
            	    {
            	    char_literal66=(Token)input.LT(1);
            	    match(input,80,FOLLOW_80_in_n_additionExpr1120); 
            	    list_80.add(char_literal66);

            	    pushFollow(FOLLOW_n_multExpr_in_n_additionExpr1124);
            	    r=n_multExpr();
            	    _fsp--;

            	    list_n_multExpr.add(r.getTree());

            	    // AST REWRITE
            	    int i_0 = 0;
            	    retval.tree = root_0;
            	    root_0 = (CommonTree)adaptor.nil();
            	    // 165:21: -> ^( N_MINUS_EXPR $n_additionExpr $r)
            	    {
            	        // src/org/six11/flatcad/flatlang/FlatLang.g:165:24: ^( N_MINUS_EXPR $n_additionExpr $r)
            	        {
            	        CommonTree root_1 = (CommonTree)adaptor.nil();
            	        root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(N_MINUS_EXPR, "N_MINUS_EXPR"), root_1);

            	        adaptor.addChild(root_1, retval.tree);
            	        adaptor.addChild(root_1, r.tree);

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }



            	    }


            	    }
            	    break;

            	default :
            	    break loop19;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end n_additionExpr

    public static class n_multExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start n_multExpr
    // src/org/six11/flatcad/flatlang/FlatLang.g:169:1: n_multExpr : ( n_unaryExpr -> n_unaryExpr ) ( ( '*' r= n_unaryExpr -> ^( N_MULT_EXPR $n_multExpr $r) ) | ( '/' r= n_unaryExpr -> ^( N_DIV_EXPR $n_multExpr $r) ) | ( '%' r= n_unaryExpr -> ^( N_MODULO_EXPR $n_multExpr $r) ) )* ;
    public n_multExpr_return n_multExpr() throws RecognitionException {
        n_multExpr_return retval = new n_multExpr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal68=null;
        Token char_literal69=null;
        Token char_literal70=null;
        n_unaryExpr_return r = null;

        n_unaryExpr_return n_unaryExpr67 = null;

        List list_n_unaryExpr=new ArrayList();
        List list_83=new ArrayList();
        List list_81=new ArrayList();
        List list_82=new ArrayList();
        CommonTree char_literal68_tree=null;
        CommonTree char_literal69_tree=null;
        CommonTree char_literal70_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:170:4: ( ( n_unaryExpr -> n_unaryExpr ) ( ( '*' r= n_unaryExpr -> ^( N_MULT_EXPR $n_multExpr $r) ) | ( '/' r= n_unaryExpr -> ^( N_DIV_EXPR $n_multExpr $r) ) | ( '%' r= n_unaryExpr -> ^( N_MODULO_EXPR $n_multExpr $r) ) )* )
            // src/org/six11/flatcad/flatlang/FlatLang.g:170:4: ( n_unaryExpr -> n_unaryExpr ) ( ( '*' r= n_unaryExpr -> ^( N_MULT_EXPR $n_multExpr $r) ) | ( '/' r= n_unaryExpr -> ^( N_DIV_EXPR $n_multExpr $r) ) | ( '%' r= n_unaryExpr -> ^( N_MODULO_EXPR $n_multExpr $r) ) )*
            {
            // src/org/six11/flatcad/flatlang/FlatLang.g:170:4: ( n_unaryExpr -> n_unaryExpr )
            // src/org/six11/flatcad/flatlang/FlatLang.g:170:5: n_unaryExpr
            {
            pushFollow(FOLLOW_n_unaryExpr_in_n_multExpr1153);
            n_unaryExpr67=n_unaryExpr();
            _fsp--;

            list_n_unaryExpr.add(n_unaryExpr67.getTree());

            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 170:17: -> n_unaryExpr
            {
                adaptor.addChild(root_0, list_n_unaryExpr.get(i_0));

            }



            }

            // src/org/six11/flatcad/flatlang/FlatLang.g:170:33: ( ( '*' r= n_unaryExpr -> ^( N_MULT_EXPR $n_multExpr $r) ) | ( '/' r= n_unaryExpr -> ^( N_DIV_EXPR $n_multExpr $r) ) | ( '%' r= n_unaryExpr -> ^( N_MODULO_EXPR $n_multExpr $r) ) )*
            loop20:
            do {
                int alt20=4;
                switch ( input.LA(1) ) {
                case 81:
                    alt20=1;
                    break;
                case 82:
                    alt20=2;
                    break;
                case 83:
                    alt20=3;
                    break;

                }

                switch (alt20) {
            	case 1 :
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:171:3: ( '*' r= n_unaryExpr -> ^( N_MULT_EXPR $n_multExpr $r) )
            	    {
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:171:3: ( '*' r= n_unaryExpr -> ^( N_MULT_EXPR $n_multExpr $r) )
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:171:4: '*' r= n_unaryExpr
            	    {
            	    char_literal68=(Token)input.LT(1);
            	    match(input,81,FOLLOW_81_in_n_multExpr1165); 
            	    list_81.add(char_literal68);

            	    pushFollow(FOLLOW_n_unaryExpr_in_n_multExpr1169);
            	    r=n_unaryExpr();
            	    _fsp--;

            	    list_n_unaryExpr.add(r.getTree());

            	    // AST REWRITE
            	    int i_0 = 0;
            	    retval.tree = root_0;
            	    root_0 = (CommonTree)adaptor.nil();
            	    // 171:22: -> ^( N_MULT_EXPR $n_multExpr $r)
            	    {
            	        // src/org/six11/flatcad/flatlang/FlatLang.g:171:25: ^( N_MULT_EXPR $n_multExpr $r)
            	        {
            	        CommonTree root_1 = (CommonTree)adaptor.nil();
            	        root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(N_MULT_EXPR, "N_MULT_EXPR"), root_1);

            	        adaptor.addChild(root_1, retval.tree);
            	        adaptor.addChild(root_1, r.tree);

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }



            	    }


            	    }
            	    break;
            	case 2 :
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:172:3: ( '/' r= n_unaryExpr -> ^( N_DIV_EXPR $n_multExpr $r) )
            	    {
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:172:3: ( '/' r= n_unaryExpr -> ^( N_DIV_EXPR $n_multExpr $r) )
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:172:4: '/' r= n_unaryExpr
            	    {
            	    char_literal69=(Token)input.LT(1);
            	    match(input,82,FOLLOW_82_in_n_multExpr1191); 
            	    list_82.add(char_literal69);

            	    pushFollow(FOLLOW_n_unaryExpr_in_n_multExpr1195);
            	    r=n_unaryExpr();
            	    _fsp--;

            	    list_n_unaryExpr.add(r.getTree());

            	    // AST REWRITE
            	    int i_0 = 0;
            	    retval.tree = root_0;
            	    root_0 = (CommonTree)adaptor.nil();
            	    // 172:22: -> ^( N_DIV_EXPR $n_multExpr $r)
            	    {
            	        // src/org/six11/flatcad/flatlang/FlatLang.g:172:25: ^( N_DIV_EXPR $n_multExpr $r)
            	        {
            	        CommonTree root_1 = (CommonTree)adaptor.nil();
            	        root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(N_DIV_EXPR, "N_DIV_EXPR"), root_1);

            	        adaptor.addChild(root_1, retval.tree);
            	        adaptor.addChild(root_1, r.tree);

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }



            	    }


            	    }
            	    break;
            	case 3 :
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:173:3: ( '%' r= n_unaryExpr -> ^( N_MODULO_EXPR $n_multExpr $r) )
            	    {
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:173:3: ( '%' r= n_unaryExpr -> ^( N_MODULO_EXPR $n_multExpr $r) )
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:173:4: '%' r= n_unaryExpr
            	    {
            	    char_literal70=(Token)input.LT(1);
            	    match(input,83,FOLLOW_83_in_n_multExpr1218); 
            	    list_83.add(char_literal70);

            	    pushFollow(FOLLOW_n_unaryExpr_in_n_multExpr1222);
            	    r=n_unaryExpr();
            	    _fsp--;

            	    list_n_unaryExpr.add(r.getTree());

            	    // AST REWRITE
            	    int i_0 = 0;
            	    retval.tree = root_0;
            	    root_0 = (CommonTree)adaptor.nil();
            	    // 173:22: -> ^( N_MODULO_EXPR $n_multExpr $r)
            	    {
            	        // src/org/six11/flatcad/flatlang/FlatLang.g:173:25: ^( N_MODULO_EXPR $n_multExpr $r)
            	        {
            	        CommonTree root_1 = (CommonTree)adaptor.nil();
            	        root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(N_MODULO_EXPR, "N_MODULO_EXPR"), root_1);

            	        adaptor.addChild(root_1, retval.tree);
            	        adaptor.addChild(root_1, r.tree);

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }



            	    }


            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end n_multExpr

    public static class n_unaryExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start n_unaryExpr
    // src/org/six11/flatcad/flatlang/FlatLang.g:177:1: n_unaryExpr : ( '-' n_primary -> ^( N_UNARY_NEG_EXPR n_primary ) | ( '+' )? n_primary -> ^( N_UNARY_POS_EXPR n_primary ) );
    public n_unaryExpr_return n_unaryExpr() throws RecognitionException {
        n_unaryExpr_return retval = new n_unaryExpr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal71=null;
        Token char_literal73=null;
        n_primary_return n_primary72 = null;

        n_primary_return n_primary74 = null;

        List list_n_primary=new ArrayList();
        List list_79=new ArrayList();
        List list_80=new ArrayList();
        CommonTree char_literal71_tree=null;
        CommonTree char_literal73_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:179:2: ( '-' n_primary -> ^( N_UNARY_NEG_EXPR n_primary ) | ( '+' )? n_primary -> ^( N_UNARY_POS_EXPR n_primary ) )
            int alt22=2;
            int LA22_0 = input.LA(1);
            if ( (LA22_0==80) ) {
                alt22=1;
            }
            else if ( ((LA22_0>=ID && LA22_0<=OBJECT)||(LA22_0>=NUM && LA22_0<=STR_LITERAL)||LA22_0==64||LA22_0==79||LA22_0==85) ) {
                alt22=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("177:1: n_unaryExpr : ( '-' n_primary -> ^( N_UNARY_NEG_EXPR n_primary ) | ( '+' )? n_primary -> ^( N_UNARY_POS_EXPR n_primary ) );", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:179:2: '-' n_primary
                    {
                    char_literal71=(Token)input.LT(1);
                    match(input,80,FOLLOW_80_in_n_unaryExpr1251); 
                    list_80.add(char_literal71);

                    pushFollow(FOLLOW_n_primary_in_n_unaryExpr1253);
                    n_primary72=n_primary();
                    _fsp--;

                    list_n_primary.add(n_primary72.getTree());

                    // AST REWRITE
                    int i_0 = 0;
                    retval.tree = root_0;
                    root_0 = (CommonTree)adaptor.nil();
                    // 179:17: -> ^( N_UNARY_NEG_EXPR n_primary )
                    {
                        // src/org/six11/flatcad/flatlang/FlatLang.g:179:20: ^( N_UNARY_NEG_EXPR n_primary )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(N_UNARY_NEG_EXPR, "N_UNARY_NEG_EXPR"), root_1);

                        adaptor.addChild(root_1, list_n_primary.get(i_0));

                        adaptor.addChild(root_0, root_1);
                        }

                    }



                    }
                    break;
                case 2 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:180:2: ( '+' )? n_primary
                    {
                    // src/org/six11/flatcad/flatlang/FlatLang.g:180:2: ( '+' )?
                    int alt21=2;
                    int LA21_0 = input.LA(1);
                    if ( (LA21_0==79) ) {
                        alt21=1;
                    }
                    switch (alt21) {
                        case 1 :
                            // src/org/six11/flatcad/flatlang/FlatLang.g:180:2: '+'
                            {
                            char_literal73=(Token)input.LT(1);
                            match(input,79,FOLLOW_79_in_n_unaryExpr1267); 
                            list_79.add(char_literal73);


                            }
                            break;

                    }

                    pushFollow(FOLLOW_n_primary_in_n_unaryExpr1270);
                    n_primary74=n_primary();
                    _fsp--;

                    list_n_primary.add(n_primary74.getTree());

                    // AST REWRITE
                    int i_0 = 0;
                    retval.tree = root_0;
                    root_0 = (CommonTree)adaptor.nil();
                    // 180:17: -> ^( N_UNARY_POS_EXPR n_primary )
                    {
                        // src/org/six11/flatcad/flatlang/FlatLang.g:180:20: ^( N_UNARY_POS_EXPR n_primary )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(N_UNARY_POS_EXPR, "N_UNARY_POS_EXPR"), root_1);

                        adaptor.addChild(root_1, list_n_primary.get(i_0));

                        adaptor.addChild(root_0, root_1);
                        }

                    }



                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end n_unaryExpr

    public static class n_primary_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start n_primary
    // src/org/six11/flatcad/flatlang/FlatLang.g:195:1: n_primary : ( n_primaryNoDot -> n_primaryNoDot ) ( ( '.' r= n_primaryNoDot ) -> ^( MEMBER_EXPR $n_primary $r) )* ;
    public n_primary_return n_primary() throws RecognitionException {
        n_primary_return retval = new n_primary_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal76=null;
        n_primaryNoDot_return r = null;

        n_primaryNoDot_return n_primaryNoDot75 = null;

        List list_n_primaryNoDot=new ArrayList();
        List list_84=new ArrayList();
        CommonTree char_literal76_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:196:4: ( ( n_primaryNoDot -> n_primaryNoDot ) ( ( '.' r= n_primaryNoDot ) -> ^( MEMBER_EXPR $n_primary $r) )* )
            // src/org/six11/flatcad/flatlang/FlatLang.g:196:4: ( n_primaryNoDot -> n_primaryNoDot ) ( ( '.' r= n_primaryNoDot ) -> ^( MEMBER_EXPR $n_primary $r) )*
            {
            // src/org/six11/flatcad/flatlang/FlatLang.g:196:4: ( n_primaryNoDot -> n_primaryNoDot )
            // src/org/six11/flatcad/flatlang/FlatLang.g:196:5: n_primaryNoDot
            {
            pushFollow(FOLLOW_n_primaryNoDot_in_n_primary1293);
            n_primaryNoDot75=n_primaryNoDot();
            _fsp--;

            list_n_primaryNoDot.add(n_primaryNoDot75.getTree());

            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 196:20: -> n_primaryNoDot
            {
                adaptor.addChild(root_0, list_n_primaryNoDot.get(i_0));

            }



            }

            // src/org/six11/flatcad/flatlang/FlatLang.g:196:39: ( ( '.' r= n_primaryNoDot ) -> ^( MEMBER_EXPR $n_primary $r) )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);
                if ( (LA23_0==84) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:197:5: ( '.' r= n_primaryNoDot )
            	    {
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:197:5: ( '.' r= n_primaryNoDot )
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:197:6: '.' r= n_primaryNoDot
            	    {
            	    char_literal76=(Token)input.LT(1);
            	    match(input,84,FOLLOW_84_in_n_primary1307); 
            	    list_84.add(char_literal76);

            	    pushFollow(FOLLOW_n_primaryNoDot_in_n_primary1311);
            	    r=n_primaryNoDot();
            	    _fsp--;

            	    list_n_primaryNoDot.add(r.getTree());

            	    }


            	    // AST REWRITE
            	    int i_0 = 0;
            	    retval.tree = root_0;
            	    root_0 = (CommonTree)adaptor.nil();
            	    // 197:28: -> ^( MEMBER_EXPR $n_primary $r)
            	    {
            	        // src/org/six11/flatcad/flatlang/FlatLang.g:197:31: ^( MEMBER_EXPR $n_primary $r)
            	        {
            	        CommonTree root_1 = (CommonTree)adaptor.nil();
            	        root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(MEMBER_EXPR, "MEMBER_EXPR"), root_1);

            	        adaptor.addChild(root_1, retval.tree);
            	        adaptor.addChild(root_1, r.tree);

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }



            	    }
            	    break;

            	default :
            	    break loop23;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end n_primary

    public static class n_primaryNoDot_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start n_primaryNoDot
    // src/org/six11/flatcad/flatlang/FlatLang.g:206:1: n_primaryNoDot : ( n_literal | n_indexExpr | parExpression | listExpression );
    public n_primaryNoDot_return n_primaryNoDot() throws RecognitionException {
        n_primaryNoDot_return retval = new n_primaryNoDot_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        n_literal_return n_literal77 = null;

        n_indexExpr_return n_indexExpr78 = null;

        parExpression_return parExpression79 = null;

        listExpression_return listExpression80 = null;



        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:207:4: ( n_literal | n_indexExpr | parExpression | listExpression )
            int alt24=4;
            switch ( input.LA(1) ) {
            case TRUE:
            case FALSE:
            case INFINITY:
            case OBJECT:
            case NUM:
            case STR_LITERAL:
                alt24=1;
                break;
            case ID:
                alt24=2;
                break;
            case 64:
                alt24=3;
                break;
            case 85:
                alt24=4;
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("206:1: n_primaryNoDot : ( n_literal | n_indexExpr | parExpression | listExpression );", 24, 0, input);

                throw nvae;
            }

            switch (alt24) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:207:4: n_literal
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_n_literal_in_n_primaryNoDot1348);
                    n_literal77=n_literal();
                    _fsp--;

                    adaptor.addChild(root_0, n_literal77.getTree());

                    }
                    break;
                case 2 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:208:4: n_indexExpr
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_n_indexExpr_in_n_primaryNoDot1353);
                    n_indexExpr78=n_indexExpr();
                    _fsp--;

                    adaptor.addChild(root_0, n_indexExpr78.getTree());

                    }
                    break;
                case 3 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:209:4: parExpression
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_parExpression_in_n_primaryNoDot1358);
                    parExpression79=parExpression();
                    _fsp--;

                    adaptor.addChild(root_0, parExpression79.getTree());

                    }
                    break;
                case 4 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:210:4: listExpression
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_listExpression_in_n_primaryNoDot1363);
                    listExpression80=listExpression();
                    _fsp--;

                    adaptor.addChild(root_0, listExpression80.getTree());

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end n_primaryNoDot

    public static class listExpression_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start listExpression
    // src/org/six11/flatcad/flatlang/FlatLang.g:213:1: listExpression : '[' n_expressionList ']' -> ^( LIST_EXPR n_expressionList ) ;
    public listExpression_return listExpression() throws RecognitionException {
        listExpression_return retval = new listExpression_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal81=null;
        Token char_literal83=null;
        n_expressionList_return n_expressionList82 = null;

        List list_n_expressionList=new ArrayList();
        List list_85=new ArrayList();
        List list_86=new ArrayList();
        CommonTree char_literal81_tree=null;
        CommonTree char_literal83_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:214:4: ( '[' n_expressionList ']' -> ^( LIST_EXPR n_expressionList ) )
            // src/org/six11/flatcad/flatlang/FlatLang.g:214:4: '[' n_expressionList ']'
            {
            char_literal81=(Token)input.LT(1);
            match(input,85,FOLLOW_85_in_listExpression1374); 
            list_85.add(char_literal81);

            pushFollow(FOLLOW_n_expressionList_in_listExpression1376);
            n_expressionList82=n_expressionList();
            _fsp--;

            list_n_expressionList.add(n_expressionList82.getTree());
            char_literal83=(Token)input.LT(1);
            match(input,86,FOLLOW_86_in_listExpression1378); 
            list_86.add(char_literal83);


            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 214:29: -> ^( LIST_EXPR n_expressionList )
            {
                // src/org/six11/flatcad/flatlang/FlatLang.g:214:32: ^( LIST_EXPR n_expressionList )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(LIST_EXPR, "LIST_EXPR"), root_1);

                adaptor.addChild(root_1, list_n_expressionList.get(i_0));

                adaptor.addChild(root_0, root_1);
                }

            }



            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end listExpression

    public static class n_indexExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start n_indexExpr
    // src/org/six11/flatcad/flatlang/FlatLang.g:218:1: n_indexExpr : ( n_variable -> ^( n_variable ) ) ( ( '[' n_assignExpression ']' )+ -> ^( INDEX_EXPR $n_indexExpr ( n_assignExpression )+ ) )? ;
    public n_indexExpr_return n_indexExpr() throws RecognitionException {
        n_indexExpr_return retval = new n_indexExpr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal85=null;
        Token char_literal87=null;
        n_variable_return n_variable84 = null;

        n_assignExpression_return n_assignExpression86 = null;

        List list_n_assignExpression=new ArrayList();
        List list_n_variable=new ArrayList();
        List list_85=new ArrayList();
        List list_86=new ArrayList();
        CommonTree char_literal85_tree=null;
        CommonTree char_literal87_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:219:4: ( ( n_variable -> ^( n_variable ) ) ( ( '[' n_assignExpression ']' )+ -> ^( INDEX_EXPR $n_indexExpr ( n_assignExpression )+ ) )? )
            // src/org/six11/flatcad/flatlang/FlatLang.g:219:4: ( n_variable -> ^( n_variable ) ) ( ( '[' n_assignExpression ']' )+ -> ^( INDEX_EXPR $n_indexExpr ( n_assignExpression )+ ) )?
            {
            // src/org/six11/flatcad/flatlang/FlatLang.g:219:4: ( n_variable -> ^( n_variable ) )
            // src/org/six11/flatcad/flatlang/FlatLang.g:219:5: n_variable
            {
            pushFollow(FOLLOW_n_variable_in_n_indexExpr1399);
            n_variable84=n_variable();
            _fsp--;

            list_n_variable.add(n_variable84.getTree());

            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 219:16: -> ^( n_variable )
            {
                // src/org/six11/flatcad/flatlang/FlatLang.g:219:19: ^( n_variable )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(list_n_variable.get(i_0), root_1);

                adaptor.addChild(root_0, root_1);
                }

            }



            }

            // src/org/six11/flatcad/flatlang/FlatLang.g:219:34: ( ( '[' n_assignExpression ']' )+ -> ^( INDEX_EXPR $n_indexExpr ( n_assignExpression )+ ) )?
            int alt26=2;
            int LA26_0 = input.LA(1);
            if ( (LA26_0==85) ) {
                switch ( input.LA(2) ) {
                    case 80:
                        alt26=1;
                        break;
                    case 79:
                        alt26=1;
                        break;
                    case NUM:
                        alt26=1;
                        break;
                    case STR_LITERAL:
                        alt26=1;
                        break;
                    case TRUE:
                        alt26=1;
                        break;
                    case FALSE:
                        alt26=1;
                        break;
                    case INFINITY:
                        alt26=1;
                        break;
                    case OBJECT:
                        alt26=1;
                        break;
                    case ID:
                        alt26=1;
                        break;
                    case 64:
                        alt26=1;
                        break;
                    case 85:
                        alt26=1;
                        break;
                }

            }
            switch (alt26) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:220:4: ( '[' n_assignExpression ']' )+
                    {
                    // src/org/six11/flatcad/flatlang/FlatLang.g:220:4: ( '[' n_assignExpression ']' )+
                    int cnt25=0;
                    loop25:
                    do {
                        int alt25=2;
                        int LA25_0 = input.LA(1);
                        if ( (LA25_0==85) ) {
                            switch ( input.LA(2) ) {
                            case 80:
                                alt25=1;
                                break;
                            case 79:
                                alt25=1;
                                break;
                            case NUM:
                                alt25=1;
                                break;
                            case STR_LITERAL:
                                alt25=1;
                                break;
                            case TRUE:
                                alt25=1;
                                break;
                            case FALSE:
                                alt25=1;
                                break;
                            case INFINITY:
                                alt25=1;
                                break;
                            case OBJECT:
                                alt25=1;
                                break;
                            case ID:
                                alt25=1;
                                break;
                            case 64:
                                alt25=1;
                                break;
                            case 85:
                                alt25=1;
                                break;

                            }

                        }


                        switch (alt25) {
                    	case 1 :
                    	    // src/org/six11/flatcad/flatlang/FlatLang.g:220:5: '[' n_assignExpression ']'
                    	    {
                    	    char_literal85=(Token)input.LT(1);
                    	    match(input,85,FOLLOW_85_in_n_indexExpr1414); 
                    	    list_85.add(char_literal85);

                    	    pushFollow(FOLLOW_n_assignExpression_in_n_indexExpr1416);
                    	    n_assignExpression86=n_assignExpression();
                    	    _fsp--;

                    	    list_n_assignExpression.add(n_assignExpression86.getTree());
                    	    char_literal87=(Token)input.LT(1);
                    	    match(input,86,FOLLOW_86_in_n_indexExpr1418); 
                    	    list_86.add(char_literal87);


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt25 >= 1 ) break loop25;
                                EarlyExitException eee =
                                    new EarlyExitException(25, input);
                                throw eee;
                        }
                        cnt25++;
                    } while (true);


                    // AST REWRITE
                    int i_0 = 0;
                    retval.tree = root_0;
                    root_0 = (CommonTree)adaptor.nil();
                    // 220:34: -> ^( INDEX_EXPR $n_indexExpr ( n_assignExpression )+ )
                    {
                        // src/org/six11/flatcad/flatlang/FlatLang.g:220:37: ^( INDEX_EXPR $n_indexExpr ( n_assignExpression )+ )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(INDEX_EXPR, "INDEX_EXPR"), root_1);

                        adaptor.addChild(root_1, retval.tree);
                        // src/org/six11/flatcad/flatlang/FlatLang.g:220:63: ( n_assignExpression )+
                        {
                        int n_1 = list_n_assignExpression == null ? 0 : list_n_assignExpression.size();
                         


                        if ( n_1==0 ) throw new RuntimeException("Must have more than one element for (...)+ loops");
                        for (int i_1=0; i_1<n_1; i_1++) {
                            adaptor.addChild(root_1, list_n_assignExpression.get(i_1));

                        }
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }



                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end n_indexExpr

    public static class n_literal_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start n_literal
    // src/org/six11/flatcad/flatlang/FlatLang.g:225:1: n_literal : ( literalNum | literalString | TRUE | FALSE | INFINITY | OBJECT );
    public n_literal_return n_literal() throws RecognitionException {
        n_literal_return retval = new n_literal_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token TRUE90=null;
        Token FALSE91=null;
        Token INFINITY92=null;
        Token OBJECT93=null;
        literalNum_return literalNum88 = null;

        literalString_return literalString89 = null;


        CommonTree TRUE90_tree=null;
        CommonTree FALSE91_tree=null;
        CommonTree INFINITY92_tree=null;
        CommonTree OBJECT93_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:226:4: ( literalNum | literalString | TRUE | FALSE | INFINITY | OBJECT )
            int alt27=6;
            switch ( input.LA(1) ) {
            case NUM:
                alt27=1;
                break;
            case STR_LITERAL:
                alt27=2;
                break;
            case TRUE:
                alt27=3;
                break;
            case FALSE:
                alt27=4;
                break;
            case INFINITY:
                alt27=5;
                break;
            case OBJECT:
                alt27=6;
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("225:1: n_literal : ( literalNum | literalString | TRUE | FALSE | INFINITY | OBJECT );", 27, 0, input);

                throw nvae;
            }

            switch (alt27) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:226:4: literalNum
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_literalNum_in_n_literal1449);
                    literalNum88=literalNum();
                    _fsp--;

                    adaptor.addChild(root_0, literalNum88.getTree());

                    }
                    break;
                case 2 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:227:8: literalString
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_literalString_in_n_literal1458);
                    literalString89=literalString();
                    _fsp--;

                    adaptor.addChild(root_0, literalString89.getTree());

                    }
                    break;
                case 3 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:228:8: TRUE
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    TRUE90=(Token)input.LT(1);
                    match(input,TRUE,FOLLOW_TRUE_in_n_literal1467); 
                    TRUE90_tree = (CommonTree)adaptor.create(TRUE90);
                    adaptor.addChild(root_0, TRUE90_tree);


                    }
                    break;
                case 4 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:229:8: FALSE
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    FALSE91=(Token)input.LT(1);
                    match(input,FALSE,FOLLOW_FALSE_in_n_literal1476); 
                    FALSE91_tree = (CommonTree)adaptor.create(FALSE91);
                    adaptor.addChild(root_0, FALSE91_tree);


                    }
                    break;
                case 5 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:230:8: INFINITY
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    INFINITY92=(Token)input.LT(1);
                    match(input,INFINITY,FOLLOW_INFINITY_in_n_literal1485); 
                    INFINITY92_tree = (CommonTree)adaptor.create(INFINITY92);
                    adaptor.addChild(root_0, INFINITY92_tree);


                    }
                    break;
                case 6 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:231:8: OBJECT
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    OBJECT93=(Token)input.LT(1);
                    match(input,OBJECT,FOLLOW_OBJECT_in_n_literal1494); 
                    OBJECT93_tree = (CommonTree)adaptor.create(OBJECT93);
                    adaptor.addChild(root_0, OBJECT93_tree);


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end n_literal

    public static class n_variable_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start n_variable
    // src/org/six11/flatcad/flatlang/FlatLang.g:234:1: n_variable : ( ID -> ^( ID ) ) ( '(' p= n_expressionList ')' -> ^( FUNCTION_CALL $n_variable $p) )? ;
    public n_variable_return n_variable() throws RecognitionException {
        n_variable_return retval = new n_variable_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token ID94=null;
        Token char_literal95=null;
        Token char_literal96=null;
        n_expressionList_return p = null;

        List list_n_expressionList=new ArrayList();
        List list_65=new ArrayList();
        List list_64=new ArrayList();
        List list_ID=new ArrayList();
        CommonTree ID94_tree=null;
        CommonTree char_literal95_tree=null;
        CommonTree char_literal96_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:235:4: ( ( ID -> ^( ID ) ) ( '(' p= n_expressionList ')' -> ^( FUNCTION_CALL $n_variable $p) )? )
            // src/org/six11/flatcad/flatlang/FlatLang.g:235:4: ( ID -> ^( ID ) ) ( '(' p= n_expressionList ')' -> ^( FUNCTION_CALL $n_variable $p) )?
            {
            // src/org/six11/flatcad/flatlang/FlatLang.g:235:4: ( ID -> ^( ID ) )
            // src/org/six11/flatcad/flatlang/FlatLang.g:235:5: ID
            {
            ID94=(Token)input.LT(1);
            match(input,ID,FOLLOW_ID_in_n_variable1506); 
            list_ID.add(ID94);


            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 235:8: -> ^( ID )
            {
                // src/org/six11/flatcad/flatlang/FlatLang.g:235:11: ^( ID )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((Token)list_ID.get(i_0), root_1);

                adaptor.addChild(root_0, root_1);
                }

            }



            }

            // src/org/six11/flatcad/flatlang/FlatLang.g:235:18: ( '(' p= n_expressionList ')' -> ^( FUNCTION_CALL $n_variable $p) )?
            int alt28=2;
            int LA28_0 = input.LA(1);
            if ( (LA28_0==64) ) {
                switch ( input.LA(2) ) {
                    case 80:
                        alt28=1;
                        break;
                    case 79:
                        alt28=1;
                        break;
                    case NUM:
                        alt28=1;
                        break;
                    case STR_LITERAL:
                        alt28=1;
                        break;
                    case TRUE:
                        alt28=1;
                        break;
                    case FALSE:
                        alt28=1;
                        break;
                    case INFINITY:
                        alt28=1;
                        break;
                    case OBJECT:
                        alt28=1;
                        break;
                    case ID:
                        alt28=1;
                        break;
                    case 64:
                        alt28=1;
                        break;
                    case 85:
                        alt28=1;
                        break;
                    case 65:
                    case 89:
                        alt28=1;
                        break;
                }

            }
            switch (alt28) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:236:4: '(' p= n_expressionList ')'
                    {
                    char_literal95=(Token)input.LT(1);
                    match(input,64,FOLLOW_64_in_n_variable1520); 
                    list_64.add(char_literal95);

                    pushFollow(FOLLOW_n_expressionList_in_n_variable1524);
                    p=n_expressionList();
                    _fsp--;

                    list_n_expressionList.add(p.getTree());
                    char_literal96=(Token)input.LT(1);
                    match(input,65,FOLLOW_65_in_n_variable1526); 
                    list_65.add(char_literal96);


                    // AST REWRITE
                    int i_0 = 0;
                    retval.tree = root_0;
                    root_0 = (CommonTree)adaptor.nil();
                    // 236:31: -> ^( FUNCTION_CALL $n_variable $p)
                    {
                        // src/org/six11/flatcad/flatlang/FlatLang.g:236:34: ^( FUNCTION_CALL $n_variable $p)
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(FUNCTION_CALL, "FUNCTION_CALL"), root_1);

                        adaptor.addChild(root_1, retval.tree);
                        adaptor.addChild(root_1, p.tree);

                        adaptor.addChild(root_0, root_1);
                        }

                    }



                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end n_variable

    public static class definition_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start definition
    // src/org/six11/flatcad/flatlang/FlatLang.g:240:1: definition : 'define' ID ( paramList )? ( block )? 'done' -> ^( FUNCTION_DEF_SIGNATURE ID ( paramList )? ( block )? ) ;
    public definition_return definition() throws RecognitionException {
        definition_return retval = new definition_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal97=null;
        Token ID98=null;
        Token string_literal101=null;
        paramList_return paramList99 = null;

        block_return block100 = null;

        List list_paramList=new ArrayList();
        List list_block=new ArrayList();
        List list_87=new ArrayList();
        List list_60=new ArrayList();
        List list_ID=new ArrayList();
        CommonTree string_literal97_tree=null;
        CommonTree ID98_tree=null;
        CommonTree string_literal101_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:241:4: ( 'define' ID ( paramList )? ( block )? 'done' -> ^( FUNCTION_DEF_SIGNATURE ID ( paramList )? ( block )? ) )
            // src/org/six11/flatcad/flatlang/FlatLang.g:241:4: 'define' ID ( paramList )? ( block )? 'done'
            {
            string_literal97=(Token)input.LT(1);
            match(input,87,FOLLOW_87_in_definition1554); 
            list_87.add(string_literal97);

            ID98=(Token)input.LT(1);
            match(input,ID,FOLLOW_ID_in_definition1556); 
            list_ID.add(ID98);

            // src/org/six11/flatcad/flatlang/FlatLang.g:241:16: ( paramList )?
            int alt29=2;
            int LA29_0 = input.LA(1);
            if ( (LA29_0==64) ) {
                int LA29_1 = input.LA(2);
                if ( (LA29_1==65||LA29_1==69) ) {
                    alt29=1;
                }
            }
            switch (alt29) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:241:16: paramList
                    {
                    pushFollow(FOLLOW_paramList_in_definition1558);
                    paramList99=paramList();
                    _fsp--;

                    list_paramList.add(paramList99.getTree());

                    }
                    break;

            }

            // src/org/six11/flatcad/flatlang/FlatLang.g:241:27: ( block )?
            int alt30=2;
            int LA30_0 = input.LA(1);
            if ( ((LA30_0>=ID && LA30_0<=OBJECT)||(LA30_0>=NUM && LA30_0<=STR_LITERAL)||LA30_0==61||LA30_0==64||(LA30_0>=66 && LA30_0<=68)||(LA30_0>=79 && LA30_0<=80)||LA30_0==85||LA30_0==88||(LA30_0>=90 && LA30_0<=92)) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:241:27: block
                    {
                    pushFollow(FOLLOW_block_in_definition1561);
                    block100=block();
                    _fsp--;

                    list_block.add(block100.getTree());

                    }
                    break;

            }

            string_literal101=(Token)input.LT(1);
            match(input,60,FOLLOW_60_in_definition1564); 
            list_60.add(string_literal101);


            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 242:12: -> ^( FUNCTION_DEF_SIGNATURE ID ( paramList )? ( block )? )
            {
                // src/org/six11/flatcad/flatlang/FlatLang.g:242:15: ^( FUNCTION_DEF_SIGNATURE ID ( paramList )? ( block )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(FUNCTION_DEF_SIGNATURE, "FUNCTION_DEF_SIGNATURE"), root_1);

                adaptor.addChild(root_1, (Token)list_ID.get(i_0));
                // src/org/six11/flatcad/flatlang/FlatLang.g:242:43: ( paramList )?
                {
                int n_1 = list_paramList == null ? 0 : list_paramList.size();
                 


                if ( n_1 > 1 ) throw new RuntimeException("paramList list has > 1 elements");
                if ( n_1==1 ) {
                    int i_1 = 0;
                    adaptor.addChild(root_1, list_paramList.get(i_1));

                    }
                }
                // src/org/six11/flatcad/flatlang/FlatLang.g:242:54: ( block )?
                {
                int n_1 = list_block == null ? 0 : list_block.size();
                 


                if ( n_1 > 1 ) throw new RuntimeException("block list has > 1 elements");
                if ( n_1==1 ) {
                    int i_1 = 0;
                    adaptor.addChild(root_1, list_block.get(i_1));

                    }
                }

                adaptor.addChild(root_0, root_1);
                }

            }



            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end definition

    public static class n_definition_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start n_definition
    // src/org/six11/flatcad/flatlang/FlatLang.g:245:1: n_definition : ( SAFE )? 'define' ID '(' n_paramList ')' ( block )? 'done' -> ^( FUNCTION_DEF_SIGNATURE ID ( n_paramList )? ( block )? ( SAFE )? ) ;
    public n_definition_return n_definition() throws RecognitionException {
        n_definition_return retval = new n_definition_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token SAFE102=null;
        Token string_literal103=null;
        Token ID104=null;
        Token char_literal105=null;
        Token char_literal107=null;
        Token string_literal109=null;
        n_paramList_return n_paramList106 = null;

        block_return block108 = null;

        List list_n_paramList=new ArrayList();
        List list_block=new ArrayList();
        List list_65=new ArrayList();
        List list_87=new ArrayList();
        List list_64=new ArrayList();
        List list_60=new ArrayList();
        List list_SAFE=new ArrayList();
        List list_ID=new ArrayList();
        CommonTree SAFE102_tree=null;
        CommonTree string_literal103_tree=null;
        CommonTree ID104_tree=null;
        CommonTree char_literal105_tree=null;
        CommonTree char_literal107_tree=null;
        CommonTree string_literal109_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:246:4: ( ( SAFE )? 'define' ID '(' n_paramList ')' ( block )? 'done' -> ^( FUNCTION_DEF_SIGNATURE ID ( n_paramList )? ( block )? ( SAFE )? ) )
            // src/org/six11/flatcad/flatlang/FlatLang.g:246:4: ( SAFE )? 'define' ID '(' n_paramList ')' ( block )? 'done'
            {
            // src/org/six11/flatcad/flatlang/FlatLang.g:246:4: ( SAFE )?
            int alt31=2;
            int LA31_0 = input.LA(1);
            if ( (LA31_0==SAFE) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:246:4: SAFE
                    {
                    SAFE102=(Token)input.LT(1);
                    match(input,SAFE,FOLLOW_SAFE_in_n_definition1601); 
                    list_SAFE.add(SAFE102);


                    }
                    break;

            }

            string_literal103=(Token)input.LT(1);
            match(input,87,FOLLOW_87_in_n_definition1604); 
            list_87.add(string_literal103);

            ID104=(Token)input.LT(1);
            match(input,ID,FOLLOW_ID_in_n_definition1606); 
            list_ID.add(ID104);

            char_literal105=(Token)input.LT(1);
            match(input,64,FOLLOW_64_in_n_definition1608); 
            list_64.add(char_literal105);

            pushFollow(FOLLOW_n_paramList_in_n_definition1610);
            n_paramList106=n_paramList();
            _fsp--;

            list_n_paramList.add(n_paramList106.getTree());
            char_literal107=(Token)input.LT(1);
            match(input,65,FOLLOW_65_in_n_definition1612); 
            list_65.add(char_literal107);

            // src/org/six11/flatcad/flatlang/FlatLang.g:246:42: ( block )?
            int alt32=2;
            int LA32_0 = input.LA(1);
            if ( ((LA32_0>=ID && LA32_0<=OBJECT)||(LA32_0>=NUM && LA32_0<=STR_LITERAL)||LA32_0==61||LA32_0==64||(LA32_0>=66 && LA32_0<=68)||(LA32_0>=79 && LA32_0<=80)||LA32_0==85||LA32_0==88||(LA32_0>=90 && LA32_0<=92)) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:246:42: block
                    {
                    pushFollow(FOLLOW_block_in_n_definition1614);
                    block108=block();
                    _fsp--;

                    list_block.add(block108.getTree());

                    }
                    break;

            }

            string_literal109=(Token)input.LT(1);
            match(input,60,FOLLOW_60_in_n_definition1617); 
            list_60.add(string_literal109);


            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 247:12: -> ^( FUNCTION_DEF_SIGNATURE ID ( n_paramList )? ( block )? ( SAFE )? )
            {
                // src/org/six11/flatcad/flatlang/FlatLang.g:247:15: ^( FUNCTION_DEF_SIGNATURE ID ( n_paramList )? ( block )? ( SAFE )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(FUNCTION_DEF_SIGNATURE, "FUNCTION_DEF_SIGNATURE"), root_1);

                adaptor.addChild(root_1, (Token)list_ID.get(i_0));
                // src/org/six11/flatcad/flatlang/FlatLang.g:247:43: ( n_paramList )?
                {
                int n_1 = list_n_paramList == null ? 0 : list_n_paramList.size();
                 


                if ( n_1 > 1 ) throw new RuntimeException("n_paramList list has > 1 elements");
                if ( n_1==1 ) {
                    int i_1 = 0;
                    adaptor.addChild(root_1, list_n_paramList.get(i_1));

                    }
                }
                // src/org/six11/flatcad/flatlang/FlatLang.g:247:56: ( block )?
                {
                int n_1 = list_block == null ? 0 : list_block.size();
                 


                if ( n_1 > 1 ) throw new RuntimeException("block list has > 1 elements");
                if ( n_1==1 ) {
                    int i_1 = 0;
                    adaptor.addChild(root_1, list_block.get(i_1));

                    }
                }
                // src/org/six11/flatcad/flatlang/FlatLang.g:247:63: ( SAFE )?
                {
                int n_1 = list_SAFE == null ? 0 : list_SAFE.size();
                 


                if ( n_1 > 1 ) throw new RuntimeException("SAFE list has > 1 elements");
                if ( n_1==1 ) {
                    int i_1 = 0;
                    adaptor.addChild(root_1, (Token)list_SAFE.get(i_1));

                    }
                }

                adaptor.addChild(root_0, root_1);
                }

            }



            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end n_definition

    public static class replaceStatement_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start replaceStatement
    // src/org/six11/flatcad/flatlang/FlatLang.g:250:1: replaceStatement : 'replace' '(' n_assignExpression ',' n_assignExpression ')' ( block )? 'done' -> ^( REPLACE ( n_assignExpression )+ ( block )? ) ;
    public replaceStatement_return replaceStatement() throws RecognitionException {
        replaceStatement_return retval = new replaceStatement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal110=null;
        Token char_literal111=null;
        Token char_literal113=null;
        Token char_literal115=null;
        Token string_literal117=null;
        n_assignExpression_return n_assignExpression112 = null;

        n_assignExpression_return n_assignExpression114 = null;

        block_return block116 = null;

        List list_n_assignExpression=new ArrayList();
        List list_block=new ArrayList();
        List list_65=new ArrayList();
        List list_88=new ArrayList();
        List list_64=new ArrayList();
        List list_60=new ArrayList();
        List list_89=new ArrayList();
        CommonTree string_literal110_tree=null;
        CommonTree char_literal111_tree=null;
        CommonTree char_literal113_tree=null;
        CommonTree char_literal115_tree=null;
        CommonTree string_literal117_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:251:9: ( 'replace' '(' n_assignExpression ',' n_assignExpression ')' ( block )? 'done' -> ^( REPLACE ( n_assignExpression )+ ( block )? ) )
            // src/org/six11/flatcad/flatlang/FlatLang.g:251:9: 'replace' '(' n_assignExpression ',' n_assignExpression ')' ( block )? 'done'
            {
            string_literal110=(Token)input.LT(1);
            match(input,88,FOLLOW_88_in_replaceStatement1662); 
            list_88.add(string_literal110);

            char_literal111=(Token)input.LT(1);
            match(input,64,FOLLOW_64_in_replaceStatement1664); 
            list_64.add(char_literal111);

            pushFollow(FOLLOW_n_assignExpression_in_replaceStatement1666);
            n_assignExpression112=n_assignExpression();
            _fsp--;

            list_n_assignExpression.add(n_assignExpression112.getTree());
            char_literal113=(Token)input.LT(1);
            match(input,89,FOLLOW_89_in_replaceStatement1668); 
            list_89.add(char_literal113);

            pushFollow(FOLLOW_n_assignExpression_in_replaceStatement1670);
            n_assignExpression114=n_assignExpression();
            _fsp--;

            list_n_assignExpression.add(n_assignExpression114.getTree());
            char_literal115=(Token)input.LT(1);
            match(input,65,FOLLOW_65_in_replaceStatement1672); 
            list_65.add(char_literal115);

            // src/org/six11/flatcad/flatlang/FlatLang.g:251:69: ( block )?
            int alt33=2;
            int LA33_0 = input.LA(1);
            if ( ((LA33_0>=ID && LA33_0<=OBJECT)||(LA33_0>=NUM && LA33_0<=STR_LITERAL)||LA33_0==61||LA33_0==64||(LA33_0>=66 && LA33_0<=68)||(LA33_0>=79 && LA33_0<=80)||LA33_0==85||LA33_0==88||(LA33_0>=90 && LA33_0<=92)) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:251:69: block
                    {
                    pushFollow(FOLLOW_block_in_replaceStatement1674);
                    block116=block();
                    _fsp--;

                    list_block.add(block116.getTree());

                    }
                    break;

            }

            string_literal117=(Token)input.LT(1);
            match(input,60,FOLLOW_60_in_replaceStatement1677); 
            list_60.add(string_literal117);


            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 252:12: -> ^( REPLACE ( n_assignExpression )+ ( block )? )
            {
                // src/org/six11/flatcad/flatlang/FlatLang.g:252:15: ^( REPLACE ( n_assignExpression )+ ( block )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(REPLACE, "REPLACE"), root_1);

                // src/org/six11/flatcad/flatlang/FlatLang.g:252:25: ( n_assignExpression )+
                {
                int n_1 = list_n_assignExpression == null ? 0 : list_n_assignExpression.size();
                 


                if ( n_1==0 ) throw new RuntimeException("Must have more than one element for (...)+ loops");
                for (int i_1=0; i_1<n_1; i_1++) {
                    adaptor.addChild(root_1, list_n_assignExpression.get(i_1));

                }
                }
                // src/org/six11/flatcad/flatlang/FlatLang.g:252:45: ( block )?
                {
                int n_1 = list_block == null ? 0 : list_block.size();
                 


                if ( n_1 > 1 ) throw new RuntimeException("block list has > 1 elements");
                if ( n_1==1 ) {
                    int i_1 = 0;
                    adaptor.addChild(root_1, list_block.get(i_1));

                    }
                }

                adaptor.addChild(root_0, root_1);
                }

            }



            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end replaceStatement

    public static class replaceAllStatement_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start replaceAllStatement
    // src/org/six11/flatcad/flatlang/FlatLang.g:255:1: replaceAllStatement : 'replaceAll' '(' n_assignExpression ',' n_assignExpression ')' ( block )? 'done' -> ^( REPLACE_ALL ( n_assignExpression )+ ( block )? ) ;
    public replaceAllStatement_return replaceAllStatement() throws RecognitionException {
        replaceAllStatement_return retval = new replaceAllStatement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal118=null;
        Token char_literal119=null;
        Token char_literal121=null;
        Token char_literal123=null;
        Token string_literal125=null;
        n_assignExpression_return n_assignExpression120 = null;

        n_assignExpression_return n_assignExpression122 = null;

        block_return block124 = null;

        List list_n_assignExpression=new ArrayList();
        List list_block=new ArrayList();
        List list_65=new ArrayList();
        List list_90=new ArrayList();
        List list_64=new ArrayList();
        List list_60=new ArrayList();
        List list_89=new ArrayList();
        CommonTree string_literal118_tree=null;
        CommonTree char_literal119_tree=null;
        CommonTree char_literal121_tree=null;
        CommonTree char_literal123_tree=null;
        CommonTree string_literal125_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:256:9: ( 'replaceAll' '(' n_assignExpression ',' n_assignExpression ')' ( block )? 'done' -> ^( REPLACE_ALL ( n_assignExpression )+ ( block )? ) )
            // src/org/six11/flatcad/flatlang/FlatLang.g:256:9: 'replaceAll' '(' n_assignExpression ',' n_assignExpression ')' ( block )? 'done'
            {
            string_literal118=(Token)input.LT(1);
            match(input,90,FOLLOW_90_in_replaceAllStatement1719); 
            list_90.add(string_literal118);

            char_literal119=(Token)input.LT(1);
            match(input,64,FOLLOW_64_in_replaceAllStatement1721); 
            list_64.add(char_literal119);

            pushFollow(FOLLOW_n_assignExpression_in_replaceAllStatement1723);
            n_assignExpression120=n_assignExpression();
            _fsp--;

            list_n_assignExpression.add(n_assignExpression120.getTree());
            char_literal121=(Token)input.LT(1);
            match(input,89,FOLLOW_89_in_replaceAllStatement1725); 
            list_89.add(char_literal121);

            pushFollow(FOLLOW_n_assignExpression_in_replaceAllStatement1727);
            n_assignExpression122=n_assignExpression();
            _fsp--;

            list_n_assignExpression.add(n_assignExpression122.getTree());
            char_literal123=(Token)input.LT(1);
            match(input,65,FOLLOW_65_in_replaceAllStatement1729); 
            list_65.add(char_literal123);

            // src/org/six11/flatcad/flatlang/FlatLang.g:256:72: ( block )?
            int alt34=2;
            int LA34_0 = input.LA(1);
            if ( ((LA34_0>=ID && LA34_0<=OBJECT)||(LA34_0>=NUM && LA34_0<=STR_LITERAL)||LA34_0==61||LA34_0==64||(LA34_0>=66 && LA34_0<=68)||(LA34_0>=79 && LA34_0<=80)||LA34_0==85||LA34_0==88||(LA34_0>=90 && LA34_0<=92)) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:256:72: block
                    {
                    pushFollow(FOLLOW_block_in_replaceAllStatement1731);
                    block124=block();
                    _fsp--;

                    list_block.add(block124.getTree());

                    }
                    break;

            }

            string_literal125=(Token)input.LT(1);
            match(input,60,FOLLOW_60_in_replaceAllStatement1734); 
            list_60.add(string_literal125);


            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 257:12: -> ^( REPLACE_ALL ( n_assignExpression )+ ( block )? )
            {
                // src/org/six11/flatcad/flatlang/FlatLang.g:257:15: ^( REPLACE_ALL ( n_assignExpression )+ ( block )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(REPLACE_ALL, "REPLACE_ALL"), root_1);

                // src/org/six11/flatcad/flatlang/FlatLang.g:257:29: ( n_assignExpression )+
                {
                int n_1 = list_n_assignExpression == null ? 0 : list_n_assignExpression.size();
                 


                if ( n_1==0 ) throw new RuntimeException("Must have more than one element for (...)+ loops");
                for (int i_1=0; i_1<n_1; i_1++) {
                    adaptor.addChild(root_1, list_n_assignExpression.get(i_1));

                }
                }
                // src/org/six11/flatcad/flatlang/FlatLang.g:257:49: ( block )?
                {
                int n_1 = list_block == null ? 0 : list_block.size();
                 


                if ( n_1 > 1 ) throw new RuntimeException("block list has > 1 elements");
                if ( n_1==1 ) {
                    int i_1 = 0;
                    adaptor.addChild(root_1, list_block.get(i_1));

                    }
                }

                adaptor.addChild(root_0, root_1);
                }

            }



            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end replaceAllStatement

    public static class fromStatement_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start fromStatement
    // src/org/six11/flatcad/flatlang/FlatLang.g:260:1: fromStatement : 'from' '(' n_assignExpression ( ',' n_assignExpression )* ')' ( block )? 'done' -> ^( FROM ( n_assignExpression )+ ( block )? ) ;
    public fromStatement_return fromStatement() throws RecognitionException {
        fromStatement_return retval = new fromStatement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal126=null;
        Token char_literal127=null;
        Token char_literal129=null;
        Token char_literal131=null;
        Token string_literal133=null;
        n_assignExpression_return n_assignExpression128 = null;

        n_assignExpression_return n_assignExpression130 = null;

        block_return block132 = null;

        List list_n_assignExpression=new ArrayList();
        List list_block=new ArrayList();
        List list_65=new ArrayList();
        List list_91=new ArrayList();
        List list_64=new ArrayList();
        List list_60=new ArrayList();
        List list_89=new ArrayList();
        CommonTree string_literal126_tree=null;
        CommonTree char_literal127_tree=null;
        CommonTree char_literal129_tree=null;
        CommonTree char_literal131_tree=null;
        CommonTree string_literal133_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:260:17: ( 'from' '(' n_assignExpression ( ',' n_assignExpression )* ')' ( block )? 'done' -> ^( FROM ( n_assignExpression )+ ( block )? ) )
            // src/org/six11/flatcad/flatlang/FlatLang.g:260:17: 'from' '(' n_assignExpression ( ',' n_assignExpression )* ')' ( block )? 'done'
            {
            string_literal126=(Token)input.LT(1);
            match(input,91,FOLLOW_91_in_fromStatement1770); 
            list_91.add(string_literal126);

            char_literal127=(Token)input.LT(1);
            match(input,64,FOLLOW_64_in_fromStatement1772); 
            list_64.add(char_literal127);

            pushFollow(FOLLOW_n_assignExpression_in_fromStatement1774);
            n_assignExpression128=n_assignExpression();
            _fsp--;

            list_n_assignExpression.add(n_assignExpression128.getTree());
            // src/org/six11/flatcad/flatlang/FlatLang.g:260:47: ( ',' n_assignExpression )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);
                if ( (LA35_0==89) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:260:48: ',' n_assignExpression
            	    {
            	    char_literal129=(Token)input.LT(1);
            	    match(input,89,FOLLOW_89_in_fromStatement1777); 
            	    list_89.add(char_literal129);

            	    pushFollow(FOLLOW_n_assignExpression_in_fromStatement1779);
            	    n_assignExpression130=n_assignExpression();
            	    _fsp--;

            	    list_n_assignExpression.add(n_assignExpression130.getTree());

            	    }
            	    break;

            	default :
            	    break loop35;
                }
            } while (true);

            char_literal131=(Token)input.LT(1);
            match(input,65,FOLLOW_65_in_fromStatement1783); 
            list_65.add(char_literal131);

            // src/org/six11/flatcad/flatlang/FlatLang.g:260:77: ( block )?
            int alt36=2;
            int LA36_0 = input.LA(1);
            if ( ((LA36_0>=ID && LA36_0<=OBJECT)||(LA36_0>=NUM && LA36_0<=STR_LITERAL)||LA36_0==61||LA36_0==64||(LA36_0>=66 && LA36_0<=68)||(LA36_0>=79 && LA36_0<=80)||LA36_0==85||LA36_0==88||(LA36_0>=90 && LA36_0<=92)) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:260:77: block
                    {
                    pushFollow(FOLLOW_block_in_fromStatement1785);
                    block132=block();
                    _fsp--;

                    list_block.add(block132.getTree());

                    }
                    break;

            }

            string_literal133=(Token)input.LT(1);
            match(input,60,FOLLOW_60_in_fromStatement1788); 
            list_60.add(string_literal133);


            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 261:3: -> ^( FROM ( n_assignExpression )+ ( block )? )
            {
                // src/org/six11/flatcad/flatlang/FlatLang.g:261:6: ^( FROM ( n_assignExpression )+ ( block )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(FROM, "FROM"), root_1);

                // src/org/six11/flatcad/flatlang/FlatLang.g:261:13: ( n_assignExpression )+
                {
                int n_1 = list_n_assignExpression == null ? 0 : list_n_assignExpression.size();
                 


                if ( n_1==0 ) throw new RuntimeException("Must have more than one element for (...)+ loops");
                for (int i_1=0; i_1<n_1; i_1++) {
                    adaptor.addChild(root_1, list_n_assignExpression.get(i_1));

                }
                }
                // src/org/six11/flatcad/flatlang/FlatLang.g:261:33: ( block )?
                {
                int n_1 = list_block == null ? 0 : list_block.size();
                 


                if ( n_1 > 1 ) throw new RuntimeException("block list has > 1 elements");
                if ( n_1==1 ) {
                    int i_1 = 0;
                    adaptor.addChild(root_1, list_block.get(i_1));

                    }
                }

                adaptor.addChild(root_0, root_1);
                }

            }



            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end fromStatement

    public static class shapeStatement_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start shapeStatement
    // src/org/six11/flatcad/flatlang/FlatLang.g:264:1: shapeStatement : 'shape' '(' literalString ')' ( block )? 'done' -> ^( SHAPE literalString ( block )? ) ;
    public shapeStatement_return shapeStatement() throws RecognitionException {
        shapeStatement_return retval = new shapeStatement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal134=null;
        Token char_literal135=null;
        Token char_literal137=null;
        Token string_literal139=null;
        literalString_return literalString136 = null;

        block_return block138 = null;

        List list_literalString=new ArrayList();
        List list_block=new ArrayList();
        List list_65=new ArrayList();
        List list_92=new ArrayList();
        List list_64=new ArrayList();
        List list_60=new ArrayList();
        CommonTree string_literal134_tree=null;
        CommonTree char_literal135_tree=null;
        CommonTree char_literal137_tree=null;
        CommonTree string_literal139_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:264:18: ( 'shape' '(' literalString ')' ( block )? 'done' -> ^( SHAPE literalString ( block )? ) )
            // src/org/six11/flatcad/flatlang/FlatLang.g:264:18: 'shape' '(' literalString ')' ( block )? 'done'
            {
            string_literal134=(Token)input.LT(1);
            match(input,92,FOLLOW_92_in_shapeStatement1812); 
            list_92.add(string_literal134);

            char_literal135=(Token)input.LT(1);
            match(input,64,FOLLOW_64_in_shapeStatement1814); 
            list_64.add(char_literal135);

            pushFollow(FOLLOW_literalString_in_shapeStatement1816);
            literalString136=literalString();
            _fsp--;

            list_literalString.add(literalString136.getTree());
            char_literal137=(Token)input.LT(1);
            match(input,65,FOLLOW_65_in_shapeStatement1818); 
            list_65.add(char_literal137);

            // src/org/six11/flatcad/flatlang/FlatLang.g:264:48: ( block )?
            int alt37=2;
            int LA37_0 = input.LA(1);
            if ( ((LA37_0>=ID && LA37_0<=OBJECT)||(LA37_0>=NUM && LA37_0<=STR_LITERAL)||LA37_0==61||LA37_0==64||(LA37_0>=66 && LA37_0<=68)||(LA37_0>=79 && LA37_0<=80)||LA37_0==85||LA37_0==88||(LA37_0>=90 && LA37_0<=92)) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:264:48: block
                    {
                    pushFollow(FOLLOW_block_in_shapeStatement1820);
                    block138=block();
                    _fsp--;

                    list_block.add(block138.getTree());

                    }
                    break;

            }

            string_literal139=(Token)input.LT(1);
            match(input,60,FOLLOW_60_in_shapeStatement1823); 
            list_60.add(string_literal139);


            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 265:3: -> ^( SHAPE literalString ( block )? )
            {
                // src/org/six11/flatcad/flatlang/FlatLang.g:265:6: ^( SHAPE literalString ( block )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(SHAPE, "SHAPE"), root_1);

                adaptor.addChild(root_1, list_literalString.get(i_0));
                // src/org/six11/flatcad/flatlang/FlatLang.g:265:28: ( block )?
                {
                int n_1 = list_block == null ? 0 : list_block.size();
                 


                if ( n_1 > 1 ) throw new RuntimeException("block list has > 1 elements");
                if ( n_1==1 ) {
                    int i_1 = 0;
                    adaptor.addChild(root_1, list_block.get(i_1));

                    }
                }

                adaptor.addChild(root_0, root_1);
                }

            }



            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end shapeStatement

    public static class paramList_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start paramList
    // src/org/six11/flatcad/flatlang/FlatLang.g:268:1: paramList : '(' ( paramID ( ',' paramID )* )? ')' -> ^( PARAM_LIST ( paramID )* ) ;
    public paramList_return paramList() throws RecognitionException {
        paramList_return retval = new paramList_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal140=null;
        Token char_literal142=null;
        Token char_literal144=null;
        paramID_return paramID141 = null;

        paramID_return paramID143 = null;

        List list_paramID=new ArrayList();
        List list_65=new ArrayList();
        List list_64=new ArrayList();
        List list_89=new ArrayList();
        CommonTree char_literal140_tree=null;
        CommonTree char_literal142_tree=null;
        CommonTree char_literal144_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:269:4: ( '(' ( paramID ( ',' paramID )* )? ')' -> ^( PARAM_LIST ( paramID )* ) )
            // src/org/six11/flatcad/flatlang/FlatLang.g:269:4: '(' ( paramID ( ',' paramID )* )? ')'
            {
            char_literal140=(Token)input.LT(1);
            match(input,64,FOLLOW_64_in_paramList1847); 
            list_64.add(char_literal140);

            // src/org/six11/flatcad/flatlang/FlatLang.g:269:8: ( paramID ( ',' paramID )* )?
            int alt39=2;
            int LA39_0 = input.LA(1);
            if ( (LA39_0==69) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:269:9: paramID ( ',' paramID )*
                    {
                    pushFollow(FOLLOW_paramID_in_paramList1850);
                    paramID141=paramID();
                    _fsp--;

                    list_paramID.add(paramID141.getTree());
                    // src/org/six11/flatcad/flatlang/FlatLang.g:269:17: ( ',' paramID )*
                    loop38:
                    do {
                        int alt38=2;
                        int LA38_0 = input.LA(1);
                        if ( (LA38_0==89) ) {
                            alt38=1;
                        }


                        switch (alt38) {
                    	case 1 :
                    	    // src/org/six11/flatcad/flatlang/FlatLang.g:269:18: ',' paramID
                    	    {
                    	    char_literal142=(Token)input.LT(1);
                    	    match(input,89,FOLLOW_89_in_paramList1853); 
                    	    list_89.add(char_literal142);

                    	    pushFollow(FOLLOW_paramID_in_paramList1855);
                    	    paramID143=paramID();
                    	    _fsp--;

                    	    list_paramID.add(paramID143.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop38;
                        }
                    } while (true);


                    }
                    break;

            }

            char_literal144=(Token)input.LT(1);
            match(input,65,FOLLOW_65_in_paramList1861); 
            list_65.add(char_literal144);


            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 270:3: -> ^( PARAM_LIST ( paramID )* )
            {
                // src/org/six11/flatcad/flatlang/FlatLang.g:270:6: ^( PARAM_LIST ( paramID )* )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(PARAM_LIST, "PARAM_LIST"), root_1);

                // src/org/six11/flatcad/flatlang/FlatLang.g:270:19: ( paramID )*
                {
                int n_1 = list_paramID == null ? 0 : list_paramID.size();
                 


                for (int i_1=0; i_1<n_1; i_1++) {
                    adaptor.addChild(root_1, list_paramID.get(i_1));

                }
                }

                adaptor.addChild(root_0, root_1);
                }

            }



            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end paramList

    public static class n_paramList_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start n_paramList
    // src/org/six11/flatcad/flatlang/FlatLang.g:273:1: n_paramList : ( ( ID )? ( ',' ID )* ) -> ^( PARAM_LIST ( ID )* ) ;
    public n_paramList_return n_paramList() throws RecognitionException {
        n_paramList_return retval = new n_paramList_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token ID145=null;
        Token char_literal146=null;
        Token ID147=null;
        List list_89=new ArrayList();
        List list_ID=new ArrayList();
        CommonTree ID145_tree=null;
        CommonTree char_literal146_tree=null;
        CommonTree ID147_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:274:4: ( ( ( ID )? ( ',' ID )* ) -> ^( PARAM_LIST ( ID )* ) )
            // src/org/six11/flatcad/flatlang/FlatLang.g:274:4: ( ( ID )? ( ',' ID )* )
            {
            // src/org/six11/flatcad/flatlang/FlatLang.g:274:4: ( ( ID )? ( ',' ID )* )
            // src/org/six11/flatcad/flatlang/FlatLang.g:274:5: ( ID )? ( ',' ID )*
            {
            // src/org/six11/flatcad/flatlang/FlatLang.g:274:5: ( ID )?
            int alt40=2;
            int LA40_0 = input.LA(1);
            if ( (LA40_0==ID) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:274:5: ID
                    {
                    ID145=(Token)input.LT(1);
                    match(input,ID,FOLLOW_ID_in_n_paramList1884); 
                    list_ID.add(ID145);


                    }
                    break;

            }

            // src/org/six11/flatcad/flatlang/FlatLang.g:274:9: ( ',' ID )*
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);
                if ( (LA41_0==89) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:274:10: ',' ID
            	    {
            	    char_literal146=(Token)input.LT(1);
            	    match(input,89,FOLLOW_89_in_n_paramList1888); 
            	    list_89.add(char_literal146);

            	    ID147=(Token)input.LT(1);
            	    match(input,ID,FOLLOW_ID_in_n_paramList1890); 
            	    list_ID.add(ID147);


            	    }
            	    break;

            	default :
            	    break loop41;
                }
            } while (true);


            }


            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 275:3: -> ^( PARAM_LIST ( ID )* )
            {
                // src/org/six11/flatcad/flatlang/FlatLang.g:275:6: ^( PARAM_LIST ( ID )* )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(PARAM_LIST, "PARAM_LIST"), root_1);

                // src/org/six11/flatcad/flatlang/FlatLang.g:275:19: ( ID )*
                {
                int n_1 = list_ID == null ? 0 : list_ID.size();
                 


                for (int i_1=0; i_1<n_1; i_1++) {
                    adaptor.addChild(root_1, (Token)list_ID.get(i_1));

                }
                }

                adaptor.addChild(root_0, root_1);
                }

            }



            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end n_paramList

    public static class paramID_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start paramID
    // src/org/six11/flatcad/flatlang/FlatLang.g:278:1: paramID : ':' ID -> ^( PARAM ID ) ;
    public paramID_return paramID() throws RecognitionException {
        paramID_return retval = new paramID_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal148=null;
        Token ID149=null;
        List list_69=new ArrayList();
        List list_ID=new ArrayList();
        CommonTree char_literal148_tree=null;
        CommonTree ID149_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:278:11: ( ':' ID -> ^( PARAM ID ) )
            // src/org/six11/flatcad/flatlang/FlatLang.g:278:11: ':' ID
            {
            char_literal148=(Token)input.LT(1);
            match(input,69,FOLLOW_69_in_paramID1914); 
            list_69.add(char_literal148);

            ID149=(Token)input.LT(1);
            match(input,ID,FOLLOW_ID_in_paramID1916); 
            list_ID.add(ID149);


            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 278:29: -> ^( PARAM ID )
            {
                // src/org/six11/flatcad/flatlang/FlatLang.g:278:32: ^( PARAM ID )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(PARAM, "PARAM"), root_1);

                adaptor.addChild(root_1, (Token)list_ID.get(i_0));

                adaptor.addChild(root_0, root_1);
                }

            }



            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end paramID

    public static class n_expressionList_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start n_expressionList
    // src/org/six11/flatcad/flatlang/FlatLang.g:281:1: n_expressionList : ( n_assignExpression )? ( ',' n_assignExpression )* -> ( n_assignExpression )* ;
    public n_expressionList_return n_expressionList() throws RecognitionException {
        n_expressionList_return retval = new n_expressionList_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal151=null;
        n_assignExpression_return n_assignExpression150 = null;

        n_assignExpression_return n_assignExpression152 = null;

        List list_n_assignExpression=new ArrayList();
        List list_89=new ArrayList();
        CommonTree char_literal151_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:282:4: ( ( n_assignExpression )? ( ',' n_assignExpression )* -> ( n_assignExpression )* )
            // src/org/six11/flatcad/flatlang/FlatLang.g:282:4: ( n_assignExpression )? ( ',' n_assignExpression )*
            {
            // src/org/six11/flatcad/flatlang/FlatLang.g:282:4: ( n_assignExpression )?
            int alt42=2;
            int LA42_0 = input.LA(1);
            if ( ((LA42_0>=ID && LA42_0<=OBJECT)||(LA42_0>=NUM && LA42_0<=STR_LITERAL)||LA42_0==64||(LA42_0>=79 && LA42_0<=80)||LA42_0==85) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:282:4: n_assignExpression
                    {
                    pushFollow(FOLLOW_n_assignExpression_in_n_expressionList1947);
                    n_assignExpression150=n_assignExpression();
                    _fsp--;

                    list_n_assignExpression.add(n_assignExpression150.getTree());

                    }
                    break;

            }

            // src/org/six11/flatcad/flatlang/FlatLang.g:282:24: ( ',' n_assignExpression )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);
                if ( (LA43_0==89) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // src/org/six11/flatcad/flatlang/FlatLang.g:282:25: ',' n_assignExpression
            	    {
            	    char_literal151=(Token)input.LT(1);
            	    match(input,89,FOLLOW_89_in_n_expressionList1951); 
            	    list_89.add(char_literal151);

            	    pushFollow(FOLLOW_n_assignExpression_in_n_expressionList1953);
            	    n_assignExpression152=n_assignExpression();
            	    _fsp--;

            	    list_n_assignExpression.add(n_assignExpression152.getTree());

            	    }
            	    break;

            	default :
            	    break loop43;
                }
            } while (true);


            // AST REWRITE
            int i_0 = 0;
            retval.tree = root_0;
            root_0 = (CommonTree)adaptor.nil();
            // 282:50: -> ( n_assignExpression )*
            {
                // src/org/six11/flatcad/flatlang/FlatLang.g:282:53: ( n_assignExpression )*
                {
                int n_1 = list_n_assignExpression == null ? 0 : list_n_assignExpression.size();
                 


                for (int i_1=0; i_1<n_1; i_1++) {
                    adaptor.addChild(root_0, list_n_assignExpression.get(i_1));

                }
                }

            }



            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end n_expressionList

    public static class literal_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start literal
    // src/org/six11/flatcad/flatlang/FlatLang.g:285:1: literal : ( literalNum | literalString );
    public literal_return literal() throws RecognitionException {
        literal_return retval = new literal_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        literalNum_return literalNum153 = null;

        literalString_return literalString154 = null;



        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:286:7: ( literalNum | literalString )
            int alt44=2;
            int LA44_0 = input.LA(1);
            if ( (LA44_0==NUM) ) {
                alt44=1;
            }
            else if ( (LA44_0==STR_LITERAL) ) {
                alt44=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("285:1: literal : ( literalNum | literalString );", 44, 0, input);

                throw nvae;
            }
            switch (alt44) {
                case 1 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:286:7: literalNum
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_literalNum_in_literal1975);
                    literalNum153=literalNum();
                    _fsp--;

                    adaptor.addChild(root_0, literalNum153.getTree());

                    }
                    break;
                case 2 :
                    // src/org/six11/flatcad/flatlang/FlatLang.g:287:9: literalString
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_literalString_in_literal1985);
                    literalString154=literalString();
                    _fsp--;

                    adaptor.addChild(root_0, literalString154.getTree());

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end literal

    public static class literalNum_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start literalNum
    // src/org/six11/flatcad/flatlang/FlatLang.g:290:1: literalNum : NUM ;
    public literalNum_return literalNum() throws RecognitionException {
        literalNum_return retval = new literalNum_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token NUM155=null;

        CommonTree NUM155_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:291:4: ( NUM )
            // src/org/six11/flatcad/flatlang/FlatLang.g:291:4: NUM
            {
            root_0 = (CommonTree)adaptor.nil();

            NUM155=(Token)input.LT(1);
            match(input,NUM,FOLLOW_NUM_in_literalNum1996); 
            NUM155_tree = (CommonTree)adaptor.create(NUM155);
            adaptor.addChild(root_0, NUM155_tree);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end literalNum

    public static class literalString_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start literalString
    // src/org/six11/flatcad/flatlang/FlatLang.g:294:1: literalString : STR_LITERAL ;
    public literalString_return literalString() throws RecognitionException {
        literalString_return retval = new literalString_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token STR_LITERAL156=null;

        CommonTree STR_LITERAL156_tree=null;

        try {
            // src/org/six11/flatcad/flatlang/FlatLang.g:295:4: ( STR_LITERAL )
            // src/org/six11/flatcad/flatlang/FlatLang.g:295:4: STR_LITERAL
            {
            root_0 = (CommonTree)adaptor.nil();

            STR_LITERAL156=(Token)input.LT(1);
            match(input,STR_LITERAL,FOLLOW_STR_LITERAL_in_literalString2007); 
            STR_LITERAL156_tree = (CommonTree)adaptor.create(STR_LITERAL156);
            adaptor.addChild(root_0, STR_LITERAL156_tree);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        return retval;
    }
    // $ANTLR end literalString


 

    public static final BitSet FOLLOW_statement_in_prog262 = new BitSet(new long[]{0x200FF00000000002L,0x000000001DA1801DL});
    public static final BitSet FOLLOW_nonDefStatement_in_block295 = new BitSet(new long[]{0x200DF00000000002L,0x000000001D21801DL});
    public static final BitSet FOLLOW_n_definition_in_statement324 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nonDefStatement_in_statement329 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_replaceStatement_in_nonDefStatement345 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_replaceAllStatement_in_nonDefStatement355 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_shapeStatement_in_nonDefStatement365 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_fromStatement_in_nonDefStatement375 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_whileStatement_in_nonDefStatement385 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_repeatStatement_in_nonDefStatement395 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifStatement_in_nonDefStatement405 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_forEachStatement_in_nonDefStatement413 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementExpression_in_nonDefStatement423 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifStatementStart_in_ifStatement447 = new BitSet(new long[]{0xD000000000000000L});
    public static final BitSet FOLLOW_altStatement_in_ifStatement452 = new BitSet(new long[]{0xD000000000000000L});
    public static final BitSet FOLLOW_elseStatement_in_ifStatement457 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_ifStatement462 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_61_in_ifStatementStart489 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_parExpression_in_ifStatementStart491 = new BitSet(new long[]{0x200DF00000000002L,0x000000001D21801DL});
    public static final BitSet FOLLOW_block_in_ifStatementStart493 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_62_in_elseStatement517 = new BitSet(new long[]{0x200DF00000000002L,0x000000001D21801DL});
    public static final BitSet FOLLOW_block_in_elseStatement519 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_altStatement540 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_parExpression_in_altStatement542 = new BitSet(new long[]{0x200DF00000000002L,0x000000001D21801DL});
    public static final BitSet FOLLOW_block_in_altStatement544 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_64_in_parExpression567 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000218001L});
    public static final BitSet FOLLOW_n_assignExpression_in_parExpression569 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_parExpression571 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_66_in_repeatStatement587 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_repeatStatement589 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000218001L});
    public static final BitSet FOLLOW_n_assignExpression_in_repeatStatement591 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_repeatStatement593 = new BitSet(new long[]{0x300DF00000000000L,0x000000001D21801DL});
    public static final BitSet FOLLOW_block_in_repeatStatement595 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_repeatStatement598 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_67_in_whileStatement622 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_whileStatement624 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000218001L});
    public static final BitSet FOLLOW_n_assignExpression_in_whileStatement626 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_whileStatement628 = new BitSet(new long[]{0x300DF00000000000L,0x000000001D21801DL});
    public static final BitSet FOLLOW_block_in_whileStatement630 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_whileStatement633 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_68_in_forEachStatement657 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_forEachStatement659 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_ID_in_forEachStatement663 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_forEachStatement665 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000218001L});
    public static final BitSet FOLLOW_n_assignExpression_in_forEachStatement669 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_forEachStatement671 = new BitSet(new long[]{0x300DF00000000000L,0x000000001D21801DL});
    public static final BitSet FOLLOW_64_in_forEachStatement675 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000218001L});
    public static final BitSet FOLLOW_n_assignExpression_in_forEachStatement679 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_forEachStatement681 = new BitSet(new long[]{0x300DF00000000000L,0x000000001D21801DL});
    public static final BitSet FOLLOW_block_in_forEachStatement686 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_forEachStatement689 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_n_assignExpression_in_statementExpression721 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_n_conditionalOrExpr_in_n_assignExpression741 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_n_assignExpression753 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000218001L});
    public static final BitSet FOLLOW_n_conditionalOrExpr_in_n_assignExpression757 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_n_conditionalAndExpr_in_n_conditionalOrExpr787 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_n_conditionalOrExpr801 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000218001L});
    public static final BitSet FOLLOW_n_conditionalAndExpr_in_n_conditionalOrExpr805 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_n_equalityExpression_in_n_conditionalAndExpr835 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_n_conditionalAndExpr849 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000218001L});
    public static final BitSet FOLLOW_n_equalityExpression_in_n_conditionalAndExpr853 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000100L});
    public static final BitSet FOLLOW_n_gtltExpression_in_n_equalityExpression883 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000600L});
    public static final BitSet FOLLOW_73_in_n_equalityExpression896 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000218001L});
    public static final BitSet FOLLOW_n_gtltExpression_in_n_equalityExpression900 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000600L});
    public static final BitSet FOLLOW_74_in_n_equalityExpression922 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000218001L});
    public static final BitSet FOLLOW_n_gtltExpression_in_n_equalityExpression926 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000600L});
    public static final BitSet FOLLOW_n_additionExpr_in_n_gtltExpression956 = new BitSet(new long[]{0x0000000000000002L,0x0000000000007800L});
    public static final BitSet FOLLOW_75_in_n_gtltExpression969 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000218001L});
    public static final BitSet FOLLOW_n_additionExpr_in_n_gtltExpression974 = new BitSet(new long[]{0x0000000000000002L,0x0000000000007800L});
    public static final BitSet FOLLOW_76_in_n_gtltExpression995 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000218001L});
    public static final BitSet FOLLOW_n_additionExpr_in_n_gtltExpression999 = new BitSet(new long[]{0x0000000000000002L,0x0000000000007800L});
    public static final BitSet FOLLOW_77_in_n_gtltExpression1020 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000218001L});
    public static final BitSet FOLLOW_n_additionExpr_in_n_gtltExpression1025 = new BitSet(new long[]{0x0000000000000002L,0x0000000000007800L});
    public static final BitSet FOLLOW_78_in_n_gtltExpression1046 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000218001L});
    public static final BitSet FOLLOW_n_additionExpr_in_n_gtltExpression1050 = new BitSet(new long[]{0x0000000000000002L,0x0000000000007800L});
    public static final BitSet FOLLOW_n_multExpr_in_n_additionExpr1080 = new BitSet(new long[]{0x0000000000000002L,0x0000000000018000L});
    public static final BitSet FOLLOW_79_in_n_additionExpr1092 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000218001L});
    public static final BitSet FOLLOW_n_multExpr_in_n_additionExpr1096 = new BitSet(new long[]{0x0000000000000002L,0x0000000000018000L});
    public static final BitSet FOLLOW_80_in_n_additionExpr1120 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000218001L});
    public static final BitSet FOLLOW_n_multExpr_in_n_additionExpr1124 = new BitSet(new long[]{0x0000000000000002L,0x0000000000018000L});
    public static final BitSet FOLLOW_n_unaryExpr_in_n_multExpr1153 = new BitSet(new long[]{0x0000000000000002L,0x00000000000E0000L});
    public static final BitSet FOLLOW_81_in_n_multExpr1165 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000218001L});
    public static final BitSet FOLLOW_n_unaryExpr_in_n_multExpr1169 = new BitSet(new long[]{0x0000000000000002L,0x00000000000E0000L});
    public static final BitSet FOLLOW_82_in_n_multExpr1191 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000218001L});
    public static final BitSet FOLLOW_n_unaryExpr_in_n_multExpr1195 = new BitSet(new long[]{0x0000000000000002L,0x00000000000E0000L});
    public static final BitSet FOLLOW_83_in_n_multExpr1218 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000218001L});
    public static final BitSet FOLLOW_n_unaryExpr_in_n_multExpr1222 = new BitSet(new long[]{0x0000000000000002L,0x00000000000E0000L});
    public static final BitSet FOLLOW_80_in_n_unaryExpr1251 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000200001L});
    public static final BitSet FOLLOW_n_primary_in_n_unaryExpr1253 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_n_unaryExpr1267 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000200001L});
    public static final BitSet FOLLOW_n_primary_in_n_unaryExpr1270 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_n_primaryNoDot_in_n_primary1293 = new BitSet(new long[]{0x0000000000000002L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_n_primary1307 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000200001L});
    public static final BitSet FOLLOW_n_primaryNoDot_in_n_primary1311 = new BitSet(new long[]{0x0000000000000002L,0x0000000000100000L});
    public static final BitSet FOLLOW_n_literal_in_n_primaryNoDot1348 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_n_indexExpr_in_n_primaryNoDot1353 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parExpression_in_n_primaryNoDot1358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_listExpression_in_n_primaryNoDot1363 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_85_in_listExpression1374 = new BitSet(new long[]{0x000DF00000000000L,0x0000000002618001L});
    public static final BitSet FOLLOW_n_expressionList_in_listExpression1376 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_listExpression1378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_n_variable_in_n_indexExpr1399 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_n_indexExpr1414 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000218001L});
    public static final BitSet FOLLOW_n_assignExpression_in_n_indexExpr1416 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_n_indexExpr1418 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_literalNum_in_n_literal1449 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literalString_in_n_literal1458 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TRUE_in_n_literal1467 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FALSE_in_n_literal1476 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INFINITY_in_n_literal1485 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBJECT_in_n_literal1494 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_n_variable1506 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_n_variable1520 = new BitSet(new long[]{0x000DF00000000000L,0x0000000002218003L});
    public static final BitSet FOLLOW_n_expressionList_in_n_variable1524 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_n_variable1526 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_87_in_definition1554 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_ID_in_definition1556 = new BitSet(new long[]{0x300DF00000000000L,0x000000001D21801DL});
    public static final BitSet FOLLOW_paramList_in_definition1558 = new BitSet(new long[]{0x300DF00000000000L,0x000000001D21801DL});
    public static final BitSet FOLLOW_block_in_definition1561 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_definition1564 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SAFE_in_n_definition1601 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_n_definition1604 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_ID_in_n_definition1606 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_n_definition1608 = new BitSet(new long[]{0x0000100000000000L,0x0000000002000002L});
    public static final BitSet FOLLOW_n_paramList_in_n_definition1610 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_n_definition1612 = new BitSet(new long[]{0x300DF00000000000L,0x000000001D21801DL});
    public static final BitSet FOLLOW_block_in_n_definition1614 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_n_definition1617 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_replaceStatement1662 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_replaceStatement1664 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000218001L});
    public static final BitSet FOLLOW_n_assignExpression_in_replaceStatement1666 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_replaceStatement1668 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000218001L});
    public static final BitSet FOLLOW_n_assignExpression_in_replaceStatement1670 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_replaceStatement1672 = new BitSet(new long[]{0x300DF00000000000L,0x000000001D21801DL});
    public static final BitSet FOLLOW_block_in_replaceStatement1674 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_replaceStatement1677 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_90_in_replaceAllStatement1719 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_replaceAllStatement1721 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000218001L});
    public static final BitSet FOLLOW_n_assignExpression_in_replaceAllStatement1723 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_replaceAllStatement1725 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000218001L});
    public static final BitSet FOLLOW_n_assignExpression_in_replaceAllStatement1727 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_replaceAllStatement1729 = new BitSet(new long[]{0x300DF00000000000L,0x000000001D21801DL});
    public static final BitSet FOLLOW_block_in_replaceAllStatement1731 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_replaceAllStatement1734 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_91_in_fromStatement1770 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_fromStatement1772 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000218001L});
    public static final BitSet FOLLOW_n_assignExpression_in_fromStatement1774 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000002L});
    public static final BitSet FOLLOW_89_in_fromStatement1777 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000218001L});
    public static final BitSet FOLLOW_n_assignExpression_in_fromStatement1779 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000002L});
    public static final BitSet FOLLOW_65_in_fromStatement1783 = new BitSet(new long[]{0x300DF00000000000L,0x000000001D21801DL});
    public static final BitSet FOLLOW_block_in_fromStatement1785 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_fromStatement1788 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_92_in_shapeStatement1812 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_shapeStatement1814 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_literalString_in_shapeStatement1816 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_shapeStatement1818 = new BitSet(new long[]{0x300DF00000000000L,0x000000001D21801DL});
    public static final BitSet FOLLOW_block_in_shapeStatement1820 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_shapeStatement1823 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_64_in_paramList1847 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000022L});
    public static final BitSet FOLLOW_paramID_in_paramList1850 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000002L});
    public static final BitSet FOLLOW_89_in_paramList1853 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_paramID_in_paramList1855 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000002L});
    public static final BitSet FOLLOW_65_in_paramList1861 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_n_paramList1884 = new BitSet(new long[]{0x0000000000000002L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_n_paramList1888 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_ID_in_n_paramList1890 = new BitSet(new long[]{0x0000000000000002L,0x0000000002000000L});
    public static final BitSet FOLLOW_69_in_paramID1914 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_ID_in_paramID1916 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_n_assignExpression_in_n_expressionList1947 = new BitSet(new long[]{0x0000000000000002L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_n_expressionList1951 = new BitSet(new long[]{0x000DF00000000000L,0x0000000000218001L});
    public static final BitSet FOLLOW_n_assignExpression_in_n_expressionList1953 = new BitSet(new long[]{0x0000000000000002L,0x0000000002000000L});
    public static final BitSet FOLLOW_literalNum_in_literal1975 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literalString_in_literal1985 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_in_literalNum1996 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STR_LITERAL_in_literalString2007 = new BitSet(new long[]{0x0000000000000002L});

}