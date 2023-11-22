package org.codecool.toucanjeti.Profile;

import org.codecool.toucanjeti.Login.LoginPOM;
import org.codecool.toucanjeti.Logout.LogoutPOM;
import org.codecool.toucanjeti.mainPage.MainPagePOM;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

class ProfilePOMTest {

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/LegitLoginDatas.csv", numLinesToSkip = 1)
    void checkUsername(String username, String password) {
        WebDriver driver = new ChromeDriver();
        LoginPOM loginPOM = new LoginPOM(driver);
        MainPagePOM mainPagePOM = new MainPagePOM(driver);
        ProfilePOM profilePOM = new ProfilePOM(driver);
        loginPOM.enterUsername(username);
        loginPOM.enterPassword(password);
        loginPOM.clickLogIn();
        mainPagePOM.clickOnUserAvatar();
        mainPagePOM.clickOnUserProfile();
        assertTrue(profilePOM.checkUsername(username));
        driver.close();
    }
}