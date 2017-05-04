package rostradamus.simplematrixcalculator;

import org.junit.*;

import java.util.*;

import rostradamus.simplematrixcalculator.exception.UnavailableMatrixException;
import rostradamus.simplematrixcalculator.model.*;
import rostradamus.simplematrixcalculator.model.Vector;

import static junit.framework.Assert.*;

/**
 * Created by rolee on 2017-05-03.
 */

public class MatrixTest {
    private MatrixController mc;

    @Before
    public void runBefore() {
        mc = MatrixController.getInstance();
    }

    @Test
    public void testConstructor() throws UnavailableMatrixException {
        IVectorController ivc = mc.getVectorController();
        Vector testVector1 = ivc.createVector(Arrays.asList(1.0, 4.0, 2.5));
        Vector testVector2 = ivc.createVector(Arrays.asList(2.0, 5.0, 3.5));
        Vector testVector3 = ivc.createVector(Arrays.asList(3.0, 6.0, 4.5));
        Matrix testMatrix;
        try {
            testMatrix = mc.createMatrix(Arrays.asList(testVector1, testVector2, testVector3));
        } catch (UnavailableMatrixException e) {
            Log.e(e.getMessage());
            return;
        }

        assertEquals(1.0, mc.getComponentAt(testMatrix, 0, 0));
        assertEquals(5.0, mc.getComponentAt(testMatrix, 1, 1));
        assertEquals(4.5, mc.getComponentAt(testMatrix, 2, 2));
        assertEquals(3.5, mc.getComponentAt(testMatrix, 2, 1));
        assertEquals(6.0, mc.getComponentAt(testMatrix, 1, 2));
        assertEquals(3, mc.getNumColumns(testMatrix));
        assertEquals(3, mc.getNumRows(testMatrix));

    }

    @Test
    public void testConstructorWithDiffVectorSize() {
        IVectorController ivc = mc.getVectorController();
        Vector testVector1 = ivc.createVector(Arrays.asList(3.0, 4.0));
        Vector testVector2 = ivc.createVector(Arrays.asList(1.0, 2.0, 3.5));
        try {
            Matrix testMatrix = mc.createMatrix(Arrays.asList(testVector1, testVector2));
        } catch (UnavailableMatrixException e) {
            Log.c(e.getMessage());
            return;
        }
        fail();
    }

    @Test
    public void testConstructorWithNoVector() {
        try{
            Matrix testMatrix = mc.createMatrix(new ArrayList<Vector>());
        } catch (UnavailableMatrixException e) {
            Log.c(e.getMessage());
            return;
        }
        fail();
    }
}
