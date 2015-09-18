package br.com.compiladores.luaFlex;

import java.io.IOException;
import java.io.StringReader;

public class TesteAnalisadorLexico {

	public static void main(String[] args) throws IOException {
		String expr = "if 2+3+a then ";
		
		AnalisadorLexico lexico = new AnalisadorLexico(new StringReader(expr));
		lexico.yylex();
		
	}
	
}
