package cz.fi.muni.pa165.ddtroops.service.config;

import cz.fi.muni.pa165.ddtroops.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.ddtroops.dto.UserDTO;
import cz.fi.muni.pa165.ddtroops.entity.User;
import cz.fi.muni.pa165.ddtroops.service.facade.UserFacadeImpl;
import cz.fi.muni.pa165.ddtroops.service.services.impl.UserServiceImpl;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by pstanko.
 *
 * @author pstanko
 */
@Configuration
@Import(PersistenceSampleApplicationContext.class)
@ComponentScan(basePackageClasses = {UserServiceImpl.class, UserFacadeImpl.class})
public class ServiceConfiguration {


    @Bean
    public Mapper dozer() {
        DozerBeanMapper dozer = new DozerBeanMapper();
        dozer.addMapping(new EntitiesMapping());
        return dozer;
    }

    /**
     * Custom config for Dozer if needed
     *
     * @author nguyen
     */
    public class DozerCustomConfig extends BeanMappingBuilder {
        @Override
        protected void configure() {
            mapping(User.class, UserDTO.class);
        }
    }

}
