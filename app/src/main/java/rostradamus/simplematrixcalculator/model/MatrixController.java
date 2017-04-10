package rostradamus.simplematrixcalculator.model;

/**
 * Created by rolee on 2017-03-06.
 */

public class MatrixController implements IMatrixController {
    private static MatrixController instance;
    MatrixController() {
        System.out.println("Matrix Controller Created...");
    }

    public static MatrixController getInstance() {
        if (instance == null) instance = new MatrixController();
        return instance;
    }
}
