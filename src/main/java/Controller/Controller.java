package Controller;
import Model.Model;
import View.View;
import org.json.JSONException;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.JobKOctets;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class Controller implements ActionListener {
    private JButton next;
    private JButton prev;
    private JTextField tf;
    private JLabel startWarn;
    private int revert;

    public JLabel timeStamp;
    public JLabel characteristic;
    public JLabel temperature;
    public JLabel humidity;
    public JLabel feelsLike;
    public JLabel pressure;
    public JLabel wind;
    public JLabel clouds;
    public JLabel place;
    public JLabel icon;

    public Controller(JButton next, JButton prev, JTextField tf, JLabel startWarn, JLabel timeStamp, JLabel characteristic,
                      JLabel temperature, JLabel feelsLike, JLabel pressure, JLabel wind, JLabel clouds, JLabel humidity, JLabel place, JLabel icon){
        this.next = next;
        this.prev = prev;
        this.tf = tf;
        this.startWarn = startWarn;
        this.revert = 0;
        this.timeStamp = timeStamp;
        this.characteristic = characteristic;
        this.temperature = temperature;
        this.humidity = humidity;
        this.feelsLike = feelsLike;
        this.pressure = pressure;
        this.wind = wind;
        this.clouds = clouds;
        this.place = place;
        this.icon = icon;
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
                    hide();
                    startWarn.setText("This city was last. Press >> to go forth");
                } else {
                    printText(model);
                    //startWarn.setText("City is " + model.name + " " + Integer.toString(revert) + ";\n");
                    startWarn.setText("");
                }
            } catch (ArrayIndexOutOfBoundsException | IOException | JSONException ex) {
                revert++;
                hide();
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
                        printText(model);
                        startWarn.setText("");
                    } else {
                        // revert++;
                        hide();
                        startWarn.setText("Please, insert desired city in the text field.");
                    }
                } else {
                    revert++;
                    model = new Model(revert);
                    //System.out.print(model.time);
                    if (model.time == 2000000000) {
                        hide();
                        startWarn.setText("Insert your city and press >>!");
                    } else {
                        printText(model);
                        //startWarn.setText("City is " + model.name + " " + Integer.toString(revert) + ";\n");
                        startWarn.setText("");
                    }
                }
            } catch (IOException ex) {
                hide();
                startWarn.setText("Entered city was not found in base");
                throw new RuntimeException(ex);
            }
        }
    }

    private void hide() {
        timeStamp.setText("");
        characteristic.setText("");
        temperature.setText("");
        humidity.setText("");
        feelsLike.setText("");
        pressure.setText("");
        wind.setText("");
        clouds.setText("");
        place.setText("");
        icon.setVisible(false);
    }

    private void printText(Model model) throws IOException {
        int seco = model.time;
        Date date = new Date(seco * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("z HH:mm:ss dd.MM.yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        String formattedDate = sdf.format(date);

        //if (model.windDeg) {

        //}
        place.setText("Place: " + model.name);
        timeStamp.setText(formattedDate);
        characteristic.setText(model.desc);
        temperature.setText(Integer.toString(model.temp) + "??C");
        humidity.setText("Humidity: " + Integer.toString(model.humidity) + "%");
        feelsLike.setText("Feels like: " + Integer.toString(model.feelsLike) + "??C");
        pressure.setText("Pressure: " + Integer.toString(model.pressure * 3 / 4) + " mmHg");
        wind.setText("Wind: " + Float.toString(model.windSpeed) + " m/sec"); // + Integer.toString(model.windDeg) + ", "
        clouds.setText("Clouds are filling " + Integer.toString(model.clouds) + "% of sky");
        String resource = "http://openweathermap.org/img/wn/" + model.icon + "@2x.png";
        System.out.println(resource);
        URL url = new URL(resource);
        URLConnection conn = url.openConnection();
        InputStream in = conn.getInputStream();
        BufferedImage img = ImageIO.read(in);
        assert url != null;
        ImageIcon icon2 = new ImageIcon(url);
        icon.setIcon(icon2);

        // icon.setBounds(40, 30, 140, 80);
        icon.setVisible(true);
    }
}
