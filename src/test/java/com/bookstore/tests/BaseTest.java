package com.bookstore.tests;

import com.bookstore.config.ApiConfig;
import com.bookstore.listener.ConfigListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = ApiConfig.class)
@TestExecutionListeners(value = {ConfigListener.class}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public abstract class BaseTest extends AbstractTestNGSpringContextTests {



}