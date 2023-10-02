package com.nitish.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.MultiValueMapAdapter;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static com.nitish.web.MockServer.mockBackEnd;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MockServer.class)
@TestPropertySource(locations = {"classpath:application.properties"})
public class IntegrationTest {

	ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void checkWebClientConnection() throws JsonProcessingException, URISyntaxException {
		Map<String, Object> resp = new HashMap<>();
		resp.put("a", 100);
		resp.put("b", true);
		resp.put("c", "Hello");

		mockBackEnd.enqueue(new MockResponse().setResponseCode(200)
				.setBody(objectMapper.writeValueAsString(resp))
				.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		);

		System.out.println(mockBackEnd.getRequestCount());

		URI uri = new URI("http", null, "localhost", mockBackEnd.getPort(),
				"", null, null);

		HttpHeaders httpHeaders = new HttpHeaders();

		Mono<DefaultWebResultResponse<Map>> mono =  MonoCallback.createMonoOutput(HttpMethod.GET, uri, httpHeaders, new MultiValueMapAdapter<>(new HashMap<>()));
		DefaultWebResultResponse<Map> defaultWebResultResponse = mono.block();
		System.out.println(objectMapper.writeValueAsString(defaultWebResultResponse.getData()));
		assertEquals(defaultWebResultResponse.getData(), resp);
	}

}
