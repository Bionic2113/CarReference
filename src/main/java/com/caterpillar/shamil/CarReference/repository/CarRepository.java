package com.caterpillar.shamil.CarReference.repository;

import com.caterpillar.shamil.CarReference.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

  List<Car> findByActiveTrue();

  Optional<Car> findByNumberAndActiveTrue(String number);

  @Query(value = "UPDATE cars set active = false where id = :id and active = true returning *",
          nativeQuery = true)
  Optional<Car> deleteBy_Id(@Param("id") int id);

  @Query(value = "UPDATE cars set active = false where number = :number and active = true returning *",
          nativeQuery = true)
  Optional<Car> deleteByNumber(@Param("number") String number);

  @Query(value = "INSERT INTO cars (number, brand, model, color, year, avtocod_link, autoru_link, active, created) "
          + "SELECT :#{#car.number}, :#{#car.brand}, :#{#car.model}, :#{#car.color}, :#{#car.year}, :#{#car.avtocod_link}, "
          + ":#{#car.autoru_link}, :#{#car.active}, :#{#car.created} "
          + "WHERE NOT EXISTS (SELECT 1 FROM cars WHERE number = :#{#car.number})"
          + "RETURNING *", nativeQuery = true)
  Optional<Car> createCarIfNotExists(@Param("car") Car car);
}
