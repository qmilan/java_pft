package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactTests extends TestBase {
  @BeforeMethod
  public void ensurePrecondition() {
    app.goTo().homePage();
    if (app.db().contacts().size() == 0) {
      Groups beforegroup = app.db().groups();
      if (beforegroup.size()==0){
        app.goTo().groupPage();
        app.group().create(new GroupData().withName("test1"));
      }
      Groups aftergroup = app.db().groups();
      GroupData idGroups = aftergroup.iterator().next();
      ContactData contact = new ContactData().withFirstname("test1").withLastname("test2").withAddress("test3")
              .withEmail("test4@test.com").withMobile("89991234567").inGroup(idGroups);
  //    String groupName = idGroups.getName();
      //проверка содержится ли group  в lists
    //  if (!listsGroupFromBD.stream().anyMatch(g -> g.getName().equals(groupName))) {
    //    app.goTo().groupPage();
   //     app.group().create(new GroupData().withName(groupName));
   //   }
      app.goTo().contactCreationPage();
      app.contact().create(contact, true);
    }
  }

  @Test
  public void testContactPhones() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEfitForm(contact);
    assertThat(contact.getLastname(), equalTo(cleaned(contactInfoFromEditForm.getLastname())));
    assertThat(contact.getFirstname(), equalTo(cleaned(contactInfoFromEditForm.getFirstname())));
    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
    assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
    assertThat(contact.getAddress(), equalTo(cleaned(contactInfoFromEditForm.getAddress())));
  }

  private String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getHome(), contact.getMobile(), contact.getWork())
            .stream().filter((s) -> !s.equals(""))
            .map(ContactTests::cleanedMobile)
            .collect(Collectors.joining("\n"));

  }

  private String mergeEmails(ContactData contact) {
    return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
            .stream().filter((s) -> !s.equals(""))
            .map(ContactTests::cleanedEmail)
            .collect(Collectors.joining("\n"));
  }

  private String mergeAddress(ContactData contact) {
    return Arrays.asList(contact.getAddress())
            .stream().filter((s) -> !s.equals(""))
            .map(ContactTests::cleaned)
            .collect(Collectors.joining("\n"));
  }

  public static String cleanedMobile(String phone) {
    return phone.replaceAll(" +", "").replaceAll("[-()]", "");
  }

  public static String cleanedEmail(String email) {
    return email.replaceAll("^(\\s+|\\n+)", "")
            .replaceAll("(\\s+|\\n+)$", "").replaceAll(" +", " ");
  }

  public static String cleaned(String address) {
    return address.replaceAll("^(\\s+|\\n+)", "")
            .replaceAll("(\\s+|\\n+)$", "").replaceAll("\n +", "\n")
            .replaceAll(" +\\n", "\\\n").replaceAll(" +", " ");
  }
}
