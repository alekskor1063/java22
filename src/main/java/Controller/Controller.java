package main.java.Controller;
import main.java.View.View;
import main.java.Model.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private JButton next;
    private JButton prev;
    private JTextField tf;
    private JLabel startWarn;

    public Controller(JButton next, JButton prev, JTextField tf, JLabel startWarn){
        this.next = next;
        this.prev = prev;
        this.tf = tf;
        this.startWarn = startWarn;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == next){
            startWarn.setText("City is " + tf.getText());
        }
    }
}
