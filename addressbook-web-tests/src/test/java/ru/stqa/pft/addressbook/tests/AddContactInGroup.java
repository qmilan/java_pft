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
    Contacts allContacts = app.db().contacts();
    Groups allGroups = app.db().groups();
    ContactData selectContact = allContacts.iterator().next();
    Groups contactGroups = selectContact.getGroups();
    System.out.println(allGroups);
    System.out.println(contactGroups);

    Set<Integer> beforeSetGroups = allGroups.stream().map(g -> g.getId()).collect(Collectors.toSet());
    Set<Integer> beforeSetSelectedGroups = contactGroups.stream().map(g -> g.getId()).collect(Collectors.toSet());
    Set<Integer> setGroups = Difference(beforeSetGroups,beforeSetSelectedGroups);
    System.out.println(setGroups);
    if (setGroups.size()==0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("Check"));
      }
    Set<Integer> afterSetGroups = app.db().groups().stream().map(g -> g.getId()).collect(Collectors.toSet());
    Set<Integer> setGroupsafter = Difference(afterSetGroups,beforeSetSelectedGroups);
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
      Set<Integer> setAfterGroups = afterGroups.stream().map(g -> g.getId()).collect(Collectors.toSet());

    System.out.println(setAfterGroups);
   System.out.println(beforeSetSelectedGroups);
    assertThat(setAfterGroups.size(), equalTo(beforeSetSelectedGroups.size() + 1));



//
 //   Contacts contacts = new Contacts();
//    List<WebElement> elements = wd.findElements(By.xpath("//tr[@name='entry']"));
//    for (int i = 0; i < before.size(); i++) {
 //     List<WebElement> cells = elements.get(i).findElements(By.tagName("td"));
//      String firstname = cells.get(2).getText();
//    app.contact().selectContactById(selectContact.getId());
//    app.contact().addToGroup();
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
