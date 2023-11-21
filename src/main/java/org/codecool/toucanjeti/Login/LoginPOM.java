package org.codecool.toucanjeti.Login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPOM {
    private static final String LOGIN_URL = "https://jira-auto.codecool.metastage.net/login.jsp";
    private static final String LOGIN_PAGE_TITLE = "Log in - Jira Auto";
    private final WebDriver driver;
    @FindBy(id = "login-form-username") private WebElement username;
    @FindBy(id = "login-form-password") private WebElement password;
    @FindBy(id = "login-form-submit") private WebElement submitButton;

    public LoginPOM(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        if (!driver.getTitle().equals(LOGIN_PAGE_TITLE)) {
            driver.get(LOGIN_URL);
        }
    }

    public void enterUsername(String username) {
        this.username.sendKeys(username);
    }

    public void enterPassword(String password) {
        this.password.sendKeys(password);
    }

    public void clickLogIn() {
        submitButton.click();
    }
}
