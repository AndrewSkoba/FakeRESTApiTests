package com.bookstore.data;

import org.testng.annotations.DataProvider;

public class AuthorDataProvider {
    @DataProvider
    public Object[][] invalidAuthorIds() {
        return new Object[][]{
                {-1},
                {0},
                {Integer.MAX_VALUE}
        };
    }
}
