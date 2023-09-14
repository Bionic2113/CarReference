package com.caterpillar.shamil.CarReference.sorting;

import com.caterpillar.shamil.CarReference.models.Car;
import com.caterpillar.shamil.CarReference.requests.Filters;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * <h3>CarReference</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 12.09.2023
 */
public class CarsFilter {
    /**
     * Тут рефлексию не применить, имхо,
     * да и не все поля подходят для
     * фильтрации, тк ссылки и так
     * используют уже указанные фильтры,
     * поэтому такая реализация.
     * @param cars - список "неудаленных" записей
     * @param filters - списки с фильтрами по
     *                каждой колонке
     * @return - отфильтрованный список
     */
    public List<Car> filteringCars(List<Car> cars, Filters filters){
        Stream<Car> carStream = cars.stream();
        if (filters.getNumbers() != null && !filters.getNumbers().isEmpty()){
            carStream = carStream.filter(car -> filters.getNumbers().contains(car.getNumber()));
        }
        if (filters.getBrands() != null && !filters.getBrands().isEmpty()){
            carStream = carStream.filter(car -> filters.getBrands().contains(car.getBrand()));
        }
        if (filters.getModels() != null && !filters.getModels().isEmpty()){
            carStream = carStream.filter(car -> filters.getModels().contains(car.getModel()));
        }
        if (filters.getColors() != null && !filters.getColors().isEmpty()){
            carStream = carStream.filter(car -> filters.getColors().contains(car.getColor()));
        }
        if (filters.getYears() != null && !filters.getYears().isEmpty()){
            carStream = carStream.filter(car -> filters.getYears().contains(car.getYear()));
        }
        if (filters.getCreated() != null && !filters.getCreated().isEmpty()){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            carStream = carStream.filter(car -> filters
                    .getCreated()
                    .contains(simpleDateFormat.format(new Date(car.getCreated()))));
        }
        return carStream.toList();
    }
}
