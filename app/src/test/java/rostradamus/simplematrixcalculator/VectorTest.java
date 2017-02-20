package rostradamus.simplematrixcalculator;


import org.junit.*;

import java.util.ArrayList;

import rostradamus.simplematrixcalculator.exception.UnavailableVectorException;
import rostradamus.simplematrixcalculator.model.Vector;

import static org.junit.Assert.*;

/**
 * Created by rolee on 2017-02-20.
 */

public class VectorTest {
    private Vector testVector1;
    private Vector testVector2;
    private Vector testVector3;

    @Before
    public void runBefore() throws Exception {

    }

    @Test
    public void constructorTest() {
        testVector1 = new Vector();
        assertTrue(testVector1.isNull());
        assertEquals(testVector1.getNumComponents(), 0);
        setUp();
        assertFalse(testVector1.isNull());
        assertEquals(testVector1.getNumComponents(), 3);
        assertEquals(testVector1.getComponents().get(0), Double.valueOf(3.0));
    }

    @Test (expected = UnavailableVectorException.class)
    public void testSetComponent() throws UnavailableVectorException{
        setUp();
        testVector1.setComponent(2, 3.0);
        assertEquals(testVector1.getComponents().get(2), Double.valueOf(3.0));
        assertEquals(testVector1.getNumComponents(), 3);
        // setComponent here is expected to fail (Out of Bound)
        testVector1.setComponent(5, 1);
        fail();
    }


    private void setUp() {
        testVector1 = new Vector(3.0, 1.5, 4.3);
        testVector2 = new Vector(5.4, 1.0, 2.7);
        testVector3 = new Vector(1.0, 2.0, 3.0, 4.0);
    }
}
