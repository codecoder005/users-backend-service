package com.company.controller.rest;

import com.company.model.request.AmountTransferRequest;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/kafka")
@RequiredArgsConstructor
@Slf4j
public class KafkaTestingController {
    private final Gson jsonHelper;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String KAFKA_ANACONDA_CONSUMERS_GROUP = "anaconda-consumers-group";

    // private static final String KAFKA_TOPIC_NAME = "my-first-kafka-topic";
    private static final String KAFKA_TOPIC_NAME = "json-requests-topic";

    @PostMapping(value = "/publish", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> publish(@RequestBody AmountTransferRequest request) {
        LocalDateTime now = LocalDateTime.now();
        publishToKafkaTopic(request).whenComplete((result, throwable) -> {
            if (throwable == null) {
                System.out.println("Sent request=[" + request +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send request=[" +
                        request + "] due to : " + throwable.getMessage());
            }
        });
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "timestamp", now,
                        "status", "PUBLISHED"
                ));
    }

    private CompletableFuture<SendResult<String, Object>> publishToKafkaTopic(AmountTransferRequest request) {
        return kafkaTemplate.send(KAFKA_TOPIC_NAME, request);
    }
    @KafkaListener(topics = KAFKA_TOPIC_NAME, groupId = KAFKA_ANACONDA_CONSUMERS_GROUP)
    public void anacondaConsume(AmountTransferRequest message) {
        log.info("anaconda-consumers consuming messages: {}", jsonHelper.toJson(message));
    }
}
