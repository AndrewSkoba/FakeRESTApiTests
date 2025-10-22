package com.bookstore.listener;

import io.qameta.allure.Allure;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class ConfigListener extends AbstractTestExecutionListener {

    @Override
    public void beforeTestClass(TestContext testContext) {
        Environment environment = testContext.getApplicationContext().getEnvironment();
        writeAllureEnvironment(environment);
    }

    private void writeAllureEnvironment(Environment environment) {
        Properties props = new Properties();

        if (environment instanceof AbstractEnvironment env) {
            for (PropertySource<?> propertySource : env.getPropertySources()) {
                if (propertySource.getName().contains("application.properties") &&
                        propertySource.getSource() instanceof Map<?, ?> map) {

                    for (Map.Entry<?, ?> entry : map.entrySet()) {
                        String key = String.valueOf(entry.getKey());
                        String value = String.valueOf(entry.getValue());
                        if (value != null && !value.isBlank()) {
                            props.setProperty(key, value);
                        }
                    }
                }
            }
        }

        props.setProperty("OS", System.getProperty("os.name"));
        props.setProperty("Java Version", System.getProperty("java.version"));
        props.setProperty("User", System.getProperty("user.name"));
        props.setProperty("Build Number", String.valueOf(System.getenv("GITHUB_RUN_NUMBER")));
        props.setProperty("Git Branch", String.valueOf(System.getenv("GITHUB_REF_NAME")));
        props.setProperty("Git Commit", String.valueOf(System.getenv("GITHUB_SHA")));
        props.setProperty("Pipeline URL", String.format(
                "https://github.com/%s/actions/runs/%s",
                System.getenv("GITHUB_REPOSITORY"),
                System.getenv("GITHUB_RUN_ID")
        ));
        props.setProperty("CI System", System.getenv("CI"));
        props.setProperty("CI Runner", System.getenv("RUNNER_NAME"));

        File dir = new File("target/allure-results");
        if (!dir.exists() && !dir.mkdirs()) {
            Allure.addAttachment("Allure Environment Error", "Failed to create allure-results directory");
            return;
        }

        try (FileOutputStream fos = new FileOutputStream(new File(dir, "environment.properties"))) {
            props.store(fos, "Allure Environment Settings (from application.properties)");
        } catch (IOException e) {
            Allure.addAttachment("Allure Environment Error",
                    "Failed to write environment.properties: " + e.getMessage());
        }
    }
}
