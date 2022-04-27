package speedcubing.lib.event;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface LibEventHandler {
    int priority() default 100;
}
