/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author hoalo
 */
public class LanguageLookup {

    private static Scanner scanner;
    private static File file = new File("./src/CodeLanguage.txt");

    public static String LanguageLookup(String code) throws FileNotFoundException {
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
                    if (code.contains(key)) {
                        result = value;
                        flag = 1;
                        return result;
                    }
                    if (code.contains(value)) {
                        result = key;
                        flag = 1;
                        return result;
                    }
                }
                if (flag == 0) {
                    result = "";
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
    /*
    public static void main(String[] args) throws FileNotFoundException {
        String language = LanguageLookup("vi");
        System.out.println(language);
    }
     */
}
