package com.nitish.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.function.Function;

public class MonoCallback {

    public static <T> Mono<DefaultWebResultResponse<T>> createMonoOutput(HttpMethod method, URI uri, HttpHeaders headers, MultiValueMap<String, Object> allReqParams, Function<ClientResponse, AbstractWebResultResponse> handlerFunction) {
        WebClient.RequestHeadersSpec<?> response = WebClient.builder()
                .filter(responseFilter())
                .build()
                .method(method)
                .uri(uri)
                .body(BodyInserters.fromMultipartData(allReqParams))
                .headers(httpHeaders -> httpHeaders.addAll(headers));
        return response.exchangeToMono(exchangeAndHandleToMono(handlerFunction));
    }

    public static <T> Mono<DefaultWebResultResponse<T>> createMonoOutput(HttpMethod method, URI uri, HttpHeaders headers, MultiValueMap<String, Object> allReqParams) {
        WebClient.RequestHeadersSpec<?> response = WebClient.builder()
                .filter(responseFilter())
                .build()
                .method(method)
                .uri(uri)
                .body(BodyInserters.fromMultipartData(allReqParams))
                .headers(httpHeaders -> httpHeaders.addAll(headers));
        return response.exchangeToMono(exchangeAndHandleDefaultToMono());
    }

    public static Function<ClientResponse, Mono<? extends AbstractWebResultResponse>> exchangeAndHandleToMono(Function<ClientResponse, AbstractWebResultResponse> function) {
        return clientResponse -> Mono.just(function.apply(clientResponse));
    }

    public static Function<ClientResponse, Mono<? extends AbstractWebResultResponse>> exchangeAndHandleDefaultToMono() {
        return clientResponse -> {
            if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                clientResponse.body(BodyExtractors.)
                return clientResponse.bodyToMono(DefaultWebResultResponse.class);
            } else if (clientResponse.statusCode().is4xxClientError()
                    || clientResponse.statusCode().is5xxServerError()) {
                return clientResponse.createException()
                        .flatMap(Mono::error);
            } else {
                return clientResponse.createException()
                        .flatMap(Mono::error);
            }
        };
    }

    public static ExchangeFilterFunction responseFilter() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            ClientResponse.Headers headers = clientResponse.headers();
            MediaType cType = getMediaType(headers);
            String contentDisposition = getContentDisposition(headers);
            String acceptRanges = getAcceptRanges(headers);
            ClientResponse.Builder cr = clientResponse.mutate()
                    .body(clientResponse.body(BodyExtractors.toDataBuffers()));

            if (cType != null) {
                if (contentDisposition != null) {
                    if (acceptRanges != null) {
                        cr.header("Content-Disposition", contentDisposition)
                                .header("Accept-Ranges", acceptRanges)
                                .header("Content-Type", headers.contentType().get().getType());
                    } else
                        cr.header("Content-Disposition", contentDisposition)
                                .header("Content-Type", headers.contentType().get().getType());
                } else {
                    cr.header("Content-Type", headers.contentType().get().getType());
                }

            }
            return Mono.just(cr.build());
        });
    }

    public static String getAcceptRanges(ClientResponse.Headers headers) {
        List<String> value = headers.header("Accept-Ranges");
        if (value == null || value.size() == 0) {
            return null;
        } else {
            return value.get(0);
        }
    }

    public static String getContentDisposition(ClientResponse.Headers headers) {
        List<String> value = headers.header("Content-Disposition");
        if (value == null || value.size() == 0) {
            return null;
        } else {
            return value.get(0);
        }
    }

    public static MediaType getMediaType(ClientResponse.Headers headers) {
        return headers.contentType().orElse(null);
    }

}
