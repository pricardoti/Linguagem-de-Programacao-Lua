package analisadorlexico;

import java.io.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalisadorLexico {

    String mensagem, mensagemArquivo;

    public static String analisador(String str) {

        AnalisadorLexico a = new AnalisadorLexico();

        BufferedReader reader = new BufferedReader(new StringReader(str));
        try {

            int line = 1;
            while ((str = reader.readLine()) != null) {
                int i = 0;
                String c = str.trim();
                
                while (i < c.length()) {
                    
                    char t = c.charAt(i);
                    
                    if (Character.isDigit(t)) {
                        
                        String word = "";
                        word += t;
                        int j = i + 1;
                        j = j - 1;
                        
                        while (checkRegex(Character.toString(c.charAt(j)), "[\\.\\p{Alnum}]")) {
                            word += c.charAt(j);
                            j++;
                            if (j == c.length()) {
                                break;
                            }
                        }

                        word = word.substring(1);
                        
                        i = j;

                        a.setMensagem(word, line);
                        
                        continue;
                        
                    } else if (Character.isLetter(t)) {
                        
                        String word = "";
                        word += t;
                        int j = i + 1;
                        j = j - 1;
                        
                        while (checkRegex(Character.toString(c.charAt(j)), "[\\w\\p{Punct}\\p{L}]")) {
                            word += c.charAt(j);
                            j++;
                            if (j == c.length()) {
                                break;
                            }
                        }

                        word = word.substring(1);
                        
                        i = j;

                        a.setMensagem(word, line);
                        
                        continue;
                        
                    } else if (!Character.isLetterOrDigit(t)) {
                        
                        if (separador(t)) {
                            
                        } else {

                            String word = "";
                            word += t;
                            
                            String pattern = "";
                            String limit = "";
                            
                            // Checa Sinal para o número, para operadores aritméticos, comentários
                            if( checkRegex(Character.toString(t), "[+|\\-|\\.]") ) {

                                int w = i + 1;
                                
                                Boolean valid = false;
                                
                                try {
                                    valid = checkRegex(Character.toString(c.charAt(w)), "[\\-]");
                                } catch (StringIndexOutOfBoundsException e) { }
                                
                                if (valid) {
                                    pattern = "[\\s|\\w|\\d|\\p{Punct}|\\p{L}]";
                                } else {
                                    pattern = "[\\w|\\d|\\p{Punct}|\\p{L}]";
                                }
                                
                            // Checa variável
                            } else if ( checkRegex(Character.toString(t), "[\\_]") ) {
                                pattern = "[\\w|\\p{Digit}]";
                                
                            // Checa string
                            } else if ( checkRegex(Character.toString(t), "[\"]") ) {
                                
                                pattern = "[\\p{L}|\\w|\\d|\\p{Punct}|\\p{Alpha}|\\s|[^\\p{Z}\\p{C}]]";
                                limit = "\"";
                                
                            // Checa operadores relacionais
                            } else if ( checkRegex(Character.toString(t), "[<|>|=|~]") ) {
                                
                                pattern = "[<|>|=|~]";
                                
                            // Simbolos
                            } else if ( checkRegex(Character.toString(t), "[,]") ) {
                                
                                pattern = "[,]";
                                
                            // Checa outros tipos de pontuações
                            } else if ( checkRegex(Character.toString(t), "[\\p{Graph}&&[^+\\-\\.\\_\"\\<\\=\\~]]") ) {
                                
                                pattern = "[\\p{Graph}&&[^\\+\\-\\.\\_\"]|[^\\p{Z}\\p{C}]|\\p{L}|\\d]";
                                
                            }
                            
                            
                            int j = i + 1;
                            j = j - 1;

                            int aux = 0;
                            String delimitador = "";
                            while (checkRegex(Character.toString(c.charAt(j)), pattern)) {
                                
                                if ( (aux > 0) && (Character.toString(c.charAt(j)).equals(limit)) ) {
                                    word += c.charAt(j);
                                    delimitador = Character.toString(c.charAt(j));
                                    j++;
                                    break;
                                }
                                word += c.charAt(j);
                                j++;
                                aux++;
                                if (j == c.length()) {
                                    break;
                                }
                            }
                            
                            i = j;

                            word = word.substring(1);
                            
                            a.setMensagem(word, line);
                            
                        }
                        
                        i++;
                        continue;
                        
                    }
                }
                line++;
            }

            a.createFile();

            return a.getMensagem();

        } catch (IOException e) {}

        return null;

    }

    public static boolean separador(char c) {
        if (c == ' ') {
            return true;
        } else {
            return false;
        }
    }

    public String[] setMensagem(String word, int line) {
        
        String[] info = scanner(word);

        if (word.contains("<=")) {
            word = "&lt;=";
        } else if (word.contains("<")) {
            word = "&lt;";
        }
        
        String color = (info[0].equals("VERDADEIRO")) ? "green" : "red";

        String resultado = (info[0].equals("FALSO")) ? "ERRO LÉXICO" : info[1];

        String msg = "<font face=arial size=4 color=" + color + ">Linha " + line + ": <b>" + word + "</b> - " + resultado + "</font><br/>";

        String msgFile = "Linha " + line + ": " + word + " - " + resultado + "\n";

        if (this.mensagem != null) {
            this.mensagem += msg;
            this.mensagemArquivo += msgFile;
        } else {
            this.mensagem = msg;
            this.mensagemArquivo = msgFile;
        }

        String[] result = new String[]{info[0], msg};
        return result;
    }

    public void createFile() {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("log.txt", true)));
            Date date = new Date();
            out.println("// -- " + date.toString() + " -- //");
            out.println(this.mensagemArquivo);
            out.close();
        } catch (IOException e) {
        }
    }

    public String getMensagem() {
        return this.mensagem;
    }

    public static String[] scanner(String word) {

        String bool, tipo;
        String[] info;

        // PALAVRAS RESERVADAS
        String number = "^(?![+-]?\\.)(?!\\$)(?!\\p{Alpha})(?!\\p{Punct}&&[^+-])(?!\\p{Space})(?![\\p{Graph}&&[^\\d\\+\\-]])(((?![+-]0)(\\+|-)?[^0]+[0-9]*+(\\.[0-9]*))|((?![+-]0)(\\+|-)?^[^0][0-9]*)|((\\+|-)?0?+(\\.[0-9]+))|0)?$";
        String funcoes = "^(io\\.write|return|print|function)$";
        String oplogicos = "^(and|or|not)$";
        String tdecisao = "^(if|then|else|elseif)$";
        String literativos = "^(while|do|repeat|until)$";
        String identificadores = "^[^\\p{Digit}\\p{Punct}áàäâÁÀÄÂéèëêÉÈËÊíìïîÍÌÏÎóòöôÓÒÖÔúùüûÚÙÜÛ](\\w)*$";
        String oparitmeticos = "^[-|+|\\*|/]$";
        String comentarios = "^-{2,}([\\p{Punct}]|\\s|\\w|\\d|\\p{L})*$";
        String string = "^\"(\\p{Digit}|\\p{Alpha}|\\s|[\\p{Punct}&&[^\"]]|[^\\p{Z}\\p{C}&&[^\"]])*\"$";
        String delimitadores = "^(end|\\(|\\)|\")$";
        String relacionais = "^(<|>|<=|>=|==|~=)$";
        String nil = "^(nil)$";
        String bolean = "^(true|false)$";
        String concatenacao = "^(\\.\\.)$";
        String simbolos = "^(=|,)$";
        
        if (checkRegex(word, simbolos)) {
            bool = "VERDADEIRO";
            tipo = " símbolos";
        }
        else if (checkRegex(word, relacionais)) {
            bool = "VERDADEIRO";
            tipo = " operadores relacionais";            
        }
        else if (checkRegex(word, concatenacao)) {
            bool = "VERDADEIRO";
            tipo = " concatenação";
        }
        else if (checkRegex(word, delimitadores)) {
            bool = "VERDADEIRO";
            tipo = " delimitadores";
        }
        else if (checkRegex(word, string)) {
            bool = "VERDADEIRO";
            tipo = " string";
        }
        else if (checkRegex(word, comentarios)) {
            bool = "VERDADEIRO";
            tipo = " comentários";
        }
        else if (checkRegex(word, oparitmeticos)) {
            bool = "VERDADEIRO";
            tipo = " operadores aritméticos";
        }
        else if (checkRegex(word, number)) {
            bool = "VERDADEIRO";
            tipo = " number";
        }
        else if (checkRegex(word, funcoes)) {
            bool = "VERDADEIRO";
            tipo = " funções";
        }
        else if (checkRegex(word, oplogicos)) {
            bool = "VERDADEIRO";
            tipo = " operadores lógicos";
        }
        else if (checkRegex(word, tdecisao)) {
            bool = "VERDADEIRO";
            tipo = " tomada de decisão";
        }
        else if (checkRegex(word, literativos)) {
            bool = "VERDADEIRO";
            tipo = " laços iterativos";
        }
        else if (checkRegex(word, nil)) {
            bool = "VERDADEIRO";
            tipo = " nil";
        }
        else if (checkRegex(word, bolean)) {
            bool = "VERDADEIRO";
            tipo = " boolean";
        }
        else if (checkRegex(word, identificadores)) {
            bool = "VERDADEIRO";
            tipo = " identificador";
        }
        else {
            bool = "FALSO";
            tipo = "";
        }

        info = new String[]{bool, tipo};
        return info;

    }

    public static boolean checkRegex(String word, String expressao) {
        //Setando a expressao regular passada de parametro
        Pattern pattern = Pattern.compile(expressao);

        //Setando com o valor atual, a ser testado, no loop
        Matcher matcher = pattern.matcher(word);

        return matcher.matches();
    }
}
