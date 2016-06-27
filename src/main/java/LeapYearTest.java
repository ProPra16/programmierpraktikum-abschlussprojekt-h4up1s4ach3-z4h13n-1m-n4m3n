import static org.junit.Assert.*;
import org.junit.*;

public class LeapYearTest {
    @Test
    public void testone()  {
        int input = 1;
        assertEquals(false, LeapYear.isLeapYear(input));
    }
    @Test
    public void multi()  {
        int input = 800;
        assertEquals(true, LeapYear.isLeapYear(input));
    }
    @Test
    public void isleap()  {
        int input = 4;
        assertEquals(true, LeapYear.isLeapYear(input));
    }
    @Test
    public void isleapmult()  {
        int input = 16;
        assertEquals(true, LeapYear.isLeapYear(input));
    }
    @Test
    public void hundredexept()  {
        int input = 200;
        assertEquals(false, LeapYear.isLeapYear(input));
    }    
}

