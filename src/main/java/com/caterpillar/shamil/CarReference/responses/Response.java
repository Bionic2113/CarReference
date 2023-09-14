package com.caterpillar.shamil.CarReference.responses;

import com.caterpillar.shamil.CarReference.models.Car;
import lombok.Getter;
import lombok.Setter;

/**
 * <h3>CarReference</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 10.09.2023
 */
@Getter
@Setter
public class Response {

    private String result;

    private Car car;
}
