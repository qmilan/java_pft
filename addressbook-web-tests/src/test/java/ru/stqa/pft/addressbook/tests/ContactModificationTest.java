package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactModificationTest extends TestBase {
  @BeforeMethod
  public void ensurePrecondition(){
    app.getNavigationHelper().goToHomePage();
    if (!app.getContactHelper().isThereAContact()) {
      app.getNavigationHelper().goToContactCreationPage();
      ContactData contact = new ContactData("test1", "test2",
              "test3", "test4@test.com", "89991234567", "test451");
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
  }
  @Test
  public void testContactModificationTest() {
    List<ContactData> before = app.getContactHelper().getContactList();
    int index = before.size() - 1;
    ContactData contact = new ContactData(before.get(index).getId(), "test2",
            "test2", "test3", "test4@test.com", "89991234567", null);
    app.getContactHelper().modifyContact(index, contact);
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());
    before.remove(index);
    before.add(contact);
    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));

    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }


}
