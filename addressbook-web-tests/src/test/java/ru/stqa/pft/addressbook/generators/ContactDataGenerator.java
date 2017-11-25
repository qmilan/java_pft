package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.tests.ContactCreationTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ContactDataGenerator {
  @Parameter(names = "-c", description = "Contact count")
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file;

  @Parameter(names = "-d", description = "Data format")
  public String format;

  public static void main(String[] args) throws IOException {
    ContactDataGenerator generator = new ContactDataGenerator();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException ex) {
      jCommander.usage();
      return;
    }
    generator.run();


  }

  private void run() throws IOException {
    List<ContactData> contacts = generateContact(count);
    if (format.equals("csv")){
      saveAsCsv(contacts, new File(file));
    } else if (format.equals("xml")) {
      saveAsXml(contacts, new File(file));
    } else {
      System.out.println("Unrecognized format");
    }
  }

  private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.processAnnotations(ContactData.class);
    String xml = xstream.toXML(contacts);
    try (Writer writer = new FileWriter(file)) {
      writer.write(xml);
    }
  }

  private void saveAsCsv(List<ContactData> contacts, File file) throws IOException {
   try (Writer writer = new FileWriter(file)) {
     for (ContactData contact : contacts) {
       writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n", contact.getLastname(), contact.getFirstname(), contact.getEmail(),
               contact.getEmail2(), contact.getEmail3(), contact.getAddress(), contact.getHome()
               , contact.getMobile(), contact.getWork(), contact.getGroup(), contact.getPhoto()));
     }
   }
  }

  private List<ContactData> generateContact(int count) {
    List<ContactData> contacts = new ArrayList<ContactData>();
    File photo = new File("src/test/resources/linux.png");
    for (int i = 0; i < count; i++) {
      contacts.add(new ContactData().withLastname(String.format("lastname %s", i)).withFirstname(String.format("firtsname %s", i))
              .withEmail(String.format("email_%s@.ru", i)).withEmail2(String.format("email2_%s@.ru", i))
              .withEmail3(String.format("email3_%s@.ru", i)).withAddress(String.format("address_%s", i))
              .withHome(String.format("12-34-%s", i)).withMobile(String.format("8912-34-%s", i))
              .withWork(String.format("12-34-5%s", i)).withGroup(String.format("test %s", i))
              .withPhoto(photo));
    }
    return contacts;
  }
}

