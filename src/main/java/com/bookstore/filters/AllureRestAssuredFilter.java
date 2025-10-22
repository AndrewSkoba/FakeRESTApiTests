package com.bookstore.filters;


import io.qameta.allure.Allure;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

import static com.bookstore.utils.ContentType.JSON;
import static com.bookstore.utils.ContentType.TXT;
import static java.lang.String.format;

public class AllureRestAssuredFilter implements Filter {

    @Override
    public Response filter(
            FilterableRequestSpecification requestSpec,
            FilterableResponseSpecification responseSpec,
            FilterContext ctx) {
        final String header = "Headers";
        final String body = "Body";
        final String noBody = "No body";

        Response response = ctx.next(requestSpec, responseSpec);

        StringBuilder requestLog = new StringBuilder();
        requestLog.append(requestSpec.getMethod())
                .append(" ")
                .append(requestSpec.getURI())
                .append(format("\n\n%s:\n", header))
                .append(requestSpec.getHeaders())
                .append(format("\n\n%s:\n", body))
                .append(requestSpec.getBody() != null ? requestSpec.getBody() : noBody);

        Allure.addAttachment("API Request", TXT.getType(), requestLog.toString(), TXT.getFileExtension());

        String responseLog = "Status code: " + response.getStatusCode() +
                format("\n\n%s:\n", header) + response.getHeaders() +
                format("\n\n%s:\n", body) + (response.getBody() != null ? response.getBody().asPrettyString() : noBody);

        Allure.addAttachment("API Response", JSON.getType(), responseLog, JSON.getFileExtension());

        return response;
    }
}
