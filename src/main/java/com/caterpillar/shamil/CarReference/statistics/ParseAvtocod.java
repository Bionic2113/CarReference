package com.caterpillar.shamil.CarReference.statistics;

import com.caterpillar.shamil.CarReference.models.Car;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <h3>CarReference</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 13.09.2023
 */
@Component
public class ParseAvtocod {
    /**
     * Сайт очень капризный и постоянно считает,
     * что на него происходит DDoS-атака, хотя
     * я могу отправлять по одному запросу раз
     * в несколько минут.
     * Из за этого долго приходится тестировать.
     * @param cars - список "неудаленных" машин
     * @return - список полных названий
     * с сайта avtocod.ru, где пробелы
     * заменены на _
     */
    // TODO: 13.09.2023 Заменить avtocod.ru на менее капризный сайт если останется время
    // TODO: На данный момент проверял на 5 записях и после на четвертой получил 429 статус
    // TODO: Попробую ставить задержку в секунду, но врядли поможет
    public String[] parsingAvtocod(List<Car> cars){
        String cookie = "cf_chl_2=51d58d08a93acbd; cf_clearance=CVzq6WA1tnhtD5Ik3qQ_jX8llylL8cLmIwkH1JsMhRw-1694557775-0-1-e69b51ba.f4e77a1f.b19a0e5a-150.0.0; device_token=eyJpdiI6IjMzSGtIeldZU05FQ1ZkUWs4MGVrZGc9PSIsInZhbHVlIjoiOGQ3b0wwbmhnNWtSVnBUYTdvbm1salhnS2EyM09pSEQ2R2g0djFZakdkd2lneVdNR1N1SmFTYmJEU0R6SVZ3K01SNlJlamN0dExNUDIrc1BKZnJUT05aaHp0N0VpZytHRWNoTThXdkxYMWxlcVk0K05uRVVIY1RzUitJZEVuclMiLCJtYWMiOiI0ZThlOTgyOGMxOTk5NmM2Nzk3OWQ2NTBlOTBjNGZmNmFhNjViM2VlOWUwYWVkZTViYjMzMDU3MDQxYmRlMDljIiwidGFnIjoiIn0%3D; tmr_lvid=292965c5b8e0edd67cb2c4d894aa2812; tmr_lvidTS=1694557785290; _gid=GA1.2.273395536.1694557785; _ym_uid=169455778551517046; _ym_d=1694557785; _ym_visorc=b; _ym_isad=2; popmechanic_sbjs_migrations=popmechanic_1418474375998%3D1%7C%7C%7C1471519752600%3D1%7C%7C%7C1471519752605%3D1; mindboxDeviceUUID=2d3f3016-a2f0-4210-8a0e-46dadd284f6e; directCrm-session=%7B%22deviceGuid%22%3A%222d3f3016-a2f0-4210-8a0e-46dadd284f6e%22%7D; PAPVisitorId=8f528f25672906e43ba0zD040jK86Nq8; _gat_UA-89458376-1=1; XSRF-TOKEN=eyJpdiI6IlVhT3FIS1pyMTUwUVdWRXBmUks2NHc9PSIsInZhbHVlIjoiR1lWTFdCY09aS1JWNHJHVGEyd1B1ajJXeDBSUmVsVVI0ZFp1TUdpQmh5TldXNW9tQjBKaUxLbVBPZ2x3aE1XQVo1TDRBbnN3cGVKVEgwL2Q2VzViWk9oR0IrMGU4V0doY1o1R3VuT2dYZkkrSUgxT2gwRVpsU2tUMWx6UzVlaUMiLCJtYWMiOiJjMzk1YzgyN2VhYTgzZWI3YWYxMTZmOWU0NzQwNTJhZjFjYWViZjY3YTFjNjlmNTlhMmYzN2U2MGYwMmViOWZiIiwidGFnIjoiIn0%3D; avtocod_session=eyJpdiI6ImJLQzM0Sy9NTkVlMXBLT21YY09hc3c9PSIsInZhbHVlIjoiMWM5RElNNGN4cWd0dUxHRWhOTWR2Vit5bHNoN08xRjBBSG1TUnZnbzFTWS9hTUxBZXhhUlhNdFBEbVZpdWhzTmRxMGtwd2xnVUpScHdsdWVEd1pSLzI1bDIvNHpyWEdqV3lBdDRVQ2tWS2I2bFphTExGRDFoV1VlUkllNFlob1IiLCJtYWMiOiIxZDVmMGUwYTE4ZmYxMTE4ZDhkMGE4ZmZiZTlkNGJiZjJiMGQ4NjI0OGMwMzcxMjU3ZGE2YzQwNjVmY2E2N2MwIiwidGFnIjoiIn0%3D; _ga=GA1.2.1449024098.1694557785; _ga_GTZZVJTR2H=GS1.1.1694557785.1.1.1694558848.17.0.0; _ga_Y8W9XMW2CY=GS1.2.1694557785.1.1.1694558849.17.0.0; tmr_detect=0%7C1694558849916";
        String[] avtocodNames = new String[cars.size()];
        int index;
        for (index = 0; index < avtocodNames.length; index++){
                try {
                    Document document = Jsoup
                            .connect(cars.get(index).getAvtocod_link())
                            .header("Cookie", cookie)
                            .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
                            .get();
                    Element title = document.select("head > title").first();
                    avtocodNames[index] = Arrays.stream(title
                            .text()
                            .split(" "))
                            .skip(1)
                            .takeWhile(str -> !str.equals("по"))
                            .collect(Collectors.joining(" "))
                            .replaceAll(" ", "_")
                    ;
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return avtocodNames;
    }
}
