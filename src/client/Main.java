package client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {

    private static final String address = "127.0.0.1";
    private static final int port = 23456;

    public static void main(String[] args) {
        System.out.println("Client started!");

            try (Socket socket = new Socket(InetAddress.getByName(address), port);
                 DataInputStream dataIn = new DataInputStream(socket.getInputStream());
                 DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream())
            ) {

                StringBuilder sb = new StringBuilder();

                for (String str : args) {
                    sb.append(str).append(" ");
                }

                dataOut.writeUTF(sb.toString().trim());
                System.out.println("Sent: " + sb.toString().replaceAll("-[a-z] ", "").trim());

                System.out.println("Received: " + dataIn.readUTF());

            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    }


