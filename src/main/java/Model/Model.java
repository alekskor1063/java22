package Model;


import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Objects;

import com.google.gson.Gson;
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


    public static URL createUrl(String link) {
        try {
            return new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public Model(String city) throws IOException {
        String appID = "eca546265305d78a307d9477b82d37c9";
        String cityID = "524901";


        // Выцепляем нужный город из файла city.list.json
        /*
        Gson gson = new Gson();
        String fileText = Files.readString(FileSystems.getDefault().getPath("/city.list.json"));
        CityList[] bigList = gson.fromJson(fileText, CityList[].class);

        for (int i = 0; i < bigList.length; i++){
            if (Objects.equals(bigList[i].name, city)){
                cityID = Integer.toString(bigList[i].id);
            }
        }
        */
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
        } else {
            this.desc = (String) resultJson;
        }
    }

    // Еще написать: конструктор от файла.
}
