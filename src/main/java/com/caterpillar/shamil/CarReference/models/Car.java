package com.caterpillar.shamil.CarReference.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name="cars")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Car {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(unique = true)
  private String number;

  @Column
  private String brand;

  @Column
  private String model;

  @Column
  private String color;

  @Column
  private int year;

  @Column
  private String avtocod_link;

  @Column
  private String autoru_link;

  @Column
  private boolean active = true;

  @Column
  private Long created = System.currentTimeMillis();

  public Car(String number, String brand, String model, String color, int year) {
    this.number = number;
    this.brand = brand;
    this.model = model;
    this.color = color;
    this.year = year;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Car car = (Car) o;
    return year == car.year && active == car.active && Objects.equals(number, car.number) && Objects.equals(brand, car.brand) && Objects.equals(model, car.model) && Objects.equals(color, car.color) && Objects.equals(avtocod_link, car.avtocod_link) && Objects.equals(autoru_link, car.autoru_link);
  }

  @Override
  public int hashCode() {
    return Objects.hash(number, brand, model, color, year, avtocod_link, autoru_link, active, created);
  }
}
