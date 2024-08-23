package com.company.controller.rest;

import com.company.api.PingAPI;
import com.company.model.Post;
import com.company.model.response.PingAPIResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/jsonplaceholder")
@RequiredArgsConstructor
@Slf4j
public class JsonPlaceHolderController implements PingAPI {
    private final RestTemplate restTemplate;

    @Override
    public PingAPIResponse ping() {
        log.info("JsonPlaceHolderController::ping");
        return PingAPIResponse.builder()
                .status(200)
                .message("Up and Healthy")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @CircuitBreaker(name = "todoById", fallbackMethod = "todoByIdFallbackMethod")
    public ResponseEntity<Post> todoById() {
        log.info("JsonPlaceHolderController::todoById");
        return restTemplate.getForEntity(
                "https://jsonplaceholder.typicode.com/posts/1",
                Post.class
        );
    }

    public ResponseEntity<String> todoByIdFallbackMethod(Throwable throwable) {
        log.info("JsonPlaceHolderController::todoByIdFallbackMethod");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(throwable.getMessage());
    }
}
