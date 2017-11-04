package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletion extends TestBase {

    @Test
    public void ContactDeletion() {
       app.getNavigationHelper().goToHomePage();
       if (! app.getContactHelper().isThereAContact ()) {
         app.getNavigationHelper().goToContactCreationPage();
         app.getContactHelper().createContact (new ContactData("test1", "test2", "test3", "test4@test.com", "89991234567","test1"), true);
       }
       app.getContactHelper().selectContact();
       app.getContactHelper().deleteSelectedContact();
       app.getContactHelper().submitDeletion();
       app.getNavigationHelper().goToHomePage();
    }

}
