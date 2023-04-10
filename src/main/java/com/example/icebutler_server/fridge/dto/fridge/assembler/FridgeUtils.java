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
}
