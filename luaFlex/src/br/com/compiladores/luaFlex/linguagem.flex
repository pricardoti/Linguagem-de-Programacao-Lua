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
ID = [_|a-z|A-Z][a-z|A-Z|0-9|_]*
INTEIRO = 0|[1-9][0-9]*
PONTOFLUTUANTE = [0-9][0-9]*"."[0-9]+
OPERADORES_MATEMATICOS = ("+" | "-" | "*" | "/")
%%
"if" { imprimir("Intrucao if", yytext()); }
"then" { imprimir("Intrucao then", yytext()); }
{BRANCO} { imprimir("Branco", yytext()); }
{ID} { imprimir("Identificador", yytext()); }
{INTEIRO} { imprimir("Numero", yytext()); }
{PONTOFLUTUANTE} { imprimir("Ponto plututante", yytext()); }
{OPERADORES_MATEMATICOS} { imprimir("Operadores matematatico", yytext()); }
"==" { imprimir("Operador igualdade", yytext()); }
. { throw new RuntimeException("Caractere invalido \""+yytext() +
"\" na linha "+yyline+", coluna "+yycolumn); }
