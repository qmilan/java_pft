package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationHelper extends BaseHelper{


  public RegistrationHelper(ApplicationManager app) {
    super(app);
    wd =app.getDriver();
  }

  public void start(String username, String email) {
    wd.get(app.getProperty("web.baseUrl")+"/signup_page.php");
    type(By.name("username"),username);
    type(By.name("email"),email);
    click(By.cssSelector("input[value='Зарегистрироваться']"));
  }

  public void finish(String confirmationLink, String password) {
    wd.get(confirmationLink);
    type(By.xpath("//*[@id=\"login-box\"]/div/div/div[4]/a"),password);
    click(By.xpath("//*[@id=\"account-update-form\"]/fieldset/span/button/span"));
  }
}
