package calcV2;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CalculatorV2 {
    
    private static final Map<Integer, String> ACTIONS = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);
    
    static {
        ACTIONS.put(1, "Сложение");
        ACTIONS.put(2, "Вычитание");
        ACTIONS.put(3, "Умножение");
        ACTIONS.put(4, "Деление");
    }

    public static void main(String[] args) {
        CalculatorV2 calculatorV2 = new CalculatorV2();
        
        calculatorV2.printActions();
        
        int action = calculatorV2.getAction();
        if (action == -1) {
            System.out.println("Завершение программы из-за некорректного действия.");
            calculatorV2.closeScanner();
            return;
        }
        
        double first_num = calculatorV2.getNum("Введите первое число: ");
        double second_num = calculatorV2.getNum("Введите второе число: ");
        
        try {
        	String result = calculatorV2.formatResult(calculatorV2.performAction(action, first_num, second_num));
        	System.out.printf("Результат: %s", result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        
        calculatorV2.closeScanner();
    }
    
    private void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }

    private int getAction() {
        System.out.print("Выберите действие: ");
        String input = scanner.nextLine();
        int action_key;
        
        try {
            action_key = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод. Пожалуйста, введите число.");
            return getAction();
        }
        
        if (!ACTIONS.containsKey(action_key)) {
            System.out.println("Действие не найдено.");
            return getAction();
        }
        
        return action_key;
    }
    
    private double getNum(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine();
        double num;
        
        try {
            num = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод. Пожалуйста, введите число.");
            return getNum(prompt);
        }
        
        return num;
    }
    
    private double performAction(int action, double first_num, double second_num) throws Exception {
        double result;
        switch (action) {
            case 1:
                result = sum(first_num, second_num);
                break;
            case 2:
                result = diff(first_num, second_num);
                break;
            case 3:
                result = mult(first_num, second_num);
                break;
            case 4:
                result = div(first_num, second_num);
                break;
            default:
                throw new Exception("Действие не поддерживается.");
        }
        
        return result;
    }
    
    private void printActions() {
        System.out.println("Выберите действие: ");
        ACTIONS.forEach((key, value) -> System.out.printf("%d. %s\n", key, value));
    }
    
    private static double sum(double first_num, double second_num) {
        return first_num + second_num;
    }
    
    private static double diff(double first_num, double second_num) {
        return first_num - second_num;
    }
    
    private static double mult(double first_num, double second_num) {
        return first_num * second_num;
    }
    
    private static double div(double first_num, double second_num) throws Exception {
        if (second_num == 0.0) {
            throw new Exception("Деление на ноль невозможно.");
        }
        return first_num / second_num;
    }
    
    private String formatResult(double result) {
        if (result == (long) result) {
            return String.format("%d", (long) result);
        }
        
        return String.format("%s", result);
    }
}
