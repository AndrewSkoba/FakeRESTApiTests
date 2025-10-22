package com.bookstore.client;

import com.bookstore.models.Book;
import com.bookstore.utils.JsonUtil;
import io.restassured.common.mapper.TypeRef;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;

@Component
public class BookClient extends ApiBaseClient {
    private static final String BASE_PATH = "/api/v1/Books";
    private static final String BOOK_BY_ID = BASE_PATH + "/{id}";

    public List<Book> getAllBooks() {
        return get(BASE_PATH, SC_OK, r -> r.as(new TypeRef<List<Book>>() {
        }));
    }

    public Book getBookById(String id) {
        return get(BOOK_BY_ID, Map.of("id", id), null, SC_OK, r -> r.as(Book.class));
    }

    public Book createBook(Book body) {
        return post(BASE_PATH, JsonUtil.toJson(body), SC_CREATED, r -> r.as(Book.class));
    }

    public Book updateBook(Book body, String id) {
        return put(BOOK_BY_ID, JsonUtil.toJson(body), Map.of("id", id), SC_OK, r -> r.as(Book.class));
    }

    public void deleteBook(String id, int statusCode) {
        delete(BOOK_BY_ID, Map.of("id", id), statusCode);
    }
}
