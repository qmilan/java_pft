package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

public class ContactModificationTest extends TestBase {
  @Test
  public void testContactModificationTest() {
    app.getNavigationHelper().goToHomePage();
    if (!app.getContactHelper().isThereAContact()) {
      app.getNavigationHelper().goToGroupPage();
      if (!app.getGroupHelper().isThereAGroup()) {
        app.getGroupHelper().createGroup(new GroupData("test1", null, null));
      }
      app.getNavigationHelper().goToContactCreationPage();
      app.getContactHelper().createContact(new ContactData("test1", "test2", "test3", "test4@test.com", "89991234567", "test1"), true);
    }
    List<ContactData> before =app.getContactHelper().getContactList();
    app.getContactHelper().selectContact(before.size()-1);
    app.getContactHelper().initContactModification();
    ContactData contact = new ContactData(before.get(before.size()-1).getId(),"test1", "test2", "test3", "test4@test.com", "89991234567", null);
    app.getContactHelper().fillContactCreation(contact, false);
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returnToHomePage();
    List<ContactData> after =app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(),before.size());

    before.remove(before.size() - 1);
    before.add(contact);
    Assert.assertEquals(new HashSet<Object>(before),new HashSet<Object>(after));
  }
}
