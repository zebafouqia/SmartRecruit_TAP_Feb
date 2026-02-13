package com.project.hook;

import java.util.Properties;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import com.project.driverfactory.DriverFactory;
import com.project.utils.CommonUtils;
import com.project.utils.ConfigReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class MyHooks extends DriverFactory {

    private static Logger logger = LogManager.getLogger(MyHooks.class);
    WebDriver driver;

    @Before
    public void setup() {

        ConfigReader config = ConfigReader.getInstance();
        DriverFactory.initializeBrowser(config.getBrowser());
        driver = DriverFactory.getDriver();
        driver.get(config.getBaseUrl());
        logger.info("url loaded in browser " + config.getBaseUrl());

    }

    @After
    public void tearDown(Scenario scenario) {

        String scenarioName = scenario.getName().replaceAll(" ", "_");

        if (scenario.isFailed()) {

            byte[] srcScreenshot = CommonUtils.takeScreenShot(scenario, driver, scenarioName);
            scenario.attach(srcScreenshot, "image/png", scenarioName);
            logger.info("scenario failed");
        }

        driver.quit();
        logger.info("driver  quit");

    }

}
