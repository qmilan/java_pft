package ru.stqa.pft.addressbook.tests;

import com.thoughtworks.xstream.XStream;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTest extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContacts() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")))) {
      String xml = "";
      String line = reader.readLine();
      while (line != null) {
        xml += line;
        line = reader.readLine();
      }
      XStream xstream = new XStream();
      xstream.processAnnotations(ContactData.class);
      List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
      return contacts.stream().map((c) -> new Object[]{c}).collect(Collectors.toList()).iterator();

    }
  }

  @Test(dataProvider = "validContacts")
  public void testContactCreationTest(ContactData contact) {
    Contacts before = app.db().contacts();
    Groups beforegroup = app.db().groups();
    if (beforegroup.size()==0){
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }
    Groups aftergroup = app.db().groups();
   // if (!listsgroup.stream().anyMatch(g -> g.getName().equals(contact.getGroups().iterator().next().getName()))) {
 //   if (app.db().groups().size()==0){
 //     app.goTo().groupPage();
 //     app.group().create(new GroupData().withName(contact.getGroups().iterator().next().getName()));
//    }
    ContactData contactwithgroup = contact.inGroup(aftergroup.iterator().next());
    app.goTo().contactCreationPage();
    app.contact().create(contactwithgroup, true);
    Contacts after = app.db().contacts();
    assertThat(after.size(), equalTo(before.size() + 1));
    assertThat(after, equalTo(before.withAdded
            (contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    verifyContactListInUI();
  }

}
