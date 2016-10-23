package cz.fi.muni.pa165.ddtroops.service;

import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * @author pstanko
 */
public interface BeanMappingService {

    public  <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    public  <T> T mapTo(Object u, Class<T> mapToClass);
    public Mapper getMapper();
}
