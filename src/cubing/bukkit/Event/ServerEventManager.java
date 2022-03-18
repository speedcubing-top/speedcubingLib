package cubing.bukkit.Event;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerEventManager {
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

    public static Object callEvent(Object event) {
        try {
            Class<?> c = event.getClass();
            if (Instances.containsKey(c)) {
                List<Object> instances = Instances.get(c);
                List<Method> methods = Methods.get(c);
                int size = instances.size();
                for (int i = 0; i < size; i++) {
                    methods.get(i).invoke(instances.get(i), event);
                }
            }
            return event;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static void registerListeners(Object... objects) {
        for (Object o : objects) {
            for (Method m : o.getClass().getDeclaredMethods()) {
                ServerEventHandler handler = m.getAnnotation(ServerEventHandler.class);
                if (m.getModifiers() == Modifier.PUBLIC && handler != null) {
                    Parameter[] parameters = m.getParameters();
                    if (parameters.length == 1) {
                        Class<?> c = parameters[0].getType();
                        createNewEvents(c);
                        int priority = handler.priority();
                        Priorities.get(c).add(priority);
                        Priorities.get(c).sort(Integer::compareTo);
                        int index = Priorities.get(c).indexOf(priority);
                        Methods.get(c).add(index, m);
                        Instances.get(c).add(index, o);
                    }
                }
            }
        }
    }
}
