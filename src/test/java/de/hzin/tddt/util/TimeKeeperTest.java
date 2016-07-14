package de.hzin.tddt.util;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by julius on 15.07.16.
 */
public class TimeKeeperTest {
    @Test
    public void addCodeTime() throws Exception {
        TimeKeeper timeKeeper = new TimeKeeper();
        TimeUnit.SECONDS.sleep(4);
        timeKeeper.addCodeTime();
        boolean test = timeKeeper.getTimeCode() > 3;
        assertTrue(test);
    }
    @Test
    public void refreshCodeTime() throws Exception {
        TimeKeeper timeKeeper = new TimeKeeper();
        TimeUnit.SECONDS.sleep(2);
        timeKeeper.addTestTime();
        TimeUnit.SECONDS.sleep(2);
        timeKeeper.refreshTime();
        System.out.println(timeKeeper.getTimeTest());
        boolean test = timeKeeper.getTimeTest() > 3;
        assertTrue(test);
    }

}