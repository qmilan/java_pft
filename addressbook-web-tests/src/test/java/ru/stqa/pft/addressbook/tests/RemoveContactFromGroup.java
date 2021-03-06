package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class RemoveContactFromGroup extends TestBase {
  @BeforeMethod
  public void ensurePrecondition() {
    Groups beforegroup = app.db().groups();
    if (beforegroup.size()==0){
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }
    Groups aftergroup = app.db().groups();
    GroupData idGroups = aftergroup.iterator().next();
    if (app.db().contacts().size() == 0) {
      ContactData contact = new ContactData().withFirstname("test1").withLastname("test2").withAddress("test3")
              .withEmail("test@test.com").withEmail2("test1@test.com")
              .withEmail3("test2@test.com").withMobile("89501234567").withHome("123445")
              .withWork("3412111").inGroup(idGroups);
      app.goTo().contactCreationPage();
      app.contact().create(contact, true);
    }
  }
  @Test
  public void testRemoveContactFromGroup (){
    ContactData contactBefore = app.db().contacts().iterator().next();
    Integer id = contactBefore.getId();
    Groups beforeGroupBD = app.db().groups();
    Groups beforeGroupsContact = contactBefore.getGroups();
    if (beforeGroupsContact.size()==0){
      beforeGroupBD.stream().forEach(g->
      {
        app.goTo().homePage();
        app.contact().selectContactById(contactBefore.getId());
        app.contact().selectGroupFromDropDown(String.valueOf(g.getId()));
        app.contact().clickOnAdd();
      });
      }
    ContactData contactBetween = app.db().contacts().stream().filter(x->id.equals(x.getId())).findAny().orElse(null);
    Groups betweenGroupsContact = contactBetween.getGroups();
    betweenGroupsContact.stream().forEach(g->
      {
        app.goTo().homePage();
        app.contact().selectAllFromDropDown(String.valueOf(g.getId()));
        app.contact().selectContactById(id);
        app.contact().clickRemove();

      });
    Groups afterGroupBD = app.db().groups();
    ContactData contactAfter = app.db().contacts().stream().filter(x->id.equals(x.getId())).findAny().orElse(null);
    Groups afterGroupsContact = contactAfter.getGroups();

    Groups afterDistinctionGroups= distinction(afterGroupBD,afterGroupsContact);
    betweenGroupsContact.removeAll(afterDistinctionGroups);
    assertThat(afterGroupsContact.size(),equalTo(betweenGroupsContact.size()));
    assertThat(afterGroupsContact,equalTo(betweenGroupsContact));
  }

  public static Groups distinction(Groups set1, Groups set2) {
    Set<GroupData> dbGroups = new HashSet<GroupData>(set1);
    dbGroups.addAll(set2);
    Set<GroupData>   groups = new HashSet<GroupData>(set1);
    groups.retainAll(set2);
    dbGroups.removeAll(groups);
    return new Groups (dbGroups);
  }
}
