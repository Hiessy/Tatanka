package com.rental.web;

import com.rental.persistence.model.entities.Rental;
import com.rental.persistence.model.entities.User;
import com.rental.service.UserService;
import com.rental.service.exception.InvalidDateException;
import com.rental.service.exception.UserDoesNotExistsException;
import com.rental.service.exception.UserExistsException;
import com.rental.web.controller.UserController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * <p>Unit testing class for the user controller.</p>
 *
 * @author Martín Díaz
 * @version 1.0
 */
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    private ArgumentCaptor<User> userCaptor;

    @Before
    public void setup(){

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        userCaptor = ArgumentCaptor.forClass(User.class);

    }

    @Test
    public void createNewNoneExistingUserTest() throws Exception{

        User createdUser = new User();
        createdUser.setId(1L);
        createdUser.setName("Martin");
        createdUser.setPassword("1234abcd");

        when(userService.createUser(any(User.class))).thenReturn(createdUser);

        mockMvc.perform(post("/v1/user").content("{\"name\":\"Martin\",\"password\":\"1234abcd\"}").contentType(MediaType.APPLICATION_JSON))
               .andExpect(header().string("Location",endsWith("/v1/user/Martin")))
               .andExpect(jsonPath("$.rid",is(createdUser.getId().intValue())))
               .andExpect(jsonPath("$.name",is(createdUser.getName())))
               .andExpect(status().isCreated());

        verify(userService).createUser(userCaptor.capture());
        String password = userCaptor.getValue().getPassword();
        assertEquals("1234abcd", password);
    }

    @Test
    public void createExistingUserTest()throws Exception{

        User createdUser = new User();
        createdUser.setId(1L);
        createdUser.setName("Martin");
        createdUser.setPassword("1234abcd");

        when(userService.createUser(any(User.class))).thenThrow(UserExistsException.class);

        mockMvc.perform(post("/v1/user").content("{\"name\":\"Martin\",\"password\":\"1234abcd\"}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());

    }

    @Test
    public void createUserWithNoDataTest() throws Exception{
        mockMvc.perform(post("/v1/user").content("").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getExistingUserTest()throws Exception{

        User userInRepository= new User();
        userInRepository.setId(1L);
        userInRepository.setName("Martin");
        userInRepository.setPassword("1234abcd");

        when(userService.getUser(any(String.class))).thenReturn(userInRepository);

        mockMvc.perform(get("/v1/user/Martin"))
                .andExpect(jsonPath("$.name",is(userInRepository.getName())))
                .andExpect(jsonPath("$.rid",is(userInRepository.getId().intValue())))
                .andExpect(jsonPath("$.links[*].rel", hasItems(endsWith("self"))))
                .andExpect(jsonPath("$.links[*].href", hasItems(endsWith(userInRepository.getName()))))
                .andExpect(status().isOk());
    }

    @Test
    public void getNonExistingUserTest() throws Exception{
        when(userService.getUser(any(String.class))).thenReturn(null);

        mockMvc.perform(get("/v1/user/Martin"))
               .andExpect(status().isNotFound());
    }
    @Test
    public void bookNewRentalUserExistTest() throws Exception{

        User user = new User("Martin","password");
        user.setId(1L);
        Rental rental = new Rental(2,2,20.0,LocalDateTime.now().plusDays(2));
        rental.setId(2L);
        rental.setUser(user);

        when(userService.createRental(any(Long.class),any(Rental.class))).thenReturn(rental);

        mockMvc.perform(post("/v1/user/1/rental")
                .content("{\"time\":2,\"numberOfBikes\":2,\"rentalDateRequested\":\"2019-06-15T13:00:00.0\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.rid", is(rental.getId().intValue())))
                .andExpect(jsonPath("$.time", is(rental.getTime())))
                .andExpect(jsonPath("$.numberOfBikes", is(rental.getNumberOfBikes())))
                .andExpect(jsonPath("$.price", is(rental.getPrice())))
                .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/v1/user/Martin"))))
                .andExpect(status().isCreated());

    }

    @Test
    public void bookNewRentalUserDoesNotExistTest() throws Exception{

        when(userService.createRental(any(Long.class),any(Rental.class))).thenThrow(UserDoesNotExistsException.class);

        mockMvc.perform(post("/v1/user/1/rental").content("{\"time\":2,\"numberOfBikes\":2,\"rentalDateRequested\":\"2019-06-15T13:00:00.0\"}")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());

    }

    @Test
    public void bookNewRentalDateIsNotValidTest()throws Exception{
        when(userService.createRental(any(Long.class),any(Rental.class))).thenThrow(InvalidDateException.class);

        mockMvc.perform(post("/v1/user/1/rental").content("{\"time\":2,\"numberOfBikes\":2,\"rentalDateRequested\":\"2015-06-15T13:00:00.0\"}")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict());

    }
}
