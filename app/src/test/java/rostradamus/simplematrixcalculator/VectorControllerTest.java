package rostradamus.simplematrixcalculator;


import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import rostradamus.simplematrixcalculator.exception.UnavailableVectorException;
import rostradamus.simplematrixcalculator.model.Vector;
import rostradamus.simplematrixcalculator.model.VectorController;

import static org.junit.Assert.*;

/**
 * Created by rolee on 2017-02-20.
 */

public class VectorControllerTest {
    private VectorController testVectorController;
    private Vector testVector1;
    private Vector testVector2;
    private Vector testVector3;
    private static final double DEFAULT_DELTA = 10e-7;

    @Before
    public void runBefore() throws Exception {
        testVectorController = VectorController.getInstance();
        testVector1 = testVectorController.createVector(new ArrayList<Double>());
        testVector2 = testVectorController.createVector(new ArrayList<Double>());
        testVector3 = testVectorController.createVector(new ArrayList<Double>());
    }

    @Test
    public void constructorTest() {
        assertTrue(testVectorController.isNull(testVector1));
        assertEquals(testVectorController.getNumComponents(testVector1), 0);
        setUp();
        assertFalse(testVectorController.isNull(testVector1));
        assertEquals(testVectorController.getNumComponents(testVector1), 3);
        assertEquals(testVectorController.getComponentAt(testVector1, 0), 3.0, DEFAULT_DELTA);
    }

    @Test
    public void testAddComponent() {
        testVectorController.addComponent(testVector1, 1.0);
        assertEquals(testVectorController.getComponentAt(testVector1, 0), 1.0, DEFAULT_DELTA);
        assertFalse(testVectorController.isNull(testVector1));
        testVectorController.addComponent(testVector1, 2.0);
        assertEquals(testVectorController.getNumComponents(testVector1), 2);
        assertEquals(testVectorController.getComponentAt(testVector1, 1), 2.0, DEFAULT_DELTA);
    }

    @Test
    public void testNorm() throws UnavailableVectorException {
        setUp();
        double expected = Math.sqrt(Math.pow(3.0, 2) + Math.pow(1.5, 2) + Math.pow(4.3, 2));
        assertEquals(testVectorController.norm(testVector1), expected, DEFAULT_DELTA);
        expected = Math.sqrt(Math.pow(5.4, 2) + Math.pow(1.0, 2) + Math.pow(2.7, 2));
        assertEquals(testVectorController.norm(testVector2), expected, DEFAULT_DELTA);
        expected = Math.sqrt(Math.pow(1.0, 2) + Math.pow(2.0, 2) + Math.pow(3.0, 2) + Math.pow(4.0, 2));
        assertEquals(testVectorController.norm(testVector3), expected, DEFAULT_DELTA);

        try {
            testVector1 = testVectorController.createVector(new ArrayList<Double>());
            testVectorController.norm(testVector1);
        } catch (UnavailableVectorException e) {
            Log.c(e.getMessage());
            return;
        }
        fail();
    }

    @Test
    public void testDotProduct() throws UnavailableVectorException {
        List<Double> l1 = Arrays.asList(3.0, 1.5, 4.3);
        List<Double> l2 = Arrays.asList(5.4, 1.0, 2.7);
        List<Double> l3 = Arrays.asList(1.0, 2.0, 3.0, 4.0);
        testVector1 = testVectorController.createVector(l1);
        testVector2 = testVectorController.createVector(l2);
        testVector3 = testVectorController.createVector(l3);
        setUp();
        double expected = (3.0 * 5.4) + (1.5 * 1.0) + (4.3 * 2.7);
        assertEquals(testVectorController.dotProduct(testVector1,testVector2), expected, DEFAULT_DELTA);
    }

    @Test
    public void testDotProductWithDifferentVectorComponents() throws UnavailableVectorException {
        try {
            List<Double> l1 = Arrays.asList(1.0, 2.0);
            List<Double> l2 = Collections.singletonList(3.0);
            testVector1 = testVectorController.createVector(l1);
            testVector2 = testVectorController.createVector(l2);
            testVectorController.dotProduct(testVector1, testVector2);
        } catch (UnavailableVectorException e) {
            Log.c(e.getMessage());
            return;
        }
        fail();
    }

    @Test
    public void testDotProductWithTwoNullVector() throws UnavailableVectorException {
        try {
            testVectorController.dotProduct(testVector1, testVector2);
        } catch (UnavailableVectorException e) {
            Log.c(e.getMessage());
            return;
        }
        fail();
    }

    @Test
    public void testDotProductWithLeftNullVector() throws UnavailableVectorException {

        testVector1 = testVectorController.createVector(Arrays.asList(1.0));
        try {
            testVectorController.dotProduct(testVector1, testVector2);
        } catch (UnavailableVectorException e) {
            Log.c(e.getMessage());
            return;
        }
        fail();
    }
    @Test
    public void testDotProductWithRightNullVector() throws UnavailableVectorException {
        testVector2 = testVectorController.createVector(Arrays.asList(1.0));
        try {
            testVectorController.dotProduct(testVector1, testVector2);
        } catch (UnavailableVectorException e) {
            Log.c(e.getMessage());
            return;
        }
        fail();
    }

    //TODO
    @Test
    public void testAddition() throws UnavailableVectorException {

    }

    //TODO
    @Test
    public void testCrossProduct() throws UnavailableVectorException {
        setUp();
        Vector testResult = testVectorController.crossProduct(testVector1, testVector2);

        assertEquals(-0.25, testVectorController.getComponentAt(testResult, 0), DEFAULT_DELTA);
        assertEquals(15.12, testVectorController.getComponentAt(testResult, 1), DEFAULT_DELTA);
        assertEquals(-5.1, testVectorController.getComponentAt(testResult, 2), DEFAULT_DELTA);
    }

    //TODO
    @Test (expected = UnavailableVectorException.class)
    public void testCrossProductWithDifferentSize() throws UnavailableVectorException  {
        Vector v1 = testVectorController.createVector(Arrays.asList(4.0, 5.0));
        Vector v2 = testVectorController.createVector(Arrays.asList(5.0, 6.0, 7.0));

        testVectorController.crossProduct(v1, v2);

    }

    @Test (expected = UnavailableVectorException.class)
    public void testCrossProductWithNullVector() throws UnavailableVectorException {
        Vector v1 = null;
        Vector v2 = testVectorController.createVector(Arrays.asList(5.0, 6.0, 7.0));

        testVectorController.crossProduct(v1, v2);
    }

    @Test
    public void testUnitVector() throws UnavailableVectorException {
        setUp();
        Vector actual = testVectorController.unitVector(testVector1);
        double norm = testVectorController.norm(testVector1);
        List<Double> l1 = Arrays.asList((3.0 / norm), (1.5 / norm), (4.3 / norm));
        Vector expected = testVectorController.createVector(l1);

        assertEquals(expected, actual);
        try {
            testVectorController.unitVector(testVectorController.createVector(new ArrayList<Double>()));
        } catch (UnavailableVectorException e) {
            Log.c(e.getMessage());
            return;
        }
        fail();
    }

    private void setUp() {
        List<Double> l1 = Arrays.asList(3.0, 1.5, 4.3);
        List<Double> l2 = Arrays.asList(5.4, 1.0, 2.7);
        List<Double> l3 = Arrays.asList(1.0, 2.0, 3.0, 4.0);
        testVector1 = testVectorController.createVector(l1);
        testVector2 = testVectorController.createVector(l2);
        testVector3 = testVectorController.createVector(l3);
    }
}
