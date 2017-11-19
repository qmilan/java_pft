package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.Set;

public class GroupModificationTest extends TestBase {
  @BeforeMethod
  public void ensurePrecondition (){
    app.goTo().groupPage();
    if (app.group().all().size()==0) {
      app.group().create(new GroupData().withName("test1"));
    }
  }
  @Test
  public void testGroupModificationTest() {
    Set<GroupData> before = app.group().all();
    GroupData modifyGroup = before.iterator().next();
    GroupData group = new GroupData().withId(modifyGroup.getId()).withName("test1")
            .withHeader("test2").withFooter("test3");
    app.group().modify(group);
    Set<GroupData> after = app.group().all();
    Assert.assertEquals(after.size(), before.size());
    before.remove(modifyGroup);
    before.add(group);
    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
    Assert.assertEquals(before, after);
  }


}
