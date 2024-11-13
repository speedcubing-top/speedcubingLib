package top.speedcubing.lib.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtils {

    public static void setStaticField(Class<?> c, String name, Object value) {
        try {
            Field field = c.getDeclaredField(name);
            field.setAccessible(true);
            field.set(null, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void setField(Object obj, String name, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Object getField(Object obj, Class<?> clazz, String name) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getField(Object obj, String name) {
        return getField(obj, obj.getClass(), name);
    }

    public static Object getStaticField(Class<?> clazz, String name) {
        return getField(null, clazz, name);
    }

    public static void invoke(Object obj, String method, Object... args) {
        try {
            Method m = obj.getClass().getDeclaredMethod(method);
            m.setAccessible(true);
            m.invoke(obj, args);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void invokeStatic(Class<?> clazz, String method, Object... args) {
        try {
            Method m = clazz.getDeclaredMethod(method);
            m.setAccessible(true);
            m.invoke(null, args);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
