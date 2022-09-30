package top.speedcubing.lib.utils;

import java.lang.reflect.Field;

public class Reflections {

    public static void setField(Object obj, String name, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static Object getField(Object obj, String name) {
        try {
            Field field = (obj instanceof Class ? (Class<?>) obj : obj.getClass()).getDeclaredField(name);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
