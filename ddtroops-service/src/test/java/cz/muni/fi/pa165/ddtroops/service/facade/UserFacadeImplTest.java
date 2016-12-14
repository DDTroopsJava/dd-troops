package cz.muni.fi.pa165.ddtroops.service.facade;


import static cz.muni.fi.pa165.ddtroops.service.facade.TestUtils.toSet;
import static org.testng.Assert.*;

import cz.muni.fi.pa165.ddtroops.dto.UserCreateDTO;
import cz.muni.fi.pa165.ddtroops.dto.UserDTO;
import cz.muni.fi.pa165.ddtroops.dto.UserUpdateDTO;
import cz.muni.fi.pa165.ddtroops.dto.UserUpdatePassDTO;
import cz.muni.fi.pa165.ddtroops.facade.UserFacade;
import cz.muni.fi.pa165.ddtroops.service.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Created by pstanko.
 *
 * @author pstanko
 */
@DirtiesContext
@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserFacadeImplTest extends AbstractTestNGSpringContextTests {

    private static final String PASSWORD_1 = "Password1";
    @Autowired
    private UserFacade userFacade;

    private UserDTO user1;
    private UserDTO user2;
    private UserDTO newUser;


    @BeforeMethod
    public void createUsers() {
        user1 = getUser("Chuck", true);
        user2 = getUser("Boogie", false);
        newUser = getUser("new-user", false);

        user1 =userFacade.register(getCreateUser(user1), PASSWORD_1);
        assertTrue(toSet(userFacade.findAll()).contains(user1));

        user2 =userFacade.register(getCreateUser(user2), PASSWORD_1);
        assertTrue(toSet(userFacade.findAll()).contains(user2));

    }


    @AfterMethod
    public void deleteUsers() {
        if (userFacade.findAll().contains(user1)) {
            userFacade.delete(user1.getId());
            assertFalse(toSet(userFacade.findAll()).contains(user1));
        }

        if (userFacade.findAll().contains(user2)) {
            userFacade.delete(user2.getId());
            assertFalse(toSet(userFacade.findAll()).contains(user2));
        }

        if (userFacade.findAll().contains(newUser)) {
            userFacade.delete(newUser.getId());
            assertFalse(toSet(userFacade.findAll()).contains(newUser));
        }
    }

    @Test
    public void testFindById() throws Exception {
        assertEquals(userFacade.findById(user1.getId()), user1);
        assertEquals(userFacade.findById(user2.getId()), user2);
        assertNull(userFacade.findById(1000L));
    }


    @Test
    public void testFindByEmail() throws Exception {
        assertEquals(userFacade.findByEmail(user1.getEmail()), user1);
        assertEquals(userFacade.findByEmail(user2.getEmail()), user2);
        assertNull(userFacade.findByEmail("non-existing@example.com"));
    }

    @Test
    public void testUpdate() throws Exception {
        user1.setPhone("12345");
        logger.info("User id: " + user1);
        user1 = userFacade.update(getUpdateUser(user1));
        assertEquals(userFacade.findAll().size(), 2);
        assertEquals(userFacade.findById(user1.getId()), user1);
        assertEquals(user1.getPhone(), "12345");
    }

    @Test
    public void testUpdatePassword() throws Exception {
        user1 = userFacade.updatePassword(updatePass(user1.getId(), PASSWORD_1, "new_pass"));
        assertFalse(userFacade.authenticate(user1.getEmail(), PASSWORD_1));
        assertTrue(userFacade.authenticate(user1.getEmail(), "new_pass"));
    }

    @Test
    public void testRegister() throws Exception {
        newUser = userFacade.register(getCreateUser(newUser), PASSWORD_1);
        assertEquals(userFacade.findById(newUser.getId()), newUser);
        assertTrue(userFacade.findAll().contains(newUser));
    }

    @Test
    public void testFindAll() throws Exception {
        assertTrue(userFacade.findAll().contains(user2));
        assertTrue(userFacade.findAll().contains(user1));
    }

    @Test
    public void testAuthenticate() throws Exception {
        assertTrue(userFacade.authenticate(user1.getEmail(), PASSWORD_1));
        assertFalse(userFacade.authenticate(user1.getEmail(), "NotAPass1234"));
        assertFalse(userFacade.authenticate("not@example.com", PASSWORD_1));
    }

    @Test
    public void testIsAdmin() throws Exception {
        assertTrue(userFacade.isAdmin(user1));
        assertFalse(userFacade.isAdmin(user2));
    }


    @Test
    public void testDelete() throws Exception {
        assertNotNull(userFacade.findById(user1.getId()));
        userFacade.delete(user1.getId());
        assertNull(userFacade.findById(user1.getId()));
        assertFalse(userFacade.findAll().contains(user1));
    }


    private UserDTO getUser(String name, boolean admin) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(name + "@example.com");
        userDTO.setName(name);
        userDTO.setAdmin(admin);
        return userDTO;
    }

    private UserCreateDTO getCreateUser(String name, boolean admin)
    {
        UserCreateDTO userDTO = new UserCreateDTO();
        userDTO.setEmail(name + "@example.com");
        userDTO.setName(name);
        userDTO.setAdmin(admin);
        return userDTO;
    }

    private UserCreateDTO getCreateUser(UserDTO user)
    {
        UserCreateDTO userDTO = new UserCreateDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setAdmin(user.isAdmin());
        return userDTO;
    }

    private UserUpdateDTO getUpdateUser(UserDTO user){
        UserUpdateDTO userDTO = new UserUpdateDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setAdmin(user.isAdmin());
        userDTO.setPhone(user.getPhone());
        return userDTO;
    }

    private UserUpdatePassDTO updatePass(Long id, String curr, String n)
    {
        return new UserUpdatePassDTO(id, curr, n);
    }
}