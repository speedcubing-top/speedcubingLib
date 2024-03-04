package top.speedcubing.lib.eventbus;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * class listener extends CubingEvent {
 *
 * @CubingEventHandler
 * public void a(MyEvent e){
 * //...
 * }
 * }
 * <p>
 * class MyEvent {
 * //...
 * }
 * <p>
 * //register listener
 * LibEventHandler.registerListeners(new listener(),...); //this will auto register MyEvent if MyEvent is not registered like below.
 * //register new event
 * LibEventHandler.createNewEvents(MyEvent.class,...);
 * }
 */
public class CubingEventManager {
    public static final Map<Class<?>, List<Object>> Instances = new HashMap<>();
    public static final Map<Class<?>, List<Integer>> Priorities = new HashMap<>();
    public static final Map<Class<?>, List<Method>> Methods = new HashMap<>();

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
                CubingEventHandler handler = m.getAnnotation(CubingEventHandler.class);
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
}
