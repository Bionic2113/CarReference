package com.caterpillar.shamil.CarReference.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * <h3>CarReference</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 12.09.2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Filters {
    private List<String> numbers;
    private List<String> brands;
    private List<String> models;
    private List<String> colors;
    private List<Integer> years;
    private List<String> created;
}
