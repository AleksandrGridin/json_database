package client;

import com.google.gson.Gson;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {

    private static final String address = "127.0.0.1";
    private static final int port = 23456;

    public static void main(String[] args) {
        System.out.println("Client started!");

            try (Socket socket = new Socket(InetAddress.getByName(address), port);
                 DataInputStream dataIn = new DataInputStream(socket.getInputStream());
                 DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream())
            ) {

                Gson gson = new Gson();
                String toServer = gson.toJson(changeToGson(args));

                dataOut.writeUTF(toServer);
                System.out.println("Sent: " + toServer);

                System.out.println("Received: " + dataIn.readUTF());

            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private static Map<String, String> changeToGson(String[] args) {

        Map<String, String> map = new LinkedHashMap<>();

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-t")) {
                map.put("type", args[i + 1]);
            }
            if (args[i].equals("-k")) {
                map.put("key", args[i + 1]);
            }
            if (args[i].equals("-v")) {
                map.put("value", args[i + 1]);
            }
        }
        return map;
    }
}



