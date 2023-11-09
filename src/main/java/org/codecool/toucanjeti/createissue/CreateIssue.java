package org.codecool.toucanjeti.createissue;

import org.codecool.toucanjeti.technical.Login;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class CreateIssue {
    private static final Map<String, String> LEGIT_LOGIN_CREDENTIALS = Map.of(
            "automation57", "CCAutoTest19.",
            "automation58", "CCAutoTest19.",
            "automation59", "CCAutoTest19."
    );
    private static final By CREATE_LINK = By.id("create_link");
    private static final By PROJECT_INPUT_LOCATOR = By.id("project-field");
    private static final By PROJECT_DROPDOWN_BUTTON_LOCATOR = By.xpath("//*[@id='project-single-select']/span");
    private static final By PROJECT_DROPDOWN_OPTIONS_LOCATOR = By.id("all-projects");
    private static final List<String> PROJECTS_TO_VALIDATE = List.of(
            "COALA project (COALA)",
            "JETI project (JETI)",
            "TOUCAN project (TOUCAN)"
    );
    private static final By PROJECT_TYPE_INPUT_LOCATOR = By.id("issuetype-field");
    private static final By PROJECT_TYPE_DROPDOWN_BUTTON_LOCATOR = By.xpath("//*[@id='issuetype-single-select']/span");
    private static final By PROJECT_TYPE_DROPDOWN_OPTIONS_LOCATOR = By.xpath("//*[@id='issuetype-suggestions']/descendant::ul");
    private static final List<String> PROJECT_TYPES_TO_VALIDATE = List.of(
            "Story",
            "Task",
            "Bug",
            "Sub-task"
    );
    private static final By SUMMARY_INPUT_LOCATOR = By.id("summary");
    private static final By ISSUE_CREATION_POPUP_NEUTRAL_ELEMENT = By.id("qf-field-picker-trigger");
    private static final By ISSUE_CREATION_CANCEL_BUTTON = By.cssSelector("button.cancel");
    private static final By CREATE_ISSUE_SUBMIT_BUTTON = By.id("create-issue-submit");
    private static final By SUCCESSFUL_CREATION_POPUP_CLOSE_BUTTON_LOCATOR = By.cssSelector("button.aui-close-button");

    private static final By ISSUES_MENU_LOCATOR = By.id("find_link");
    private static final By SEARCH_ISSUES_LINK_LOCATOR = By.id("issues_new_search_link_lnk");
    private static final By SEARCH_INPUT_FIELD_FOR_ISSUES_LOCATOR = By.id("searcher-query");
    private static final By SEARCH_BUTTON_FOR_ISSUES_LOCATOR = By.cssSelector("button.search-button");
    private static final By DISPLAYED_ISSUE_TITLE_LOCATOR = By.id("summary-val");
    private static final By USER_AVATAR_LOCATOR = By.id("header-details-user-fullname");
    private static final By LOGOUT_LINK_LOCATOR = By.id("log_out");

    private final WebDriver driver;
    private final Wait<WebDriver> wait;
    private final List<String> projectDropdownOptions;
    private final List<String> projectTypeDropdownOptions;

    public CreateIssue(WebDriver driver) {
        this.driver = driver;
        projectDropdownOptions = new ArrayList<>();
        projectTypeDropdownOptions = new ArrayList<>();
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    public void test() throws InterruptedException {
        for (Map.Entry<String, String> entry : LEGIT_LOGIN_CREDENTIALS.entrySet()) {
            setStageForIssueCreation(entry);
            findAllAvailableProjects();

            List<String> projectsAvailableForUser = PROJECTS_TO_VALIDATE.stream().filter(projectDropdownOptions::contains).toList();
            List<String> projectOptionsUnavailableForUser = getUnavailableOptions(projectDropdownOptions, PROJECTS_TO_VALIDATE);

            wait.until(ExpectedConditions.elementToBeClickable(ISSUE_CREATION_CANCEL_BUTTON));
            WebElement cancelButton = driver.findElement(ISSUE_CREATION_CANCEL_BUTTON);
            Thread.sleep(1000);
            cancelButton.click();

            if (!projectOptionsUnavailableForUser.isEmpty()) {
                System.out.println("The following project(s) are not available for '" + entry.getKey() + "' user:\n" + projectOptionsUnavailableForUser);
            }

            Thread.sleep(1000);

            for (String project : projectsAvailableForUser) {
                Thread.sleep(2000);
                openIssueCreationPopup();
                cancelButton = driver.findElement(ISSUE_CREATION_CANCEL_BUTTON);

                findAllAvailableProjectTypes();
                List<String> projectTypesAvailableForUser = PROJECT_TYPES_TO_VALIDATE.stream().filter(projectTypeDropdownOptions::contains).toList();
                List<String> projectTypeOptionsUnavailableForUser = getUnavailableOptions(projectTypeDropdownOptions, PROJECT_TYPES_TO_VALIDATE);

                if (!projectTypeOptionsUnavailableForUser.isEmpty()) {
                    System.out.println("The following project type(s) are not available with '" + entry.getKey() + "' username for project '" + project + "':\n" + projectTypeOptionsUnavailableForUser);
                }

                cancelButton.click();

                for (String projectType : projectTypesAvailableForUser) {
                    Thread.sleep(2000);
                    openIssueCreationPopup();
                    WebElement projectInputField = driver.findElement(PROJECT_INPUT_LOCATOR);

                    projectInputField.click();
                    projectInputField.sendKeys(project);

                    clickElementNumberOfTimes(ISSUE_CREATION_POPUP_NEUTRAL_ELEMENT, 2);
                    WebElement projectTypeInputField = driver.findElement(PROJECT_TYPE_INPUT_LOCATOR);

                    projectTypeInputField.click();
                    projectTypeInputField.sendKeys(projectType);
                    clickElementNumberOfTimes(ISSUE_CREATION_POPUP_NEUTRAL_ELEMENT, 1);

                    wait.until(ExpectedConditions.presenceOfElementLocated(SUMMARY_INPUT_LOCATOR));
                    WebElement summaryInputField = driver.findElement(SUMMARY_INPUT_LOCATOR);
                    summaryInputField.click();
                    WebElement submitButton = driver.findElement(CREATE_ISSUE_SUBMIT_BUTTON);

                    String summary = project + " " + projectType + " " + UUID.randomUUID();
                    summaryInputField.sendKeys(summary);
                    submitButton.click();

                    Thread.sleep(200);
                    wait.until(ExpectedConditions.presenceOfElementLocated(SUCCESSFUL_CREATION_POPUP_CLOSE_BUTTON_LOCATOR));
                    driver.findElement(SUCCESSFUL_CREATION_POPUP_CLOSE_BUTTON_LOCATOR).click();

                    navigateToIssues();

                    if (validateIssueCreation(summary)) {
                        System.out.println("SUCCESS: Issue '" + summary + "' successfully created!\n");
                    } else {
                        System.out.println("FAILED: Issue could not be found!\n");
                    }
                }
            }

            logOut();
        }
    }

    private void setStageForIssueCreation(Map.Entry<String, String> entry) throws InterruptedException {
        Login.toJiraWith(entry.getKey(), entry.getValue(), driver);
        navigateToIssues();
        Thread.sleep(2000);
        openIssueCreationPopup();
    }

    private void findAllAvailableProjectTypes() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(PROJECT_TYPE_DROPDOWN_BUTTON_LOCATOR));
        clickElementNumberOfTimes(PROJECT_TYPE_DROPDOWN_BUTTON_LOCATOR, 2);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        projectTypeDropdownOptions.addAll(getOptionsFromInputField(PROJECT_TYPE_INPUT_LOCATOR, PROJECT_TYPE_DROPDOWN_OPTIONS_LOCATOR));
    }

    private void findAllAvailableProjects() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(PROJECT_DROPDOWN_BUTTON_LOCATOR));
        clickElementNumberOfTimes(PROJECT_DROPDOWN_BUTTON_LOCATOR, 2);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        projectDropdownOptions.addAll(getOptionsFromInputField(PROJECT_INPUT_LOCATOR, PROJECT_DROPDOWN_OPTIONS_LOCATOR));
    }

    private void openIssueCreationPopup() {
        wait.until(ExpectedConditions.presenceOfElementLocated(CREATE_LINK));
        driver.findElement(CREATE_LINK).click();
        wait.until(ExpectedConditions.elementToBeClickable(CREATE_ISSUE_SUBMIT_BUTTON));
    }

    private List<String> getOptionsFromInputField(By inputFieldLocator, By dropdownOptionsLocator) {
        List<String> options = new ArrayList<>();
        WebElement inputField = driver.findElement(inputFieldLocator);
        String currentText = inputField.getAttribute("value");
        options.add(currentText);

        WebElement allOptionsList = driver.findElement(dropdownOptionsLocator);
        List<WebElement> optionElementsFromDropdown = allOptionsList.findElements(By.tagName("a"));
        for (WebElement link : optionElementsFromDropdown) {
            options.add(link.getAttribute("innerText"));
        }

        return options;
    }

    private List<String> getUnavailableOptions(List<String> actual, List<String> expectedOnes) {
        List<String> unavailableOptions = new ArrayList<>();

        for (String expected : expectedOnes) {
            if (!actual.contains(expected)) {
                unavailableOptions.add(expected);
            }
        }

        return unavailableOptions;
    }

    private void clickElementNumberOfTimes(By elementLocator, int numberOfTimes) throws InterruptedException {
        for (int i = 0; i < numberOfTimes; i++) {
            wait.until(ExpectedConditions.presenceOfElementLocated(elementLocator));
            driver.findElement(elementLocator).click();
            Thread.sleep(2000);
        }
    }

    private boolean validateIssueCreation(String issueSummary) throws InterruptedException {
        wait.until(ExpectedConditions.presenceOfElementLocated(SEARCH_INPUT_FIELD_FOR_ISSUES_LOCATOR));
        WebElement searchInput = driver.findElement(SEARCH_INPUT_FIELD_FOR_ISSUES_LOCATOR);
        wait.until(ExpectedConditions.elementToBeClickable(SEARCH_BUTTON_FOR_ISSUES_LOCATOR));
        WebElement searchButton = driver.findElement(SEARCH_BUTTON_FOR_ISSUES_LOCATOR);
        searchInput.click();
        searchInput.sendKeys(issueSummary);
        searchButton.click();

        Thread.sleep(1000);
        WebElement searchedIssueTitle = driver.findElement(DISPLAYED_ISSUE_TITLE_LOCATOR);
        String title = searchedIssueTitle.getText();

        System.out.println("Given summary: " + issueSummary + "\n" + "Title on site: " + title);

        return Objects.equals(issueSummary, title);
    }

    private void navigateToIssues() {
        driver.findElement(ISSUES_MENU_LOCATOR).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_ISSUES_LINK_LOCATOR));
        driver.findElement(SEARCH_ISSUES_LINK_LOCATOR).click();
    }

    private void logOut() {
        wait.until(ExpectedConditions.presenceOfElementLocated(USER_AVATAR_LOCATOR));
        driver.findElement(USER_AVATAR_LOCATOR).click();
        driver.findElement(LOGOUT_LINK_LOCATOR).click();
    }
}
