package speedcubing.lib.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

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
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static void copyParentFields(Object o, Object parent) {
        try {
            for (Field f : o.getClass().getSuperclass().getDeclaredFields()) {
                if (!Modifier.isStatic(f.getModifiers())) {
                    f.setAccessible(true);
                    f.set(o, parent.getClass().getDeclaredField(f.getName()).get(parent));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
