package com.bookstore.tests;

import com.bookstore.client.BookClient;
import io.qameta.allure.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * Pretty similar class as Author. Just added a few titles cases to be covered
 */
@Epic("Bookstore API Automation")
@Feature("Authors API")
@Slf4j
public class BookTests extends BaseTest {


    @Autowired
    private BookClient client;

    @Test
    @Story("Get All Books")
    @Description("Verify that all books can be retrieved successfully")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAllBooks() {

    }

    @Test
    @Story("Get Book by ID")
    @Description("Verify that specific books can be retrieved by their IDs using data provider")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetBookById() {

    }

    @Test
    @Story("Create New Book")
    @Description("Verify that a new book can be created successfully")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateBook() {

    }

    @Test
    @Story("Update Existing Book")
    @Description("Verify that an existing book can be updated successfully")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateBook() {

    }

    @Test
    @Story("Delete Book")
    @Description("Verify that a book can be deleted successfully")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteBook() {

    }

    @Test
    @Story("Delete Non-existent Book")
    @Description("Verify error handling when deleting a non-existent book")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteNonExistentBook() {

    }

}