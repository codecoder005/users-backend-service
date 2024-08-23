package com.company.api;

import com.company.model.response.PingAPIResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * PingAPI is a REST API interface for health check operations.
 * It defines an endpoint to verify that the service is up and running.
 */
public interface PingAPI {
    /**
     * Endpoint to check the health of the application.
     *
     * @return a {@link PingAPIResponse} containing the status code,
     * a message indicating the status, and the timestamp of the response.
     */
    @GetMapping(value = "/ping", produces = {MediaType.APPLICATION_JSON_VALUE})
    PingAPIResponse ping();
}
