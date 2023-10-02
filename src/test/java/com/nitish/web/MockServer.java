package com.nitish.web;

import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.TestConfiguration;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.IOException;

@TestConfiguration
public class MockServer {

    public static MockWebServer mockBackEnd;

    @PostConstruct
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @PreDestroy
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

}
