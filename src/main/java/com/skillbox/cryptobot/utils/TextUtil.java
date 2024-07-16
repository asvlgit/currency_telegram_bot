package com.skillbox.cryptobot.utils;

import java.math.BigDecimal;

public class TextUtil {

    public static String toString(double value) {
        return String.format("%.3f", value);
    }

    public static BigDecimal toDecimal(String value) {
        return new BigDecimal(value);
    }

}
