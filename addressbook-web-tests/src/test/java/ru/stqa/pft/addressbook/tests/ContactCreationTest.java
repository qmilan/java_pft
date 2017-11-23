package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTest extends TestBase {

  @Test
  public void testContactCreationTest() {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    app.goTo().contactCreationPage();
    File photo = new File("src/test/resources/linux.png");
    ContactData contact = new ContactData().withFirstname("test2").withLastname("test2").withAddress("test3")
            .withEmail("test4@test.com").withMobile("89991234567").withGroup("test211").withPhoto(photo);
    List<GroupData> lists = app.group().dropdownList();
    String group = contact.getGroup();
    if (!lists.stream().anyMatch(g -> g.getName().equals(group))) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName(contact.getGroup()));
    }
    app.goTo().contactCreationPage();
    app.contact().create(contact, true);
    Contacts after = app.contact().all();
    assertThat(after.size(), equalTo(before.size() + 1));
    assertThat(after, equalTo(before.withAdded
            (contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }

}
