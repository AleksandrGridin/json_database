package server;

import com.google.gson.Gson;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    private static Map<String, String> dataBase = new HashMap<>();
    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;
    private static Map<String, String> map = new LinkedHashMap<>();

    public static void main(String[] args) throws IOException {

        System.out.println("Server started!");

        try (ServerSocket serverSocket = new ServerSocket(PORT, 50,
                InetAddress.getByName(ADDRESS))) {

            while (true) {
                try (Socket socket = serverSocket.accept();
                     DataInputStream dataIn = new DataInputStream(socket.getInputStream());
                     DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream())) {

                    Gson gson = new Gson();


                    String[] msgFromClient = dataIn.readUTF()
                            .replaceAll("[{}\"]", "")
                            .replaceAll(":", " ")
                            .replaceAll(",", " ")
                            .split(" ");


                    if (msgFromClient[1].equals("exit")) {
                        map.put("response","OK");

                        dataOut.writeUTF(gson.toJson(map));
                        map.clear();
                        break;
                    }

                    switch (msgFromClient[1]) {
                        case "set":
                            set(msgFromClient);
                            break;
                        case "get":
                            get(msgFromClient[3]);
                            break;
                        case "delete":
                            delete(msgFromClient);
                            break;

                    }
                    String toClient = gson.toJson(map);
                    dataOut.writeUTF(toClient);
                    map.clear();
                }
            }
        }
    }

    private static void delete(String[] msgFromClient) throws IOException {
        try {
            if (dataBase.get(msgFromClient[3]).equals("")) {
                map.put("response", "ERROR");
                map.put("reason", "No such key");
            } else {
                dataBase.remove(msgFromClient[3]);
                map.put("response", "OK");
            }
        } catch (Exception e) {
            map.put("response", "ERROR");
            map.put("reason", "No such key");
        }
    }

    private static void get(String s) throws IOException {
        try {
            String fromBase = dataBase.getOrDefault(s, "");
            if (fromBase.equals("")) {
                map.put("response","ERROR");
                map.put("reason", "No such key");
            } else {
                map.put("response", "OK");
                map.put("value", fromBase);
            }
        } catch (IndexOutOfBoundsException e) {
            map.put("response","ERROR");
            map.put("reason", "No such key");
        }
    }

    private static void set(String[] msgFromClient) throws IOException {

            try {
            StringBuilder sb = new StringBuilder();
            for (int i = 5; i < msgFromClient.length; i++) {
                sb.append(msgFromClient[i]).append(" ");
            }
            dataBase.put(msgFromClient[3], sb.toString().trim());
            map.put("response", "OK");
        } catch (IndexOutOfBoundsException e) {
            map.put("response","ERROR");
            map.put("reason","No such key");
        }
    }
}
