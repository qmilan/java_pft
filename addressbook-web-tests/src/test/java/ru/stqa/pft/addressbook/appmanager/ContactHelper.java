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


  public void selectContact() {
    click(By.name("selected[]"));
  }
  public void deleteSelectedContact() {
    click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
  }
  public void submitDeletion () {
    clickButton();
  }

  public void initContactModification() {
    click(By.xpath("//table[@id='maintable']//img[@title='Edit']"));
  }

  public void submitContactModification() {
    click(By.name("update"));
  }
  public void returnToHomePage() {
    click(By.linkText("home page"));
  }
}
