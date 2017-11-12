package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactCreationTest extends TestBase {

  @Test
  public void testContactCreationTest() {
    app.getNavigationHelper().goToHomePage();
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getNavigationHelper().goToContactCreationPage();
    ContactData contact = new ContactData("test2", "test2", "test3", "test4@test.com", "89991234567", "test1211");
    //получение списка групп со страницы создани контакта
    List<GroupData> lists = app.getContactHelper().CheckboxGroupList();
    String group = contact.getGroup();
    //проверка содержится ли group  в lists
    if (!lists.stream().anyMatch(g -> g.getName().equals(group))) {
      app.getNavigationHelper().goToGroupPage();
      app.getGroupHelper().createGroup(new GroupData(contact.getGroup(), null, null));
    }
    app.getNavigationHelper().goToContactCreationPage();
    app.getContactHelper().createContact(contact, true);
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() + 1);
//    contact.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(),o2.getId())).get().getId());
    before.add(contact);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }

}
