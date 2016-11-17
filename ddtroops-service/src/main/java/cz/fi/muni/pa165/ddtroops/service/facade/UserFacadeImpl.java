package cz.fi.muni.pa165.ddtroops.service.facade;

import java.util.Collection;

import cz.fi.muni.pa165.ddtroops.dto.UserDTO;
import cz.fi.muni.pa165.ddtroops.entity.User;
import cz.fi.muni.pa165.ddtroops.facade.UserFacade;
import cz.fi.muni.pa165.ddtroops.service.exceptions.InvallidPasswordException;
import cz.fi.muni.pa165.ddtroops.service.services.BeanMappingService;
import cz.fi.muni.pa165.ddtroops.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by pstanko.
 * @author pstanko
 */
@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

    @Autowired
    private UserService userService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public UserDTO findById(Long userId) {
        User user = userService.findById(userId);
        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public UserDTO findByEmail(String email) {
        User user = userService.findByEmail(email);
        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        User userEntity = beanMappingService.mapTo(userDTO, User.class);
        userService.update(userEntity);
        userDTO.setId(userEntity.getId());
        return userDTO;
    }

    @Override
    public UserDTO updatePassowrd(UserDTO userDTO, String oldPassword, String newPassword) {
        User userEntity = beanMappingService.mapTo(userDTO, User.class);
        if(!userService.updatePassword(userEntity, oldPassword, newPassword)){
            throw new InvallidPasswordException();
        }
        return userDTO;
    }

    @Override
    public void register(UserDTO userDTO, String unencryptedPassword) {
        User userEntity = beanMappingService.mapTo(userDTO, User.class);
        userService.register(userEntity, unencryptedPassword);
        userDTO.setId(userEntity.getId());
    }

    @Override
    public Collection<UserDTO> findAll() {
        return beanMappingService.mapTo(userService.findAll(), UserDTO.class);
    }

    @Override
    public boolean authenticate(String email, String unencryptedPassword) {
        return userService.authenticate(userService.findByEmail(email), unencryptedPassword);
    }

    @Override
    public boolean isAdmin(UserDTO u) {
        return userService.isAdmin(beanMappingService.mapTo(u, User.class));
    }

}