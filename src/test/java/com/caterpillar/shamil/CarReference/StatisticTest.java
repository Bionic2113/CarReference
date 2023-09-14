package com.caterpillar.shamil.CarReference;

import com.caterpillar.shamil.CarReference.models.Car;
import com.caterpillar.shamil.CarReference.repository.CarRepository;
import com.caterpillar.shamil.CarReference.statistics.GoogleTranslate;
import com.caterpillar.shamil.CarReference.statistics.ParseAvtocod;
import com.caterpillar.shamil.CarReference.statistics.ResultAfterTranslate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <h3>CarReference</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 13.09.2023
 */
@SpringBootTest
@AutoConfigureMockMvc
public class StatisticTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CarRepository carRepositoryMock;

    List<Car> cars = Arrays.asList(
            new Car("С133СС095", "hyundae", "grand santa fe", "blue", 2013),
            new Car("С137КМ112", "VAZ", "21063", "black", 2013),
            new Car("331МК005", "FORD", "TRANSIT AMBULANCE medical", "black", 2013),
            new Car("К134СМ001", "volkswagen", "tiguan", "black", 2013),
            new Car("A111AA777", "KIA", "spectra fв2272", "black", 2013)
    );


    @Test
    public void translateCarTest(){
        /**
         * Закомментированные строчки можно применить,
         * если убрать заглушку в CarService, но больше
         * 3-ёх элементов не примет сработает полноценно,
         * тк avtocod потом будет выдавать 429 ошибку.
         * В ex массиве закомментированные строчки -
         * это то, что удалось бы вытащить с avtocod.ru
         */
//        cars.get(0).setAvtocod_link("https://avtocod.ru/proverkaavto/в012aa774?rd=GRZ");
//        cars.get(1).setAvtocod_link("https://avtocod.ru/proverkaavto/м092aa774?rd=GRZ");
//        cars.get(2).setAvtocod_link("https://avtocod.ru/proverkaavto/b099bb196?rd=GRZ");
//        cars.get(3).setAvtocod_link("https://avtocod.ru/proverkaavto/b787ee196?rd=GRZ");
//        cars.get(4).setAvtocod_link("https://avtocod.ru/proverkaavto/o787ee196?rd=GRZ");
        when(carRepositoryMock.findByActiveTrue()).thenReturn(cars);

        try {
            String result = mockMvc.perform(get("/statistics")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            ResultAfterTranslate[] ex = new ResultAfterTranslate[]{
                    new ResultAfterTranslate(true,
                            (cars.get(0).getBrand()+"_"+cars.get(0).getModel().replaceAll(" ", "_")).toLowerCase(),
                            (cars.get(0).getBrand()+"_"+cars.get(0).getModel().replaceAll(" ", "_")).toLowerCase()

                            ),//"ХЕНДЭ_GRAND_SANTA_FE"),
                    new ResultAfterTranslate(true,
                            (cars.get(1).getBrand()+"_"+cars.get(1).getModel().replaceAll(" ", "_")).toLowerCase(),
                            (cars.get(1).getBrand()+"_"+cars.get(1).getModel().replaceAll(" ", "_")).toLowerCase()

                            ),//"ВАЗ_21063"),
                    new ResultAfterTranslate(true,
                            (cars.get(2).getBrand()+"_"+cars.get(2).getModel().replaceAll(" ", "_")).toLowerCase(),
                            (cars.get(2).getBrand()+"_"+cars.get(2).getModel().replaceAll(" ", "_")).toLowerCase()

                            ),//"ФОРД_ТРАНЗИТ_АВТОМОБИЛЬ_СКОРОЙ_МЕДИЦИНСК"),
                    new ResultAfterTranslate(true,
                            (cars.get(3).getBrand()+"_"+cars.get(3).getModel().replaceAll(" ", "_")).toLowerCase(),
                            (cars.get(3).getBrand()+"_"+cars.get(3).getModel().replaceAll(" ", "_")).toLowerCase()

                            ),//"ФОЛЬКСВАГЕН_ТИГУАН"),
                    new ResultAfterTranslate(true,
                            (cars.get(4).getBrand()+"_"+cars.get(4).getModel().replaceAll(" ", "_")).toLowerCase(),
                            (cars.get(4).getBrand()+"_"+cars.get(4).getModel().replaceAll(" ", "_")).toLowerCase()

                            ),//"КИА_СПЕКТРА_FВ2272")
            };

            String expected = new Gson().toJson(ex);

            Assertions.assertEquals(expected, result);
        } catch (Exception e) {
            Assertions.fail("Exception in translateCarTest " + e);
        }

    }
}
