package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTest extends TestBase {

  @Test
  public void testContactCreationTest() {

    goToContactCreation();
    fillContactCreation(new ContactData("test1", "test2", "test3", "test4@test.com", "89991234567"));
    submitContactCreation();
    goToHomePage();
  }

}
