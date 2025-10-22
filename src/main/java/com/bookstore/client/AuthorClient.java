package com.bookstore.client;

import com.bookstore.models.Author;
import com.bookstore.models.ErrorObject;
import com.bookstore.utils.JsonUtil;
import io.restassured.common.mapper.TypeRef;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static org.apache.http.HttpStatus.*;

@Component
public class AuthorClient extends ApiBaseClient {
    private static final String BASE_PATH = "/api/v1/Authors";
    private static final String AUTHOR_BY_ID = BASE_PATH + "/{id}";
    private static final String GET_BOOK_AUTHORS = BASE_PATH + "/authors/books/{idBook}";

    public List<Author> getAllAuthors() {
        return get(BASE_PATH, SC_OK, r -> r.as(new TypeRef<List<Author>>() {
        }));
    }

    public Author getAuthorById(int id) {
        return get(AUTHOR_BY_ID, Map.of("id", id), null, SC_OK, r -> r.as(Author.class));
    }

    public List<Author> getBookAuthors(int idBook) {
        return get(GET_BOOK_AUTHORS, Map.of("idBook", idBook), null, SC_OK, r -> r.as(new TypeRef<List<Author>>() {
        }));
    }

    public Author createAuthor(Author author) {
        return post(BASE_PATH, author, SC_CREATED, r -> r.as(Author.class));
    }

    public Author updateAuthor(Author body, String id) {
        return put(AUTHOR_BY_ID, JsonUtil.toJson(body), Map.of("id", id), SC_OK, r -> r.as(Author.class));
    }

    public void deleteAuthor(String id, int statusCode) {
        delete(AUTHOR_BY_ID, Map.of("id", id), statusCode);
    }

    public ErrorObject getAuthorNotFound(int id) {
        return get(AUTHOR_BY_ID, Map.of("id", id), null, SC_NOT_FOUND, r -> r.as(ErrorObject.class));
    }
}
