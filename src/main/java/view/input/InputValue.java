package view.input;

import java.util.Scanner;

public class InputValue {

    Scanner scanner = new Scanner((System.in));

    public String input(String message){
        System.out.println(message);
        return scanner.nextLine();
    }
}
