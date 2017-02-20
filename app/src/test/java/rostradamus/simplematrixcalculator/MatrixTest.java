
package rostradamus.simplematrixcalculator;

import org.junit.*;

import rostradamus.simplematrixcalculator.exception.UnavailableMatrixException;
import rostradamus.simplematrixcalculator.model.Matrix;

import static org.junit.Assert.*;

/**
 * Created by rolee on 2017-02-17.
 */

public class MatrixTest {

    private Matrix testMatrix;

    @Before
    public void runBefore() throws Exception{

    }

    @Test (expected = UnavailableMatrixException.class)
    public void constructor() throws UnavailableMatrixException{
        Matrix wrongMatrix = new Matrix();
        fail();
    }


}