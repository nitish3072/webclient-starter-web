package com.nitish.web;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;
import java.util.function.Function;

// Considering response type is a Map
public class MonoCallback {

    public static Mono<DefaultWebResultResponse<Map>> createMonoOutput(HttpMethod method, URI uri, HttpHeaders headers, Map<String, Object> map) {
        WebClient.RequestBodySpec response = WebClient.builder()
                .build()
                .method(method)
                .uri(uri);
        if(map!=null && !map.isEmpty()) {
            response.body(BodyInserters.fromValue(map));
        }
        response.headers(httpHeaders -> httpHeaders.addAll(headers));
        return response.exchangeToMono(exchangeAndHandleDefaultToMono());
    }

    private static Function<ClientResponse, Mono<DefaultWebResultResponse<Map>>> exchangeAndHandleDefaultToMono() {
        return clientResponse -> {
            if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                return clientResponse.bodyToMono(Map.class).map(abstractWebResultResponse -> {
                    DefaultWebResultResponse<Map> defaultWebResultResponse = new DefaultWebResultResponse<>(HttpStatus.OK, abstractWebResultResponse);
                    return defaultWebResultResponse;
                });
            } else if (clientResponse.statusCode().is4xxClientError()
                    || clientResponse.statusCode().is5xxServerError()) {
                return clientResponse.bodyToMono(Map.class).map(abstractWebResultResponse -> {
                    DefaultWebResultResponse<Map> defaultWebResultResponse = new DefaultWebResultResponse<>(HttpStatus.valueOf(clientResponse.statusCode().value()), abstractWebResultResponse);
                    return defaultWebResultResponse;
                });
            } else {
                return clientResponse.createException()
                        .flatMap(Mono::error);
            }
        };
    }

}
