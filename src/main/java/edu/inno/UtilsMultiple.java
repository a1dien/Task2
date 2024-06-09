package edu.inno;

import java.lang.reflect.Proxy;

public class UtilsMultiple {
    public static <T> T cache(T objectIncome) {
        return (T) Proxy.newProxyInstance(
                objectIncome.getClass().getClassLoader(),
                objectIncome.getClass().getInterfaces(),
                new MakeCacheFractionMultiple<>(objectIncome)
        );
    }
}
