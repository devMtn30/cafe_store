package com.payhere.kimsan.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ElasticsearchIndexInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ElasticsearchIndexInitializer.class);

    @Value("${spring.elasticsearch.rest.uris}")
    private String elasticSearchUrl;

    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchIndexInitializer.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("Initializing ElasticSearch...");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            String firstEndpoint = elasticSearchUrl + "/product/";
            String firstRequestBody = "{\n  \"settings\": {\n    \"index\": {\n      \"analysis\": {\n        \"filter\": {\n          \"suggest_filter\": {\n            \"type\": \"edge_ngram\",\n            \"min_gram\": 1,\n            \"max_gram\": 50\n          }\n        },\n        \"tokenizer\": {\n          \"jaso_search_tokenizer\": {\n            \"type\": \"jaso_tokenizer\",\n            \"mistype\": true,\n            \"chosung\": true\n          },\n          \"jaso_index_tokenizer\": {\n            \"type\": \"jaso_tokenizer\",\n            \"mistype\": true,\n            \"chosung\": true\n          }\n        },\n        \"analyzer\": {\n          \"suggest_search_analyzer\": {\n            \"type\": \"custom\",\n            \"tokenizer\": \"jaso_search_tokenizer\"\n          },\n          \"suggest_index_analyzer\": {\n            \"type\": \"custom\",\n            \"tokenizer\": \"jaso_index_tokenizer\",\n            \"filter\": [\n              \"suggest_filter\"\n            ]\n          }\n        }\n      }\n    }\n  }\n}'";

            String firstResponse = restTemplate.exchange(firstEndpoint, HttpMethod.PUT,
                new HttpEntity<>(firstRequestBody, headers), String.class).getBody();
            log.info("First PUT request response: {}", firstResponse);
        } catch (HttpClientErrorException.BadRequest e) {
            log.warn("Ignoring 400 error. ElasticSearch already initialized.");
        } catch (HttpClientErrorException e) {
            log.error(e.getLocalizedMessage());
        } finally {
            try {
                String secondEndpoint = elasticSearchUrl + "/product/_mapping";
                String secondRequestBody = "{\n  \"properties\": {\n    \"name\": {\n      \"type\": \"text\",\n      \"store\": true,\n      \"analyzer\": \"suggest_index_analyzer\",\n      \"search_analyzer\": \"suggest_search_analyzer\"\n    }\n  }\n}'";

                String secondResponse = restTemplate.exchange(secondEndpoint, HttpMethod.PUT,
                    new HttpEntity<>(secondRequestBody, headers), String.class).getBody();
                log.info("Second PUT request response: {}", secondResponse);
            } catch (HttpClientErrorException.BadRequest e) {
                log.warn("Ignoring 400 error. ElasticSearch already initialized.");
            } catch (HttpClientErrorException e) {
                log.error(e.getLocalizedMessage());
            }
        }
    }
}
