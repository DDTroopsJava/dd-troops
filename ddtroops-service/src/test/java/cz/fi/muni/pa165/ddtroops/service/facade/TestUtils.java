package cz.fi.muni.pa165.ddtroops.service.facade;

import java.util.HashSet;
import java.util.Set;

/**
 * @author pstanko
 */
public class TestUtils {
    static <T> Set<T> toSet(Iterable<T> iterable) {
        Set<T> result = new HashSet<>();
        for (T item : iterable) {
            result.add(item);
        }
        return result;
    }
}
