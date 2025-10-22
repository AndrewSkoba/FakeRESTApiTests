package com.bookstore.tests;

import com.bookstore.client.AuthorClient;
import com.bookstore.data.AuthorDataProvider;
import com.bookstore.datagenerator.AuthorGenerator;
import com.bookstore.models.Author;
import com.bookstore.models.ErrorObject;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

@Epic("Bookstore API Automation")
@Feature("Authors API")
@Slf4j
public class AuthorTests extends BaseTest {

    @Autowired
    private AuthorClient authorClient;

    @Test
    @Story("Get All Authors")
    @Description("Verify that all authors can be retrieved successfully")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAllAuthors() {
        List<Author> authors = authorClient.getAllAuthors();

        Assert.assertNotNull(authors, "Authors list should not be null");
        Assert.assertFalse(authors.isEmpty(), "Authors list should not be empty");
    }

    @Test
    @Story("Get Author by ID")
    @Description("Verify that a specific author can be retrieved by their ID")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAuthorById() {
        Author createdAuthor = authorClient.createAuthor(AuthorGenerator.createRandomAuthor());

        Author actual = authorClient.getAuthorById(createdAuthor.getId());
        Assert.assertEquals(createdAuthor, actual, "Author objects are different");
    }

    @Test
    @Story("Create New Author")
    @Description("Verify that a new author can be created successfully")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateAuthor() {
        Author authorToBeCreated = AuthorGenerator.createRandomAuthor();
        Author created = authorClient.createAuthor(authorToBeCreated);
        Assert.assertEquals(authorToBeCreated, created, "Author objects are different");
    }

    @Test(dataProvider = "invalidAuthorIds", dataProviderClass = AuthorDataProvider.class)
    @Story("Get Author with Invalid ID")
    @Description("Verify error handling when requesting an author with invalid ID")
    @Severity(SeverityLevel.NORMAL)
    public void testGetAuthorWithInvalidId(int authorId) {
        ErrorObject actual = authorClient.getAuthorNotFound(authorId);
        ErrorObject expected = ErrorObject.builder().title("Not Found").type("https://tools.ietf.org/html/rfc7231#section-6.5.4").status(404).traceId("00-56af9237292c494ea455949735de1a4e-b3ea36a9335b1048-00").build();
        Assert.assertEquals(actual, expected, "Received wrong error object");
    }
}