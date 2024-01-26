package view.input;

import demo.workwear.model.modelEnum.WorkShoesType;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class InputValue {

    Scanner scanner = new Scanner((System.in));

    public String input(String message) {
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

    public Integer inputInt(String message) {
        int result;
        try {
            result = Integer.parseInt(input(message));
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат числа. Введите число еще раз");
            result = inputInt(message);
        }
        return result;
    }

    public String inputCompany(String message) {
        System.out.println(message);
        String[] companyArray = {"АО \"Катод\"", "ООО \"Катод\""};
        return  (String) JOptionPane.showInputDialog(null, "Выберите компанию", "Компания"
                , JOptionPane.INFORMATION_MESSAGE, null, companyArray, companyArray[0]);
    }

     public String inputEnum (String message, Class<?> clazz) {
        try {
            Method method = clazz.getDeclaredMethod("getTypeArray") ;
            String [] arr = (String[]) method.invoke(null);
            return  (String) JOptionPane.showInputDialog(null, message, message
                    , JOptionPane.INFORMATION_MESSAGE, null, arr, arr[0]);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}