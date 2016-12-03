package cz.fi.muni.pa165.ddtroops.facade;

import cz.fi.muni.pa165.ddtroops.dto.RoleDTO;

import java.util.Collection;

/**
 * Created by Petr Kolacek
 *
 * @author Petr Kolacek
 */
public interface RoleFacade {

    /**
     * Creates role
     *
     * @param role - created role
     * @return new role with id
     */
    RoleDTO create(RoleDTO role);

    /**
     * Find role by id
     *
     * @param id role id
     * @return role by id
     */
    RoleDTO findById(long id);

    /**
     * Find role by name
     *
     * @param name role name
     * @return role by name
     */
    RoleDTO findByName(String name);

    /**
     * Returns all roles
     *
     * @return collection of roles
     */
    Collection<RoleDTO> findAll();

    /**
     * Updates role
     *
     * @param role updating role
     * @return updated role
     */
    RoleDTO update(RoleDTO role);

    /**
     * Deletes role
     *
     * @param id role that will be removed
     */
    void delete(Long id);


}
