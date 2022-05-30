package Model;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

import com.sun.source.util.SourcePositions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONPointer;

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
        int id = -1;
        JSONObject element = null;
        for (int i = 0; i < cities.length(); i++) {
            element = cities.getJSONObject(i);
            if (Objects.equals(element.getString("name"), city)) {
                id = element.getInt("id");
            }
        }
        return id;
    }


    public Model(String city) throws IOException {
        String appID = "eca546265305d78a307d9477b82d37c9";

        int code = getCode(city);

        String cityID = Integer.toString(code);
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
            throw new IOException("City fault");
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

        // Сохранить прогноз в файл
        try {
            File myFile = new File ("res/Forecasts/" + time + ".txt");
            if (myFile.createNewFile()) {
                System.out.println("File created: " + myFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            FileWriter myWriter = new FileWriter("res/Forecasts/" + time + ".txt");
            myWriter.write(resultJson);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    // конструктор от файла
    public Model(int rollback) throws IOException {
        File dir = new File("res/Forecasts/");
        File[] files = dir.listFiles();
        int[] fileNames = new int[files.length];
        String buffer = "";
        for (int i = 0; i < files.length; i++){
            buffer = files[i].getName();
            // System.out.println(buffer.substring(0, buffer.length()-4));
            fileNames[i] = Integer.parseInt(buffer.substring(0, buffer.length()-4));
        }
        Arrays.sort(fileNames);

        // Найти файл
        String fileName = Integer.toString(fileNames[files.length - 1 + rollback]);
        // Прочитать его
        String everything = "";
        try(BufferedReader br = new BufferedReader(new FileReader("res/Forecasts/" + fileName + ".txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JSONObject weatherJsonObject = new JSONObject(everything);
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
        this.code = 200;
    }
}