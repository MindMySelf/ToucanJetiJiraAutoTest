package org.codecool.toucanjeti.Logout;

import org.codecool.toucanjeti.Login.LoginPOM;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LogoutPOMTest {

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/LegitLoginDatas.csv", numLinesToSkip = 1)
    void logoutTest(String username, String password) {
        WebDriver driver = new ChromeDriver();
        LoginPOM loginPOM = new LoginPOM(driver);
        LogoutPOM logoutPOM = new LogoutPOM(driver);
        loginPOM.enterUsername(username);
        loginPOM.enterPassword(password);
        loginPOM.clickLogIn();
        assertTrue(logoutPOM.logout());
        driver.close();
    }
}