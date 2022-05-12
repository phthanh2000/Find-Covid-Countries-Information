package Server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class CountryLookup {

    private static Scanner scanner;
    private static File file = new File("./src/CodeCountry.txt");

    public static String CountryLookup(String country) throws FileNotFoundException {
        String result = null;
        Scanner sc = null;
        sc = new Scanner(file);
        HashMap<String, String> map = new HashMap<>();
        try {
            int flag = 0;
            do {
                String key = null;
                String value = null;
                while (sc.hasNextLine()) {
                    StringTokenizer st = new StringTokenizer(sc.nextLine(), ":");
                    String str1 = st.nextToken();
                    String str2 = st.nextToken();
                    map.putIfAbsent(str1, str2);
                }
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    key = entry.getKey();
                    value = entry.getValue();
                    if (key.equalsIgnoreCase(country)) {
                        result = value;
                        flag = 1;
                        return result;
                    }
                    if (value.equalsIgnoreCase(country)) {
                        result = key;
                        flag = 1;
                        return result;
                    }
                }
                if (flag == 0) {
                    result = "Quốc gia bạn vừa nhập không hợp lệ";
                    return result;
                }
            } while (flag == 0);

        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            sc.close();
        }
        return result;
    }

    //   public static void main(String[] agrs) throws FileNotFoundException {
    //       System.out.println(CountryLookup("Vietnam"));
    //   }
}
