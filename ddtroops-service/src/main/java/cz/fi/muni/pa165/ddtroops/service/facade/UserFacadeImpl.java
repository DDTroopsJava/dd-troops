package cz.fi.muni.pa165.ddtroops.service.facade;

import java.util.Collection;

import cz.fi.muni.pa165.ddtroops.dto.UserCreateDTO;
import cz.fi.muni.pa165.ddtroops.dto.UserDTO;
import cz.fi.muni.pa165.ddtroops.dto.UserUpdateDTO;
import cz.fi.muni.pa165.ddtroops.dto.UserUpdatePassDTO;
import cz.fi.muni.pa165.ddtroops.entity.User;
import cz.fi.muni.pa165.ddtroops.facade.UserFacade;
import cz.fi.muni.pa165.ddtroops.service.exceptions.InvalidPasswordException;
import cz.fi.muni.pa165.ddtroops.service.services.BeanMappingService;
import cz.fi.muni.pa165.ddtroops.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        User user = userService.findById(userId);
        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public boolean delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id");
        }
        User userEntity = beanMappingService.mapTo(findById(id), User.class);
        userService.delete(userEntity);
        return true;

    }

    @Override
    public UserDTO findByEmail(String email) {
        User user = userService.findByEmail(email);
        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public UserDTO update(UserUpdateDTO userDTO) {
        User userEntity = beanMappingService.mapTo(userDTO, User.class);
        UserDTO orig = findById(userDTO.getId());
        userEntity.setJoinedDate(orig.getJoinedDate());
        userService.update(userEntity);
        return findById(userDTO.getId());
    }

    @Override
    public UserDTO updatePassword(UserUpdatePassDTO userDTO) {

        User userEntity = beanMappingService.mapTo(findById(userDTO.getId()), User.class);

        if (!userService.updatePassword(userEntity, userDTO.getCurrentPassword(), userDTO.getNewPassword())) {
            throw new InvalidPasswordException();
        }

        return findById(userDTO.getId());
    }

    @Override
    public UserDTO register(UserCreateDTO userDTO, String unencryptedPassword) {
        User userEntity = beanMappingService.mapTo(userDTO, User.class);
        userEntity = userService.register(userEntity, unencryptedPassword);
        return findById(userEntity.getId());
    }

    @Override
    public UserDTO register(UserCreateDTO userDTO) {
        User userEntity = beanMappingService.mapTo(userDTO, User.class);
        userEntity = userService.register(userEntity, userDTO.getPassword());
        return findById(userEntity.getId());
    }

    @Override
    public Collection<UserDTO> findAll() {
        return beanMappingService.mapTo(userService.findAll(), UserDTO.class);
    }

    @Override
    public boolean authenticate(String email, String unencryptedPassword) {
        User user = userService.findByEmail(email);
        return user != null && userService.authenticate(user, unencryptedPassword);
    }

    @Override
    public boolean isAdmin(UserDTO u) {
        return userService.isAdmin(beanMappingService.mapTo(u, User.class));
    }

}