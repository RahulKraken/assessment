package com.kraken.assesment.Utils;

public class StringUtils {

    public static String formatString(String string) {
        string = string.trim();
        return string.replace(" ", "+");
    }
}
