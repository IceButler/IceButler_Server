package com.example.icebutler_server.fridge.dto.fridge.assembler;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class FridgeUtils {
    public static int calShelfLife(LocalDate shelfLife) {
        return (int) (-1 * ChronoUnit.DAYS.between(LocalDate.now(), shelfLife));
    }

    public static double calPercentage(int val, int sum) {
        if(sum == 0) return 0.0;
        return Math.round(((double) val /sum) * 100.0)/100.0;
    }
}
