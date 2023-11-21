package org.codecool.toucanjeti.Profile;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProfilePOM {
    private WebDriver driver;
    @FindBy(id = "up-d-username")
    private WebElement username;

    public ProfilePOM(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public boolean checkUsername(String name) {
        return username.getText().equals(name);
    }
}
