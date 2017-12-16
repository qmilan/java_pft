package ru.stqa.pft.rest;

import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static com.sun.javafx.runtime.async.BackgroundExecutor.getExecutor;
import static org.testng.Assert.assertEquals;

public class restTests {

  @Test
  public void testCreateIssue() throws IOException {
    Set<Issue> oldIssues = getIssue();
    Issue newIssue = new Issue();
    int issueId = createIssue(newIssue);
    oldIssues.add(newIssue.withId(issueId));
    Set<Issue> newIssues = getIssue();
    assertEquals(newIssues,oldIssues);
  }


  private Set<Issue> getIssue() throws IOException {
    String json =  getExecutor().execute(Request.Get("http://demo.bugify.com/api/issues.json"))
            .returnContent().asString();
    return null;
  }
  private Executor getExecutor() {
    return Executor.newInstance().auth("28accbe43ea112d9feb328d2c00b3eed", "");

  }
  private int createIssue(Issue newIssue) {
    return 0;
  }

}
