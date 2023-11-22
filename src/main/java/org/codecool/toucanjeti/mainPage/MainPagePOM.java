package org.codecool.toucanjeti.mainPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// url: https://jira-auto.codecool.metastage.net/secure/Dashboard.jspa

public class MainPagePOM {

    private final WebDriver webDriver;

    @FindBy(id = "logo")
    private WebElement homeButton;

    @FindBy(id = "home_link")
    private WebElement dashboardsNavElement;

    @FindBy(id = "browse_link")
    private WebElement projectsNavElement;

    @FindBy(id = "find_link")
    private WebElement issuesNavElement;

    @FindBy(id = "greenhopper_menu")
    private WebElement boardsNavElement;

    @FindBy(id = "ktm_top_menu")
    private WebElement testsNavElement;

    @FindBy(id = "aui-responsive-header-dropdown-0-trigger")
    private WebElement moreNavElement;

    @FindBy(id = "create_link")
    private WebElement createNavElement;

    @FindBy(id = "header-details-user-fullname")
    private WebElement userAvatar;

    @FindBy(id = "view_profile")
    private WebElement viewUserProfile;

    @FindBy(id = "quickSearchInput")
    private WebElement searchInput;

    public MainPagePOM(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void clickOnHomeButton() {
        this.homeButton.click();
    }

    public void clickOnDashboard() {
        this.dashboardsNavElement.click();
    }

    public void clickOnProjects() {
        this.projectsNavElement.click();
    }

    public void clickOnIssues() {
        this.issuesNavElement.click();
    }

    public void clickOnBoards() {
        this.boardsNavElement.click();
    }

    public void clickOnTests() {
        this.testsNavElement.click();
    }

    public void clickOnMore() {
        this.moreNavElement.click();
    }

    public void clickOnCreate() {
        this.createNavElement.click();
    }

    public void clickOnUserAvatar() {
        this.userAvatar.click();
    }

    public void clickOnUserProfile() {
        this.viewUserProfile.click();
    }

    public void useSearchInput(String searchText) {
        this.searchInput.click();
        this.searchInput.sendKeys(searchText);
    }
}
