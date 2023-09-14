package com.caterpillar.shamil.CarReference;

import com.caterpillar.shamil.CarReference.models.Car;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = "/before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@TestPropertySource("/application-test.yaml")
@ActiveProfiles("test")
class CreateTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void createCarControllerTest() {
        Car car = new Car("ММ100М190", "BMW", "M8", "black", 2013);
        try {
            String json = new ObjectMapper().writeValueAsString(car);
            mockMvc.perform(post("/create")
                            .content(json)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.car.number").value(car.getNumber()))
                    .andExpect(jsonPath("$.car.brand").value(car.getBrand()))
                    .andExpect(jsonPath("$.car.model").value(car.getModel()))
                    .andExpect(jsonPath("$.car.color").value(car.getColor()))
                    .andExpect(jsonPath("$.car.year").value(car.getYear())
                    );
        } catch (Exception e) {
            Assertions.fail("Exception in createCarControllerTest " + e);
        }
    }



}
