package com.rental.web;

import com.rental.persistence.model.entities.Rental;
import com.rental.persistence.model.entities.User;
import com.rental.service.RentalService;
import com.rental.service.utils.RentalList;
import com.rental.web.controller.RentalController;
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
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>Unit testing class for the rental controller.</p>
 *
 * @author Martín Díaz
 * @version 1.0
 */
public class RentalControllerTest {

    @InjectMocks
    private RentalController rentalController;

    @Mock
    private RentalService rentalService;

    private MockMvc mockMvc;

    private ArgumentCaptor<Rental> rentalCaptor;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(rentalController).build();
        rentalCaptor = ArgumentCaptor.forClass(Rental.class);

    }

    @Test
    public void getAllRentalsTest() throws Exception {
        RentalList rentalList = setupRentalList();

        when(rentalService.getAllRentals()).thenReturn(rentalList);

        mockMvc.perform(get("/v1/rental"))
                .andExpect(jsonPath("$.rentalResources[*]", hasSize(2)))
                .andExpect(jsonPath("$.rentalResources[*].rid", hasItem(rentalList.getRentals().get(0).getId().intValue())))
                .andExpect(jsonPath("$.rentalResources[*].time", hasItem(rentalList.getRentals().get(0).getTime())))
                .andExpect(jsonPath("$.rentalResources[*].price", hasItem(rentalList.getRentals().get(0).getPrice())))
                .andExpect(jsonPath("$.rentalResources[*].links[*].href", hasItem(endsWith("/v1/user/" + rentalList.getRentals().get(0).getUser().getName()))))
                .andExpect(jsonPath("$.rentalResources[*].rid", hasItem(rentalList.getRentals().get(1).getId().intValue())))
                .andExpect(jsonPath("$.rentalResources[*].time", hasItem(rentalList.getRentals().get(1).getTime())))
                .andExpect(jsonPath("$.rentalResources[*].price", hasItem(rentalList.getRentals().get(1).getPrice())))
                .andExpect(jsonPath("$.rentalResources[*].links[*].href", hasItem(endsWith("/v1/user/" + rentalList.getRentals().get(1).getUser().getName()))))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllRentalsEmptyListTest() throws Exception {

        List<Rental> rentals = new ArrayList<>();
        RentalList rentalList = new RentalList(rentals);
        when(rentalService.getAllRentals()).thenReturn(rentalList);

        mockMvc.perform(get("/v1/rental")).andExpect(status().isNotFound());
    }

    @Test
    public void removeRentalExistTest() throws Exception {

        User user = new User("Martin","password");
        user.setId(1L);
        Rental rental = new Rental(2,2,20.0,LocalDateTime.now().plusDays(2));
        rental.setId(2L);
        rental.setUser(user);

        when(rentalService.removeRental(any(Long.class))).thenReturn(rental);

        mockMvc.perform(delete("/v1/rental/2"))
                .andExpect(jsonPath("$.rid", is(rental.getId().intValue())))
                .andExpect(jsonPath("$.time", is(rental.getTime())))
                .andExpect(jsonPath("$.numberOfBikes", is(rental.getNumberOfBikes())))
                .andExpect(jsonPath("$.price", is(rental.getPrice().doubleValue())))
                .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/v1/user/"+rental.getUser().getName()))))
                .andExpect(status().isOk());

    }

    @Test
    public void removeRentalDoesNotExistTest() throws Exception {

        when(rentalService.removeRental(any(Long.class))).thenReturn(null);
        mockMvc.perform(delete("/v1/rental/2")).andExpect(status().isNotFound());
    }

    @Test
    public void getPricingTest() throws Exception {
        Rental rental = new Rental();
        rental.setTime(1);
        rental.setNumberOfBikes(3);
        rental.setPrice(10.5);
        when(rentalService.getPricing(any(Rental.class))).thenReturn(rental);
        mockMvc.perform(put("/v1/rental")
                .content("{\"time\":2,\"numberOfBikes\":2}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.rid", is(nullValue())))
                .andExpect(jsonPath("$.time", is(rental.getTime())))
                .andExpect(jsonPath("$.numberOfBikes", is(rental.getNumberOfBikes())))
                .andExpect(jsonPath("$.price", is(rental.getPrice())))
                .andExpect(jsonPath("$.rentalDateRequested", is(nullValue())))
                .andExpect(status().isOk());
    }

    @Test
    public void getPricingInvalidData() throws Exception {
        mockMvc.perform(put("/v1/rental").content("").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private RentalList setupRentalList() {
        List<Rental> rentals = new ArrayList<>();

        Rental rentalA = new Rental(2, 1,10.0, LocalDateTime.now().plusDays(2));
        Rental rentalB = new Rental(1, 2, 10.0, LocalDateTime.now().plusDays(2));
        User userA = new User("Martin", "password");
        User userB = new User("Juan", "password");

        userA.setId(1L);

        userB.setId(2L);

        rentalA.setId(10L);
        rentalA.setUser(userA);

        rentalB.setId(20L);
        rentalB.setUser(userB);

        rentals.add(rentalA);
        rentals.add(rentalB);

        return new RentalList(rentals);

    }
}
