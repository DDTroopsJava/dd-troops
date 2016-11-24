package cz.fi.muni.pa165.ddtroops.dao;

import cz.fi.muni.pa165.ddtroops.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author P. Kolacek
 */

public interface RoleDao extends JpaRepository<Role, Long> {
    
    /**
     * Get role by name
     * @param name - Role name
     * @return Role if exists, null if not
     */
    
    public Role findByName(String name);
}
