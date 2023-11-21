package org.codecool.toucanjeti.Logout;

import org.codecool.toucanjeti.technical.Login;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LogoutPOM {
    private WebDriver driver;
    @FindBy(id = "header-details-user-fullname")
    private WebElement avatar;
    @FindBy(id = "log_out")
    private WebElement logoutButton;

    public LogoutPOM(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }
    public boolean logout() {
        avatar.click();
        logoutButton.click();
        return driver.getTitle().equals("Logout - Jira Auto");
    }
}
