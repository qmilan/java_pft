package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupDeletion extends TestBase {
  @BeforeMethod
  public void ensurePrecondition(){
    app.goTo().groupPage();
    if (app.group().all().size()==0) {
      app.group().create(new GroupData().withName("test1"));
    }
  }
  @Test
  public void testGroupDeletion() {
//    Set<GroupData> before = app.group().all();
    Groups before = app.group().all();
    GroupData deletedGroup = before.iterator().next();
    app.group().delete(deletedGroup);
//    Set<GroupData> after = app.group().all();
    Groups after = app.group().all();
    assertThat(after.size(), equalTo(before.size() - 1));
    assertThat(after, equalTo(before.withOut(deletedGroup)));
  }



}
