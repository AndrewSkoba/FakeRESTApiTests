package com.bookstore.client;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.BiFunction;

@Getter
@AllArgsConstructor
public enum HttpMethods {
    GET(RequestSpecification::get),
    POST(RequestSpecification::post),
    DELETE(RequestSpecification::delete),
    PUT(RequestSpecification::put);

    private final BiFunction<RequestSpecification, String, Response> executor;

    public ValidatableResponse execute(RequestSpecification spec, String endpoint) {
        return executor.apply(spec, endpoint).then().assertThat();
    }
}
