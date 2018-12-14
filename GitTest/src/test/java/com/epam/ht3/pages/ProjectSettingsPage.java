package com.epam.ht3.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class ProjectSettingsPage extends AbstractPage {

    private final Logger logger = LogManager.getRootLogger();

    @FindBy(xpath = "//summary[contains(text(),'Delete this repository')]")
    private WebElement buttonDelete;

    @FindBy(xpath = "//input[@aria-label='Type in the name of the repository to confirm that you want to delete this repository.']")
    private WebElement form;

    @FindBy(xpath = "//button[contains(text(),'I understand the consequences, delete this repository')]")
    private WebElement linkDeleteThisRepository;

    @FindBy(id="rename-field")
    private WebElement projectName;

    @FindBy(xpath = "//button[contains(text(), 'Rename')]")
    private WebElement renameButton;

    public ProjectSettingsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void openPage() {}

    public void deleteRepository() {
        buttonDelete.click();
        while (!form.isEnabled()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        form.clear();
        form.sendKeys(projectName.getAttribute("value"));
        linkDeleteThisRepository.click();
    }

    public void renameRepository(String name)
    {
        projectName.clear();
        projectName.sendKeys(name);
        renameButton.click();
    }
}



















