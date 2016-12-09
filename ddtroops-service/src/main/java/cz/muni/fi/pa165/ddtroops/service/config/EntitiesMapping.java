package cz.muni.fi.pa165.ddtroops.service.config;

import cz.muni.fi.pa165.ddtroops.dto.HeroDTO;
import cz.muni.fi.pa165.ddtroops.dto.RoleDTO;
import cz.muni.fi.pa165.ddtroops.dto.TroopDTO;
import cz.muni.fi.pa165.ddtroops.dto.UserDTO;
import cz.muni.fi.pa165.ddtroops.entity.Hero;
import cz.muni.fi.pa165.ddtroops.entity.Role;
import cz.muni.fi.pa165.ddtroops.entity.Troop;
import cz.muni.fi.pa165.ddtroops.entity.User;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;

/**
 * @author pstanko
 */
public class EntitiesMapping extends BeanMappingBuilder {

    @Override
    protected void configure() {
        mapping(User.class, UserDTO.class, TypeMappingOptions.mapNull(false));
        mapping(Hero.class, HeroDTO.class, TypeMappingOptions.mapNull(false))
                .fields(field("roles").accessible(true), field("roles").accessible(true));
        mapping(Troop.class, TroopDTO.class, TypeMappingOptions.mapNull(false))
                .fields(field("heroes").accessible(true), field("heroes").accessible(true));
        mapping(Role.class, RoleDTO.class, TypeMappingOptions.mapNull(false));
    }
}
