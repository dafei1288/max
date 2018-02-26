grammar MaxExpr;


/** The start rule; beginparsing here. */
prog:stat+;

stat: logicExpr    # printLogicExpr
| expr             # printExpr
| IT '=' logicExpr # assignLogic
| ID '=' logicExpr # assignItLogic
| ID '=' expr      # assign
| IT               # itSelf
| SEM              # blank
;

expr:expr op=('*'|'/') expr     # MulDiv
| expr op=('+'|'-') expr        # AddSub
| IT                            # it
| INT                           # int
| ID                            # id
| '(' expr ')'                  # parens
;



logicExpr: logicExpr op=('||'|'&&'|'!') logicExpr       # topLevelLogic
| logicExpr op=('>'|'<'|'=='|'!='|'>='|'<=') logicExpr  # logicexpress
| IT op=('>'|'<'|'=='|'!='|'>='|'<=') expr              # itExpr
| expr op=('>'|'<'|'=='|'!='|'>='|'<=') expr            # logicnumber
| IT                                                    # assIt
| TRUE                                                  # true
| FALSE                                                 # false
| '(' logicExpr ')'                                     # parentsLogic
;


MUL : '*' ;
DIV : '/' ;
ADD : '+' ;
SUB : '-' ;

GT : '>';
GTE : '>=';
LT : '<';
LTE : '<=';
EQ : '==';
NEQ : '!=';

OR : '||';
AND : '&&';
NOT : '!';
IT : 'it';
TRUE : 'true';
FALSE : 'flase';
ID : [a-zA-Z]+ ;
INT : [0-9]+ ;
NEWLINE:'\r'? '\n' ;
SEM : ';';
WS : [ \t]+ -> skip ;
