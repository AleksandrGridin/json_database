package server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

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


                    try (Socket socket = serverSocket.accept();
                         DataInputStream dataIn = new DataInputStream(socket.getInputStream());
                         DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
                         Scanner sc = new Scanner(System.in);) {


                        int n = 12;
                        System.out.print("Received: Give me a record # " + n + "\n");
                        dataOut.writeUTF(String.valueOf(n));
                        String number = dataIn.readUTF();
                        System.out.println("Sent: A record # " + number + " was sent!");

                    }

                }
        }




//        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
//        String line = null;
//
//        while (!(line = scanner.readLine()).equals("exit")) {
//
//            String[] mass = line.split(" ");
//
//            if (mass[0].equals("set")) {
//                try {
//                    StringBuilder sb = new StringBuilder();
//                    for (int i = 2; i < mass.length; i++) {
//                        sb.append(mass[i] + " ");
//                    }
//                    dataBase[Integer.parseInt(mass[1]) - 1] = sb.toString().trim();
//                    System.out.println("OK");
//                } catch (IndexOutOfBoundsException e) {
//                    System.out.println("ERROR");
//                }
//            } else if (mass[0].equals("get")) {
//                try {
//                String get = dataBase[Integer.parseInt(mass[1]) - 1];
//                if (get.equals("")) {
//                    System.out.println("ERROR");
//                } else {
//                    System.out.println(get);
//                }
//                } catch (IndexOutOfBoundsException e) {
//                    System.out.println("ERROR");
//                }
//            } else if (mass[0].equals("delete")) {
//                try {
//                    dataBase[Integer.parseInt(mass[1]) - 1] = "";
//                    System.out.println("OK");
//                } catch (IndexOutOfBoundsException e) {
//                    System.out.println("ERROR");
//                }
//            } else {
//                break;
//            }
//        }


    private static void initDataBase() {
        Arrays.fill(dataBase, "");
    }
}
