package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletion extends TestBase {
  @BeforeMethod
  public void ensurePrecondition(){
    app.goTo().homePage();
    if (app.contact().all().size()==0) {
      app.goTo().contactCreationPage();
      ContactData contact = new ContactData().withFirstname("test1").withLastname("test2").withAddress("test3")
              .withEmail("test4@test.com").withMobile("89991234567").withGroup("test51");
      List<GroupData> lists = app.group().dropdownList();
      String group = contact.getGroup();
      //проверка содержится ли group  в lists
      if (!lists.stream().anyMatch(g -> g.getName().equals(group))) {
        app.goTo().groupPage();
        app.group().create(new GroupData().withName(contact.getGroup()));
      }
      app.goTo().contactCreationPage();
      app.contact().create(contact, true);
    }
  }
  @Test
  public void ContactDeletion() {
    Contacts before = app.contact().all();
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact);
    app.goTo().homePage();
    Contacts after = app.contact().all();
    assertThat(after.size(), equalTo(before.size() - 1));
    assertThat(after, equalTo(before.withOut(deletedContact)));
  }



}
