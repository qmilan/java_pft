package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletion extends TestBase {

    @Test
    public void ContactDeletion() {
       app.getNavigationHelper().goToHomePage();
       app.getContactHelper().selectContact();
       app.getContactHelper().deleteSelectedContact();
       app.getContactHelper().submitDeletion();
       app.getNavigationHelper().goToHomePage();
    }

}
