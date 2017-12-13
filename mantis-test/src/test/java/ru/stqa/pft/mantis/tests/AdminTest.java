package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;

import java.io.IOException;

public class AdminTest extends TestBase {
  @Test
  public void testAdmin () throws IOException {
    app.registration().startAdmin(app.getProperty("web.adminLogin"),
            app.getProperty("web.adminPassword"));
    app.clickOnManage();
    app.clickOnManageUsers();
  }


}
