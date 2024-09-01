package top.speedcubing.lib.eventbus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class CubingEvent {

    public CubingEvent call() {
        Class<?> c = getClass();
        if (CubingEventManager.Instances.containsKey(c)) {
            List<Object> instances = CubingEventManager.Instances.get(c);
            List<Method> methods = CubingEventManager.Methods.get(c);
            int size = instances.size();
            for (int i = 0; i < size; i++) {
                try {
                    methods.get(i).invoke(instances.get(i), this);
                }  catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return this;
    }
}
