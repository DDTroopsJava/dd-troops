package cz.fi.muni.pa165.ddtroops.service.facade;

import cz.fi.muni.pa165.ddtroops.dto.RoleDTO;
import cz.fi.muni.pa165.ddtroops.entity.Role;
import cz.fi.muni.pa165.ddtroops.service.exceptions.DDTroopsServiceException;
import cz.fi.muni.pa165.ddtroops.facade.RoleFacade;
import cz.fi.muni.pa165.ddtroops.service.services.BeanMappingService;
import cz.fi.muni.pa165.ddtroops.service.services.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;

/**
 * Created by Petr Koláček
 * 
 * @author Petr Koláček
 */
@Service
@Transactional
public class RoleFacadeImpl implements RoleFacade
{
    @Autowired
    private RoleService roleService;

    @Autowired
    private BeanMappingService beanMappingService;

    private Logger logger = LoggerFactory.getLogger(RoleFacadeImpl.class.getName());

    @Override
    public RoleDTO createRole(RoleDTO role) {
        Role roleEntity = beanMappingService.mapTo(role, Role.class);
        try {
            roleService.create(roleEntity);
        } catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);
        }
        role.setId(roleEntity.getId());
        return role;
    }

    @Override
    public RoleDTO findById(long id) {
        Role role = null;
        try {
            role = roleService.findById(id);
        } catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);
            return null;
        }
        return (role == null) ? null : beanMappingService.mapTo(role, RoleDTO.class);
    }

    @Override
    public RoleDTO findByName(String name) {
        Role role = null;
        try {
            role = roleService.findByName(name);
        } catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);
            return null;
        }
        return (role == null) ? null : beanMappingService.mapTo(role, RoleDTO.class);
    }

    @Override
    public Collection<RoleDTO> findAll() {
        try {
            return beanMappingService.mapTo(roleService.findAll(), RoleDTO.class);
        } catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public RoleDTO updateRole(RoleDTO role) {
        Role roleEntity = beanMappingService.mapTo(role, Role.class);
        try {
            roleService.update(roleEntity);
            role.setId(roleEntity.getId());
            return role;
        }catch (DDTroopsServiceException ex){
            logger.warn(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public Boolean deleteRole(RoleDTO role) {
        Role roleEntity = beanMappingService.mapTo(role, Role.class);
        try {
            roleService.delete(roleEntity);
            role.setId(roleEntity.getId());
            return true;
        }catch (DDTroopsServiceException ex){
            logger.warn(ex.getMessage(), ex);
        }
        return false;
    }

    
}
