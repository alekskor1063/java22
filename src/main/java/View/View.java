package main.java.View;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private JPanel panel;

    public static void main(String[] args) {
        new View();
    }

    private View (){
        initPanel();
        initFrame();
    }

    private void initPanel () {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(getImage("weather_icon-01"), 45, 15, this);
            }
        };
        panel.setPreferredSize(new Dimension(300, 300));
        add(panel);
    }

    private void initFrame () {
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Weather");
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private Image getImage (String name) {
        String filename = "/WeatherIcons/" + name.toLowerCase() + ".png";
        // filename = "/WeatherIcons/weather_icon-01.png";
        ImageIcon icon = new ImageIcon (getClass().getResource(filename));
        return icon.getImage();
    }
}
