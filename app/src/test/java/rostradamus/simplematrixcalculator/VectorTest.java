package rostradamus.simplematrixcalculator;


import org.junit.*;

import rostradamus.simplematrixcalculator.model.Vector;

import static org.junit.Assert.*;

/**
 * Created by rolee on 2017-02-20.
 */

public class VectorTest {
    private Vector testVector;

    @Before
    public void runBefore() throws Exception {

    }

    @Test
    public void constructorTest() {
        testVector = new Vector();
        assertTrue(testVector.isNull());
        assertEquals(testVector.getNumComponents(), 0);

    }
}
