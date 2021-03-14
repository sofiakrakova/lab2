package main.java;

import java.util.*;
import java.util.stream.Collectors;

public class MathEvaluator {

    private static final List<String> operations = List.of("(", ")", "*", "+", "-", "/", "^");

    /**
     * Подсчет выражение математического выражение
     * @param expression математическое выражение
     * @return результат выражения
     * @throws Exception ошибки связанные с невалидным математическим выражением
     */
    public double evaluate(String expression) throws Exception {

        expression = String.format("(%s)", expression);
        List<String> elements = parseExpression(expression);

        Stack<Double> numbers = new Stack<>();
        Stack<String> functions = new Stack<>();

        for (String element : elements) {

            if (isNumber(element)) {
                numbers.push(Double.parseDouble(element));
            } else if (isOperation(element)) {
                if (")".equals(element)) {
                    while (!functions.isEmpty() && !"(".equals(functions.peek())) {
                        if (numbers.size() < 2)
                            throw new Exception();
                        double b = numbers.pop();
                        double a = numbers.pop();

                        String operation = functions.pop();

                        numbers.push(performOperation(a, b, operation));
                    }

                    if (functions.isEmpty())
                        throw new Exception("Open bracket was expected");
                    functions.pop();
                } else {
                    while (canPushOut(element, functions)) {
                        if (numbers.size() < 2)
                            throw new Exception();
                        double b = numbers.pop();
                        double a = numbers.pop();

                        String operation = functions.pop();

                        numbers.push(performOperation(a, b, operation));
                    }

                    functions.push(element);
                }
            } else
                throw new Exception("Unexpected element '" + element + "'");
        }

        if (numbers.size() > 1 || functions.size() > 0)
            throw new Exception("Bad input");

        return numbers.peek();
    }

    /**
     *
     * @param operation операция для проверки
     * @param operations стек с операциями
     * @return true, если operation может вытолкнуть операцию в вершине стека, false иначе
     * @throws Exception
     */
    public boolean canPushOut(String operation, Stack<String> operations) throws Exception {
        if (operations.isEmpty())
            return false;

        int firstPriority = getPriority(operation);
        int secondPriority = getPriority(operations.peek());

        return secondPriority >= 0 && firstPriority >= secondPriority;
    }


    /**
     * Получение приоритета операции
     * @param operation знак операции
     * @return приоритет операции
     * @throws Exception неподдерживаемая операция
     */
    private int getPriority(String operation) throws Exception {
        switch (operation) {
            case "(":
                return -1;
            case "*":
            case "/":
            case "^":
                return 1;
            case "+":
            case "-":
                return 2;
            default:
                throw new Exception("Unkown operation");
        }
    }

    /**
     * Выполнение заданной операции
     * @param a первое число
     * @param b второе число
     * @param operation знак операции
     * @return результат операции
     * @throws Exception неподдерживаемая операция
     */
    private double performOperation(double a, double b, String operation) throws Exception {
        switch (operation) {
            case "*":
                return a * b;
            case "/":
                return a / b;
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "^":
                return Math.pow(a, b);
        }

        throw new Exception("Unsupported operation '" + operation + "'");
    }

    /**
     * Разделение строки на элементы
     * @param expression исходное выражение
     * @return список элементов в исходном порядке
     */
    private List<String> parseExpression(String expression) {
        List<String> result = new ArrayList<>();

        for (String operation : operations) {
            expression = expression.replace(operation, " " + operation + " ");
        }

        expression = expression.replaceAll("\\s+", " ");
        for (Object operation : operations.stream().filter(it->!it.equals(")")).collect(Collectors.toList())) {
            expression = expression.replace(operation + " - ", operation + " -");
        }

        Scanner scanner = new Scanner(expression);
        while (scanner.hasNext()) {
            result.add(scanner.next());
        }

        return result;
    }

    /**
     * Проверяем является ли строка числом
     * @param str проверяемая строка
     * @return является ли числом
     */
    private boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Проверяем является ли строка знаком операции
     * @param str проверяемая строка
     * @return является ли знаком операции
     */
    private boolean isOperation(String str) {
        return operations.contains(str);
    }
}
