package com.project.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = { "com.project.hook",
                "com.project.stepdefinitions" }, plugin = {
                                "pretty",
                                "summary",
                                "html:target/CucumberReports/CucumberReport-JUnit.html",
                                "json:target/cucumber-junit.json",
                                "junit:target/cucumber-junit.xml",
                                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
                }, monochrome = true, tags = "@smoke", dryRun = false, publish = false)
public class TestRunnerJUnit {
        // This class will be empty - Cucumber uses annotations for configuration

        /**
         * No explicit test methods needed
         * Cucumber JUnit runner automatically discovers and executes scenarios
         */
}