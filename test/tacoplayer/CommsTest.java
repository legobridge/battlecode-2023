package tacoplayer;

import org.junit.Test;

import static org.junit.Assert.*;

public class CommsTest {
    @Test
    public void testGetNumFromBits() {
        // 17 is 10001
        // 1  is  0001
        // 8  is 1000
        assertEquals(1, Comms.getNumFromBits(17, 1, 4));
        assertEquals(8, Comms.getNumFromBits(17, 2, 5));
    }
}