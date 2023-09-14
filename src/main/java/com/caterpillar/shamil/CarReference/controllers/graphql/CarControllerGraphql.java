package com.caterpillar.shamil.CarReference.controllers.graphql;

import com.caterpillar.shamil.CarReference.models.Car;
import com.caterpillar.shamil.CarReference.requests.Filters;
import com.caterpillar.shamil.CarReference.requests.Id_Car;
import com.caterpillar.shamil.CarReference.requests.Number_Car;
import com.caterpillar.shamil.CarReference.requests.SortingOptions;
import com.caterpillar.shamil.CarReference.responses.Response;
import com.caterpillar.shamil.CarReference.service.CarService;
import com.caterpillar.shamil.CarReference.statistics.ResultAfterTranslate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <h3>CarReference</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 13.09.2023
 */
@Controller
public class CarControllerGraphql{

    @Autowired
    CarService carService;

    @MutationMapping
    Response createCar(@Argument Car car) {
        return carService.createCar(car);
    }

    @MutationMapping
    Response deleteById(@Argument Id_Car id){
        return carService.deleteById(id.getId());
    }

    @MutationMapping
    Response deleteByNumber(@Argument Number_Car number){
        return carService.deleteByNumber(number.getNumber());
    }

    @MutationMapping
    List<Car> sortCars(@Argument SortingOptions sortingOptions){
        return carService.sortsListCars(sortingOptions);
    }

    @MutationMapping
    List<Car> filteringCars(@Argument Filters filters){
        return carService.filteringListCars(filters);
    }

    @QueryMapping
    ResultAfterTranslate[] getStatistics(){
        return carService.statisticCars();
    }


}
