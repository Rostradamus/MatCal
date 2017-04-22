
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
    private Matrix testMatrix4;
    private Vector testVectors1;
    private Vector testVectors2;
    private Vector testVectors3;


    @Before
    public void runBefore() throws Exception{
        testMatrixController = MatrixController.getInstance();
        testVectorController = testMatrixController.getVectorController();
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
        fail("Expected UnavailableMatrixException but not caught");
    }



    private void setUp() throws UnavailableMatrixException{
        Vector testVector1 = testVectorController.createVector(Arrays.asList(3.0, 1.5, 4.3));
        Vector testVector2 = testVectorController.createVector(Arrays.asList(5.4, 1.0, 2.7));
        Vector testVector3 = testVectorController.createVector(Arrays.asList(4.9, 5.6, 3.0));
        Vector testVector4 = testVectorController.createVector(Arrays.asList(1.0, 2.0, 3.0));
        Vector testVector5 = testVectorController.createVector(Arrays.asList(2.0, 3.0, 4.0));
        Vector testVector6 = testVectorController.createVector(Arrays.asList(3.0, 4.0, 5.0));
        Vector testVector7 = testVectorController.createVector(Arrays.asList(1.0, 2.0));
        Vector testVector8 = testVectorController.createVector(Arrays.asList(4.0, 3.0));

        try {
            testMatrix1 = testMatrixController.createMatrix(Arrays.asList(testVector1, testVector2, testVector3));
            testMatrix2 = testMatrixController.createMatrix(Arrays.asList(testVector4, testVector5, testVector6));
            testMatrix3 = testMatrixController.createMatrix(Arrays.asList(testVector7, testVector8));
        } catch (UnavailableMatrixException e) {
            System.out.println(e.getMessage());
            fail();
        }

        // Vector testVector7 = testVectorController.createVector(Arrays.asList(1.0, 2.0, 3.0, 4.0));
    }


}