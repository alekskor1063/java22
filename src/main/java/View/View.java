package View;

import Controller.Controller;
import Model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class View extends JFrame {
    private JFrame frame;
    public JButton next;
    public JButton prev;
    public JTextField tf;
    public JLabel startWarn;
    public JLabel timeStamp;
    public JLabel characteristic;
    public JLabel temperature;
    public JLabel humidity;
    public JLabel feelsLike;
    public JLabel pressure;
    public JLabel wind;
    public JLabel clouds;
    public JLabel place;

    public static void main(String[] args) throws IOException {
        new View();
    }


    public View() throws IOException {
        frame = new JFrame("Weather");
        next = new JButton(">>");
        prev = new JButton("<<");
        tf = new JTextField("City name...");
        startWarn = new JLabel("Type your city and press \">>\"...");
        timeStamp = new JLabel("");
        temperature = new JLabel("");
        feelsLike = new JLabel("");
        characteristic = new JLabel("");
        humidity = new JLabel("");
        pressure = new JLabel("");
        wind = new JLabel("");
        clouds = new JLabel("");
        place = new JLabel("");
        timeStamp.setBounds(0, 0, 280, 20);
        place.setBounds(0, 0, 280, 60);
        characteristic.setBounds(0, 120, 280, 30);
        temperature.setBounds(120, 30, 140, 80);
        feelsLike.setBounds(0, 100, 280, 30);
        pressure.setBounds(0, 140, 280, 30);
        humidity.setBounds(0, 160, 280, 30);
        wind.setBounds(0, 180, 280, 30);
        clouds.setBounds(0, 200, 280, 30);
        timeStamp.setHorizontalAlignment(JTextField.CENTER);
        timeStamp.setVerticalAlignment(JTextField.CENTER);
        temperature.setHorizontalAlignment(JTextField.CENTER);
        temperature.setVerticalAlignment(JTextField.CENTER);
        feelsLike.setHorizontalAlignment(JTextField.CENTER);
        feelsLike.setVerticalAlignment(JTextField.CENTER);
        pressure.setHorizontalAlignment(JTextField.CENTER);
        pressure.setVerticalAlignment(JTextField.CENTER);
        wind.setHorizontalAlignment(JTextField.CENTER);
        wind.setVerticalAlignment(JTextField.CENTER);
        clouds.setHorizontalAlignment(JTextField.CENTER);
        clouds.setVerticalAlignment(JTextField.CENTER);
        humidity.setHorizontalAlignment(JTextField.CENTER);
        humidity.setVerticalAlignment(JTextField.CENTER);
        characteristic.setHorizontalAlignment(JTextField.CENTER);
        characteristic.setVerticalAlignment(JTextField.CENTER);
        place.setHorizontalAlignment(JTextField.CENTER);
        place.setVerticalAlignment(JTextField.CENTER);

        startWarn.setBounds(0, 0, 280, 260);
        startWarn.setHorizontalAlignment(JTextField.CENTER);
        startWarn.setVerticalAlignment(JTextField.CENTER);
        next.setBounds(220, 230, 60, 30);
        ActionListener actionListener = new Controller(next, prev, tf, startWarn, timeStamp,  characteristic, temperature, feelsLike, pressure, wind,
                clouds, humidity, place);
        next.addActionListener(actionListener);
        prev.addActionListener(actionListener);
        tf.setBounds(70, 230, 150, 30);
        prev.setBounds(7, 230, 60, 30);
        frame.add(startWarn);
        frame.add(timeStamp);
        frame.add(temperature);
        frame.add(feelsLike);
        frame.add(pressure);
        frame.add(humidity);
        frame.add(wind);
        frame.add(clouds);
        frame.add(characteristic);
        frame.add(tf);
        frame.add(next);
        frame.add(prev);
        frame.add(place);

        temperature.setFont(new Font("Serif", Font.PLAIN, 64));
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        startWarn.setText("Enter your city and press >> to see the weather");
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
