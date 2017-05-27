
package rostradamus.simplematrixcalculator;

import org.junit.*;

import java.util.*;


import rostradamus.simplematrixcalculator.exception.UnavailableMatrixException;
import rostradamus.simplematrixcalculator.model.Matrix;
import rostradamus.simplematrixcalculator.model.MatrixController;
import rostradamus.simplematrixcalculator.model.Vector;
import rostradamus.simplematrixcalculator.model.VectorController;

import static org.junit.Assert.*;

/**
 * Created by rolee on 2017-02-17.
 */

public class MatrixControllerTest {

    private MatrixController testMatrixController;
    private VectorController testVectorController;
    private Matrix testMatrix1;
    private Matrix testMatrix2;
    private Matrix testMatrix3;
//    private Matrix testMatrix4;
//    private Vector testVectors1;
//    private Vector testVectors2;
//    private Vector testVectors3;

    private static final String EXCEPTION_UNCAUGHT_MSG = "Expected UnavailableMatrixException but not caught";
    private static final double DEFAULT_DELTA = 10e-7;


    @Before
    public void runBefore() throws Exception{
        testMatrixController = MatrixController.getInstance();
        testVectorController = VectorController.getInstance();
        setUp();
    }


    @Test
    public void testAddition() throws UnavailableMatrixException {
        List<Vector> vectors = Arrays.asList(
                testVectorController.createVector(Arrays.asList(4.0, 3.5, 7.3)),
                testVectorController.createVector(Arrays.asList(7.4, 4.0, 6.7)),
                testVectorController.createVector(Arrays.asList(7.9, 9.6, 8.0))
        );
        Matrix expected = testMatrixController.createMatrix(vectors);
        Matrix actual = testMatrixController.add(testMatrix1, testMatrix2);
        assertEquals(expected, actual);

    }

    @Test (expected = UnavailableMatrixException.class)
    public void testAdditionWithDifferentNumRows() throws UnavailableMatrixException {
        testMatrixController.add(testMatrix1, testMatrix3);
        fail(EXCEPTION_UNCAUGHT_MSG);
    }

    @Test
    public void testDeterminant() throws UnavailableMatrixException {
        double expected = testMatrixController.determinant(testMatrix1);
        assertEquals(expected,  68.14699999999999, DEFAULT_DELTA);

        Vector v1 = testVectorController.createVector(Arrays.asList(3.0, 1.5, 4.3, 1.6, 4.9));
        Vector v2 = testVectorController.createVector(Arrays.asList(5.4, 1.0, 2.7, 11.4, 999.0));
        Vector v3 = testVectorController.createVector(Arrays.asList(4.9, 5.6, 3.0, 1.7, 4.55));
        Vector v4 = testVectorController.createVector(Arrays.asList(1.0, 2.0, 3.0, 33.4, 54.1));
        Vector v5 = testVectorController.createVector(Arrays.asList(2.0, 3.0, 4.0, -4.31, -405.3));

        Matrix testMatrix = testMatrixController.createMatrix(Arrays.asList(v1, v2, v3, v4, v5));

        expected = testMatrixController.determinant(testMatrix);
        assertEquals(expected, 275989.8161120002, DEFAULT_DELTA);
    }

    @Test (expected = UnavailableMatrixException.class)
    public void testDeterminantWithNonSquareMatrix() throws UnavailableMatrixException {
        Vector v1 = testVectorController.createVector(Arrays.asList(3.0, 1.5, 4.3));
        Vector v2 = testVectorController.createVector(Arrays.asList(5.4, 1.0, 2.7));
        Matrix testMatrix = testMatrixController.createMatrix(Arrays.asList(v1, v2));
        double result = testMatrixController.determinant(testMatrix);
        fail(EXCEPTION_UNCAUGHT_MSG);
    }

    private void setUp() throws UnavailableMatrixException{
        Vector v1 = testVectorController.createVector(Arrays.asList(3.0, 1.5, 4.3));
        Vector v2 = testVectorController.createVector(Arrays.asList(5.4, 1.0, 2.7));
        Vector v3 = testVectorController.createVector(Arrays.asList(4.9, 5.6, 3.0));
        Vector v4 = testVectorController.createVector(Arrays.asList(1.0, 2.0, 3.0));
        Vector v5 = testVectorController.createVector(Arrays.asList(2.0, 3.0, 4.0));
        Vector v6 = testVectorController.createVector(Arrays.asList(3.0, 4.0, 5.0));
        Vector v7 = testVectorController.createVector(Arrays.asList(1.0, 2.0));
        Vector v8 = testVectorController.createVector(Arrays.asList(4.0, 3.0));
        testMatrix1 = testMatrixController.createMatrix(Arrays.asList(v1, v2, v3));
        testMatrix2 = testMatrixController.createMatrix(Arrays.asList(v4, v5, v6));
        testMatrix3 = testMatrixController.createMatrix(Arrays.asList(v7, v8));

        // Vector v7 = testVectorController.createVector(Arrays.asList(1.0, 2.0, 3.0, 4.0));
    }
}