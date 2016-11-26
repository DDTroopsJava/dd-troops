package cz.fi.muni.pa165.ddtroops.service.services;

import cz.fi.muni.pa165.ddtroops.entity.Role;
import cz.fi.muni.pa165.ddtroops.service.exceptions.DDTroopsServiceException;

import java.util.List;

/**
 * Created by Petr Koláček
 *
 * @author Petr Koláček
 */
public interface RoleService {

    /**
     * Creates role
     *
     * @param role role that will be created
     */
    Role create(Role role) throws DDTroopsServiceException;

    /**
     * Gets role with unique id
     *
     * @param id role id
     * @return role wuth particular unique id
     */
    Role findById(Long id) throws DDTroopsServiceException;

    /**
     * Gets role with unique name.
     *
     * @param name role name
     * @return role with particular unique name
     */
    Role findByName(String name) throws DDTroopsServiceException;

    /**
     * Gets all roles
     *
     * @return collection of roles
     */
    List<Role> findAll() throws DDTroopsServiceException;

    /**
     * Updates existing role
     *
     * @param role role that will be updated
     */
    Role update(Role role) throws DDTroopsServiceException;

    /**
     * Removes particular role
     *
     * @param role role that will be deleted
     */
    void delete(Role role) throws DDTroopsServiceException;

}
