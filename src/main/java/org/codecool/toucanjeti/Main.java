package org.codecool.toucanjeti;

import org.codecool.toucanjeti.Login.LoginTest;
import org.codecool.toucanjeti.Logout.LogoutTest;
import org.codecool.toucanjeti.createissue.CreateIssue;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
//        LoginTest loginTest = new LoginTest(driver);
//        LogoutTest logoutTest = new LogoutTest(driver);
//        loginTest.runTests();
//        logoutTest.logout();
        CreateIssue createIssue = new CreateIssue(driver);
        createIssue.test();
        driver.quit();
    }
}