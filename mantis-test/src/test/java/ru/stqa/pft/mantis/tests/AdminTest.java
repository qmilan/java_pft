package ru.stqa.pft.mantis.tests;

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
  public void testAdmin () throws IOException, MessagingException {
    long now = System.currentTimeMillis();
    String email = String.format("user%s@localhost",now);
    String user = String.format("user%s",now);
    String password = "password";
    app.registration().start (user, email);
    List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
    String confirmationLink = findConfirmationLink(mailMessages, email);
    app.registration().finish(confirmationLink, password);
    // assertTrue(app.newSession().login(user,password));
    app.registration().startAdmin(app.getProperty("web.adminLogin"),
            app.getProperty("web.adminPassword"));
    app.clickOnManage();
    app.clickOnManageUsers();
    app.clickUser(user);
    app.resetPasswordOfUser();
    List<MailMessage> mailResetMessages = app.mail().waitForMail(3, 10000);
    List<MailMessage> msgs = findResetConfigrmationMail(mailResetMessages);

    String confirmationLinkReset = findConfirmationLink(msgs, email);
    String newpassword = "password1";
    app.registration().finish(confirmationLinkReset, newpassword);
    assertTrue(app.newSession().login(user,newpassword));
  }

  private List<MailMessage> findResetConfigrmationMail(List<MailMessage> messages){
    List<MailMessage> msg = messages
            .stream()
            .filter(m -> m.subject.toLowerCase().contains("password reset"))
            .collect(Collectors.toList());
    return msg;
  }

  private  String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).
            findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }
  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }

}
