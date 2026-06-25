package com.liverpool.orders.infrastructure.utils;

import java.lang.reflect.Field;

public class Utils {
    public static void asasa(Object object) throws IllegalAccessException {
        Field[] fields = object.getClass().getDeclaredFields();
        fields[0].getName();
        for(Field f : fields) {
            Class t = f.getType();
            Object v = f.get(object);
            System.out.println("F: " + t + ", " + f.getName() + ", " + v + "");
        }
    }
}
