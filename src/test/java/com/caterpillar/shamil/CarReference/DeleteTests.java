package com.caterpillar.shamil.CarReference;

import com.caterpillar.shamil.CarReference.models.Car;
import com.caterpillar.shamil.CarReference.repository.CarRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <h3>CarReference</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 12.09.2023
 */
@SpringBootTest
@AutoConfigureMockMvc
public class DeleteTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CarRepository carRepositoryMock;

    Car car_1 = new Car("CC009C198", "BMW", "M8", "black", 2013);
    Car car_2 = new Car("K900KK005", "Nissan", "Juke", "gray", 2014);

    @Test
    public void firstDeleteCarControllerTest(){
        try {
            when(carRepositoryMock.deleteByNumber("CC009C198")).thenReturn(Optional.of(car_1));
            String json = new ObjectMapper().writeValueAsString(car_1);
            mockMvc.perform(delete("/delete_by_number")
                            .content(json)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value("Объект удален"));
        } catch (Exception e) {
            Assertions.fail("Exception in firstDeleteCarControllerTest " + e);
        }
    }

    @Test
    public void secondDeleteControllerTest(){
        car_2.setId(1000);
        try {
            when(carRepositoryMock.deleteBy_Id(1000)).thenReturn(Optional.of(car_2));
            String json = new ObjectMapper().writeValueAsString(car_2);
            mockMvc.perform(delete("/delete_by_id")
                            .content(json)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value("Объект удален"));
        } catch (Exception e) {
            Assertions.fail("Exception in secondDeleteControllerTest " + e);
        }
    }

    @Test
    public void thirdDeleteControllerTest(){
        try {
            when(carRepositoryMock.deleteByNumber("CC009C198")).thenReturn(Optional.empty());
            String json = new ObjectMapper().writeValueAsString(car_1);
            mockMvc.perform(delete("/delete_by_number")
                            .content(json)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value("Объект для удаление не был найден или уже был удален"));

        } catch (Exception e) {
            Assertions.fail("Exception in thirdDeleteControllerTest " + e);
        }
    }

    @Test
    public void fourthDeleteControllerTest(){
        try {
            when(carRepositoryMock.deleteBy_Id(1000)).thenReturn(Optional.empty());
            String json = new ObjectMapper().writeValueAsString(car_2);
            mockMvc.perform(delete("/delete_by_id")
                            .content(json)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value("Объект для удаление не был найден или уже был удален"));
        } catch (Exception e) {
            Assertions.fail("Exception in fourthDeleteControllerTest " + e);
        }
    }
}
