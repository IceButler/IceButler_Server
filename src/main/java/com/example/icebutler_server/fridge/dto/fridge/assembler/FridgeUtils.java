package com.example.icebutler_server.fridge.dto.fridge.assembler;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class FridgeUtils {
    public static String calShelfLife(LocalDate shelfLife) {
        long day = Math.abs(ChronoUnit.DAYS.between(LocalDate.now(), shelfLife));
        String mark;
        if(shelfLife.isAfter(LocalDate.now())) mark = "-";
        else mark = "+";

        return "D"+mark+day;
    }

    public static double calPercentage(int val, int sum) {
        if(sum == 0) return 0.0;
        return Math.round(((double) val /sum) * 100.0)/100.0;
    }
}
