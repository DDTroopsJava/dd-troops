/*
package cz.muni.fi.pa165.ddtroops.rest.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import cz.muni.fi.pa165.ddtroops.dto.UserCreateDTO;
import cz.muni.fi.pa165.ddtroops.dto.UserDTO;
import cz.muni.fi.pa165.ddtroops.dto.UserUpdateDTO;
import cz.muni.fi.pa165.ddtroops.facade.UserFacade;
import cz.muni.fi.pa165.ddtroops.rest.RootWebContext;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@WebAppConfiguration
@ContextConfiguration(classes = {RootWebContext.class})
public class UserControllerTest {

    @Mock
    private UserFacade userFacade;


    @InjectMocks
    UsersController usersController = new UsersController();

    private MockMvc mockMvc;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(usersController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

   */
/* private ExceptionHandlerExceptionResolver createExceptionResolver() {
        ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {
                Method method = new ExceptionHandlerMethodResolver(GlobalExceptionController.class).resolveMethod(exception);
                return new ServletInvocableHandlerMethod(new GlobalExceptionController(), method);
            }
        };
        exceptionResolver.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        exceptionResolver.afterPropertiesSet();
        return exceptionResolver;
    }*//*


    @Test
    public void debugTest() throws Exception {
        doReturn(Collections.unmodifiableList(this.createUser())).when(
                userFacade).findAll();
        mockMvc.perform(get("/users")).andDo(print());
    }

    @Test
    public void shouldGetAllUsers() throws Exception {
        doReturn(Collections.unmodifiableList(this.createUser())).when(
                userFacade).findAll();

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].name").value("Chuck"))
                .andExpect(jsonPath("$.[?(@.id==3)].name").value("Bill"));
    }

    @Test
    public void shouldGetUser() throws Exception {
        List<UserDTO> users = this.createUser();

        doReturn(users.get(0)).when(userFacade).findById(3L);

        mockMvc.perform(get("/users/3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name").value("Bill"));
    }

    @Test
    public void shouldDeleteUser() throws Exception {

        List<UserDTO> users = this.createUser();

        mockMvc.perform(delete("/users/3"))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldCreateUser() throws Exception {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setName("Bill");
        userCreateDTO.setEmail("bill@microsoft.com");

        doReturn(1L).when(userFacade).register(
                any(UserCreateDTO.class));
        String json = this.convertObjectToJsonBytes(userCreateDTO);

        System.out.println(json);

        mockMvc.perform(
                post("/users/").contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void shouldUpdateUser() throws Exception {
        List<UserUpdateDTO> users = this.updateUser();

        doReturn(users.get(0)).when(userFacade).findById(3L);
        doReturn(10L).when(userFacade).update(users.get(0));
        users.get(0).setName("Renamed");

        String json = this.convertObjectToJsonBytes(users.get(0));
        mockMvc.perform(put("/users/3").contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .content(json)).andDo(print())
                .andExpect(status().isOk());
    }


    private List<UserDTO> createUser() {

        return null;
    }

    private List<UserUpdateDTO> updateUser() {

        return null;
    }


    private static String convertObjectToJsonBytes(Object object)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }


}*/
