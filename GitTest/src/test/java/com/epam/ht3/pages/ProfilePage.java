package com.epam.ht3.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProfilePage extends AbstractPage {

    private String name;
    private final Logger logger = LogManager.getRootLogger();
    private final String BASE_URL = "https://github.com/";

    @FindBy(className = "Counter")
    private WebElement counter;

    public ProfilePage(WebDriver driver, String name) {
        super(driver);
        PageFactory.initElements(this.driver, this);
        this.name = name;
    }

    public ProfilePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver, this);

    }

    public void openPage() {
        driver.navigate().to(BASE_URL+name);
        logger.info("Profile page opened");
    }

    public int getCounter() {
        return Integer.parseInt(counter.getText());
    }

    public String getName() {
        return name;
    }
}