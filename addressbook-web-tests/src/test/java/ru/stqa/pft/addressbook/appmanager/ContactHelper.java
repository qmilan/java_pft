package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends BaseHelper {

  private By locator;

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitContactCreation() {
    click(By.name("submit"));
  }

  public void fillContactCreation(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("address"), contactData.getAddress());
    type(By.name("email"), contactData.getEmail());
    type(By.name("mobile"), contactData.getMobile());
    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }


  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void deleteSelectedContact() {
    click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
  }

  public void submitDeletion() {
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

  public void createContact(ContactData contact, boolean creation) {
    fillContactCreation(contact, creation);
    submitContactCreation();
    returnToHomePage();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int getContactCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public List<ContactData> getContactList() {
    List<ContactData> contacts = new ArrayList<ContactData>(); //создание списка
    List<WebElement> elements = wd.findElements(By.xpath("//tr[@name='entry']"));
    for (int i=0;i<elements.size();i++){
      List<WebElement> cells = elements.get(i).findElements(By.tagName("td"));
      String firstname =  cells.get(2).getText();
      String lastname =  cells.get(1).getText();
      String id = cells.get(0).findElement(By.tagName("input")).getAttribute("value");
        ContactData contact = new ContactData(id,firstname,lastname,null,null,null,null);
        contacts.add(contact);
      }
      return contacts;//заполнение списка
//    }
 //   return elements;
  }
}
