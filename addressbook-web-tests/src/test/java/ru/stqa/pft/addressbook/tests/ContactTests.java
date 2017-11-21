package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactTests extends  TestBase {
  @Test
  public void testContactPhones (){
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();

    ContactData contactInfoFromEditForm = app.contact().infoFromEfitForm(contact);
   assertThat(contact.getLastname(), equalTo(cleaned(contactInfoFromEditForm.getLastname())));
   assertThat(contact.getFirstname(), equalTo(cleaned(contactInfoFromEditForm.getFirstname())));
   assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
   assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
   assertThat(contact.getAddress(), equalTo(mergeAddress(contactInfoFromEditForm)));

  }

  private String mergePhones(ContactData contact) {
   return  Arrays.asList(contact.getHome(),contact.getMobile(),contact.getWork())
            .stream().filter((s)-> ! s.equals(""))
           .map(ContactTests::cleanedMobile)
           .collect(Collectors.joining("\n"));

  }

  private String mergeEmails(ContactData contact) {
    return  Arrays.asList(contact.getEmail(),contact.getEmail2(),contact.getEmail3())
            .stream().filter((s)-> ! s.equals(""))
            .map(ContactTests::cleanedEmail)
            .collect(Collectors.joining("\n"));
  }
  private String mergeAddress(ContactData contact) {
    return  Arrays.asList(contact.getAddress())
            .stream().filter((s)-> ! s.equals(""))
            .map(ContactTests::cleaned)
            .collect(Collectors.joining("\n"));
  }
  public static String cleanedMobile(String phone){
    return phone.replaceAll("\\s","").replaceAll("[-()]","");
  }
  public static String cleanedEmail(String phone){
    return phone.replaceAll("\\s","");
  }
  public static String cleaned(String address){
    return address.replaceAll("^(\\s+|\\n+)","")
            .replaceAll("(\\s+|\\n+)$","").replaceAll("\n +","\n")
            .replaceAll(" +\\n","\\\n").replaceAll(" +"," ");
  }
}
