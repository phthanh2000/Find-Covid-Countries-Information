package Server;

import java.io.IOException;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class CovidDetail {

    public static String CovidDetail(String country, String yesterday, String today) throws IOException, JSONException {
        //https://api.covid19api.com/country/vietnam?from=2021-12-03T00:00:00Z&to=2021-12-04T00:00:00Z
        String result = "";
        try {
            Connection.Response response = Jsoup.connect("https://api.covid19api.com/country/" + country + "?from=" + yesterday + "T00:00:00Z&to=" + today + "T00:00:00Z")
                    .method(Connection.Method.GET).ignoreContentType(true).execute();

            JSONArray arrCovid = new JSONArray(response.body());

            int yesterdayConfirmed = arrCovid.getJSONObject(0).getInt("Confirmed");
            int todayConfirmed = arrCovid.getJSONObject((arrCovid.length() - 1)).getInt("Confirmed");
            int Confirmed = Math.abs(todayConfirmed - yesterdayConfirmed);
            System.out.println("Số ca nhiễm từ ngày " + yesterday + " đến ngày " + today + " là: " + Confirmed);

            int yesterdayRecovered = arrCovid.getJSONObject(0).getInt("Recovered");
            int todayRecovered = arrCovid.getJSONObject((arrCovid.length() - 1)).getInt("Recovered");
            int Recovered = Math.abs(todayRecovered - yesterdayRecovered);
            System.out.println("Số ca bình phục từ ngày " + yesterday + " đến ngày " + today + " là: " + Recovered);

            int yesterdayDeaths = arrCovid.getJSONObject(0).getInt("Deaths");
            int todayDeaths = arrCovid.getJSONObject((arrCovid.length() - 1)).getInt("Deaths");
            int Deaths = Math.abs(todayDeaths - yesterdayDeaths);
            System.out.println("Số ca tử vong trong ngày " + yesterday + " đến ngày " + today + " là: " + Deaths);
            result = Confirmed + ";" + Recovered + ";" + Deaths;
        } catch (Exception e) {
            System.out.println("Không có thông tin !");
        }
        return result;
    }
}
