package ru.stqa.pft.mantis.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertTrue;

public class AdminTest extends TestBase {
  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testAdmin() throws IOException, MessagingException {
    app.user().startAdmin(app.getProperty("web.adminLogin"),
            app.getProperty("web.adminPassword"));
    app.user().clickOnManage();
    app.user().clickOnManageUsers();
    String userLogin = app.all().iterator().next();
    app.user().clickUser(userLogin);
    app.user().resetPasswordOfUser();
    List<MailMessage> mailResetMessages = app.mail().waitForMail(1, 10000);
    List<MailMessage> msgs = findResetConfigrmationMail(mailResetMessages);
    String confirmationLinkReset = findConfirmationLink(msgs, userLogin);
    app.user().finish(confirmationLinkReset, app.getProperty("web.newUserPassword"));
    assertTrue(app.newSession().login(userLogin, app.getProperty("web.newUserPassword")));
  }

  private List<MailMessage> findResetConfigrmationMail(List<MailMessage> messages) {
    List<MailMessage> msg = messages
            .stream()
            .filter(m -> m.subject.toLowerCase().contains("password reset"))
            .collect(Collectors.toList());
    return msg;
  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.contains(email)).
            findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }


  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }

}
