package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

public class AddContactInGroup extends TestBase {
  @BeforeMethod
  public void ensurePrecondition() {
    if (app.db().contacts().size() == 0) {
      Groups beforegroup = app.db().groups();
      if (beforegroup.size()==0){
        app.goTo().groupPage();
        app.group().create(new GroupData().withName("test1"));
      }
      Groups aftergroup = app.db().groups();
      GroupData idGroups = aftergroup.iterator().next();
      ContactData contact = new ContactData().withFirstname("test1").withLastname("test2").withAddress("test3")
              .withEmail("test@test.com").withEmail2("test1@test.com")
              .withEmail3("test2@test.com").withMobile("89501234567").withHome("123445")
              .withWork("3412111").inGroup(idGroups);
//      if (!listsGroupFromBD.stream().anyMatch(g -> g.getName().equals(groupName))) {
      app.goTo().contactCreationPage();
      app.contact().create(contact, true);
    }
  }
  @Test
  public void testAddContactInGroup (){

  }
}
