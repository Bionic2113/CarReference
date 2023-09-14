package com.caterpillar.shamil.CarReference.service;

import com.caterpillar.shamil.CarReference.models.Car;
import com.caterpillar.shamil.CarReference.repository.CarRepository;
import com.caterpillar.shamil.CarReference.requests.Filters;
import com.caterpillar.shamil.CarReference.requests.SortingOptions;
import com.caterpillar.shamil.CarReference.responses.Response;
import com.caterpillar.shamil.CarReference.sorting.CarsFilter;
import com.caterpillar.shamil.CarReference.sorting.CarsSort;
import com.caterpillar.shamil.CarReference.statistics.GoogleTranslate;
import com.caterpillar.shamil.CarReference.statistics.ParseAvtocod;
import com.caterpillar.shamil.CarReference.statistics.ResultAfterTranslate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CarService {

    @Autowired
    CarRepository carRepository;

    @Autowired
    ParseAvtocod parseAvtocod;

    @Autowired
    GoogleTranslate googleTranslate;

    @Transactional
    public Response createCar(Car c) {
        c.setAvtocod_link("https://avtocod.ru/proverkaavto/" + c.getNumber().toLowerCase() + "?rd=GRZ");
        c.setAutoru_link("https://auto.ru/moskva/cars/"
                + c.getBrand() + "/"
                + c.getModel().replaceAll(" ", "_").toLowerCase()
                + "/" + c.getYear() + "-year/all/");
        Car car = carRepository.createCarIfNotExists(c).orElse(new Car());
        Response response = new Response();
        if (!c.equals(car)) {
            response.setResult("Объект с таким номером уже существует");
        } else {
            response.setCar(car);
            response.setResult("Успешно создано");
        }
        return response;
    }

    @Transactional
    public Response deleteById(Integer id) {
        Response response = new Response();
        try {
            Car car = carRepository.deleteBy_Id(id).orElseThrow(NoSuchElementException::new);
            response.setCar(car);
            response.setResult("Объект удален");
        } catch (NoSuchElementException e) {
            response.setResult("Объект для удаление не был найден или уже был удален");
        }
        return response;
    }

    @Transactional
    public Response deleteByNumber(String number) {
        Response response = new Response();
        try {
            Car car = carRepository.deleteByNumber(number).orElseThrow(NoSuchElementException::new);
            response.setCar(car);
            response.setResult("Объект удален");
        } catch (NoSuchElementException e) {
            response.setResult("Объект для удаление не был найден или уже был удален");
        }
        return response;
    }

    public List<Car> sortsListCars(SortingOptions sortingOptions){
        List<Car> cars = carRepository.findByActiveTrue();
        return new CarsSort().mySort(cars, sortingOptions);
    }

    public List<Car> filteringListCars(Filters filters){
        List<Car> cars = carRepository.findByActiveTrue();
        return new CarsFilter().filteringCars(cars, filters);
    }

    /**
     * Стоит "заглушка" для тестов, тк сайт
     * не дает выполнить больше трёх запросов.
     *
     * @return - Список элементов, состоящих из
     * булева значения по отношению к данным об
     * автомобиле из базы данных и данные по
     * проверке номеров на avtocod.ru
     */

    public ResultAfterTranslate[]statisticCars(){
        List<Car> cars = carRepository.findByActiveTrue();
        String[] fullNames = new String[cars.size()];
        for (int i = 0; i < fullNames.length; i++){
            fullNames[i] = cars.get(i).getBrand() + "_" + cars.get(i)
                                                            .getModel()
                                                            .replaceAll(" ", "_");
        }
        String[] test = new String[]{
                "ХЕНДЭ_GRAND_SANTA_FE",
                "ВАЗ_21063",
                "ФОРД_ТРАНЗИТ_АВТОМОБИЛЬ_СКОРОЙ_МЕДИЦИНСК",
                "ФОЛЬКСВАГЕН_ТИГУАН",
                "КИА_СПЕКТРА_FВ2272"
        };
        return googleTranslate.translate(fullNames, test);//заместо test должно быть
                                                          // parseAvtocod.parsingAvtocod(cars));
                                                          // сайт после 3 элемента выдаст 429 ошибку
    }
}
