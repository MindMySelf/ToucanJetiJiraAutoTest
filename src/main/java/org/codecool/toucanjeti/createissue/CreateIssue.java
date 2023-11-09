package org.codecool.toucanjeti.createissue;

import org.codecool.toucanjeti.technical.Login;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateIssue {
    private static final Map<String, String> LEGIT_LOGIN_CREDENTIALS = Map.of(
            "automation57", "CCAutoTest19.",
            "automation58", "CCAutoTest19.",
            "automation59", "CCAutoTest19."
    );
    private static final By CREATE_LINK = By.id("create_link");
    private static final By PROJECT_INPUT_LOCATOR = By.id("project-field");
    private static final By PROJECT_DROPDOWN_BUTTON_LOCATOR = By.xpath("//*[@id='project-single-select']/span");
    private static final By PROJECT_DROPDOWN_OPTIONS_LOCATOR = By.xpath("//*[@id='all-projects']/a");
    private static final List<String> PROJECTS = List.of(
            "COALA project (COALA)",
            "JETI project (JETI)",
            "TOUCAN project (TOUCAN)"
    );
    private static final By PROJECT_TYPE_INPUT_LOCATOR = By.id("issuetype-field");
    private static final By PROJECT_TYPE_DROPDOWN_BUTTON_LOCATOR = By.xpath("//*[@id='issuetype-single-select']/span");
    private static final By PROJECT_TYPE_DROPDOWN_OPTIONS_LOCATOR = By.xpath("//*[@id='aui-last']/a");
    private static final List<String> PROJECT_TYPES = List.of(
            "Story",
            "Task",
            "Bug",
            "Sub-task"
    );
    private static final By SUMMARY_INPUT_LOCATOR = By.id("summary");
    private static final String SUMMARY_TEXT_ENDING = "SUMMARY";
    private static final By CREATE_ISSUE_SUBMIT_BUTTON = By.id("create-issue-submit");

    private final WebDriver driver;
    private final Wait<WebDriver> wait;
    private final List<String> projectDropdownOptions;
    private final List<String> projectTypeDropdownOptions;

    public CreateIssue(WebDriver driver) {
        this.driver = driver;
        projectDropdownOptions = new ArrayList<>();
        projectTypeDropdownOptions = new ArrayList<>();
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    public void test() {
        for (Map.Entry<String, String> entry : LEGIT_LOGIN_CREDENTIALS.entrySet()) {
            Login.toJiraWith(entry.getKey(), entry.getValue(), driver);
            WebElement createIssueSubmitButton = driver.findElement(CREATE_ISSUE_SUBMIT_BUTTON);
            driver.findElement(CREATE_LINK).click();
            wait.until(d -> createIssueSubmitButton.isDisplayed());

            WebElement projectDropdownButton = driver.findElement(PROJECT_DROPDOWN_BUTTON_LOCATOR);
            clickElementNumberOfTimes(projectDropdownButton, 2);
            projectDropdownOptions.addAll(getOptionsFromInputField(PROJECT_INPUT_LOCATOR, PROJECT_DROPDOWN_OPTIONS_LOCATOR));

            WebElement projectTypeDropdownButton = driver.findElement(PROJECT_TYPE_DROPDOWN_BUTTON_LOCATOR);
            clickElementNumberOfTimes(projectTypeDropdownButton, 2);
            projectTypeDropdownOptions.addAll(getOptionsFromInputField(PROJECT_TYPE_INPUT_LOCATOR, PROJECT_TYPE_DROPDOWN_OPTIONS_LOCATOR));

            List<String> projectOptionsUnavailableForUser = getUnavailableOptions(projectDropdownOptions, PROJECTS);
            List<String> projectTypeOptionsUnavailableForUser = getUnavailableOptions(projectTypeDropdownOptions, PROJECT_TYPES);
        }
    }

    private List<String> getOptionsFromInputField(By inputFieldLocator, By dropdownOptionsLocator) {
        List<String> options = new ArrayList<>();
        WebElement inputField = driver.findElement(inputFieldLocator);
        String currentText = inputField.getText();
        options.add(currentText);

        List<WebElement> optionElementsFromDropdown = driver.findElements(dropdownOptionsLocator);
        optionElementsFromDropdown.forEach(webElement -> options.add(webElement.getText()));

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

    private void clickElementNumberOfTimes(WebElement element, int numberOfTimes) {
        for (int i = 0; i < numberOfTimes; i++) {
            element.click();
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        }
    }
}
