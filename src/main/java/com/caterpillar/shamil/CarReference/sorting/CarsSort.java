package com.caterpillar.shamil.CarReference.sorting;

import com.caterpillar.shamil.CarReference.models.Car;
import com.caterpillar.shamil.CarReference.requests.SortingOptions;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

/**
 * <h3>CarReference</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 11.09.2023
 */
public class CarsSort {
    /**
     * @param cars - список неудаленных машин
     * @param sortingOptions - список колонок
     *                         для сортировки
     * Применяется рефлексия, тк поля могут
     * добавляться и удаляться, поэтому она применяется,
     * чтобы не исправлять всё время код.
     * Правда толку нет от active колонки, тк все записи
     * будут "неудаленные"
     * @return - остортированный список
     */
    public List<Car> mySort(List<Car> cars, SortingOptions sortingOptions) {
        System.out.println(sortingOptions.getOptions());
        cars.sort((o1, o2) -> compare(sortingOptions.getOptions().iterator(), o1, o2));
        return cars;
    }
    private int compare(Iterator<String> iter, Car c1, Car c2) {
        if (iter.hasNext()) {
            String f = iter.next();
            try {
                Field field = Car.class.getDeclaredField(f);
                field.setAccessible(true);
                Comparable c1_field = (Comparable) field.get(c1);
                Comparable c2_field = (Comparable) field.get(c2);

                int result = c1_field.compareTo(c2_field);
                return result != 0 ? result : compare(iter, c1, c2);
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }
}
