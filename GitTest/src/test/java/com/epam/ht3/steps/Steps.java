package com.epam.ht3.steps;

import com.epam.ht3.driver.DriverSingleton;
import com.epam.ht3.pages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import test.java.com.epam.ht3.utils.Utils;

public class Steps {
    private WebDriver driver;

    private final Logger logger = LogManager.getRootLogger();

    public void openBrowser() {
        driver = DriverSingleton.getDriver();
    }

    public void closeBrowser() {
        DriverSingleton.closeDriver();
    }

    public void loginGithub(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openPage();
        loginPage.login(username, password);
    }

    public String getLoggedInUserName() {
        LoginPage loginPage = new LoginPage(driver);
        return loginPage.getLoggedInUserName().trim().toLowerCase();
    }

    public void createNewRepository(String repositoryName, String repositoryDescription) {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickOnCreateNewRepositoryButton();
        CreateNewRepositoryPage createNewRepositoryPage = new CreateNewRepositoryPage(driver);
        createNewRepositoryPage.createNewRepository(repositoryName, repositoryDescription);
    }

    public boolean currentRepositoryIsEmpty() {
        CreateNewRepositoryPage createNewRepositoryPage = new CreateNewRepositoryPage(driver);
        return createNewRepositoryPage.isCurrentRepositoryEmpty();
    }

    public String getCurrentRepositoryName() {
        CreateNewRepositoryPage createNewRepositoryPage = new CreateNewRepositoryPage(driver);
        return createNewRepositoryPage.getCurrentRepositoryName();
    }

    public String generateRandomRepositoryNameWithCharLength(int howManyChars) {
        String repositoryNamePrefix = "testRepo_";
        return repositoryNamePrefix.concat(Utils.getRandomString(howManyChars));
    }

    public void openMainPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openPage();
    }

    public void openProfilePage(String userName) {
        MainPage mainPage = new MainPage(driver);
        mainPage.openProfilePage(userName);
    }

    public int getNumberOfRepositories() {
        ProfilePage profilePage = new ProfilePage(driver);
        return profilePage.getCounter();
    }

    public void deleteRepository(String name) {
        findRepository(name);

        ProjectPage projectPage = new ProjectPage(driver);
        projectPage.setting(name, getLoggedInUserName());

        ProjectSettingsPage projectSettingsPage = new ProjectSettingsPage(driver);
        projectSettingsPage.deleteRepository();


    }

    public boolean findRepository(String name) {
        MainPage mainPage = new MainPage(driver);
        mainPage.openPage();
        if (mainPage.findRepository(name, getLoggedInUserName()) != null) {
            return true;
        } else return false;
    }

    public void renameRepository(String oldName, String newName) {
        findRepository(oldName);

        ProjectPage projectPage = new ProjectPage(driver);
        projectPage.setting(oldName, getLoggedInUserName());

        ProjectSettingsPage projectSettingsPage = new ProjectSettingsPage(driver);
        projectSettingsPage.renameRepository(newName);
    }

}
