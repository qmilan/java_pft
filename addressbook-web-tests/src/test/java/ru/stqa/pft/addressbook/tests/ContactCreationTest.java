package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ContactCreationTest extends TestBase {

  @Test
  public void testContactCreationTest() {
    app.goTo().homePage();
    Set<ContactData> before = app.contact().all();
    app.goTo().contactCreationPage();
    ContactData contact = new ContactData().withFirstname("test2").withLastname("test2").withAddress("test3")
            .withEmail("test4@test.com").withMobile("89991234567").withGroup("test211");
    //получение списка групп со страницы создани контакта
    List<GroupData> lists = app.group().dropdownList();
    String group = contact.getGroup();
    //проверка содержится ли group  в lists
    if (!lists.stream().anyMatch(g -> g.getName().equals(group))) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName(contact.getGroup()));
    }
    app.goTo().contactCreationPage();
    app.contact().create(contact, true);
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), before.size() + 1);
//    contact.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(),o2.getId())).get().getId());
    contact.withId(after.stream().mapToInt((c)-> c.getId()).max().getAsInt());
    before.add(contact);
    Assert.assertEquals(before, after);
  }

}
