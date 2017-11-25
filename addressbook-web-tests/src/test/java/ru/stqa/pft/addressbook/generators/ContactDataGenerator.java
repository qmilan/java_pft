package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {
  @Parameter(names = "-c", description = "Contact count")
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file;

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
    save(contacts, new File(file));
  }

  private void save(List<ContactData> contacts, File file) throws IOException {
    Writer writer = new FileWriter(file);
    for (ContactData contact : contacts) {
      writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n", contact.getLastname(), contact.getFirstname(), contact.getEmail(),
              contact.getEmail2(), contact.getEmail3(), contact.getAddress(), contact.getHome()
              , contact.getMobile(), contact.getWork(), contact.getGroup()));
    }
    writer.close();
  }

  private List<ContactData> generateContact(int count) {
    List<ContactData> contacts = new ArrayList<ContactData>();
    for (int i = 0; i < count; i++) {
      contacts.add(new ContactData().withLastname(String.format("lastname %s", i)).withFirstname(String.format("firtsname %s", i))
              .withEmail(String.format("email_%s@.ru", i)).withEmail2(String.format("email2_%s@.ru", i))
              .withEmail3(String.format("email3_%s@.ru", i)).withAddress(String.format("address_%s", i))
              .withHome(String.format("12-34-%s", i)).withMobile(String.format("8912-34-%s", i))
              .withWork(String.format("12-34-5%s", i)).withGroup(String.format("test %s", i)));
    }
    return contacts;
  }
}

