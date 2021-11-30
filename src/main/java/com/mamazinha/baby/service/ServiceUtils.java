package com.mamazinha.baby.service;

import java.math.BigDecimal;
import org.decimal4j.util.DoubleRounder;

public final class ServiceUtils {

    private ServiceUtils() {}

    public static Double maxTwoDecimalPlaces(Double value) {
        if (value % 1 == 0) {
            return value;
        } else if (new BigDecimal(Double.toString(value)).scale() > 2) {
            return DoubleRounder.round(value, 2);
        }
        return value;
    }
}
