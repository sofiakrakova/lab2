package main.java;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        MathEvaluator evaluator = new MathEvaluator();

        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();

        try {
            System.out.println(evaluator.evaluate(input));
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
