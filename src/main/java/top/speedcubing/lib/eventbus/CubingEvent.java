package top.speedcubing.lib.eventbus;

import java.lang.reflect.*;
import java.util.List;

public class CubingEvent {

    public CubingEvent call() {
        try {
            Class<?> c = getClass();
            if (CubingEventManager.Instances.containsKey(c)) {
                List<Object> instances = CubingEventManager.Instances.get(c);
                List<Method> methods = CubingEventManager.Methods.get(c);
                int size = instances.size();
                for (int i = 0; i < size; i++) {
                    methods.get(i).invoke(instances.get(i), this);
                }
            }
            return this;
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
