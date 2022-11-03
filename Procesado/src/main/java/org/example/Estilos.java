/*
 * Created by JFormDesigner on Fri Oct 28 20:14:07 CEST 2022
 */

package org.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

/**
 * @author Arturo, Carlos
 */
public class Estilos extends JDialog {
    JTextArea texto;
    Color color;

    public Estilos(Window owner, JTextArea texto) {
        super(owner);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.texto = texto;
        color = texto.getForeground();

        initComponents();
        textPrueba.setFont(texto.getFont());
        textPrueba.setForeground(texto.getForeground());
        spinner1.setModel(new SpinnerNumberModel(texto.getFont().getSize(), 0, 100, 1));
    }

    private void colorAction(ActionEvent e) {
        // TODO add your code here
        JColorChooser colorChooser = new JColorChooser();

        color = JColorChooser.showDialog(this, "Seleccione un color", color);
        textPrueba.setForeground(color);
    }

    private void cancelAction(ActionEvent e) {
        // TODO add your code here
        dispose();
    }

    private void textoStateChanger(ChangeEvent e) {
        // TODO add your code here
        textPrueba.setFont(new Font(textPrueba.getFont().getName(), textPrueba.getFont().getStyle(), (int) spinner1.getValue()));
    }

    private void okAction(ActionEvent e) {
        // TODO add your code here
        texto.setFont(textPrueba.getFont());
        texto.setForeground(textPrueba.getForeground());
        dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        panel2 = new JPanel();
        label1 = new JLabel();
        label4 = new JLabel();
        spinner1 = new JSpinner();
        panel1 = new JPanel();
        label2 = new JLabel();
        colorButton = new JButton();
        label3 = new JLabel();
        textPrueba = new JLabel();
        buttonBar = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setBackground(new Color(0x272727));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setBackground(new Color(0x272727));
                contentPanel.setLayout(new GridLayout(3, 1, 0, 25));

                //======== panel2 ========
                {
                    panel2.setOpaque(false);
                    panel2.setLayout(new GridLayout(1, 3));

                    //---- label1 ----
                    label1.setText("Tama\u00f1o de la fuente");
                    label1.setHorizontalAlignment(SwingConstants.CENTER);
                    label1.setForeground(Color.white);
                    label1.setBorder(null);
                    label1.setBackground(new Color(0x272727));
                    panel2.add(label1);
                    panel2.add(label4);

                    //---- spinner1 ----
                    spinner1.setMaximumSize(new Dimension(32767, 30));
                    spinner1.setModel(new SpinnerNumberModel(0, 0, 100, 1));
                    spinner1.addChangeListener(e -> textoStateChanger(e));
                    panel2.add(spinner1);
                }
                contentPanel.add(panel2);

                //======== panel1 ========
                {
                    panel1.setOpaque(false);
                    panel1.setLayout(new GridLayout(1, 3));
                    panel1.add(label2);

                    //---- colorButton ----
                    colorButton.setText("Seleccionar color");
                    colorButton.setBackground(new Color(0x0e0f0c));
                    colorButton.setBorder(new LineBorder(Color.gray, 1, true));
                    colorButton.setForeground(Color.white);
                    colorButton.addActionListener(e -> colorAction(e));
                    panel1.add(colorButton);
                    panel1.add(label3);
                }
                contentPanel.add(panel1);

                //---- textPrueba ----
                textPrueba.setText("Este es un texto de prueba");
                textPrueba.setHorizontalAlignment(SwingConstants.CENTER);
                contentPanel.add(textPrueba);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setOpaque(false);
                buttonBar.setLayout(new GridLayout(1, 2, 50, 0));

                //---- okButton ----
                okButton.setText("OK");
                okButton.addActionListener(e -> okAction(e));
                buttonBar.add(okButton);

                //---- cancelButton ----
                cancelButton.setText("Cancel");
                cancelButton.addActionListener(e -> cancelAction(e));
                buttonBar.add(cancelButton);
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JPanel panel2;
    private JLabel label1;
    private JLabel label4;
    private JSpinner spinner1;
    private JPanel panel1;
    private JLabel label2;
    private JButton colorButton;
    private JLabel label3;
    private JLabel textPrueba;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
