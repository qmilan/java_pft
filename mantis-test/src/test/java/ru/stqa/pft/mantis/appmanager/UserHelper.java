package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class UserHelper extends BaseHelper {

  public UserHelper(ApplicationManager app) {
    super(app);
    wd =app.getDriver();
  }

  public void startAdmin(String username, String password) {
    wd.get(app.getProperty("web.baseUrl")+"/login_page.php");
    type(By.name("username"),username);
    click(By.cssSelector("input[value='Войти']"));
    type(By.name("password"),password);
    click(By.cssSelector("input[value='Войти']"));
  }
  public void finish(String confirmationLink, String password) {
    wd.get(confirmationLink);
    type(By.name("password"),password);
    type(By.name("password_confirm"),password);
    click(By.xpath("//*[@id=\"account-update-form\"]/fieldset/span/button/span"));
  }

  public void clickOnManage() {
    wd.findElement(By.linkText("управление")).click();
  }

  public void clickOnManageUsers() {
    wd.findElement(By.linkText("Управление пользователями")).click();
  }

  public void clickUser(String user) {
    wd.findElement(By.linkText(user)).click();
  }

  public void resetPasswordOfUser() {
    wd.findElement(By.xpath("//form[@id='manage-user-reset-form']/fieldset/span/input")).click();

  }
  public void start(String username, String email) {
    wd.get(app.getProperty("web.baseUrl")+"/signup_page.php");
    type(By.name("username"),username);
    type(By.name("email"),email);
    click(By.cssSelector("input[value='Зарегистрироваться']"));
  }
}
