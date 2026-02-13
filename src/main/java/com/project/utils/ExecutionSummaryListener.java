package com.project.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExecutionSummaryListener implements ITestListener {

    private int passed = 0;
    private int failed = 0;
    private int skipped = 0;

    @Override
    public void onTestSuccess(ITestResult result) {
        passed++;
    }

    @Override
    public void onTestFailure(ITestResult result) {
        failed++;
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        skipped++;
    }

    @Override
    public void onFinish(ITestContext context) {
        int total = passed + failed + skipped;
        System.out.println("=================================================");
        System.out.println("   AUTOMATED TEST EXECUTION SUMMARY");
        System.out.println("=================================================");
        System.out.println(" Total Scenarios Executed : " + total);
        System.out.println(" Passed Scenarios         : " + passed);
        System.out.println(" Failed Scenarios         : " + failed);
        System.out.println(" Skipped Scenarios        : " + skipped);
        System.out.println("=================================================");
        System.out.println(" Reports Generated at: target/ExtentReports/SparkReport.html");
        System.out.println("                    target/CucumberReports/CucumberReport-TestNG.html");
        System.out.println("=================================================");
    }
}
