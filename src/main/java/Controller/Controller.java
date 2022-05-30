package Controller;
import Model.Model;
import View.View;
import org.json.JSONException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class Controller implements ActionListener {
    private JButton next;
    private JButton prev;
    private JTextField tf;
    private JLabel startWarn;
    private int revert;

    public Controller(JButton next, JButton prev, JTextField tf, JLabel startWarn){
        this.next = next;
        this.prev = prev;
        this.tf = tf;
        this.startWarn = startWarn;
        this.revert = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        System.out.println(revert);
        if (e.getSource() == prev){
            Model model = null;
            try {
                revert--;
                model = new Model(revert);
                if (model.time == 1) {
                    startWarn.setText("This city was last. Press >> to go forth");
                } else {
                    startWarn.setText("City is " + model.name + " " + Integer.toString(revert) + ";\n");
                }
            } catch (ArrayIndexOutOfBoundsException | IOException | JSONException ex) {
                revert++;
                startWarn.setText("You saw all the forecasts.");
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == next){
            String cityName = tf.getText();
            startWarn.setText("City is " + cityName);
            Model model = null;
            try {
                if (revert >= 0) {
                    if (!Objects.equals(cityName, "City name...")) {
                        // System.out.println(cityName);
                        model = new Model(cityName);
                    } else {
                        // revert++;
                        startWarn.setText("Please, insert desired city in the text field.");
                    }
                } else {
                    revert++;
                    model = new Model(revert);
                    System.out.print(model.time);
                    if (model.time == 2000000000) {
                        startWarn.setText("Insert your city and press >>!");
                    } else {
                        startWarn.setText("City is " + model.name + " " + Integer.toString(revert) + ";\n");
                    }
                }
            } catch (IOException ex) {
                startWarn.setText("Entered city was not found in base");
                throw new RuntimeException(ex);
            }
        }
    }
}
