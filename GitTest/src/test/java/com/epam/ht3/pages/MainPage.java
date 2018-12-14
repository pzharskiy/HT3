package com.epam.ht3.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends AbstractPage
{
	private final String BASE_URL = "https://github.com/";
	private final Logger logger = LogManager.getRootLogger();

	@FindBy(xpath = "//summary[@aria-label='Create new…']")
	private WebElement buttonCreateNew;

	@FindBy(xpath = "//a[contains(text(), 'New repository')]")
	private WebElement linkNewRepository;

	@FindBy(id = "dashboard-repos-filter-sidebar")
	private WebElement findRepositoryLabel;


	public MainPage(WebDriver driver)
	{
		super(driver);
		PageFactory.initElements(this.driver, this);
	}

	public void clickOnCreateNewRepositoryButton()
	{
		buttonCreateNew.click();
		linkNewRepository.click();
	}

	public ProjectPage findRepository(String repo, String name)
	{
		findRepositoryLabel.sendKeys(repo);

		try {
			driver.findElement(By.xpath("//a[starts-with(@href, \"/" + name + "/" + repo + "\")]")).click();
			return PageFactory.initElements(this.driver, ProjectPage.class);
		}
		catch(Exception e)
		{
			return null;
		}

	}

	@Override
	public void openPage()
	{
		driver.navigate().to(BASE_URL);
		logger.info("Main page opened");
	}

	public ProfilePage openProfilePage(String user)
	{
		driver.navigate().to(BASE_URL+"/"+user);
		logger.info("Profile page opened");
		return PageFactory.initElements(driver, ProfilePage.class);
	}
}
