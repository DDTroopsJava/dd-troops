package cz.fi.muni.pa165.ddtroops.service.facade;

import cz.fi.muni.pa165.ddtroops.dto.UserDTO;
import cz.fi.muni.pa165.ddtroops.entity.User;
import cz.fi.muni.pa165.ddtroops.facade.UserFacade;
import cz.fi.muni.pa165.ddtroops.service.exceptions.DDTroopsServiceException;
import cz.fi.muni.pa165.ddtroops.service.exceptions.InvalidPasswordException;
import cz.fi.muni.pa165.ddtroops.service.services.BeanMappingService;
import cz.fi.muni.pa165.ddtroops.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;


/**
 * Created by pstanko.
 *
 * @author pstanko
 */
@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

    @Autowired
    private UserService userService;

    @Autowired
    private BeanMappingService beanMappingService;

    private Logger logger = LoggerFactory.getLogger(UserFacadeImpl.class.getName());

    @Override
    public UserDTO findById(Long userId) {
        User user = null;
        try {
            user = userService.findById(userId);
        } catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);
            return null;
        }
        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public boolean delete(Long id) {
        if(id == null){
            throw new IllegalArgumentException("id");
        }
        User userEntity = beanMappingService.mapTo(findById(id), User.class);
        try {
            userService.delete(userEntity);
            return true;
        } catch (DDTroopsServiceException ex) {
            logger.warn(ex.getMessage(), ex);
        }
        return false;
    }

    @Override
    public UserDTO findByEmail(String email) {
        User user;
        try {
            user = userService.findByEmail(email);
        } catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);
            return null;
        }
        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        User userEntity = beanMappingService.mapTo(userDTO, User.class);
        try {
            userService.update(userEntity);
            userDTO.setId(userEntity.getId());
            return userDTO;
        } catch (DDTroopsServiceException ex) {
            logger.warn(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public UserDTO updatePassword(UserDTO userDTO, String oldPassword, String newPassword) {

        User userEntity = beanMappingService.mapTo(userDTO, User.class);

        try {
            if (!userService.updatePassword(userEntity, oldPassword, newPassword)) {
                throw new InvalidPasswordException();
            }
        } catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);
        }

        return findById(userDTO.getId());
    }

    @Override
    public void register(UserDTO userDTO, String unencryptedPassword) {
        User userEntity = beanMappingService.mapTo(userDTO, User.class);
        try {
            userService.register(userEntity, unencryptedPassword);
        } catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);
        }
        userDTO.setId(userEntity.getId());
    }

    @Override
    public Collection<UserDTO> findAll() {
        try {
            return beanMappingService.mapTo(userService.findAll(), UserDTO.class);
        } catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public boolean authenticate(String email, String unencryptedPassword) {
        try {
            User user = userService.findByEmail(email);
            return user != null && userService.authenticate(user, unencryptedPassword);
        } catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);

        }
        return false;
    }

    @Override
    public boolean isAdmin(UserDTO u) {
        try {
            return userService.isAdmin(beanMappingService.mapTo(u, User.class));
        } catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);
        }
        return false;
    }

}