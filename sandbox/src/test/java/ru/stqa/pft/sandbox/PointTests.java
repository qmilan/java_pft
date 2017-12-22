package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {
//доп.проверка
    @Test
    public void testDistance () {
        Point p1 = new Point(0,0);
        Point p2 = new Point(4,3);
        Assert.assertEquals(MyFirstProgram.distance(p1,p2),15.0);
    }
//проверка в точке p1
    @Test
    public void p1Distance () {
        Point p1 = new Point(0,0);
        Point p2 = new Point(8,0);
        Assert.assertEquals(p1.distance(p2),8.0);
    }
//проверка в точке p2
    @Test
    public void p2Distance () {
        Point p1 = new Point(3,3);
        Point p2 = new Point(7,6);
        Assert.assertEquals(p2.distance(p1),5.0);
    }
}
