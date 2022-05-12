package Server;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

/**
 *
 * @author mrboy
 */
public class CountryDetail {

    public static String CountryDetail(String country) throws FileNotFoundException, IOException, JSONException {
        String result = "";
        try {

            String codeCountry = CountryLookup.CountryLookup(country);

            Connection.Response response = Jsoup.connect("http://api.geonames.org/countryInfoJSON?username=ngocthang&country=" + codeCountry)
                    .method(Connection.Method.GET).ignoreContentType(true).execute();

            JSONObject countrydetail = new JSONObject(response.body());
            JSONArray arr = countrydetail.getJSONArray("geonames");
            for (int i = 0; i < arr.length(); i++) {
                String countryName = arr.getJSONObject(i).getString("countryName");
                System.out.println("Tên quốc gia: " + countryName);
                String capital = arr.getJSONObject(i).getString("capital");
                System.out.println("Thủ đô: " + capital);
                String languages1 = arr.getJSONObject(i).getString("languages");
                String[] l = languages1.split(",");
                String languages2 = l[0];
                String languages = LanguageLookup.LanguageLookup(languages2);
                System.out.println("Ngôn ngữ: " + languages);
                String continentName = arr.getJSONObject(i).getString("continentName");
                System.out.println("Tên lục địa: " + continentName);
                String currencyCode = arr.getJSONObject(i).getString("currencyCode");
                System.out.println("Mã tiền tệ: " + currencyCode);
                double south = arr.getJSONObject(i).getDouble("south");
                double north = arr.getJSONObject(i).getDouble("north");
                double east = arr.getJSONObject(i).getDouble("east");
                double west = arr.getJSONObject(i).getDouble("west");
                System.out.println("Tọa độ: " + "cực Nam: " + south + "Nam" + ", cực Bắc: " + north + "Bắc" + ", cực Đông: " + east + "Đông" + ", cực Tây: " + west + "Tây");
                result = countryName + ";" + capital + ";" + languages + ";" + continentName + ";" + currencyCode + ";" + south + " Nam" + ", " + north + " Bắc" + ", " + east + " Đông" + ", " + west + " Tây" + ";";
            }

            // Tao format
            Locale localeVN = new Locale("vi", "VN");
            NumberFormat vn = NumberFormat.getInstance(localeVN);

            //Quốc kỳ
            String quocky = "https://restcountries.com/v3.1/name/" + country + "?fullText=true";
            String jsquocky = Jsoup.connect(quocky).ignoreContentType(true).execute().body();
            JsonArray he = new JsonParser().parse(jsquocky).getAsJsonArray();
            JsonObject JS = he.get(0).getAsJsonObject();
            JsonObject flags = JS.get("flags").getAsJsonObject();
            String png = flags.get("png").getAsString();
            System.out.println("Quốc kỳ: " + png);

            Connection.Response response1 = Jsoup.connect("https://restcountries.com/v3.1/name/" + country + "?fullText=true")
                    .method(Connection.Method.GET).ignoreContentType(true).execute();
            JSONArray detailpeople = new JSONArray(response1.body());
            String result1 = "";
            for (int i = 0; i < detailpeople.length(); i++) {
                int population = detailpeople.getJSONObject(i).getInt("population");
                String populationf = vn.format(population);
                System.out.println("Dân số: : " + populationf);
                int area = detailpeople.getJSONObject(i).getInt("area");
                String areaf = vn.format(area);
                System.out.println("Diện tích: " + areaf + " km2");
                result1 = populationf + ";" + areaf + " km2" + ";" + png;
            }
            result = result + result1;
            //http://api.geonames.org/neighboursJSON?country=VN&username=ngocthang
            Connection.Response response2 = Jsoup.connect("http://api.geonames.org/neighboursJSON?username=ngocthang&country=" + codeCountry)
                    .method(Connection.Method.GET).ignoreContentType(true).execute();

            JSONObject neighbours = new JSONObject(response2.body());
            JSONArray arr2 = neighbours.getJSONArray("geonames");
            String countryneighbours = "";
            for (int i = 0; i < arr2.length(); i++) {
                String countryneighbours1 = arr2.getJSONObject(i).getString("countryName");
                if (i == arr2.length() - 1) {
                    countryneighbours += countryneighbours1;
                } else {
                    countryneighbours += countryneighbours1 + ", ";
                }
            }
            System.out.println("Tiếp giáp: " + countryneighbours);
            if (!countryneighbours.equals("")) {
                result = result + ";" + countryneighbours;
            } else {
                result = result + ";" + "Không có quốc gia tiếp giáp";
            }
        } catch (IOException e) {
            System.out.println("Không có thông tin !");
            result = "Không có thông tin !";
        }
        return result;
    }
    /*
    public static void main(String[] args) throws IOException, FileNotFoundException, JSONException {
        CountryDetail("Vietnam");

    }
     */
}
