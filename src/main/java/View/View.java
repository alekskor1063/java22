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
        panel = new JPanel();
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
}
