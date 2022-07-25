package com.nl.cgi.bff.mockdata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nl.cgi.bff.exception.ExceptionResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static reactor.netty.http.client.HttpClient.create;

public class StubServerConfig {

    public static final ExceptionResponse PS_BAD_REQUEST = new ExceptionResponse("AS400", "Bad request");
    public static final ExceptionResponse PS_SERVER_ERROR = new ExceptionResponse("AS500", "Could not connect to Authorization Service");


    public static MockWebServer mockServer;
    public static ObjectMapper objectMapper = new ObjectMapper();
    public WebClient webClient;

    /**
     * Function will give proper output only
     * when service bean initialized inside @BeforeEach.
     * If not, resist using this assertion
     */
    public static void assertCalledAtLeastOnce() {
        assertEquals(1, mockServer.getRequestCount());
    }

    /**
     * Function will give proper output only
     * when service bean initialized inside @BeforeEach.
     * If not, resist using this assertion
     */
    public static void assertCalledAtLeast(int times) {
        assertEquals(times, mockServer.getRequestCount());
    }

    /**
     * Function will give proper output only
     * when service bean initialized inside @BeforeEach.
     * If not, resist using this assertion
     */
    public static void assertRequestContains(List<String> matches) throws InterruptedException {
        String request = Objects.requireNonNull(mockServer.takeRequest(1, TimeUnit.SECONDS)).getBody().readUtf8();
        matches.forEach(string -> assertTrue(request.contains(string)));
    }

    /**
     * Method must be called inside @BeforeEach / init method
     **/
    public void setUpMockServer() throws IOException {
        mockServer = new MockWebServer();
        mockServer.start();
        webClient = WebClient.builder()
                .baseUrl(mockServer.url("/").toString())
                .clientConnector(new ReactorClientHttpConnector(create()))
                .build();
    }

    /**
     * Method must be called inside @AfterEach / tearDown method
     **/
    public void tearDownMockServer() throws IOException {
        mockServer.shutdown();
    }

    public void mockServerWithResponse(MockResponse response) {
        mockServer.enqueue(response);
    }

    @BeforeEach
    public void setupStubServer() throws IOException {
        setUpMockServer();
    }

    @AfterEach
    public void tearDownStubServer() throws IOException {
        tearDownMockServer();
    }

    public static class ResponseBuilder<T> {
        public MockResponse mockResponseFor(T responseFormat) throws JsonProcessingException {
            return new MockResponse().setResponseCode(200)
                    .setBody(objectMapper.writeValueAsString(responseFormat))
                    .addHeader("Content-Type", "application/json");
        }
    }

    public static class ResponseBuilderVoid<T> {
        public MockResponse mockResponseForVoid() {
            return new MockResponse()
                    .addHeader("Content-Type", "application/json");
        }
    }

    public static class ErrorResponseBuilder<T> {
        public MockResponse mockErrorResponseFor(T responseFormat, int statusCode) throws JsonProcessingException {
            return new MockResponse().setResponseCode(statusCode)
                    .setBody(objectMapper.writeValueAsString(responseFormat))
                    .addHeader("Content-Type", "application/json");
        }
    }
}