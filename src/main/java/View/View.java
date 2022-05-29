package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private JFrame frame;
    public JButton next;
    public JButton prev;
    public JTextField tf;
    public JLabel startWarn;

    public static void main(String[] args) {
        new View();
    }

    public View() {
        frame = new JFrame("Weather");
        next = new JButton(">>");
        prev = new JButton("<<");
        tf = new JTextField("City name...");
        startWarn = new JLabel("Type your city and press \">>\"...");
        startWarn.setBounds(0, 0, 280, 260);
        startWarn.setHorizontalAlignment(JTextField.CENTER);
        startWarn.setVerticalAlignment(JTextField.CENTER);
        next.setBounds(220, 230, 60, 30);
        ActionListener actionListener = new Controller(next, prev, tf, startWarn);
        next.addActionListener(actionListener);
        tf.setBounds(70, 230, 150, 30);
        prev.setBounds(7, 230, 60, 30);
        frame.add(startWarn);
        frame.add(tf);
        frame.add(next);
        frame.add(prev);

        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /*
    private Image getImage (String name) {
        String filename = "/WeatherIcons/" + name.toLowerCase() + ".png";
        filename = "/WeatherIcons/weather_icon-10.png";
        ImageIcon icon = new ImageIcon (getClass().getResource(filename));
        return icon.getImage();
    }
    */
    public String GetCity(){
        return tf.getText();
    }
    public void SetCity(String city){
        startWarn.setText("City is " + city);
    }
}
