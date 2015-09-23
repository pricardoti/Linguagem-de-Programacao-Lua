package br.com.compiladores.luaFlex;

import java.io.IOException;
import java.io.StringReader;

public class TesteAnalisadorLexico {

	public static void main(String[] args) throws IOException {
		String expr = "and";
		
		AnalisadorLexico lexico = new AnalisadorLexico(new StringReader(expr));
		lexico.yylex();
		
	}
	
}
