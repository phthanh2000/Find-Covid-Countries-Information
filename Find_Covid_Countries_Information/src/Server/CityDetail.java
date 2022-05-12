/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.Arrays;
import org.jsoup.Jsoup;
import Server.Remove.*;

/**
 *
 * @author mrboy
 */
public class CityDetail {

    public static String CityDetail(String city1) throws IOException {
        String output = "";
        String city = Remove.up(city1);
        try {
            String wiki = "https://en.m.wikipedia.org/api/rest_v1/page/summary/" + city;
            String jsonwiki = Jsoup.connect(wiki).ignoreContentType(true).execute().body();
            JsonObject jsonObjectwiki = new JsonParser().parse(jsonwiki).getAsJsonObject();
            JsonObject fetchedJSON1 = jsonObjectwiki.getAsJsonObject();
            //ten
            String ten = fetchedJSON1.get("title").getAsString();
            //gioithieu
            String gioithieu = fetchedJSON1.get("extract").getAsString();

            String googledientich = Jsoup.connect("https://www.google.com/search?q=diện tích " + city)
                    .followRedirects(true)
                    .execute()
                    .url()
                    .toExternalForm();
            org.jsoup.nodes.Document docgoogledientich = Jsoup.connect(googledientich).get();
            //dientich
            String dientich = docgoogledientich.getElementsByClass("Z0LcW").get(0).text();

            String googlethoigian = Jsoup.connect("https://www.google.com/search?q= time " + city)
                    .followRedirects(true)
                    .execute()
                    .url()
                    .toExternalForm();
            org.jsoup.nodes.Document docgooglethoigian = Jsoup.connect(googlethoigian).get();
            //thoigian
            String thoigian = docgooglethoigian.getElementsByClass("vk_gy vk_sh card-section sL6Rbf").get(0).text();

            String openweather = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=f21cbac1202f29aaed2ed7f01698d4a0&units=metric";
            String jsonopenweather = Jsoup.connect(openweather).ignoreContentType(true).execute().body();
            JsonObject jsonObjectopenweather = new JsonParser().parse(jsonopenweather).getAsJsonObject();
            JsonObject coord = jsonObjectopenweather.get("coord").getAsJsonObject();
            //toado
            String toado = coord.get("lon").toString() + "°Bắc(N) - " + coord.get("lat").toString() + "°Đông(W)";
            JsonObject main = jsonObjectopenweather.get("main").getAsJsonObject();
            //nhietdo
            String nhietdo = main.get("temp").toString() + "°C|°F";
            //doam
            String doam = main.get("humidity").toString() + "%";
            JsonObject wind = jsonObjectopenweather.get("wind").getAsJsonObject();
            //tocdogio
            String tocdogio = wind.get("speed").toString() + " m/s";
            JsonObject fetchedJSON = jsonObjectopenweather.getAsJsonObject();
            JsonArray jarray = fetchedJSON.getAsJsonArray("weather");
            jsonObjectopenweather = jarray.get(0).getAsJsonObject();
            //thoitiet
            String thoitiet = jsonObjectopenweather.get("description").getAsString();
            output = ten + ";" + dientich + ";" + toado + ";" + nhietdo + ";" + doam + ";" + tocdogio + ";" + thoitiet + ";" + thoigian + ";" + gioithieu;
        } catch (Exception e) {
            output = "Không có thông tin !";
        }

        return output;
    }

}
