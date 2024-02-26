package top.speedcubing.lib.utils.collection;

import java.util.Collections;
import java.util.HashSet;

public class Sets {
    public static <E> java.util.HashSet<E> hashSet(E... elements) {
        java.util.HashSet<E> set = new HashSet<>();
        Collections.addAll(set, elements);
        return set;
    }
}
