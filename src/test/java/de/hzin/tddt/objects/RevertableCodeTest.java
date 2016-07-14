package de.hzin.tddt.objects;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by julius on 15.07.16.
 */
public class RevertableCodeTest {
    @Test
    public void addContent() throws Exception {
        RevertableCode revertableCode = new RevertableCode();
        revertableCode.addContent("test");
        assertEquals("test", revertableCode.getCurrentContent());
    }

    @Test
    public void addContent2() throws Exception {
        RevertableCode revertableCode = new RevertableCode();
        revertableCode.addContent("test1");
        revertableCode.addContent("test2");
        assertEquals("test2", revertableCode.getCurrentContent());
        assertEquals("test1", revertableCode.getPastContent());
    }

}