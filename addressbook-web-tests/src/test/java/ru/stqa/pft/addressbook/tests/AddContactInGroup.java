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
    Contacts beforeContacts = app.db().contacts();
    Groups beforeGroups = app.db().groups();

    ContactData selectContact = beforeContacts.iterator().next();
    Groups groupsOfContact = selectContact.getGroups();

    Set<Integer> beforeIdsGroups = beforeGroups.stream().map(g -> g.getId()).collect(Collectors.toSet());
    Set<Integer> beforeIdsGroupsContact = groupsOfContact.stream().map(g -> g.getId()).collect(Collectors.toSet());
    Set<Integer> diffGroups = Difference(beforeIdsGroups,beforeIdsGroupsContact);
    System.out.println(diffGroups);
    if (diffGroups.size()==0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("Check"));
      }
    Set<Integer> afterSetGroups = app.db().groups().stream().map(g -> g.getId()).collect(Collectors.toSet());
    Set<Integer> setGroupsafter = Difference(afterSetGroups,beforeIdsGroupsContact);
      for ( Integer groups :  setGroupsafter ) {
        app.goTo().homePage();
        app.contact().selectAllFromDropDown();
        app.contact().selectContactById(selectContact.getId());
        app.contact().selectGroupFromDropDown(String.valueOf(groups));
        app.contact().clickOnAdd();
       // app.contact().clickOnGoToGroup(groups);
      //  System.out.println(groups);
    }
    Groups afterGroups = app.db().groups();
    Contacts afterContacts = app.db().contacts();
      for (ContactData all : afterContacts){
        if (all.getId()==selectContact.getId()){
          Set<Integer> setAfterGroups = afterGroups.stream().map(g -> g.getId()).collect(Collectors.toSet());
          System.out.println(setAfterGroups);
          assertThat(setAfterGroups.size(), equalTo(beforeIdsGroupsContact.size() + setGroupsafter.size()));
         // assertThat(afterGroups, equalTo(beforeGroups.withAdded()));
        }
      }
  }


  public static Set<Integer> Difference(Set<Integer> set1, Set<Integer> set2) {
    Set<Integer> dbGroups = new HashSet<Integer>(set1);
    dbGroups.addAll(set2);
    Set<Integer> groups = new HashSet<Integer>(set1);
    groups.retainAll(set2);
    dbGroups.removeAll(groups);
    return dbGroups;
  }
}
