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
        // todo: 소수점 둘째까지 할지 아님 그냥 넘길지 프론트에 여쭤봄!
        if(sum == 0) return 0.0;
        return ((double) val /sum)*100.0;
    }
}
