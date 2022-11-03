/*
 * Created by JFormDesigner on Wed Oct 26 12:20:21 CEST 2022
 */

package org.example;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * @author Arturo, Carlos
 */
public class Procesado extends JFrame {

    File archivo;

    public static void main(String[] args) {
        Procesado procesado = new Procesado();
        procesado.setSize(new Dimension(600, 600));

        Timer timer = new Timer(500, e -> procesado.labelHora.setText(DateFormat.getDateTimeInstance().format(new Date())));
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();

        procesado.setVisible(true);
    }

    public Procesado() {
        initComponents();
    }

    private void BotonAcercaDe(ActionEvent e) {
        // TODO add your code here
        String message = "Trabajo hecho por Arturo y Carlos";
        String title = "Acerca de";
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }


    private void abrirAction(ActionEvent e) {
        // TODO add your code here

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setCurrentDirectory(new File((getClass().getResource("/").getPath())));
        fileChooser.setFileFilter(new FileTypeFilter(".txt", "Archivo de texto"));

        int response = fileChooser.showOpenDialog(this);

        if (response == JFileChooser.APPROVE_OPTION) {
            archivo = fileChooser.getSelectedFile();
            StringBuilder texto = new StringBuilder("");
            textoArchivo.setText("");
            textoArchivo.setEditable(true);
            labelInformacion.setText("Archivo abierto: " + archivo.getPath() + "           Tamaño: " + archivo.length() + " bytes");

            Scanner sc;
            try {
                sc = new Scanner(archivo);

                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    texto.append(line).append("\n");
                }
                textoArchivo.setText(texto.toString());

            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Has elegido un archivo que no existe", "Error", JOptionPane.ERROR_MESSAGE);;
            }
        }
    }

    private void guardarAction(ActionEvent e) {
        // TODO add your code here
        FileWriter fichero;
        try {
            JFileChooser fileChooser = new JFileChooser(new File((getClass().getResource("/").getPath())));
            fileChooser.setDialogTitle("Guardar Archivo");
            fileChooser.setFileFilter(new FileTypeFilter(".txt", "Archivo de texto"));
            fileChooser.setCurrentDirectory(new File("C:\\"));

            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                fichero = new FileWriter(fileChooser.getSelectedFile().getPath()+".txt");
                fichero.write(textoArchivo.getText());
                fichero.flush();
                fichero.close();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Archivo guardado correctamente", "Archivo guardado", JOptionPane.INFORMATION_MESSAGE);;
        }
    }

    private void salirAction(ActionEvent e) {
        // TODO add your code here
        try {
            StringBuilder texto = new StringBuilder();
            Scanner sc = new Scanner(archivo);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                texto.append(line).append("\n");
            }

            if (!texto.toString().equals(textoArchivo.getText())) {
                int response = JOptionPane.showConfirmDialog(this, "¿Desea guardar los cambios?", "Guardar cambios", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    guardarAction(e);
                }
                System.exit(0);
            }


        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void estiloAction(ActionEvent e) {
        // TODO add your code here
        new Estilos(this, textoArchivo).setVisible(true);

    }

    private void capitalizacionAction(ActionEvent e) {
        // TODO add your code here
        int response = JOptionPane.showOptionDialog(this, "¿Qué tipo de capitalización desea?", "Capitalización", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Pasar todo a mayúsculas", "Pasar todo a minúsculas", "Aplicar Camelcase"}, "Mayúsculas");
        if (response == 0) {
            textoArchivo.setText(textoArchivo.getText().toUpperCase());
        } else if (response == 1) {
            textoArchivo.setText(textoArchivo.getText().toLowerCase());
        } else if (response == 2) {
            textoArchivo.setText(textoArchivo.getText().toLowerCase());
            String[] palabras = textoArchivo.getText().split(" ");
            StringBuilder texto = new StringBuilder();
            for (String palabra : palabras) {
                texto.append(palabra.substring(0, 1).toUpperCase()).append(palabra.substring(1)).append(" ");
            }
            textoArchivo.setText(texto.toString());
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        menuBar1 = new JMenuBar();
        archivoMenu = new JMenu();
        panel2 = new JPanel();
        abrirButton = new JButton();
        panel3 = new JPanel();
        guardarButton = new JButton();
        panel4 = new JPanel();
        salirButton = new JButton();
        estiloButton = new JButton();
        capitalizacionButton = new JButton();
        acercaDeButton = new JButton();
        textoArchivo = new JTextArea();
        panel1 = new JPanel();
        labelInformacion = new JLabel();
        labelHora = new JLabel();

        //======== this ========
        setBackground(new Color(0x1a2323));
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== menuBar1 ========
        {
            menuBar1.setBorder(new LineBorder(new Color(0x414450), 1, true));
            menuBar1.setBorderPainted(false);
            menuBar1.setBackground(new Color(0x1a2323));

            //======== archivoMenu ========
            {
                archivoMenu.setText("Archivo");
                archivoMenu.setBackground(new Color(0x144050));
                archivoMenu.setForeground(Color.white);

                //======== panel2 ========
                {
                    panel2.setLayout(new GridLayout(1, 1));

                    //---- abrirButton ----
                    abrirButton.setText("Abrir");
                    abrirButton.setBackground(new Color(0x1a2323));
                    abrirButton.setForeground(Color.white);
                    abrirButton.setBorderPainted(false);
                    abrirButton.addActionListener(e -> abrirAction(e));
                    panel2.add(abrirButton);
                }
                archivoMenu.add(panel2);

                //======== panel3 ========
                {
                    panel3.setLayout(new GridLayout(1, 1));

                    //---- guardarButton ----
                    guardarButton.setText("Guardar");
                    guardarButton.setDefaultCapable(false);
                    guardarButton.setForeground(Color.white);
                    guardarButton.setBackground(new Color(0x1a2323));
                    guardarButton.setBorderPainted(false);
                    guardarButton.addActionListener(e -> guardarAction(e));
                    panel3.add(guardarButton);
                }
                archivoMenu.add(panel3);

                //======== panel4 ========
                {
                    panel4.setLayout(new GridLayout(1, 1));

                    //---- salirButton ----
                    salirButton.setText("Salir");
                    salirButton.setBackground(new Color(0x1a2323));
                    salirButton.setForeground(Color.white);
                    salirButton.setBorderPainted(false);
                    salirButton.setMargin(new Insets(2, 24, 2, 24));
                    salirButton.addActionListener(e -> salirAction(e));
                    panel4.add(salirButton);
                }
                archivoMenu.add(panel4);
            }
            menuBar1.add(archivoMenu);

            //---- estiloButton ----
            estiloButton.setText("Estilo");
            estiloButton.setForeground(Color.white);
            estiloButton.setBorderPainted(false);
            estiloButton.setContentAreaFilled(false);
            estiloButton.addActionListener(e -> estiloAction(e));
            menuBar1.add(estiloButton);

            //---- capitalizacionButton ----
            capitalizacionButton.setText("Capitalizacion");
            capitalizacionButton.setForeground(Color.white);
            capitalizacionButton.setBorderPainted(false);
            capitalizacionButton.setContentAreaFilled(false);
            capitalizacionButton.addActionListener(e -> capitalizacionAction(e));
            menuBar1.add(capitalizacionButton);

            //---- acercaDeButton ----
            acercaDeButton.setText("Acerca de");
            acercaDeButton.setBorderPainted(false);
            acercaDeButton.setContentAreaFilled(false);
            acercaDeButton.setForeground(Color.white);
            acercaDeButton.addActionListener(e -> BotonAcercaDe(e));
            menuBar1.add(acercaDeButton);
        }
        setJMenuBar(menuBar1);

        //---- textoArchivo ----
        textoArchivo.setLineWrap(true);
        textoArchivo.setWrapStyleWord(true);
        textoArchivo.setEditable(false);
        textoArchivo.setBackground(new Color(0x272727));
        textoArchivo.setForeground(Color.white);
        contentPane.add(textoArchivo, BorderLayout.CENTER);

        //======== panel1 ========
        {
            panel1.setBackground(new Color(0x1a2323));
            panel1.setLayout(new GridLayout(1, 2));

            //---- labelInformacion ----
            labelInformacion.setForeground(Color.white);
            panel1.add(labelInformacion);

            //---- labelHora ----
            labelHora.setHorizontalAlignment(SwingConstants.TRAILING);
            labelHora.setForeground(Color.white);
            panel1.add(labelHora);
        }
        contentPane.add(panel1, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JMenuBar menuBar1;
    private JMenu archivoMenu;
    private JPanel panel2;
    private JButton abrirButton;
    private JPanel panel3;
    private JButton guardarButton;
    private JPanel panel4;
    private JButton salirButton;
    private JButton estiloButton;
    private JButton capitalizacionButton;
    private JButton acercaDeButton;
    private JTextArea textoArchivo;
    private JPanel panel1;
    private JLabel labelInformacion;
    private JLabel labelHora;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
