package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddContactInGroup extends TestBase {

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
//      if (!listsGroupFromBD.stream().anyMatch(g -> g.getName().equals(groupName))) {
      app.goTo().contactCreationPage();
      app.contact().create(contact, true);
    }
  }
  @Test
  public void testAddContactInGroup (){

    ContactData contactBefore = app.db().contacts().stream().sorted(Comparator.comparingInt(ContactData::getId)).findFirst().get();

    Contacts beforeContacts = app.db().contacts();
    Groups beforeGroupBD = app.db().groups();

    Groups beforeGroupsContact = contactBefore.getGroups();
    Groups beforeDistinctionGroups= distinction(beforeGroupBD,beforeGroupsContact);
    //Set<GroupData> beforeDistinctionGroups= distinction(beforeGroupBD,beforeGroupsContact);
    System.out.println(beforeGroupBD);
    System.out.println("GC:"+beforeGroupsContact);
    System.out.println("DIS:"+beforeDistinctionGroups);
    if (beforeDistinctionGroups.size()==0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test_zero"));
    }
    Groups afterGroupBD = app.db().groups();
    //Groups afterGroupsContact = contact.getGroups();
    Groups afterDistinctionGroups= distinction(afterGroupBD,beforeGroupsContact);
      for (GroupData groups : afterDistinctionGroups) {
        app.goTo().homePage();
        app.contact().selectContactById(contactBefore.getId());
        app.contact().selectGroupFromDropDown(String.valueOf(groups.getId()));
        app.contact().clickOnAdd();
    }

    ContactData contactAfter = app.db().contacts().stream()
            .sorted(Comparator.comparingInt(ContactData::getId))
            .findFirst()
            .get();
    Groups afterGroupsContact = contactAfter.getGroups();
      beforeGroupsContact.addAll(afterDistinctionGroups);
      assertThat(afterGroupsContact.size(),equalTo(beforeGroupsContact.size()));
      assertThat(afterGroupsContact,equalTo(beforeGroupsContact));

  }


  public static Set<Integer> Difference(Set<Integer> set1, Set<Integer> set2) {
    Set<Integer> dbGroups = new HashSet<Integer>(set1);
    dbGroups.addAll(set2);
    Set<Integer> groups = new HashSet<Integer>(set1);
    groups.retainAll(set2);
    dbGroups.removeAll(groups);
    return dbGroups;
  }
  public static Groups Difference2(Groups set1, Groups set2) {
    Groups dbGroups =(set1);
    dbGroups.addAll(set2);
    Groups groups = (set1);
    groups.retainAll(set2);
    dbGroups.removeAll(groups);
    return dbGroups;
  }
  public static Groups distinction(Groups set1, Groups set2) {
    Set<GroupData>  dbGroups = new HashSet<GroupData>(set1);
    dbGroups.addAll(set2);
    Set<GroupData>   groups = new HashSet<GroupData>(set1);
    groups.retainAll(set2);
    dbGroups.removeAll(groups);
    return new Groups (dbGroups);
  }

}
