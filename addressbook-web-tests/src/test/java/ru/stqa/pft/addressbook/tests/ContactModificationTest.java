package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTest extends TestBase {
  @BeforeMethod
  public void ensurePrecondition(){
    if (app.db().contacts().size()==0) {
      ContactData contact = new ContactData().withFirstname("test1").withLastname("test2").withAddress("test3")
              .withEmail("test@test.com").withEmail2("test1@test.com")
              .withEmail3("test2@test.com").withMobile("89501234567").withHome("123445")
              .withWork("3412111").withGroup("test1");
      Groups lists = app.db().groups();
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
  public void testContactModificationTest() {
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstname("test2").withLastname("test2")
            .withAddress("test3").withEmail("test4@test.com").withEmail2("test422@test.com")
            .withEmail3("test433@test.com").withMobile("89991234567").withHome("12345")
            .withWork("3412");
    app.contact().modify(contact);
    Contacts after = app.db().contacts();
    assertThat(after.size(), equalTo(before.size()));
    assertThat(after, equalTo(before.withOut(modifiedContact).withAdded(contact)));
  }


}
