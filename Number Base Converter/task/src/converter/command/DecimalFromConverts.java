package converter.command;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DecimalFromConverts {
    private final Scanner scanner = new Scanner(System.in);
    private final CharToNumberConverter converter = new CharToNumberConverter();

    public void command(BigInteger from) {
        while (true) {
            System.out.println("Enter number in base "
                    + 10 + " to convert to base "
                    + from + " (To go back type /back) ");

            String input = scanner.nextLine();
            if (input.equals("/back")) {
                return;
            }
            if (input.contains(".")) {
                String[] splitInput = input.split("\\.");
                String splitInt = splitInput[0];
                String splitDouble = splitInput[1];

                System.out.println("Conversion result: "
                        + convertBigInteger(from, splitInt)
                        + "."
                        + convertDesTo(from, "0." + splitDouble));
            } else {
                System.out.println("Conversion result: "
                        + convertBigInteger(from, input));
            }

        }
    }

    private String convertBigInteger(BigInteger from, String splitInteger) {
        List<String> list = new ArrayList<>();
        BigInteger result = new BigInteger(splitInteger);

        while (result.longValue() > 0) {
            list.add(0,
                    converter.toLater(
                            result.longValue() % from.longValue()));
            result = result.divide(from);
        }
        if (list.isEmpty()) {
            return "0";
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
        StringBuilder s = new StringBuilder(String.format(sum+"%5d",0).replace(' ','0')).delete(7,20);
        return s + "";
    }
}