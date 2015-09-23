package br.com.compiladores.luaFlex;

import java.io.File;

public class Gerador {

	public static void main(String[] args) {
		
		
		 String arquivo = "C:\\Users\\Evandro\\Desktop\\Compiladores\\workspace\\luaFlex\\src\\br\\com\\compiladores\\luaFlex\\lua.lex";
		
		File file = new File(arquivo );
		jflex.Main.generate(file);
		
		
	}

}
