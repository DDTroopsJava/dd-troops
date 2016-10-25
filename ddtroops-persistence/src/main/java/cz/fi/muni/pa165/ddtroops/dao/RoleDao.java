package cz.fi.muni.pa165.ddtroops.dao;

import cz.fi.muni.pa165.ddtroops.entity.Role;

import java.util.List;

/**
 * @author P. Kolacek
 */

public interface RoleDao {

    /**
     * Create new role record
     * @param role - instance of role
     * @return Role
     */
    public Role create(Role role);
    
    /**
     * Delete role
     * @param role - instance of role
     * @return Role
     */
    public Role delete(Role role);
    
    /**
     * Update role
     * @param role - instance of role
     * @return Role
     */
    public Role update(Role role);
    
    /**
     * Get role by id
     * @param id - Role id
     * @return Role if exists, null if not
     */
    public Role findById(long id);
    
    /**
     * Get role by name
     * @param name - Role name
     * @return Role if exists, null if not
     */
    
    public Role findByName(String name);
    
    /**
     * Get all roles
     * @return List of roles
     */
    public List<Role> listAll();
}
