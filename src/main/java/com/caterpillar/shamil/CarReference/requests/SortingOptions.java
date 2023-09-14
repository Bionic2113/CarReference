package com.caterpillar.shamil.CarReference.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;

/**
 * <h3>CarReference</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 11.09.2023
 */
@NoArgsConstructor
public class SortingOptions {
    /**
     * Используется LinkedHashSet, чтобы
     * не было повторяющихся полей
     * и оставался нужный порядок
     */
    @Getter
    private LinkedHashSet<String> options;

    public SortingOptions(LinkedHashSet<String> options) {
        this.options = options;
    }
}
