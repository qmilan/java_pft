package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class ContactDeletion extends TestBase {

  @Test
  public void ContactDeletion() {
    app.getNavigationHelper().goToHomePage();
    if (!app.getContactHelper().isThereAContact()) {
      app.getNavigationHelper().goToContactCreationPage();
      ContactData contact = new ContactData("test1", "test2", "test3", "test4@test.com", "89991234567", "test51");
      List<GroupData> lists = app.getGroupHelper().CheckboxGroupList();
      String group = contact.getGroup();
      //проверка содержится ли group  в lists
      if (!lists.stream().anyMatch(g -> g.getName().equals(group))) {
        app.getNavigationHelper().goToGroupPage();
        app.getGroupHelper().createGroup(new GroupData(contact.getGroup(), null, null));
      }
      app.getNavigationHelper().goToContactCreationPage();
      app.getContactHelper().createContact(contact, true);
    }
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().selectContact(before.size() - 1);
    app.getContactHelper().deleteSelectedContact();
    app.getContactHelper().submitDeletion();
    app.getNavigationHelper().goToHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() - 1);
    before.remove(before.size() - 1);
    Assert.assertEquals(before, after);
  }

}
