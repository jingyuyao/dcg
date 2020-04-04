package com.dcg;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String userCommand = scanner.nextLine();
            if (userCommand.equals("quit")) {
                System.out.println("goodbye");
                break;
            }
        }
    }
}
