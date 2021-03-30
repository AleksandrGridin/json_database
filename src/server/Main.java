package server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Main {
    private static String[] dataBase = new String[100];
    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;

    static {
        initDataBase();
    }

    public static void main(String[] args) throws IOException {

        System.out.println("Server started!");

        try (ServerSocket serverSocket = new ServerSocket(PORT, 50,
                InetAddress.getByName(ADDRESS))) {

            while (true) {
                try (Socket socket = serverSocket.accept();
                     DataInputStream dataIn = new DataInputStream(socket.getInputStream());
                     DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream())) {

                    String[] msgFromClient = dataIn.readUTF().split(" ");

                    if (msgFromClient[1].equals("exit")) {
                        break;
                    }

                    switch (msgFromClient[1]) {
                        case "set":
                            set(dataOut, msgFromClient);
                            break;
                        case "get":
                            get(dataOut, msgFromClient[3]);
                            break;
                        case "delete":
                            delete(dataOut, msgFromClient);
                            break;
                    }
                }
            }
        }
    }

    private static void delete(DataOutputStream dataOut, String[] msgFromClient) throws IOException {
        try {
            dataBase[Integer.parseInt(msgFromClient[3]) - 1] = "";
            dataOut.writeUTF("OK");
        } catch (IndexOutOfBoundsException e) {
            dataOut.writeUTF("ERROR");
        }
    }

    private static void get(DataOutputStream dataOut, String s) throws IOException {
        try {
            String dataFromBase = dataBase[Integer.parseInt(s) - 1];
            if (dataFromBase.equals("")) {
                dataOut.writeUTF("ERROR");
            } else {
                dataOut.writeUTF(dataFromBase);
            }
        } catch (IndexOutOfBoundsException e) {
            dataOut.writeUTF("ERROR");
        }
    }

    private static void set(DataOutputStream dataOut, String[] msgFromClient) throws IOException {
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 2; i < msgFromClient.length; i++) {
                sb.append(msgFromClient[i]).append(" ");
            }
            dataBase[Integer.parseInt(msgFromClient[3]) - 1] = sb.toString().trim();
            dataOut.writeUTF("OK");

        } catch (IndexOutOfBoundsException e) {
            dataOut.writeUTF("ERROR");
        }
    }


    private static void initDataBase() {
        Arrays.fill(dataBase, "");
    }
}
