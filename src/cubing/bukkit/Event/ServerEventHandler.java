package cubing.bukkit.Event;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ServerEventHandler {
    int priority() default 100;
}
