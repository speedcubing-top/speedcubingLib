package top.speedcubing.lib.utils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class MorseCode {

    //example: textToMorse("hello") = ".... . .-.. .-.. ---"
    public static String textToMorse(String text) {
        StringBuilder builder = new StringBuilder();
        int a;
        for (char c : Pattern.compile(" +").matcher(text.trim()).replaceAll(" ").toCharArray()) {
            if (c == 32) {
                builder.append("/ ");
                continue;
            } else if (c >= 65 && c <= 90) //A-Z
                a = c - 65;
            else if (c >= 48 && c <= 57) //0-9
                a = c - 22;
            else if (c >= 97 && c <= 122) //a-z
                a = c - 97;
            else throw new IllegalArgumentException("Invalid character found.");
            builder.append(codes.get(a)).append(" ");
        }
        return builder.substring(0, builder.length() - 1);
    }

    //example: morseToText(".... . .-.. .-.. ---") = "HELLO"
    public static String morseToText(String code) {
        StringBuilder builder = new StringBuilder();
        int b;
        for (String s : code.trim().split("( +)/( +)")) {
            for (String s2 : s.split(" +")) {
                b = codes.indexOf(s2);
                if (0 <= b && b <= 25)
                    builder.append((char) (b + 65));
                else if (26 <= b && b <= 35)
                    builder.append((char) (b + 22));
                else throw new IllegalArgumentException("Invalid character found.");
            }
            builder.append(" ");
        }
        return builder.substring(0, builder.length() - 1);
    }

    private static final List<String> codes = Arrays.asList(".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---",
            ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--..", ".----", "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.", "-----");
}
