package converter.command;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MaxConvert {
    private final Scanner scanner = new Scanner(System.in);
    private final CharToNumberConverter converter = new CharToNumberConverter();

    public void command(BigInteger from, BigInteger to) {
        while (true) {
            System.out.println("Enter number in base "
                    + from + " to convert to base "
                    + to + " (To go back type /back) >");

            String input = scanner.nextLine();
            if (input.equals("/back")) {
                return;
            }
            if (input.contains(".")) {
                String[] splitInput = input.split("\\.");

                String[] splitInteger = splitInput[0].split("");
                converter.toNumbers(splitInteger);

                String[] splitDouble = splitInput[1].split("");
                converter.toNumbers(splitDouble);

                String resultInt = convertBigInteger(from, splitInteger).toString();
                String resultDouble = convertForDes(new BigDecimal(from), splitDouble);

                String ree = convertFromBigInteger(to, resultInt);
                String d = convertDesTo(to, "0." + resultDouble);
                System.out.println("Conversion result: "
                        + ree
                        + "." + d
                );
            } else {
                String resInt = convertBigInteger(from, input.split("")).toString();

                System.out.println("Conversion result: "
                        + convertFromBigInteger(to, resInt));
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

    private String convertFromBigInteger(BigInteger from, String splitInteger) {
        List<String> list = new ArrayList<>();
        BigInteger result = new BigInteger(splitInteger);

        while (result.longValue() > 0) {
            list.add(0,
                    converter.toLater(
                            result.longValue() % from.longValue()));
            result = result.divide(from);
        }
        StringBuilder sum = new StringBuilder();
        for (String s : list) {
            sum.append(s);
        }
        return sum.toString();
    }

    private String convertDesTo(BigInteger from, String splitDouble) {
        List<String> list = new ArrayList<>();
        BigDecimal result = new BigDecimal(splitDouble);
        int counter = 0;
        while (result.compareTo(BigDecimal.ZERO) > 0 && counter < 5) {
            result = result.multiply(new BigDecimal(from));
            list.add(converter.toLater(result.longValue()));
            result = result.remainder(BigDecimal.ONE);
            counter++;
        }
        StringBuilder sum = new StringBuilder();
        for (String s : list) {
            sum.append(s);
        }
        StringBuilder s = new StringBuilder(String.format(sum+"%5d",0).replace(' ','0')).delete(5,20);
        return s + "";
    }
}