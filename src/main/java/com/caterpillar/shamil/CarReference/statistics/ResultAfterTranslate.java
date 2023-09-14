package com.caterpillar.shamil.CarReference.statistics;

import lombok.*;

/**
 * <h3>CarReference</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 13.09.2023
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResultAfterTranslate {

    private boolean match;

    private String actual;

    private String expected;

}
