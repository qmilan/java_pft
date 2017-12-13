package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.mantis.appmanager.HttpSession;

import java.io.IOException;

public class AdminTest extends TestBase {
  @Test
  public void testAdmin () throws IOException {
    String user = "administrator";
    String password = "root";
    app.registration().starAdmint(user, password);
  }


}
