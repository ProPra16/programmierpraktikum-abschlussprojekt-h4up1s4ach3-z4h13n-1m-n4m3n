package de.hzin.tddt.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by julius on 15.07.16.
 */
public class ErrorCounterTest {
    @Test
    public void addErrorCounter() throws Exception {
        ErrorCounter errorCounter = new ErrorCounter();
        errorCounter.countError("[Zeile:8]PhiFunkTest.java:8:error:';' expected");
        assertEquals(1,errorCounter.getSyntax());
    }

    @Test
    public void addErrorCounter2() throws Exception {
        ErrorCounter errorCounter = new ErrorCounter();
        errorCounter.countError("[Zeile:8]PhiFunkTest.java:8:error:cannot find symbol");
        assertEquals(1,errorCounter.getIdentifiers());
    }

    @Test
    public void addErrorCounter3() throws Exception {
        ErrorCounter errorCounter = new ErrorCounter();
        errorCounter.countError("[Zeile:8]PhiFunkTest.java:8:error:';' expected");
        errorCounter.countError("[Zeile:8]PhiFunkTest.java:8:error:cannot find symbol");
        assertEquals(1,errorCounter.getIdentifiers());
        ErrorCounter errorCounter1 = new ErrorCounter();
        errorCounter1.addErrorCounter(errorCounter.getSyntax(),errorCounter.getIdentifiers(),errorCounter.getComputation(),errorCounter.getReturnStatements(),errorCounter.getAccessToStaticEntities());
        assertEquals(1,errorCounter.getIdentifiers());
        assertEquals(1,errorCounter.getSyntax());
    }
}