package git.janek79.javaeese.eese;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IntegerEqualityTest {

	@Test
	void test() {
		Integer a = new Integer(2);
		Integer b = new Integer(2);
		assertEquals(a, b);
	}

}
