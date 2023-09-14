package com.caterpillar.shamil.CarReference.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <h3>CarReference</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 12.09.2023
 */
@Log4j2
@Component
public class GoogleTranslate {
    static class Translation {
        @JsonProperty("translatedText")
        String translatedText;
        @JsonProperty("model")
        String model;
    }

    static class Data {
        @JsonProperty("translations")
        List<Translation> translations = new ArrayList<>();
    }

    static class GoogleTranslateResponse {
        @JsonProperty("data")
        Data data = new Data();
    }

    public ResultAfterTranslate[] translate(String[] carFullNames, String[] carNameInAdvtocod) {
        ResultAfterTranslate[] result = new ResultAfterTranslate[carFullNames.length];
        ExecutorService executorService = Executors.newFixedThreadPool(result.length);
            for (int i = 0; i < result.length; i++) {
                final int index = i;
                executorService.execute(() -> {
                    try {
                        result[index] = updateText(carFullNames[index], carNameInAdvtocod[index]);
                    } catch (IOException e) {
                        System.out.println("Error in "+ index +" call");
                        e.printStackTrace();
                    }
                });
            }
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    private ResultAfterTranslate updateText(String text, String expected) throws IOException {
        String link = "https://translate.googleapis.com/language/translate/v2?"
                + "format=text&"
                + "key=AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw&"
                + "source=ru&"
                + "target=en&"
                + "model=base&"
                + "q=" + expected;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(link);
        ObjectMapper objectMapper = new ObjectMapper();

        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = httpclient.execute(httpPost);

        HttpEntity responseEntity = response.getEntity();
        String responseJson = EntityUtils.toString(responseEntity);

        GoogleTranslateResponse googleResp = objectMapper.readValue(responseJson, GoogleTranslateResponse.class);
        log.log(Level.INFO, "Из парсинга: {}, из БД: {}",
                googleResp.data.translations.get(0).translatedText.toLowerCase(),
                text.toLowerCase());
        response.close();
        text = text.toLowerCase();
        expected = googleResp.data.translations.get(0).translatedText.replaceAll(" ", "_").toLowerCase();
        return new ResultAfterTranslate(
                text.equals(expected),
                text,
                expected
        );
    }
}
