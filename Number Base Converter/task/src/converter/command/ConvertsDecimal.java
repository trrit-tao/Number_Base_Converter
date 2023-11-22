package converter.command;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.Scanner;


public class ConvertsDecimal {
    private final Scanner scanner = new Scanner(System.in);
    private final CharToNumberConverter converter = new CharToNumberConverter();

    public void command(BigInteger from) {
        while (true) {
            System.out.println("Enter number in base "
                    + from + " to convert to base "
                    + 10 + " (To go back type /back) >");

            String input = scanner.nextLine();
            if (input.equals("/back")) {
                return;
            }
            if (input.contains(".")) {
                String[] splitDecimal = input.split("\\.");

                String[] splitInteger = splitDecimal[0].split("");
                converter.toNumbers(splitInteger);

                String[] splitDouble = splitDecimal[1].split("");
                converter.toNumbers(splitDouble);

                System.out.println("Conversion to decimal result: "
                        + convertBigInteger(from, splitInteger)
                        + "." + convertForDes(new BigDecimal(from), splitDouble)
                        + "\n");
            } else {
                System.out.println("Conversion to decimal result: "
                        + convertBigInteger(from, input.split("")));
            }
        }
    }

    private BigInteger convertBigInteger(BigInteger from, String[] splitInteger) {
        BigInteger result = BigInteger.ZERO;
        converter.toNumbers(splitInteger);
        for (int i = 0; i < splitInteger.length; i++) {
            result = result
                    .add(new BigInteger(splitInteger[i])
                            .multiply(from.pow(splitInteger.length - i - 1)));
        }
        return result;
    }

    private String convertForDes(BigDecimal from, String[] splitInteger) {
        BigDecimal result = BigDecimal.ZERO;
        converter.toNumbers(splitInteger);
        for (int i = 0; i < splitInteger.length; i++) {
            result = result.add(
                    new BigDecimal(splitInteger[i]).multiply(from.pow(-(i + 1), MathContext.DECIMAL32))
            );
        }
        return new DecimalFormat("#.00000")
                .format(result).substring(1);
    }
}
