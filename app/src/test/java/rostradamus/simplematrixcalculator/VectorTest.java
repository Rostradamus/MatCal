package rostradamus.simplematrixcalculator;


import org.junit.*;

import java.util.ArrayList;

import rostradamus.simplematrixcalculator.exception.UnavailableVectorException;
import rostradamus.simplematrixcalculator.model.Vector;
import rostradamus.simplematrixcalculator.model.VectorController;

import static org.junit.Assert.*;

/**
 * Created by rolee on 2017-02-20.
 */

public class VectorTest {
    private VectorController testVectorController;
    private Vector testVector1;
    private Vector testVector2;
    private Vector testVector3;
    private double doublePrecision = 10e-19;

    @Before
    public void runBefore() throws Exception {
        testVectorController = VectorController.getInstance();
        testVector1 = testVectorController.createVector();
        testVector2 = testVectorController.createVector();
        testVector3 = testVectorController.createVector();
    }

    @Test
    public void constructorTest() {
        assertTrue(testVectorController.isNull(testVector1));
        assertEquals(testVectorController.getNumComponents(testVector1), 0);
        setUp();
        assertFalse(testVectorController.isNull(testVector1));
        assertEquals(testVectorController.getNumComponents(testVector1), 3);
        assertEquals(testVectorController.getComponents(testVector1).get(0), Double.valueOf(3.0));
    }

    @Test
    public void testSetComponent() throws UnavailableVectorException{
        setUp();
        testVectorController.setComponent(testVector1, 2, 3.0);
        assertEquals(testVectorController.getComponents(testVector1).get(2), Double.valueOf(3.0));
        assertEquals(testVectorController.getNumComponents(testVector1), 3);
        // setComponent here is expected to fail (Out of Bound)
        try {
            testVectorController.setComponent(testVector1, 5, 1);
        } catch (UnavailableVectorException e) {
            System.out.println("Expected Error caught. " + e.getMessage());
            return;
        }
        fail();
    }

    @Test
    public void testAddComponent() {
        testVectorController.addComponent(testVector1, 1.0);
        assertEquals(testVectorController.getComponents(testVector1).get(0), Double.valueOf(1.0));
        assertFalse(testVectorController.isNull(testVector1));
        testVectorController.addComponent(testVector1, 2.0);
        assertEquals(testVectorController.getNumComponents(testVector1), 2);
        assertEquals(testVectorController.getComponents(testVector1).get(1), Double.valueOf(2.0));
    }

    @Test
    public void testNorm() throws UnavailableVectorException {
        setUp();
        double expected = Math.sqrt(Math.pow(3.0, 2) + Math.pow(1.5, 2) + Math.pow(4.3, 2));
        assertEquals(testVectorController.norm(testVector1), expected, doublePrecision);
        expected = Math.sqrt(Math.pow(5.4, 2) + Math.pow(1.0, 2) + Math.pow(2.7, 2));
        assertEquals(testVectorController.norm(testVector2), expected, doublePrecision);
        expected = Math.sqrt(Math.pow(1.0, 2) + Math.pow(2.0, 2) + Math.pow(3.0, 2) + Math.pow(4.0, 2));
        assertEquals(testVectorController.norm(testVector3), expected, doublePrecision);

        try {
            testVector1 = testVectorController.createVector();
            testVectorController.norm(testVector1);
        } catch (UnavailableVectorException e) {
            System.out.println("Expected Error caught. " + e.getMessage());
            return;
        }
        fail();
    }

    @Test
    public void testDotProduct() throws UnavailableVectorException {
        testVector1 = testVectorController.createVector(3.0, 1.5, 4.3);
        testVector2 = testVectorController.createVector(5.4, 1.0, 2.7);
        testVector3 = testVectorController.createVector(1.0, 2.0, 3.0, 4.0);
        setUp();
        double expected = (3.0 * 5.4) + (1.5 * 1.0) + (4.3 * 2.7);
        assertEquals(testVectorController.dotProduct(testVector1,testVector2), expected, doublePrecision);
    }

    @Test
    public void testDotProductWithDifferentVectorComponents() throws UnavailableVectorException {
        try {
            testVector1 = testVectorController.createVector(1.0, 2.0);
            testVector2 = testVectorController.createVector(3.0);
            testVectorController.dotProduct(testVector1, testVector2);
        } catch (UnavailableVectorException e) {
            System.out.println("Expected Error caught. " + e.getMessage());
            return;
        }
        fail();
    }

    @Test
    public void testDotProductWithTwoNullVector() throws UnavailableVectorException {
        try {
            testVectorController.dotProduct(testVector1, testVector2);
        } catch (UnavailableVectorException e) {
            System.out.println("Expected Error caught. " + e.getMessage());
            return;
        }
        fail();
    }

    @Test
    public void testDotProductWithLeftNullVector() throws UnavailableVectorException {
        testVector1 = testVectorController.createVector(1.0);
        try {
            testVectorController.dotProduct(testVector1, testVector2);
        } catch (UnavailableVectorException e) {
            System.out.println("Expected Error caught. " + e.getMessage());
            return;
        }
        fail();
    }
    @Test
    public void testDotProductWithRightNullVector() throws UnavailableVectorException {
        testVector2 = testVectorController.createVector(1.0);
        try {
            testVectorController.dotProduct(testVector1, testVector2);
        } catch (UnavailableVectorException e) {
            System.out.println("Expected Error caught. " + e.getMessage());
            return;
        }
        fail();
    }

    @Test
    public void testUnitVector() throws UnavailableVectorException {
        setUp();
        Vector actual = testVectorController.unitVector(testVector1);
        double norm = testVectorController.norm(testVector1);
        Vector expected = testVectorController.createVector((3.0 / norm), (1.5 / norm), (4.3 / norm));
        assertEquals(expected, actual);

        try {
            testVectorController.unitVector(testVectorController.createVector());
        } catch (UnavailableVectorException e) {
            System.out.println("Expected Error caught. " + e.getMessage());
            return;
        }
        fail();
    }


    private void setUp() {
        testVector1 = testVectorController.createVector(3.0, 1.5, 4.3);
        testVector2 = testVectorController.createVector(5.4, 1.0, 2.7);
        testVector3 = testVectorController.createVector(1.0, 2.0, 3.0, 4.0);
    }
}
