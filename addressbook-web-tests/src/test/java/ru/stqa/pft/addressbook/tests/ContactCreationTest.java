package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class ContactCreationTest extends TestBase {

  @Test
  public void testContactCreationTest() {
    app.getNavigationHelper().goToGroupPage();
    if (!app.getGroupHelper().isThereAGroup()) {
      app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }
    app.getNavigationHelper().goToHomePage();
    List<ContactData> before =app.getContactHelper().getContactList();
    app.getNavigationHelper().goToContactCreationPage();
    app.getContactHelper().createContact(new ContactData("test1", "test2", "test3", "test4@test.com", "89991234567", "test1"), true);
    List<ContactData> after =app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(),before.size()+1);
  }

}
