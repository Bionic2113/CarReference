package com.caterpillar.shamil.CarReference.controllers;

import com.caterpillar.shamil.CarReference.models.Car;
import com.caterpillar.shamil.CarReference.requests.Filters;
import com.caterpillar.shamil.CarReference.requests.Id_Car;
import com.caterpillar.shamil.CarReference.requests.Number_Car;
import com.caterpillar.shamil.CarReference.requests.SortingOptions;
import com.caterpillar.shamil.CarReference.responses.Response;
import com.caterpillar.shamil.CarReference.service.CarService;
import com.caterpillar.shamil.CarReference.statistics.ResultAfterTranslate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class CarController {

	@Autowired
	CarService carService;

	/**
	 * Принимает либо все параметры, а это id, number,
	 * brand, model, color, year, avtocod_link,
	 * autoru_link, active, created.
	 * avtocod_link и autoru_link заменятся
	 * в методе обработчике
	 * Но лучше, конечно, отправлять
	 * number, brand, model, color и year
	 * На остальные комбинации не пройдет добавление
	 * в базу.
	 * Можно добавить, но я смысла в этом не вижу.
	 */
	@PostMapping(path = "/create")
	Response createCar(@RequestBody Car car) {
		return carService.createCar(car);
	}

	@DeleteMapping(path = "/delete_by_id")
	Response deleteById(@RequestBody Id_Car id){
		return carService.deleteById(id.getId());
	}

	@DeleteMapping(path = "/delete_by_number")
	Response deleteByNumber(@RequestBody Number_Car number){
		return carService.deleteByNumber(number.getNumber());
	}

	/**
	 * @param sortingOptions - список полей,
	 * по которым пройдет сортировка по порядку,
	 * который передаст клиент.
	 * Только active передавать смысла нет,
	 * тк проверяется только на "неудаленных"
	 *                       	записях.
	 * @return - отсортированный список
	 */
	@PostMapping(path = "/sort")
	List<Car> sortCars(@RequestBody SortingOptions sortingOptions){
		return carService.sortsListCars(sortingOptions);
	}

	/**
	 * @param filters - это класс,
	 * который содержит список каждого из
	 * полей, которые пригодны для фильтра:
	 *    Номера
	 *    Марки
	 *    Модели
	 *    Цвета
	 *    Года
	 *    Создание записи в формате "dd-MM-yyyy"
	 * @return - массив соответствующий фильтрам
	 */
	@PostMapping(path = "/filter")
	List<Car> filteringCars(@RequestBody Filters filters){
		return carService.filteringListCars(filters);
	}

	/**
	 * Проверяет соответствие введенных
	 * данных о машине с данными от
	 * сайта avtocod.ru
	 *
	 * @return - Список элементов, состоящих из
	 * булева значения по отношению к данным об
	 * автомобиле из базы данных и данные по
	 * проверке номеров на avtocod.ru
	 */
	@GetMapping(path = "/statistics")
	ResultAfterTranslate[] getStatistics(){
		return carService.statisticCars();
	}
}
