package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTest extends TestBase {
  @BeforeMethod
  public void ensurePrecondition() {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testGroupModificationTest() {
//    Set<GroupData> before = app.group().all();
    Groups before = app.db().groups();
    GroupData modifyGroup = before.iterator().next();
    GroupData group = new GroupData().withId(modifyGroup.getId()).withName("test1")
            .withHeader("test2").withFooter("test3");
    app.goTo().groupPage();
    app.group().modify(group);
//    Set<GroupData> after = app.group().all();
    Groups after = app.db().groups();
    assertThat(after.size(), equalTo(before.size()));
    assertThat(after, equalTo(before.withOut(modifyGroup).withAdded(group)));
  }


}
