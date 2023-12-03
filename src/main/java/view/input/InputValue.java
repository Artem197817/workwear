package view.input;

import java.util.Scanner;

public class InputValue {

    Scanner scanner = new Scanner((System.in));

    public String input(String message){
        System.out.println(message);
        return scanner.nextLine();
    }

    public Long inputLong(String message) {
        Long result;
        try {
            result = Long.parseLong(input(message));
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат числа. Введите число еще раз");
            result = inputLong(message);
        }
        return result;
    }
}
