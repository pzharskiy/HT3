package com.epam.ht3.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProjectPage extends AbstractPage {

    private final Logger logger = LogManager.getRootLogger();
    //Ссылку на настройки можно искать так:
    @FindBy(xpath = ".//*[@id='js-repo-pjax-container']/div[1]/nav/a[4]")
    private WebElement linkSettings;
    //Или искать динамично по переданному имени пользователя и названия репозитория

    public ProjectPage(WebDriver driver)
    {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    public ProjectSettingsPage setting(String repo, String name){
        driver.findElement(By.xpath("//a[starts-with(@href, \"/"+name+"/"+repo+"/settings\")]")).click();
        return PageFactory.initElements(driver, ProjectSettingsPage.class);
    }

    @Override
    public void openPage() {

    }
}
