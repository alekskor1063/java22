package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

public class Model {
    public int code;
    public String desc;
    public String icon; // переделать!
    public int temp;
    public int feelsLike;
    public int pressure;
    public int humidity;
    public float windSpeed;
    public int windDeg;
    public int clouds;
    public int time;
    public String name;


    public static URL createUrl(String link) {
        try {
            return new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private int getCode(String city) throws IOException {

        // Выцепляем нужный город из файла city.list.json
        Path path = FileSystems.getDefault().getPath("res/city.list.json");
        String fileText = Files.readString(path, StandardCharsets.UTF_8);
        JSONArray cities = new JSONArray(fileText);
        int id = 0;
        JSONObject element = null;
        for (int i = 0; i < cities.length(); i++) {
            element = cities.getJSONObject(i);
            if (Objects.equals(element.getString("name"), city)) {
                id = element.getInt("id");
            }
        }
        return id;
        // return 524901;
    }

    public Model(String city) throws IOException {
        String appID = "eca546265305d78a307d9477b82d37c9";

        String cityID = Integer.toString(getCode(city));
        String link = "http://api.openweathermap.org/data/2.5/weather?id="+ cityID + "&lang=ru&units=metric&APPID=" + appID;

        URL url = createUrl(link);


        // парсим сайт
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("GET");

        http.setRequestProperty("Accept", "application/json");
        http.setConnectTimeout(5000);
        http.setReadTimeout(5000);
        http.setInstanceFollowRedirects(false);
        int status = http.getResponseCode();

        this.code = status; // код
        System.out.println(status);
        BufferedReader in = null;
        if (status < 299) {
            in = new BufferedReader(new InputStreamReader(http.getInputStream()));
        } else {
            in = new BufferedReader(new InputStreamReader(http.getErrorStream()));
        }
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        http.disconnect();

        String resultJson = content.toString();
        if (status <= 298) {
            JSONObject weatherJsonObject = new JSONObject(resultJson);
            JSONObject mainArray = weatherJsonObject.getJSONObject("main");
            JSONArray weatherArrayOne = weatherJsonObject.getJSONArray("weather");
            JSONObject cloudsArray = weatherJsonObject.getJSONObject("clouds");
            JSONObject windArray = weatherJsonObject.getJSONObject("wind");
            this.time = weatherJsonObject.getInt("dt");
            this.temp = Math.round(mainArray.getFloat("temp"));
            this.feelsLike = Math.round(mainArray.getFloat("feels_like"));
            this.pressure = mainArray.getInt("pressure");
            this.humidity = mainArray.getInt("humidity");
            JSONObject weatherArray = weatherArrayOne.getJSONObject(0);
            this.desc = weatherArray.getString("description");
            this.icon = weatherArray.getString("icon");
            this.windSpeed = windArray.getFloat("speed");
            this.windDeg = windArray.getInt("deg"); // градусы
            this.clouds = cloudsArray.getInt("all"); // проценты облачности
            this.name = weatherJsonObject.getString("name");
        } else {
            this.desc = (String) resultJson;
        }
    }

    // Еще написать: конструктор от файла.
}
