package org.codecool.toucanjeti.editIssue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class EditIssuePOM {

    @FindBy(id = "edit-issue")
    private WebElement editIssue;

    @FindBy(id = "edit-issue-submit")
    private WebElement updateIssue;

    @FindBy(id = "summary")
    private WebElement summary;

    @FindBy(id = "issuetype-field")
    private WebElement issueType;

    @FindBy(id = "description")
    private WebElement description;

    @FindBy(id = "priority-field")
    private WebElement priority;

    @FindBy(id = "labels-textarea")
    private WebElement labels;

    @FindBy(id = "comment")
    private WebElement comment;

    private final Logger logger = Logger.getLogger(getClass().getName());

    private final WebDriver webDriver;

    public EditIssuePOM(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void clickEditButton() {
        try {
            if (this.editIssue.isDisplayed()) {
                this.editIssue.click();
                this.webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
            } else {
                throw new NoSuchElementException("Edit button is not displayed or accessible.");
            }
        } catch (NoSuchElementException e) {
            this.logger.info("Exception: " + e.getMessage());
        }
    }

    public void clickUpdateButton() {
        this.updateIssue.click();
    }

    /**
     * It is the responsibility of the method to be able to change the summary or description of an issue.
     * */
    private void clearAndChangeIssueField(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

    /**
     * It is the responsibility of the method to be able to change
     * the issue type, priority, label or comment of an issue.
     * */
    private void clickAndChangeIssueField(WebElement element, String text) {
        element.click();
        element.sendKeys(text);
    }

    public void changeSummaryField(String newSummary) {
        clearAndChangeIssueField(this.summary, newSummary);
    }

    public void changeIssueType(String newIssue) {
        clickAndChangeIssueField(this.issueType, newIssue);
    }

    public void changeDescription(String newDescription) {
        clearAndChangeIssueField(this.description, newDescription);
    }

    public void changePriority(String priority) {
        clickAndChangeIssueField(this.priority, priority);
    }

    public void changeLabel(String label) {
        clickAndChangeIssueField(this.labels, label);
    }

    public void addNewComment(String newComment) {
        clickAndChangeIssueField(this.comment, newComment);
    }
}
