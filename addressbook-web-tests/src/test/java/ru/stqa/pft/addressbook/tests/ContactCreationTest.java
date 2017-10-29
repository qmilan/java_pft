package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTest extends TestBase {

  @Test
  public void testContactCreationTest() {

    app.getContactHelper().goToContactCreation();
    app.getContactHelper().fillContactCreation(new ContactData("test1", "test2", "test3", "test4@test.com", "89991234567"));
    app.getContactHelper().submitContactCreation();
    app.getNavigationHelper().goToHomePage();
  }

}
