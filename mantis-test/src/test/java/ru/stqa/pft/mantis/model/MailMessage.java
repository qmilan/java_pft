package ru.stqa.pft.mantis.model;

public class MailMessage {
  public String to;
  public String text;
  public String subject;
  public  MailMessage (String to,String text, String subject) {
    this.to = to;
    this.text = text;
    this.subject = subject;
  }
}
