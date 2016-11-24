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
    public RoleDTO createRole(RoleDTO role);
    
    /**
     * Find role by id
     * @param id role id
     * @return role by id
     */
    public RoleDTO findById(long id);
    
    /**
     * Find role by name
     * @param name role name
     * @return role by name
     */
    public RoleDTO findByName(String name);
    
    /**
     * Returns all roles
     * @return collection of roles
     */
    public Collection<RoleDTO> findAll();
    
    /**
     * Updates role
     * @param role updating role
     * @return updated role
     */
    public RoleDTO updateRole(RoleDTO role);
    
    /**
     * Deletes role
     * @param role role that will be removed
     * @return true if role is deleted, false otherwise
     */
    public Boolean deleteRole(RoleDTO role);
    
    /**
     * Delete all roles
     * @return true if all roles were deleted, false otherwise
     */
    public Boolean deleteAllRoles();

}
