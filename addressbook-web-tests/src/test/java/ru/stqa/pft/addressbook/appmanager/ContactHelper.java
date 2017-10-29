package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends BaseHelper {

  public ContactHelper(FirefoxDriver wd) {
   super(wd);
  }

  public void submitContactCreation() {
    click(By.name("submit"));
  }

  public void fillContactCreation(ContactData contactData) {
    type(By.name("firstname"),contactData.getFirstname());
    type(By.name("lastname"),contactData.getLastname());
    type(By.name("address"),contactData.getAddress());
    type(By.name("email"),contactData.getEmail());
    type(By.name("mobile"),contactData.getMobile());
  }

  public void goToContactCreation() {
    click(By.linkText("add new"));
  }
}
