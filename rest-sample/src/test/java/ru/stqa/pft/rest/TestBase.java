package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.testng.SkipException;

import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

public class TestBase {
  public Executor getExecutor() {
    return Executor.newInstance().auth("28accbe43ea112d9feb328d2c00b3eed", "");

  }
  public boolean isIssueOpen(int issueId) throws IOException {
    String status = getStatusById(issueId);
    if (status.equals("resolved") || status.equals("closed")) {
      return true;
    } else {
      return false;
    }
  }
  public String getStatusById(int issueId) throws IOException {
    String json =  getExecutor().execute(Request.
            Get(String.format("http://demo.bugify.com/issues/%s.json",issueId)))
            .returnContent().toString();
      JsonElement parsed = new JsonParser().parse(json);
      JsonElement issues = parsed.getAsJsonObject().get("issues");
      Set<Issue> issue= new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
    return issue.iterator().next().getStatus();
    }



  public void skipIfNotFixed(int issueId) throws IOException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }
}


