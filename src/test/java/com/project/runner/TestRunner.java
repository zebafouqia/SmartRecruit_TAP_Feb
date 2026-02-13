package com.project.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(features = "src/test/resources/features", glue = { "com.project.hook",
        "com.project.stepdefinitions" }, plugin = {
                "pretty",
                "summary",
                "html:target/CucumberReports/CucumberReport-TestNG.html",
                "json:target/cucumber-testng.json",
                "junit:target/cucumber-testng.xml",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        }, monochrome = true, tags = "@smoke", dryRun = false, publish = false)
public class TestRunner extends AbstractTestNGCucumberTests {

    /**
     * Scenarios data provider for parallel execution
     * Set parallel = true for parallel execution
     * 
     * @return Object[][] of scenarios
     */
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}