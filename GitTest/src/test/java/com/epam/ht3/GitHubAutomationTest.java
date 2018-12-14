package com.epam.ht3;

import com.epam.ht3.steps.Steps;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GitHubAutomationTest {
    private Steps steps;
    private final String USERNAME = "testautomationuser";
    private final String PASSWORD = "Time4Death!";
    private final int REPOSITORY_NAME_POSTFIX_LENGTH = 6;
    private String repoName;
    private final String REPOSITORY_NEW_NAME = "Repo_changedName";

    @BeforeMethod(description = "Init browser")
    public void setUp() {
        steps = new Steps();
        steps.openBrowser();
        steps.loginGithub(USERNAME, PASSWORD);
    }

    @Test
    public void oneCanCreateProject() {
        String repositoryName = steps.generateRandomRepositoryNameWithCharLength(REPOSITORY_NAME_POSTFIX_LENGTH);
        steps.createNewRepository(repositoryName, "auto-generated test repo");
        Assert.assertEquals(steps.getCurrentRepositoryName(), repositoryName);
        repoName = steps.getCurrentRepositoryName();
    }

    @Test(description = "Login to Github")
    public void oneCanLoginGithub() {
        Assert.assertEquals(USERNAME, steps.getLoggedInUserName());
    }

    @Test(description = "rename repo, which we've created earlier", dependsOnMethods = "oneCanCreateProject")
    public void oneCanRenameProject() {
        steps.renameRepository(repoName, REPOSITORY_NEW_NAME);
        Assert.assertFalse(steps.findRepository(repoName));
        Assert.assertTrue(steps.findRepository(REPOSITORY_NEW_NAME));
        repoName = REPOSITORY_NEW_NAME;

    }

    @Test(description = "work of counter")
    public void oneCanCountRepositories() {
        steps.openProfilePage(USERNAME);
        int countBeforeCreating = steps.getNumberOfRepositories();
        steps.openMainPage();
        String temporary = "CheckCount";
        steps.createNewRepository(temporary, "auto-generated test repo");
        steps.openProfilePage(USERNAME);
        int countAfterCreating = steps.getNumberOfRepositories();
        Assert.assertEquals(countAfterCreating, countBeforeCreating + 1);
        steps.deleteRepository(temporary);
    }

    @Test(description = "delete repo, which we've created earlier", dependsOnMethods = "oneCanRenameProject")
    public void oneCanDeleteProject() {
        steps.deleteRepository(repoName);
        Assert.assertFalse(steps.findRepository(repoName));
    }

    @AfterMethod(description = "Stop Browser")
    public void stopBrowser() {
        steps.closeBrowser();
    }

}
