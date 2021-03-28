package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static String[] dataBase = new String[100];
    static {
        initDataBase();
    }

    public static void main(String[] args) throws IOException {

        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
        String line = null;

        while (!(line = scanner.readLine()).equals("exit")) {

            String[] mass = line.split(" ");

            if (mass[0].equals("set")) {
                try {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 2; i < mass.length; i++) {
                        sb.append(mass[i] + " ");
                    }
                    dataBase[Integer.parseInt(mass[1]) - 1] = sb.toString().trim();
                    System.out.println("OK");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("ERROR");
                }
            } else if (mass[0].equals("get")) {
                try {
                String get = dataBase[Integer.parseInt(mass[1]) - 1];
                if (get.equals("")) {
                    System.out.println("ERROR");
                } else {
                    System.out.println(get);
                }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("ERROR");
                }
            } else if (mass[0].equals("delete")) {
                try {
                    dataBase[Integer.parseInt(mass[1]) - 1] = "";
                    System.out.println("OK");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("ERROR");
                }
            } else {
                break;
            }
        }
    }

    private static void initDataBase() {
        Arrays.fill(dataBase, "");
    }
}
