package examplefuncsplayer;

import static org.junit.Assert.*;

import battlecode.common.MapLocation;
import org.junit.Test;

public class RobotPlayerTest {

	@Test
	public void testSanity() {
		assertEquals(2, 1+1);
	}

	@Test
	public void distTest1() {
		MapLocation a = new MapLocation(2, 2);
		MapLocation b = new MapLocation(2, 3);
		assertEquals(1, a.distanceSquaredTo(b));
	}

	@Test
	public void distTest2() {
		MapLocation a = new MapLocation(2, 2);
		MapLocation b = new MapLocation(3, 3);
		assertEquals(2, a.distanceSquaredTo(b));
	}
}
