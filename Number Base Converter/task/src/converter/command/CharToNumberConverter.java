package converter.command;


import static java.util.Locale.ROOT;

public class CharToNumberConverter {

    public static final char MIN_CHAR = 'a';

    public void toNumbers(String[] splitInteger) {
        for (int i = 0; i < splitInteger.length; i++) {
            char temp = splitInteger[i].toLowerCase(ROOT).charAt(0);
            if (temp <= '9') {
                continue;
            }
            splitInteger[i] = (temp - MIN_CHAR + 10) + "";
        }
    }

    public String toLater(long num) {
        if (num > 9) {
            char temp = (char) ('a' + num - 10);
            return temp + "";
        } else {
            return num + "";
        }
    }
}
