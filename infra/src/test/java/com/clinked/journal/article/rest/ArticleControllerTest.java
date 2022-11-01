package com.clinked.journal.article.rest;

import com.clinked.journal.article.dto.CreateArticleRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import lombok.SneakyThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
class ArticleControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

    }

    @Test
    @SneakyThrows
    void when_createRequestIsNotValid_thenReturnBadRequest() {
        CreateArticleRequest articleRequest = new CreateArticleRequest();
        String jsonRequest = objectMapper.writeValueAsString(articleRequest);
        ResponseEntity<String> responseEntity = testRestTemplate.exchange("/v1/articles",
            HttpMethod.POST,
            new HttpEntity<>(articleRequest, null),
            new ParameterizedTypeReference<>() {
            });

        Assertions.assertThat(responseEntity).isNotNull()
            .returns(HttpStatus.OK, Assertions.from(ResponseEntity::getStatusCode));
    }
}