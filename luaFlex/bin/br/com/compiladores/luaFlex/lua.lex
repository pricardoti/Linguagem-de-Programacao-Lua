package br.com.compiladores.luaFlex;
import java_cup.runtime.*;
%%
%{
/*-*
* funcoes e variaveis
*/
private void imprimir(String descricao, String lexema) {
System.out.println(lexema + " - " + descricao);
}
%}
/*-*
* informacoes sobre a clase gerada
*/
%public
%class AnalisadorLexico
%type void
/*-*
* definicaoes de regulares
*/
BRANCO = [\n| |\t]
OPERADORES_LOGICOS = ("and" | "or" | "not")
IDENTIFICADOR = [_|a-z|A-Z][a-z|A-Z|0-9|_]*
FUNCOES = ("write" | "return" | "print" | "function")
INTEIRO = 0|[1-9][0-9]*
PONTOFLUTUANTE = [0-9][0-9]*"."[0-9]+
OPERADORES_MATEMATICOS = ("+" | "-" | "*" | "/")
ESTRUTURAS_REPETICAO = ("while" | "do" | "repeat" | "until")
%%
"function" { imprimir("funcao", yytext()); }
"if" { imprimir("Intrucao if", yytext()); }
"then" { imprimir("Intrucao then", yytext()); }
{BRANCO} { imprimir("Branco", yytext()); }
{OPERADORES_LOGICOS} { imprimir("Operadores logicos", yytext()); }
{IDENTIFICADOR} { imprimir("identificador", yytext()); }
{FUNCOES} { imprimir("Funcoes", yytext()); }
{INTEIRO} { imprimir("Numero", yytext()); }
{PONTOFLUTUANTE} { imprimir("Ponto plututante", yytext()); }
{OPERADORES_MATEMATICOS} { imprimir("Operadores matematatico", yytext()); }
{ESTRUTURAS_REPETICAO} { imprimir("Estrutura de repeticao", yytext()); }
"==" { imprimir("Operador igualdade", yytext()); }
. { throw new RuntimeException("Caractere invalido \""+yytext() +
"\" na linha "+yyline+", coluna "+yycolumn); }
