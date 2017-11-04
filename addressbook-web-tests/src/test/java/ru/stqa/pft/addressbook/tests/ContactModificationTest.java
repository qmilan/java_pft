package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTest extends TestBase {
  @Test
  public void testContactModificationTest () {
    app.getNavigationHelper().goToHomePage();
    if (! app.getContactHelper().isThereAContact ()) {
      app.getNavigationHelper().goToContactCreationPage();
      app.getContactHelper().createContact (new ContactData("test1", "test2", "test3", "test4@test.com", "89991234567","test1"), true);
    }
    app.getContactHelper().selectContact();
    app.getContactHelper().initContactModification ();
    app.getContactHelper().fillContactCreation(new ContactData("test1", "test2", "test3", "test4@test.com", "89991234567", null),false);
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returnToHomePage();
  }
}
