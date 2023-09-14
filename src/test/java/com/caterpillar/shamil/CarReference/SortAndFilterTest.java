package com.caterpillar.shamil.CarReference;

import com.caterpillar.shamil.CarReference.models.Car;
import com.caterpillar.shamil.CarReference.repository.CarRepository;
import com.caterpillar.shamil.CarReference.requests.Filters;
import com.caterpillar.shamil.CarReference.requests.SortingOptions;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * <h3>CarReference</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 11.09.2023
 */
@SpringBootTest
@AutoConfigureMockMvc
public class SortAndFilterTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CarRepository carRepositoryMock;

    List<Car> cars = Arrays.asList(
            new Car("A331МК005", "Audi", "z4", "black", 2013),
            new Car("С133СС095", "Opel", "p", "blue", 2013),
            new Car("С137КМ112", "Opel", "p", "black", 2013),
            new Car("К134СМ001", "Lifan", "gz", "black", 2013),
            new Car("В111AA777", "BMW", "M8", "black", 2013),
            new Car("A111AA777", "Mercedes", "M8", "black", 2013),
            new Car("A111AA777", "BMW", "M4", "black", 2013),
            new Car("A111AA777", "BMW", "M8", "white", 2013),
            new Car("A111AA777", "BMW", "M8", "black", 2014),
            new Car("A111AA777", "BMW", "M8", "black", 2013)
    );

    /**
     * Использовался русский алфавит, поэтому К раньше С
     */
    @Test
    public void sortCarControllerTest() {
        when(carRepositoryMock.findByActiveTrue()).thenReturn(cars);
        try {
            String json = new ObjectMapper()
                    .writeValueAsString(
                            new SortingOptions(new LinkedHashSet<>(List.of(
                                    "number",
                                    "brand",
                                    "model",
                                    "color",
                                    "year"
                            )))
                    );


            String result = mockMvc.perform(post("/sort")
                            .content(json)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse()
                    .getContentAsString();
            List<Car> carsExpected = List.of(
                    new Car("A111AA777", "BMW", "M4", "black", 2013),
                    new Car("A111AA777", "BMW", "M8", "black", 2013),
                    new Car("A111AA777", "BMW", "M8", "black", 2014),
                    new Car("A111AA777", "BMW", "M8", "white", 2013),
                    new Car("A111AA777", "Mercedes", "M8", "black", 2013),
                    new Car("A331МК005", "Audi", "z4", "black", 2013),
                    new Car("В111AA777", "BMW", "M8", "black", 2013),
                    new Car("К134СМ001", "Lifan", "gz", "black", 2013),
                    new Car("С133СС095", "Opel", "p", "blue", 2013),
                    new Car("С137КМ112", "Opel", "p", "black", 2013)
            );
            List<Car> carsResult = new Gson().fromJson(result, new TypeToken<List<Car>>() {}.getType());
            Assertions.assertThat(carsResult).containsExactlyElementsOf(carsExpected);
        } catch (Exception e) {
            Assertions.fail("Exception in sortCarControllerTest " + e);
        }
    }

    @Test
    public void filterCarsControllerTest(){
        when(carRepositoryMock.findByActiveTrue()).thenReturn(cars);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try{
            String json = new ObjectMapper()
                    .writeValueAsString(
                            new Filters(
                                    new ArrayList<>(List.of("A111AA777","С133СС095")),
                                    new ArrayList<>(List.of("Mercedes","Opel")),
                                    new ArrayList<>(List.of("M8","М8","p","р")),
                                    new ArrayList<>(List.of("blue","black")),
                                    new ArrayList<>(List.of(2013)),
                                    new ArrayList<>(List.of(
                                            simpleDateFormat.format(
                                                    new Date(System.currentTimeMillis()))
                                    ))
                            ));
            String result = mockMvc.perform(post("/filter")
                            .content(json)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse()
                    .getContentAsString();
            List<Car> carsExpected = List.of(
                    new Car("С133СС095", "Opel", "p", "blue", 2013),
                    new Car("A111AA777", "Mercedes", "M8", "black", 2013)
            );
            List<Car> carsResult = new Gson().fromJson(result, new TypeToken<List<Car>>() {}.getType());
            Assertions.assertThat(carsResult).containsExactlyElementsOf(carsExpected);
            // test 2
            Filters filters = new Filters();
            filters.setBrands(List.of("BMW"));
            json = new ObjectMapper().writeValueAsString(filters);
            result = mockMvc.perform(post("/filter")
                            .content(json)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse()
                    .getContentAsString();
            carsExpected = List.of(
                    new Car("В111AA777", "BMW", "M8", "black", 2013),
                    new Car("A111AA777", "BMW", "M4", "black", 2013),
                    new Car("A111AA777", "BMW", "M8", "white", 2013),
                    new Car("A111AA777", "BMW", "M8", "black", 2014),
                    new Car("A111AA777", "BMW", "M8", "black", 2013)
            );
            carsResult = new Gson().fromJson(result, new TypeToken<List<Car>>() {}.getType());
            Assertions.assertThat(carsResult).containsExactlyElementsOf(carsExpected);
            // test 3
            filters = new Filters();
            json = new ObjectMapper().writeValueAsString(filters);
            result = mockMvc.perform(post("/filter")
                            .content(json)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse()
                    .getContentAsString();
            carsResult = new Gson().fromJson(result, new TypeToken<List<Car>>() {}.getType());
            Assertions.assertThat(carsResult).containsExactlyElementsOf(cars);
        } catch (Exception e){
            Assertions.fail("Exception in filterCarsControllerTest " + e);

        }
    }
}
