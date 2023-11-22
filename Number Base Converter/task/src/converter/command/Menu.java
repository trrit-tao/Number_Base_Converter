package converter.command;

import java.math.BigInteger;
import java.util.Scanner;

public class Menu {
    DecimalFromConverts decimal = new DecimalFromConverts();
    ConvertsDecimal convertsDecimal = new ConvertsDecimal();
    MaxConvert maxConvert = new MaxConvert();
    Scanner scanner = new Scanner(System.in);
    String[] numFromTo;

    public void start() {
        while (true) {
            System.out.println("Enter two numbers in format: {source base} {target base} (To quit type /exit) ");
            String input = scanner.nextLine().trim();
            if (input.equals("/exit")) {
                new Exit().command();
                break;
            }
            numFromTo = input.split(" ");
            try {
                if (Integer.parseInt(numFromTo[0]) == 10) {
                    decimal.command(new BigInteger(numFromTo[1]));
                } else if (Integer.parseInt(numFromTo[1]) == 10) {
                    convertsDecimal.command(new BigInteger(numFromTo[0]));
                } else {
                    maxConvert.command(new BigInteger(numFromTo[0]), new BigInteger(numFromTo[1]));
                }
            } catch (Exception e) {
                System.out.println("Не коректный ввод");
                System.out.println(e);
            }
        }
    }
}
