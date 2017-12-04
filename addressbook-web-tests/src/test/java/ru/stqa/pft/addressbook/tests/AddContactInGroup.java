package ru.stqa.pft.addressbook.tests;

import com.google.common.collect.Sets;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.collect.Sets.intersection;

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
   // Set groupSet = allGroups.stream().map(g -> g.getId()).collect(Collectors.toSet());

    Set<Integer> set1 = allGroups.stream().map(g -> g.getId()).collect(Collectors.toSet());
    Set<Integer> set2 = contactGroups.stream().map(g -> g.getId()).collect(Collectors.toSet());
    Set<Integer> intersection = symmetricDifference(set1,set2);
    
  //  Set<Integer> intersect = set1.stream().filter(set2::contains).collect(Collectors.toSet());
   // Set<Integer> setC = intersection(set1, set2);
   System.out.println(intersection);

    //Set<Integer> intersection = Sets.intersection(Sets.newHashSet(set2), Sets.newHashSet(set1));
  //  Set<Integer> union = Stream.concat(set1.stream(), set2.stream()).collect(Collectors.toSet());
   // System.out.println(setC);
   // System.out.println(union + " - " + intersect);

    //for ( ContactData contact : (List<ContactData>) before ) {
    //  Groups allGroups = contact.getGroups();
   // }

//
 //   Contacts contacts = new Contacts();
//    List<WebElement> elements = wd.findElements(By.xpath("//tr[@name='entry']"));
//    for (int i = 0; i < before.size(); i++) {
 //     List<WebElement> cells = elements.get(i).findElements(By.tagName("td"));
//      String firstname = cells.get(2).getText();
//    app.contact().selectContactById(selectContact.getId());
//    app.contact().addToGroup();
  }
  public static Set<Integer> symmetricDifference(Set<Integer> set1, Set<Integer> set2) {
    Set<Integer> symmetricDiff = new HashSet<Integer>(set1);
    symmetricDiff.addAll(set2);
    Set<Integer> tmp = new HashSet<Integer>(set1);
    tmp.retainAll(set2);
    symmetricDiff.removeAll(tmp);
    return symmetricDiff;
  }
}
