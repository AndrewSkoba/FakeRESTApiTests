package com.bookstore.client;

import com.bookstore.utils.JsonUtil;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

import static io.restassured.RestAssured.given;

@Component
public abstract class ApiBaseClient {

    @Autowired
    protected RequestSpecification requestSpec;

    protected <T> T get(String path, int statusCode, Function<Response, T> mapper) {
        return execute(HttpMethods.GET, path, null, null, null, statusCode, mapper);
    }

    protected <T> T get(String path,
                        Map<String, Object> pathParams,
                        Map<String, Object> queryParams,
                        int statusCode,
                        Function<Response, T> mapper) {
        return execute(HttpMethods.GET, path, null, pathParams, queryParams, statusCode, mapper);
    }

    protected <T> T post(String path, Object body, int statusCode, Function<Response, T> mapper) {
        return execute(HttpMethods.POST, path, body, null, null, statusCode, mapper);
    }

    protected <T> T put(String path, Object body, Map<String, Object> pathParams, int statusCode, Function<Response, T> mapper) {
        return execute(HttpMethods.PUT, path, body, null, null, statusCode, mapper);
    }

    protected void delete(String path, Map<String, Object> pathParams, int statusCode) {
        execute(HttpMethods.DELETE, path, null, null, null, statusCode, r -> null);
    }

    private <T> T execute(HttpMethods method,
                          String path,
                          Object body,
                          Map<String, Object> pathParams,
                          Map<String, Object> queryParams,
                          int expectedStatus,
                          Function<Response, T> mapper) {

        RequestSpecification currentSpec = given().spec(requestSpec);

        if (pathParams != null) {
            currentSpec.pathParams(pathParams);
        }
        if (queryParams != null) {
            currentSpec.queryParams(queryParams);
        }
        if (body != null) {
            currentSpec.body(JsonUtil.toString(body));
        }

        Response response = method.execute(currentSpec, path).extract().response();

        int actualStatus = response.getStatusCode();
        if (actualStatus != expectedStatus) {
            throw new AssertionError(String.format(
                    "Expected HTTP status %d but got %d for %s %s. Response body: %s",
                    expectedStatus, actualStatus, method.name(), path, response.getBody().asString()
            ));
        }

        return mapper.apply(response);
    }
}
