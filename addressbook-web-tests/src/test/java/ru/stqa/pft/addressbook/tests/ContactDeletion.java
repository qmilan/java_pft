package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactDeletion extends TestBase {

    @Test
    public void ContactDeletion() {
       app.getNavigationHelper().goToHomePage();
       if (! app.getContactHelper().isThereAContact ()) {
         app.getNavigationHelper().goToGroupPage();
         if (! app.getGroupHelper().isThereAGroup ()){
           app.getGroupHelper().createGroup (new GroupData("test1", null, null));
         }
         app.getNavigationHelper().goToContactCreationPage();
         app.getContactHelper().createContact (new ContactData("test1", "test2", "test3", "test4@test.com", "89991234567","test1"), true);
       }
       app.getContactHelper().selectContact();
       app.getContactHelper().deleteSelectedContact();
       app.getContactHelper().submitDeletion();
       app.getNavigationHelper().goToHomePage();
    }

}
