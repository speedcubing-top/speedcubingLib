package top.speedcubing.lib.eventbus;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class LibEventManager {
    private static final Map<Class<?>, List<Object>> Instances = new HashMap<>();
    private static final Map<Class<?>, List<Integer>> Priorities = new HashMap<>();
    private static final Map<Class<?>, List<Method>> Methods = new HashMap<>();

    public static void createNewEvents(Class<?>... classObjs) {
        for (Class<?> c : classObjs) {
            if (!Priorities.containsKey(c)) {
                Instances.put(c, new ArrayList<>());
                Priorities.put(c, new ArrayList<>());
                Methods.put(c, new ArrayList<>());
            }
        }
    }

    public static void registerListeners(Object... objects) {
        for (Object o : objects) {
            for (Method m : o.getClass().getDeclaredMethods()) {
                LibEventHandler handler = m.getAnnotation(LibEventHandler.class);
                if (m.getModifiers() == Modifier.PUBLIC && handler != null && m.getParameterTypes().length == 1) {
                    Class<?> c = m.getParameterTypes()[0];
                    createNewEvents(c);
                    int priority = handler.priority();
                    List<Integer> i = Priorities.get(c);
                    if (priority != 0 && i.contains(priority))
                        System.out.println("[speedcubingLib] Duplicated priority " + m + " " + o);
                    i.add(priority);
                    Collections.sort(i);
                    int index = i.indexOf(priority);
                    Methods.get(c).add(index, m);
                    Instances.get(c).add(index, o);
                }
            }
        }
    }

    public LibEventManager call() {
        try {
            Class<?> c = getClass();
            if (Instances.containsKey(c)) {
                List<Object> instances = Instances.get(c);
                List<Method> methods = Methods.get(c);
                int size = instances.size();
                for (int i = 0; i < size; i++) {
                    methods.get(i).invoke(instances.get(i), this);
                }
            }
            return this;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
