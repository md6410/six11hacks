grammar FlatLang;

options {
  output = AST;
  ASTLabelType = CommonTree;
  k=2;
}

tokens {
  PROG;
  FUNCTION_DEF_SIGNATURE;
  FUNCTION_CALL;
  STATEMENT;
  PARAM_LIST;
  PARAM;
  EXPR;
  BLOCK;
  REPLACE;
  REPLACE_ALL;
  SHAPE;
  FROM;
  N_ADD_EXPR;
  N_MINUS_EXPR;
  N_MULT_EXPR;
  N_DIV_EXPR;
  N_MODULO_EXPR;
  N_ASSIGN_EXPR;
  N_UNARY_EXPR;
  N_UNARY_NEG_EXPR;
  N_UNARY_POS_EXPR;
  N_LT_EXPR;
  N_LTEQ_EXPR;
  N_GTEQ_EXPR;
  N_GT_EXPR;
  N_EQ_EXPR;
  N_NEQ_EXPR;
  N_AND_EXPR;
  N_OR_EXPR;
  WHILE_STATEMENT;
  REPEAT_STATEMENT;
  IF_STATEMENT;
  IF_ALT;
  ELSE_ALT;
  LITERAL;
  INDEX_EXPR;
  LIST_EXPR;
  MEMBER_EXPR;
  MEMBER_EXPR_DOT;
  FOR_EACH_STATEMENT;
  }

@header {
package org.six11.flatcad.flatlang;
}

@lexer::header {
package org.six11.flatcad.flatlang;
}

prog	:	statement*        -> ^(PROG statement*)
	;

block
    :   nonDefStatement+      -> ^(BLOCK nonDefStatement+)
    ;

statement
	:	n_definition
	|	nonDefStatement
	;

nonDefStatement
    :   replaceStatement
    |   replaceAllStatement
    |   shapeStatement
    |   fromStatement
    |   whileStatement
    |   repeatStatement
    |   ifStatement
    |	forEachStatement
    |   statementExpression   -> ^(STATEMENT statementExpression)
    ;

ifStatement
	:	ifStatementStart 
		altStatement*
		elseStatement?
		'done'
		-> ^(IF_STATEMENT ifStatementStart altStatement* elseStatement?)
	;

ifStatementStart
	:	'if' parExpression block? -> ^(IF_ALT parExpression block?)
	;


elseStatement
	:	'else' block? -> ^(ELSE_ALT block?)
	;

altStatement
	:	'alt' parExpression block? -> ^(IF_ALT parExpression block?)
	;

parExpression
	:	'(' n_assignExpression ')' -> n_assignExpression
	;
	
repeatStatement
	: 'repeat' '(' n_assignExpression ')' block? 'done'
		-> ^(REPEAT_STATEMENT n_assignExpression block?)
	;

whileStatement
	: 'while' '(' n_assignExpression ')' block? 'done'
		-> ^(WHILE_STATEMENT n_assignExpression block?)
	;

forEachStatement
	: 'for each' '(' v=ID ':' l=n_assignExpression ')' ( '(' c=n_assignExpression ')' )? block? 'done'
		-> ^(FOR_EACH_STATEMENT $v $l $c? block?)
	;

statementExpression
	:	n_assignExpression -> ^(EXPR n_assignExpression)
	;

n_assignExpression
	:	(n_conditionalOrExpr -> n_conditionalOrExpr) (
		 '=' r=n_conditionalOrExpr -> ^(N_ASSIGN_EXPR $n_assignExpression $r)
		 )?
	;

n_conditionalOrExpr
	:	(n_conditionalAndExpr -> n_conditionalAndExpr) (
		 ( 'or' r=n_conditionalAndExpr -> ^(N_OR_EXPR $n_conditionalOrExpr $r))
		)*
	;

n_conditionalAndExpr
	:	(n_equalityExpression -> n_equalityExpression) ( 
		 ('and' r=n_equalityExpression -> ^(N_AND_EXPR $n_conditionalAndExpr $r))
		)*
	;

n_equalityExpression
	:	(n_gtltExpression -> n_gtltExpression) (
		 ('==' r=n_gtltExpression -> ^(N_EQ_EXPR $n_equalityExpression $r)) | 
		 ('!=' r=n_gtltExpression -> ^(N_NEQ_EXPR $n_equalityExpression $r))
		)*
	;

n_gtltExpression
	:	(n_additionExpr -> n_additionExpr) (
		 ('<'  r=n_additionExpr -> ^(N_LT_EXPR $n_gtltExpression $r)) |
		 ('<=' r=n_additionExpr -> ^(N_LTEQ_EXPR $n_gtltExpression $r)) |
		 ('>'  r=n_additionExpr -> ^(N_GT_EXPR $n_gtltExpression $r)) |
		 ('>=' r=n_additionExpr -> ^(N_GTEQ_EXPR $n_gtltExpression $r))
		)*
	;

n_additionExpr
	:	(n_multExpr -> n_multExpr) (
	 ('+' r=n_multExpr -> ^(N_ADD_EXPR   $n_additionExpr $r)) |	 
	 ('-' r=n_multExpr -> ^(N_MINUS_EXPR $n_additionExpr $r))
	)*
	;

n_multExpr
	:	(n_unaryExpr -> n_unaryExpr) (
	 ('*' r=n_unaryExpr -> ^(N_MULT_EXPR   $n_multExpr $r)) |
	 ('/' r=n_unaryExpr -> ^(N_DIV_EXPR    $n_multExpr $r)) |
	 ('%' r=n_unaryExpr -> ^(N_MODULO_EXPR $n_multExpr $r))
	)*
	;

n_unaryExpr
	:
	'-' n_primary  -> ^(N_UNARY_NEG_EXPR n_primary) |
	'+'? n_primary -> ^(N_UNARY_POS_EXPR n_primary)
	;

/*
n_primary
	:	(n_primaryNoDot -> ^(n_primaryNoDot)) (
		 (r=dotExpression) -> ^(MEMBER_EXPR $n_primary $r)
		)?
	;

dotExpression
	:	('.' n_primaryNoDot)+ -> ^(MEMBER_EXPR_DOT n_primaryNoDot+)
	;
*/

n_primary
	:	(n_primaryNoDot -> n_primaryNoDot) (
		  ('.' r=n_primaryNoDot) -> ^(MEMBER_EXPR $n_primary $r)
		)*
		 
	;

//dotExpression
//	:	('.' n_primaryNoDot)+ -> ^(MEMBER_EXPR_DOT n_primaryNoDot+)
//	;

n_primaryNoDot
	:	n_literal
	|	n_indexExpr
	|	parExpression
	|	listExpression
	;

listExpression
	:	'[' n_expressionList ']' -> ^(LIST_EXPR n_expressionList)
	;


n_indexExpr
	:	(n_variable -> ^(n_variable)) (
		 ('[' n_assignExpression ']')+ -> ^(INDEX_EXPR $n_indexExpr n_assignExpression+)
		)?
	;


n_literal
	:	literalNum
    	|	literalString
    	|	TRUE
    	|	FALSE
    	|	INFINITY
    	|	OBJECT
	;

n_variable
	:	(ID -> ^(ID)) (
		 '(' p=n_expressionList ')' -> ^(FUNCTION_CALL $n_variable $p)
		)?
	;

definition
	:	'define' ID paramList? block? 'done' 
           -> ^(FUNCTION_DEF_SIGNATURE ID paramList? block?)
	;

n_definition
	:	SAFE? 'define' ID '(' n_paramList ')' block? 'done' 
           -> ^(FUNCTION_DEF_SIGNATURE ID n_paramList? block? SAFE?)
	;

replaceStatement
    :   'replace' '(' n_assignExpression ',' n_assignExpression ')' block? 'done'
           -> ^(REPLACE n_assignExpression+ block?)
    ;

replaceAllStatement
    :   'replaceAll' '(' n_assignExpression ',' n_assignExpression ')' block? 'done'
           -> ^(REPLACE_ALL n_assignExpression+ block?)
    ;

fromStatement	:	'from' '(' n_assignExpression (',' n_assignExpression)* ')' block? 'done'
		-> ^(FROM n_assignExpression+ block?)
	;

shapeStatement	:	'shape' '(' literalString ')' block? 'done'
		-> ^(SHAPE literalString block?)
	;

paramList
	:	'(' (paramID (',' paramID)*)? ')'
		-> ^(PARAM_LIST paramID*)
	;

n_paramList
	:	(ID? (',' ID)*)
		-> ^(PARAM_LIST ID*)
	;

paramID	:	':' ID            -> ^(PARAM ID) 
	;

n_expressionList
	:	n_assignExpression? (',' n_assignExpression)* -> n_assignExpression*
	;

literal	
    :	literalNum
    |   literalString
	;

literalNum
	:	NUM
	;

literalString
	:	STR_LITERAL
	;

SAFE	:	'safe'
	;
	
OBJECT	:	'__object__'
	;

INFINITY:	'infinity'
	;
	
TRUE	:	'true'
	;
	
FALSE	:	'false'
	;

NUM	:	INT+
	|	INT* '.' INT+
	;

ID	:	(LETTER | '_') (LETTER | '_' | INT)*
	;

fragment
LETTER	:	('a'..'z'|'A'..'Z')
	;

fragment
INT	:	('0'..'9')+
    	;

STR_LITERAL
     :   '"' ( ESC_SEQ | ~('\\'|'"') )* '"'
     ;

fragment
ESC_SEQ
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UNI_ESC
    |   OCT_ESC
    ;

fragment
OCT_ESC	:	'\\' ('0'..'3') ('0'..'7') ('0'..'7')
	|	'\\' ('0'..'7') ('0'..'7')
	|	'\\' ('0'..'7')
	;

fragment
UNI_ESC	:	'\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
    	;

fragment
HEX_DIGIT
	:	('0'..'9'|'a'..'f'|'A'..'F') 
	;

LINE_COMMENT
	:	(';' .* '\n') { $channel = HIDDEN; }
	;
	
WS	:	(' ' | '\t' | '\r' | '\n' ) { $channel = HIDDEN; }
	;
