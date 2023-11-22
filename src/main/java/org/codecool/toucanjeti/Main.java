package org.codecool.toucanjeti;

import org.codecool.toucanjeti.BrowseIssue.BrowseIssuesTest;
import org.codecool.toucanjeti.Login.LoginTest;
import org.codecool.toucanjeti.Logout.LogoutPOM;
import org.codecool.toucanjeti.editIssue.EditIssue;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();
        LoginTest loginTest = new LoginTest(driver);
        LogoutPOM logoutPOM = new LogoutPOM(driver);
        EditIssue editIssue = new EditIssue(driver);

        editIssue.changeSummaryField();
        editIssue.changeIssueType();
        editIssue.changePriorityField();
        editIssue.changeLabelField();
        editIssue.changeDescriptionField();
        editIssue.addNewAttachment();
        editIssue.addNewComment();

        BrowseIssuesTest browseIssues = new BrowseIssuesTest(driver);
//        loginTest.runTests();
//        logoutTest.logout();
        browseIssues.runTests();
        driver.close();

    }
}