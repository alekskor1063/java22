package Controller;
import Model.Model;
import View.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
            String cityName = tf.getText();
            startWarn.setText("City is " + cityName);
            Model model = null;
            try {
                model = new Model(cityName);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            startWarn.setText("City is " + cityName + ";\n" + model.name);
        }
    }
}
