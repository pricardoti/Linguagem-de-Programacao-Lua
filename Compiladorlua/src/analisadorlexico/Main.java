package analisadorlexico;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import utils.TextLineNumber;


public class Main extends JFrame {

    //Componentes
    JTextPane textAreaPrincipal;
    JEditorPane textAreaResultado;
    JButton botaoCompilar, botaoLimpar;
    JMenuBar menuBar;
    JMenu menu;
    //Areas dos componentes
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;

    public Main() {
    }

    public void addComponentsToPane(final Container pane) {

        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }

        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        if (shouldFill) {
            //natural height, maximum width
            c.fill = GridBagConstraints.HORIZONTAL;
        }

        //Adicionando componente do menu
        menuBar = new JMenuBar();
        menu = new JMenu("Sobre");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);
        menu.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(pane, 
                        "UNIVERSIDADE DO ESTADO DO RIO GRANDE DO NORTE - Campi Natal\n"+
                        "DEPARTAMENTO DE CIÊNCIA DA COMPUTAÇÃO\n"+
                        "DISCIPLINA: Compiladores\n"+
                        "PROFESSORA: Rosiery\n\n"+
                        "GRUPO: Jônata Marcelino, Porto Costa, Mariêta Cunha" +
                        "ANALISADOR LÉXICO\n\n"+
                        "Sistema feito para a obtenção da nota da primeira unidade de Compiladores.\n" +
                        "Data da Última Correção: 13/02/2013"
                        );
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        pane.add(menuBar, c);

        //Adicionando componente da text area principal
        textAreaPrincipal = new JTextPane();
        Border border = BorderFactory.createLineBorder(Color.WHITE);
        textAreaPrincipal.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        textAreaPrincipal.setFont(new Font("Arial", Font.PLAIN, 15));
        JScrollPane scrollPane = new JScrollPane(textAreaPrincipal,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(575, 200));
        scrollPane.setMinimumSize(new Dimension(10, 10));
        TextLineNumber tln = new TextLineNumber(textAreaPrincipal);
        scrollPane.setRowHeaderView(tln);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 40;
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(scrollPane, c);

        //Adicionando componente do botão compilar
        botaoCompilar = new JButton("Compilar");
        botaoCompilar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                AnalisadorLexico analisa = new AnalisadorLexico();
                textAreaResultado.setText(analisa.analisador(textAreaPrincipal.getText()));
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 10;
        c.weightx = 1;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 2;
        pane.add(botaoCompilar, c);

        //Adicionando componente do botão limpar
        botaoLimpar = new JButton("Limpar");
        botaoLimpar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                textAreaPrincipal.setText("");
                textAreaResultado.setText("");
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 10;
        c.weightx = 0.0;
        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 2;
        pane.add(botaoLimpar, c);

        //Adicionando componente label
        Label lab = new Label("Resultado:");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 3;
        pane.add(lab, c);

        //Adicionando componente da text area do resultado
        textAreaResultado = new JEditorPane();
        textAreaResultado.setContentType("text/html");
        JScrollPane editorScrollPane = new JScrollPane(textAreaResultado);
        editorScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        editorScrollPane.setPreferredSize(new Dimension(575, 100));
        editorScrollPane.setMinimumSize(new Dimension(10, 10));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 4;
        pane.add(editorScrollPane, c);

    }

    public void actionPerformed(ActionEvent e) {
        //...Get information from the action event...
        //...Display it in the text area...
        System.out.println(e);
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        Main frame = new Main();
        frame.setTitle("Analisador Léxico");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        //Set up the content pane.
        frame.addComponentsToPane(frame.getContentPane());
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
