package cz.fi.muni.pa165.ddtroops.service.services.impl;

import cz.fi.muni.pa165.ddtroops.dao.RoleDao;
import cz.fi.muni.pa165.ddtroops.entity.Role;
import cz.fi.muni.pa165.ddtroops.service.exceptions.DDTroopsServiceException;
import cz.fi.muni.pa165.ddtroops.service.services.RoleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * Created by Petr Koláček
 * 
 * @author Petr Koláček
 */
@Service
public class RoleServiceImpl implements RoleService 
{
    @Autowired
    private RoleDao roleDao;

    @Override
    public void createRole(Role role) throws DDTroopsServiceException {
        if (role == null) {
            throw new IllegalArgumentException("Role is null.");
        }
        try {
            roleDao.save(role);
        }
        catch (Throwable e) {
            throw new DDTroopsServiceException("Cannot create role named " + role.getName() + " with id" + role.getId(), e);
        }
    }

    @Override
    public Role findById(Long id) throws DDTroopsServiceException {
        if (id == null) {
            throw new IllegalArgumentException("Role id is null.");
        }
        try {
            return roleDao.findOne(id);
        }catch(Throwable e) {
            throw new DDTroopsServiceException("Cannot find role with id  "+ id, e);
        }
    }

    @Override
    public Role findByName(String name) throws DDTroopsServiceException {
        if ((name == null) || (name.isEmpty())) {
            throw new IllegalArgumentException("Role name is empty or null.");
        }
        try {
            return roleDao.findByName(name);
        }catch(Throwable e) {
            throw new DDTroopsServiceException("Cannot find role named  "+ name, e);
        }
    }

    @Override
    public List<Role> findAll() throws DDTroopsServiceException {
        try {
            return roleDao.findAll();
        } catch (Throwable e) {
            throw new DDTroopsServiceException("Could not receive list of roles", e);
        }
    }

    @Override
    public void updateRole(Role role) throws DDTroopsServiceException {
        if (role == null) {
            throw new IllegalArgumentException("Role is null.");
        }
        try {
            roleDao.save(role);
        }
        catch (Throwable e) {
            throw new DDTroopsServiceException("Cannot update role named " + role.getName()
                    + " with id" + role.getId(), e);
        }
    }

    @Override
    public void deleteRole(Role role) throws DDTroopsServiceException {
        if (role == null) {
            throw new IllegalArgumentException("Role is null.");
        }
        try {
            roleDao.delete(role);
        }
        catch (Throwable e) {
            throw new DDTroopsServiceException("Cannot delete role named " + role.getName()
                    + " with id" + role.getId(), e);
        }
    }

    @Override
    public Boolean deleteAllRoles() throws DDTroopsServiceException {
        try {
            roleDao.deleteAll();
            return true;
        }
        catch (Throwable e) {
            throw new DDTroopsServiceException("Cannot delete all roles.", e);
        }
    }
}
