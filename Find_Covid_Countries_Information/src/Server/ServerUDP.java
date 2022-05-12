/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import static Server.CityDetail.CityDetail;
import static Server.CountryDetail.CountryDetail;
import static Server.CovidDetail.CovidDetail;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;

import org.json.JSONException;

import data.AES;
import data.Connect.ServerReceive;
import data.RSA;

public class ServerUDP {

    public static int port = 1234;
    public static HashMap<String, AES> mapKey;

    public static void main(String[] args) throws FileNotFoundException, JSONException {
        DatagramSocket socket;
        RSA rsa = new RSA();
        mapKey = new HashMap<>();
        InetAddress add = null;
        int portClient = 0;
        try {
            socket = new DatagramSocket(port);
            System.out.println("Server is starting...");
            while (true) {
                //Nhận kết nối từ client;
                ServerReceive receive = data.Connect.receiveServer(socket, add, portClient);
                add = receive.getAdd();
                portClient = receive.getPort();
                String key = receive.getAdd().getHostAddress() + ":" + receive.getPort();
                //Giải mã tin nhắn (nếu đã có key)
                String tmp = recieveString(key, receive.getMsg());
                System.out.println("Server received: " + tmp + " from "
                        + receive.getAdd().getHostAddress() + " at port "
                        + receive.getPort());
                String[] list = tmp.split(";");
                if (tmp.contentEquals("bye")) {
                    System.out.println(key + " close socket.");
                    mapKey.remove(key);
                } else if (tmp.contentEquals("hello") || receive.getMsg().contentEquals("hello")) {
                    System.out.println("Send RSA to client");
                    data.Connect.send(rsa.getPublicKey(), socket, receive.getAdd(), receive.getPort());
                    System.out.println("Receive AES from client");
                    ServerReceive temp = data.Connect.receiveServer(socket, add, portClient);
                    //Giải mã AES client gửi cho.
                    AES aes = new AES(rsa.decrypt(temp.getMsg()));
                    mapKey.put(key, aes);
                    System.out.println("Complete send and receive key... From " + key);
                } // Dành cho quốc gia
                else if (list[0].equalsIgnoreCase("2")) {
                    String result = CountryDetail(list[1]);
                    System.out.println(result);
                    send(key, result, socket, add, portClient);

                } // Dành cho Covid
                else if (list[0].equalsIgnoreCase("3")) {
                    String result = CovidDetail(list[1], list[2], list[3]);
                    System.out.println(result);
                    send(key, result, socket, add, portClient);
                } else {
                    String result = CityDetail(tmp);
                    System.out.println(result);
                    send(key, result, socket, add, portClient);
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static void send(String key, String result, DatagramSocket socket, InetAddress address, int port) {
        if (mapKey.get(key) != null) {
            data.Connect.send(mapKey.get(key).encrypt(result), socket, address, port);
        } else {
            data.Connect.send(null, socket, address, port);
        }
    }

    public static String recieveString(String key, String result) {
        if (mapKey.get(key) != null) {
            return mapKey.get(key).decrypt(result);
        } else {
            return result;
        }
    }
}
